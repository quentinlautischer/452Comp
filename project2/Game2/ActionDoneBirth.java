class ActionDoneBirth extends Action {
  
  public void makeDecision(Ant ant) {
    ant.setStatus(AntStatus.ALIVE);
  }
}