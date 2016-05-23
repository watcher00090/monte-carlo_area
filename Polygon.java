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
        if (!getBoundingBox().contains(x, y)) { 
System.out.println("outside boundingbox");
            return false;
        }
        if (isOnBoundary(x, y)) return true;
        return ((crossingNumber(new Point(x, y)) % 2) == 1);
    }
    
    public Rectangle getBoundingBox() {
        double minx = V[0].x;
        double miny = V[0].y;
        double maxx = V[0].x;
        double maxy = V[0].y;
        for (int i=1; i<V.length; i++) {
            if (V[i].x < minx) minx = V[i].x;
            if (V[i].y < miny) miny = V[i].y;
            if (V[i].x > maxx) maxx = V[i].x;
            if (V[i].y > maxy) maxy = V[i].y;
        }
        return new Rectangle(minx, maxy, maxx - minx, maxy - miny);
    }

    public boolean isOnBoundary(double x, double y) {
        for (int i=0; i<V.length; i++) { 
            double x0 = V[i].x;
            double y0 = V[i].y;
            double x1 = V[(i+1) % V.length].x;
            double y1 = V[(i+1) % V.length].y;
            Point p0 = new Point(x0, y0);
            Point p1 = new Point(x1, y1);
            double xmin = min(x0, x1);
            double xmax = max(x0, x1);
            double ymin = min(y0, y1);
            double ymax = max(y0, y1);
            if (isLeft(p0, p1, new Point(x, y)) == 0 && xmin <= x && x <= xmax && ymin <= y && y <= ymax) {
                return true;
            }
        }
        return false;
    }
    
    //rays of choice: directed horizontally from the point to the right
    //returns unpredicatable results for points on the boundary of P
    public int crossingNumber(Point P) {
        int cn = 0;
        double ε = 1E-3; //tolerance, to account for rounding error
        for (int i=0; i<V.length; i++) { 
            double x0 = V[i].x;
            double y0 = V[i].y;
            double x1 = V[(i+1) % V.length].x;
            double y1 = V[(i+1) % V.length].y;
            Point p0 = new Point(x0, y0);
            Point p1 = new Point(x1, y1);
            double ymin = min(y0, y1);
            double ymax = max(y0, y1);
            if (isLeft(p0, p1, P) >= 0) {
                if (equals(P.y, y0, ε) && equals(P.y, y1, ε)) {
                    continue; //if edge lies on ray, ignore 
                }
                else if (equals(P.y, y1, ε)) {
                    continue; //if edge ends on ray, ignore 
                }
                else if (equals(P.y, y0, ε)) {
                    cn++; //if edge starts on ray, increment
                }
                else {
                    if (ymin < P.y && P.y < ymax) {
                        cn++; //normal intersection
                    }
                }
            }
        }
        return cn;
    }

    public String toString() {
        String result = "";
        for (int i=0; i<V.length; i++) {
            result = result + " " + V[i].toString();
        }
        return result;
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
    
    //Copyright 2000 softSurfer, 2012 Dan Sunday (Dan's original method has been completely refactored)
    //    Input:  three points P0, P1, and P2
    //    Return: >0 for P2 left of the line through P0 and P1, or above the line if the line is horizontal
    //            =0 for P2 on the line
    //            <0 for P2 right of the line through P0 or P1, or below the line if the line is horizontal
    private static double isLeft(Point P0, Point P1, Point P2) {
        if (P0.x == P1.x) return P0.x - P2.x; //vertical line
        if (P0.y == P1.y) return P2.y - P1.y; //horizontal line 
        double m = (P1.y - P0.y) / (P1.x - P0.x);
        double y_at_P2x = m * (P2.x - P0.x) + P0.y;
        return Math.signum(m) * (P2.y - y_at_P2x);
    }

    //unit tests
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
        System.out.println(V);

        System.out.println("poly.contains"+p1+" = "+poly.contains(p1.x, p1.y));
        System.out.println("poly.contains"+p2+" = "+poly.contains(p2.x, p2.y));
        System.out.println("poly.contains"+p3+" = "+poly.contains(p3.x, p3.y));
        System.out.println("poly.contains"+p4+" = "+poly.contains(p4.x, p4.y));
        System.out.println("poly.contains"+p5+" = "+poly.contains(p5.x, p5.y));
        System.out.println("poly.contains"+p6+" = "+poly.contains(p6.x, p6.y));
        System.out.println("poly.contains"+p7+" = "+poly.contains(p7.x, p7.y));
        System.out.println("poly.contains"+p8+" = "+poly.contains(p8.x, p8.y));
        System.out.println("poly.contains"+pin1+" = "+poly.contains(pin1.x, pin1.y));
        System.out.println("poly.contains"+pin2+" = "+poly.contains(pin2.x, pin2.y));
        System.out.println("poly.contains"+pin3+" = "+poly.contains(pin3.x, pin3.y));
        System.out.println("poly.contains"+pin4+" = "+poly.contains(pin4.x, pin4.y));
        System.out.println("poly.contains"+pout1+" = "+poly.contains(pout1.x, pout1.y));
        System.out.println("poly.contains"+pout2+" = "+poly.contains(pout2.x, pout2.y));
        System.out.println();

        Point p9 = new Point(0, 0);
        Point p10 = new Point(1, 0);
        Point p11 = new Point(2, 1);
        Point p12 = new Point(1, 2);
        Point p13 = new Point(0, 2);
        Point p14 = new Point(-1, 1);
        
        Point[] V2 = {p9, p10, p11, p12, p13, p14};
        Polygon poly2 = new Polygon(V2);
        System.out.println(V2);

        System.out.println("poly2.contains"+p1+" = "+poly2.contains(p1.x, p1.y));
        System.out.println("poly2.contains"+p2+" = "+poly2.contains(p2.x, p2.y));
        System.out.println("poly2.contains"+p3+" = "+poly2.contains(p3.x, p3.y));
        System.out.println("poly2.contains"+p4+" = "+poly2.contains(p4.x, p4.y));
        System.out.println("poly2.contains"+p5+" = "+poly2.contains(p5.x, p5.y));
        System.out.println("poly2.contains"+p6+" = "+poly2.contains(p6.x, p6.y));
        System.out.println("poly2.contains"+p7+" = "+poly2.contains(p7.x, p7.y));
        System.out.println("poly2.contains"+p8+" = "+poly2.contains(p8.x, p8.y));
        System.out.println("poly2.contains"+pin1+" = "+poly2.contains(pin1.x, pin1.y));
        System.out.println("poly2.contains"+pin2+" = "+poly2.contains(pin2.x, pin2.y));
        System.out.println("poly2.contains"+pin3+" = "+poly2.contains(pin3.x, pin3.y));
        System.out.println("poly2.contains"+pin4+" = "+poly2.contains(pin4.x, pin4.y));
        System.out.println("poly2.contains"+pout1+" = "+poly2.contains(pout1.x, pout1.y));
        System.out.println("poly2.contains"+pout2+" = "+poly2.contains(pout2.x, pout2.y));
    }

}       
    

