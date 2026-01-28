package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import object.OBJ_RepairKit;


// CLASSE DA UI

public class UI {
	
	// ATRIBUTOS
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_20, arial_80B;
	public boolean messageOn = false;
	public String message = "";
	
	int messageCounter = 0;
	public int commandNum = 0;
	public int command2Num = 0;
	
	BufferedImage repairKitImage;
	BufferedImage heartImage;
	BufferedImage clockImage;
	
	double playTime = 0;
	public double  maxTime = (double)60*5;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	// CONSTRUTOR
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		
		arial_20 = new Font("Arial",Font.PLAIN,20);
		arial_80B = new Font("Arial",Font.BOLD,80);
		OBJ_RepairKit  repairKit= new OBJ_RepairKit();
		repairKitImage = repairKit.image;
		
		try {
			heartImage = ImageIO.read(getClass().getResourceAsStream("/ui/heart.png"));	
			clockImage = ImageIO.read(getClass().getResourceAsStream("/ui/clock.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	// MOSTRAR AS MENSAGENS CURTAS
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	// DRAW
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_20);
		g2.setColor(Color.white);
		
		
		// TELA INICIO
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// DERROTA
		
		if(gp.gameState == gp.failState) {
			drawFailScreen();
		}
		
		// JOGO RODANDO
		
		if(gp.gameState == gp.playState) {
			
			// TEMPO DE JOGO
			
			gp.setFont(arial_20);
			
 			playTime += (double) 1/(gp.FPS);
 			
 			g2.drawImage(clockImage, gp.tileSize*12,gp.tileSize/2, gp.tileSize,gp.tileSize,null);
			g2.drawString("= " + dFormat.format(maxTime),gp.tileSize*13,58);
			
			maxTime -= (double) 1/(gp.FPS);
			
			if(maxTime <= 0) {
				gp.gameState = gp.failState;
			}
			
			// CONTADOR DE PEÇAS
			
			g2.setColor(Color.white);
			g2.drawImage(repairKitImage, gp.tileSize/2,gp.tileSize/2, gp.tileSize,gp.tileSize,null);
			g2.drawString("x " + gp.player.hasRepairKits, 60, 65);
			
			
			// CONTADOR DE VIDAS
	
			g2.setColor(Color.white);
			g2.drawImage(heartImage, 2* gp.tileSize+15,gp.tileSize/2+5, gp.tileSize-10,gp.tileSize-10,null);
			
			g2.drawString("x " + gp.player.lifeNum, 140, 65);
				
			// MENSAGENS DA TELA
			
			if(messageOn == true) {
				
				g2.drawString(message, gp.tileSize/2, gp.tileSize*3);
				
				messageCounter++;
				
				if(messageCounter > 120) {
					messageOn = false;
					messageCounter = 0;
				}	
			}
		}
		
		// PAUSE
		
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		// VITÓRIA
		
		if(gp.gameState == gp.winState) {
			drawFinalScreen();		
		}
	}
	
	// MÉTODOS PARA CADA SITUAÇÃO
	
	public void drawTitleScreen(){
		
		String text;
		int x;
		int y;

		g2.setColor(new Color(0,0,80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// NOME 
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
		text = "Space Evaders";
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - gp.tileSize*3;

		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// IMAGEM DA NAVE
		
		x = gp.screenWidth/2 - (gp.tileSize);
		y = gp.tileSize*4;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2,null);
		
		// MENU
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text  = "NOVO JOGO";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text  = "SAIR";
		x = getXforCenteredText(text);
		y += gp.tileSize*2;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}	
	}
	
	public void drawFailScreen() {
		String text;
		int x;
		int y;
		
		
		g2.setColor(new Color(0,0,80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(arial_80B);
		g2.setColor(Color.yellow);
		
		text = "Você perdeu!";
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - gp.tileSize*2;
		
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		g2.setColor(Color.white);
		
		text  = "NOVO JOGO";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(command2Num == 0) {
			g2.drawString(">", x-(int)1.5*gp.tileSize, y);
		}
		
		text  = "SAIR";
		x = getXforCenteredText(text);
		y += gp.tileSize*2;
		g2.drawString(text, x, y);
		if(command2Num == 1) {
			g2.drawString(">", x-(int)1.5*gp.tileSize, y);
		}	
	}
	
	public void drawFinalScreen() {
		String text;
		int x;
		int y;
		
		gp.stopMusic();
		gp.playMusic(5);
		
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		
		g2.setColor(Color.white);
		text = "Você terminou o jogo!";
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - gp.tileSize*3;
		 
		g2.drawString(text, x, y);
		
		text = "Você escapou em " + dFormat.format(playTime) + "s!";
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 + gp.tileSize*3;
		 
		g2.drawString(text, x, y);
		
		g2.setFont(arial_80B);
		g2.setColor(Color.yellow);
		
		text = "Parabéns!";
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - gp.tileSize*4;
		
		g2.drawString(text, x, y);
		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		g2.setColor(Color.white);
		
		text  = "NOVO JOGO";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(command2Num == 0) {
			g2.drawString(">", x-(int)1.5*gp.tileSize, y);
		}
		
		text  = "SAIR";
		x = getXforCenteredText(text);
		y += gp.tileSize*2;
		g2.drawString(text, x, y);
		if(command2Num == 1) {
			g2.drawString(">", x-(int)1.5*gp.tileSize, y);
		}
	}
	
	public void drawPauseScreen() {
		
		int x, y;
		String text;
		
		text = "Aperte p para continuar!";
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 + gp.tileSize*3;
		 
		g2.drawString(text, x, y);
		
		text = "PAUSADO";
	
		g2.setFont(arial_80B);
		g2.setColor(Color.blue);
		
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - gp.tileSize*1;
		 
		g2.drawString(text, x, y);
	
	}
	
	// RETORNA A POSIÇÃO DO MEIO DA TELA
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
