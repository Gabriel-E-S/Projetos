package main;

import java.util.Random;

// GERA O LABIRINTO ALEATÓRIO

public class MazeGenerator {
	
	// ATRIBUTOS
	
    private final int WIDTH;   
    private final int HEIGHT;  
    private final int[][] map; 
    private final boolean[][] visited; 
    private final Random rand = new Random();

    
    
    
    private static final int WALL = 0;
    private static final int PATH = 1;
    private static final int ACID = 2;   // opcional
    private static final int REPAIRKIT = 3;   // opcional
    private static final int DOOR = 4;  // opcional
    private static final int EXIT = 5;
    private static final int GALOON = 6;
    private static final int ENEMY1 = 7; 

    /**
     * @param cols número de colunas (ex: gp.maxWorldCol)
     * @param rows número de linhas  (ex: gp.maxWorldRow)
     * @param map  referência ao array mapTileNum (map[col][row]) que será preenchido
     */
    
    // CONSTRUTOR
    
    public MazeGenerator(int cols, int rows, int[][] map) {
        this.WIDTH = cols;
        this.HEIGHT = rows;
        this.map = map;
        System.out.println("w = " + WIDTH + "h = " + HEIGHT);

        // validação simples
        if (WIDTH < 5 || HEIGHT < 5 || WIDTH % 2 == 0 || HEIGHT % 2 == 0) {
            throw new IllegalArgumentException("MazeGenerator requires odd dimensions >=5 (ex: 50x50 funciona).");
        }

        // número de células na malha (interior): (WIDTH-1)/2 x (HEIGHT-1)/2
        int cellsX = (WIDTH - 1) / 2;
        int cellsY = (HEIGHT - 1) / 2;
        visited = new boolean[cellsX][cellsY];
    }

    /** Gera o labirinto e preenche o array `map`. */
    public void generate() {
        // Inicializa tudo como parede (0)
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                map[x][y] = WALL;
            }
        }

        // Escava as células: cada célula ocupa coordenada (2*cx+1, 2*cy+1)
        // Começa na célula (0,0) da malha -> posição (1,1) no mapa real
        int startCx = 0, startCy = 0;
        map[1][1] = PATH;
        visited[startCx][startCy] = true;
        carve(startCx, startCy);

        // Opcional: transformar alguns pisos em 2/3/4 (mantendo-os passáveis)
        sprinkleVariants(0.03, 0.30, 0.03,0.02,0.01); // probabilidades: door, acid, black

        // Coloca a saída (5) em uma borda (não canto), escolhida aleatoriamente
        placeExit();
        
        // GARANTINDO O SPAWN
        
        int r = rand.nextInt(15) + 8;
        
        addOpenSpaces(r);
        
        for(int i = 1; i<4; i++) {
        	for(int j = 1; j<4;j++) {
        		map[i][j] = PATH;
        	}
        }
        map[2][1] = REPAIRKIT;
        
        
    }
    
    
    private void addOpenSpaces(int count) {
        for (int i = 0; i < count; i++) {
            int x, y;
            boolean valid = false;

            // tenta até achar uma posição válida
            while (!valid) {
                // evita bordas: começa em 1 e termina em WIDTH-2
                x = 1 + rand.nextInt(WIDTH - 2 - 1);  // [1 .. WIDTH-2]
                y = 1 + rand.nextInt(HEIGHT - 2 - 1); // [1 .. HEIGHT-2]

                // o centro do 3x3 precisa estar a pelo menos 1 tile das bordas
                if (x > 1 && x < WIDTH - 2 && y > 1 && y < HEIGHT - 2) {
                    // abre espaço 3x3 centrado em (x,y)
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                        	if(map[x + dx][y + dy] == WALL || map[x + dx][y + dy] == DOOR) {
                        		map[x + dx][y + dy] = PATH;                        		
                        	}
                        }
                    }
                    valid = true;
                }
            }
        }
    }

    // DFS recursivo sobre a malha de células
    private void carve(int cx, int cy) {
        int[] dirs = new int[]{0,1,2,3}; // N,S,E,W
        shuffle(dirs);

        for (int d : dirs) {
            int nx = cx, ny = cy;
            int dx = 0, dy = 0;

            switch (d) {
                case 0: ny = cy - 1; dy = -1; break; // Norte
                case 1: ny = cy + 1; dy = 1;  break; // Sul
                case 2: nx = cx + 1; dx = 1;  break; // Leste
                case 3: nx = cx - 1; dx = -1; break; // Oeste
            }

            // limites da malha de células
            if (nx < 0 || ny < 0 || nx >= visited.length || ny >= visited[0].length) continue;

            if (!visited[nx][ny]) {
                // Remove a parede entre as células no mapa real:
                // paredeX = 2*cx+1 + dx, paredeY = 2*cy+1 + dy
                int wallX = 2*cx + 1 + dx;
                int wallY = 2*cy + 1 + dy;
                int cellX = 2*nx + 1;
                int cellY = 2*ny + 1;

                map[wallX][wallY] = PATH;
                map[cellX][cellY] = PATH;

                visited[nx][ny] = true;
                carve(nx, ny);
            }
        }
    }

    // Embaralha array (Fisher-Yates)
    private void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        }
    }

    // Substitui aleatoriamente alguns PATH por variantes (2,3,4) mas não por WALL
    private void sprinkleVariants(double probDoor, double probAcid, double probBlack, double probGaloon, double probEnemy1) {
        for (int cx = 0; cx < (WIDTH-1)/2; cx++) {
            for (int cy = 0; cy < (HEIGHT-1)/2; cy++) {
                int mapX = 2*cx + 1;
                int mapY = 2*cy + 1;
                if (map[mapX][mapY] != PATH) continue;
                double r = rand.nextDouble();
                if (r < probDoor) map[mapX][mapY] = ACID;
                else if (r < probDoor + probAcid) map[mapX][mapY] = REPAIRKIT;
                else if (r < probDoor + probAcid + probBlack) map[mapX][mapY] = DOOR;
                else if (r < probDoor + probAcid + probBlack + probGaloon) map[mapX][mapY] = GALOON;
                else if (r < probDoor + probAcid + probBlack + probGaloon + probEnemy1) map[mapX][mapY] = ENEMY1;
            }
        }
    }

    // Coloca a saída (5) em uma borda, evitando cantos.
    private void placeExit() {
        // Escolhe aleatoriamente uma das quatro bordas e uma posição ímpar
        int side = rand.nextInt(4); // 0=topo,1=base,2=esquerda,3=direita
        int ex = -1, ey = -1;

        switch (side) {
            case 0: // topo (y=0), x ímpar entre 1..WIDTH-2
                ex = randomOdd(1, WIDTH-2);
                ey = 0;
                break;
            case 1: // base (y=HEIGHT-1)
                ex = randomOdd(1, WIDTH-2);
                ey = HEIGHT-1;
                break;
            case 2: // esquerda (x=0)
                ex = 0;
                ey = randomOdd(1, HEIGHT-2);
                break;
            case 3: // direita (x=WIDTH-1)
                ex = WIDTH-1;
                ey = randomOdd(1, HEIGHT-2);
                break;
        }

        // Coloca a saída; garante que exista passagem adjacente (se não, abre)
        map[ex][ey] = EXIT;

        // abre a célula interna adjacente (se por acaso estava parede)
        int adjX = ex, adjY = ey;
        if (ex == 0)            adjX = 1;
        else if (ex == WIDTH-1) adjX = WIDTH-2;
        else if (ey == 0)       adjY = 1;
        else if (ey == HEIGHT-1)adjY = HEIGHT-2;

        if (map[adjX][adjY] == WALL) map[adjX][adjY] = PATH;
    }

    // retorna um número ímpar entre min e max inclusive (assume min/max apropriados)
    private int randomOdd(int min, int max) {
        int span = (max - min) / 2 + 1;
        int r = rand.nextInt(span);
        return min + 2*r;
    }

    /** Caso queira recuperar o mapa (não necessário se o array foi passado por referência) */
    public int[][] getMap() {
        return map;
    }
}
