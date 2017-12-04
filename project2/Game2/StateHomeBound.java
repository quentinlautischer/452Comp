import java.util.ArrayList;

class StateHomeBound extends State {

  public StateHomeBound() {
    transitions = new ArrayList<Transition>() {{
      add(TransitionFactory.get(TransitionFactory.TransitionEnum.GOTHOME));
      add(TransitionFactory.get(TransitionFactory.TransitionEnum.GOTPOISON));
    }};

    action = ActionFactory.get(ActionFactory.ActionEnum.GOHOME);
  }
  
}