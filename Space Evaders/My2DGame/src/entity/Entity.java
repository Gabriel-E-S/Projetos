	/*
	 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
	 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
	 */
	package entity;
	
	import java.awt.Graphics2D;
	import java.awt.Rectangle;
	import java.awt.geom.AffineTransform;
	import java.awt.image.BufferedImage;
	
	import main.GamePanel;
	import main.Util;
	
	/**
	 *
	 * @author Espírito Santo
	 */
	
	// CLASSE ABSTRATA QUE REPRESENTA AS ENTIDADES 
	
	public abstract class Entity {
	    
		// ATRIBUTOS
		
	    GamePanel gp;
	    public String name;
	    public String direction;
	    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	    public int worldX,worldY;
	    public int speed;
	    public int spriteCounter = 0;
	    public int spriteNum = 1 ;
	    public int damageDelay = 0;
	    public int solidAreaDefaultX, solidAreaDefaultY;
	    public int actionLockCounter = 0;
	    public Rectangle solidArea = new Rectangle(5,5,40,40) ;
	    public boolean collisionOn = false;
	    public boolean damageOn = false;
	    public boolean onPath = false;
	    
	    
	    // CONSTRUTOR
	    
	    public Entity(GamePanel gp) {
	    	this.gp = gp;
	    }
	    
	    // VERIFICAR COLISÕES
	    
	    public void checkCollision() {
	    	gp.cChecker.checkTile(this);
	    	gp.cChecker.checkObject(this, false);
	    	gp.cChecker.checkEntity(this, gp.npc); 
	    	int res = gp.cChecker.checkPlayer(this);
	    	
	    	// SE O INIMIGO ENCOSTA NO PLAYER
	    	if(collisionOn == true && name == "Jet1" && res == 1) {
	    		gp.player.takeKillDamage();
	    	}
	    }
	    
	    // DAR AÇÃO PRO NPC
	    public void setAction() {};
	    
	    // ATUALIZAR ENTIDADE
	    
	    public void update() {
	    	setAction();
	
	    	collisionOn = false;
	    	
	    	checkCollision();
	    	
	    	
	    	// SE A COLISÃO FOR FALSA, PODE SE MOVER
			
			if(collisionOn == false) {
				switch(direction) {
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;	
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;					
						
				}
			}
			
			// CONTADOR QUE ATUALIZA OS SPRITES
			
			spriteCounter++;
	        if(spriteCounter > 10) {
	        	if(spriteNum == 1) {
	        		spriteNum = 2;
	        	}
	        	else if (spriteNum == 2) {
	        		spriteNum = 1;
	        	}
	        	spriteCounter = 0;
	        }
	    }
	    
	    // DESENHA A ENTIDADE
	    
	    public void draw(Graphics2D g2, double scale1X, double scale1Y, double scale2X, double scale2Y) {
	        
	        int screenX = worldX - gp.player.worldX + gp.player.screenX;
	        int screenY = worldY - gp.player.worldY + gp.player.screenY;
	        
	        BufferedImage spriteSelector = up1;
	        Util u = new Util();
	        
	        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
	           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
	           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
	           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
	            
	            switch(direction) {
	                case "up":
	                    spriteSelector = (spriteNum == 1) ? 
	                        u.scaleBufferedImage(up1, scale1X, scale1Y) :
	                        u.scaleBufferedImage(up2, scale2X, scale2Y);
	                    break;
	                case "down":
	                    spriteSelector = (spriteNum == 1) ? 
	                        u.scaleBufferedImage(down1, scale1X, scale1Y) :
	                        u.scaleBufferedImage(down2, scale2X, scale2Y);
	                    break;
	                case "left":
	                    spriteSelector = (spriteNum == 1) ? 
	                        u.scaleBufferedImage(left1, scale1X, scale1Y) :
	                        u.scaleBufferedImage(left2, scale2X, scale2Y);
	                    break;
	                case "right":
	                    spriteSelector = (spriteNum == 1) ? 
	                        u.scaleBufferedImage(right1, scale1X, scale1Y) :
	                        u.scaleBufferedImage(right2, scale2X, scale2Y);
	                    break;
	            }

	            // SALVA ESTADO ORIGINAL
	            AffineTransform oldTransform = g2.getTransform();

	            // MOVE O SISTEMA DE COORDENADAS
	            g2.translate(screenX, screenY);

	            // DESENHA EM 0,0 PQ JÁ FOI TRANSLADADO
	            g2.drawImage(spriteSelector, 0, 0, gp.tileSize, gp.tileSize, null);

	            // RESTAURA O ESTADO INICIAL
	            g2.setTransform(oldTransform);
	        }
	    }

	    // PROCURAR MELHOR CAMINHO ATÉ O OBJETIVO
	    
	    public void searchPath(int goalCol, int goalRow) {
	    	
	    	int startCol = (worldX + solidArea.x)/gp.tileSize;
	    	int startRow= (worldY + solidArea.y)/gp.tileSize;
	    	
	    	gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
	    	
	    	if(gp.pFinder.search() == true) {
	    		
	    		// NOVO WORLDX E WORLDY
	    		int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
	    		int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
	    		
	    		// POSIÇÃO SOLID AREA DA ENTIDADE
	    		
	    		int enLeftX = worldX + solidArea.x;
	    		int enRightX = worldX + solidArea.x + solidArea.width;
	    		int enTopY = worldY + solidArea.y;
	    		int enBottomY = worldY + solidArea.y + solidArea.height;
	    		
	    		if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize ) {
	    			direction = "up";
	    		}
	    		else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize ) {
	    			direction = "down";
	    		}
	    		else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize ) {
	    			// LEFT OU RIGHT
	    			if(enLeftX > nextX) {
	    				direction = "left";    				
	    			}
	    			if(enLeftX < nextX) {
	    				direction = "right";
	    			}
	    		}
	    		else if(enTopY > nextY && enLeftX > nextX) {
	    			// UP OU LEFT
	    			direction = "up";
	    			checkCollision();
	    			if(collisionOn == true) {
	    				direction = "left";
	    			}
	    		}
	    		else if(enTopY > nextY && enLeftX < nextX) {
	    			// UP OU RIGHT
	    			direction = "up";
	    			checkCollision();
	    			if(collisionOn == true) {
	    				direction = "right";
	    			}
	    		}
	    		else if(enTopY < nextY && enLeftX > nextX) {
	    			// DOWN OU LEFT
	    			direction = "down";
	    			if(collisionOn == true) {
	    				direction = "left";
	    			}
	    		}
	    		else if(enTopY < nextY && enLeftX < nextX) {
	    			// DOWN OU RIGHT
	    			direction = "down";
	    			if(collisionOn == true) {
	    				direction = "right";
	    			}
	    		}
	    		
	//			ESSE BLOCO É SE O NPC PRECISA IR PRA ALGUM LUGAR SOZINHO, MAS N VOU UTILIZAR.
	//    		int nextCol = gp.pFinder.pathList.get(0).col;
	//    		int nextRow = gp.pFinder.pathList.get(0).row;
	//    		
	//    		if(nextCol == goalCol && nextRow == goalRow) {
	//    			onPath = false;
	//    		}
	
	    	}
	    	
	    }
	}
