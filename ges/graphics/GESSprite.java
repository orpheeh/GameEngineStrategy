package ges.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GESSprite extends GESDrawableEntity {
    public enum Direction { LTR, RTL }

    GESImage image = null;
    GESImageRect imageRect;

    Direction direction = Direction.LTR;

    public void setImage(GESImage image) {
        this.image = image;
        BufferedImage buffer = image.getImage();
        imageRect = new GESImageRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setImageRect(GESImageRect imageRect){
        this.imageRect = imageRect;
    }

    @Override
    protected void initBound(){
        axisAlignBoundingBox = new GESAxisAlignBoundingBox(imageRect.width, imageRect.height, position);
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        if(image != null && imageRect != null) {
            int dx1 = (int) position.getX();
            int dy1 = (int) position.getY();
            int dx2 = (int) position.getX() + imageRect.width;
            int dy2 = (int) position.getY() + imageRect.height;

            int sx2, sx1;

            if(direction == Direction.LTR){
                sx1 = imageRect.top;
                sx2 = imageRect.top + imageRect.width;
            } else if(direction == Direction.RTL){
                sx2 = imageRect.top;
                sx1 = imageRect.top + imageRect.width;
            } else {
                sx1 = imageRect.top;
                sx2 = imageRect.top + imageRect.width;
            }

            int sy1 = imageRect.left;
            int sy2 = imageRect.left + imageRect.height;

            g2d.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }
}
