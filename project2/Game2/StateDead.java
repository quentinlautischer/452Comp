class StateDead extends State {
  public StateDead() {
    entryAction = ActionFactory.get(ActionFactory.ActionEnum.DIE);
  }
}