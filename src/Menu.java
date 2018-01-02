import java.awt.*;
import java.util.LinkedList;

class Menu {
    private Background background = null;
    LinkedList<SimpleButton> buttons = new LinkedList<>();

    private static final int BUTTONS = 3;
    private String[] buttonTexts = new String[]{
            "",
            "START",
            "RANKING"
    };
    private ButtonFunction[] buttonFunctions = new ButtonFunction[]{
            ButtonFunction.PlayerName,
            ButtonFunction.Start,
            ButtonFunction.Ranking
    };

    void setBackGround(Background background) {
        this.background = background;
    }

    void addButtons(double windowWidth, double windowHeight){
        double breakBetweenButtons = windowHeight / 15;
        double upperButtonMargin = windowHeight / 2.5;
        double lowerButtonMargin = windowHeight / 10;
        double sideButtonMargin = windowWidth / 4;
        Double buttonWidth = windowWidth - 2 * sideButtonMargin;
        Double buttonHeight = (windowHeight - upperButtonMargin - lowerButtonMargin - (BUTTONS - 1) * breakBetweenButtons) / BUTTONS;

        for (int i = 0; i < BUTTONS; i++) {
            Double buttonX, buttonY;
            buttonX = sideButtonMargin;
            buttonY = upperButtonMargin + i * buttonHeight + i * breakBetweenButtons;
            SimpleButton button = new SimpleButton(buttonX.intValue(), buttonY.intValue(), buttonWidth, buttonHeight, buttonTexts[i], buttonFunctions[i]);
            addButton(button);
        }
    }

    void addButton(SimpleButton button) {
        this.buttons.add(button);
    }

    void setPlayerName(String playerName){
        buttons.get(0).setText(playerName);
    }

    void render(Graphics graphics) {
        if (background != null)
            background.render(graphics);

        graphics.setColor(Color.WHITE);

        for (SimpleButton button : buttons) {
            if (button.exists) {
                button.render(graphics);
            }
        }
    }
}
