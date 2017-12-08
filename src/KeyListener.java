import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    private Game game;
    private int keyCode;

    KeyListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE)
            System.exit(0);

        if(keyCode == KeyEvent.VK_LEFT){
            GameObject tempObject = game.objects.get(0);
            tempObject.setVelX(-5.0);
        }
        else if(keyCode == KeyEvent.VK_RIGHT){
            GameObject tempObject = game.objects.get(0);
            tempObject.setVelX(5.0);
        }
        else if(keyCode == KeyEvent.VK_UP){
            GameObject tempObject = game.objects.get(1);
            tempObject.setAllowMovement(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT){
            GameObject tempObject = game.objects.get(0);
            tempObject.setVelX(0.0);
        }
    }
}
