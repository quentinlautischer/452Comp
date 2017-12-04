class TransitionHadBirth extends Transition {
  
  @Override
  public boolean isTriggered(AntFarm tile) {
    return true; //Birth is instant
  }

  @Override
  public State getTargetState() {
    return StateFactory.get(StateFactory.StateEnum.THIRSTY);
  }

  @Override
  public Action getAction() {
    return ActionFactory.get(ActionFactory.ActionEnum.DONEBIRTH);
  }
  
}