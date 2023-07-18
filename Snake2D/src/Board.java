import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT=400;
    int B_WIDTH=400;
    int MAX_DOTS=1600;
    int DOT_SIZE=10;
    int DOT;
    int x[]=new int[MAX_DOTS];
    int y[]=new int[MAX_DOTS];
    int apple_x;
    int apple_y;

    Image body, head ,apple;
    Timer timer;
    int DELAY = 170;
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;
    boolean inGame=true;



    Board(){
        TAdaptor tAdaptor=new TAdaptor();
        addKeyListener(tAdaptor);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }
    public void initGame(){
        DOT=3;
        x[0]=250;
        y[0]=250;
        for(int i=1;i<DOT;i++)
        {
            x[i]=x[0]+DOT_SIZE*i;
            y[i]=y[0];
        }
//        apple_x=150;
//        apple_y=150;
        locateApple();
        timer=new Timer(DELAY,this);
        timer.start();
    }
    public void loadImages(){
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOT; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
        else
        {
            gameOver(g);
            timer.stop();
            }
            //random apple location

    }

    public void locateApple(){
        apple_x=((int)(Math.random()*39))*DOT_SIZE;
        apple_y=((int)(Math.random()*39)*DOT_SIZE);
    }

    public void checkCollision(){
        for(int i=1;i<DOT;i++)
        {
            if(i>4 && x[0]==x[i] && y[0]==y[i])
            {
                inGame=false;
            }
        }
        if(x[0]<0)
        {
            inGame=false;
        }
        if(x[0]>=B_WIDTH)
        {
            inGame=false;
        }
        if(y[0]<0)
        {
            inGame=false;
        }
        if(x[0]>=B_HEIGHT)
        {
            inGame=false;
        }
    }

    public void gameOver(Graphics g){

        String msg="GAME OVER";
        int score=(DOT-3)*100;
        String scoremsg="SCORE:"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);
        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,B_HEIGHT/4);
        g.drawString(scoremsg,(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2,3*(B_HEIGHT/4));
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame)
        {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }


    public void move(){
        for(int i=DOT-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0]-=DOT_SIZE;
        }
        if(rightDirection)
        {
            x[0]+=DOT_SIZE;
        }
        if(upDirection)
        {
            y[0]-=DOT_SIZE;
        }
        if(downDirection)
        {
            y[0]+=DOT_SIZE;
        }
    }

    public void checkApple(){
        if(apple_x == x[0]&&apple_y==y[0]){
            DOT++;
            locateApple();
        }
    }
    //implement controls

    private class TAdaptor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_LEFT &&! rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT &&! leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP &&! downDirection){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN &&! upDirection){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }

}
