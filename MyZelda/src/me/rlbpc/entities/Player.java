package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;
import me.rlbpc.world.Camera;
import me.rlbpc.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public static double speed = 1.4;
	
	private int frames = 0,maxFrames = 5,index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage playerDamage;
	private BufferedImage[] upPlayer, downPlayer;
	private boolean arma = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false,mouseShoot = false;
	
	public static double life = 100,maxLife=100;
	public int mx,my;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 16, Game.xyPixelsByTile, Game.xyPixelsByTile);
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.xyPixelsByTile),16,Game.xyPixelsByTile,Game.xyPixelsByTile);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.xyPixelsByTile),32,Game.xyPixelsByTile,Game.xyPixelsByTile);
		}
		for (int i = 0; i < 4; i++) {	
			upPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.xyPixelsByTile),48,Game.xyPixelsByTile,Game.xyPixelsByTile);
		}
		for (int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.xyPixelsByTile),0,Game.xyPixelsByTile,Game.xyPixelsByTile);
		}
	}
	
	public void tick(){
		LoadGuns();
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			if(arma && ammo > 0) {
			ammo--;
			//Criar bala e atirar!
			
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 6;
			if(dir == right_dir) {
				px = 14;
				py = 8;
				dx = 1;
				dy = 0;
			}
			if(dir == left_dir) {
				px = -2;
				py = 8;
				dx = -1;
				dy = 0;
			}
			if(dir == up_dir) {
				px = 11;
				py = 6;
				dx = 0;
				dy = -1;
			}
			if(dir == down_dir) {
				px = 10;
				py = 12;
				dx = 0;
				dy = 1;
			}
			BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
			Game.bullets.add(bullet);
			}
		}
		
		if(mouseShoot) {
			//System.out.println("Atirou com o mouse!");
			mouseShoot = false;
			double angle = 0;
			if(arma && ammo > 0) {
				ammo--;
				
				//Criar bala e atirar!
				
				double dx = 0;
				double dy = 0;
				int px = 0;
				int py = 6;
				if(dir == right_dir) {
					px = 14;
					py = 8;
					dx = 1;
					dy = 0;
					angle = (Math.atan2(my - (this.getY()+8 - Camera.y),mx - (this.getX()+8 - Camera.x)));
					//if (angle < 0) angle = 360+angle;
					//System.out.println("ângulo de tiro:" + angle);
				}
				if(dir == left_dir) {
					px = -2;
					py = 8;
					dx = -1;
					dy = 0;
					angle = (Math.atan2(my - (this.getY()+8 - Camera.y),mx - (this.getX()+8 - Camera.x)));
					//if (angle < 0) angle = 360+angle;
					//System.out.println("ângulo de tiro:" + angle);
				}
				if(dir == up_dir) {
					px = 11;
					py = 6;
					dx = 0;
					dy = -1;
					angle = (Math.atan2(my - (this.getY()+8 - Camera.y),mx - (this.getX()+8 - Camera.x)));
					//if (angle < 0) angle = 360+angle;
					//System.out.println("ângulo de tiro:" + angle);
				}
				if(dir == down_dir) {
					px = 10;
					py = 12;
					dx = 0;
					dy = 1;
					angle = (Math.atan2(my - (this.getY()+8 - Camera.y),mx - (this.getX()+8 - Camera.x)));
					//if (angle < 0) angle = 360+angle;
					//System.out.println("ângulo de tiro:" + angle);
				}
				dx = Math.cos(angle);
				dy = Math.sin(angle);
				BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				Game.bullets.add(bullet);
				}
		}
		
		
	/*	if(life<=0) {
			//Game over!
			life = 0;
			Game.gameState = "GAME_OVER";
		}*/
		
		updateCamera();
	
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionGun() {
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					arma = true;
					//System.out.println("Pegou a arma!");
			
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					ammo+=1000;
					//System.out.println("Municao atual:" + ammo);
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLifePack(){
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifePack) {
				if(Entity.isColidding(this, atual)) {
					life+=10;
					if(life > 100)
						life = 100;
					Game.entities.remove(atual);
				}
			}
		}
	}

	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(arma) {
					g.drawImage(gun[1],this.getX() - Camera.x+7, this.getY() - Camera.y+5,null);//desenha arma para a direita
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(arma) {
					g.drawImage(gun[2],this.getX() - Camera.x-6, this.getY() - Camera.y+5,null);//desenha arma para a esquerda
				}
			} else if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(arma) {
					g.drawImage(gun[3],this.getX() - Camera.x+2, this.getY()+4 - Camera.y,null);//desenha arma para a esquerda
				}
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(arma) {
					g.drawImage(gun[4],this.getX() - Camera.x+4, this.getY()+6 - Camera.y,null);//desenha arma para a esquerda
				}
			}
		}else {
			g.drawImage(playerDamage, this.getX()-Camera.x, this.getY() - Camera.y,null);
			if(arma) {
				/*if(dir == left_dir) {
					g.drawImage(Entity.GUN_DAMAGE_LEFT, this.getX()-8 - Camera.x,this.getY() - Camera.y, null);
				}else {
					g.drawImage(Entity.GUN_DAMAGE_RIGHT, this.getX()+8 - Camera.x,this.getY() - Camera.y, null);
				}*/
			}
		}
	}

}
