package me.rlbpc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;

//Objetos est�ticos do cen�rio (vari�veis do tipo static inicializam apenas uma vez)
public class Tile {
	public static BufferedImage  TILE_FLOOR = Game.spritesheet.getSprite(0,0,Game.xyPixelsByTile,Game.xyPixelsByTile);
	public static BufferedImage  TILE_WALL = Game.spritesheet.getSprite(16,0,Game.xyPixelsByTile,Game.xyPixelsByTile);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,x - Camera.x, y - Camera.y ,null);
	}
}
