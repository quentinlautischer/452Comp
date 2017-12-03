class Kinematic
{
  public Tuple<Double, Double> position = new Tuple<Double, Double>(0.0, 0.0);
  public Tuple<Double, Double> velocity = new Tuple<Double, Double>(0.0, 0.0);
  public Orientation orientation = new Orientation(0.0);
  public Orientation rotation = new Orientation(0.0);

  public Kinematic(){}

  public double normalize(double x, double y)
  {
      double max = Math.pow((x * x + y * y), 0.5);
      return x/max;
  }

  public void update(Steering steering, double maxSpeed, double time)
  {
    if (steering == null)
      return;

    position.x += velocity.x*time;
    position.y += velocity.y*time;
    orientation.set(orientation.get() + rotation.get()*time);

    System.out.println("X: " + steering.linear.x + " Y: " + steering.linear.y);

    velocity.x += steering.linear.x * time;
    velocity.y += steering.linear.y * time;

    if ( Math.abs(velocity.x) > maxSpeed || Math.abs(velocity.y) > maxSpeed)
    {
      velocity.x = normalize(velocity.x, velocity.x);
      velocity.y = normalize(velocity.y, velocity.y);

      velocity.x *= maxSpeed;
      velocity.y *= maxSpeed;
    }

    System.out.println("Vel X: " + velocity.x + " Vel Y: " + velocity.y);

    rotation.set(steering.angular.get() * time*10000000);
    
  }
}