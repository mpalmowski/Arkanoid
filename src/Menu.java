import java.awt.*;
import java.util.LinkedList;

/**
 * App phase responsible for the game menu.
 * Contains a background and a fixed number of buttons.
 * Appears on the application start and after finishing a game.
 */
class Menu extends AppPhase {
    LinkedList<SimpleButton> buttons = new LinkedList<>();
    private static final String BG_PATH = "MenuBg.png";
    private static final int BUTTONS = 3;
    private String[] buttonTexts = new String[]{
            "",
            "START",
            "RANKING"
    };
    private ButtonPurpose[] buttonPurposes = new ButtonPurpose[]{
            ButtonPurpose.PlayerName,
            ButtonPurpose.Start,
            ButtonPurpose.Ranking
    };

    @Override
    void setBackground() {
        Image backgroundImage = new Image(BG_PATH);
        this.background = new Background(this, backgroundImage);
    }

    /**
     * Calculates suitable positions for the buttons, according to window dimensions.
     * Creates the buttons and adds them to the object list.
     */
    void addButtons() {
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
            SimpleButton button = new SimpleButton(buttonX.intValue(), buttonY.intValue(), buttonWidth, buttonHeight, buttonTexts[i], buttonPurposes[i]);
            this.buttons.add(button);
        }
    }

    /**
     * Displays current players name on a responsible button.
     *
     * @param playerName Specified players name
     */
    void setPlayerName(String playerName) {
        buttons.get(0).setText(playerName);
    }

    @Override
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
