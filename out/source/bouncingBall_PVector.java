import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.openkinect.freenect.*; 
import org.openkinect.processing.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bouncingBall_PVector extends PApplet {




Kinect kinect;
BallSystem bs;

// int k = 0;
float minThresh = 650;
float maxThresh = 725;
int time = 0;

public void setup() {
  
  kinect = new Kinect(this);
  kinect.enableMirror(true);
  kinect.initDepth();

  bs = new BallSystem();
}

public void draw() { 
  background(79,98,114);

  PVector gravity = new PVector(0,0.01f);
  bs.applyForce(gravity);


  // // if (mousePressed) {
  // //   bs.repelledByMouse();
  // // }

  if(millis() > time + 80) {
    bs.addBall();
    time = millis();
  }
  //bs.addBall();
  bs.runSystem();

  float sumX = 0;
  float sumY = 0; 
  float totalPixels = 0;
  // float allPixels = 0;
  
  // get raw depth values as array of integers
  int[] depth = kinect.getRawDepth();  
 
  for (int x = 0; x < kinect.width; x++) {
    for (int y = 0; y < kinect.height; y++) {
      int offset = x + y * kinect.width;
      int d = depth[offset];
      // Check if the current pixel is between the threshold values
      if (d > minThresh && d < maxThresh) {
        //print("Depth Value ", d, "\n");

        // sum up x and y values to calculate average (in order to make balls follow center of hand)
        sumX += x;
        sumY += y;

        // Hold in a variable the amount of pixels that are within the threshold
        totalPixels++;
        // If more than 100 pixels are withing the threshold, (color them) follow them
        if(totalPixels > 100) {
          float avgX = sumX / totalPixels;
          float avgY = sumY / totalPixels;
          // acceleration towards hand
          bs.attract(avgX, avgY);
          bs.handBall(avgX, avgY);

          // Comment this out if its too laggy
          // print(k + 1, "\n");
          //k = k + 1;
        } 
       } 
      // if (d > 550 && d < 600) {
      //   allPixels++;
      //   if(allPixels > 100) {
      //     bs.repulse(x, y);
      //     //k = k + 1;
      //   }
      // }
    }
  }
}

class Ball {
  
  // --------------------------------
  // Position and Size of Ball
  // --------------------------------

  float ballSize = 15.0f;   // Diameter of Ball

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
      //stroke(strokeColor);
      fill(ballColor, lifespan);
      //fill(ballColor);
      ellipse(location.x, location.y, ballSize, ballSize);
    }

  // --------------------------------
  // Add Gravity
  // --------------------------------

  public void applyForce(PVector force) {
    acceleration.add(force);
  }

  public void attract(float x,float y) {
    PVector mouse = new PVector(x, y);
    mouse.sub(location);
    mouse.setMag(0.3f);
    acceleration = mouse;
  }

  public void repulse(int x,int y) {
    PVector mouse = new PVector(x, y);
    mouse.sub(location);
    mouse.setMag(-0.2f);
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
    if(balls.size() < 50) {
      balls.add(new Ball());
    }
  }

  public void handBall(float x, float y) {
    balls.add(new Ball());
  }

  // Apply force to all particles
  public void applyForce(PVector f) {
    for (Ball b : balls) {
      b.applyForce(f);
    }
  }

  public void attract(float x,float y) {
    // print(x, y);
    for (Ball b : balls) {
      b.attract(x, y);
    }
  }

  // void repulse(int x,int y) {
  //   // print(x, y);
  //   for (Ball b : balls) {
  //     b.repulse(x, y);
  //   }
  // }

  public void runSystem() {
    for (int i = balls.size()-1; i >= 0; i--) {
      Ball b = balls.get(i);
      b.runBall();
      
      // if (b.isDying()) {
      //     b.acceleration.x -= 3.0;
      //     b.acceleration.y -= 3.0;
      // }

      if (b.isDead()) {
        balls.remove(i);
      }
    }
  }
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bouncingBall_PVector" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
