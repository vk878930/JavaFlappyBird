# Flappy Bird Java Game

This is a simple **Flappy Bird** game built in Java using `javax.swing` and `java.awt`. The game mimics the behavior of the classic Flappy Bird game, where the player controls a bird to pass through pipes without colliding.

---

## Features

- Smooth animations with `Timer`.
- Dynamic obstacle placement (pipes).
- Gravity and collision mechanics.
- Game over and restart functionality.
- Scoring system displayed at the top left corner.

---

## How to Run

1. Make sure you have **Java JDK** installed (version 8 or higher).
2. Compile the Java files using:
   ```bash
   javac App.java
Run the game using:
bash
Copy
Edit
java App
Code Overview
1. App Class
   The App class initializes the game window.

java
Copy
Edit
// import javax.swing.*;

// public class App {
//     public static void main(String[] args) throws Exception {
//         int boardWidth = 360;
//         int boardHeight = 640;

//         JFrame frame = new JFrame("Flappy Bird");
//         // Set frame size and properties
//         frame.setSize(boardWidth, boardHeight);
//         frame.setLocationRelativeTo(null);
//         frame.setResizable(false);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         FlappyBird flappybird = new FlappyBird(); // Add the game panel
//         frame.add(flappybird);
//         frame.pack();
//         flappybird.requestFocus(); // Request focus for key events
//         frame.setVisible(true);
//     }
// }
2. FlappyBird Class
   This is the main game logic class.

Variables and Initialization
java
Copy
Edit
// import java.awt.*;
// import java.awt.event.*;
// import java.util.ArrayList;
// import java.util.Random;
// import javax.swing.*;

// public class FlappyBird extends JPanel implements ActionListener, KeyListener {
//     // Board dimensions
//     int boardWidth = 360;
//     int boardHeight = 640;

//     // Images for game objects
//     Image backgroundImg;
//     Image birdImg;
//     Image topPipeImg;
//     Image bottomPipeImg;

//     // Bird properties
//     int birdX = boardWidth / 8;
//     int birdY = boardHeight / 2;
//     int birdWidth = 34;
//     int birdHeight = 24;

//     // Game mechanics
//     int velocityX = -4;  // Speed of the pipes
//     int velocityY = 0;   // Vertical velocity of the bird
//     int gravity = 1;     // Gravity applied to the bird

//     // Pipe properties
//     int pipeWidth = 64;
//     int pipeHeight = 512;
//     ArrayList<Pipe> pipes; // Stores all active pipes
//     Random random = new Random();

//     // Timers for game updates and pipe placement
//     Timer gameLoop;
//     Timer placePipesTimer;

//     boolean gameOver = false; // Game over state
//     double score = 0;         // Player's score

//     // Bird class for bird properties
//     class Bird {
//         int x = birdX;
//         int y = birdY;
//         int width = birdWidth;
//         int height = birdHeight;
//         Image img;

//         Bird(Image img) {
//             this.img = img;
//         }
//     }

//     // Pipe class for pipe properties
//     class Pipe {
//         int x = boardWidth;
//         int y = 0;
//         int width = pipeWidth;
//         int height = pipeHeight;
//         Image img;
//         boolean passed = false;

//         Pipe(Image img) {
//             this.img = img;
//         }
//     }

//     // Constructor to initialize the game
//     FlappyBird() {
//         setPreferredSize(new Dimension(boardWidth, boardHeight));
//         setFocusable(true);
//         addKeyListener(this);

//         // Load images
//         backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
//         birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
//         topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
//         bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

//         bird = new Bird(birdImg); // Initialize the bird
//         pipes = new ArrayList<>(); // Initialize the pipe list

//         // Timer for placing pipes
//         placePipesTimer = new Timer(1500, new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 placePipes();
//             }
//         });
//         placePipesTimer.start();

//         // Timer for game updates
//         gameLoop = new Timer(1000 / 60, this);
//         gameLoop.start();
//     }
Core Game Functions
Placing Pipes: Randomly places pipes with a gap for the bird to pass through.
java
Copy
Edit
// public void placePipes() {
//     int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
//     int openingSpace = boardHeight / 4;

//     Pipe topPipe = new Pipe(topPipeImg);
//     topPipe.y = randomPipeY;
//     pipes.add(topPipe);

//     Pipe bottomPipe = new Pipe(bottomPipeImg);
//     bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
//     pipes.add(bottomPipe);
// }
Game Updates: Moves the bird, pipes, and checks for collisions.
java
Copy
Edit
// public void move() {
//     velocityY += gravity; // Gravity effect on the bird
//     bird.y += velocityY;  // Update bird's position
//     bird.y = Math.max(bird.y, 0); // Prevent bird from moving off-screen

//     for (int i = 0; i < pipes.size(); i++) {
//         Pipe pipe = pipes.get(i);
//         pipe.x += velocityX; // Move pipes left

//         // Check if the bird has passed a pipe
//         if (!pipe.passed && bird.x > pipe.x + pipe.width) {
//             pipe.passed = true;
//             score += 0.5; // Increase score
//         }

//         // Check for collisions
//         if (collision(bird, pipe)) {
//             gameOver = true;
//         }
//     }

//     // Check if the bird hits the bottom of the screen
//     if (bird.y > boardHeight) {
//         gameOver = true;
//     }
// }
Collision Detection: Checks if the bird hits any pipe.
java
Copy
Edit
// public boolean collision(Bird a, Pipe b) {
//     return a.x < b.x + b.width &&
//            a.x + a.width > b.x &&
//            a.y < b.y + b.height &&
//            a.y + a.height > b.y;
// }
Event Handling
Key Presses: Space bar is used to make the bird jump.
java
Copy
Edit
// @Override
// public void keyPressed(KeyEvent e) {
//     if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//         velocityY = -9; // Bird jumps upward

//         if (gameOver) {
//             // Restart the game
//             bird.y = birdY;
//             velocityY = 0;
//             pipes.clear();
//             score = 0;
//             gameOver = false;
//             gameLoop.start();
//             placePipesTimer.start();
//         }
//     }
// }

// @Override
// public void keyTyped(KeyEvent e) {}

// @Override
// public void keyReleased(KeyEvent e) {}
Assets Used
Background Image: flappybirdbg.png
Bird Image: flappybird.png
Pipe Images: toppipe.png, bottompipe.png
