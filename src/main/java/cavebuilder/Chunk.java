package cavebuilder;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

import static java.lang.Math.min;

public class Chunk {
    private int x;
    private int top;
    private int bottom;
    private final int level;
    private final int chunkWidth;
    private final Random rand = new Random();

    public Chunk(int levelSize, int chunkWidth, int chunkHeight, int level, int x, int narrowing, int maxNarrowing){
        this.level = level;
        this.bottom = level * levelSize;
        this.top = bottom + chunkHeight;
        int i = 0;
        while (rand.nextInt(100) < narrowing && i < maxNarrowing){
            i++;
            this.bottom += levelSize;
        }
        i = 0;
        while (rand.nextInt(100) < narrowing && i < maxNarrowing){
            i++;
            this.top -= levelSize;
        }

        this.x = x;
        this.chunkWidth = chunkWidth;
    }

    public int getX() {
        return x;
    }

    public int getLevel() {
        return level;
    }

    public void updatePosition(int speed){
        x -= speed;
    }

    public int getTop(){
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public void draw(GraphicsContext gc, int screenHeight, int screenWidth){
        gc.fillRect(x, screenHeight - bottom, min(chunkWidth, screenWidth - x), bottom);
        gc.fillRect(x, 0, min(chunkWidth, screenWidth - x), screenHeight - top);
    }
}
