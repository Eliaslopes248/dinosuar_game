import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Chromedinosaur extends JPanel implements ActionListener,KeyListener {

    int boardwidth = 750;
    int boardheight = 250;

    //images
    Image dinosaurImg;
    Image dindosaurDead;
    Image dindosaurJump;
    Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;

    class Block{

        int x;
        int y;
        int width;
        int height;
        Image img;

        public Block(int x,int y,int width,int height,Image img){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;

        }

    }

    //dinosaur
    int dinowidth = 88;
    int dinoheight = 94;
    int X = 50;
    int Y = boardheight - dinoheight;

    //placeholder object
    Block dinosaur;

    //cactus
    int cactus1Width = 34;
    int cactus2Width = 69;
    int cactus3Width = 102;

    int cactusHeight = 70;

    int cactusX = 700;
    int cactusY = boardheight - cactusHeight;

    ArrayList<Block> catcusList;
    

    //physics
    int velocityY = 0;
    int velocityX = -12;
    int gravity = 1;


    Timer gameLoop;
    Timer placeCactusTimer;

    int score = 0;

    public Chromedinosaur(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        dinosaurImg = new ImageIcon(getClass().getResource("./images/dino-run.gif")).getImage();
        dindosaurDead = new ImageIcon(getClass().getResource("./images/dino-dead.png")).getImage();
        dindosaurJump = new ImageIcon(getClass().getResource("./images/dino-jump.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./images/big-cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./images/big-cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./images/big-cactus3.png")).getImage();

        //dinosaur
        dinosaur = new Block(X, Y, dinowidth, dinoheight, dinosaurImg);

        catcusList = new ArrayList<Block>();


        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placeCactus();
            }
        });
        placeCactusTimer.start();
    }

    void placeCactus(){
        double placechance = Math.random();
        //System.out.println(placechance);
        if (gameOver) {
            return;
        }

        if(placechance > .90){
            Block cactus = new Block(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
            catcusList.add(cactus);
        }
        else if(placechance > .70){
            Block cactus = new Block(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
            catcusList.add(cactus);
        }

        else if(placechance > .50){
            Block cactus = new Block(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
            catcusList.add(cactus);
        }

        if(catcusList.size() > 10){
            catcusList.remove(0);
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
   
    public void draw(Graphics g){
        //draw dino
        g.drawImage(dinosaur.img,dinosaur.x,dinosaur.y,dinosaur.width,dinosaur.height,null);

        //draw cactus
        for(int i = 0; i < catcusList.size(); i++){
            Block cactus = catcusList.get(i);
            
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }

        //draw score
        g.setColor(Color.black);
        g.setFont(new Font("Courier",Font.PLAIN,32));

        if(gameOver){
            g.drawString("GAME OVER: " +String.valueOf(score),10,35);
        }else{
            g.drawString("SCORE: "+ String.valueOf(score), 10, 35);
        }
    }

    public void move(){
        velocityY += gravity;
        dinosaur.y += velocityY;

        if(dinosaur.y > Y){
            dinosaur.y = Y;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }

        for(int i = 0; i < catcusList.size(); i++){
            Block cactus = catcusList.get(i);
            cactus.x += velocityX;

            if (collison(dinosaur, cactus)) {
                gameOver = true;
                dinosaur.img = dindosaurDead;
            }
        }
        score++;
    }
    boolean gameOver = false;

    boolean collison(Block a ,Block b){

        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameOver){
            placeCactusTimer.stop();
            gameLoop.stop();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_SPACE){
        System.out.println("JUMP!");
        
        if(dinosaur.y == Y){
            velocityY = -17;
            dinosaur.img = dindosaurJump;
        }

        if(gameOver){
            dinosaur.y = Y;
            dinosaur.img = dinosaurImg;
            velocityY = 0;
            score = 0;
            catcusList.clear();
            gameOver = false;

            gameLoop.start();
            placeCactusTimer.start();
        }

       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
