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

    public void translate(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    public GESVector2d createByTranslate(double dx, double dy){
        return new GESVector2d(x + dx, y + dy);
    }

    public double getLenght(){
        return Math.sqrt(x*x + y*y);
    }

    public GESVector2d multiply(double scalar){
        return new GESVector2d(x * scalar, y * scalar);
    }

    public GESVector2d normalize(){
        return this.multiply(1.0 / getLenght());
    }

    public void translate(GESVector2d v){
        x += v.x;
        y += v.y;
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public static double dot(GESVector2d v1, GESVector2d v2){
        return v1.x*v2.x + v1.y*v2.y;
    }

    public static double det(GESVector2d v1, GESVector2d v2){
        return v1.x*v2.y - v1.y*v2.x;
    }
}
