package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

// CHÃO DE ÁCIDO

public class OBJ_AcidPit extends SuperObject {

	GamePanel gp;
	
	public OBJ_AcidPit(GamePanel gp) {
		this.gp = gp;
		name = "AcidPit";
		
		image = createAcidObject();
	}
	
	private BufferedImage createAcidObject(){

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

}
