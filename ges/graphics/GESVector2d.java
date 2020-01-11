package ges.graphics;

import java.awt.*;

public class GESVector2d {
    private double x;
    private double y;

    public GESVector2d(){
        this.x = 0;
        this.y = 0;
    }

    public GESVector2d(double x, double y){
        this.x = x;
        this.y = y;
    }
    public  GESVector2d(Point p){ this(p.x, p.y); }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point toPoint(){ return new Point((int)x, (int)y); }

}
