package ai;


// CLASSE FEITA PARA PROGRAMAR A IA QUE ACHA O CAMINHO MAIS CURTO ATÉ O OBJETIVO

public class Node {

	// ATRIBUTOS
	
	Node parent;
	public int col;
	public int row;
	int gCost;
	int hCost;
	int fCost;
	boolean solid;
	boolean open;
	boolean checked;
	
	// CONSTRUTOR
	
	public Node(int col,int row) {
		this.col = col;
		this.row = row;
	}
	

}
