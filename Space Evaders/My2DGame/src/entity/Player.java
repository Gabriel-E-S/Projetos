/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 *
 * @author Espírito Santo
 */


// CLASSE DO PLAYER

public class Player extends Entity {
    
	// ATRIBUTOS
	
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int hasRepairKits = 0;
    public int lifeNum = 3;
    
    // CONSTRUTOR
    
    public Player(GamePanel gp, KeyHandler keyH){
    	
    	super(gp);
    	
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        
        solidArea.x = 5;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 12 ;
        solidArea.height = gp.tileSize - 12;
        
        up1 = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        up2 = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        
        createPlayerSprites();
        
        setDefaultValues();
    }
    
    // COLOCANDO OS VALORES DEFAULT
    
    public void setDefaultValues(){
        worldX = 1*gp.tileSize;
        worldY = 1*gp.tileSize;
        speed = 3;
        direction = "up" ;
    }
    
    // MÉTODO UPDATE
    
    public void update(){
    	
    	if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
    		
    		if(keyH.upPressed == true){
                direction = "up";
            }else if(keyH.downPressed == true){
                direction = "down";
            }else if(keyH.leftPressed == true){

                direction = "left";
            }else if(keyH.rightPressed == true){
                direction = "right";
            } 
    		
    		// COLISÃO TILES
    		collisionOn = false;
    		gp.cChecker.checkTile(this);
    		
    		// COLISÃO OBJETOS
    		int objIndex = gp.cChecker.checkObject(this, true);
    		pickUpObject(objIndex);
    		
    		// COLISÃO NPCS
    		
    		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
    		interactNPC(npcIndex);
    		
    		// SE A COLISÃO N EXISTIR, PODE SE MOVER
    		
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

    		// TEMPO DE INVENCIBILIDADE
    		
    		if(damageOn == true) {
    			damageDelay++;

        		if(damageDelay > 120){
        			damageOn = false;
        			damageDelay = 0;
        		}
    		}
    		
    		// TROCA DE SPRITES
    		           
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
    	else {
    		spriteNum =1 ; // SE TIVER PARADO 
    	}
    }
    
    // PEGAR OBJETOS
    
    public void pickUpObject(int index) {
    	if(index  != 999) {
    		String objectName = gp.obj[index].name;
	
    		switch(objectName) {
    			case "RepairKit":
    				hasRepairKits++;
    				gp.playSoundEffects(1);
    				gp.obj[index] = null;
    				gp.ui.showMessage("Você pegou uma peça!");
    				break;
    			case "Door":
    				if(hasRepairKits > 0) {
    					gp.playSoundEffects(3); 
    					gp.obj[index] = null;
    					hasRepairKits --;
    					gp.ui.showMessage("Você Abriu a porta!");
    				}
    				else {
    					gp.playSoundEffects(4);
    					gp.ui.showMessage("Ainda faltam peças pra abrir!");
    				}
    				break;
    			case "GasGaloon":
    				if(speed <7) {
    					gp.playSoundEffects(2);
        				speed += 1;
        				gp.obj[index] = null;
        				gp.ui.showMessage("Você ganhou velocidade!");
    				}
    				else {
    					gp.ui.showMessage("Você está na velocidade máxima!");
    					gp.obj[index] = null;
    				}   				
    				break;
    			case "AcidPit":
	    				if(damageOn   == false) {
	    					gp.playSoundEffects(7);
							gp.ui.showMessage("Você tomou dano do ácido!");
						}
    					takeDamage();				
    				break;
    			case "ExitDoor":
    				int counter = gp.aSetter.repairKitCounter - gp.aSetter.doorCounter - gp.aSetter.acidPitCounter-50;
    				if(counter<=0) {
    					counter = 30;
    				}
    				
    				if(hasRepairKits >= (counter)) {
    					gp.gameState = gp.winState;
        				gp.stopMusic();
        				gp.playSoundEffects(5);
    				}
    				else {
    					gp.ui.showMessage("Você deve pegar mais " + ((gp.aSetter.repairKitCounter - gp.aSetter.doorCounter - gp.aSetter.acidPitCounter-15)- hasRepairKits) + "kits de reparo!" );
    				}
    				break;	
    		} 		
    	}
    }
    
    // INTERAGIR NPC
    
    public void interactNPC(int index) {
    	if(index  != 999) {
    		
    		String npcName = gp.npc[index].name;
    		
    		switch(npcName) {
    			case "Jet1":
    				takeKillDamage();
    				break;
 		
    		}    		
    	}
    }
    
    // TOMAR DANO NORMAL
    
    public void takeDamage() {
    	
    	if(damageOn == false) {
    		lifeNum--;
        	if(lifeNum <= 0 ) {
        		if(hasRepairKits > 0) {
        			gp.ui.showMessage("Você usou kit de reparo  para  se  salvar!");
        			lifeNum++;
        			hasRepairKits--;
        		}
        		else {
        			gp.gameState = gp.failState;
        			gp.stopMusic();
    				gp.playSoundEffects(6);
        		}
        	}
        	damageOn = true;
    	}

    }
    
    // MATAR O PLAYER
    
    public void takeKillDamage() {
    	
    	if(damageOn == false) {
    		lifeNum-= 50;
        	if(lifeNum <= 0 ) {
        		
        			gp.gameState = gp.failState;
        			gp.stopMusic();
    				gp.playSoundEffects(6);
        		}
        	}
        	damageOn = true;
    }

   // DESENHAR
    
   public void draw(Graphics2D g2){ // A LÓGICA DE TRANSFORMAÇÃO É A MESMA DA ENTIDADE
        AffineTransform oldTransform = g2.getTransform();

        g2.translate(screenX, screenY); 

        g2.setColor(Color.white);
        
        BufferedImage spriteSelector = up1;
        
        switch(direction) {
        	case "up":
        		if(spriteNum == 1) {
        			spriteSelector = up1;
        		}
        		if(spriteNum == 2) {
        			spriteSelector = up2;
        		}
        		break;
        	case "down":
        		if(spriteNum == 1) {
        			spriteSelector = down1;
        		}
        		if(spriteNum == 2) {
        			spriteSelector = down2;
        		}
        		break;
        	case "left":
        		if(spriteNum == 1) {
        			spriteSelector = left1;
        		}
        		if(spriteNum == 2) {
        			spriteSelector = left2;
        		}
        		break;
        	case "right":
        		if(spriteNum == 1) {
        			spriteSelector = right1;
        		}
        		if(spriteNum == 2) {
        			spriteSelector = right2;
        		}
        		break;
        }

        g2.drawImage(spriteSelector, 0,0, gp.tileSize,gp.tileSize,null);

        g2.setTransform(oldTransform);         
   }
   
   // CRIAR SPRITES

   private void createPlayerSprites(){
	   try {
		   
		   Util u = new Util();
		   
		   up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up1.png"));
		   up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up2.png"));
		   down1 = u.rotateImage("/player/player_up1.png", 180);
		   down2 = u.rotateImage("/player/player_up2.png", 180);
		   right1 = u.rotateImage("/player/player_up1.png", 90);
		   right2 = u.rotateImage("/player/player_up2.png", 90);
		   left1 = u.rotateImage("/player/player_up1.png", -90);
		   left2 = u.rotateImage("/player/player_up2.png", -90);
		   
	   }catch(IOException e) {
		   e.printStackTrace();
	   }
   }  
}

