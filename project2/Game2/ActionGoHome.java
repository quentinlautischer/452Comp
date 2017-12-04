class ActionGoHome extends Action {
  
  public void makeDecision(Ant ant) {
    switch (ant.getRandom().nextInt(2))
    {
      case 0:
        if (ant.getRow() != 0) {
          ant.setRow(ant.getRow()-1);
          ant.setRotation(0);
        }
        else{
          if (ant.getCol() != 0) {
            ant.setCol(ant.getCol()-1);
            ant.setRotation(270);
          }
        }
        break;
      default:
        if (ant.getCol() != 0) { 
          ant.setCol(ant.getCol()-1);
          ant.setRotation(270);
        }
        else {
          if (ant.getRow() != 0) {
            ant.setRow(ant.getRow()-1);
            ant.setRotation(0);
          }
        }
        break;
    }
  }
}