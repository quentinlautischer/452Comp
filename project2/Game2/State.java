import java.util.ArrayList;

class State {
  
  ArrayList<Transition> transitions = new ArrayList<Transition>();

  Action entryAction = new Action();
  Action exitAction = new Action();
  Action action = new Action();

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