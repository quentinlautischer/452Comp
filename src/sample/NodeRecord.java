package sample;

class NodeRecord
{
  Node node;
  Node connection = null;
  int costSoFar = 0;
  int estimatedTotalCost;

  public NodeRecord(Node currentNode, int estimatedTotalCost)
  {
    node = currentNode;
    this.estimatedTotalCost = estimatedTotalCost;
  }

  public Node getNode()
  {
    return node;
  }

  public void setConnection(Node connection)
  {
    this.connection = connection;
  }

  public Node getConnection()
  {
    return connection;
  }

  public void setCostSoFar(int cost)
  {
    costSoFar = cost;
  }

  public int getCostSoFar()
  {
    return costSoFar;
  }

  public int getEstimatedTotalCost()
  {
    return estimatedTotalCost;
  }

  public void setEstimatedTotalCost(int cost)
  {
    estimatedTotalCost = cost;
  }

}