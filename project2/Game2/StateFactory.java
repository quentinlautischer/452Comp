class StateFactory
{
  public enum StateEnum {DEAD, BIRTHING, HOMEBOUND, HUNGRY, THIRSTY, EMPTY};
  static State dead = new StateDead();
  static State birthing = new StateBirthing();
  static State homebound = new StateHomeBound();
  static State hungry = new StateHungry();
  static State thirsty = new StateThirsty();
  static State empty = new State();

  static public State get(StateEnum s){
    switch(s) {
      case DEAD:
        return dead;
      case BIRTHING:
        return birthing;
      case HOMEBOUND:
        return homebound;
      case HUNGRY:
        return hungry;
      case THIRSTY:
        return thirsty;
      case EMPTY:
        return empty;
      default:
        return empty;
    }
  }
}