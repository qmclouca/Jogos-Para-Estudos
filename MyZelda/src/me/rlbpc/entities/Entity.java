package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
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
