class TransitionFactory
{
  public enum TransitionEnum {GOTFOOD, GOTHOME, GOTPOISON, GOTWATER, HADBIRTH, EMPTY};
  static Transition gotFood = new TransitionGotFood();
  static Transition gotHome = new TransitionGotHome();
  static Transition gotPoison = new TransitionGotPoison();
  static Transition gotWater = new TransitionGotWater();
  static Transition hadBirth = new TransitionHadBirth();
  static Transition empty = new Transition();

  static public Transition get(TransitionEnum s){
    switch(s) {
      case GOTFOOD:
        return gotFood;
      case GOTHOME:
        return gotHome;
      case GOTPOISON:
        return gotPoison;
      case GOTWATER:
        return gotWater;
      case HADBIRTH:
        return hadBirth;
      case EMPTY:
        return empty;
      default:
        return empty;
    }
  }
}