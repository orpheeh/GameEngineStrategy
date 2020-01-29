package ges.graphics;

import java.awt.*;

public class GESRectangleShape extends GESDrawableEntity {

    private Color backgroundColor;
    private Color borderColor;
    private int borderSize;
    private int width;
    private int height;

    public GESRectangleShape(int width, int height) {
        this.width = width;
        this.height = height;
        backgroundColor = Color.black;
        borderColor = Color.black;
        borderSize = 0;
    }

    protected void initBound(){
        axisAlignBoundingBox = new GESAxisAlignBoundingBox(width, height, position);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    @Override
    public void onDraw(Graphics2D g2d) {

        g2d.setColor(backgroundColor);
        g2d.fillRect((int)position.getX(), (int)position.getY(), width, height);

        if(borderSize > 0) {
            int margin = borderSize/2;
            BasicStroke stroke = new BasicStroke(borderSize);
            g2d.setStroke(stroke);
            g2d.setColor(borderColor);
            g2d.drawRect((int) position.getX() + margin, (int) position.getY() + margin, width-margin*2, height-margin*2);
        }
    }
}
