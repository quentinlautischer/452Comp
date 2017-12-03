import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.util.Random;

public class Duck
{
    //Kinematics
    Kinematic kinematic = new Kinematic();
    Kinematic target = new Kinematic();
    Wander wander = new Wander(kinematic, target);
    Arrive arrive = new Arrive(kinematic, target);
    Align align = new Align(kinematic, target);
    Face face = new Face(kinematic, target);

    //Render
    int radius = 10;
    Circle circle = new Circle();
    TrianglePolygon triangle = new TrianglePolygon(0,0,0,0,0,0);
    Color color = Color.rgb(255, 211, 0);
    Random random = new Random(123);

    public Duck(Double x, Double y)
    {
        kinematic.position.x = x;
        kinematic.position.y = y;
        circle.setRadius(radius);
        circle.setFill(color);
        triangle.setFill(color);
    }

    public void setTarget(Kinematic target)
    {
        this.target = target;
        wander.setTarget(this.target);
        face.setTarget(this.target);
        arrive.setTarget(this.target);
        align.setTarget(this.target);
    }

    public void update(double elapsedTime)
    {
        kinematic.update(arrive.getSteering(), 25.0, elapsedTime);

        circle.setCenterX(kinematic.position.x);
        circle.setCenterY(kinematic.position.y);
        triangle.setPoint1(kinematic.position.x, kinematic.position.y+(radius*1.5));
        triangle.setPoint2(kinematic.position.x+radius, kinematic.position.y);
        triangle.setPoint3(kinematic.position.x-radius, kinematic.position.y);
        triangle.rotate(kinematic.orientation.get(), kinematic.position.x, kinematic.position.y);
    }

    public void render(Group root)
    {
        if (!root.getChildren().contains(circle))
            root.getChildren().add(circle);
        if (!root.getChildren().contains(triangle))
            root.getChildren().add(triangle);
    }
}