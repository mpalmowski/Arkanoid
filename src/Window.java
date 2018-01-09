import javax.swing.*;
import java.awt.*;

/**
 * Main window of the application
 */
class Window extends Canvas {

    private JFrame frame;

    /**
     * Creates a window and displays it.
     * @param width specified width
     * @param height specified height
     * @param title specified title
     * @param view specified view
     */
    Window(int width, int height, String title, View view){
        frame = new JFrame(title);
        Dimension dimension = new Dimension(width, height);

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(view);
        frame.setVisible(true);
    }

    Insets getInsets(){
        return frame.getInsets();
    }
}
