class TransitionGotWater extends Transition {

  public TransitionGotWater() {
    state = new StateHungry();
  }
  
  @Override
  public boolean isTriggered(AntFarm tile) {
    return (tile == AntFarm.WATER);
  }
  
}