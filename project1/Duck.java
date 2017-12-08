import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

public class Duck
{
    //Kinematics
    Kinematic kinematic = new Kinematic();
    Kinematic target = new Kinematic();
    
    SteeringBase steering = new SteeringBase(kinematic, target);
    Wander wander = new Wander(1, kinematic, target);
    Arrive arrive = new Arrive(kinematic, target);
    Align align = new Align(kinematic, target);
    Face face = new Face(kinematic, target);

    //Render
    int radius = 10;
    Image duck_img = new Image("res/duck.png");
    ImageView duck_iv = new ImageView();
    Rectangle2D duck_r = new Rectangle2D(0, 0, 32, 32);
    Random random = new Random();

    public Duck(long moveSeed, Double x, Double y)
    {
        random.setSeed(moveSeed);
        Wander wander = new Wander(moveSeed, kinematic, target);

        kinematic.position.x = x;
        kinematic.position.y = y;
        
        duck_iv.setFitWidth(radius*4);
        duck_iv.setFitHeight(radius*4);
        duck_iv.setX(x);
        duck_iv.setY(y);
        
        duck_iv.setImage(duck_img);
        duck_iv.setViewport(duck_r);
        duck_iv.setFitWidth(radius*4);
        duck_iv.setFitHeight(radius*4);
        duck_iv.setSmooth(true);
        duck_iv.setCache(true);
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
        kinematic.update(wander.getSteering(), 50.0, elapsedTime);

        duck_iv.setX(kinematic.position.x);
        duck_iv.setY(kinematic.position.y);
        duck_iv.setRotate(kinematic.orientation.get());
    }

    public void render(Group root)
    {
        if (!root.getChildren().contains(duck_iv))
            root.getChildren().add(duck_iv);
    }
}