import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // Board dimensions
    int boardWidth = 360;
    int boardHeight = 640;

    // Images for the game elements
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird properties
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    // Pipe properties
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // Game logic variables
    Bird bird;
    int velocityX = -4; // Pipe movement speed
    int velocityY = 0; // Vertical velocity of the bird
    int gravity = 1; // Gravity affecting the bird

    ArrayList<Pipe> pipes; // List of pipes on the screen
    Random random = new Random(); // Random generator for pipe placement

    Timer gameLoop; // Timer for the main game loop
    Timer placePipesTimer; // Timer for spawning new pipes
    boolean gameOver = false; // Game over state
    double score = 0; // Player's score

    // Constructor
    FlappyBird() {
        // Set panel properties
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Initialize the bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<>();

        // Timer for spawning pipes
        placePipesTimer = new Timer(1500, e -> placePipes());
        placePipesTimer.start();

        // Main game loop timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // Spawns new pipes at regular intervals
    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; // Space between top and bottom pipes

        // Create and position the top pipe
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        // Create and position the bottom pipe
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    // Handles game updates
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        // Stop the game if it's over
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    // Paints game elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draws all game elements on the screen
    public void draw(Graphics g) {
        // Draw the background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Draw the bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // Draw the pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Draw the score (top-left corner)
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // Handles bird and pipe movement
    public void move() {
        // Bird movement
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); // Prevent the bird from going above the screen

        // Pipe movement and collision detection
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            // Check if the bird has passed the pipe
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // Increment score for passing a pipe pair
            }

            // Check for collisions
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        // Remove pipes that are off the screen
        pipes.removeIf(pipe -> pipe.x + pipe.width < 0);

        // End the game if the bird hits the ground
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    // Checks for collision between the bird and a pipe
    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    // Handles key press events
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9; // Make the bird "jump"
            if (gameOver) {
                // Restart the game
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // Inner class for the bird
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Inner class for the pipes
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }
}