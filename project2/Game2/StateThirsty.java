import java.util.ArrayList;

class StateThirsty extends State {

  public StateThirsty() {
    transitions = new ArrayList<Transition>() {{
      add(new TransitionGotWater());
      add(new TransitionGotPoison());
    }};

    action = new ActionRandomMove();
    exitAction = new ActionEat();
  }
}