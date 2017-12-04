class TransitionGotHome extends Transition {

  @Override
  public boolean isTriggered(AntFarm tile) {
    return (tile == AntFarm.HOME);
  }

  @Override
  public State getTargetState() {
    return StateFactory.get(StateFactory.StateEnum.BIRTHING);
  }

}