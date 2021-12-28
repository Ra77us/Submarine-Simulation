package submarine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Submarine {

    final Image img = new Image("file:" + System.getProperty("user.dir") + "\\src\\main\\resources\\images\\submarine.png");
    private double velocity = 0;
    private final int width;
    private final int height;
    private int y;
    private final int x;

    public Submarine(int width, int height, int y, int x){
        this.width = width;
        this.height = height;
        this.y = y;
        this.x = x;
    }

    public void update(double acceleration, double time){
        velocity += acceleration  * time;
        y += velocity;
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }

    public double getVelocity() {
        return velocity;
    }

    public void draw(GraphicsContext gc, int screenHeight){
        gc.drawImage(img, x, screenHeight - y, width, height);
    }
}
