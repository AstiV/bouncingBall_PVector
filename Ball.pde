class Ball {
  
  // --------------------------------
  // Position and Size of Ball
  // --------------------------------

  float ballSize = 15.0;   // Diameter of Ball

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

  // --------------------------------
  // Acceleration
  // --------------------------------
  PVector acceleration;

  // life
  float lifespan = 255;

  // Color
  color ballColor;
  color strokeColor;

  // --------------------------------
  // Constructor
  // --------------------------------

  Ball() {
    ballColor = color(173,252,249);

    // random color
    //ballColor = color(random(50), random(255), random(200));
    strokeColor = ballColor;
    
  
    // Initially position ball randomly

    // ballX = random(width);
    // ballY = random(height);
    location = new PVector(random(0, width), random(0, height));
    velocity = new PVector(0.0, 0.0);
    acceleration = new PVector(0.0, 0.0);

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
      //stroke(strokeColor);
      fill(ballColor, lifespan);
      //fill(ballColor);
      ellipse(location.x, location.y, ballSize, ballSize);
    }

  // --------------------------------
  // Add Gravity
  // --------------------------------

  void applyForce(PVector force) {
    acceleration.add(force);
  }

  void attract(float x,float y) {
    PVector mouse = new PVector(x, y);
    mouse.sub(location);
    mouse.setMag(0.3);
    acceleration = mouse;
  }

  void repulse(float x, float y) {
    PVector mouse = new PVector(x, y);
    // println("Repulse Coordinates from Ball Class : "+mouse);
    mouse.sub(location);
    mouse.setMag(-2.0);
    acceleration = mouse;
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
    velocity.add(acceleration);
    lifespan -= 0.5;

    // random acceleration (set acc and vel values in constructor to 0!)
    //acceleration = PVector.random2D();

    // limiting velocity
    velocity.limit(7.5);

    // clear out acceleration
    acceleration.mult(0);
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

  boolean isDying() {
    if (lifespan < 80.0) {
      return true;
    } else {
      return false;
    }
  }

  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}
