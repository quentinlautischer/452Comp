class SpellTargetParameter extends Parameter{
  
  public SpellTargetParameter(){}

  @Override
  public int tweaks(){
    return Game2WorldData.targets;
  }

  @Override
  public void nextTweak(){
    val += 1;
    val %= Game2WorldData.targets;
  }
}