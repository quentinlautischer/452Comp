import javafx.scene.paint.Color;
import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;

class Ant {
  AntStatus status = AntStatus.ALIVE;

  AntStateMachine sm = new AntStateMachine();

  int col = 0;
  int row = 0;
  int rotation = 0;
  Color color = Color.BLACK;
  int size = Game2WorldData.tile_size - 10;
  Rectangle2D r = new Rectangle2D(0,0,0,0);
  Grid<AntFarm> grid = null;

  Group root = null;
  Image image = new Image("res/ant.png");
  ImageView iv = new ImageView();

  Random random = new Random();

  public Ant(long moveSeed, Grid<AntFarm> grid) {    
    this.grid = grid;
    random.setSeed(moveSeed);
    iv.setImage(image);
    r = new Rectangle2D(0, 0, 512, 512);

    iv.setViewport(r);
    iv.setFitWidth(size);
    iv.setFitHeight(size);
    iv.setSmooth(true);
    iv.setCache(true);
  }

  public Random getRandom() {
    return random;
  }

  public void setRotation(int rot) {
    rotation = rot;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int c) {
    col = c;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int r) {
    row  = r;
  }

  public void eat() {
    grid.replace(col, row, AntFarm.EMPTY);
  }

  public void setStatus(AntStatus s) {
    status = s;
  }

  public AntStatus update(Grid<AntFarm> grid) {
    ArrayList<Action> actions = sm.update(inspect(grid));
    for(Action action : actions) {
      action.makeDecision(this);
    }

    return status;
  }

  public AntFarm inspect(Grid<AntFarm> grid) {
    return grid.get(col, row);
  }

  public void render(Group root) {
    if (this.root == null)
      this.root = root;

    int x = (col*Game2WorldData.tile_size)+(Game2WorldData.tile_size - size)/2;
    int y = (row*Game2WorldData.tile_size)+(Game2WorldData.tile_size - size)/2;
    
    iv.setX(x);
    iv.setY(y);
    iv.setRotate(rotation);
    if (!this.root.getChildren().contains(iv))
      this.root.getChildren().add(iv);
  }
}