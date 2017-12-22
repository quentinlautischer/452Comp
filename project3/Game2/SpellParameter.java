class SpellParameter extends Parameter{
  public SpellParameter(){}
  
  @Override
  public int tweaks(){
    return Game2WorldData.spells;
  }

  @Override
  public void nextTweak(){
    val += 1;
    val %= Game2WorldData.spells;
  }
}