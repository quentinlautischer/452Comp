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
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

import javafx.scene.shape.Rectangle;

public class Game1 extends Application {

    Long lastNanoTime;
    double updateTime;

    int canvas_height = 800;
    int canvas_width = 800;
    int rows = 16;
    int cols = 16;
    int tile_size = 50;

    Node start;
    Node finish;
    Grid<Node> grid;
    Grid<Rectangle> gridRender = null;
    Grid<Rectangle> pathRender = null;
    AStarPathfinder pathfinder = null;
    ArrayList<Node> path = null;

    String inputFile = null;

    public static void main(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("Not Enough Args provided");
        launch(args);
    }

    public Grid<Node> readData(String file) throws IOException {
        int count = 0;
        Grid<Node> content = new Grid<Node>(rows, cols);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                for(int i=0; i < cols; i++) {
                    char c = line.charAt(i * 2);
                    Terrain t = Terrain.getTerrain(c);
                    Node node = new Node(t);
                    node.setTrack(i + count);
                    content.add(node);
                    if (node.getTerrain() == Terrain.START)
                        start = node;
                    else if (node.getTerrain() == Terrain.FINISH)
                        finish = node;
                }
                count += 16;
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }
        return content;
    }

    private void renderPath(Group root) {
        for (Node n : grid) {
            int idx = grid.idxFromObj(n);
            Rectangle r = pathRender.get(idx);
            if (path != null && path.contains(n))
                r.setFill(Color.YELLOW);
            else if (pathfinder.getCurrent().getNode() == n)
                r.setFill((Color.CYAN));
            else if (pathfinder.getClosed().containsKey(n))
                r.setFill(Color.DARKRED);
            else if (pathfinder.getOpen().has(n))
                r.setFill((Color.PINK));
            else
                r.setFill((Color.color(0,0,0,0)));

            if (!root.getChildren().contains(r))
                root.getChildren().add(r);
        }
    }

    private void renderGrid(Group root) {
        int idx = 0;
        for(Node n : grid) {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);
            Rectangle r = gridRender.get(idx);
            r.setFill(Terrain.getColor(grid.get(col,row).getTerrain()));
            if (!root.getChildren().contains(r))
                root.getChildren().add(r);

            idx++;
        }
    }

    private Grid<Rectangle> gridRenderInit(Group root) {
        gridRender = new Grid<Rectangle>(cols, rows);

        int idx = 0;
        for (Node n : grid) {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);

            Rectangle r = new Rectangle();
            r.setFill((Color.BLACK));

            r.setX(col*tile_size);
            r.setY(row*tile_size);
            r.setWidth(tile_size);
            r.setHeight(tile_size);
            gridRender.add(r);


            if (!root.getChildren().contains(r))
                root.getChildren().add(r);
            idx++;
        }

        return gridRender;
    }

    private Grid<Rectangle> pathRenderInit(Group root) {
        pathRender = new Grid<Rectangle>(cols, rows);

        int idx = 0;
        for(Node t : grid) {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);

            Rectangle r = new Rectangle();
            r.setFill((Color.PURPLE));

            r.setX(col*tile_size+(tile_size/4));
            r.setY(row*tile_size+(tile_size/4));
            r.setWidth(tile_size/4);
            r.setHeight(tile_size/4);
            pathRender.add(r);


            if (!root.getChildren().contains(r))
                root.getChildren().add(r);
            idx++;
        }

        return pathRender;
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle( "Game1 - Pathfinder" );

        try {
            System.out.println("Getting Data");
            inputFile = new String(getParameters().getRaw().get(0));
            grid = readData(inputFile);

            pathfinder = new AStarPathfinder(grid, start, finish);
        }
        catch (IOException e) {
            System.out.println("Could not read file");
        }

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( canvas_width, canvas_height );
        root.getChildren().add( canvas );

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gridRender = gridRenderInit(root);
        pathRender = pathRenderInit(root);

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        pathfinder.setupPathFindAStar();

        lastNanoTime = new Long( System.nanoTime() );

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                updateTime += elapsedTime;

                //Slow down render so we can watch evolution
                if (updateTime < 0.1)
                    return;
                else
                    updateTime = 0.0;

                // logic
                if (!pathfinder.isPathFound())
                    pathfinder.pathFindAStartIteration();
                else if (path == null)
                    path = pathfinder.getPath();

                // render
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