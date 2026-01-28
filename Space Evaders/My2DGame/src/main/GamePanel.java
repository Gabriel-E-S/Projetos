/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;

import javax.swing.JPanel;

import ai.PathFinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import tiles.TileManager;

/**
 *
 * @author Espírito Santo
 */

// CLASSE DE CONTROLE DO JOGO

public class GamePanel extends JPanel implements Runnable{

	// CONFIG TELA
    
    /**
	 *  SÓ PRA TIRAR O WARNING
	 */
	private static final long serialVersionUID = 1L;
	
	final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale ;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
     // CONFIG MUNDO
    
    public final int maxWorldCol = 33;
    public final int maxWorldRow = 33;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // FPS
    final int FPS = 60;
    
    // VARÍAVEIS DE CONTROLE
    
    public final int maxNPCS = 3;
    
    // VARIÁVEIS DE SISTEMA
    
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Sound soundEffects = new Sound();
    Thread gameThread;
    public TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this,tileM);
    public PathFinder pFinder = new PathFinder(this);
    
    
    public Player player = new Player(this,keyH);
    public UI ui = new UI(this);
    
    
    public SuperObject obj[] =  new SuperObject[200];
    public Entity npc[] = new Entity[maxNPCS];
    
    // ESTADO DO JOGO
    
    public int gameState ;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int winState = 3;
    public final  int failState = 4;

    // CONSTRUTOR
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    // SEMPRE FAZ NO COMEÇO DO JOGO
    
    public void setupGame() {
    	Arrays.fill(npc,null);
    	Arrays.fill(obj, null);
    	aSetter.setObject();
    	aSetter.setNPC();
    	
    	gameState = titleState;
    	player.lifeNum = 3;
    	player.speed = 3;
    	player.hasRepairKits = 0;
    	player.worldX = 1*tileSize;
    	player.worldY = 1*tileSize;
    	ui.maxTime = (double) 60 * 5 ;
    }

    // COMEÇAR NOVA THREAD
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    // RODAR
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS; // 0.01666 secons
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameThread != null){
     
            update();
            
            repaint();
     
            // ESSA PARTE É PRA DEIXAR NO FPS CERTO
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // CONVERTENDO PARA MS
                
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
           
            } catch (InterruptedException ex) {
                System.getLogger(GamePanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
    
    // UPDATE
    
    public void update(){
    	
    	if(gameState == playState) {
    		// PLAYER
    		player.update();
    		
    		// NPC
    		
    		for(int i = 0; i< npc.length;i++) {
    			if(npc[i]!= null) {
    				npc[i].update();
    			}
    		}
    		
    	}
    	if(gameState == pauseState) {
    		
    	}
    	if(gameState == failState) {
    	
    	}
    }
    
    
    // PAINT COMPONENT
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        // MENU PRINCIPAL
        
        if(gameState == titleState) {
        	ui.draw(g2);
        }
        else {
        	
        	 // TILES
        	
            tileM.draw(g2);
            
            // OBJETOS
            
            for(int i = 0; i< obj.length ; i++) {
            	if(obj[i]!= null) {
            		obj[i].draw(g2, this);
            	}
            }
            
            // NPC
            
            for(int i = 0; i <npc.length;i++) {
            	if(npc[i]!= null) {
            		npc[i].draw(g2,0.9,0.9,0.8,0.8);
            	}
            }
            
            // PLAYER
            player.draw(g2);
            
            // UI
            
            ui.draw(g2);
        }
    
        // LIBERA O CONTEXTO GRÁFICO
        
        g2.dispose();
    }
    
    // PARTE MUSICAL
    
    public void playMusic(int i) {
    	sound.setFile(i);
    	sound.play();
    	sound.loop();
    }
    public void stopMusic() {
    	sound.stop();
    }
    public void playSoundEffects(int i) {
    	soundEffects.playWithDelay(i);

    }        
}
