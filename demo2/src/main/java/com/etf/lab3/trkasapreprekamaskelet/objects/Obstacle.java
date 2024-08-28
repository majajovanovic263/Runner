package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.obstacles.Hurdles;
import com.etf.lab3.trkasapreprekamaskelet.obstacles.LargeHurdles;
import com.etf.lab3.trkasapreprekamaskelet.obstacles.ObstacleBody;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;

import java.util.Random;

public class Obstacle extends GameObject 
{
    private static double OBSTACLE_SPEED = 4.0;
	private static double step = 0.00000000002;
    
	public ObstacleBody obstacleBody;
	
	public Obstacle(Position position)
	{
		super(position);

		Random r = new Random();
		int rand = 0;
		while (true){
			rand = r.nextInt(1001);
			if(rand !=0) break;
		}
		if(rand>=500) obstacleBody = new Hurdles(position);
		else obstacleBody = new LargeHurdles(position);
		
		this.setTranslateX(position.getX());
		this.setTranslateY(position.getY() - obstacleBody.getObstacleHeight() / 2);
		this.setTranslateZ(position.getZ());
		
		this.getChildren().add(obstacleBody);
	}
	
	public boolean move()
	{
		if(Game.isGameActive() == true) {
			OBSTACLE_SPEED += step;
			this.setTranslateZ(this.getTranslateZ() - OBSTACLE_SPEED);
		}
		return isOnTrack();
	}
	
	public boolean isOnTrack() 
	{
	    return this.getTranslateZ() > 0;
	}
	public boolean ifObstacleHit(Player player){
		if (this.getBoundsInParent().intersects((player.localToScene(player.getParentBounds())))) return true;
		return false;
	}

}
