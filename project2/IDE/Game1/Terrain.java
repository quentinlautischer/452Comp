import javafx.scene.paint.Color;

public enum Terrain {
    OPEN, 
    GRASS, 
    SWAMP,
    OBSTACLE,
    START,
    FINISH;

    public static Color getColor(Terrain terrain) {
      switch (terrain){
        case START:
          return Color.BLUE;
        case FINISH:
          return Color.RED;
        case OPEN:
          return Color.LIGHTGREEN;
        case GRASS:
          return Color.GREEN;
        case SWAMP:
          return Color.DARKGREEN;
        case OBSTACLE:
          return Color.BLACK;
        default:
          return Color.BLACK;
      } 
    }

    public static int getCost(Terrain terrain) {
      switch (terrain){
        case START:
          return 0;
        case FINISH:
          return 0;
        case OPEN:
          return 1;
        case GRASS:
          return 3;
        case SWAMP:
          return 4;
        case OBSTACLE:
          return 999;
        default:
          return 999;
      }
    }

    public static char getChar(Terrain terrain) {
      switch (terrain){
        case START:
          return 's';
        case FINISH:
          return 'g';
        case OPEN:
          return 'o';
        case GRASS:
          return 'f';
        case SWAMP:
          return 's';
        case OBSTACLE:
          return 'X';
        default:
          return 'X'; 
      }
    }

    public static Terrain getTerrain(char index) {
      switch(index){
        case '1':
          return OPEN;
        case '2':
          return GRASS;
        case '3':
          return SWAMP;
        case '4':
          return OBSTACLE;
        case 's':
          return START;
        case 'f':
          return FINISH;
        default: 
          return OBSTACLE;
      }
    }
}