import org.openkinect.freenect.*;
import org.openkinect.processing.*;

Kinect kinect;
BallSystem bs;

// int k = 0;
float minThresh = 650;
float maxThresh = 725;
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

  PVector gravity = new PVector(0,0.01);
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

