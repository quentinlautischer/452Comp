class ConnectFourTileAdder {
  public static void add(Grid<Tile> grid, int col, TileType type) {
    Tile lastEmptyTile = null;
    for(int r = 0; r < Game1WorldData.rows; r++){
      Tile t = grid.get(col, r);
      if (t.getType() == TileType.EMPTY)
        lastEmptyTile = t;
    }
    if (lastEmptyTile != null)
      lastEmptyTile.setType(type);
  }
}