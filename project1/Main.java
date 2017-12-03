import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Main extends Application
{
    int canvas_height = 800;
    int canvas_width = 800;

    Long lastNanoTime;
    ArrayList<Duck> ducks = new ArrayList<Duck>();
    ArrayList<GravityWell> gravityWells = new ArrayList<GravityWell>();
    Kinematic target = null;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Gravity Wells" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( canvas_width, canvas_height );
        root.getChildren().add( canvas );

        final ArrayList<String> keyboardInput = new ArrayList<String>();
        final ArrayList<MouseEvent> mouseInput = new ArrayList<MouseEvent>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !keyboardInput.contains(code) )
                            keyboardInput.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        keyboardInput.remove( code );
                    }
                });

        theScene.setOnMousePressed(
            new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent e)
                {
                    if (!mouseInput.contains(e))
                        mouseInput.add(e);
                }
            });

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        lastNanoTime = new Long( System.nanoTime() );

        Duck d = new Duck(500.0, 500.0);
        d.kinematic.orientation.set(180.0);
        target = d.kinematic;
        d.update(0.0);
        d.render(root);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // game logic

                if (mouseInput.size() > 0)
                {
                    MouseEvent e = mouseInput.get(0);

                    if (e.isPrimaryButtonDown())
                        ducks.add(new Duck(e.getX(), e.getY()));
                    else if (e.isSecondaryButtonDown())
                        gravityWells.add(new GravityWell(e.getX(), e.getY()));
                    mouseInput.remove(e);
                }

                for (Duck duck : ducks)
                {
                    duck.setTarget(target);
                    duck.update(elapsedTime);
                    duck.kinematic.position.x %= canvas_width;
                    if (duck.kinematic.position.x<0) duck.kinematic.position.x += canvas_width;
                    duck.kinematic.position.y %= canvas_height;
                    if (duck.kinematic.position.y<0) duck.kinematic.position.y += canvas_height;
                }

                // collision detection

                // render
                gc.clearRect(0, 0, 1200, 1200);
                gc.setFill( Color.GREEN );
                gc.fillRect(0, 0, 1200, 1200);
                gc.setStroke( Color.GREEN );
                for (Duck duck : ducks)
                {
                    duck.render(root);    
                }
            }
        }.start();

        theStage.show();
    }
}