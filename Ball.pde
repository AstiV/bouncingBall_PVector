class Ball {
  
  // Position and Size of Ball
  float ballX, ballY;  // Position of Ball
  float ballSize = 50.0;   // Diameter of Ball

  float xOffset = 0.0; 
  float yOffset = 0.0;

  // Speed of Movement
  float xSpeed = 3.0;
  float ySpeed = 2.3;

  // Color
  color ballColor;
  color strokeColor;

  // --------------------------------
  // Constructor
  // --------------------------------

  Ball() {
    ballColor = color(173,252,249);
  
    // Initially position ball randomly
    ballX = random(width);
    ballY = random(height);
  
    ellipseMode(CENTER);
  
  }

  // --------------------------------
  // Display circle
  // --------------------------------

    void displayBall(){
      stroke(strokeColor);
      fill(ballColor);
      ellipse(ballX, ballY, ballSize, ballSize);
    }
  
  // -------------------------------------------
  // Move circle by adding to it's coordinates
  // -------------------------------------------

  void moveBall() {
    ballX = ballX + xSpeed;
    ballY = ballY + ySpeed;
  }

  // ---------------------------------------------------------
  // Check if Ball touches borders, if yes change direction
  // ---------------------------------------------------------

  void checkBorders() {
    // (https://www.youtube.com/watch?v=YIKRXl3wH8Y&index=5&list=PLRqwX-V7Uu6YqykuLs00261JCqnL_NNZ_)
    if (ballX > width || ballX < 0 ) {
      xSpeed = xSpeed * -1; 
    } else if (ballY > height || ballY < 0) {
      ySpeed = ySpeed * -1;
    }
  }
}
