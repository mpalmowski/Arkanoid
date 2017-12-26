import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    private Model model;

    MouseListener(Model model) {
        this.model = model;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.handleMousePressed(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
