class ConnectFourHeuristic{
  static int PriWeighting = 1;
  static int DuoWeighting = 4;
  static int TriWeighting = 8;
  static int TetWeighting = 36;

  public static int getAIHeuristic(Grid<Tile> grid){
    return  (PriWeighting * countPri(grid, TileType.COMPUTER))
          + (DuoWeighting * countDuo(grid, TileType.COMPUTER))
          + (TriWeighting * countTri(grid, TileType.COMPUTER))
          + (TetWeighting * countTet(grid, TileType.COMPUTER))
          - (TriWeighting * 2 * countPri(grid, TileType.PLAYER))
          - (DuoWeighting * 2 * countDuo(grid, TileType.PLAYER))
          - (TriWeighting * 2 * countTri(grid, TileType.PLAYER))
          - (TetWeighting * 4 * countTet(grid, TileType.PLAYER));
  }

  public static int countPri(Grid<Tile> grid, TileType type){
    return count(grid, type, 1);
  }

  public static int countDuo(Grid<Tile> grid, TileType type){
    return count(grid, type, 2); 
  }

  public static int countTri(Grid<Tile> grid, TileType type){
    return count(grid, type, 3);
  }

  public static int countTet(Grid<Tile> grid, TileType type){
    return count(grid, type, 4);
  }

  public static int count(Grid<Tile> grid, TileType type, int length){
    int count = 0;
    int offset = length-1;
    
    // horizontalCheck
    for (int j = 0; j<Game1WorldData.rows-offset; j++ ){
      for (int i = 0; i<Game1WorldData.cols; i++){
        boolean res = true;
        for (int k = 0; k<= offset; k++){
          if (grid.get(i, j+k).getType() != type)
            res = false;
        }
        if (res) count++;    
      }
    }
    // verticalCheck
    for (int i = 0; i<Game1WorldData.cols-offset; i++ ){
      for (int j = 0; j<Game1WorldData.rows; j++){
        boolean res = true;
        for (int k = 0; k<= offset; k++){
          if (grid.get(i+k,j).getType() != type)
            res = false;
        }           
        if (res) count++;
      }
    }
    // ascendingDiagonalCheck 
    for (int i=offset; i<Game1WorldData.cols; i++){
        for (int j=0; j<Game1WorldData.rows-offset; j++){
          boolean res = true;
          for (int k=0; k<= offset; k++){
            if (grid.get(i-k,j+k).getType() != type)
              res = false;
          }
          if (res) count++;
        }
    }
    // descendingDiagonalCheck
    for (int i=offset; i<Game1WorldData.cols; i++){
        for (int j=offset; j<Game1WorldData.rows; j++){
          boolean res = true;
          for (int k=0; k<=offset; k++){
            if (grid.get(i-k,j-k).getType() != type)
              res = false;
          }
          if (res) count++;
        }
    }

    return count;
  }
}
        
