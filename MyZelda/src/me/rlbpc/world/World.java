package me.rlbpc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.rlbpc.entities.*;
import me.rlbpc.main.Game;

public class World {
	
	private Tile[] tiles;
	public static int WIDTH,HEIGHT;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0,map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy< map.getWidth(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					switch (pixelAtual) {
					case 0xFF353500:
						//FLOOR
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					break;
					case 0xFFFFFFFF:
						//Parede
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_WALL);
					break;
					case 0xFF0026FF:
						//Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					break;
					case 0xFF834900: 
						//Arma
						Game.entities.add(new Weapon(xx*16,yy*16,16,16,Entity.WEAPON_EN));
					break;
					case 0xFF792300:
						//INIMIGO
						Game.entities.add(new Enemy(xx*16,yy*16,16,16,Entity.ENEMY_EN));
					break;
					case 0xFF701B79: 
						//VIDA
						Game.entities.add(new LifePack(xx*16,yy*16,16,16,Entity.LIVEPACK_EN));
					break;
					case 0xFFA18C00:
						//BULLET
						Game.entities.add(new Bullet(xx*16,yy*16,16,16,Entity.BULLET_EN));
					break;
					default:
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void render(Graphics g) {
		for (int xx = 0; xx< WIDTH; xx++) {
			for (int yy = 0; yy< HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
		
	}
}
