import java.awt.*;

public class Controller extends Canvas implements Runnable{

    private static final Integer WIDTH = 480, HEIGHT = 600;
    private Thread thread;
    private Handler handler;
    private View view;
    private boolean running = false;
    private double boardWidth, boardHeight;

    Controller(){
        handler = new Handler();

        view = new View(WIDTH, HEIGHT, handler);
        view.createWindow();
        boardWidth = view.getBoardWidth();
        boardHeight = view.getBoardHeight();
        view.addKeyListener(new KeyInput(handler));

        view.addObjects();
        

        start();
    }

    private synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void run(){
        long before;
        long now;
        double ns = 1000000000/60.0;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int framesPerSecond = 0;

        before = System.nanoTime();
        while (running){
            now = System.nanoTime();
            delta += (now - before)/ns;
            before = now;

            while(delta >= 1){
                tick();
                delta--;
            }

            view.render();
            framesPerSecond ++;

            if(System.currentTimeMillis() - timer > 1000){
                timer = System.currentTimeMillis();
                //System.out.print("FPS: " + framesPerSecond + System.lineSeparator());
                framesPerSecond = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }

    private synchronized  void stop(){
        try{
            thread.join();
            running = false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
