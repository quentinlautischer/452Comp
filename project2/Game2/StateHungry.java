import java.util.ArrayList;

class StateHungry extends State {

  public StateHungry() {
    transitions = new ArrayList<Transition>() {{
      add(new TransitionGotFood());
      add(new TransitionGotPoison());
    }};

    action = new ActionRandomMove();
    exitAction = new ActionEat();
  }

  
}