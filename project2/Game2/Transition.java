class Transition
{
  public boolean isTriggered(AntFarm tile) {
    return false;
  }

  public State getTargetState() {
    return StateFactory.get(StateFactory.StateEnum.EMPTY);
  }

  public Action getAction() {
    return ActionFactory.get(ActionFactory.ActionEnum.EMPTY);
  }
}