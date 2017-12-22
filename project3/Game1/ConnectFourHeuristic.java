class ConnectFourHeuristic{
  static int PriWeighting = 1;
  static int DuoWeighting = 8;
  static int TriWeighting = 128;
  static int TetWeighting = 1000;

  static int TriInterceptWeighting = 10001;

  public static int get234Heuristic(Grid<Tile> grid, TileType type){
    return (DuoWeighting * countDuo(grid, type)) + (TriWeighting * countTri(grid, type)) + (TetWeighting * countTet(grid, type));
  }

  public static int getMaxThreatHeuristic(Grid<Tile> grid, TileType type){
    return get234Heuristic(grid, type) + (TriInterceptWeighting * countTriIntercept(grid, type));
  }

  public static int getAIHeuristic(Grid<Tile> grid){
    return  (PriWeighting * countPri(grid, TileType.COMPUTER))
          + (DuoWeighting * countDuo(grid, TileType.COMPUTER))
          + (TriWeighting * countTri(grid, TileType.COMPUTER))
          + (TetWeighting * countTet(grid, TileType.COMPUTER))
          - (PriWeighting * countPri(grid, TileType.PLAYER))
          - (DuoWeighting * countDuo(grid, TileType.PLAYER))
          - (TriWeighting * countTri(grid, TileType.PLAYER))
          - (TetWeighting * countTet(grid, TileType.PLAYER));
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

  public static int countTriIntercept(Grid<Tile> grid, TileType type){
    int count = 0;
    int length = 4;
    int offset = length-1;
    
    
    // horizontalCheck
    for (int j = 0; j<Game1WorldData.rows-offset; j++ ){
      for (int i = 0; i<Game1WorldData.cols; i++){
        int tempCount = 0;
        for (int k = 0; k<= offset; k++){
          if (grid.get(i, j+k).getType() != type)
            tempCount++;
        }
        if (tempCount == 1) count++;    
      }
    }

    // verticalCheck
    for (int i = 0; i<Game1WorldData.cols-offset; i++ ){
      for (int j = 0; j<Game1WorldData.rows; j++){
        int tempCount = 0;
        for (int k = 0; k<= offset; k++){
          if (grid.get(i+k,j).getType() != type)
            tempCount++;
        }           
        if (tempCount == 1) count++;   
      }
    }
    // ascendingDiagonalCheck 
    for (int i=offset; i<Game1WorldData.cols; i++){
        for (int j=0; j<Game1WorldData.rows-offset; j++){
          int tempCount = 0;
          for (int k=0; k<= offset; k++){
            if (grid.get(i-k,j+k).getType() != type)
              tempCount++;
          }
          if (tempCount == 1) count++;   
        }
    }
    // descendingDiagonalCheck
    for (int i=offset; i<Game1WorldData.cols; i++){
        for (int j=offset; j<Game1WorldData.rows; j++){
          int tempCount = 0;
          for (int k=0; k<=offset; k++){
            if (grid.get(i-k,j-k).getType() != type)
              tempCount++;
          }
          if (tempCount == 1) count++;   
        }
    }

    return count;
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
        
