class Ball {
  
  float ballSize = 15.0; // Diameter of Ball
  color ballColor;
  color strokeColor;
  float lifespan = 255;

  PVector location;
  PVector velocity; // Speed / Direction of movement
  PVector acceleration;

  Ball() {
    ballColor = color(173,252,249);

    // random color
    //ballColor = color(random(50), random(255), random(200));
    strokeColor = ballColor;
    
    // Initially position ball randomly
    location = new PVector(random(width), random(height));
    velocity = new PVector(0.0, 0.0);
    acceleration = new PVector(0.0, 0.0);

    ellipseMode(CENTER);
  }

  void runBall() {
    displayBall();
    moveBall();
    checkBorders();
  }

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

  // --------------------------------
  // Add Attraction / Repulsion
  // --------------------------------

  void attract(float x,float y) {
    PVector hand = new PVector(x, y);
    hand.sub(location);
    hand.setMag(0.3);
    acceleration = hand;
  }

  void repulse(float x, float y) {
    PVector hand = new PVector(x, y);
    // println("Repulse Coordinates from Ball Class : "+hand);
    hand.sub(location);
    hand.setMag(-2.0);
    acceleration = hand;
  }
  
  // -------------------------------------------
  // Move Ball by adding to it's coordinates
  // -------------------------------------------

  void moveBall() {
    // + operator does not work with vectors => function add() is needed
    location.add(velocity);
    velocity.add(acceleration);
    lifespan -= 0.5;

    // limiting velocity
    velocity.limit(7.5);

    // clear out acceleration
    acceleration.mult(0);
  }

  // ---------------------------------------------------------
  // Check if Ball touches borders, if yes change direction
  // ---------------------------------------------------------

  void checkBorders() {
    if (location.x > width || location.x < 0 ) {
      velocity.x = velocity.x * -1; 
    } else if (location.y > height || location.y < 0) {
      velocity.y = velocity.y * -1;
    }
  }

  // --------------------------------------------------------------
  // Check if Ball is dying / dead to make it fade out / disappear
  // --------------------------------------------------------------

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
