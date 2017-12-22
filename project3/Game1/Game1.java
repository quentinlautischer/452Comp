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
import javafx.scene.text.*;

public class Game1 extends Application
{
    int depth = 9;
    Long lastNanoTime;
    double updateTime;

    int canvas_height = Game1WorldData.rows*Game1WorldData.tile_size;
    int canvas_width = Game1WorldData.cols*Game1WorldData.tile_size;

    Grid<Tile> grid = new Grid<Tile>(Game1WorldData.rows,Game1WorldData.cols);
    Grid<Circle> gridRender = new Grid<Circle>(Game1WorldData.rows,Game1WorldData.cols);

    Text winLabel = new Text(50, 100, "");


    public static void main(String[] args)
    {
        launch(args);
    }

    private void renderGridInit(Group root)
    {
        int idx = 0;
        for(Tile t : grid)
        {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);
            Circle c = new Circle();
            c.setFill(TileType.getColor(t.getType()));
            c.setRadius(Game1WorldData.tile_size/3);
            c.setCenterX(col*Game1WorldData.tile_size + Game1WorldData.tile_size/2);
            c.setCenterY(row*Game1WorldData.tile_size + Game1WorldData.tile_size/2);
            root.getChildren().add(c);
            gridRender.add(c);
            idx++;
        }

        winLabel.setFont(new Font(50));
        winLabel.setFill(Color.GREEN);
        root.getChildren().add(winLabel);
    }

    private void renderGrid(Group root)
    {
        int idx = 0;
        for(Tile t : grid){
            Circle c = gridRender.get(idx);
            c.setFill(TileType.getColor(t.getType()));
            idx++;
        }
    }

    private int getMouseEventCol(MouseEvent e) {
        return (int) e.getX()/Game1WorldData.tile_size;
    }

    private boolean didPlayerWin(){
        return (ConnectFourHeuristic.countTet(grid, TileType.PLAYER) > 0);
    }

    private boolean didComputerWin(){
        return (ConnectFourHeuristic.countTet(grid, TileType.COMPUTER) > 0);
    }

    public Grid<Tile> cloneGrid(Grid<Tile> originalGrid)
    {
        Grid<Tile> newGrid = new Grid<Tile>(Game1WorldData.rows, Game1WorldData.cols);

        int idx = 0;
        for(Tile t : grid){
            newGrid.add(new Tile(grid.get(idx).getType()));
            idx++;
        }

        return newGrid;
    }


    private int minMaxColChoice(MinMaxGridNode node, int depth, TileType type){
        return MinMax.pruneNegamax(node, depth, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE).getColumn();
    }

    private boolean gridComplete(Grid<Tile> g){
        for(int i =0; i<Game1WorldData.cols; i++){
            if (g.get(i, 0).getType() == TileType.EMPTY)
                return false;
        }
        return true;
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Game1 - ConnectFour" );

        for(int i = 0; i <6*7; i++) {
            grid.add(new Tile());
        }

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( canvas_width, canvas_height );
        root.getChildren().add( canvas );

        theScene.setOnMousePressed(
            new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent e)
                {
                    int col = getMouseEventCol(e);
                    ConnectFourTileAdder.add(grid, col, TileType.PLAYER);
                    // ConnectFourTileAdder.add(grid, minMaxColChoice(new MinMaxGridNode(cloneGrid(grid), 0, 0), depth, TileType.PLAYER), TileType.PLAYER);
                    int aiMove = minMaxColChoice(new MinMaxGridNode(cloneGrid(grid), 0, 0), depth, TileType.COMPUTER);
                    System.out.println("AI selected column : " + aiMove);
                    ConnectFourTileAdder.add(grid, aiMove, TileType.COMPUTER);

                }
            });

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        renderGridInit(root);

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
                
                // logic
                if (didPlayerWin()){
                    winLabel.setText("PLAYER WON");
                }
                if (didComputerWin()){
                    winLabel.setText("COMPUTER WON");
                }

                // render
                gc.clearRect(0, 0, 1200, 1200);
                gc.setFill( Color.YELLOW );
                gc.fillRect(0, 0, 1200, 1200);
                gc.setStroke( Color.YELLOW );
                renderGrid(root);
            }
        }.start();

        theStage.show();
    }
}