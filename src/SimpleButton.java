import java.awt.*;

/**
 * Button composed of a rectangle with a text in the middle.
 * Has a specified purpose used by a responsible class.
 */
class SimpleButton {
    private int x = 0, y = 0;
    private Double width = 0.0, height = 0.0;
    private String text;
    boolean exists = true;
    private ButtonPurpose buttonPurpose;

    /**
     * Creates a button in a specified point in a window, with specified dimensions, text and purpose.
     *
     * @param x             Specified x coordinate
     * @param y             Specified x coordinate
     * @param width         Specified width
     * @param height        Specified height
     * @param text          Specified text
     * @param buttonPurpose Specified purpose
     */
    SimpleButton(int x, int y, Double width, Double height, String text, ButtonPurpose buttonPurpose) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.buttonPurpose = buttonPurpose;
    }

    void setText(String text) {
        this.text = text;
    }

    void render(Graphics graphics) {
        Double textX, textY, textWidth, textHeight;
        textWidth = graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        textHeight = graphics.getFontMetrics().getStringBounds(text, graphics).getHeight();
        textX = x + (width - textWidth) / 2;
        textY = y + (height - textHeight) / 2 + textHeight;

        graphics.drawString(text, textX.intValue(), textY.intValue());
        graphics.drawRect(x, y, width.intValue(), height.intValue());
    }

    /**
     * @return Rectangle of width, height and coordinates same as the object.
     */
    Rectangle getBounds() {
        return new Rectangle(x, y, width.intValue(), height.intValue());
    }

    ButtonPurpose getButtonPurpose() {
        return buttonPurpose;
    }
}
