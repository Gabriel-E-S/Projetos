/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.JFrame;

/**
 *
 * @author Espírito Santo
 */

// CLASSE DO MÉTODO MAIN

public class My2DGame {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Space Evaders");
        
        GamePanel gamePanel = new GamePanel();
        
        window.add(gamePanel);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
