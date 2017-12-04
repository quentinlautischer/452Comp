import java.util.ArrayList;

class StateHomeBound extends State {

  public StateHomeBound() {
    transitions = new ArrayList<Transition>() {{
      add(new TransitionGotHome());
      add(new TransitionGotPoison());
    }};

    action = new ActionGoHome();
  }
  
}