class Ball {
  
  // --------------------------------
  // Position and Size of Ball
  // --------------------------------

  float ballSize = 50.0;   // Diameter of Ball

  // float ballX, ballY;  // Position of Ball
  // => x and y can be expressed in PVector object (location)
  PVector location;

  // --------------------------------
  // Speed / Direction of movement
  // --------------------------------

  // float xSpeed = 3.0;
  // float ySpeed = 2.3;
  // xSpeed and ySpeed can be expressed in PVector oblect (velocity)
  PVector velocity;

  // life
  float lifespan = 255;

  // Color
  color ballColor;
  color strokeColor;

  // --------------------------------
  // Constructor
  // --------------------------------

  Ball() {
    // ballColor = color(173,252,249);

    // random color
    ballColor = color(random(255), random(255), random(255));
    strokeColor = ballColor;
    
  
    // Initially position ball randomly

    // ballX = random(width);
    // ballY = random(height);
    location = new PVector(random(width), random(height));
    velocity = new PVector(3.0, 2.3);

    ellipseMode(CENTER);
  
  }

  void runBall() {
    displayBall();
    moveBall();
    checkBorders();
  }


  // --------------------------------
  // Display Ball
  // --------------------------------

    void displayBall(){
      stroke(strokeColor, lifespan);
      fill(ballColor, lifespan);
      ellipse(location.x, location.y, ballSize, ballSize);
    }
  
  // -------------------------------------------
  // Move Ball by adding to it's coordinates
  // -------------------------------------------

  void moveBall() {
    // ballX = ballX + xSpeed;
    // ballY = ballY + ySpeed;
    // => translates into the following PVectors
    // + operator does not work with vectors => function is needed
    location.add(velocity);
    lifespan -= 2.0;
  }

  // ---------------------------------------------------------
  // Check if Ball touches borders, if yes change direction
  // ---------------------------------------------------------

  void checkBorders() {
    // (https://www.youtube.com/watch?v=YIKRXl3wH8Y&index=5&list=PLRqwX-V7Uu6YqykuLs00261JCqnL_NNZ_)
    
    // if (ballX > width || ballX < 0 ) {
    //   xSpeed = xSpeed * -1; 
    // } else if (ballY > height || ballY < 0) {
    //   ySpeed = ySpeed * -1;
    // }

    if (location.x > width || location.x < 0 ) {
      velocity.x = velocity.x * -1; 
    } else if (location.y > height || location.y < 0) {
      velocity.y = velocity.y * -1;
    }
  }

  void isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}
