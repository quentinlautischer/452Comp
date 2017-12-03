import java.lang.Math;

class Orientation
  {
      double value = 0.0;

      Orientation(double value)
      {
          this.value = value;
      }

      Orientation(Orientation o){this.value = o.get();}

      void increment(double val)
      {
          this.value = (this.value + val) % 360;
          if (this.value<0) this.value += 360;
      }

      void decrement(double val)
      {
          this.value = (this.value - val) % 360;
          if (this.value<0) this.value += 360;
      }

      void set(double val)
      {
          this.value = val % 360;
          // if (this.value<0) this.value += 360;
      }

      void set(double x, double y)
      {
        this.value = Math.atan2(x, y) * (180/Math.PI);
      }

      double get()
      {
          return this.value;
      }

      double getRadians()
      {
          return this.value*(Math.PI/180.0);
      }

      double getVectorX()
      {
        return Math.cos(this.value*(Math.PI/180.0));
      }

      double getVectorY()
      {
        return Math.sin(this.value*(Math.PI/180.0));
      }
  }