public class GameInterface{
  Game2 gameState = null;
  HealthBar target = null;
  
  public GameInterface(Game2 gameState){
    this.gameState = gameState;
    target = gameState.grid.get(0);
  }

  public boolean canChooseNextSpell(){
    return !gameState.currentSpell.isCasting();
  }

  public void chooseSpell(int i){
    if (gameState.currentSpell.isCasting())
      return;
    gameState.selectedSpell = i;

    if (gameState.selectedSpell == 0)
      gameState.currentSpell = gameState.heal;
    else if (gameState.selectedSpell == 1)
      gameState.currentSpell = gameState.greaterHeal;
    else
      gameState.currentSpell = gameState.groupHeal;
  }

  public void selectTarget(int t){ 
    target = gameState.grid.get(t);
  }

  public void castOnTarget(){
    if (!gameState.currentSpell.isCasting()){
      gameState.currentSpell.startCast(gameState.lastNanoTime / 1000000000.0, target);
    }
  }
}