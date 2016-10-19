import javax.swing.JFrame;

public class Snake {

    public static void main(String[] args) {
        JFrame window  = new JFrame ();  
        Game s = new Game ();
        window.add (s);
        window.setVisible (true);
        window.setBounds (0,0,500,500);
        window.setTitle ("Snake");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
