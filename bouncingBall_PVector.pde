BallSystem bs;

public void settings() {
  size(650, 750);
}

void setup() {
  bs = new BallSystem();
}

void draw() { 
  background(79,98,114);
  bs.addBall();
  bs.runSystem();
}
