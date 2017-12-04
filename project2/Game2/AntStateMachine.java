import java.util.ArrayList;

class AntStateMachine
{
  ArrayList<State> states = new ArrayList<State>() {{ 
    add(new StateHungry());
    add(new StateHomeBound());
    add(new StateBirthing());
    add(new StateThirsty());
    add(new StateDead());
  }};

  State currentState = new StateHungry();

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
      actions.add(targetState.getEntryAction());

      currentState = targetState;
      return actions;
    } else {
      actions.add(currentState.getAction());
      return actions;
    }
  }
}