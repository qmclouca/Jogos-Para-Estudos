package me.rlbpc.entities;

import java.awt.Color;
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
	public static double atenuadorDeInimigo1 = 0.8; 
	private double speed = Player.speed*atenuadorDeInimigo1;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
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
	}
	
	//Checa se os inimigos estão colidindo
	public boolean isColidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext, yNext,Game.xyPixelsByTile,Game.xyPixelsByTile);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e== this) continue;
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(),Game.xyPixelsByTile,Game.xyPixelsByTile);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;		
			}
		}
		
		return false;
	}
	//descomentar para ver a máscara de colisão
	/*
	public void render (Graphics g) {
		super.render(g);
		g.setColor(Color.BLUE);
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, Game.xyPixelsByTile, Game.xyPixelsByTile);
	}*/
	}

