import javafx.scene.paint.Color;

class HealthBar {

  double maxHealth = 200.0;
  double health = 200;

  HealthBar(){}

  public double getHealthPercentage(){
    return (health/maxHealth);
  }

  public double getHealth(){
    return health;
  }

  public boolean isDead(){
    return health == 0;
  }

  public void takeDamage(double dmg){
    health -= dmg;
    if (health < 0.0)
      health = 0.0;
  }

  public void receiveHealing(double healing){
    if (isDead())
      return;
    health += healing;
    if (health > maxHealth)
      health = maxHealth;
  }

  public Color getColor(){
    return Color.color(1.0f*(((float)maxHealth-health)/(float)maxHealth), 1.0f*(((float)health)/(float)maxHealth), 0.0f);
  }

  public void reset(){
    health = maxHealth;
  }
}