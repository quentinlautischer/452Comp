package sample;

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
import java.util.concurrent.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import sample.AntFarm;
import sample.Grid;


public class Game2 extends Application
{
    Long lastNanoTime;
    double updateTime;
    Random random = new Random();
    
    int starting_ants_ = 1;
    double foodSpawnChance = 0.1;
    double waterSpawnChance = 0.1;
    double poisonSpawnChance = 0.01;

    int canvas_height = 800;
    int canvas_width = 800;
    int rows = Game2WorldData.rows;
    int cols = Game2WorldData.cols;
    int tile_size = Game2WorldData.tile_size;

    Grid<AntFarm> grid = new Grid<AntFarm>(cols, rows);
    Grid<Rectangle> gridRender = null;
    Grid<ImageView> gridIvRender = null;
    CopyOnWriteArrayList<Ant> ants = new CopyOnWriteArrayList<Ant>();

    Image food_img = new Image("food.png");
    Image water_img = new Image("water.png");
    Image poison_img = new Image("poison.png");
    Image blank_img = new Image("blank.png");
    ImageView food_iv = new ImageView();
    ImageView water_iv = new ImageView();
    ImageView poison_iv = new ImageView();
    ImageView blank_iv = new ImageView();

    Rectangle2D food_r = new Rectangle2D(0, 0, 1117, 771);
    Rectangle2D water_r = new Rectangle2D(0, 0, 340, 340);
    Rectangle2D poison_r = new Rectangle2D(0, 0, 200, 200);

    public static void main(String[] args)
    {
        if (args.length < 1)
            throw new RuntimeException("Not enough args");
        launch(args);
    }


    private void initGridRender(Group root)
    {
        gridRender = new Grid<Rectangle>(cols, rows);
        gridIvRender = new Grid<ImageView>(cols, rows);

        int idx = 0;
        for(AntFarm t : grid)
        {   
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);
            
            Rectangle r = new Rectangle();
            r.setFill(AntFarm.getColor(AntFarm.EMPTY));
            r.setX(col*tile_size);
            r.setY(row*tile_size);
            r.setWidth(tile_size);
            r.setHeight(tile_size);
            gridRender.add(r);

            ImageView iv = new ImageView();
            iv.setFitWidth(tile_size);
            iv.setFitHeight(tile_size);
            iv.setX(col*tile_size);
            iv.setY(row*tile_size);
            gridIvRender.add(iv);

            if (!root.getChildren().contains(r))
                root.getChildren().add(r);

            if (!root.getChildren().contains(iv))
                root.getChildren().add(iv);
        }

        food_iv.setImage(food_img);
        water_iv.setImage(water_img);
        poison_iv.setImage(poison_img);
        blank_iv.setImage(blank_img);

        food_iv.setViewport(food_r);
        water_iv.setViewport(water_r);
        poison_iv.setViewport(poison_r);

        food_iv.setFitWidth(Game2WorldData.tile_size);
        water_iv.setFitWidth(Game2WorldData.tile_size);
        poison_iv.setFitWidth(Game2WorldData.tile_size);
        blank_iv.setFitWidth(Game2WorldData.tile_size);

        food_iv.setFitHeight(Game2WorldData.tile_size);
        water_iv.setFitHeight(Game2WorldData.tile_size);
        poison_iv.setFitHeight(Game2WorldData.tile_size);
        blank_iv.setFitHeight(Game2WorldData.tile_size);

        food_iv.setSmooth(true);
        water_iv.setSmooth(true);
        poison_iv.setSmooth(true);
        food_iv.setCache(true);
        water_iv.setCache(true);
        poison_iv.setCache(true);
    }

    private void renderGrid(Group root)
    {
        if (gridRender == null)
            initGridRender(root);

        int idx = 0;
        for(AntFarm t : grid)
        {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);
            Rectangle r = gridRender.get(idx);
            r.setFill(AntFarm.getColor(t));
            r.setX(col*tile_size);
            r.setY(row*tile_size);
            r.setWidth(tile_size);
            r.setHeight(tile_size);
            if (!root.getChildren().contains(r))
                root.getChildren().add(r);
            idx++;
        }

        idx = 0;
        for(AntFarm t : grid)
        {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);
            ImageView iv = gridIvRender.get(idx);
            
            switch(t)
            {
                case FOOD:
                    iv.setImage(food_iv.getImage());
                    iv.setViewport(food_iv.getViewport());
                    break;
                case WATER:
                    iv.setImage(water_iv.getImage());
                    iv.setViewport(water_iv.getViewport());
                    break;
                case POISON:
                    iv.setImage(poison_iv.getImage());
                    iv.setViewport(poison_iv.getViewport());
                    break;
                default:
                    iv.setImage(blank_iv.getImage());
                    iv.setViewport(blank_iv.getViewport());
                    break;
            }

            iv.setX(col*tile_size);
            iv.setY(row*tile_size);
            iv.setFitWidth(tile_size);
            iv.setFitHeight(tile_size);
            if (!root.getChildren().contains(iv))
                root.getChildren().add(iv);
            idx++;
        }
    }

    private void renderAnts(Group root)
    {
        for(Ant ant : ants)
            ant.render(root);
    }

    private void spawnFood()
    {
        if (random.nextDouble()>foodSpawnChance)
            return;

        int idx = random.nextInt((rows*cols)-1);
        idx++; //Avoiding home
        grid.replace(idx, AntFarm.FOOD);
    }

    private void spawnWater()
    {
        if (random.nextDouble()>waterSpawnChance)
            return;

        int idx = random.nextInt((rows*cols)-1);
        idx++; //Avoiding home
        grid.replace(idx, AntFarm.WATER);
    }

    private void spawnPoison()
    {
        if (random.nextDouble()>poisonSpawnChance)
            return;

        int idx = random.nextInt((rows*cols)-1);
        idx++; //Avoiding home
        grid.replace(idx, AntFarm.POISON);
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Game2 - AntFarm" );
        for(int i=0;i<starting_ants_;i++)
            ants.add(new Ant((long)1.0));
        
        for(int i = 0; i < cols*rows; i++)
            grid.add(AntFarm.EMPTY);
        grid.replace(0, AntFarm.HOME);

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
            Ant.AntStatus status = Ant.AntStatus.ALIVE;
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                updateTime += elapsedTime;
                
                if (updateTime < 0.1)
                    return;
                else
                    updateTime = 0.0;

                //logic
                for(Ant ant : ants)
                {
                  status = ant.update(grid);
                  switch(status)
                  {
                    case DEAD:
                        ants.remove(ant);
                        break;
                    case BIRTHING:
                        ants.add(new Ant((long)1.0));
                        break;
                    default:
                        break;
                  }
                }
                System.out.println("Number of ants: " + ants.size());

                random.setSeed(currentNanoTime);
                spawnFood();
                spawnWater();
                spawnPoison();

                // render
                gc.clearRect(0, 0, 1200, 1200);
                gc.setFill( Color.WHITE );
                gc.fillRect(0, 0, 1200, 1200);
                gc.setStroke( Color.WHITE );
                renderGrid(root);
                renderAnts(root);
            }
        }.start();

        theStage.show();
    }
}