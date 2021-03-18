package me.rlbpc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;
import me.rlbpc.world.Camera;

public class Entity {
	public static BufferedImage LIVEPACK_EN = Game.spritesheet.getSprite(96,0,Game.xyPixelsByTile,Game.xyPixelsByTile);
	public static BufferedImage WEAPON_EN= Game.spritesheet.getSprite(46,66,Game.xyPixelsByTile,Game.xyPixelsByTile);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(97,16,Game.xyPixelsByTile,Game.xyPixelsByTile);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(112,16,Game.xyPixelsByTile,Game.xyPixelsByTile);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(144,16,Game.xyPixelsByTile,Game.xyPixelsByTile);
	//usar protected para poder usar nas classes derivadas
	protected double x,y;
	protected int width,height, z;
	
	protected BufferedImage gun[];
	private BufferedImage sprite;
	
public void LoadGuns() {
	gun = new BufferedImage[5];
	for (int i = 0; i<5; i++) {
		gun[i] = Game.spritesheet.getSprite(32+(i*Game.xyPixelsByTile),64,Game.xyPixelsByTile,Game.xyPixelsByTile);
		//sequência direita com sombra [0], direita[1], para esquerda[2], para cima e baixo[3]
	
	}
}

public int maskx,masky,mwidth,mheight;
	
	public Entity(int x,int y,int width,int height,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight){
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
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
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y, null);
		//descomentar para debugar lifepack
		//g.setColor(Color.RED);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, height);
	
	}

	

	public void setWidth(int width) {
		this.width = width;
	}

	

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
