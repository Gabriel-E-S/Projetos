import java.util.Scanner;

public class Principal{

   public static void main(String Args[]){
   
      Tabuleiro tab = new Tabuleiro();
      int linha, coluna, resultado = 0;
      
      Jogador p1 = new Jogador("Jogador 1",'X');
      Jogador p2 = new Jogador("Jogador 2",'O');
      Jogador jogadorAtual;

      Scanner ler = new Scanner(System.in) ;
            
      tab.inicializar();
      jogadorAtual = p2;

      while(resultado == 0){

         tab.imprimir();
         jogadorAtual = tab.trocaJogador(jogadorAtual, p1, p2);
         
         System.out.println("\n\nJogador : " + jogadorAtual.getNome() + "\nsimbolo = " + jogadorAtual.getSimbolo());

         System.out.println("\ndigite a linha: ");
         linha = ler.nextInt();
         System.out.println("digite a coluna: ");
         coluna = ler.nextInt();

         tab.fazerJogada(jogadorAtual, linha, coluna);
         resultado = tab.verificarSituacao(jogadorAtual);
         

      }
      if(resultado == 2){
         System.out.println("Empate!");
      }
      else{
         tab.imprimir();
         System.out.println("\n\nVencedor = " + jogadorAtual.getNome()  + "\nsimbolo = " + jogadorAtual.getSimbolo());
      }
   
   
      ler.close();
   }


}