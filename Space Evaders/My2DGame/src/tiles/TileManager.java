/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiles;

import main.GamePanel;
import main.MazeGenerator;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Espírito Santo
 */


// GERENCIADOR DE TILES

public class TileManager {
	
	// ATRIBUTOS
	
    GamePanel gp;
    public Tile tile[] ;
    public int mapTileNum[][];
    boolean drawPath = false;
    
    // CONSTRUTOR
    
    public TileManager(GamePanel gp){
        this.gp = gp;
        
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        getTileImage();
        loadMap("/maps/map01.txt");
    }
    
    // PEGAR IMAGENS
    
    public void getTileImage(){
        
    	// PAREDE
        tile[0] = new Tile();
        tile[0].image = createWallTile();
        tile[0].colision = true;
        
        // CHÃO
        tile[1] = new Tile();
        tile[1].image = createPathTile();
        
        // ÁCIDO
        tile[2] = new Tile();
        tile[2].image = createAcidTile();
        
        // REPAIR KIT
        tile[3] = new Tile();
        tile[3].image = createFakePathTile();
        
        // PORTA NORMAL
        tile[4] = new Tile();
        tile[4].image = createFakePathTile();
        
        // PORTA FINAL
        tile[5] = new Tile();
        tile[5].image = createWallTile();
        
        // GALÃO DE GASOLINA
        tile[6] = new Tile();
        tile[6].image = createFakePathTile();
        
        // INIMIGO BURRO
        tile[7] = new Tile();
        tile[7].image = createFakePathTile();
        
    }
    
    // MÉTODOS DE DESENHO
    
    private BufferedImage createPathTile(){

        // Cria o sprite 32x32
        BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();

        // Deixando as linhas mais grossas
        g.setStroke(new BasicStroke(2.0f));

        // Fundo cinza
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);   

        // Contorno preto
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, gp.tileSize, gp.tileSize);

        // Linhas verticais
        g.drawLine(gp.tileSize/3, 0, gp.tileSize/3, gp.tileSize);
        g.drawLine(2*gp.tileSize/3, 0, 2*gp.tileSize/3, gp.tileSize);

        // Linhas horizontais
        g.drawLine(0, gp.tileSize/3, gp.tileSize/3, gp.tileSize/3);
        g.drawLine(0, 2*gp.tileSize/3, gp.tileSize/3, 2*gp.tileSize/3);
        g.drawLine(gp.tileSize/3, gp.tileSize/2, 2*gp.tileSize/3, gp.tileSize/2);
        g.drawLine(2*gp.tileSize/3, gp.tileSize/3, gp.tileSize, gp.tileSize/3);
        g.drawLine(2*gp.tileSize/3, 2*gp.tileSize/3, gp.tileSize, 2*gp.tileSize/3);

        g.dispose();

        return sprite;
    }
    private BufferedImage createFakePathTile(){

        // Cria o sprite 32x32
        BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();

        // Deixando as linhas mais grossas
        g.setStroke(new BasicStroke(2.0f));

        // Fundo cinza
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);   

        // Contorno preto
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, gp.tileSize, gp.tileSize);

        // Linhas verticais
        g.drawLine(gp.tileSize/3, 0, gp.tileSize/3, gp.tileSize);
        g.drawLine(2*gp.tileSize/3, 0, 2*gp.tileSize/3, gp.tileSize);

        // Linhas horizontais
        g.drawLine(0, gp.tileSize/3, gp.tileSize/3, gp.tileSize/3);
        g.drawLine(0, 2*gp.tileSize/3, gp.tileSize/3, 2*gp.tileSize/3);
        g.drawLine(gp.tileSize/3, gp.tileSize/2, 2*gp.tileSize/3, gp.tileSize/2);
        g.drawLine(2*gp.tileSize/3, gp.tileSize/3, gp.tileSize, gp.tileSize/3);
        g.drawLine(2*gp.tileSize/3, 2*gp.tileSize/3, gp.tileSize, 2*gp.tileSize/3);

        g.dispose();

        return sprite;
    }
    
    private BufferedImage createWallTile(){

        // Cria o sprite 32x32
        BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();

        // Deixando as linhas mais grossas
        g.setStroke(new BasicStroke(2.0f));

        // Fundo cinza
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);   
      
        // Desenhando o chão
        g.setColor(Color.black);
        
        g.drawRect(0, 0, gp.tileSize, gp.tileSize);
        
        g.drawLine(0, gp.tileSize/2, gp.tileSize, gp.tileSize/2);
        
        g.drawLine(gp.tileSize/3, 0, gp.tileSize/3, gp.tileSize/2);
        
        g.drawLine(2*gp.tileSize/3, gp.tileSize/2, 2*gp.tileSize/3, gp.tileSize);
      
        g.dispose();

        return sprite;
    }
    private BufferedImage createAcidTile(){

        // Cria o sprite 32x32
        BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();

        // Deixando as linhas mais grossas
        g.setStroke(new BasicStroke(2.0f));

        // Fundo verde
        g.setColor(new Color(117,236,39));
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);   
      
        // Desenhando o chão
        g.setColor(new Color(0,128,0));
        
        g.fillOval(gp.tileSize/3, gp.tileSize/3, gp.tileSize/10, gp.tileSize/10);
        g.fillOval(gp.tileSize/4, gp.tileSize/2, gp.tileSize/8, gp.tileSize/8);
        g.fillOval(gp.tileSize/8, gp.tileSize/8, gp.tileSize/3, gp.tileSize/3);
        g.fillOval(3*gp.tileSize/4, gp.tileSize/5, gp.tileSize/6, gp.tileSize/6);
        
        g.setColor(Color.white);
        
        g.drawOval(gp.tileSize/3, gp.tileSize/3, gp.tileSize/10, gp.tileSize/10);
        g.fillOval(3*gp.tileSize/4, 3*gp.tileSize/5, gp.tileSize/10, gp.tileSize/10);
        g.fillOval(gp.tileSize/2, 4*gp.tileSize/5, gp.tileSize/7, gp.tileSize/7);
        
        g.setColor(new Color(61,255,165));
        
        g.fillOval(gp.tileSize/2, gp.tileSize/2, gp.tileSize/7, gp.tileSize/7);
      
        g.dispose();

        return sprite;
    }
    
    @SuppressWarnings("unused")
	private BufferedImage createBlackTile(){

        // Cria o sprite 32x32
        BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();

        // Deixando as linhas mais grossas
        g.setStroke(new BasicStroke(2.0f));

        // Fundo preto
        g.setColor(Color.black);
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);   
      
        g.dispose();

        return sprite;
    }
    
    private BufferedImage createRedTile(){

        // Cria o sprite 32x32
        BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();

        // Deixando as linhas mais grossas
        g.setStroke(new BasicStroke(2.0f));

        // Fundo preto
        g.setColor(Color.red);
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);   
      
        g.dispose();

        return sprite;
    }
    
    public void loadMap(String mapPath) { 
	
    	// SE QUISER CARREGAR O MAPA DE UM TXT
//    	try {
//    		InputStream is = getClass().getResourceAsStream(mapPath);
//			BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
//			
//			int col = 0;
//			int row = 0;
//			
//			while(col<gp.maxWorldCol && row <gp.maxWorldRow) {
//				//System.out.println("col, rol = " + col +"," + row + "\n");
//				String line = br.readLine();
//				while(col < gp.maxWorldCol) {
//					String numbers[] = line.split(" ");
//					int num = Integer.parseInt(numbers[col]);
//					
//					mapTileNum[col][row] = num ;
//					col++;
//					//System.out.println("mapTile[" + col + "]["+ row+ "] = " + mapTileNum[col][row]+ "\n");
//					} 
//				if(col == gp.maxWorldCol) {
//					
//					col = 0;
//					row ++;
//				}
//			}
//		}catch(Exception e){ System.out.println("Erro ao carregar mapa: "+ e); } 
    	MazeGenerator gen = new MazeGenerator(gp.maxWorldCol,gp.maxWorldRow,mapTileNum);
    	gen.generate();

    }
    
    // DEBUG
    
    public void printMap() {
        for (int row = 0; row < gp.maxWorldRow; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < gp.maxWorldCol; col++) {
                sb.append(mapTileNum[col][row]).append(" ");
            }
            System.out.println(sb.toString());
        }
    }

    // DESENHAR OS TILES NA TELA
    
    public void draw(Graphics2D g2){
        
        int worldCol = 0;
        int worldRow = 0;

        
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
        	
        	int tileNum = mapTileNum[worldCol][worldRow];
        
        	int worldX = worldCol * gp.tileSize;
        	int worldY = worldRow * gp.tileSize;
        	int screenX = worldX - gp.player.worldX + gp.player.screenX;
        	int screenY = worldY - gp.player.worldY + gp.player.screenY;
        	
        	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
    		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
    		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
    		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        		g2.drawImage(tile[tileNum].image, screenX,screenY, gp.tileSize,gp.tileSize,null);
        	}
    
            worldCol++;

            if(worldCol == gp.maxWorldCol){
            	worldCol= 0 ;
                
                worldRow++;           
            }
        }
        
        if(drawPath == true) {
        	g2.setColor(new Color(255,0,0,70));
        	
        	for(int i = 0; i < gp.pFinder.pathList.size();i++) {
        		int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
            	int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
            	int screenX = worldX - gp.player.worldX + gp.player.screenX;
            	int screenY = worldY - gp.player.worldY + gp.player.screenY;
            	
            	g2.fillRect(screenX, screenY, gp.tileSize,gp.tileSize);
        	}
        }   
    }
}
