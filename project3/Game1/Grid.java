import java.util.ArrayList;
import java.lang.RuntimeException;
import java.util.Iterator;


public class Grid<T> implements Iterable<T>
{
  ArrayList<T> grid;
  int height;
  int width;

  public Grid(int height, int width)
  {
    this.height = height;
    this.width = width;
    grid = new ArrayList<T>();
  }

  public int rowFromIdx(int idx)
  {
    return idx/width;
  }

  public int colFromIdx(int idx)
  {
    return idx%width;
  }

  public int idxFromColRow(int col, int row)
  {
    return row*width+col;
  }

  public void add(T item)
  {
    if (grid.size() >= height*width)
      throw new RuntimeException();
    grid.add(item);
  }

  public T get(int idx)
  {
    return grid.get(idx);
  }

  public T get(int col, int row)
  {
    return grid.get((row*width)+col);
  }

  public void replace(int col, int row, T item)
  {
    grid.set(idxFromColRow(col, row), item);
  }

  public void replace(int idx, T item)
  {
    grid.set(idx, item);
  }

  public Iterator<T> iterator()
  {
    return grid.iterator();
  }
}