class TransitionGotFood extends Transition {

  public TransitionGotFood() {
    state = new StateHomeBound();
  }
    
  @Override
  public boolean isTriggered(AntFarm tile) {
    return (tile == AntFarm.FOOD);
  }
}