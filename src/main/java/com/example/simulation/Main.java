package com.example.simulation;

import cavebuilder.CaveSystem;
import fuzzy.FuzzyDriver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import submarine.Submarine;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Main extends Application {

    private static final int width = 1000;
    private static final int height = 800;
    private static final int chartFieldWidth = 200;
    private static final int submarineHeight = 30;
    private static final int submarineWidth = 50;
    private static final double timeTick = 0.1;
    private static final int lookahead = 55;

    DecimalFormat formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));



    public void start(Stage stage){

        stage.setTitle("Simulation");
        formatter.setRoundingMode( RoundingMode.DOWN );
        Canvas canvas = new Canvas(width + chartFieldWidth, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        CaveSystem cs = new CaveSystem(height, width);
        Submarine sub = new Submarine(submarineWidth, submarineHeight, height / 2 + submarineHeight / 2, 50);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc, cs, sub)));
        tl.setCycleCount(Timeline.INDEFINITE);

        stage.setScene(new Scene(new StackPane(canvas)));

        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc, CaveSystem cs, Submarine sub) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, width, height);
        cs.draw(gc);
        sub.draw(gc, height);

        gc.setFill(Color.BLACK);
        gc.fillRect(width, 0, chartFieldWidth, height);

        int bot = cs.getBottomAt(sub.getX() + submarineWidth + lookahead);
        int top = cs.getTopAt(sub.getX() + submarineWidth + lookahead);
        int topDist = Math.max(0, top - sub.getY());
        int botDist = Math.max(0, sub.getY() - submarineHeight - bot);
        double velocity = sub.getVelocity();

        double acceleration = FuzzyDriver.evalForce(botDist, topDist, velocity);

        Font font = new Font("Serif", 24);
        gc.setFont(font);

        gc.setFill(Color.WHITE);
        if (velocity > 0.4){
            gc.setFill(Color.GREEN);
        }
        else if (velocity < -0.4){
            gc.setFill(Color.RED);
        }

        gc.fillText("Velocity: " + formatter.format(sub.getVelocity()),width + 30,100);

        gc.setFill(Color.WHITE);
        if (acceleration > 0.2){
            gc.setFill(Color.GREEN);
        }
        else if (acceleration < -0.2){
            gc.setFill(Color.RED);
        }

        gc.fillText("Acc: " + formatter.format(acceleration),width + 30,200);

        int currTop = cs.getTopAt(sub.getX() + submarineWidth/2);
        int currTopDist = Math.max(0, currTop - sub.getY());
        gc.setFill(getColor(currTopDist));
        gc.fillText("Dist top: " + currTopDist,width + 30,300);

        int currBot = cs.getBottomAt(sub.getX() + submarineWidth/2);
        int currBotDist = Math.max(0, sub.getY() - submarineHeight - currBot);
        gc.setFill(getColor(currBotDist));
        gc.fillText("Dist bot: " + currBotDist,width + 30,400);

        sub.update(acceleration, timeTick);
        cs.update(50, timeTick);
    }

    public Color getColor(int dist){
        if (dist > 80){
            return Color.GREEN;
        }
        if (dist > 40){
            return Color.WHITE;
        }
        return Color.RED;
    }

    public static void main(String[] args) {
        launch(args);
    }
}