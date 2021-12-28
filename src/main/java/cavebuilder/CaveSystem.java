package cavebuilder;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CaveSystem {

    private final ArrayList<Chunk> chunks = new ArrayList<>();

    private Random random = new Random();
    private final int chunkWidth = 100;
    private final int chunkHeight = 220;
    private final int maxLevel = 30;
    private final int defaultLevel = maxLevel / 2;
    private final int minLevel = 1;
    private final int levelSize;
    private final int screenHeight;
    private final int screenWidth;
    private final int changePercent = 75;
    private final int chunkNarrowing = 50;
    private final int maxNarrowing = 2;

    private int tendency = 0;

    public CaveSystem(int screenHeight, int screenWidth){
        this.screenWidth = screenWidth;
        levelSize = (screenHeight - chunkHeight) / (maxLevel + 1);
        for(int i = 0; i < screenWidth / chunkWidth + 5; i++){
            addChunk();
        }
        this.screenHeight = screenHeight;
    }

    public void update(double speed, double time){

        for(Chunk chunk : chunks){
            chunk.updatePosition((int) (speed * time));
        }

        while (!chunks.isEmpty() && chunks.get(0).getX() <= -chunkWidth){
            chunks.remove(0);
        }

        while (chunks.size() < screenWidth / chunkWidth + 5) {
            addChunk();
        }
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.GREY);
        for(Chunk chunk : chunks){
            chunk.draw(gc, screenHeight, screenWidth);
        }
    }

    public int getTopAt(int x){
        int i = 0;
        while(!(chunks.get(i).getX() <= x && chunks.get(i).getX() + chunkWidth >= x)){
            i++;
        }
        return chunks.get(i).getTop();
    }

    public int getBottomAt(int x){
        int i = 0;
        while(!(chunks.get(i).getX() <= x && chunks.get(i).getX() + chunkWidth >= x)){
            i++;
        }
        return chunks.get(i).getBottom();
    }


    private void addChunk() {
        if (tendency == 0) {
            tendency = random.nextInt(maxLevel) - (maxLevel - 1)/2;
        }

        int prevX = -chunkWidth;
        int prevLevel = defaultLevel;

        if (!chunks.isEmpty()) {
            Chunk prev = chunks.get(chunks.size() - 1);
            prevX = prev.getX();
            prevLevel = prev.getLevel();
        }

        if (prevLevel == minLevel && tendency <= 0){

            tendency = random.nextInt(maxLevel / 5) + 1;
        }

        else if (prevLevel == maxLevel && tendency >= 0){
            tendency = -(random.nextInt(maxLevel / 5) + 1);
        }

        int level = prevLevel;

        boolean change = random.nextInt(100) < changePercent;
        if (tendency < 0){
            if (change) {
                level--;
            }
            tendency++ ;
        }
        else if(tendency > 0){
            if (change) {
                level++;
            }
            tendency--;
        }

        chunks.add(new Chunk(levelSize, chunkWidth, chunkHeight, level, prevX + chunkWidth, chunkNarrowing, maxNarrowing));
    }
}
