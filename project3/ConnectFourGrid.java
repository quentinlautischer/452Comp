public enum ConnectFourGrid
{
  EMPTY,
  PLAYER,
  COMPUTER;

  public Color getColor(ConncetFourGrid tile)
  {
    switch(tile)
    {
      case PLAYER:
        return Color.WHITE;
      case COMPUTER:
        return Color.BLACK;
      case EMPTY:
        return Color.GRAY;
      default:
        return Color.GRAY;
    }
  }
}