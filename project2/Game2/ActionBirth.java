class ActionBirth extends Action {
  
  public void makeDecision(Ant ant) {
    ant.setStatus(AntStatus.BIRTHING);
  }
}