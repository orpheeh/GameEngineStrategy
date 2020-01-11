package ges.graphics;

import java.awt.*;

public class GESLabel extends GESDrawableEntity {

    private String text;
    private FontMetrics fontMetrics;
    private GESFont font;
    private Color color;

    public GESLabel(String text) {
        this.text = text;
        font = new GESFont();
        font.setFont(new Font("Arial", Font.PLAIN, 18));
        color = Color.WHITE;
    }

    public void setFont(GESFont font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    protected void initBound() {
        if(fontMetrics == null){
            axisAlignBoundingBox = new GESAxisAlignBoundingBox(1, 1, position);
        } else {
            int width = fontMetrics.stringWidth(text);
            int height = fontMetrics.getHeight();
            axisAlignBoundingBox = new GESAxisAlignBoundingBox(width, height, position);
        }
    }

    @Override
    protected void onDraw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setFont(font.getFont());
        fontMetrics = g2d.getFontMetrics();

        g2d.drawString(text, (int)position.getX(), (int)position.getY() + fontMetrics.getAscent());
    }
}
