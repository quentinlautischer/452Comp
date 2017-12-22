class Spell {
  boolean currentlyCasting = false;
  boolean castCompleted = false;

  int ticksToCast = 50;
  int currentTick = 0;

  Grid<HealthBar> grid = null;
  HealthBar target = null;

  Spell(Grid<HealthBar> grid){
    this.grid = grid;
  }

  public double castProgress(){
    return (double) currentTick / (double) ticksToCast;
  }

  public boolean isCasting(){
    return currentlyCasting;
  }

  public void startCast(double startTime, HealthBar target){
    currentTick = 0;
    currentlyCasting = true;
    castCompleted = false;
    this.target = target;
  }

  public void update(){
    currentTick++;
    if (currentTick > ticksToCast){
      currentlyCasting = false;
      castCompleted = true;
    }

    if (castCompleted && target != null){
      applySpell();
      target = null;
    }
    
  }

  public void applySpell(){
    castCompleted = false;
  }


}