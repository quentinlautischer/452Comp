class Arrive extends SteeringBase
{
    double targetRadius = 40.0;
    double slowRadius = 75.0;
    double timeToTarget = 1.0;
    double maxAcceleration = 200.0;
    double maxSpeed = 50.0;

    public Arrive(Kinematic character, Kinematic target)
    {
        super(character, target);
    }

    private double normalize(double x, double y)
    {
        double z =  Math.pow(Math.pow(x,2)+Math.pow(y, 2), 0.5);
        return x/z;
    }

    public Steering getSteering()
    {
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

        double velx = directionx;
        double vely = directiony;

        System.out.println("Steering X,Y " + velx + " " + vely);

        targetVelocityX = normalize(velx, maxSpeed-Math.abs(velx));
        targetVelocityY = normalize(vely, maxSpeed-Math.abs(vely));

        System.out.println("Steering2 X,Y " + targetVelocityX + " " + targetVelocityY);

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
    