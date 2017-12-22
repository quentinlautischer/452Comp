#Project 1

#Project 2 - Learning - HillClimb

#### Quick notes
I use thread stop. When compiling you may get a deprecation warning. Hope its not an issue.

### Game to learn
I designed a new game from scratch. Its a mmorpg healing simulator. The game consists of a group of healthboxes that will lose health over time. As long as a healthbox is alive it will produce damage which is a mechanism for score. The goal of the game is to keep the healthboxes alive with healing spells. 

Heal (0): 
  Time: short
  Amount: small
  Target(s): 1

Greater Heal (1):
  Time: medium
  Amount: Large
  Target(s): 1

Group Heal (2):
  Time: large
  Amount: Medium
  Target(s): 5

I sped up the game so that the learning algorithm could iterate quickly. There are human based interface controls but it may be difficult to perform at the same speed.

### Compile
```bash
  cd Game2
  javac *.java
```

### Input:
No additional input is required. In the future I could add a 
cmd line argument that provided an initial set of parameters.
```bash
  java Game2
```

### Output
This custom game described above has a visual GUI for you to visualize what is happening. You can see the score increase and the status of the healthboxes.

The command line will output some data indicating the learning algorithms current set of parameters as well as indications of updated best paths.

I've set the hillclimb iteration steps to 3. After three steps a final parameter output will be displayed and the program will infinity run its best parameters.

### Design Decisions
I designed a new game that I knew I could easily construct a interface for my learning algorithm to use. I choose to use the hillClimbing technique for this game as there are many inputs and is a steady rising maxima.

For increased learning times I added a game constraint that only 5 healing spells were allowed to be cast.

The interface allows the learning algorithm to provide a spell selection and target as inputs. The output the algorithm used is the damage dealt score.

This leads to having a parameter set of 5 spell casts of which have two branching options of 3 different spells and 10 different targets.

### Results
Running this learning algorithm will take less than 30mins.
I've removed any possibility for variation (before I used real time counted spell casts now I used tick based spell casts for consistency )

Initial Parameters:
Spells: 0 0 0 0 0 
Target: 0 0 0 0 0 

First Step Results:
Spells: 2 2 2 2 2
Target: 0 0 0 0 0 

In the first round it determines that the group heal is very effective which makes sense as it keep a lot of healthboxes alive for longer.

Second Step Results:
Spells: 2 1 2 1 2
Target: 0 0 0 0 0 

In the second round the learning algorithm has adjusted to alternating between the group heals and the greater heal. This is probably because now that the group heals is in the main parameter set there is an overhealing which is optimized by insertion of a greater heal in between.

Target of the spells didn't get modified because I suppose there was a great enough difference in the insertion of the greater heal.

Spells: 2 1 2 1 2
Target: 5 0 5 0 5

What is fascinating is how the targets have now changed such that the group heals do not affect the target 0 anymore they prioritize other healthboxes and then two greater heals are dumbed into target 0.
