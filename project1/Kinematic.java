class Kinematic
{
  public Tuple<Double, Double> position = new Tuple<Double, Double>(0.0, 0.0);
  public Tuple<Double, Double> velocity = new Tuple<Double, Double>(0.0, 0.0);
  public Orientation orientation = new Orientation(0.0);
  public Orientation rotation = new Orientation(0.0);

  public Kinematic(){
    velocity.x = 0.0;
    velocity.y = 0.0;
  }

  public double normalize(double x, double y)
  {
      double z = Math.pow(((x*x) + (y*y)),0.5);
      return x/z;
  }

  public void update(Steering steering, double maxSpeed, double time)
  {
    if (steering == null)
      return;

    position.x += velocity.x*time;
    position.y += velocity.y*time;
    orientation.set(orientation.get() + rotation.get()*time);

    velocity.x += steering.linear.x * time;
    velocity.y += steering.linear.y * time;

    rotation.set(rotation.get() +(steering.angular.get()*time));    

    if ( Math.abs(velocity.x) > maxSpeed | Math.abs(velocity.y) > maxSpeed)
    {
      double velx = velocity.x;
      double vely = velocity.y;
      velocity.x = normalize(velx, maxSpeed-Math.abs(velx));
      velocity.y = normalize(vely, maxSpeed-Math.abs(vely));
      velocity.x *= maxSpeed;
      velocity.y *= maxSpeed;
    }
  }
}