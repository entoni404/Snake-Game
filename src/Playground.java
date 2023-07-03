import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class Playground extends JPanel implements ActionListener {

    // variables
    static final int SC_Width = 600;
    static final int SC_Height = 600;
    static final int Unit_Size = 25; // how big the objects will be
    static final int Game_Units = (SC_Width * SC_Height) / Unit_Size; // calculates how many object are fitted in the screen
    static final int delay = 80; // checks the speed of the game
    final int posX[] = new int[Game_Units]; // hold the position of the snake body part in the Xs
    final int posY[] = new int[Game_Units]; // hold the position of the snake body part in the Ys
    int snakeBodyLength = 3; // initial number of body parts for the snake
    int scoreCount; // score counting
    int targetX; // random position of target showing up in the Xs
    int targetY; // random position of target showing up in the Ys
    char direction = 'R'; // R: right; L: left; U:up; D:down
    boolean hunting = false; // checking if the game is still on
    Timer timer;
    Random random;

    // Create constructor for the Playgroung
    Playground() {
        random = new Random();
        this.setPreferredSize(new Dimension(SC_Width, SC_Height)); // sets the size of the playground
        this.setBackground(Color.black); // bg color
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    // methods

    public void startGame() {
        newTarget();
        hunting = true;
        timer = new Timer(delay, this); // passing *this since we are using ActionListener Interface
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (hunting) {

//
//            // drawing lines
//            for (int i = 0; i < SC_Height / Unit_Size; i++) {
//                g.drawLine(i * Unit_Size, 0, i * Unit_Size, SC_Height);
//                g.drawLine(0, i * Unit_Size, SC_Width, i * Unit_Size);
//
//            }

            // drawing target
            g.setColor(Color.red);
            g.fillOval(targetX, targetY, Unit_Size, Unit_Size);

            // drawing snake
            for (int i = 0; i < snakeBodyLength; i++) {
                if (i == 0) {
                    g.setColor(Color.white);
                    g.fillRect(posX[i], posY[i], Unit_Size, Unit_Size);
                } else {
                    g.setColor(Color.lightGray);
                    g.fillRect(posX[i], posY[i], Unit_Size, Unit_Size);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " +scoreCount , (SC_Width - metrics.stringWidth("Score: " +scoreCount))/2,g.getFont().getSize());

        }
        else {
            gameOver(g);
        }
    }

    public void newTarget() {
        targetX = random.nextInt((int) (SC_Width / Unit_Size)) * Unit_Size;
        targetY = random.nextInt((int) (SC_Height / Unit_Size)) * Unit_Size;
    }

    public void move() {
        for (int i = snakeBodyLength; i > 0; i--) {
            posX[i] = posX[i - 1];
            posY[i] = posY[i - 1];
        }
        switch (direction) {
            case 'U':
                posY[0] = posY[0] - Unit_Size;
                break;
            case 'D':
                posY[0] = posY[0] + Unit_Size;
                break;
            case 'R':
                posX[0] = posX[0] + Unit_Size;
                break;
            case 'L':
                posX[0] = posX[0] - Unit_Size;
                break;
        }
    }

    public void countScores() {
        if ((posX[0] == targetX) && (posY[0] == targetY)) {
            snakeBodyLength++;
            scoreCount++;
            newTarget();

        }

    }

    public void checkSuicide() {
        // checking if snake eats itself
        for (int i = snakeBodyLength; i > 0; i--) {
            if ((posX[0] == posX[i]) && (posY[0] == posY[i])) {
                hunting = false;
            }
        }
        // check if snake hits the right borders
        if (posX[0] > SC_Width) {
            hunting = false;
        }
        // check if snake hits the bottom border
        if (posY[0] > SC_Height) {
            hunting = false;
        }
        // check if snake hits the left border
        if (posX[0] < 0) {
            hunting = false;
        }
        // check if snake hits the top border
        if (posY[0] < 0) {
            hunting = false;
        }
        if (!hunting) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SC_Width - metrics.stringWidth("Game Over"))/2, SC_Height/2);

        // game score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " +scoreCount , (SC_Width - metrics1.stringWidth("Score: " +scoreCount))/2,g.getFont().getSize());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hunting) {
            move();
            countScores();
            checkSuicide();
        }
        repaint();
    }

    // inner class for keyboard inputs
    public class MyKeyAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

            }


        }


    }


}
