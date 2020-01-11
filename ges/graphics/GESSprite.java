package ges.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GESSprite extends GESDrawableEntity {
    GESImage image = null;
    GESImageRect imageRect;

    public void setImage(GESImage image) {
        this.image = image;
        BufferedImage buffer = image.getImage();
        imageRect = new GESImageRect(0, 0, buffer.getWidth(), buffer.getHeight());
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
            int sx1 = imageRect.top;
            int sy1 = imageRect.left;
            int sx2 = imageRect.top + imageRect.width;
            int sy2 = imageRect.left + imageRect.height;

            g2d.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }
}
