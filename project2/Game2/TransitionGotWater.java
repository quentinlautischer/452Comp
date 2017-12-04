class TransitionGotWater extends Transition {

  @Override
  public boolean isTriggered(AntFarm tile) {
    return (tile == AntFarm.WATER);
  }

  @Override
  public State getTargetState() {
    return StateFactory.get(StateFactory.StateEnum.HUNGRY);
  }
  
}