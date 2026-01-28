package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import main.GamePanel;

// GALÃO DE GASOLINA

public class OBJ_GasGaloon extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_GasGaloon(GamePanel gp) {
		this.gp = gp;
		name = "GasGaloon";
		
		image = createGasGaloonObject(gp);
	}

	private BufferedImage createGasGaloonObject(GamePanel gp){

	    // Cria o sprite 32x32
	    BufferedImage sprite = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = sprite.createGraphics();

	    // Deixando as linhas mais grossas
	    g.setStroke(new BasicStroke(3.0f));
	    
	    GeneralPath gep = new GeneralPath();
	    
	    gep.moveTo(5,10);
	    
	    gep.lineTo(5,gp.tileSize-10);
	    gep.lineTo(9,gp.tileSize-5);
	    gep.lineTo(gp.tileSize-3,gp.tileSize-5);
	    gep.lineTo(gp.tileSize-3,17);
	    gep.lineTo(gp.tileSize-10,5);
	    gep.lineTo(9,5);
	    gep.closePath();
	    
	    GeneralPath gep2 = new GeneralPath();
	    
	    Ellipse2D elipse = new Ellipse2D.Double(10,14,15,7);
	    
	    gep2.append(elipse,false);
	    
	    g.setColor(Color.red);
	    g.fill(gep);

	    // Fundo cinza
	    g.setColor(Color.black);
	       
	    g.draw(gep);
	    g.fill(gep2);

	    g.dispose();

	    return sprite;
	}
}
