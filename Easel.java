package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Random;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.FontMetrics;

public class Easel extends JPanel implements ActionListener {

    int GAME_WIDTH = 900;
    int GAME_HIEGHT = 600;
    int FOOD_SIZE = 30; // it is a divisor of both width and height
    int xFood = 0;
    int yFood = 0;
    int score = 0;
    char dir = 'E';
    int xSnake[] = new int[599];
    int ySnake[] = new int[599];
    int snakelength = 3;
    Random random;
    Timer timer;
    boolean startGame = false;
    
    Easel(){
        this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HIEGHT));
        this.setBackground(Color.black);
        random = new Random();
        this.setFocusable(true);
        this.addKeyListener(new Key());
        gameStart();
    }

    private void gameStart() {
        spawnFood();
        timer = new Timer(160, this);
        timer.start();
        startGame = true; 
    }

    @Override
   public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
   }

    private void draw(Graphics g) {
        if(startGame){
            
            // drawing food on the panel
            g.setColor(Color.red);
            g.fillOval(xFood, yFood, FOOD_SIZE, FOOD_SIZE);

            // putting score on the panel / board
            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans MS",Font.BOLD,FOOD_SIZE));
            FontMetrics fme = getFontMetrics(g.getFont());
            g.drawString("Score :"+score, GAME_WIDTH/2-(fme.stringWidth("Score :"+score)/2) , g.getFont().getSize());

            //creating snake
            for(int i=0; i<snakelength ; i++){
                g.setColor(Color.BLUE);
                g.fillRect(xSnake[i], ySnake[i], FOOD_SIZE, FOOD_SIZE);

            }
        } else {
            // we will show the score and GAME OVER on the screen
            g.setColor(Color.cyan);
            g.setFont(new Font("Comic Sans MS",Font.BOLD,FOOD_SIZE));
            FontMetrics fme = getFontMetrics(g.getFont());
            g.drawString("Score :"+score, GAME_WIDTH/2-(fme.stringWidth("Score :"+score)/2) , 4*g.getFont().getSize());

            g.setColor(Color.red);
            g.setFont(new Font("Comic Sans MS",Font.BOLD,3*FOOD_SIZE));
            FontMetrics fme1 = getFontMetrics(g.getFont());
            g.drawString("GAME OVER", GAME_WIDTH/2-(fme1.stringWidth("GAME OVER")/2) , GAME_HIEGHT/2);


            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans MS",Font.BOLD,FOOD_SIZE));
            FontMetrics fme2 = getFontMetrics(g.getFont());
            g.drawString("Press 'R' to Restart", GAME_WIDTH/2-(fme2.stringWidth("Press 'R' to Restart")/2) , 15*g.getFont().getSize());
        }
        
    }

    private void spawnFood() {
        xFood = random.nextInt((GAME_WIDTH/FOOD_SIZE)) * FOOD_SIZE;
        yFood = random.nextInt((GAME_HIEGHT/FOOD_SIZE)) * FOOD_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        foodeaten();
        coolapsed();
    }

    private void coolapsed() {
        //check if the head is hitting any boundary
        if(xSnake[0] > GAME_WIDTH || xSnake[0] < 0 || ySnake[0] < 0 || ySnake[0] >GAME_HIEGHT){
            gameover();
        }

        // check if the head hits its own body
        for(int i=1; i<snakelength ; i++){
            if(xSnake[0] == xSnake[i] && ySnake[0] == ySnake[i]){
                gameover();
            }
        }
    }

    private void gameover() {
        startGame = false;
    }

    private void foodeaten() {
        if(xSnake[0] == xFood && ySnake[0] == yFood){
            score++;
            snakelength++;
            spawnFood();
        }
    }

    private void move() {
        
        for(int i=snakelength ; i>0 ; i--){
            xSnake[i] = xSnake[i-1];
            ySnake[i] = ySnake[i-1];
        }


        if(dir == 'E'){
            xSnake[0] += FOOD_SIZE;
        } else if(dir == 'S'){
            ySnake[0] += FOOD_SIZE;
        } else if(dir == 'W'){
            xSnake[0] -= FOOD_SIZE;
        } else if(dir == 'N'){
            ySnake[0] -= FOOD_SIZE;
        }
        
    }

    public class Key extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP : 
                if(dir != 'S'){
                  dir = 'N';  
                } 
                break;
                case KeyEvent.VK_DOWN : 
                if(dir != 'N'){
                    dir = 'S';
                }
                break;
                case KeyEvent.VK_LEFT : 
                if(dir != 'E'){
                    dir = 'W';
                }
                break;
                case KeyEvent.VK_RIGHT : 
                if(dir != 'W'){
                    dir = 'E';
                }
                break;
                case KeyEvent.VK_R:
                if(startGame == false){
                    
                    Arrays.fill(xSnake,0);
                    Arrays.fill(ySnake,0);
                    dir = 'E';
                    score = 0;
                    snakelength = 3;
                    timer.stop();
                    gameStart();
                    
                }
            }

        }

    }

}
