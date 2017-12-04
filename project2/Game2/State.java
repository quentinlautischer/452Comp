import java.util.ArrayList;

class State {
  
  ArrayList<Transition> transitions = new ArrayList<Transition>();

  Action entryAction = ActionFactory.get(ActionFactory.ActionEnum.EMPTY);
  Action exitAction = ActionFactory.get(ActionFactory.ActionEnum.EMPTY);
  Action action = ActionFactory.get(ActionFactory.ActionEnum.EMPTY);

  public Action getAction() {
    return action;
  }

  public Action getEntryAction() {
    return entryAction;
  }

  public Action getExitAction() {
    return exitAction;
  }

  public ArrayList<Transition> getTransitions() {
    return transitions;
  }
}