class GroupHeal extends Spell{
  int potency = 20;
  int ticksToCast = 40;

  GroupHeal(Grid<HealthBar> grid){
    super(grid);
  }

  private int mod(int val, int m){
    int v = val % m;
    if (v < 0)
      v = m + v;
    return v;
  }

  @Override
  public void applySpell(){
    super.applySpell();
    target.receiveHealing(potency);
    int idx = grid.idxFromObj(target);
    HealthBar target2 =  grid.get(mod(idx+1, grid.size()));
    HealthBar target3 =  grid.get(mod(idx+2, grid.size()));
    HealthBar target4 =  grid.get(mod(idx-1, grid.size()));
    HealthBar target5 =  grid.get(mod(idx-2, grid.size()));
    target2.receiveHealing(potency);
    target3.receiveHealing(potency);
    target4.receiveHealing(potency);
    target5.receiveHealing(potency);
  }

}