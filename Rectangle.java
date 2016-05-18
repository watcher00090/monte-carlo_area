public class Rectangle extends Domain {
    double x1;
    double y1; 
    double x2;
    double y2;
        
    public Rectangle(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Rectangle getBoundingBox() {
        return this;
    }

    public boolean contains(double x, double y) {
        return (x1 <= x && x <= x2 && y1 <= y && y <= y2);
    }

    public double area() {
        return (x2 - x1) * (y2 - y1);
    }
    
}

