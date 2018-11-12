class BallSystem {
  ArrayList<Ball> balls;

  // constructor
  BallSystem() {
    balls = new ArrayList<Ball>();
  }

  void addBall() {
    balls.add( new Ball());
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