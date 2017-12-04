class ActionFactory
{
  public enum ActionEnum {BIRTH, DIE, EAT, GOHOME, RANDOMMOVE, DONEBIRTH, EMPTY};
  static Action birth = new ActionBirth();
  static Action die = new ActionDie();
  static Action eat = new ActionEat();
  static Action goHome = new ActionGoHome();
  static Action randomMove = new ActionRandomMove();
  static Action doneBirth = new ActionDoneBirth();
  static Action empty = new Action();

  static public Action get(ActionEnum s){
    switch(s) {
      case BIRTH:
        return birth;
      case DIE:
        return die;
      case GOHOME:
        return goHome;
      case RANDOMMOVE:
        return randomMove;
      case EAT:
        return eat;
      case DONEBIRTH:
        return doneBirth;
      case EMPTY:
        return empty;
      default:
        return empty;
    }
  }
}