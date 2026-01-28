package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;
import main.Util;

// CLASSE DOS INIMIGOS

public class NPC_Jet extends Entity {
	
	public NPC_Jet(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		name = "Jet1";
		onPath = true;
		createJetSprites();
	}
	
	// CRIAR SPRITES
	
	private void createJetSprites(){
   
			   Util u = new Util();
			   
			   up1 = drawJetSprite1();
			   up2 = drawJetSprite2();
			   down1 = u.rotateBufferedImage(up1, 180);
			   down2 = u.rotateBufferedImage(up2, 180);
			   right1 = u.rotateBufferedImage(up1, 90);
			   right2 = u.rotateBufferedImage(up2, 90);
			   left1 = u.rotateBufferedImage(up1, -90);
			   left2 = u.rotateBufferedImage(up2, -90);
			   
		   
	}
	
	// SETAR A AÇÃO
	
	public void setAction() {
		
		if(onPath == true) {
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
			searchPath(goalCol, goalRow);
		}
		else {
			
			// SE O ONPATH ESTIVER DESLIGADO, O COMPORTAMENTO É COM BASE NA SORTE
			
			actionLockCounter ++ ;
			
			if(actionLockCounter == 60) {
				Random random = new Random();
				
				int i = random.nextInt(100) + 1;
				
				if(i <= 25) {
					direction = "up";
				}
				if(i >  25 && i  <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}	
				
				actionLockCounter = 0;
			}
		}
		
	}
	
	// DESENHANDO OS SPRITES

	public BufferedImage drawJetSprite1(){
		
			
		    BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2 = sprite.createGraphics();

		
	       	g2.setColor(Color.white);
	        
	       	int x = gp.tileSize/2;
	       	int y = gp.tileSize/2;
	       	
	        GeneralPath rocket = new GeneralPath();
	        
	        rocket.moveTo(x-5, y);
	        rocket.lineTo(x-5, y-12);
	        rocket.lineTo(x, y-18);
	        rocket.lineTo(x+5, y-12);
	        rocket.lineTo(x+5, y);
	        
	        
	        rocket.lineTo(x+16, y+12);
	        rocket.lineTo(x+5, y+12);
	        

	        rocket.lineTo(x+13, y+18);
	        rocket.lineTo(x-13,y+18);
	        rocket.lineTo(x-5, y+12);
	        
	        rocket.lineTo(x-16, y+12);
	        rocket.lineTo(x-5, y);
	        
	        rocket.closePath();
	        

	        g2.draw(rocket);
	        g2.setColor(Color.blue);
	        g2.fill(rocket);
	        
	        g2.dispose();
	        
	        return sprite;
	        
	   }
	
	public BufferedImage drawJetSprite2(){
		
	    BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = sprite.createGraphics();

       	g2.setColor(Color.white);
        
       	int x = gp.tileSize/2;
       	int y = gp.tileSize/2;
       	
        GeneralPath rocket = new GeneralPath();
        
        rocket.moveTo(x-5, y);
        rocket.lineTo(x-5, y-12);
        rocket.lineTo(x, y-18);

        rocket.lineTo(x+5, y-12);
        rocket.lineTo(x+5, y);
     
        rocket.lineTo(x+16, y+12);
        rocket.lineTo(x+5, y+12);
        
        rocket.lineTo(x+13, y+18);
        rocket.lineTo(x-13,y+18);
        rocket.lineTo(x-5, y+12);
        
        rocket.lineTo(x-16, y+12);
        rocket.lineTo(x-5, y);
        
        rocket.closePath();

        g2.draw(rocket);
        g2.setColor(Color.blue);
        g2.fill(rocket);
        
        g2.dispose();
        
        return sprite;
        
   }
}
