import javafx.scene.paint.Color;

public enum TileType
{
  EMPTY,
  PLAYER,
  COMPUTER;

  public static Color getColor(TileType type)
  {
    switch(type)
    {
      case EMPTY:
        return new Color(0.5,0.5,0.5,1);
      case COMPUTER:
        return new Color(0.1, 0.1, 0.1, 1);
      case PLAYER:
        return new Color(0.95, 0.95, 0.95, 1);
      default:
        return new Color(0,0,0,0);
    }
  }
}