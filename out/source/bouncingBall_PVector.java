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

int time = 0;

public void setup() {
  
  kinect = new Kinect(this);
  kinect.enableMirror(true);
  kinect.initDepth();

  bs = new BallSystem();
}

public void draw() { 
  background(0);
    //bs.addBall();
  bs.runSystem();

  PVector gravity = new PVector(0,0.01f);
  bs.applyForce(gravity);

  float sumX = 0;
  float sumY = 0; 
  float sumZ = 0;
  float totalPixels = 0;
  float avgX = 0;
  float avgY = 0;
  float avgZ = 0;

  // Add new Balls to system only after a certain amount of time
  if(millis() > time + 80) {
    bs.addBall();
    time = millis();
  }

  // --------------------------------------------------------
  // Attraction / Repulsion via kinect + spandex interaction
  // --------------------------------------------------------
  
  // // get raw depth values as array of integers
  // int[] depth = kinect.getRawDepth();  
 
  // for (int x = 0; x < kinect.width; x++) {
  //   for (int y = 0; y < kinect.height; y++) {
      
  //     int offset = x + y * kinect.width;
  //     int depthValue = depth[offset];
  //     float minThresh = 600; // 624
  //     float maxThresh = 745; // 725
    
  //     // Check if the current pixel is between the threshold values
  //     if (depthValue > minThresh && depthValue < maxThresh) {
  //       //print("Depth Value ", depthValue, "\n");

  //       // sum up x, y and z values to calculate average (in order to make balls follow center of hand)
  //       sumX += x;
  //       sumY += y;
  //       sumZ += depthValue;
  //       // println(depthValue);

  //       // Hold in a variable the amount of pixels that are within the threshold
  //       totalPixels++;
  //     }
  //   }
  //       // If more than 100 pixels are withing the threshold, (color them) follow them
  //         avgX = sumX / totalPixels;
  //         avgY = sumY / totalPixels;
  //         avgZ = Math.round(sumZ / (totalPixels * 10));
  //         //println("depth: " + avgZ);
  //         // PVector avgPosition = new PVector(avgX, avgY);
  //         //println("avgZ before: " + avgZ);
  //         if(avgZ > 65 && avgZ <= 72){
  //         	// println(avgZ);
  //         	bs.attract(avgX, avgY);
  //         }else if(avgZ > 72 && avgZ < 75){
  //           //println("AvgZ from else: "+avgZ);
  //         	bs.repulse(avgX, avgY);
  //         }
  // }
}

// --------------------------------------------------
// Attraction / Repulsion via mouse interaction
// --------------------------------------------------

public void mouseDragged(){
	// PVector mouse = new PVector(mouseX, mouseY);
	bs.attract(mouseX, mouseY);
}

public void mousePressed(){
	// PVector mouse = new PVector(mouseX,mouseY);
	bs.repulse(mouseX, mouseY);
}

class Ball {
  
  float ballSize = 15.0f; // Diameter of Ball
  int ballColor;
  int strokeColor;
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
    velocity = new PVector(0.0f, 0.0f);
    acceleration = new PVector(0.0f, 0.0f);

    ellipseMode(CENTER);
  }

  public void runBall() {
    displayBall();
    moveBall();
    checkBorders();
  }

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

  // --------------------------------
  // Add Attraction / Repulsion
  // --------------------------------

  public void attract(float x,float y) {
    PVector hand = new PVector(x, y);
    hand.sub(location);
    hand.setMag(0.3f);
    acceleration = hand;
  }

  public void repulse(float x, float y) {
    PVector hand = new PVector(x, y);
    // println("Repulse Coordinates from Ball Class : "+hand);
    hand.sub(location);
    hand.setMag(-2.0f);
    acceleration = hand;
  }
  
  // -------------------------------------------
  // Move Ball by adding to it's coordinates
  // -------------------------------------------

  public void moveBall() {
    // + operator does not work with vectors => function add() is needed
    location.add(velocity);
    velocity.add(acceleration);
    lifespan -= 0.5f;

    // limiting velocity
    velocity.limit(7.5f);

    // clear out acceleration
    acceleration.mult(0);
  }

  // ---------------------------------------------------------
  // Check if Ball touches borders, if yes change direction
  // ---------------------------------------------------------

  public void checkBorders() {
    if (location.x > width || location.x < 0 ) {
      velocity.x = velocity.x * -1; 
    } else if (location.y > height || location.y < 0) {
      velocity.y = velocity.y * -1;
    }
  }

  // --------------------------------------------------------------
  // Check if Ball is dying / dead to make it fade out / disappear
  // --------------------------------------------------------------

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

  // Apply force to all balls
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

  public void repulse(float x,float y) {
    // println("Repulse Coordinates from BS: "+x, y);
    for (Ball b : balls) {
      b.repulse(x, y);
    }
  }

  public void runSystem() {
    for (int i = balls.size()-1; i >= 0; i--) {
      Ball b = balls.get(i);
      b.runBall();

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
