package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarPathfinder {

    boolean pathFound = false;

    Node start;
    Node finish;
    NodeRecord current;

    Heuristic heuristic;


    Grid<Node> grid;
    Map<Node, Node> fromNode = new HashMap<>();
    OpenList open = new OpenList();
    HashMap<Node, NodeRecord> closed = new HashMap<>();
    ArrayList<Node> path = null;


    public AStarPathfinder(Grid<Node> grid, Node start, Node finish)
    {
        this.start = start;
        this.finish = finish;
        this.grid = grid;
        heuristic = new Heuristic(grid, finish);
    }

    public NodeRecord getCurrent()
    {
        return current;
    }

    public HashMap<Node, NodeRecord> getClosed()
    {
        return closed;
    }

    public OpenList getOpen()
    {
        return open;
    }


    public boolean isPathFound()
    {
        return !(open.size() > 0) | pathFound;
    }


    public void setupPathFindAStar() {
        NodeRecord startRecord = new NodeRecord(start, heuristic.estimate(start));
        startRecord.setCostSoFar(0);

        open.add(startRecord);
    }

    public void pathFindAStartIteration()
    {
        current = open.smallestElement();
        if (current.getNode() == finish)
        {
            pathFound = true;
            return;
        }

        ArrayList<Node> connections = grid.getConnections(current.getNode());

        NodeRecord endNodeRecord;
        int endNodeHeuristic = 0;


        for (Node connection : connections)
        {
            Node endNode = connection;

            if(connection.getTerrain()==Terrain.OBSTACLE)
                continue;

            int geometric_weight = 10;
            if (grid.isDiagnal(grid.idxFromObj(current.getNode()), grid.idxFromObj(connection)))
                geometric_weight = 14;
            int endNodeCost = current.getCostSoFar() + (geometric_weight*Terrain.getCost(connection.getTerrain()));


            if (closed.containsKey(endNode))
            {
                endNodeRecord = closed.get(endNode);

                if (endNodeRecord.getCostSoFar() <= endNodeCost)
                    continue;

                closed.remove(endNode);

                endNodeHeuristic = endNodeRecord.getEstimatedTotalCost() - endNodeRecord.getCostSoFar();

            }
            else if (open.has(endNode))
            {
                endNodeRecord = open.find(endNode);

                if (endNodeRecord.getCostSoFar() <= endNodeCost)
                    continue;

                // Supposed to be  IDK what cost is
                endNodeHeuristic = endNodeCost - endNodeRecord.getCostSoFar();
            }
            else // Unvisited Node
            {
                endNodeRecord = new NodeRecord(endNode, heuristic.estimate(endNode));

                //might not be needed but I'm thinking so
                endNodeHeuristic = endNodeRecord.getEstimatedTotalCost();
            }

            endNodeRecord.setCostSoFar(endNodeCost);
            endNodeRecord.setConnection(connection);
            endNodeRecord.setEstimatedTotalCost(endNodeCost + endNodeHeuristic);

            if (!open.has(endNode))
                open.add(endNodeRecord);
            fromNode.put(connection, current.getNode());
        }

        open.remove(current);
        closed.put(current.getNode(), current);
    }

    public ArrayList<Node> getPath()
    {
        if (current.getNode() != finish)
        {
            return null;
        }
        else
        {
            ArrayList<Node> path = new ArrayList<Node>();

            Node currentNode = current.getNode();
            while (currentNode != start)
            {
                path.add(currentNode);
                currentNode = fromNode.get(currentNode);
            }
            return path;
        }
    }
}
