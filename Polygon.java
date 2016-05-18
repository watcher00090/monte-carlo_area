import java.util.ArrayList;

public class Polygon extends Domain {
    public Point[] V;

    public Polygon(Point[] V) {
        this.V = V; 
    }

    public Polygon(ArrayList<Point> points) {
        V = new Point[points.size()];
        for (int i=0; i<V.length; i++) {
            V[i] = points.get(i);
        }
    }

    public boolean contains(double x, double y) {
        //if (!getBoundingBox().contains(x, y)) return false;
        return ((crossingNumber(new Point(x, y)) % 2) == 1);
    }
    
    public Rectangle getBoundingBox() {
        double minx = V[0].x;
        double miny = V[0].y;
        double maxx = V[0].x;
        double maxy = V[0].y;
        for (int i=0; i<V.length; i++) {
            if (V[i].x < minx) minx = V[i].x;
            if (V[i].y < miny) miny = V[i].y;
            if (V[i].x > maxx) maxx = V[i].x;
            if (V[i].y > maxy) maxy = V[i].y;
        }
        return new Rectangle(minx, maxy, maxx, miny);
    }
    
    //rays of choice: y = P.y, pointing to the right
    public int crossingNumber(Point P) {
System.out.println("CALLING_CROSSINGNUMBER("+P+")");
System.out.println();
        int cn = 0;
        double ε = 1E-3; //tolerance, to account for rounding error
        int i = 0; 
        while (i < V.length) {
            double x0 = V[i].x;
            double y0 = V[i].y;
            double x1 = (i == V.length - 1 ? V[0].x : V[i+1].x);
            double y1 = (i == V.length - 1 ? V[0].y : V[i+1].y);
            Point p0 = new Point(x0, y0);
            Point p1 = new Point(x1, y1);
System.out.println("VERTICES:");
System.out.println("(x0, y0)="+"("+x0+", "+y0+")");
System.out.println("(x1, y1)="+"("+x1+", "+y1+")");
            i++;
            double ymin = min(y0, y1);
            double ymax = max(y0, y1);
            if (isLeft(p0, p1, P) >= 0) {
                if (equals(P.y, y0, ε) && equals(P.y, y1, ε)) {
System.out.println("EDGE_LIES_ON_RAY)");
System.out.println();
                    continue; //if edge lies on ray, ignore 
                }
                else if (equals(P.y, y1, ε)) {
System.out.println("EDGE_ENDS_ON_RAY)");
System.out.println();
                    continue; //if edge ends on ray, ignore 
                }
                else if (equals(P.y, y0, ε)) {
System.out.println("EDGE_STARTS_ON_RAY)");
System.out.println();
                    cn++;
                }
                else {
                    if (ymin < P.y && P.y < ymax) {
System.out.println("P_IS_LEFT_OF_OR_ON_LINE_X0,Y0_TO_X1,Y1_AND_IN_THE_DESIRED_Y_RANGE");
                        cn++;
                    }
                }
            }
System.out.println();
        }
System.out.println("cn="+cn);
        return cn;
    }

    public static double min(double a, double b) {
        if (a < b) return a;
        return b;
    }

    public static double max(double a, double b) {
        if (a > b) return a;
        return b;
    }

    //precondition: tolerance >= 0
    public static boolean equals(double a, double b, double tolerance) {
        if (b - tolerance <= a && a <= b + tolerance) return true;
        return false;
    }

    //precondition: tolerance >= 0
    public static boolean greaterThanOrEquals(double a, double b, double tolerance) {
        if (a + tolerance <= b) return true;
        return false;
    }

    //precondition: tolerance >= 0
    public static boolean greaterThan(double a, double b, double tolerance) {
        if (a + tolerance < b)  return true;
        return false;
    }
    
    //Copyright 2000 softSurfer, 2012 Dan Sunday
    // isLeft(): tests if a point is Left|On|Right of an infinite line.
    //    Input:  three points P0, P1, and P2
    //    Return: >0 for P2 left of the line through P0 and P1
    //            =0 for P2  on the line, or if the line is horizontal and P2 is above the line
    //            <0 for P2  right of the line
    private static double isLeft(Point P0, Point P1, Point P2) {
        if (P0.x == P1.x) return P0.x - P2.x;
        if (P0.y == P1.y) return P2.y - P0.y; 
        return ((P1.x - P0.x) * (P2.y - P0.y) - (P2.x -  P0.x) * (P1.y - P0.y));
    }

   //Copyright 2000 softSurfer, 2012 Dan Sunday
    private int windingNumber(Point P) {
        int wn = 0;  
        for (int i=0; i<V.length-1; i++) {   // edge from V[i] to  V[i+1]
            if (V[i].y <= P.y) {          // start y <= P.y
                if (V[i+1].y  > P.y) {     // an upward crossing
                    if (isLeft(V[i], V[i+1], P) > 0) {  // P left of  edge
                        ++wn;            // have  a valid up intersect
                    }
                }
            }
            else {                        // start y > P.y (no test needed)
                if (V[i+1].y  <= P.y) {     // a downward crossing
                    if (isLeft(V[i], V[i+1], P) < 0) {  // P right of  edge
                        --wn;            // have  a valid down intersect
                    }
                }
            }
        }
        return wn;
    } 

    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 0);
        Point p3 = new Point(3, 1);
        Point p4 = new Point(2, 1);
        Point p5 = new Point(2, 2);
        Point p6 = new Point(1, 2);
        Point p7 = new Point(1, 3);
        Point p8 = new Point(0, 3); 
        Point pin1 = new Point(.5, .5); //directly inside
        Point pin2 = new Point(2.5, .5); //directly inside
        Point pin3 = new Point(.5, 3); //on the boundary, not a vertex
        Point pin4 = new Point(1, 2.5); //on the boundary, not a vertex
        Point pout1 = new Point(-5, 5); //random exterior points
        Point pout2 = new Point(5, 5);

        System.out.println("isLeft("+p1+", "+p2+", "+p3+") = "+isLeft(p1, p2, p3));
        System.out.println("isLeft("+p2+", "+p3+", "+p4+") = "+isLeft(p2, p3, p4));
        System.out.println("isLeft("+p3+", "+p4+", "+p5+") = "+isLeft(p3, p4, p5));
        System.out.println("isLeft("+p4+", "+p5+", "+p6+") = "+isLeft(p4, p5, p6));
        System.out.println();

        Point[] V = {p1, p2, p3, p4, p5, p6, p7, p8};
        Polygon poly = new Polygon(V);        

        System.out.println("P.contains"+p1+" = "+poly.contains(p1.x, p1.y));
        System.out.println();
        System.out.println("P.contains"+p2+" = "+poly.contains(p2.x, p2.y));
        System.out.println();
        System.out.println("P.contains"+p3+" = "+poly.contains(p3.x, p3.y));
        System.out.println();
        System.out.println("P.contains"+p4+" = "+poly.contains(p4.x, p4.y));
        System.out.println();
        System.out.println("P.contains"+p5+" = "+poly.contains(p5.x, p5.y));
        System.out.println();
        System.out.println("P.contains"+p6+" = "+poly.contains(p6.x, p6.y));
        System.out.println();
        System.out.println("P.contains"+p7+" = "+poly.contains(p7.x, p7.y));
        System.out.println();
        System.out.println("P.contains"+p8+" = "+poly.contains(p8.x, p8.y));
        System.out.println();
        System.out.println("P.contains"+pin1+" = "+poly.contains(pin1.x, pin1.y));
        System.out.println();
        System.out.println("P.contains"+pin2+" = "+poly.contains(pin2.x, pin2.y));
        System.out.println();
        System.out.println("P.contains"+pin3+" = "+poly.contains(pin3.x, pin3.y));
        System.out.println();
        System.out.println("P.contains"+pin4+" = "+poly.contains(pin4.x, pin4.y));
        System.out.println();
        System.out.println("P.contains"+pout1+" = "+poly.contains(pout1.x, pout1.y));
        System.out.println();
        System.out.println("P.contains"+pout2+" = "+poly.contains(pout2.x, pout2.y));
        System.out.println();
    }
}
