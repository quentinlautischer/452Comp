class Arrive extends SteeringBase
{
    public Arrive(Kinematic character, Kinematic target)
    {
        super(character, target);
    }

    private double normalize(double x, double y)
    {
        return Math.pow(Math.pow(x,2)+Math.pow(y, 2), 0.5);
    }

    public Steering getSteering()
    {
        double targetRadius = 100.0;
        double slowRadius = 150.0;
        double timeToTarget = 1.0;
        double maxAcceleration = 20.0;
        double maxSpeed = 50.0;

        Steering steering = new Steering();

        double directionx = target.position.x - character.position.x;
        double directiony = target.position.y - character.position.y;
        double distance = Math.pow((directionx * directionx) + (directiony * directiony), 0.5);

        if (distance < targetRadius)
            return null;

        double targetSpeed = 0.0;
        if (distance > slowRadius)
            targetSpeed = maxSpeed;
        else
            targetSpeed = maxSpeed * (distance/slowRadius);

        double targetVelocityX = directionx;
        double targetVelocityY = directiony;


        targetVelocityX = normalize(targetVelocityX, targetVelocityX);
        targetVelocityY = normalize(targetVelocityY, targetVelocityY);

        targetVelocityX *= targetSpeed;
        targetVelocityY *= targetSpeed;

        steering.linear.x = targetVelocityX - character.velocity.x;
        steering.linear.y = targetVelocityY - character.velocity.y;

        steering.linear.x /= timeToTarget;
        steering.linear.y /= timeToTarget;

        Face face = new Face(character, target);
        Steering s2 = face.getSteering();
        if (s2 != null)
            steering.angular = s2.angular;
        return steering;

    }

}
    