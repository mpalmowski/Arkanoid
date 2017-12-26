import java.awt.*;

public class Controller extends Canvas implements Runnable {

    private static final Integer WIDTH = 450, HEIGHT = 600;
    private Thread thread;
    private View view;
    private Model model;
    private boolean running = false;
    private State gameState = State.Menu;

    Controller() {
        Game game = new Game();
        Menu menu = new Menu();

        view = new View(WIDTH, HEIGHT, game, menu);
        model = new Model(this, game, menu);

        view.createWindow();
        view.getWindowParameters();

        view.addKeyListener(new KeyListener(model));
        view.addMouseListener(new MouseListener(model));

        view.setGameState(gameState);
        model.setGameState(gameState);

        addObjects();

        start();
    }

    void setGameState(State gameState){
        this.gameState = gameState;
        view.setGameState(gameState);
        model.setGameState(gameState);
    }

    private void addObjects() {
        model.addMenuBackground();
        view.render();

        model.addMenuButtons();
        view.render();

        model.addGameBackground();
        view.render();

        model.addPaddle();
        view.render();

        model.addBall();
        view.render();

        model.calculateBricksPositions();
        model.addBricks();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void run() {
        long before;
        long now;
        double ns = 1000000000 / 60.0;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int framesPerSecond = 0;

        before = System.nanoTime();
        while (running) {
            now = System.nanoTime();
            delta += (now - before) / ns;
            before = now;

            while (delta >= 1) {
                model.tick();
                delta--;
            }

            view.render();
            framesPerSecond++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.print("FPS: " + framesPerSecond + System.lineSeparator());
                framesPerSecond = 0;
            }
        }
        stop();
    }

    private synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
