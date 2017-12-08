class MinMaxGridNode{
  Integer col_choice = 0;
  Integer heuristic_value = 0;
  Grid<Tile> grid = null;

  public MinMaxGridNode(Grid<Tile> grid, Integer heuristic, Integer col){
    this.col_choice = col;
    heuristic_value = heuristic;
    this.grid = grid;
  }

  public Integer getColChoice(){
    return this.col_choice;
  }

  public void setColChoice(Integer col){
    this.col_choice = col;
  } 

  public void setHeuristic(Integer heuristic){
    heuristic_value = heuristic;
  }

  public Integer getHeuristic(){
    return heuristic_value;
  }

  public void setGrid(Grid<Tile> grid){
    this.grid = grid;
  }

  public Grid<Tile> getGrid(){
    return grid;
  }

}