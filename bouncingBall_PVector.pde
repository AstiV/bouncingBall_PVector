BallSystem bs;

public void settings() {
  size(650, 750);
}

void setup() {
  bs = new BallSystem();
}

void draw() { 
  background(79,98,114);

  PVector gravity = new PVector(0,0.01);
  bs.applyForce(gravity);

  if (mousePressed) {
    // acceleration towards mouse
    bs.followMouse();
  }

  bs.addBall();
  bs.runSystem();
}
