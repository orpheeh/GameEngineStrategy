package ges.graphics;

import java.awt.*;

public class GESAxisAlignBoundingBox {

    private GESVector2d position;
    private int width;
    private int height;

    public GESAxisAlignBoundingBox(int width, int height, GESVector2d position){
        this.width = width;
        this.height = height;
        this.position = position;
    }

    public void setPosition(GESVector2d position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    public GESVector2d getCenterPosition(){
        GESVector2d center = new GESVector2d();
        center.setX(position.getX() + width/2);
        center.setY(position.getY() + height/2);
        return center;
    }

    public boolean intersect(GESAxisAlignBoundingBox other){
        return !(this.position.getX() + this.width < other.position.getX() ||
                this.position.getX() > other.position.getX() + other.width ||
                this.position.getY() + this.height < other.position.getY() ||
                this.position.getY() > other.position.getY() + other.width);
    }

    public boolean intersect(GESVector2d p){
        return p.getX() >= this.position.getX() &&
        p.getX() <= this.position.getX() + width &&
        p.getY() >= this.position.getY() &&
        p.getY() <= this.position.getY() + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
