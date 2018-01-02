import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;

class Ranking {
    private Background background = null;
    private LinkedList<String> playerNames = new LinkedList<>();
    private LinkedList<Integer> playerScores = new LinkedList<>();

    private String fileName;
    Ranking(String fileName) {
        this.fileName = fileName;
        load();
    }

    void setBackGround(Background background) {
        this.background = background;
    }

    void render(Graphics graphics) {
        if (background != null)
            background.render(graphics);

        double textHeight = graphics.getFontMetrics().getStringBounds("GAME OVER", graphics).getHeight();
        textHeight += 5;
        textHeight *= playerNames.size();
        graphics.drawRect(0,0,0,0);
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
        for (String playerName1 : playerNames) {
            if (playerName1.equals(playerName)) {
                return;
            }
        }

        playerNames.add(playerName);
        playerScores.add(0);
    }

    void renamePlayer(String oldName, String newName) {
        for (String playerName1 : playerNames) {
            if (playerName1.equals(newName)) {
                for (String playerName2 : playerNames) {
                    if (playerName2.equals(oldName)) {
                        deletePlayer(oldName);
                    }
                }
                return;
            }
        }

        int index = -1;
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).equals(oldName)) {
                index = i;
                break;
            }
        }
        playerNames.set(index, newName);
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

    private void deletePlayer(String playerName) {
        playerNames.remove(playerName);
    }
}
