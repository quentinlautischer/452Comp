class TransitionGotHome extends Transition {
  
  public TransitionGotHome() {
    state = new StateBirthing();
  }

  @Override
  public boolean isTriggered(AntFarm tile) {
    return (tile == AntFarm.HOME);
  }
}