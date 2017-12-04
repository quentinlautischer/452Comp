class ActionDie extends Action {
  
  public void makeDecision(Ant ant) {
    ant.setStatus(AntStatus.DEAD);
  }
}
