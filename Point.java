public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //precondition: pointstring = '(' NUMBER ',' NUMBER ')'
    public Point(String pointstring) {
        int commaindex = pointstring.indexOf(',');    
        String xstring = pointstring.substring(1, commaindex);
        String ystring = pointstring.substring(commaindex+1, pointstring.length()-1);
        x = Double.parseDouble(xstring);
        y = Double.parseDouble(ystring);
    }

    public boolean equals(Point Q) {
        return (x == Q.x && y == Q.y);
    }

    public String toString() {
        return "("+x+","+y+")";
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public static void main(String[] args) {
        for (int i=0; i<args.length; i++) {
            Point P = new Point(args[i]);
            System.out.println(P.toString() + " ");
        }
    }
}
