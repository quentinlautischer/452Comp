class GreaterHeal extends Spell{
  int potency = 20;
  int ticksToCast = 30;

  GreaterHeal(Grid<HealthBar> grid){
    super(grid);
  }

  @Override
  public void applySpell(){
    super.applySpell();
    target.receiveHealing(potency);
  }

}