package me.rlbpc.entities;

import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;
import me.rlbpc.world.World;

public class Enemy extends Entity {
	/*atenuadorDeInimigo1 - Porcentagem da velocidade do player que o inimigo ter�, varia de 0 a 1
	 *atenuadorDeInimigo2 - Randomiza��o da movimenta��o do inimigo, varia de 0 a 100, tipicamente entre 30 e 70)  
	 */
	public static int atenuadorDeInimigo1 = 1, atenuadorDeInimigo2 = 30; 
	private double speed = Player.speed*atenuadorDeInimigo1;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if (Game.rand.nextInt(100) < atenuadorDeInimigo2) {
			if(x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
				x+=speed;
			} else if (x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())) {
				x-=speed;
			}
			if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))) {
				y+=speed;
			} else if (y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))) {
				y-=speed;
			}
		}
	}
}
