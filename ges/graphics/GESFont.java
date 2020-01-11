package ges.graphics;

import java.awt.*;
import java.io.IOException;

public class GESFont {
    private Font font;

    void loadFromFile(String fileName, int fontFormat) throws IOException, FontFormatException {
        font = Font.createFont(fontFormat, ClassLoader.getSystemResourceAsStream(fileName));
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
