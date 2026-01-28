package main;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

// CLASSE UTILIDADE

public  class Util {

	   // ROTACIONA
	
	   public BufferedImage rotateImage(String imagePath, double angleDegrees) {
	       try {
	           BufferedImage original = ImageIO.read(getClass().getResourceAsStream(imagePath));

	           int w = original.getWidth();
	           int h = original.getHeight();
	           
	           double angle = Math.toRadians(angleDegrees);

	           AffineTransform transform = new AffineTransform();
	           transform.rotate(angle, w / 2.0, h / 2.0);

	          
	           BufferedImage rotated = new BufferedImage(w, h, original.getType());

	           Graphics2D g2d = rotated.createGraphics();
	           g2d.setTransform(transform);
	           g2d.drawImage(original, 0, 0, null);
	           g2d.dispose();

	           return rotated;

	       } catch (Exception e) {
	           e.printStackTrace();
	           return null;
	       }
	   }
	   
	   // ROTACIONA BUFFERED IMAGE
	   
	   public BufferedImage rotateBufferedImage(BufferedImage image, double angleDegrees) {
    
	           BufferedImage original = image;

	           int w = original.getWidth();
	           int h = original.getHeight();
	           
	           double angle = Math.toRadians(angleDegrees);
  
	           AffineTransform transform = new AffineTransform();
	           transform.rotate(angle, w / 2.0, h / 2.0);

	           BufferedImage rotated = new BufferedImage(w, h, original.getType());

	           Graphics2D g2d = rotated.createGraphics();
	           g2d.setTransform(transform);
	           g2d.drawImage(original, 0, 0, null);
	           g2d.dispose();

	           return rotated;
      }
	   
	   // MÉTODOS ESCALA
	   
	   public BufferedImage scaleImage(String imagePath, double scaleX, double scaleY) {
		    try {
		        BufferedImage original = ImageIO.read(getClass().getResourceAsStream(imagePath));

		        int w = original.getWidth();
		        int h = original.getHeight();

		        int newW = (int) Math.round(w * scaleX);
		        int newH = (int) Math.round(h * scaleY);

		        BufferedImage scaled = new BufferedImage(newW, newH, original.getType());

		        AffineTransform transform = new AffineTransform();
		        transform.scale(scaleX, scaleY);

		        Graphics2D g2d = scaled.createGraphics();
		        g2d.drawImage(original, transform, null);
		        g2d.dispose();

		        return scaled;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		    }
		}
	   
	   public BufferedImage scaleBufferedImage(BufferedImage image, double scaleX, double scaleY) {
		    int w = image.getWidth();
		    int h = image.getHeight();

		    BufferedImage scaled = new BufferedImage(w, h, image.getType());

		    double tx = w/2.0 - (w * scaleX) / 2.0;
		    double ty = h/2.0 - (h * scaleY) / 2.0;

		    AffineTransform transform = new AffineTransform();
		    transform.translate(tx, ty);
		    transform.scale(scaleX, scaleY);

		    Graphics2D g2d = scaled.createGraphics();
		    
		    g2d.drawImage(image, transform, null);
		    g2d.dispose();

		    return scaled;
		}  
}
