import java.util.Comparator;
import java.util.PriorityQueue;

public class OpenList extends PriorityQueue<NodeRecord> {

    public OpenList()
        {
            super(new Comparator<NodeRecord>() {
                @Override
                public int compare(NodeRecord o1, NodeRecord o2) {
                    return (o1.getCostSoFar() - o2.getCostSoFar());
                }
            });
        }

    public NodeRecord smallestElement()
    {
        return this.peek();
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

