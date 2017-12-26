import java.awt.*;

class SimpleButton {
    private int x=0, y=0;
    private Double width=0.0, height=0.0;
    private String text;
    boolean exists = true;
    ButtonFunction buttonFunction;

    SimpleButton(int x, int y, Double width, Double height, String text, ButtonFunction buttonFunction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.buttonFunction = buttonFunction;
    }

    void render(Graphics graphics) {
        Double textX, textY, textWidth, textHeight;
        textWidth = graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        textHeight = graphics.getFontMetrics().getStringBounds(text, graphics).getHeight();
        textX = x + (width - textWidth)/2;
        textY = y + (height - textHeight)/2 + textHeight;

        graphics.drawString(text, textX.intValue(), textY.intValue());
        graphics.drawRect(x, y, width.intValue(), height.intValue());
    }

    Rectangle getBounds(){
        return new Rectangle(x, y, width.intValue(), height.intValue());
    }

    ButtonFunction getButtonFunction(){
        return buttonFunction;
    }
}
