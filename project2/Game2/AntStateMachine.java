import java.util.ArrayList;

class AntStateMachine
{
  State currentState = StateFactory.get(StateFactory.StateEnum.HUNGRY);

  public State getCurrentState() {
    return currentState;
  }

  public ArrayList<Action> update(AntFarm tile) {
    ArrayList<Action> actions = new ArrayList<>();

    Transition triggeredTransition = null;

    for(Transition t : currentState.getTransitions()) {
      if (t.isTriggered(tile)){
        triggeredTransition = t;
        break;
      }
    }

    if (triggeredTransition != null) {
      State targetState = triggeredTransition.getTargetState();

      actions.add(currentState.getExitAction());
      actions.add(triggeredTransition.getAction());
      if (targetState == null)
        System.out.println("TARGET STATE IS NULL");
      actions.add(targetState.getEntryAction());

      currentState = targetState;
      return actions;
    } else {
      actions.add(currentState.getAction());
      return actions;
    }
  }
}