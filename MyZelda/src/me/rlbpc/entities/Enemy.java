package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;
import me.rlbpc.main.Sound;
import me.rlbpc.world.Camera;
import me.rlbpc.world.World;

public class Enemy extends Entity {
	/*atenuadorDeInimigo1 - Porcentagem da velocidade do player que o inimigo terá, varia de 0 a 1
	 *atenuadorDeInimigo2 - Randomização da movimentação do inimigo, varia de 0 a 100, tipicamente entre 30 e 70)  
	 */
	public static double atenuadorDeInimigo1 = 0.4; 
	private double speed = Player.speed*atenuadorDeInimigo1;
	//tamanho da máscara de colisão do inimigo
	private int maskxenemy = 8, maskyenemy = 8, maskwenemy = 7, maskhenemy = 8; //ajuste do tamanho da máscara de colisão do inimigo
	private int maskxplayer = 8, maskyplayer = 8, maskwplayer = 6, maskhplayer = 8; //ajuste do tamanho da máscara de colisão do player
	private int frames = 0, index = 0, maxFrames = 20, maxIndex = 1, life = 10;
	private BufferedImage[] sprites;
	private boolean isDamaged = false;
	private int damagedFrames = 10, damageCurrent = 0;

	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, Game.xyPixelsByTile, Game.xyPixelsByTile);
		sprites[1] = Game.spritesheet.getSprite(128, 16, Game.xyPixelsByTile, Game.xyPixelsByTile);
	}
	
	public void tick() {
			
		if (this.isColiddingWithPlayer() == false ) {
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
				
				
		} else {
			//código perda de vida
			if(Game.rand.nextInt(100) < 10) {
				Sound.hurtEffect.play();
				Game.player.life -= Game.rand.nextInt(4);
				Game.player.isDamaged = true;
				System.out.println("Vida: " + Game.player.life);
				if (Game.player.life <= 0) {
					System.out.println("Você morreu!");
				}
			}
		}
		collidingBullet();
		if(life <= 0) {
			destroySelf();
			return;
		}
		if(isDamaged) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damagedFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
		}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
		
	}
	public void collidingBullet() {
		for(int i=0; i<Game.bullets.size();i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskxplayer, this.getY() + maskyplayer,maskwplayer,maskhplayer);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),Game.xyPixelsByTile,Game.xyPixelsByTile);	
		return enemyCurrent.intersects(player); 	
	}
	
	//Checa se os inimigos estão colidindo entre si
	public boolean isColidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + maskxenemy, yNext + maskyenemy,maskwenemy,maskhenemy);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskxenemy, e.getY() + maskyenemy,maskwenemy,maskhenemy);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;		
			}
		}
		
		return false;
	}
	
	public void render (Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index],this.getX() - Camera.x, this.getY() - Camera.y,null);
		} else if(isDamaged) {
				g.drawImage(Entity.ENEMY_FEEDBACK,this.getX() - Camera.x, this.getY() - Camera.y,null);
		}
	}
}

