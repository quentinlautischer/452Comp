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

public class Game2 extends Application
{
    Long lastNanoTime;
    double updateTime;
    Thread t = null;

    int canvas_height = 2*(Game2WorldData.rows+1)*Game2WorldData.tile_size;
    int canvas_width = Game2WorldData.cols*Game2WorldData.tile_size;

    boolean gameOver = false;
    int spellsCast = 0;
    int damageDealt = 0;
    Text damageDoneLabel = null;

    double bossDmg = 0.05;
    double bossDmgIncrease = 0.001;

    Grid<HealthBar> grid = new Grid<HealthBar>(Game2WorldData.rows,Game2WorldData.cols);
    Grid<Rectangle> gridRender = new Grid<Rectangle>(Game2WorldData.rows,Game2WorldData.cols);
    
    Spell heal = new Heal(grid);
    Spell greaterHeal = new GreaterHeal(grid);
    Spell groupHeal = new GroupHeal(grid);

    Spell currentSpell = groupHeal;

    int selectedSpell = 0;
    Rectangle spellSelection1 = new Rectangle();
    Text ss1_label = new Text(0, 0, "Heal");
    Rectangle spellSelection2 = new Rectangle();
    Text ss2_label = new Text(0, 0, "Greater Heal");
    Rectangle spellSelection3 = new Rectangle();
    Text ss3_label = new Text(0, 0, "Group Heal");

    Rectangle castBar = new Rectangle();

    GameInterface ctrl = null;    
    
    final Learning learning = new Learning(this);


    public static void main(String[] args) {
        launch(args);
    }

    public void reset(){
        selectedSpell = 0;
        currentSpell = heal;
        bossDmg = 0.5;
        damageDealt = 0;
        gameOver = false;
        spellsCast = 0;
        
        for(HealthBar hb : grid){
            hb.reset();
        }
    }

    private void renderGridInit(Group root) {
        int idx = 0;
        for(HealthBar hb : grid)
        {
            int col = grid.colFromIdx(idx);
            int row = grid.rowFromIdx(idx);

            //Background
            Rectangle br = new Rectangle();
            br.setFill(Color.BLACK);
            br.setWidth(Game2WorldData.tile_size-10);
            br.setHeight(Game2WorldData.tile_size-10);
            br.setX(col*Game2WorldData.tile_size + 5);
            br.setY(row*Game2WorldData.tile_size + 5);
            root.getChildren().add(br);

            //Health
            Rectangle r = new Rectangle();
            r.setFill(hb.getColor());
            r.setWidth(Game2WorldData.tile_size-10);
            r.setHeight(Game2WorldData.tile_size-10);
            r.setX(col*Game2WorldData.tile_size + 5);
            r.setY(row*Game2WorldData.tile_size + 5);
            root.getChildren().add(r);
            gridRender.add(r);

            idx++;
        }
        damageDoneLabel = new Text(Game2WorldData.rows*Game2WorldData.tile_size/3, (Game2WorldData.rows+0.5)*Game2WorldData.tile_size, "Damage Done: " + damageDealt);
        damageDoneLabel.setFont(new Font(20));
        damageDoneLabel.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(damageDoneLabel);

        ss1_label.setX(0.5*Game2WorldData.tile_size);
        ss1_label.setY(Game2WorldData.tile_size*(Game2WorldData.rows+1)-10);
        ss1_label.setFont(new Font(20));
        ss1_label.setFill(Color.WHITE);

        spellSelection1.setStrokeWidth(10);
        spellSelection1.setWidth(Game2WorldData.tile_size);
        spellSelection1.setHeight(Game2WorldData.tile_size);
        spellSelection1.setX(0.5*Game2WorldData.tile_size);
        spellSelection1.setY(Game2WorldData.tile_size*(Game2WorldData.rows+1));
        root.getChildren().add(spellSelection1);
        root.getChildren().add(ss1_label);

        ss2_label.setX(2*Game2WorldData.tile_size);
        ss2_label.setY(Game2WorldData.tile_size*(Game2WorldData.rows+1)-10);
        ss2_label.setFont(new Font(20));
        ss2_label.setFill(Color.WHITE);

        spellSelection2.setStrokeWidth(10);
        spellSelection2.setWidth(Game2WorldData.tile_size);
        spellSelection2.setHeight(Game2WorldData.tile_size);
        spellSelection2.setX(2*Game2WorldData.tile_size);
        spellSelection2.setY(Game2WorldData.tile_size*(Game2WorldData.rows+1));
        root.getChildren().add(spellSelection2);
        root.getChildren().add(ss2_label);

        ss3_label.setX(3.5*Game2WorldData.tile_size);
        ss3_label.setY(Game2WorldData.tile_size*(Game2WorldData.rows+1)-10);
        ss3_label.setFont(new Font(20));
        ss3_label.setFill(Color.WHITE);

        spellSelection3.setStrokeWidth(10);
        spellSelection3.setWidth(Game2WorldData.tile_size);
        spellSelection3.setHeight(Game2WorldData.tile_size);
        spellSelection3.setX(3.5*Game2WorldData.tile_size);
        spellSelection3.setY(Game2WorldData.tile_size*(Game2WorldData.rows+1));
        root.getChildren().add(spellSelection3);
        root.getChildren().add(ss3_label);

        //castbar
        Rectangle castBarBg = new Rectangle();
        castBarBg.setY(Game2WorldData.height-Game2WorldData.tile_size/3);
        castBarBg.setWidth(Game2WorldData.tile_size*Game2WorldData.cols);
        castBarBg.setHeight(Game2WorldData.tile_size/3);
        castBarBg.setFill(Color.BLACK);
        root.getChildren().add(castBarBg);

        castBar.setY(Game2WorldData.height-Game2WorldData.tile_size/3);
        castBar.setWidth(Game2WorldData.tile_size*Game2WorldData.cols);
        castBar.setHeight(Game2WorldData.tile_size/3);
        castBar.setFill(Color.CYAN);
        root.getChildren().add(castBar);

    }

    private void renderGrid(Group root) {
        int idx = 0;
        for(HealthBar hb : grid){
            Rectangle r = gridRender.get(idx);
            r.setFill(hb.getColor());
            double width = Game2WorldData.tile_size-10.0; 
            r.setWidth(width*hb.getHealthPercentage());
            idx++;
        }

        damageDoneLabel.setText("Damage Done: " + damageDealt);
        castBar.setWidth(Game2WorldData.tile_size*Game2WorldData.cols*currentSpell.castProgress());

        spellSelection1.setStroke(Color.BLACK);
        spellSelection2.setStroke(Color.BLACK);
        spellSelection3.setStroke(Color.BLACK);
        if (selectedSpell == 0){
            spellSelection1.setStroke(Color.GREEN);
        } else if (selectedSpell == 1)
            spellSelection2.setStroke(Color.GREEN);
        else {
            spellSelection3.setStroke(Color.GREEN);
        }

    }

    private void castOnTarget(MouseEvent e){
        int x = (int)e.getX()/Game2WorldData.tile_size;
        int y = (int)e.getY()/Game2WorldData.tile_size;

        //out of healthbar area
        if (y > Game2WorldData.rows-1)
            return;

        HealthBar target = grid.get(x, y);
        currentSpell.startCast(lastNanoTime / 1000000000.0, target);
    }

    private void changeSpellSelection(MouseEvent e){
        double x = e.getX();
        double y = e.getY();

        double tileSize = Game2WorldData.tile_size;
        double spellSelectorY = Game2WorldData.tile_size*(Game2WorldData.rows+1);

        double spellSelectorX = (0.5*Game2WorldData.tile_size);
        if ( x > spellSelectorX && x < spellSelectorX+tileSize)
            if ( y > spellSelectorY && y < spellSelectorY+tileSize){
                selectedSpell = 0;
                currentSpell = heal;
            }
        
        spellSelectorX = (2*Game2WorldData.tile_size);
        if ( x > spellSelectorX && x < spellSelectorX+tileSize)
            if ( y > spellSelectorY && y < spellSelectorY+tileSize){
                selectedSpell = 1;
                currentSpell = greaterHeal;
            }

        spellSelectorX = (3.5*Game2WorldData.tile_size);
        if ( x > spellSelectorX && x < spellSelectorX+tileSize)
            if ( y > spellSelectorY && y < spellSelectorY+tileSize){
                selectedSpell = 2;
                currentSpell = groupHeal;
            }
    }

    @Override
    public void stop(){
        if (t != null)
            t.stop();
        // Save file
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Game2 - Raid Healer - Learning" );

        for(int i = 0; i <Game2WorldData.cols*Game2WorldData.rows; i++) {
            grid.add(new HealthBar());
        }
        ctrl = new GameInterface(this);

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
                    // IF YOU WANNA PLAY UNCOMMENT THIS
                    // if (!currentSpell.isCasting())
                    //     castOnTarget(e);

                    // changeSpellSelection(e);
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

        // Hillclimb
        t = new Thread(){
            public void run(){
                learning.hillClimb(new Parameterss(), Game2WorldData.steps);
            }
        };
        t.start();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                currentSpell.update();
                
                // logic
                for(HealthBar hb : grid){
                    hb.takeDamage(bossDmg);
                    if (!hb.isDead())
                        damageDealt += 1;
                }

                int count = 0;
                for(HealthBar hb : grid){
                    if (hb.isDead())
                        count++;
                }
                if (count == Game2WorldData.targets)
                    gameOver = true;
                bossDmg += bossDmgIncrease;

                // render
                gc.clearRect(0, 0, 1200, 1200);
                gc.setFill( Color.DARKGREY );
                gc.fillRect(0, 0, 1200, 1200);
                gc.setStroke( Color.DARKGREY );
                renderGrid(root);
            }
        }.start();

        theStage.show();
    }
}