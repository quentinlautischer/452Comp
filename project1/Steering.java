class Steering
{
  public Tuple<Double, Double> linear = new Tuple<Double, Double>(0.0, 0.0);
  public Orientation angular = new Orientation(0.0);

  public Steering(){}

  public Steering(Double linearx, Double lineary, Double orientation)
  {
    linear.x = linearx;
    linear.y = lineary;
    angular.set(orientation);
  }
}