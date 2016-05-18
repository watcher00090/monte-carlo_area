public class Circle extends Domain { 
    double x0;
    double y0;
    double r;

    public Circle(double x0, double y0, double r) {
        this.x0 = x0;
        this.y0 = y0;   
        this.r = r;
    }
    
    public boolean contains(double x, double y) {
        double dist = Math.sqrt((x-x0)*(x-x0) + (y-y0)*(y-y0));
        return (dist <= r);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x0 - r, y0 - r, x0 + r, y0 + r);
    }

}
