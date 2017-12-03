import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.util.Random;

class GravityWell
{
  Kinematic kinematic = new Kinematic();

  //Render
  int radius = 10;
  Circle circle = new Circle();
  Color color = Color.rgb(255, 211, 0);

  public GravityWell(Double x, Double y)
  {
      kinematic.position.x = x;
      kinematic.position.y = y;
      circle.setRadius(radius);
      circle.setFill(color);
  }
}