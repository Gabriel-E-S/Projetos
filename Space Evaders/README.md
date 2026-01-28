# 🛸 Space Evaders

**Space Evaders** é um jogo 2D de sobrevivência e labirinto desenvolvido puramente em **Java**, sem o uso de engines gráficas prontas.

O projeto explora conceitos fundamentais de computação gráfica, como transformações geométricas, renderização de primitivas e geração procedural de mapas.

## 📖 Sobre o Jogo

**Objetivo:** Coletar as peças de reparo necessárias e encontrar a saída antes que o tempo (5 minutos) acabe ou suas vidas se esgotem.

## ✨ Funcionalidades Técnicas

Este projeto foi desenvolvido para demonstrar a aplicação prática de bibliotecas e funções do **Java 2D**:

* **Geração Procedural:** O labirinto é gerado aleatoriamente a cada nova partida, garantindo que nenhum jogo seja igual ao outro.
* **Engine Gráfica Própria:**
    * **Renderização Vetorial:** Os tiles do cenário (paredes, chão) são desenhados via código utilizando linhas, curvas e elipses (`GeneralPath`), armazenados em `BufferedImage` para otimização.
    * **Pixel Art:** Interface e Player utilizam sprites pixelados para uma estética retrô.
* **Inteligência Artificial:** Inimigos com algoritmos de perseguição e busca de caminho.
* **Matemática Vetorial:** Implementação manual de transformações de **translação**, **escala** e **rotação** na movimentação e animação dos sprites.
* **Sistema de Áudio:** Efeitos sonoros integrados, criados por mim.

## 🎮 Controles

| Tecla | Ação |
| :---: | :--- |
| **W, A, S, D** | Movimentação da Nave |
| **P** | Pausar Jogo |
| **W / S** | Navegação nos Menus |
| **ENTER** | Confirmar seleção |


## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 17+)
* **IDE de Desenvolvimento:** Eclipse

## 🚀 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone "https://github.com/Gabriel-E-S/Projetos/tree/main/Space%20Evaders/My2DGame"
    ```
2.  **Importe o projeto:**
    * Abra o **Eclipse IDE** (ou sua IDE de preferência).
    * Importe o projeto como *Existing Java Project*.
3.  **Execute:**
    * Localize a classe principal **My2DGame.java**.
    * Execute como *Java Application*.

## 👨‍💻 Autor

**Gabriel do Espírito Santo**
* **Curso:** Engenharia de Computação
* **Instituição:** Universidade Estadual de Ponta Grossa (UEPG)

---
*Desenvolvido em 2025.*
