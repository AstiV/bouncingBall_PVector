class BallSystem {
  ArrayList<Ball> balls;

  // constructor
  BallSystem() {
    balls = new ArrayList<Ball>();
  }

  void addBall() {
    if(balls.size() < 50) {
      balls.add(new Ball());
    }
  }

  // Apply force to all balls
  void applyForce(PVector f) {
    for (Ball b : balls) {
      b.applyForce(f);
    }
  }

  void attract(float x,float y) {
    // print(x, y);
    for (Ball b : balls) {
      b.attract(x, y);
    }
  }

  void repulse(float x,float y) {
    // println("Repulse Coordinates from BS: "+x, y);
    for (Ball b : balls) {
      b.repulse(x, y);
    }
  }

  void runSystem() {
    for (int i = balls.size()-1; i >= 0; i--) {
      Ball b = balls.get(i);
      b.runBall();

      if (b.isDead()) {
        balls.remove(i);
      }
    }
  }
}