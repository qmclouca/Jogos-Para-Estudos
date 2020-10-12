package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;
import me.rlbpc.world.Camera;
import me.rlbpc.world.World;

public class Enemy extends Entity {
	/*atenuadorDeInimigo1 - Porcentagem da velocidade do player que o inimigo terá, varia de 0 a 1
	 *atenuadorDeInimigo2 - Randomização da movimentação do inimigo, varia de 0 a 100, tipicamente entre 30 e 70)  
	 */
	public static double atenuadorDeInimigo1 = 0.4; 
	private double speed = Player.speed*atenuadorDeInimigo1;
	//tamanho da máscara de colisão do inimigo
	private int maskx = 8, masky = 8, maskw = 7, maskh = 9;
	private int frames = 0, index = 0, maxFrames = 20, maxIndex = 1;
	private BufferedImage[] sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, Game.xyPixelsByTile, Game.xyPixelsByTile);
		sprites[1] = Game.spritesheet.getSprite(128, 16, Game.xyPixelsByTile, Game.xyPixelsByTile);
	}
	
	public void tick() {
	
			if(x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) 
					&& !isColidding((int)(x+speed), this.getY())) {
				x+=speed;
			} else if (x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
					&& !isColidding((int)(x-speed), this.getY())) {
				x-=speed;
			}
			if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
					&& !isColidding(this.getX(), (int)(y+speed))) {
				y+=speed;
			} else if (y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
					&& !isColidding(this.getX(), (int)(y-speed))) {
				y-=speed;
			}
				frames++;
				if (frames == maxFrames) {
					frames = 0;
					index++;
					if (index > maxIndex) index = 0;
				}
	}
	
	//Checa se os inimigos estão colidindo
	public boolean isColidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky,maskw,maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e== this) continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky,maskw,maskh);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;		
			}
		}
		
		return false;
	}
	
	public void render (Graphics g) {
		g.drawImage(sprites[index],this.getX() - Camera.x, this.getY() - Camera.y,null);
	
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw,  maskh);
	}
	}

