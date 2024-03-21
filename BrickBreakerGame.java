import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BrickBreakerGame extends JFrame implements KeyListener, ActionListener {
    private Timer timer;
    private int ballX = 120, ballY = 350, ballXDir = -1, ballYDir = -2;
    private int paddleX = 310;
    private boolean left = false, right = false;
    private int[][] bricks;
    private int brickWidth = 50, brickHeight = 20;
    private int totalBricks = 21;

    public BrickBreakerGame() {
        bricks = new int[3][7];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                bricks[i][j] = 1;
            }
        }

        setTitle("Brick Breaker Game");
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(5, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 592, 392);

        // Bricks
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (bricks[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }

        // Paddle
        g.setColor(Color.green);
        g.fillRect(paddleX, 330, 100, 8);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballX, ballY, 20, 20);

        // Game Over
        if (totalBricks <= 0) {
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("You Win!", 230, 200);
            ballXDir = 0;
            ballYDir = 0;
        }

        if (ballY > 370) {
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + totalBricks, 190, 200);
            ballXDir = 0;
            ballYDir = 0;
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (left) {
            paddleX -= 20;
        }
        if (right) {
            paddleX += 20;
        }

        // Ball movement
        ballX += ballXDir;
        ballY += ballYDir;
        if (ballX < 0 || ballX > 570) {
            ballXDir = -ballXDir;
        }
        if (ballY < 0 || ballY > 370) {
            ballYDir = -ballYDir;
        }

        // Collision with paddle
        if (ballY > 330 && ballY < 340 && ballX > paddleX && ballX < paddleX + 100) {
            ballYDir = -ballYDir;
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (bricks[i][j] == 1) {
                    Rectangle brickRect = new Rectangle(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
                    if (ballRect.intersects(brickRect)) {
                        bricks[i][j] = 0;
                        totalBricks--;
                        if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                            ballXDir = -ballXDir;
                        } else {
                            ballYDir = -ballYDir;
                        }
                    }
                }
            }
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
    }

    public static void main(String[] args) {
        BrickBreakerGame game = new BrickBreakerGame();
        game.setVisible(true);
    }
}
