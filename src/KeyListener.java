import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    private Model model;

    KeyListener(Model model) {
        this.model = model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        model.handleKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        model.handleKeyReleased(e.getKeyCode());
    }
}
