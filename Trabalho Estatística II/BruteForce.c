// bruteforce_fork.c
// Compilar: gcc -O2 -std=c11 bruteforce_fork.c -o bruteforce_fork
// Uso: ./bruteforce_fork <senha_alvo> <comprimento_fixo>
// Ex:  ./bruteforce_fork 1234 4

#define _POSIX_C_SOURCE 199309L
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <stdint.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <signal.h>
#include <stdatomic.h>
#include <errno.h>

#define CHILDREN 3
#define MAX_PASS 512

static void timespec_diff(const struct timespec *start, const struct timespec *end, struct timespec *out) {
    out->tv_sec = end->tv_sec - start->tv_sec;
    out->tv_nsec = end->tv_nsec - start->tv_nsec;
    if (out->tv_nsec < 0) {
        out->tv_sec -= 1;
        out->tv_nsec += 1000000000L;
    }
}

typedef struct {
    atomic_int found;               // 0 = nada, 1 = encontrado
    atomic_int winner_idx;          // 0..CHILDREN-1
    pid_t winner_pid;
    _Atomic uint64_t attempts[CHILDREN];
    char winner_candidate[MAX_PASS];
    struct timespec start_time;
    struct timespec end_time;
} shared_t;

void build_charset_for_mode(int modo, char *out_charset, size_t *out_len) {
    size_t l = 0;
    if (modo == 1) {
        for (char c = '0'; c <= '9'; ++c) out_charset[l++] = c;
    } else if (modo == 2) {
        for (char c = 'a'; c <= 'z'; ++c) out_charset[l++] = c;
    } else if (modo == 3) {
        for (char c = 'a'; c <= 'z'; ++c) out_charset[l++] = c;
        for (char c = 'A'; c <= 'Z'; ++c) out_charset[l++] = c;
    }
    out_charset[l] = '\0';
    *out_len = l;
}

void brute_worker(const char *target, size_t target_len, size_t fixed_len, shared_t *sh, int child_idx) {
    // modo por child: 1,2,3
    int modo = child_idx + 1;
    char charset[128];
    size_t charset_len = 0;
    build_charset_for_mode(modo, charset, &charset_len);
    if (charset_len == 0) {
        _exit(1);
    }

    if (fixed_len >= MAX_PASS) {
        fprintf(stderr, "fixed_len too large (max %d)\n", MAX_PASS-1);
        _exit(1);
    }

    char *candidate = malloc(fixed_len + 1);
    if (!candidate) _exit(1);
    candidate[fixed_len] = '\0';
    size_t *idx = calloc(fixed_len, sizeof(size_t));
    if (!idx) { free(candidate); _exit(1); }

    // initialize
    for (size_t i = 0; i < fixed_len; ++i) {
        candidate[i] = charset[0];
        idx[i] = 0;
    }

    // local clock
    struct timespec t_local_start, t_local_end;
    if (clock_gettime(CLOCK_MONOTONIC, &t_local_start) != 0) { /* ignore */ }

    int found_local = 0;
    int overflow = 0;

    while (!overflow && !found_local) {
        // check global flag first -> exit if someone else found
        if (atomic_load_explicit(&sh->found, memory_order_relaxed)) break;

        // attempt
        atomic_fetch_add_explicit(&sh->attempts[child_idx], 1, memory_order_relaxed);

        // compare only if lengths match
        if (fixed_len == target_len && strcmp(candidate, target) == 0) {
            // try to become the winner (only first succeeds)
            int expected = 0;
            if (atomic_compare_exchange_strong_explicit(&sh->found, &expected, 1,
                                                        memory_order_acq_rel, memory_order_relaxed)) {
                // we are the winner -> write info
                atomic_store_explicit(&sh->winner_idx, child_idx, memory_order_relaxed);
                sh->winner_pid = getpid();
                strncpy(sh->winner_candidate, candidate, MAX_PASS-1);
                sh->winner_candidate[MAX_PASS-1] = '\0';
                if (clock_gettime(CLOCK_MONOTONIC, &t_local_end) == 0) {
                    sh->end_time = t_local_end;
                }
            }
            found_local = 1;
            break;
        }

        // increment odometer (position 0 = least significant)
        size_t pos = 0;
        while (pos < fixed_len) {
            idx[pos] += 1;
            if (idx[pos] < charset_len) {
                candidate[pos] = charset[idx[pos]];
                break;
            } else {
                idx[pos] = 0;
                candidate[pos] = charset[0];
                pos++;
            }
        }
        if (pos == fixed_len) overflow = 1;
    }

    free(candidate);
    free(idx);
    _exit(found_local ? 0 : 2); // different exit codes (not required)
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Uso: %s <senha_alvo> <comprimento_fixo>\n", argv[0]);
        fprintf(stderr, "Este programa executa simultaneamente 3 modos (1=numeros,2=minusculas,3=min+MAI).\n");
        return 1;
    }

    const char *target = argv[1];
    long fixed_len_l = atol(argv[2]);
    if (fixed_len_l <= 0) {
        fprintf(stderr, "comprimento_fixo deve ser >= 1\n");
        return 1;
    }
    size_t fixed_len = (size_t) fixed_len_l;
    size_t target_len = strlen(target);

    if (fixed_len >= MAX_PASS) {
        fprintf(stderr, "comprimento_fixo muito grande (max %d)\n", MAX_PASS-1);
        return 1;
    }

    // mmap shared struct
    shared_t *sh = mmap(NULL, sizeof(shared_t), PROT_READ | PROT_WRITE,
                        MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    if (sh == MAP_FAILED) {
        perror("mmap");
        return 1;
    }

    // initialize shared
    atomic_init(&sh->found, 0);
    atomic_init(&sh->winner_idx, -1);
    sh->winner_pid = 0;
    for (int i = 0; i < CHILDREN; ++i) atomic_init(&sh->attempts[i], 0);
    memset(sh->winner_candidate, 0, sizeof(sh->winner_candidate));
    if (clock_gettime(CLOCK_MONOTONIC, &sh->start_time) != 0) perror("clock_gettime");

    pid_t pids[CHILDREN];
    for (int i = 0; i < CHILDREN; ++i) {
        pid_t pid = fork();
        if (pid < 0) {
            perror("fork");
            // kill previously spawned children
            for (int j = 0; j < i; ++j) kill(pids[j], SIGTERM);
            return 1;
        } else if (pid == 0) {
            // child
            brute_worker(target, target_len, fixed_len, sh, i);
            // never returns
        } else {
            pids[i] = pid;
        }
    }

    // parent: wait until found is set
    while (!atomic_load_explicit(&sh->found, memory_order_acquire)) {
        // small sleep to avoid busy-waiting
        struct timespec ts = {0, 10000000L}; // 10ms
        nanosleep(&ts, NULL);

        // also check if all children exited without finding
        int status;
        pid_t w = waitpid(-1, &status, WNOHANG);
        if (w == 0) {
            // no child status yet
        } else if (w > 0) {
            // one child finished; check if any other child still running
            int running = 0;
            for (int i = 0; i < CHILDREN; ++i) {
                if (pids[i] == w) continue;
                if (kill(pids[i], 0) == 0) running = 1;
            }
            if (!running) {
                // no child running -> nobody found; break and print totals
                break;
            }
        } else {
            // waitpid error
            if (errno != ECHILD) { /* ignore */ }
            break;
        }
    }

    // if found: sh->end_time must be set by winner; if not, set end_time now
    struct timespec t_now;
    if (clock_gettime(CLOCK_MONOTONIC, &t_now) == 0) {
        if (! (sh->end_time.tv_sec || sh->end_time.tv_nsec)) {
            sh->end_time = t_now;
        }
    }

    // if found, get winner info
    int found = atomic_load_explicit(&sh->found, memory_order_relaxed);
    int winner_idx = atomic_load_explicit(&sh->winner_idx, memory_order_relaxed);
    pid_t winner_pid = sh->winner_pid;
    char winner_candidate[MAX_PASS];
    strncpy(winner_candidate, sh->winner_candidate, MAX_PASS-1);
    winner_candidate[MAX_PASS-1] = '\0';

    // kill remaining children (if any)
    for (int i = 0; i < CHILDREN; ++i) {
        if (pids[i] <= 0) continue;
        if (winner_pid == pids[i]) continue;
        // if child still alive, ask to terminate
        if (kill(pids[i], 0) == 0) {
            kill(pids[i], SIGTERM);
        }
    }

    // reap children
    for (int i = 0; i < CHILDREN; ++i) waitpid(pids[i], NULL, 0);

    // aggregate attempts
    uint64_t total_attempts = 0;
    for (int i = 0; i < CHILDREN; ++i) {
        uint64_t a = atomic_load_explicit(&sh->attempts[i], memory_order_relaxed);
        total_attempts += a;
    }

    struct timespec diff;
    timespec_diff(&sh->start_time, &sh->end_time, &diff);
    double elapsed = (double)diff.tv_sec + (double)diff.tv_nsec / 1e9;

    printf("\n--- Resultado ---\n");
    if (found) {
        printf("Senha encontrada pelo processo %d (child index %d): '%s'\n", winner_pid, winner_idx, winner_candidate);
    } else {
        printf("Senha NAO encontrada (testadas todas combinacoes de tamanho %zu em cada modo).\n", fixed_len);
    }

    for (int i = 0; i < CHILDREN; ++i) {
        uint64_t a = atomic_load_explicit(&sh->attempts[i], memory_order_relaxed);
        printf("Modo %d (child %d) tentativas: %llu\n", i+1, pids[i], (unsigned long long)a);
    }

    printf("Total de tentativas (soma dos 3 modos): %llu\n", (unsigned long long) total_attempts);
    printf("Tempo decorrido: %.6f s\n", elapsed);
    if (elapsed > 0.0) {
        printf("Tentativas por segundo (soma): %.2f\n", total_attempts / elapsed);
    }

    // cleanup
    munmap(sh, sizeof(shared_t));
    return 0;
}