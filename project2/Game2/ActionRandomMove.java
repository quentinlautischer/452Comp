class ActionRandomMove extends Action {
  
  public void makeDecision(Ant ant) {
    double rand = ant.getRandom().nextDouble();
    if (rand < 0.25) {
      if (ant.getRow() != 0) {
        ant.setRow(ant.getRow()-1);
        ant.setRotation(0);
      } 
    }
    else if (rand < 0.50) {
      if (ant.getRow() != Game2WorldData.rows-1) {
        ant.setRow(ant.getRow()+1);
        ant.setRotation(180);        
      } 
    }
    else if (rand < 0.75) {
      if (ant.getCol() != 0) {
        ant.setCol(ant.getCol()-1);
        ant.setRotation(270);
      }
    }
    else{
      if (ant.getCol() != Game2WorldData.cols-1) {
        ant.setCol(ant.getCol()+1);
        ant.setRotation(90);
      }
    }
  }
}