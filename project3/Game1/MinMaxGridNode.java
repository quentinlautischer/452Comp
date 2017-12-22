class MinMaxGridNode{
  Integer col = 0;
  Integer score = 0;
  Grid<Tile> grid = null;
  TileType currentPlayer = TileType.COMPUTER;

  public MinMaxGridNode(Grid<Tile> grid, Integer score, Integer col){
    this.col = col;
    this.score = score;
    this.grid = grid;
  }

  public Integer getColumn(){
    return this.col;
  }

  public void setColumn(Integer col){
    this.col = col;
  } 

  public void setScore(Integer score){
    this.score = score;
  }

  public Integer getScore(){
    return score;
  }

  public void setGrid(Grid<Tile> grid){
    this.grid = grid;
  }

  public Grid<Tile> getGrid(){
    return grid;
  }

  public void setCurrentPlayer(TileType currentPlayer){
    this.currentPlayer = currentPlayer;
  }

  public TileType getCurrentPlayer(){
    return currentPlayer;
  }

}