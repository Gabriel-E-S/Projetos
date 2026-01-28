package ai;

import java.util.ArrayList;

import main.GamePanel;

// CLASSE QUE ACHA O CAMINHO MAIS CURTO

public class PathFinder {

	// ATRIBUTOS
	
	GamePanel gp;
	Node [][] node;
	
	ArrayList <Node> openList = new ArrayList<>();
	public ArrayList <Node> pathList = new ArrayList<Node>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	
	// CONSTRUTOR
	
	public PathFinder(GamePanel gp){
		this.gp = gp;
		instantiateNodes();
	}
	
	
	// INSTANCIA OS NÓS
	
	public void instantiateNodes() {
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		
		while ((col < gp.maxWorldCol && row < gp.maxWorldRow)) {
			node[col][row] = new Node(col,row);
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
	}
	
	// RESETA TODAS AS CONFIGURAÇÕES DO ALGORITMO
	
	public void resetNodes() {
		int col = 0;
		int row = 0;
		
		
		while ((col < gp.maxWorldCol && row < gp.maxWorldRow)) {
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}	
		}
		
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
		
	}
	
	// SETA OS NÓS
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		
		resetNodes();
		
		// DEFINIR O COMEÇO E O NÓ ALVO
		
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		
		while ((col < gp.maxWorldCol && row < gp.maxWorldRow)) {
			// SET SOLID NODE
			
			
			// VERIFICAR TILES
			int tileNum = gp.tileM.mapTileNum[col][row];
			if(gp.tileM.tile[tileNum].colision == true) {
				node[col][row].solid = true;
			}
			
			// VER TILES INTERATIVOS
			for(int i = 0; i< gp.obj.length;i++) {
				if(gp.obj[i] != null && gp.obj[i].name == "Door") {
					int itCol = gp.obj[i].worldX/gp.tileSize;
					int itRow = gp.obj[i].worldY/gp.tileSize;
					node[itCol][itRow].solid = true;
					
				}
				
			}

			// SET COAST
			getCost(node[col][row]);
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		
		}
		
	}
	
	// PEGA O CUSTO DE CADA DECISÃO
	
	public void getCost(Node node) {
		
		// G cost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		// H cost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		// F cost
		node.fCost = node.gCost + node.hCost;
		
	}
	
	// PROCURAR O CAMINHO
	
	public boolean search() {
		while(goalReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			// VER O NÓ ATUAL
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			// ABRIR O NÓ ACIMA
			
			if(row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			
			// NÓ DA ESQUERDA
			
			if(col -1 >= 0) {
				openNode(node[col-1][row]);
			}
			
			// NÓ DE BAIXO
			
			if(row + 1 <gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			
			// NÓ DIREITA
			
			if(col + 1 <gp.maxWorldCol) {
				openNode(node[col+1][row]);
			}
			
			// ACHAR O MELHOR NÓ
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			
			for(int i = 0; i < openList.size(); i++) {
				
				// VERIFICAR SE O F DESSE NÓ É MELHOR
				
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				
				// SE O F COST FOR IGUAL, CHEGAR O G COST
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
				
			}
			
			// SE NÃO HOUVER ELEMENTOS, TERMINA O LOOP
			
			if(openList.size() == 0) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;

		}
		return goalReached;
	}
	
	// RASTREAR O CAMINHO
	
	public void trackThePath() {
		Node current = goalNode;
		
		while(current != startNode) {
			
			pathList.add(0,current);
			current = current.parent;
		}
	}
	
	// ABRIR NÓ
	
	public void openNode(Node node) {
		
		if(node.open == false && node.checked == false && node.solid == false) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	
}
