Ball b;

void setup() {
  size(650, 750);
  b = new Ball();
}

void draw() { 
  
  // Draw stuff
  background(79,98,114);
  b.displayBall();
  b.moveBall();
  b.checkBorders();
}
