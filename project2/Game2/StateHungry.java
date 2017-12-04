import java.util.ArrayList;

class StateHungry extends State {

  public StateHungry() {
    transitions = new ArrayList<Transition>() {{
      add(TransitionFactory.get(TransitionFactory.TransitionEnum.GOTFOOD));
      add(TransitionFactory.get(TransitionFactory.TransitionEnum.GOTPOISON));
    }};

    action = ActionFactory.get(ActionFactory.ActionEnum.RANDOMMOVE);
    exitAction = ActionFactory.get(ActionFactory.ActionEnum.EAT);
  }

  
}