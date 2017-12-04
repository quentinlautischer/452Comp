import java.util.ArrayList;

class StateBirthing extends State {
  
  public StateBirthing() {
    transitions = new ArrayList<Transition>() {{
      add(new TransitionHadBirth());
    }};

    entryAction = new ActionBirth();
    action = new ActionRandomMove();
  }
}