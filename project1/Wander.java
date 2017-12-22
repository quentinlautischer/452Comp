import java.util.Random;

class Wander extends SteeringBase
{
  double wanderOffset = 0.01;
  double wanderRadius = 400.0;
  double wanderRate = 10.0;
  Orientation wanderOrientation = new Orientation(180.0);
  double maxAccelaration = 5000.0;
  double maxSpeed = 20.0;
  double maxRotation = 270.0;
  Random random = null;

  Face face = null;

  public Wander(Random rand, Kinematic character, Kinematic target)
  {
    super(character, target);
    face = new Face(character, target);
    this.random = rand;
  }
  
  public void setTarget(Kinematic target)
  {
    this.target = target;
    face.setTarget(target);
  }

  private double randomBinomial()
  {
    double rand =  random.nextDouble() - random.nextDouble();
    return rand;
  }

  public Steering getSteering()
  {
    // wanderOrientation.set(wanderOrientation.get() + randomBinomial()*wanderRate);
    // // target.orientation.set(wanderOrientation.get() + character.orientation.get());
    // target.position.x += randomBinomial()*wanderOffset;
    // target.position.y += randomBinomial()*wanderOffset;
    // target.position.x %= WorldData.getWidth();
    // if (target.position.x<0) target.position.x += WorldData.getWidth();
    // target.position.y %= WorldData.getHeight();
    // if (target.position.y<0) target.position.y += WorldData.getHeight();

    // target.position.x += wanderRadius * target.orientation.getVectorX();
    // target.position.y += wanderRadius * target.orientation.getVectorY();
    character.orientation.set(character.orientation.get() + randomBinomial()*wanderRate);
    // System.out.println("Target Pos: " + target.position.x + ", " + target.position.y);

    // Steering steering = face.getSteering();

    // if (steering == null)
    Steering steering = new Steering();
    // System.out.println("Orientation Vector: " + character.orientation.getVectorX() + ", " + character.orientation.getVectorY());
    steering.linear.x = maxAccelaration*character.orientation.getVectorX();
    steering.linear.y = maxAccelaration*character.orientation.getVectorY();
    // System.out.println("linear Steer: " + steering.linear.x + ", " + steering.linear.y);

    // steering.linear.x = character.orientation.getVectorX();
    // steering.linear.y = character.orientation.getVectorY();
    // steering.linear.x *= maxSpeed;
    // steering.linear.y *= maxSpeed;


    // double change = randomBinomial();
    // steering.angular.set(change*maxRotation);  
    
    // System.out.println("Steering Angular: " + steering.angular.get());
    return steering;
  }
}