import java.lang.Math;

class Align extends SteeringBase
{
  double maxAngularAcceleration = 1.0;
  double maxRotation = 270.0;
  double targetRadius = 1.0;
  double slowRadius = 2.0;
  double timeToTarget = 1.0;

  public Align(Kinematic character, Kinematic target)
  {
    super(character, target);
  }

  public Steering getSteering()
  {
    Steering steering = new Steering();

    double rotation = target.orientation.get() - character.orientation.get();

    double rotationSize = Math.abs(rotation);

    if (rotationSize < targetRadius)
      return null;

    double targetRotation = maxRotation * (rotationSize / slowRadius);
    if (rotationSize > slowRadius)
      targetRotation = maxRotation;

    targetRotation *= rotation / rotationSize;

    steering.angular.set(targetRotation - character.rotation.get());
    steering.angular.set(steering.angular.get() / timeToTarget);

    double angularAcceleration = Math.abs(steering.angular.get());
    if (angularAcceleration > maxAngularAcceleration)
    {
      steering.angular.set(steering.angular.get() / angularAcceleration);
      steering.angular.set(steering.angular.get() * maxAngularAcceleration);
    }

    steering.linear.x = 0.0;
    steering.linear.y = 0.0;

    return steering;
  }
}