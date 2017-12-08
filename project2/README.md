#Project 2 
My projects are created using JavaFX for a graphical rendering engine. If there are any issues please let me know.

## Game 1 - A* Pathfinding

### Compile
```bash
  cd Game1
  javac *.java
```
### Input:
```bash
  java Game1 <Input File (CSV)>
  i.e.
  java Game1 sampleInputs/sampleInput.csv
```
Input files can be created as follows:
Create a 16X16 comma separated list.
This CSV must contain 1 's' one 'f' for start and finish.
The rest of the values can be mapped as follows:
Open      = '1' (Cost = 1)
Grassland = '2' (Cost = 3)
Swampland = '3' (Cost = 4)
Obstacles = '4' (Cost = Inf)

### Output: 
The game will run and a A* path evolution will be explored. Open nodes are represented as pink. Closed nodes are represented as Dark Red. Once a path is found it will be highlighted in Yellow.

### Design Decisions
A* implementation comes from the pseudo code provided in the textbook. I've allowed for pathfinding to estimate and paths to include diagonals.
A priority queue was used to optimize the open list as there are many calls for the smallest value.
A hashmap was used to optimized the closed list as there are many find calls on this list.
Weighted values of 10 are multiplied into the terrain cost of Edge Adjacent tiles and weighted values of 14 for Orthogonally Adjacent tiles.

## Game 2 - Ant Farm
### Compile
```bash
  cd Game2
  javac *.java
```
### Input:
```bash
  java Game2 <number of ants>
  or 
  java Game2 4
```

### Output:
Ants will walk through the gridded map.
Ant AI tree:
  Hungry -> Look for food
  Ate Food -> Return home
  At Home -> Spawn child
  Thirsty -> Look for water
  Drank Water -> Hungry

  Poison -> Die.

Dead ants will remain motionless on the map.

### Design Decisions
I decided to implement the decision making scheme using a robust State Machine as demonstrated in pseudo-code in the book. This required a lot of coding and lots of methods but I thought it would be good to go through the workings rather than a hard coded FSM and I wanted more modularity than decision trees could provide.

I did not sort my code into folders but I've setup a structured syntax to the filenames. I have Action<Desc>, State<Desc>, and Transition<Desc>. Factories are used to provide one time instantiation of state, action, and transition objects.
