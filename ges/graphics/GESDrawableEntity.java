package ges.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GESDrawableEntity implements GESDrawable {

    protected GESVector2d position;
    protected GESAxisAlignBoundingBox axisAlignBoundingBox;
    protected AffineTransform transform;
    protected double rotation = 0.0;

    public GESDrawableEntity(){
        position = new GESVector2d();
        transform = new AffineTransform();
    }

    public GESVector2d getPosition() {
        return position;
    }

    public void setPosition(GESVector2d position) {
        this.position = position;
    }

    public void rotate(double theta, double ancrx, double ancry){
        rotation += theta;
        transform.rotate(rotation, ancrx, ancry);
    }

    public void rotate(double theta){
        GESVector2d origin = getAABB().getCenterPosition();
        rotate(theta, origin.getX(), origin.getY());
    }

    public void scale(double sx, double sy){
        transform.scale(sx, sy);
    }

    public void setRotation(double rotation){
        this.rotation = 0;
        rotate(rotation);
    }

    public void move(GESVector2d direction){
        position.setX(direction.getX() + position.getX());
        position.setY(direction.getY() + position.getY());
    }

    protected abstract void initBound();

    protected abstract void onDraw(Graphics2D g2d);

    public GESAxisAlignBoundingBox getAABB(){
        initBound();
        return axisAlignBoundingBox;
    }

    public void setPosition(double x, double y){
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public void draw(Graphics2D g2d){
        AffineTransform saveTransform = g2d.getTransform();
        g2d.transform(transform);
        onDraw(g2d);
        g2d.setTransform(saveTransform);
        transform = new AffineTransform();
    }
}
