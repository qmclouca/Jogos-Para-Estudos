package me.rlbpc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;

public class Entity {
	public static BufferedImage LIVEPACK_EN = Game.spritesheet.getSprite(96,0,16,16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(112,0,16,16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(97,16,16,16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(112,16,16,16);
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
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(),this.getY(), null);
		g.setColor(Color.RED);
	
	}

	

	public void setWidth(int width) {
		this.width = width;
	}

	

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
