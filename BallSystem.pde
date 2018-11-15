class BallSystem {
  ArrayList<Ball> balls;

  // constructor
  BallSystem() {
    balls = new ArrayList<Ball>();
  }

  void addBall() {
    balls.add(new Ball());
  }

  // Apply force to all particles
  void applyForce(PVector f) {
    for (Ball b : balls) {
      b.applyForce(f);
    }
  }

  void followMouse() {
    for (Ball b : balls) {
      b.followMouse();
    }
  }

  void runSystem() {
    for (int i = balls.size()-1; i >= 0; i--) {
      Ball b = balls.get(i);
      b.runBall();
      
      if (b.isDying()) {
          b.velocity.x -= 3.0;
          b.velocity.y -= 3.0;
      }

      if (b.isDead()) {
        balls.remove(i);
      }
    }
  }
}