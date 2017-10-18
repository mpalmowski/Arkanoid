import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private int keyCode;

    KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT){
            GameObject tempObject = handler.objects.get(0);
            tempObject.setVelX(-5);
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            GameObject tempObject = handler.objects.get(0);
            tempObject.setVelX(5);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT){
            GameObject tempObject = handler.objects.get(0);
            tempObject.setVelX(0);
        }
    }
}
