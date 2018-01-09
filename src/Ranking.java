import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;

class Ranking extends AppPhase {
    private LinkedList<String> playerNames = new LinkedList<>();
    private LinkedList<Integer> playerScores = new LinkedList<>();
    private String fileName;
    private static final int RANKINGSIZE = 10;

    Ranking(String fileName) {
        this.fileName = fileName;
        load();
    }

    private void load() {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                playerNames.add(scanner.next());
                playerScores.add(Integer.parseInt(scanner.next()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Invalid ranking file name");
            System.exit(1);
        }
    }

    void save() {
        sortLists();

        try {
            Formatter formatter = new Formatter(fileName);
            for (int i = 0; i < playerNames.size(); i++) {
                formatter.format("%s %s\n", playerNames.get(i), playerScores.get(i).toString());
            }
            formatter.close();
        } catch (FileNotFoundException e) {
            System.out.print("Invalid ranking file name");
            System.exit(1);
        }
    }

    void addPlayer(String playerName) {
        playerNames.add(playerName);
        playerScores.add(0);
    }

    void updateScore(String playerName, Integer playerScore) {
        int index = 0;
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).equals(playerName)) {
                index = i;
            }
        }

        if (playerScores.get(index) < playerScore) {
            playerScores.set(index, playerScore);
        }
    }

    private void sortLists(){
        int maxScore, maxScoreIndex;
        for(int i=0; i<RANKINGSIZE; i++){
            maxScore = 0;
            maxScoreIndex = i;
            if(playerScores.size() > i) {
                for (int j = i; j < playerScores.size(); j++) {
                    if (playerScores.get(j) > maxScore) {
                        maxScore = playerScores.get(j);
                        maxScoreIndex = j;
                    }
                }
                playerNames.add(i, playerNames.get(maxScoreIndex));
                playerNames.remove(maxScoreIndex + 1);
                playerScores.add(i, playerScores.get(maxScoreIndex));
                playerScores.remove(maxScoreIndex + 1);
            }
        }
        for(int i=RANKINGSIZE; i<playerScores.size(); i++){
            playerNames.remove(i);
            playerScores.remove(i);
        }
    }

    @Override
    void render(Graphics graphics) {
        if (background != null)
            background.render(graphics);

        graphics.setColor(Color.WHITE);

        Double upperMargin = windowHeight / 3;
        Double sideMargin = windowWidth / 5;
        double textHeight = graphics.getFontMetrics().getStringBounds("A", graphics).getHeight();
        Double tableHeight = (textHeight + 5) * 10 + 5;
        Double tableWidth = windowWidth - 2 * sideMargin;
        graphics.drawRect(sideMargin.intValue(), upperMargin.intValue(), tableWidth.intValue(), tableHeight.intValue());

        Double nameX, textY, scoreX;
        double scoreWidth;
        nameX = sideMargin + 5;
        textY = upperMargin + textHeight;
        for(int i=0; i<RANKINGSIZE; i++){
            if(playerNames.size() > i) {
                graphics.drawString(playerNames.get(i), nameX.intValue(), textY.intValue());
                scoreWidth = graphics.getFontMetrics().getStringBounds(playerScores.get(i).toString(), graphics).getWidth();
                scoreX = windowWidth - sideMargin - scoreWidth - 5;
                graphics.drawString(playerScores.get(i).toString(), scoreX.intValue(), textY.intValue());
                textY += textHeight + 5;
            }
        }

        Double instructionX, instructionY, instructionWidth;
        final String instruction = "PRESS   ESC   TO   RETURN   TO   MENU";
        instructionWidth = graphics.getFontMetrics().getStringBounds(instruction, graphics).getWidth();
        instructionX = (windowWidth - instructionWidth)/2;
        instructionY = windowHeight - windowHeight/18 - textHeight;
        graphics.drawString(instruction, instructionX.intValue(), instructionY.intValue());
    }
}
