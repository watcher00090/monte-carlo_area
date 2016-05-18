import java.util.*;

public class MonteCarlo {

    public static void montecarlo_area(Domain domain) {
        int totalin = 0;
        int totalpoints = 0;
        Rectangle boundingbox = domain.getBoundingBox();
        double boundaryarea = boundingbox.area();
        double regionarea = 0;
        double minx = boundingbox.x1;
        double miny = boundingbox.y2;
        double maxx = boundingbox.x2;
        double maxy = boundingbox.y1;
        while (true) {
            double x = minx + (maxx - minx) * Math.random(); //w/in [minx, maxx)
            double y = miny + (maxy - miny) * Math.random();
            if (domain.contains(x, y)) totalin++;
            totalpoints++;
            regionarea = ((double) totalin / (double) totalpoints) * boundaryarea;
            System.out.println(regionarea);
        }
    }

    public static void main(String[] args) {
        Domain d = null;
        if (args[0].equalsIgnoreCase("circle")) {
            double x0 = Double.parseDouble(args[1]);
            double y0 = Double.parseDouble(args[2]);
            double r = Double.parseDouble(args[3]);
            d = new Circle(x0, y0, r);
            montecarlo_area(d);
        }
        if (args[0].equalsIgnoreCase("polygon")) {
            ArrayList<Point> V = new ArrayList<Point>();
            int i = 0;
            while (i < args.length && !args[i].equalsIgnoreCase("contains")) {
                V.add(new Point(args[i]));
                i++;
            }
            d = new Polygon(V);
            if (i < args.length && args[i].equalsIgnoreCase("contains")) {
                for (int j=i+1; j<args.length; j++) {
                    Point P = new Point(args[j]);
                    System.out.println("polygon.contains"+P+" = " + d.contains(P.x, P.y));
                }
            }
            else {
                montecarlo_area(d);
            }
        }
    }

}

