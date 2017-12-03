import java.lang.Math;

class Ancillaries
{
  public static double normalize(double x, double y)
  {
      double max = Math.pow((x * x + y * y), 0.5);
      return x/max;
  }
}