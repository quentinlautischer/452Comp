class TransitionHadBirth extends Transition {
  
  public TransitionHadBirth() {
    state = new StateThirsty();
    action = new Action() {
      public void makeDecision(Ant ant) {
        ant.setStatus(AntStatus.ALIVE);
      }};
  }

  @Override
  public boolean isTriggered(AntFarm tile) {
    return true; //Birth is instant
  }
  
}