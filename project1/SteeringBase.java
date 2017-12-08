class SteeringBase implements ISteering
{
  public Kinematic character = null;
  public Kinematic target = null;

  public SteeringBase(Kinematic character, Kinematic target)
  {
    this.character = character;
    this.target = target;
  }

  public void setCharacter(Kinematic character)
  {
    this.character = character;
  }

  public void setTarget(Kinematic target)
  {
    this.target = target;
  }

  public Steering getSteering()
  {
    return new Steering(0.0, 0.0, 0.0);
  }
}