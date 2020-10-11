package me.rlbpc.entities;

import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;

public class Enemy extends Entity {

	private double speed = 0.6;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if(x < Game.player.getX()) {
			x+=speed;
		} else if (x > Game.player.getX()) {
			x-=speed;
		}
	}

}
