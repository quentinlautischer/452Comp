import java.util.Random;

class Wander extends SteeringBase
{
  double wanderOffset = 100.0;
  double wanderRadius = 100.0;
  double wanderRate = 0.05;
  Orientation wanderOrientation = new Orientation(0.0);
  double maxAccelaration = 1.0;
  double maxSpeed = 50.0;
  double maxRotation = 1.0;
  Random random = new Random();

  Face face = null;

  public Wander(Kinematic character, Kinematic target)
  {
    super(character, target);
    face = new Face(character, target);
  }

  private double randomBinomial()
  {
    double rand =  random.nextDouble() - random.nextDouble();
    return rand;
  }

  public Steering getSteering()
  {
    wanderOrientation.set(wanderOrientation.get() + randomBinomial()*wanderRate);
    Orientation targetOrientation = new Orientation(wanderOrientation.get() + character.orientation.get());
    target.position.x = character.position.x + wanderOffset*character.orientation.getVectorX();
    target.position.y = character.position.y + wanderOffset*character.orientation.getVectorY();

    target.position.x += wanderRadius * targetOrientation.getVectorX();
    target.position.y += wanderRadius * targetOrientation.getVectorY();

    Steering steering = face.getSteering();

    steering.linear.x = maxAccelaration*character.orientation.getVectorX();
    steering.linear.y = maxAccelaration*character.orientation.getVectorY();

    steering.linear.x = character.orientation.getVectorX();
    steering.linear.y = character.orientation.getVectorY();
    steering.linear.x *= maxSpeed;
    steering.linear.y *= maxSpeed;


    double change = randomBinomial();
    steering.angular.set(change*maxRotation);  
    
    System.out.println("Steering Angular: " + steering.angular.get());
    return steering;
  }
}