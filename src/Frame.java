import javax.swing.JFrame;

public class Frame extends JFrame {

    // create construction for the Snake Game Frame - Playground
    Frame(){
            this.add(new Playground());
            this.setTitle("Snake Game");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
    }

}
