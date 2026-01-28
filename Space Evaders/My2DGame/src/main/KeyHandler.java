/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Espírito Santo
 */


// CLASSE QUE PEGA AS TECLAS PRESSIONADAS

public class KeyHandler implements KeyListener {
    
	// ATRIBUTOS
	
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GamePanel gp;

    // MÉTODOS DA CLASSE ABSTRATA
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
    
    // CONSTRUTOR
    
    public KeyHandler(GamePanel gp) {
    	this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	
    	int code = e.getKeyCode();

    	if(gp.gameState == gp.titleState) {
    		     
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0 ) {
                	gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1 ) {
                	gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0) {
                	gp.gameState = gp.playState;
                	gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1) {
                	System.exit(0);
                }
            }
    	}
	
    	if(gp.gameState == gp.playState) {
            
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
        }
    	
    	if(gp.gameState == gp.failState) {
   
            if(code == KeyEvent.VK_W){
                gp.ui.command2Num--;
                
                if(gp.ui.command2Num < 0 ) {
                	gp.ui.command2Num = 1;
                }
            }
            if(code == KeyEvent.VK_S){ 	
                downPressed = true;
                gp.ui.command2Num++;
                if(gp.ui.command2Num > 1 ) {
                	gp.ui.command2Num = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){      	
                if(gp.ui.command2Num == 0) {
                	gp.gameState = gp.playState;
                	gp.stopMusic();
                	gp.setupGame();
                	gp.tileM.loadMap(null);
                }
                if(gp.ui.command2Num == 1) {
                	System.exit(0);
                }
            }
    	}
    	
    	if(gp.gameState == gp.winState) {
    
            if(code == KeyEvent.VK_W){
                gp.ui.command2Num--;
                
                if(gp.ui.command2Num < 0 ) {
                	gp.ui.command2Num = 1;
                }
            }
            if(code == KeyEvent.VK_S){	
                downPressed = true;
                gp.ui.command2Num++;
                if(gp.ui.command2Num > 1 ) {
                	gp.ui.command2Num = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){      	
                if(gp.ui.command2Num == 0) {
                	gp.gameState = gp.playState;
                	gp.stopMusic();
                	gp.setupGame();
                	gp.tileM.loadMap(null);
                }
                if(gp.ui.command2Num == 1) {
                	System.exit(0);
                }
            }
    	}

    	if(code == KeyEvent.VK_P){
        	if(gp.gameState == gp.playState) {
            	gp.gameState = gp.pauseState;
            }else if(gp.gameState == gp.pauseState) {
            		gp.gameState = gp.playState;
            		}
    	}    
    }    
}
