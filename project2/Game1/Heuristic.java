import java.lang.Math;

class Heuristic {
  Grid<Node> grid;
  Node goalNode = null;

  public Heuristic(Grid<Node> grid, Node goalNode) {
    this.goalNode = goalNode;
    this.grid = grid;
  }

  public int estimate(Node node) {
    int idx = grid.idxFromObj(node);
    int row = grid.rowFromIdx(idx);
    int col = grid.colFromIdx(idx);

    int idxGoal = grid.idxFromObj(goalNode);
    int rowGoal = grid.rowFromIdx(idxGoal);
    int colGoal = grid.colFromIdx(idxGoal);

    int hcost = 0;
    while(row!=rowGoal & col!=colGoal) {
      int pre_idx = grid.idxFromColRow(col, row);
      if (col < colGoal)
        col++;
      else if (col > colGoal)
        col--;

      if (row < rowGoal)
        row++;
      else if (row > rowGoal)
        row--;

      int i = grid.idxFromColRow(col, row);
      int geometric_weight = 10;
      if (grid.isDiagnal(pre_idx, i))
        geometric_weight = 14;
      hcost += geometric_weight*Terrain.getCost(grid.objFromIdx(i).getTerrain());
    }
    return hcost;
  }
}