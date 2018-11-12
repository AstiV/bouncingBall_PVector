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

Ball b;

public void setup() {
  
  b = new Ball();
}

public void draw() { 
  
  // Draw stuff
  background(79,98,114);
  b.displayBall();
  b.moveBall();
  b.checkBorders();
}
class Ball {
  
  // --------------------------------
  // Position and Size of Ball
  // --------------------------------

  float ballSize = 50.0f;   // Diameter of Ball

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

  // Color
  int ballColor;
  int strokeColor;

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
    velocity = new PVector(3.0f, 2.3f);

    ellipseMode(CENTER);
  
  }

  // --------------------------------
  // Display Ball
  // --------------------------------

    public void displayBall(){
      stroke(strokeColor);
      fill(ballColor);
      ellipse(location.x, location.y, ballSize, ballSize);
    }
  
  // -------------------------------------------
  // Move circle by adding to it's coordinates
  // -------------------------------------------

  public void moveBall() {
    // ballX = ballX + xSpeed;
    // ballY = ballY + ySpeed;
    // => translates into the following PVectors
    // + operator does not work with vectors => function is needed
    location.add(velocity);
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
}
  public void settings() {  size(650, 750); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bouncingBall_PVector" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
