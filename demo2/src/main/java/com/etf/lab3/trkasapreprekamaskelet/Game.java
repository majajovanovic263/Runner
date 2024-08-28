package com.etf.lab3.trkasapreprekamaskelet;

import java.util.List;
import java.util.Random;

import com.etf.lab3.trkasapreprekamaskelet.objects.*;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application 
{
    private static final double WINDOW_WIDTH = 720.0;
	private double inmode = 0;
    private static final double WINDOW_HEIGHT = 400.0;
    
    private static final double OBSTACLE_SPAWN_DEPTH = 1200.0;
    
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.CADETBLUE;
    
    private static final int DEFAULT_OBSTACLE_TARGET_COUNT = 5;
    private static final long DEFAULT_OBSTACLE_CREATION_SPEED = 1500000000l;
	public static PointLight plight;
	private int lifes = 1;
	private Label l1, l2, l3;

	private double duplirajpoene = 0;

    private Group objects;
    private SubScene scene;
	private Scene mainscene;
    private Stage stage;
    
    private Player player;
    private Track track;
    
    private long lastObstacleCreatedTime = 0;
    private int obstacleCount = 0;
    private int targetObstacleCount = DEFAULT_OBSTACLE_TARGET_COUNT;
    private long obstacleCreationSpeed = DEFAULT_OBSTACLE_CREATION_SPEED;
    
    private static boolean isGameActive = true;
	private Group root;
	private Label tp;
	private AnimationTimer t;
	private int points = 0;

	private int flag = 0;
	private int k = 0;
    
    private final UpdateTimer timer = new UpdateTimer();

    private class UpdateTimer extends AnimationTimer
    {
        @Override
        public void handle(long now)
		{
			updateObstacles(now);
        }
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		stage = primaryStage;
		
		setupScene();
        showStage();
	}
	
	private void setupScene() 
	{
		mainscene = new Scene(root = new Group(), WINDOW_WIDTH,WINDOW_HEIGHT, false);
		scene = new SubScene(objects = new Group(),
						  WINDOW_WIDTH,
						  WINDOW_HEIGHT,
				          true,
					      SceneAntialiasing.BALANCED);
		root.getChildren().addAll(scene);
		scene.setFill(DEFAULT_BACKGROUND_COLOR);       
		mainscene.setCursor(Cursor.NONE);

		player = Player.InstantiatePlayer();
		scene.setCamera(player.getCamera());
        mainscene.setOnMouseMoved(player);
        mainscene.setOnKeyPressed(player);
        mainscene.setOnKeyReleased(player);
		plight = new PointLight(Color.TRANSPARENT);
		plight.getTransforms().addAll(
				new Translate(-250,-100, -10000)
		);
        
        track = new Track();

		Label tt = new Label("00:00");
		tt.getTransforms().addAll(new Translate(WINDOW_WIDTH*0.9, 5));
		tt.setTextFill(Color.BLACK);

		tp = new Label("0");
		tp.getTransforms().addAll(new Translate(WINDOW_WIDTH*0.1, 5));
		tp.setTextFill(Color.BLACK);

		l1 = new Label("+");
		l1.getTransforms().addAll(new Translate(WINDOW_WIDTH*0.1-20, 20));
		l1.setTextFill(Color.GREEN);
		l2 = new Label("+");
		l2.getTransforms().addAll(new Translate(WINDOW_WIDTH*0.1, 20));
		l2.setTextFill(Color.RED);
		l3 = new Label("+");
		l3.getTransforms().addAll(new Translate(WINDOW_WIDTH*0.1 + 20, 20));
		l3.setTextFill(Color.RED);


		t = new AnimationTimer() {
			int seconds = 0, minutes = 0, miliseconds = 0;
			@Override
			public void handle(long l) {
				miliseconds++;
				if(flag == 1){
					if(k<=10) {
						if (miliseconds == 60) {
							seconds++;
							k++;
							points+=2;
							miliseconds = 0;
						}
						if (seconds == 60) {
							minutes++;
							seconds = 0;
						}
					}
					else{
						flag = 0;
					}
				}
				else {
					if (miliseconds == 60) {
						seconds++;
						points++;
						miliseconds = 0;
					}
					if (seconds == 60) {
						minutes++;
						seconds = 0;
					}
				}
				tt.setText(minutes + ":" + seconds);
				tp.setText(String.valueOf(points));
			}
		};
		t.start();


		AmbientLight ambientLight = new AmbientLight(Color.WHITE);
		ambientLight.setOpacity(0.2);
		ambientLight.setTranslateZ(-1000);
		ambientLight.setBlendMode(BlendMode.SOFT_LIGHT);

		objects.getChildren().addAll(player, track, plight, ambientLight);
		root.getChildren().addAll(tt, tp, l1,l2,l3);
	}
	
	private void showStage() 
	{
		stage.setTitle("Trka sa preprekama");
		stage.setScene(mainscene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
		
		timer.start();
	}
	
	private void updateObstacles(long now)
	{		
		List<Node> children = objects.getChildren();
		 
		for (int i = 0; i < objects.getChildren().size(); i++) 
		{
			Node child = children.get(i);
			if (child instanceof Obstacle)
			{
				if(lifes == 1){
					l1.setTextFill(Color.GREEN);
					l2.setTextFill(Color.RED);
					l3.setTextFill(Color.RED);
				} else if (lifes == 2) {
					l1.setTextFill(Color.GREEN);
					l2.setTextFill(Color.GREEN);
					l3.setTextFill(Color.RED);
				}
				else if (lifes == 3){
					l1.setTextFill(Color.GREEN);
					l2.setTextFill(Color.GREEN);
					l3.setTextFill(Color.GREEN);
				} else if (lifes == 0) {
					l1.setTextFill(Color.RED);
					l2.setTextFill(Color.RED);
					l3.setTextFill(Color.RED);
				}
				if (child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds()))))
	            {
					objects.getChildren().remove(child);
					lifes--;
					if(lifes == 0) {
						isGameActive = false;
						t.stop();
						tp.getTransforms().addAll(new Translate(0.4*WINDOW_WIDTH, WINDOW_HEIGHT*0.5-5));
					}
	            }
	            
				if (obstacleCount > 0 && !((Obstacle)child).move())
				{
					obstacleCount--;
					objects.getChildren().remove(child);
				}
			}
			if(child instanceof GreenDiamond){
				((GreenDiamond) child).rotate();
				if (child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds()))))
				{
					objects.getChildren().remove(child);
					if(flag == 1)points+=2;
					else points++;
					tp.setText(String.valueOf(points));
				}

				if (!((GreenDiamond)child).move())
				{
					objects.getChildren().remove(child);
				}
			}
			if(child instanceof YellowDiamond){
				((YellowDiamond) child).rotate();
				if (child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds()))))
				{
					objects.getChildren().remove(child);
					flag = 1;
					k = 0;
				}

				if (!((YellowDiamond)child).move())
				{
					objects.getChildren().remove(child);
				}
			}
			if(child instanceof Life){
				((Life) child).rotate();
				if (child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds()))))
				{
					objects.getChildren().remove(child);
					if(lifes<3 && lifes>0)lifes++;
					if(lifes == 1){
						l1.setTextFill(Color.GREEN);
						l2.setTextFill(Color.RED);
						l3.setTextFill(Color.RED);
					} else if (lifes == 2) {
						l1.setTextFill(Color.GREEN);
						l2.setTextFill(Color.GREEN);
						l3.setTextFill(Color.RED);
					}
					else if (lifes == 3){
						l1.setTextFill(Color.GREEN);
						l2.setTextFill(Color.GREEN);
						l3.setTextFill(Color.GREEN);
					} else if (lifes == 0) {
						l1.setTextFill(Color.RED);
						l2.setTextFill(Color.RED);
						l3.setTextFill(Color.RED);
					}
				}

				if (!((Life)child).move())
				{
					objects.getChildren().remove(child);
				}
			}
			if(child instanceof Nozzle){
				((Nozzle) child).rotate();
				if (child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds()))))
				{
					objects.getChildren().remove(child);
					nozzleEverybody();
				}

				if (!((Nozzle)child).move())
				{
					objects.getChildren().remove(child);
				}
			}
		}
		
		if (obstacleCount < targetObstacleCount && now > lastObstacleCreatedTime + obstacleCreationSpeed) 
		{
			lastObstacleCreatedTime = now;
			Random r = new Random();
			int rand = 0;
			while (true){
				rand = r.nextInt(1001);
				if(rand !=0) break;
			}
			if(rand>300){
				GreenDiamond g = new GreenDiamond(new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH));

				if(inmode == 1){
					g.setTranslateY(-100);
				}
				objects.getChildren().add(g);

			}
			else if(rand<=300 && rand>200){
				YellowDiamond y = new YellowDiamond(new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH));
				if(inmode == 1){
					y.setTranslateY(-100);
				}
				objects.getChildren().add(y);
			}
			else if(rand>0 && rand<=100){
				Life ll = new Life(new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH));
				if(inmode == 1){
					ll.setTranslateY(-100);
				}
				objects.getChildren().add(ll);
			}
			else if(rand>100 && rand<=150){
				objects.getChildren().add(new Nozzle(new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH)));
			}


			objects.getChildren().add(new Obstacle(new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH)));
			obstacleCount++;
		}
	}

	private void nozzletoken() {

	}

	private void nozzleEverybody() {
		inmode = 1;
		Duration d1 = Duration.ZERO;
		Duration d2 = Duration.seconds(10);
		Duration d3 = Duration.seconds(11);
		for (int j = 0; j < objects.getChildren().size(); j++) {
			if ((objects.getChildren().get(j) instanceof GreenDiamond) || objects.getChildren().get(j) instanceof YellowDiamond || objects.getChildren().get(j) instanceof Life || objects.getChildren().get(j) instanceof Player) {
				KeyValue startValue = new KeyValue(objects.getChildren().get(j).translateYProperty(), -100, Interpolator.LINEAR);
				KeyFrame startFrame = new KeyFrame(d1, startValue);
				KeyValue midValue = new KeyValue(objects.getChildren().get(j).translateYProperty(), -100, Interpolator.LINEAR);
				KeyFrame midFrame = new KeyFrame(d2, midValue);
				KeyValue endValue = new KeyValue(objects.getChildren().get(j).translateYProperty(), 0, Interpolator.LINEAR);
				KeyFrame endFrame = new KeyFrame(d3, endValue);
				Timeline timeline = new Timeline(startFrame, midFrame, endFrame);
				timeline.play();
				if(objects.getChildren().get(j) instanceof Player){
					timeline.setOnFinished(event -> {
						inmode = 0;
					});
				}
			}
		}


	}


	public static void main(String[] args)
    {
        launch(args);
    }
    
    public static boolean isGameActive() 
    {
    	return isGameActive;
    }




}
