class Heal extends Spell{
  int potency = 10;
  int ticksToCast = 25;

  Heal(Grid<HealthBar> grid){
    super(grid);
  }

  @Override
  public void applySpell(){
    super.applySpell();
    target.receiveHealing(potency);
  }

}