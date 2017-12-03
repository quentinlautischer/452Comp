import javafx.scene.paint.Color;
import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

class Ant
{
  public enum AntStatus
  {
    ALIVE,
    DEAD,
    BIRTHING;
  }

  private enum Behaviour
  {
    FOOD_SEARCH, 
    WATER_SEARCH, 
    HOME_SEARCH;
  }

  Behaviour behaviour = Behaviour.FOOD_SEARCH;
  AntStatus status = AntStatus.ALIVE;

  Color color = Color.BLACK;
  int size = Game2WorldData.tile_size - 10;
  int col = 0;
  int row = 0;
  int rotation = 0;

  Rectangle2D r = new Rectangle2D(0,0,0,0);
  Group root = null;
  Image image = new Image("ant.png");
  ImageView iv = new ImageView();

  Random random = new Random();

  public Ant(long moveSeed)
  {    
    random.setSeed(moveSeed);
    iv.setImage(image);
    r = new Rectangle2D(0, 0, 512, 512);

    iv.setViewport(r);
    iv.setFitWidth(size);
    iv.setFitHeight(size);
    iv.setSmooth(true);
    iv.setCache(true);
  }

  private void goHome()
  {
    switch (random.nextInt(2))
    {
      case 0:
        if (row != 0) 
        {
          row--;
          rotation = 0;
        }
        else
          if (col != 0)
          { 
            col--;
            rotation = 270;
          }
        break;
      default:
        if (col != 0)
        { 
          col--;
          rotation = 270;
        }
        else
          if (row != 0)
          {
            row--;
            rotation = 0;
          }
        break;
    }
  }

  private void randomMove()
  {
    double rand = random.nextDouble();
    if (rand < 0.25)
    {
      if (row != 0)
      {
        row--;
        rotation = 0;        
      } 
    }
    else if (rand < 0.50)
    {
      if (row != Game2WorldData.rows-1)
      {
        row++;
        rotation = 180;        
      } 
    }
    else if (rand < 0.75)
    {
      if (col != 0) 
      {
        col--;
        rotation = 270;
      }
    }
    else
    {
      if (col != Game2WorldData.cols-1) 
      {
        col++;
        rotation = 90;
      }
    }
  }

  public Ant.AntStatus update(Grid<AntFarm> grid)
  {
    switch (behaviour)
    {
      case FOOD_SEARCH:
        randomMove();
        inspect(grid);
        return status;
      case WATER_SEARCH:
        randomMove();
        inspect(grid);
        return status;
      case HOME_SEARCH:
        goHome();
        inspect(grid);
        break;
    }    

    return status;
  }

  public void inspect(Grid<AntFarm> grid)
  {
    AntFarm tile = grid.get(col, row);
    switch (tile)
    {
      case FOOD:
        if (behaviour == Behaviour.FOOD_SEARCH)
        {
          grid.replace(col, row, AntFarm.EMPTY);
          status = AntStatus.ALIVE;
          behaviour = Behaviour.HOME_SEARCH;
        }
        break;
      case WATER:
        if (behaviour == Behaviour.WATER_SEARCH)
        {
          grid.replace(col, row, AntFarm.EMPTY);
          status = AntStatus.ALIVE;
          behaviour = Behaviour.FOOD_SEARCH;  
        }
        break;
      case POISON:
        grid.replace(col, row, AntFarm.EMPTY);
        status = AntStatus.DEAD;
        if (this.root.getChildren().contains(r) & status == AntStatus.DEAD)
          this.root.getChildren().remove(iv);

        break;
      case HOME:
        if (behaviour == Behaviour.HOME_SEARCH & status == AntStatus.ALIVE)
        {
          status = AntStatus.BIRTHING;
          behaviour = Behaviour.WATER_SEARCH;  
        }
        else
        {
          status = AntStatus.ALIVE;
        }
        break;
      default:
        status = AntStatus.ALIVE;
        break;
    }
  }

  public void render(Group root)
  {
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