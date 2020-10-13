package me.rlbpc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.rlbpc.entities.*;
import me.rlbpc.main.Game;

public class World {
	
	public static Tile[] tiles;
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
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Tile.TILE_FLOOR);
					switch (pixelAtual) {
					case 0xFF353500:
						//FLOOR
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Tile.TILE_FLOOR);
					break;
					case 0xFFFFFFFF:
						//Parede
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Tile.TILE_WALL);
					break;
					case 0xFF0026FF:
						//Player
						Game.player.setX(xx*Game.xyPixelsByTile);
						Game.player.setY(yy*Game.xyPixelsByTile);
					break;
					case 0xFF834900: 
						//Arma
						Weapon arma = new Weapon(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Game.xyPixelsByTile,Game.xyPixelsByTile,Entity.WEAPON_EN);
						//arma.setMask(3, 0, 8, 8);
						Game.entities.add(arma);
					break;
					case 0xFF792300:
						//INIMIGO
						Enemy inimigo = new Enemy(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Game.xyPixelsByTile,Game.xyPixelsByTile,Entity.ENEMY_EN);
						Game.entities.add(inimigo);
						//lista de inimigos para checagem de colisões entre inimigos
						Game.enemies.add(inimigo);
					break;
					case 0xFF701B79: 
						//VIDA
						LifePack livePack = new LifePack(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Game.xyPixelsByTile,Game.xyPixelsByTile,Entity.LIVEPACK_EN);
						//livepack.setMask(3, 0, 8, 8);
						Game.entities.add(livePack);
					break;
					case 0xFFA18C00:
						//BULLET
						Bullet bulletPack = new Bullet(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Game.xyPixelsByTile,Game.xyPixelsByTile,Entity.BULLET_EN);
						//bulletPack.setMask(3,0,8,8);
						Game.entities.add(bulletPack);
					break;
					default:
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*Game.xyPixelsByTile,yy*Game.xyPixelsByTile,Tile.TILE_FLOOR);
					break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//box para detecção de colisões
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext/Game.xyPixelsByTile;
		int y1 = yNext/Game.xyPixelsByTile;
		
		int x2 = (xNext+(Game.xyPixelsByTile-1))/Game.xyPixelsByTile;
		int y2 = yNext/Game.xyPixelsByTile;
		
		int x3 = xNext/Game.xyPixelsByTile;
		int y3 = (yNext+(Game.xyPixelsByTile-1))/Game.xyPixelsByTile;
		
		int x4 = (xNext+(Game.xyPixelsByTile-1))/Game.xyPixelsByTile;
		int y4 = (yNext+(Game.xyPixelsByTile-1))/Game.xyPixelsByTile;
		
		//verifica se tem parede nas próximas posições direita, esquerda, para cima e para baixo e retorna false (uso de !)
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) || 
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) || 
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4; //Operador >> desloca bits para a direita e completa os espaços deixados na esquerda com o bit de sinal (bit mais significativo), ou seja, se o bit de sinal for 1 preenche os espaços com 1, caso contrário preenche os espaços com 0.
		int ystart = Camera.y >> 4; 
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for (int xx = xstart; xx <= (xfinal) ; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
					if(xx < 0 || yy < 0 || xx >= WIDTH || yy >=HEIGHT)
						continue;
					Tile tile = tiles[xx + (yy*WIDTH)]; 
					tile.render(g);
			}
		}
	}
}
