class Transition
{
  State state = new State();
  Action action = new Action();

  public boolean isTriggered(AntFarm tile) {
    return false;
  }

  public State getTargetState() {
    return state;
  }

  public Action getAction() {
    return action;
  }
}