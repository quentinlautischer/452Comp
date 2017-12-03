package sample;

import javafx.scene.paint.Color;

public enum AntFarm {
    EMPTY,
    FOOD, 
    WATER,
    POISON,
    HOME;

    public static Color getColor(AntFarm terrain)
    {
      switch (terrain){
        case FOOD:
          return Color.LIGHTGREEN;
        case WATER:
          return Color.LIGHTGREEN;
        case POISON:
          return Color.LIGHTGREEN;
        case HOME:
          return Color.BROWN;
        case EMPTY:
          return Color.LIGHTGREEN;
        default:
          return Color.LIGHTGREEN;
      } 
    }
}