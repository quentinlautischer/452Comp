class Flee extends SteeringBase
{
    Arrive arrive = null;
    public Flee(Kinematic character, Kinematic target)
    {
        super(character, target);
        arrive = new Arrive(character, target);
    }

    public Steering getSteering()
    {
        Steering steering = arrive.getSteering();

        steering.linear.x = -steering.linear.x;
        steering.linear.y = -steering.linear.y;

        Face face = new Face(character, target);
        Steering s2 = face.getSteering();
        if (s2 != null)
            steering.angular = s2.angular;
        return steering;

    }

}
    