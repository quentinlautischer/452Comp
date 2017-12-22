import java.util.ArrayList;

class Parameterss {
  // 5 Spellcasts will occur
  // Of those 5 spells casts each cast can have a target 0-9
  // Of those 5 spells casts each cast can be of spells 0, 1, or 2
  int spellCasts = Game2WorldData.spellCasts;
  ArrayList<SpellParameter> spell = new ArrayList<>(Game2WorldData.spellCasts);
  ArrayList<SpellTargetParameter> spellTarget = new ArrayList<>(Game2WorldData.spellCasts);
  

  public Parameterss(){
    for(int i = 0; i < spellCasts; i++){
      spell.add(new SpellParameter());
    }
    for(int i = 0; i < spellCasts; i++){
      spellTarget.add(new SpellTargetParameter());
    }
  }

  public Parameter get(int i){
    if (i < spellCasts)
      return getSpellParam(i);
    else
      return getSpellTargetParam(i - spellCasts);
  }

  public void setTweak(int i, int val){
    if (i < spellCasts)
      spell.get(i).set(val);
    else
      spellTarget.get(i - spellCasts).set(val);
  }

  public SpellParameter getSpellParam(int i){
    return spell.get(i);
  }

  public SpellTargetParameter getSpellTargetParam(int i){
    return spellTarget.get(i);
  }

  public int size(){
    return Game2WorldData.spellCasts*2;
  }

  public void print(){
    System.out.println("Parameters:");
    System.out.print("Spells: ");
    for(int i = 0; i < spellCasts; i++){
      System.out.print(getSpellParam(i).val() + " ");
    }
    System.out.println("");
    System.out.print("Target: ");
    for(int i = 0; i < spellCasts; i++){
      System.out.print(getSpellTargetParam(i).val() + " ");
    }
    System.out.println("");
  }

}