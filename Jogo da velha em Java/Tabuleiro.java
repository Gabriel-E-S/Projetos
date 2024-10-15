public class Tabuleiro{

    private char tabuleiroAtual[][] = new char[3][3] ;
 
 
    public void inicializar(){
       int i,j;
       
       for(i=0;i<3;i++){
          for(j=0;j<3;j++){
             tabuleiroAtual[i][j] =  ' ' ;
          }
    }
 }
 
    public void imprimir(){
       int i,j;
       
       System.out.println("\n\n");
 
       for(i=0;i<3;i++){
          for(j=0;j<3;j++){
             System.out.print(tabuleiroAtual[i][j]) ;
             if(j<2){
                System.out.print(" | ") ;
             }
          }
       if(i<2){
          System.out.print("\n---------\n") ;
       }
         
       }
    }
    public int verificarSituacao(Jogador jogador){
       int i,j, cont = 0;
       
       for(i=0 ; i<3 ; i++){
          cont = 0 ;
          for(j=0;j<3;j++){
             if(tabuleiroAtual[i][j] == jogador.getSimbolo()){
                cont +=1 ;
             }
          }
          if(cont == 3){
             return 1;
          }
       }
 
       for(i=0 ; i<3 ; i++){
          cont = 0 ;
          for(j=0;j<3;j++){
             if(tabuleiroAtual[j][i] == jogador.getSimbolo()){
                cont +=1 ;
             }
          }
          if(cont == 3){
             return 1;
          }
       }
 
       cont = 0 ;
       for(j=0;j<3;j++){
          if(tabuleiroAtual[j][j] == jogador.getSimbolo())
             cont += 1;   
       }
       if(cont == 3){
          return 1;
       }
 
       cont = 0 ;
       for(j=0;j<3;j++){
          if(tabuleiroAtual[j][2-j] == jogador.getSimbolo())
             cont += 1;   
       }
       if(cont == 3){
          return 1;
       }
       
       cont = 0;
 
       for(i=0;i<3;i++){
          for(j=0;j<3;j++){
             if(tabuleiroAtual[i][j] == ' '){
                return  0;
             }
             cont ++ ;
          }
       }
       if(cont == 9){
          return 2;
       }
       return 0;
    }
 
 
 /*
  * 
  *          switch(numero){
             case 1: tabuleiroAtual[0][0] = jogador.getSimbolo() ; break;
             case 2: tabuleiroAtual[0][1] = jogador.getSimbolo() ; break;
             case 3: tabuleiroAtual[0][2] = jogador.getSimbolo() ; break;
             case 4: tabuleiroAtual[1][0] = jogador.getSimbolo() ; break;
             case 5: tabuleiroAtual[1][1] = jogador.getSimbolo() ; break;
             case 6: tabuleiroAtual[1][2] = jogador.getSimbolo() ; break;
             case 7: tabuleiroAtual[2][0] = jogador.getSimbolo() ; break;
             case 8: tabuleiroAtual[2][1] = jogador.getSimbolo() ; break;
             case 9: tabuleiroAtual[2][2] = jogador.getSimbolo() ; break;         }
  * 
  * 
  */
 
    
 
    public void fazerJogada(Jogador jogador,int linha, int coluna){
       if(tabuleiroAtual[linha][coluna] == ' ' ){
          tabuleiroAtual[linha][coluna] = jogador.getSimbolo();
       }
       else{
          System.out.println("Jogada inválida!");
       }
    }
 
    public Jogador trocaJogador(Jogador atual,Jogador p1, Jogador p2){
 
       if(atual == p1){
          return p2 ;
       }
       else{
          return p1 ;
       }
    }
 
 }
 