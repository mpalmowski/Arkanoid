import javax.swing.*;
import java.awt.*;

class Window extends Canvas {

    private JFrame frame;

    Window(int width, int height, String title, Game game){
        frame = new JFrame(title);
        Dimension dimension = new Dimension(width, height);

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
    }

    Insets getInsets(){
        return frame.getInsets();
    }
}
