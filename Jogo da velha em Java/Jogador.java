public class Jogador{

    private String nome ;
    private char simbolo ;
 
    public Jogador(String nome,char simbolo){
       this.nome = nome ;
       this.simbolo = simbolo ;
    }
    
    public char getSimbolo(){
       return simbolo ;   
    }
    public String getNome(){
       return nome ;
    }
 
    public void setNome(String nome) {
       this.nome = nome;
    }
    public void setSimbolo(char simbolo) {
       this.simbolo = simbolo;
    }
 }