//(x, y) = upper left
//mathematical rectangle
public class Rectangle extends Domain {
    double x;
    double y;
    double width;
    double height;
        
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBoundingBox() {
        return this;
    }

    public boolean contains(double x, double y) {
        return (this.x <= x && x <= (this.x + width) && (this.y - height) <= y && y <= this.y);
    }

    public double area() {
        return width*height;
    }

    public String toString() {
        return "Rectangle: upper left=(" + x + ", " + y + "), width=" + width + ", height=" + height;
    }
    
}

