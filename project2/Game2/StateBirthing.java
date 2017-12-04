import java.util.ArrayList;

class StateBirthing extends State {
  
  public StateBirthing() {
    transitions = new ArrayList<Transition>() {{
      add( TransitionFactory.get(TransitionFactory.TransitionEnum.HADBIRTH));
    }};

    entryAction = ActionFactory.get(ActionFactory.ActionEnum.BIRTH);
    action = ActionFactory.get(ActionFactory.ActionEnum.RANDOMMOVE);
  }
}