class TransitionGotPoison extends Transition {
  
  @Override
  public boolean isTriggered(AntFarm tile) {
    return  (tile == AntFarm.POISON);
  }

  @Override
  public State getTargetState() {
    return StateFactory.get(StateFactory.StateEnum.DEAD);
  }

  @Override
  public Action getAction() {
    return ActionFactory.get(ActionFactory.ActionEnum.EAT);
  }
}