package me.rlbpc.entities;

import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;
import me.rlbpc.world.World;

public class Enemy extends Entity {

	private double speed = Player.speed*0.6;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if(x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
			x+=speed;
		} else if (x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())) {
			x-=speed;
		}
		if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))) {
			y+=speed;
		} else if (y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))) {
			y-=speed;
		}
	}

}
