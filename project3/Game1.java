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
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Game1 extends Application
{
    Long lastNanoTime;
    double updateTime;

    int canvas_height = Game1WorldData.rows*Game1WorldData.tile_size;
    int canvas_width = Game1WorldData.cols*Game1WorldData.tile_size;;

    Grid<ConnectFourGrid> grid = new Grid<ConnectFourGrid>();

    public static void main(String[] args)
    {
        launch(args);
    }

    private void renderGrid(Group root)
    {
        int idx = 0;
        for(Terrain t : grid)
        {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);
            Rectangle r = new Rectangle();
            r.setFill(Terrain.getColor(grid.get(col,row)));
            r.setX(col*tile_size);
            r.setY(row*tile_size);
            r.setWidth(tile_size);
            r.setHeight(tile_size);
            root.getChildren().add(r);
            idx++;
        }
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Game1 - ConnectFour" );

        try {
            int count = 0;
            for(Terrain t : grid)
            {   
                System.out.print(t);
                System.out.print(" ");
                if (count >= cols - 1)
                {
                    System.out.println("");
                    count = 0;
                }else
                {
                    count++;                    
                }

            }              
            System.out.println("Got Data");
        }
        catch (IOException e)
        {
            System.out.println("Could not read file");
        }

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( canvas_width, canvas_height );
        root.getChildren().add( canvas );

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        lastNanoTime = new Long( System.nanoTime() );

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                updateTime += elapsedTime;
                
                if (updateTime < 1.0)
                    return;
                else
                    updateTime = 0.0;
                // logic
                updatePathFind();

                // render
                System.out.println("Rendering");
                gc.clearRect(0, 0, 1200, 1200);
                gc.setFill( Color.WHITE );
                gc.fillRect(0, 0, 1200, 1200);
                gc.setStroke( Color.WHITE );
                renderGrid(root);
                renderPath(root);
            }
        }.start();

        theStage.show();
    }
}