import org.openkinect.freenect.*;
import org.openkinect.processing.*;

Kinect kinect;
BallSystem bs;

int time = 0;

void setup() {
  size(640, 480);
  kinect = new Kinect(this);
  kinect.enableMirror(true);
  kinect.initDepth();

  bs = new BallSystem();
}

void draw() { 
  background(0);
  bs.runSystem();

  PVector gravity = new PVector(0,0.01);
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

void mouseDragged(){
	// PVector mouse = new PVector(mouseX, mouseY);
	bs.attract(mouseX, mouseY);
}

void mousePressed(){
	// PVector mouse = new PVector(mouseX,mouseY);
	bs.repulse(mouseX, mouseY);
}

