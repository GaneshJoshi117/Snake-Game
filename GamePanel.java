package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener{

    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];

    private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random();
    private int foodX,foodY;
    private int lengthOfSnake = 3;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    int moves = 0,score = 0;
    boolean gameOver = false;
    private  ImageIcon snaketitle = new ImageIcon(getClass().getResource("title.jpeg"));
    private  ImageIcon leftImg = new ImageIcon(getClass().getResource("left.jpeg"));
    private  ImageIcon rightImg = new ImageIcon(getClass().getResource("right.jpeg"));
    private  ImageIcon upImg = new ImageIcon(getClass().getResource("up.jpeg"));
    private  ImageIcon downImg = new ImageIcon(getClass().getResource("down.jpeg"));
    private  ImageIcon snakeImg = new ImageIcon(getClass().getResource("food.jpeg"));
    private  ImageIcon foodImg = new ImageIcon(getClass().getResource("enemy.jpeg"));

    private Timer timer;
    private int delay = 150;

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeys(true);
        timer = new Timer(delay, (ActionListener) this);
        timer.start();

        newFood();
    }

    private void collisionWithSnake() {
        for(int i = lengthOfSnake-1; i>=1;i--){
            if(snakeXlength[i]==snakeXlength[0] && snakeYlength[i]==snakeYlength[0]){
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void collisionWithEnemy() {
        if (snakeXlength[0]==foodX && snakeYlength[0]==foodY){
            newFood();
            lengthOfSnake++;
            snakeXlength[lengthOfSnake-1] = snakeXlength[lengthOfSnake-2];
            snakeYlength[lengthOfSnake-1] = snakeYlength[lengthOfSnake-2];
            score++;
        }
    }

    private void newFood() {
        foodX = xPos[random.nextInt(34)];
        foodY = yPos[random.nextInt(23)];
        for(int i = lengthOfSnake-1; i >= 0; i--){
            if(snakeXlength[i]==foodX && snakeYlength[i]==foodY){
                newFood();
            }
        }
    }

    private void setFocusTraversalKeys(boolean b) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

        if(moves == 0){
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
            moves++;
        }
        if(left){
            leftImg.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(right){
            rightImg.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(up){
            upImg.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(down){
            downImg.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        for(int i = 1; i < lengthOfSnake ; i++){
            snakeImg.paintIcon(this,g,snakeXlength[i],snakeYlength[i]);
        }
        foodImg.paintIcon(this,g,foodX,foodY);
        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
            g.drawString("Game Over",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
            g.drawString("Press Enter to Restart",175,400);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Score :" + score,750,30);
        g.drawString("Length :"+ lengthOfSnake, 750,50);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = lengthOfSnake-1 ; i >0 ; i--){
            snakeXlength[i] = snakeXlength[i-1];
            snakeYlength[i] = snakeYlength[i-1];
        }
        if(left){
            snakeXlength[0] = snakeXlength[0]-25;
        }
        if(right){
            snakeXlength[0] = snakeXlength[0]+25;
        }
        if(up){
            snakeYlength[0] = snakeYlength[0]-25;
        }
        if(down){
            snakeYlength[0] = snakeYlength[0]+25;
        }
        if(snakeXlength[0] > 850){
            snakeXlength[0] = 25;
        }
        if(snakeXlength[0] < 25){
            snakeXlength[0] = 850;
        }
        if(snakeYlength[0] < 75){
            snakeYlength[0] = 625;
        }
        if(snakeYlength[0] > 625){
            snakeYlength[0] = 75;
        }
        collisionWithEnemy();
        collisionWithSnake();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT && !right){
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && !left){
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && !down){
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && !up){
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }
        if(gameOver && e.getKeyCode()==KeyEvent.VK_ENTER){
            restart();
        }
    }

    private void restart() {
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        newFood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
