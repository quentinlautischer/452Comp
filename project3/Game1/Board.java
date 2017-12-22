class Board{
  
    static private Grid<Tile> cloneGrid(Grid<Tile> originalGrid)
    {
        Grid<Tile> newGrid = new Grid<Tile>(Game1WorldData.rows, Game1WorldData.cols);
        int idx = 0;
        for(Tile t : originalGrid){
            newGrid.add(new Tile(originalGrid.get(idx).getType()));
            idx++;
        }
        return newGrid;
    }

  static Grid<Tile> makeMove(Grid<Tile> grid, TileType type, int col){
    Grid<Tile> tempGrid = cloneGrid(grid);
    if (tempGrid.get(col, 0).getType() != TileType.EMPTY)
      return null;
    ConnectFourTileAdder.add(tempGrid, col, type);
    return tempGrid;
  }

  static boolean didPlayerWin(Grid<Tile> grid){
    return (ConnectFourHeuristic.countTet(grid, TileType.PLAYER) > 0);
  }

  static boolean didComputerWin(Grid<Tile> grid){
    return (ConnectFourHeuristic.countTet(grid, TileType.COMPUTER) > 0);
  }

  static boolean isGameOver(Grid<Tile> grid){
    if (ConnectFourHeuristic.countTet(grid, TileType.PLAYER) > 0){
      return true;
    }

    if (ConnectFourHeuristic.countTet(grid, TileType.COMPUTER) > 0){
      return true;
    }


    for(int i=0; i<Game1WorldData.cols; i++){
        if (grid.get(i, 0).getType() == TileType.EMPTY)
            return false;
    }
    return true;
  }
}