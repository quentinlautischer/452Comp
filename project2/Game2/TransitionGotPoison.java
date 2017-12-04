class TransitionGotPoison extends Transition {
  
  public TransitionGotPoison() {
    state = new StateDead();
    action = new ActionEat();
  }

  @Override
  public boolean isTriggered(AntFarm tile) {
    return  (tile == AntFarm.POISON);
  }
}