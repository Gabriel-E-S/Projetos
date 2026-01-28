package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class OBJ_FinalDoor extends SuperObject {

GamePanel gp;
	
	public OBJ_FinalDoor(GamePanel gp) {
		name = "ExitDoor";
		this.gp = gp;
		
		image = createDoorObject(gp); 
		collision = true;

		}
	private BufferedImage createDoorObject(GamePanel gp){

	    // Cria o sprite 32x32
	    BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = sprite.createGraphics();

	    // Deixando as linhas mais grossas
	    g.setStroke(new BasicStroke(5.0f));

	    // Fundo cinza
	    g.setColor(Color.white);
	    g.fillRect(0, 0, gp.tileSize, gp.tileSize);   
	  

	    
	    
	    g.setColor(Color.gray);
	    
	    g.drawLine(0,0,0, gp.tileSize );
	    g.drawLine(gp.tileSize-1,0,gp.tileSize-1, gp.tileSize );
	    g.drawLine(0, 0, gp.tileSize, 0);
	    g.drawLine(0, gp.tileSize, gp.tileSize, gp.tileSize);
	    
	   
	    
	    // Desenhando o chão
	    g.setColor(Color.black);
	    g.drawLine(gp.tileSize/2,5, gp.tileSize/2, gp.tileSize-5);
	    
	    g.setColor(Color.lightGray);
	    g.fillRect(gp.tileSize/2+5, gp.tileSize/2, 5, 5);
	    g.fillRect(gp.tileSize/2-10, gp.tileSize/2, 5, 5);
	  
	    g.dispose();

	    return sprite;
	}
	
	
	
}
