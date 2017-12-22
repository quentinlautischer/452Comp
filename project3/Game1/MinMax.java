class MinMax{

  static int max(int a, int b){
    if (a > b)
      return a;
    return b;
  }

  static void printGrid(Grid<Tile> grid){
    System.out.println("");

    int idx = 0;
    for(Tile t : grid){
      if (t.getType() == TileType.PLAYER)
        System.out.print("1 ");
      else if (t.getType() == TileType.COMPUTER)
        System.out.print("2 ");
      else
        System.out.print("0 ");

      if (idx == Game1WorldData.cols-1){
        idx = 0;
        System.out.println("");
      }
      else
        idx++;
    }

    System.out.println("");
  }



  static MinMaxGridNode pruneNegamax(MinMaxGridNode node, int maxDepth, int currentDepth, int alpha, int beta){

    if (Board.isGameOver(node.getGrid()) | currentDepth == maxDepth){
      int evaluate = ConnectFourHeuristic.getMaxThreatHeuristic(node.getGrid(), node.getCurrentPlayer());
      node.setScore(evaluate);
      return node;
    }

    MinMaxGridNode bestMove = new MinMaxGridNode(node.getGrid(), -Integer.MAX_VALUE, null);

    for (int i = 0; i < Game1WorldData.cols; i++){
      Grid<Tile> newGrid = Board.makeMove(node.getGrid(), node.getCurrentPlayer(), i);
      if (newGrid == null)
        continue;

      MinMaxGridNode currentNode = new MinMaxGridNode(newGrid, -Integer.MAX_VALUE, i);
      currentNode.setCurrentPlayer(node.getCurrentPlayer());
      
      int evaluate = ConnectFourHeuristic.get234Heuristic(currentNode.getGrid(), currentNode.getCurrentPlayer());
      currentNode.setScore(evaluate);

      if (currentNode.getScore() > bestMove.getScore()){
        bestMove = currentNode;
      }
    }

    if (bestMove.getCurrentPlayer() == TileType.PLAYER){
      bestMove.setCurrentPlayer(TileType.COMPUTER);
    } else {
      bestMove.setCurrentPlayer(TileType.PLAYER);
    }
    return pruneNegamax(bestMove, maxDepth, currentDepth+1, -beta, -max(alpha, bestMove.getScore()));      
  }
  


  static MinMaxGridNode abNegamax(MinMaxGridNode node, int maxDepth, int currentDepth, int alpha, int beta){

    if (Board.isGameOver(node.getGrid()) | currentDepth == maxDepth){
      int evaluate = ConnectFourHeuristic.get234Heuristic(node.getGrid(), node.getCurrentPlayer());
      node.setScore(evaluate);
      return node;
    }

    MinMaxGridNode bestMove = new MinMaxGridNode(node.getGrid(), -Integer.MAX_VALUE, null);

    for (int i = 0; i < Game1WorldData.cols; i++){
      Grid<Tile> newGrid = Board.makeMove(node.getGrid(), node.getCurrentPlayer(), i);
      if (newGrid == null)
        continue;

      MinMaxGridNode currentNode = new MinMaxGridNode(newGrid, -Integer.MAX_VALUE, i);
      if (node.getCurrentPlayer() == TileType.COMPUTER){
        currentNode.setCurrentPlayer(TileType.PLAYER);
      }
      currentNode = abNegamax(currentNode, maxDepth, currentDepth+1, -beta, -max(alpha, bestMove.getScore()));      

      currentNode.setScore(-currentNode.getScore());

      if (currentNode.getScore() > bestMove.getScore()){
        bestMove = currentNode;

        if (bestMove.getScore() >= beta){
          return bestMove;
        }
      }
      printGrid(currentNode.getGrid());
      System.out.println(currentNode.getScore());
      System.out.println("");
      System.out.println("");
    }
    System.out.println(bestMove.getCurrentPlayer() + " BEST MOVE AT DEPTH: " + currentDepth);
    printGrid(bestMove.getGrid());
    System.out.println(bestMove.getScore());
    System.out.println("");
    System.out.println("");
    return bestMove;
  }

  static MinMaxGridNode abMinMax(MinMaxGridNode node, int maxDepth, int currentDepth, int alpha, int beta){
    if (Board.isGameOver(node.getGrid()) || currentDepth == maxDepth){
      return node;
    }
      
    MinMaxGridNode bestMove = new MinMaxGridNode(null, -Integer.MAX_VALUE, null);
    if (node.getCurrentPlayer() == TileType.PLAYER){
      bestMove.setScore(Integer.MAX_VALUE);
    }

    for (int i = 0; i < Game1WorldData.cols; i++){
      Grid<Tile> tempGrid = Board.makeMove(node.getGrid(), node.getCurrentPlayer(), i);
      if (tempGrid == null){
        continue;
      }
      MinMaxGridNode currentNode = new MinMaxGridNode(tempGrid, ConnectFourHeuristic.getAIHeuristic(tempGrid), i);
      if (node.getCurrentPlayer() == TileType.COMPUTER){
        currentNode.setCurrentPlayer(TileType.PLAYER);
      }
      currentNode = abMinMax(currentNode, maxDepth, currentDepth+1, alpha, beta);

      if (node.getCurrentPlayer() == TileType.COMPUTER){
        if (currentNode.getScore() > bestMove.getScore()){
          bestMove = currentNode;
        }
      }
      else{
        if (currentNode.getScore() < bestMove.getScore()){
          bestMove = currentNode;
        }
      }

      printGrid(currentNode.getGrid());
      System.out.println(currentNode.getScore());
      System.out.println("");
      System.out.println("");

    }
    return bestMove;
  
  }
}