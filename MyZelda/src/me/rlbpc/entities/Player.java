package me.rlbpc.entities;

import java.awt.image.BufferedImage;

public class Player extends Entity {
	
	public boolean right,up,left,down;
	public int speed = 4;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		if (right) {
			x+=4;
		} else if (left) {
			x-=4;
		} else if (down) {
			y-=4;			
		} else if (up) {
			y+=4;
		}
	}
	

}
