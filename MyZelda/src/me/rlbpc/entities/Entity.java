package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;

public class Entity {
	public static BufferedImage LIVEPACK_EN = Game.spritesheet.getSprite(112,0,16,16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(128,0,16,16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(112,16,16,16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(128,16,16,16);
	//usar protected para poder usar nas classes derivadas
	protected double x,y;
	protected int width,height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(),this.getY(), null);
	}

	public int getX() {
		return (int)this.x;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
