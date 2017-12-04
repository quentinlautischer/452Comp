class TransitionGotFood extends Transition {
  
  @Override
  public boolean isTriggered(AntFarm tile) {
    return (tile == AntFarm.FOOD);
  }

  @Override
  public State getTargetState() {
    return StateFactory.get(StateFactory.StateEnum.HOMEBOUND);
  }

  @Override
  public Action getAction() {
    return ActionFactory.get(ActionFactory.ActionEnum.EMPTY);
  }
}