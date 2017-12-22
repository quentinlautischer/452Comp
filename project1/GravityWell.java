import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.*;

class GravityWell
{
  Kinematic kinematic = new Kinematic();

  int count = 0;

  //Render
  int radius = 10;
  int max_radius = 100;

  double timeToDetonate = 3.0;

  Circle circle = new Circle();
  Color color = Color.rgb(200, 0, 200);
  double spawnTime = 0;
  double lastRecordedTime = 0;
  double offset = 2*radius;

  enum Status {SPAWNED, DETONATING, DETONATED, DONE, DEAD};
  Status status = Status.SPAWNED;

  CopyOnWriteArrayList<Duck> listOfDucks = new CopyOnWriteArrayList<Duck>();

  public GravityWell(double spawnTime, Double x, Double y, CopyOnWriteArrayList<Duck> listOfDucks)
  {
      this.spawnTime = spawnTime;
      this.listOfDucks = listOfDucks;

      kinematic.position.x = x;
      kinematic.position.y = y; 

      circle.setCenterX(kinematic.position.x+offset);
      circle.setCenterY(kinematic.position.y+offset);
      circle.setRadius(radius);
      circle.setFill(color);
  }

  public Kinematic getKinematic(){
    return kinematic;
  }

  public boolean isDetonating(){
    return (status == Status.DETONATING);
  }

  public boolean isDead(){
    return (status == Status.DEAD);
  }

  private boolean pointWithinRadius(double x, double y){
    x = Math.abs(kinematic.position.x - x);
    y = Math.abs(kinematic.position.y - y);
    double dist = Math.pow(Math.pow(x,2) + Math.pow(y, 2), 0.5);
    return (dist < (double) radius);
  }

  public void eatDucks(){
    for(Duck d : listOfDucks){
      if (pointWithinRadius(d.getX(), d.getY())){
        d.die();
        listOfDucks.remove(d);
        count++;
      }
    }
  }

  public int getCount(){
    return count;
  }

  public void update(double currentTime){
    lastRecordedTime = currentTime;
    
    if ((status == Status.SPAWNED) & ((spawnTime + timeToDetonate) < lastRecordedTime)){
      status = Status.DETONATING;
      color = Color.rgb(0,0,0);
    }
    if((status == Status.DETONATING) &  ((spawnTime + (timeToDetonate*1.3)) < lastRecordedTime)){
      status = Status.DETONATED;
      color = Color.rgb(200,0,200);
    }


    if (status == Status.DETONATED){
      radius += 1;
      eatDucks();
      if (radius > max_radius){
        status = Status.DONE;
      }
    } else if (status == Status.DONE) {
      radius -= 1;
    }
    
    if (radius < 0){
      status = Status.DEAD;
    }
  }

  public void render(Group root)
  {
    circle.setRadius(radius);
    circle.setFill(color);
    if (!root.getChildren().contains(circle))
      root.getChildren().add(circle);
  }
}