import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bouncingBall_PVector extends PApplet {

BallSystem bs;

public void settings() {
  size(650, 750);
}

public void setup() {
  bs = new BallSystem();
}

public void draw() { 
  background(79,98,114);

  PVector gravity = new PVector(0,0.01f);
  bs.applyForce(gravity);

  if (mousePressed) {
    // acceleration towards mouse
    bs.followMouse();
  }

  bs.addBall();
  bs.runSystem();
}
class Ball {
  
  // --------------------------------
  // Position and Size of Ball
  // --------------------------------

  float ballSize = 30.0f;   // Diameter of Ball

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
  int ballColor;
  int strokeColor;

  // --------------------------------
  // Constructor
  // --------------------------------

  Ball() {
    // ballColor = color(173,252,249);

    // random color
    ballColor = color(random(100), 0, random(250));
    strokeColor = ballColor;
    
  
    // Initially position ball randomly

    // ballX = random(width);
    // ballY = random(height);
    location = new PVector(random(width), random(height));
    velocity = new PVector(0.0f, 0.0f);
    acceleration = new PVector(0.0f, 0.0f);

    ellipseMode(CENTER);
  
  }

  public void runBall() {
    displayBall();
    moveBall();
    checkBorders();
  }


  // --------------------------------
  // Display Ball
  // --------------------------------

    public void displayBall(){
      stroke(strokeColor, lifespan);
      fill(ballColor, lifespan);
      ellipse(location.x, location.y, ballSize, ballSize);
    }

  // --------------------------------
  // Add Gravity
  // --------------------------------

  public void applyForce(PVector force) {
    acceleration.add(force);
  }

  public void followMouse() {
    PVector mouse = new PVector(mouseX, mouseY);
    mouse.sub(location);
    mouse.setMag(0.1f);
    acceleration = mouse;
  }
  
  // -------------------------------------------
  // Move Ball by adding to it's coordinates
  // -------------------------------------------

  public void moveBall() {
    // ballX = ballX + xSpeed;
    // ballY = ballY + ySpeed;
    // => translates into the following PVectors
    // + operator does not work with vectors => function is needed
    location.add(velocity);
    velocity.add(acceleration);
    lifespan -= 0.5f;

    // random acceleration (set acc and vel values in constructor to 0!)
    //acceleration = PVector.random2D();

    // limiting velocity
    velocity.limit(7.5f);

    // clear out acceleration
    acceleration.mult(0);
  }

  // ---------------------------------------------------------
  // Check if Ball touches borders, if yes change direction
  // ---------------------------------------------------------

  public void checkBorders() {
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

  public boolean isDying() {
    if (lifespan < 80.0f) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isDead() {
    if (lifespan < 0.0f) {
      return true;
    } else {
      return false;
    }
  }
}
class BallSystem {
  ArrayList<Ball> balls;

  // constructor
  BallSystem() {
    balls = new ArrayList<Ball>();
  }

  public void addBall() {
    balls.add(new Ball());
  }

  // Apply force to all particles
  public void applyForce(PVector f) {
    for (Ball b : balls) {
      b.applyForce(f);
    }
  }

  public void followMouse() {
    for (Ball b : balls) {
      b.followMouse();
    }
  }

  public void runSystem() {
    for (int i = balls.size()-1; i >= 0; i--) {
      Ball b = balls.get(i);
      b.runBall();
      
      if (b.isDying()) {
          b.velocity.x -= 3.0f;
          b.velocity.y -= 3.0f;
      }

      if (b.isDead()) {
        balls.remove(i);
      }
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bouncingBall_PVector" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
