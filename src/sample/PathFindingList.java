package sample;

import java.util.ArrayList;

class PathFindingList extends ArrayList<NodeRecord>
{
  public PathFindingList()
  {
    super();
  }

  public NodeRecord smallestElement()
  {
    NodeRecord min = new NodeRecord(new Node(Terrain.OBSTACLE), 0);
    min.setCostSoFar(9999);
    min.setEstimatedTotalCost(9999);
    for (NodeRecord n : this)
    {
      if (n.getCostSoFar() < min.getCostSoFar())
        min = n;
    }
    return min;
  }

  public boolean has(Node node)
  {
    return find(node) != null;
  }

  public NodeRecord find(Node node)
  {
    for (NodeRecord n : this)
    {
      if (n.getNode() == node)
        return n;
    }
    return null;
  }
}