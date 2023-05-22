package SnakeGame;

import javax.swing.JFrame;

public class Frame extends JFrame {
    
    Frame(){
        this.setTitle("Snake Game");
        // we cannot draw anything on the frame, that is why we need a panel
        this.add(new Easel());
        this.pack(); // it makes the size of panel and frame equal
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}
