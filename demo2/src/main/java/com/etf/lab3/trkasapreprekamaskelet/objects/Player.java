package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;


public class Player extends GameObject implements EventHandler<Event> 
{
	private static final double DEFAULT_POSITION_X = 0;
	private static final double DEFAULT_POSITION_Y = 0;
	private static final double DEFAULT_POSITION_Z = 0;
	
    public static final double NEAR_CLIP = 0.1;
    public static final double FAR_CLIP = 10_000;
    public static final double FIELD_OF_VIEW = 60;
	private int angle = 0;
	
    private PerspectiveCamera camera;
    private Box shape;
	private double y;
	private boolean jumping = false;
    
    private int lane = 1;
	private int l = 0;
	private Timeline timeline, timeline1,timeline2;
	private Group Steve;
	private Box head,body,arm1,arm2,leg1,leg2;
	
	public Player(Position position)
	{
		super(position);
		
		shape = new Box(30.0, 30.0, 30.0);
		shape.setVisible(false);

		head = new Box(5,5,5);
		body = new Box(10,15,5);
		arm1 = new Box(5,10,5);
		arm2 = new Box(5,10,5);
		leg1 = new Box(5,10,5);
		leg2 = new Box(5,10,5);

		PhongMaterial s = new PhongMaterial(Color.WHITE);
		head.setMaterial(s);
		body.setMaterial(s);
		arm1.setMaterial(s);
		arm2.setMaterial(s);
		leg1.setMaterial(s);
		leg2.setMaterial(s);

		head.setTranslateY(-10);
		arm1.setTranslateY(-2.5);
		arm1.setTranslateX(-5);
		arm2.setTranslateY(-2.5);
		arm2.setTranslateX(5);
		leg1.setTranslateY(10);
		leg1.setTranslateX(-2.5);
		leg2.setTranslateY(10);
		leg2.setTranslateX(2.5);


		Steve = new Group();
		Steve.getChildren().addAll(head,body,arm1,arm2,leg1,leg2);
		Steve.setTranslateY(10);
		Steve.setTranslateZ(-10);





        camera = new PerspectiveCamera(true);
        camera.setNearClip(NEAR_CLIP);
        camera.setFarClip(FAR_CLIP);
        camera.setFieldOfView(FIELD_OF_VIEW);
        
        super.setTranslateY(position.getY());
		y = super.getTranslateY();
        
        super.getChildren().addAll(shape, camera, Steve);
	}
	
	public static Player InstantiatePlayer() 
	{
		return new Player(new Position(DEFAULT_POSITION_X,
									   DEFAULT_POSITION_Y,
									   DEFAULT_POSITION_Z));
	}

	@Override
	public void handle(Event event) 
	{		
		if (event instanceof KeyEvent)
		{
			KeyEvent keyEvent = (KeyEvent)event;
	        if (keyEvent.getCode() == KeyCode.ESCAPE &&
	        	keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
	        {
	            System.exit(0);
	        }
	        else 
	        {
	    		if (!Game.isGameActive())
	    		{
	    			return;
	    		}
	    		
		        if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) &&
			        	  keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
		        {
		        	moveLeft();
		        }
		        else if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) &&
			        	  keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
		        {
		        	moveRight();
		        }
				else if ((keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
				{
					jump();
				}
				else if ((keyEvent.getCode() == KeyCode.L) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
				{
					light();
				}
				else if ((keyEvent.getCode() == KeyCode.R) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
				{
					rotatecamera(-1);
				}
				else if ((keyEvent.getCode() == KeyCode.T) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
				{
					rotatecamera(1);
				} else if ((keyEvent.getCode() == KeyCode.DIGIT2) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
					camera.setTranslateY(-20);
					camera.setTranslateZ(-100);
				} else if ((keyEvent.getCode() == KeyCode.DIGIT1) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
					camera.setTranslateY(0);
					camera.setTranslateZ(0);
				}
				else if((keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) &&
						keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
					land();
				}
			}
		}
	}

	private void land() {
		Duration d1 = Duration.ZERO;
		Duration d2 = Duration.seconds(0.1);
		Duration d3 = Duration.seconds(0.2);
		KeyValue startValue = new KeyValue(this.translateYProperty(), 0);
		KeyFrame startFrame = new KeyFrame(d1, startValue);
		KeyValue midValue = new KeyValue(this.translateYProperty(), 0);
		KeyFrame midFrame = new KeyFrame(d2, midValue);
		KeyValue endValue = new KeyValue(this.translateYProperty(), 0);
		KeyFrame endFrame = new KeyFrame(d3, endValue);
		timeline2 = new Timeline(startFrame, midFrame, endFrame);
		if(jumping) timeline.stop();
		timeline2.play();
	}

	private void rotatecamera(int i) {
		if(angle + i<15 && angle + i>-15){
			camera.getTransforms().addAll(new Rotate(i,Rotate.X_AXIS));
			angle += i;
		}
		else return;
	}

	public Bounds getParentBounds() 
	{
		return this.shape.getBoundsInParent();
	}
	
    public Camera getCamera() 
    {
        return camera;
    }
    
    private void moveLeft() 
    {
    	if (lane == 0) 
    	{
    		return;
    	}
    	
    	lane--;
    	this.setTranslateX(this.getTranslateX() - Track.LANE_WIDTH);
    }
    
    private void moveRight() 
    {
    	if (lane == 2) 
    	{
    		return;
    	}
    	
    	lane++;
    	this.setTranslateX(this.getTranslateX() + Track.LANE_WIDTH);
    }

	private void jump()
	{
		boolean j = false;
		if(jumping)
			 j = true;
		jumping = true;
		Duration d1 = Duration.ZERO;
		Duration d2 = Duration.seconds(0.5);
		Duration d3 = Duration.seconds(1);
		KeyValue startValue = new KeyValue(this.translateYProperty(), 0);
		KeyFrame startFrame = new KeyFrame(d1, startValue);
		KeyValue midValue;
		if(j == false) midValue= new KeyValue(this.translateYProperty(), -30);
		else midValue = new KeyValue(this.translateYProperty(), -60);
		KeyFrame midFrame = new KeyFrame(d2, midValue);
		KeyValue endValue = new KeyValue(this.translateYProperty(), 0);
		KeyFrame endFrame = new KeyFrame(d3, endValue);
		timeline = new Timeline(startFrame, midFrame, endFrame);
		timeline.setOnFinished(event -> {
			jumping = false;
		});



		timeline.play();
	}
	public void light(){
		if(Game.isGameActive() == false){
			Game.plight.setColor(Color.RED);
		}
		if(l == 0){
			Game.plight.setColor(Color.WHITE);
			l = 1;
		}
		else{
			Game.plight.setColor(Color.TRANSPARENT);
			l = 0;
		}
	}
	public void movelegsandarms(){
		Duration d1 = Duration.ZERO;
		Duration d2 = Duration.seconds(0.5);
		Duration d3 = Duration.seconds(1);
		Duration d4 = Duration.seconds(1.5);
		Duration d5 = Duration.seconds(2);



		timeline1.play();
	}

}
