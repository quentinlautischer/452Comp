import java.util.ArrayList;
import java.lang.RuntimeException;
import java.util.Iterator;


public class Grid<T> implements Iterable<T> {
  ArrayList<T> grid;
  int height;
  int width;

  public Grid(int height, int width) {
    this.height = height;
    this.width = width;
    grid = new ArrayList<T>();
  }

  public int rowFromIdx(int idx) {
    return idx/width;
  }

  public int colFromIdx(int idx) {
    return idx%width;
  }

  public int idxFromObj(T obj) {
    int idx = 0;
    for(T t : grid) {
      if (obj==t)
        return idx;
      idx++; 
    }
    return -1;
  }

  public T objFromIdx(int idx) {
    int i = 0;
    for(T t : grid) {
      if (i==idx)
        return t;
      i++;
    }
    return null;
  }

  

  public int idxFromColRow(int col, int row) {
    return (row*width)+col;
  }

  public void add(T item) {
    if (grid.size() >= height*width)
      throw new RuntimeException();
    grid.add(item);
  }

  public T get(int idx) {
    return grid.get(idx);
  }

  public T get(int col, int row) {
    if (col < 0 | col > height-1)
      return null;
    if (row < 0 | row > width-1)
      return null;

    return grid.get((row*width)+col);
  }

  public void replace(int col, int row, T item) {
    grid.set(idxFromColRow(col, row), item);
  }

  public void replace(int idx, T item) {
    grid.set(idx, item);
  }

  public Iterator<T> iterator() {
    return grid.iterator();
  }

  public boolean isDiagnal(int idx1, int idx2) {
    int row1 = rowFromIdx(idx1);
    int row2 = rowFromIdx(idx2);
    int col1 = colFromIdx(idx1);
    int col2 = colFromIdx(idx2);

    return (row1!=row2 & col1!=col2);
  }

  public ArrayList<T> getConnections(T obj) {
    ArrayList<T> connections = new ArrayList<T>();

    int idx = idxFromObj(obj);
    int row = rowFromIdx(idx);
    int col = colFromIdx(idx);

    T t = get(col-1, row-1);
    if (t != null)
      connections.add(t);
    t = get(col-0, row-1);
    if (t != null)
      connections.add(t);
    t = get(col+1, row-1);
    if (t != null)
      connections.add(t);

    t = get(col-1, row-0);
    if (t != null)
      connections.add(t);
    t = get(col+1, row-0);
    if (t != null)
      connections.add(t);

    t = get(col-1, row+1);
    if (t != null)
      connections.add(t);
    t = get(col-0, row+1);
    if (t != null)
      connections.add(t);
    t = get(col+1, row+1);
    if (t != null)
      connections.add(t);

    return connections;
  }
}