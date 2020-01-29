package ges.graphics;

import java.awt.*;

public class GESCircleShape extends GESDrawableEntity {

    private int radius;
    private Color backgroundColor;
    private Color borderColor;
    private int borderSize;

    public GESCircleShape(int radius) {
        this.radius = radius;
        backgroundColor = Color.BLACK;
        borderColor = Color.BLACK;
        borderSize = 0;
    }

    protected void initBound(){
        axisAlignBoundingBox = new GESAxisAlignBoundingBox(radius*2, radius*2, position);
    }

    public void setRadius(int radius) {
        this.radius = radius;
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getRadius() {
        return radius;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.setColor(backgroundColor);
        g2d.fillOval((int)position.getX(), (int)position.getY(), radius*2, radius*2);

        if(borderSize > 0) {
            int margin = borderSize / 2;
            BasicStroke stroke = new BasicStroke(borderSize);
            g2d.setStroke(stroke);
            g2d.setColor(borderColor);
            g2d.drawOval((int) position.getX() + margin, (int) position.getY() + margin, radius*2 - margin*2, radius*2 - margin*2);
        }
    }
}
