import javafx.scene.shape.Polygon;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


class TrianglePolygon extends Polygon
    {
        public Orientation orientation = new Orientation(0.0);

        public TrianglePolygon(double ax, double ay, double bx, double by, double cx, double cy)
        {
            this.getPoints().setAll(ax, ay, bx, by, cx, cy);
        }

        public void setPoint(int index, double x, double y)
        {
            this.getPoints().set(2*index, x);
            this.getPoints().set(2*index+1, y);
        }

        public void setPoint1(double ax, double ay)
        {
            setPoint(0, ax, ay);
        }

        public void setPoint2(double bx, double by)
        {
            setPoint(1, bx, by);
        }

        public void setPoint3(double cx, double cy)
        {
            setPoint(2, cx, cy);
        }

        public void rotate(double angle, double x, double y)
        {
            Rotate rot = new Rotate(angle, x, y);
            Transform transform = rot;
            
            double points[] = this.getPoints().stream().mapToDouble(Number::doubleValue).toArray();
            transform.transform2DPoints(points, 0, points, 0, points.length/2);
            for(int i = 0; i < points.length/2; i++)
            {
                this.setPoint(i, points[2*i], points[2*i+1]);
            }
            orientation.set(orientation.get() + angle);
        }

        public void rotateTo(double angle, double x, double y)
        {
            Rotate rot = new Rotate(orientation.get() - angle, x, y);
            Transform transform = rot;
            
            double points[] = this.getPoints().stream().mapToDouble(Number::doubleValue).toArray();
            transform.transform2DPoints(points, 0, points, 0, points.length/2);
            for(int i = 0; i < points.length/2; i++)
            {
                this.setPoint(i, points[2*i], points[2*i+1]);
            }
            orientation.set(orientation.get() - angle);
        }

        public void translate(double x, double y)
        {
            Translate translate = new Translate(x, y);
            
            double points[] = this.getPoints().stream().mapToDouble(Number::doubleValue).toArray();
            translate.transform2DPoints(points, 0, points, 0, points.length/2);
            for(int i = 0; i < points.length/2; i++)
            {
                this.setPoint(i, points[2*i], points[2*i+1]);
            }   
        }
    }