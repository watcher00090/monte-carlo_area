import java.util.*;

public class MonteCarlo {

    public static void montecarlo_area(Domain domain) {
        int totalin = 0;
        int totalpoints = 0;
        Rectangle boundingbox = domain.getBoundingBox();
        double boundaryarea = boundingbox.area();
        double regionarea = 0;
        double minx = boundingbox.x;
        double maxy = boundingbox.y;
        double maxx = boundingbox.x + boundingbox.width;
        double miny = boundingbox.y - boundingbox.height;
        while (true) {
            double x = minx + (maxx - minx) * Math.random(); //w/in [minx, maxx)
            double y = miny + (maxy - miny) * Math.random();
            if (domain.contains(x, y)) {
System.out.println("(" + x + ", " + y + ") lies inside");
                totalin++;
            }
            else {
System.out.println("(" + x + ", " + y + ") lies outside");
            }
System.out.println();
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
            Rectangle box = d.getBoundingBox();
//System.out.println(box);
//System.out.println("box.area()="+box.area());
            montecarlo_area(d);
        }
        else if (args[0].equalsIgnoreCase("polygon")) {
            ArrayList<Point> V = new ArrayList<Point>();
            for (int i=1; i<args.length; i++) {
//System.out.println(new Point(args[i]));
                V.add(new Point(args[i]));
            }
            d = new Polygon(V);
            Rectangle box = d.getBoundingBox();
//System.out.println(box);
//System.out.println("box.area()="+box.area());
            montecarlo_area(d);
        }
    }

}

