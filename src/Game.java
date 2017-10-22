import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

    private Thread thread;
    private Handler handler;
    private boolean running = false;
    private Insets windowInsets;

    private static final int WIDTH = 640, HEIGHT = 640;
    public int boardWidth, boardHeight;

    Game(){
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        Window window = new Window(WIDTH, HEIGHT, "Arkanoid", this);

        windowInsets = window.getInsets();
        getWindowDimensions();
        handler.setBoardWidth(boardWidth);
        handler.setBoardHeight(boardHeight);

        addObjects();
    }

    private void getWindowDimensions(){
        boardHeight = HEIGHT - windowInsets.top - windowInsets.bottom;
        boardWidth = WIDTH - windowInsets.left - windowInsets.right;
    }

    private void addObjects(){
        handler.addObject(new Paddle(boardWidth /2 - 100/2, boardHeight - 22, handler, ID.Paddle));
        handler.addObject(new Ball(boardWidth /2 - 10/2, boardHeight - 100, handler, ID.Ball));
    }

    synchronized void start(){
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

            render();
            framesPerSecond ++;

            if(System.currentTimeMillis() - timer > 1000){
                timer = System.currentTimeMillis();
                System.out.print("FPS: " + framesPerSecond + System.lineSeparator());
                framesPerSecond = 0;
            }
        }

        stop();
    }

    private void tick(){
        handler.tick();
    }

    private void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
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
