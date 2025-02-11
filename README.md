Flappy Bird Game Documentation

Overview

This project is a recreation of the popular Flappy Bird game using Java's Swing library for the graphical interface. The game involves a bird controlled by the player navigating through pairs of pipes while avoiding collisions. The player scores points by passing through the pipes.

Features

Simple 2D Graphics: Utilizes images for the background, bird, and pipes.

Game Mechanics: Includes gravity, jump mechanics, and collision detection.

Score Tracking: Displays the current score in the top-left corner of the screen.

Game Restart: Automatically restarts the game when the player presses the jump button after a game over.

Files

1. App.java

This is the entry point of the application. It sets up the game window and initializes the game panel.

Key Responsibilities:

Create the JFrame for the game.

Add the FlappyBird panel to the frame.

Configure the window properties (size, location, etc.).

int boardWidth = 360;
int boardHeight = 640;

JFrame frame = new JFrame("Flappy Bird");
frame.setSize(boardWidth, boardHeight);
frame.setLocationRelativeTo(null);
frame.setResizable(false);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

FlappyBird flappybird = new FlappyBird();
frame.add(flappybird);
frame.pack();
frame.setVisible(true);

2. FlappyBird.java

This class contains the main game logic and rendering. It extends JPanel and implements ActionListener and KeyListener.

Key Components:

1. Images

Images are loaded for the background, bird, and pipes using ImageIcon.

backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

2. Game Elements

Bird:

Represented by the Bird inner class, which holds the bird's position, dimensions, and image.

Pipes:

Represented by the Pipe inner class, which holds the pipe's position, dimensions, and image.

3. Timers

gameLoop: A timer running at 60 FPS to update the game state.

placePipesTimer: A timer to spawn new pipes every 1.5 seconds.

4. Game Mechanics

Gravity: The bird is affected by gravity, increasing its vertical velocity over time.

Jump: Pressing the space bar applies an upward force to the bird.

Collision Detection: Checks if the bird collides with pipes or goes off the screen.

Scoring: Increments the score when the bird successfully passes a pipe pair.

5. Restart Mechanism

When the player presses the space bar after a game over, the game state is reset:

if (gameOver) {
    bird.y = birdY;
    velocityY = 0;
    pipes.clear();
    score = 0;
    gameOver = false;
    gameLoop.start();
    placePipesTimer.start();
}

Code Walkthrough

Key Methods:

1. placePipes()

Spawns a pair of pipes (top and bottom) at random vertical positions, ensuring there is a gap between them for the bird to pass through.

int randomPipeY = (int)(pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
int openingSpace = boardHeight / 4;

Pipe topPipe = new Pipe(topPipeImg);
topPipe.y = randomPipeY;
pipes.add(topPipe);

Pipe bottomPipe = new Pipe(bottomPipeImg);
bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
pipes.add(bottomPipe);

2. move()

Handles the bird's movement, pipe movement, and collision detection.

Updates the bird's vertical position based on gravity.

Moves pipes to the left and removes them when they go off-screen.

Checks for collisions between the bird and pipes.

3. collision()

Checks for collision between the bird and a pipe using bounding box logic.

return a.x < b.x + b.width &&
       a.x + a.width > b.x &&
       a.y < b.y + b.height &&
       a.y + a.height > b.y;

4. paintComponent(Graphics g)

Renders the game elements on the screen, including the background, bird, pipes, and score.

5. Key Event Handlers

keyPressed(KeyEvent e): Handles the bird's jump and game restart.

keyTyped(KeyEvent e) and keyReleased(KeyEvent e): Currently unused.

Game Flow

The game starts with the bird positioned at the center of the screen.

The player presses the space bar to make the bird jump.

Pipes spawn at regular intervals and move from right to left.

The player scores points by passing through the pipes.

The game ends if the bird collides with a pipe or falls off the screen.

The player can restart the game by pressing the space bar.

Customization

1. Adjusting Difficulty

Pipe Speed: Modify velocityX to increase or decrease the pipe movement speed.

Gravity: Adjust the gravity variable to make the bird fall faster or slower.

Pipe Spacing: Change openingSpace in placePipes() to alter the gap between pipes.

2. Changing Assets

Replace the image files (flappybirdbg.png, flappybird.png, etc.) in the resources folder with your custom images.

Future Improvements

Advanced Graphics: Add animations for smoother transitions and effects.

Sound Effects: Play sounds for jumps, collisions, and scoring.

High Scores: Implement a leaderboard to track the player's best scores.

Mobile Compatibility: Port the game to a mobile platform.

Conclusion

This project demonstrates the fundamentals of creating a 2D game using Java Swing, including rendering graphics, handling user input, and implementing basic game mechanics. It is an excellent starting point for learning game development in Java.


