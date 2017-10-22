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

        if(keyCode == KeyEvent.VK_ESCAPE)
            System.exit(0);

        if(keyCode == KeyEvent.VK_LEFT){
            GameObject tempObject = handler.objects.get(0);
            tempObject.setVelX(-5);
        }
        else if(keyCode == KeyEvent.VK_RIGHT){
            GameObject tempObject = handler.objects.get(0);
            tempObject.setVelX(5);
        }
        else if(keyCode == KeyEvent.VK_UP){
            GameObject tempObject = handler.objects.get(1);
            tempObject.setAllowMovement(true);
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
