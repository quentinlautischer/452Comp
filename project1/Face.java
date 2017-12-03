class Face extends SteeringBase
{
  Align align = null;

  public Face(Kinematic character, Kinematic target)
  {
    super(character, target);
    align = new Align(character, target);
  }

  public Steering getSteering()
  {
    //Calc real target?
    Kinematic explicitTarget = target;

    double direction_x = target.position.x - character.position.x;
    double direction_y = target.position.y - character.position.y;

    align.target = explicitTarget;
    align.target.orientation.set(Math.atan2(-direction_x, direction_y)*(180/Math.PI));

    return align.getSteering();
  }
}