import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    private Menu menu;
    private Model model;
    private View view;
    private int mouseX, mouseY;

    public MouseListener(Menu menu, Model model, View view) {
        this.menu = menu;
        this.model = model;
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        for(SimpleButton button : menu.buttons){
            Rectangle buttonBounds = button.getBounds();
            if(buttonBounds.contains(mouseX, mouseY)){
                model.setGameState(State.Game);
                view.setGameState(State.Game);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
