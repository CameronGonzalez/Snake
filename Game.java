import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener,KeyListener {

    Timer t = new Timer (5,this);
    
    boolean started = true;
    boolean collided = false;
    boolean gameOver = false;
    
    int x=0,y=0,velx=0,vely=0,numParts=0,pause=0;
    int applex,appley;
 
    Vector<Integer> snakex = new Vector<> ();
    Vector<Integer> snakey = new Vector<> ();
        
    public Game (){
        t.start ();
        addKeyListener (this);
        setFocusable (true);
        setFocusTraversalKeysEnabled (true); 
    }
    
    @Override
    public void paintComponent (Graphics g){
        if (started){
        Apple ();
        started = false;
        }
        
        super.paintComponent (g);
        this.setBackground (Color.black);
        g.setColor(Color.red);
        g.fillRect(x, y, 15, 15);
        g.fillRect(applex, appley, 15, 15);
        
        if (collided){
            snakex.add (x);
            snakey.add (y);
            
            if (snakex.size () > (15*numParts) && snakey.size () > (15*numParts)){
                for (int i = 0;i<(15*numParts);i++){
                    g.fillRect(snakex.get(i),snakey.get(i), 15, 15);
                }
                if (snakex.size()>(15*numParts) && snakey.size()>(15*numParts)){
                    snakex.remove(0);
                    snakey.remove(0);
                }
            }
            
        }
        if (gameOver){
            GameOver (g);
        }
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint ();
        x += velx;
        y += vely;
        if (!started){
            AppleCollision ();
            SnakeCollision ();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); 
        
        if (key == KeyEvent.VK_LEFT) {
            velx = -1;
            vely = 0; 
        }
        else if (key == KeyEvent.VK_RIGHT) {
            velx = 1;
            vely = 0;
        }
        else if (key == KeyEvent.VK_UP) {
            velx = 0;
            vely = -1; 
        }
        else if (key == KeyEvent.VK_DOWN) {
            velx = 0;
            vely = 1; 
        }
        else if (key==KeyEvent.VK_P){
            pause++;
            if (pause == 2){
                t.start();
                pause =0;
            }
        }
        else if (key == KeyEvent.VK_R){
            x=0;
            y=0;
            velx=0;
            vely=0;
            numParts =0;
            collided = false;
            gameOver = false;
            snakex.clear();
            snakey.clear();
            Apple ();
            t.start ();       
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode(); 
        if (key==KeyEvent.VK_P){
            if (pause==1){
                t.stop();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public void Apple (){
        Random apple = new Random ();
        applex = apple.nextInt(450);
        appley = apple.nextInt(450);
    }
    
    public void AppleCollision (){
        if (x < (applex+13)&& x > (applex-13)&& y < (appley+13) && y > (appley -13)){
            numParts++;
            Apple ();
            collided = true;
        }
    }
    
    public void SnakeCollision (){
        for (int j =0;j<(snakex.size());j++){
            if (x==snakex.get(j)&&y==snakey.get(j)){
                gameOver = true;
                t.stop();
            }
        }
	if (x>500||x<0||y>500||y<0){
		gameOver = true;
		t.stop();
	}
    }
    
    public void GameOver (Graphics g){
        String msg = "Game Over!";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, 195, 200);
    }
}
