import java.util.ArrayList;

class StateThirsty extends State {

  public StateThirsty() {
    transitions = new ArrayList<Transition>() {{
      add(TransitionFactory.get(TransitionFactory.TransitionEnum.GOTWATER));
      add(TransitionFactory.get(TransitionFactory.TransitionEnum.GOTPOISON));
    }};

    action = ActionFactory.get(ActionFactory.ActionEnum.RANDOMMOVE);
    exitAction = ActionFactory.get(ActionFactory.ActionEnum.EAT);
  }
}