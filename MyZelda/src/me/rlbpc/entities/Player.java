package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.rlbpc.graficos.SpriteSheet;
import me.rlbpc.main.Game;
import me.rlbpc.world.Camera;
import me.rlbpc.world.World;

public class Player extends Entity {
	
	public boolean right, up, left, down, upleft, upright, downleft, downright, moved = false, isDamaged = false;
	public static boolean isShooting = false, hasGun = false; 
	public static double speed = 1.2, life = 100, maxLife = 100;
	public int frames = 0, index = 0, maxFrames = 5, maxIndex = 3, right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3, dir = right_dir, damageFrames = 0, ammo = 0;
	//a cada 5 frames muda o sheet do personagem, neste caso s�o 4 desenhos para direita e quatro para a esqueda e tamb�m para cima e para baixo
	private BufferedImage[] rightPlayer, leftPlayer, upPlayer, downPlayer;
	private BufferedImage playerDamage;
		
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Carregar as sprites para anima��o do player
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
	
	public void tick() {
		moved = false;
			
		if (right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		} 
		if (left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		} 
		if (down && World.isFree(this.getX(),(int)(y-speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		} 
		if (up && World.isFree(this.getX(),(int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		
		} 
		
		//l�gica para animar o personagem trocando a sheet a cada 4 frames
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) index = 0;
			}
		}
	chekCollisionLifePack();
	chekCollisionAmmo();
	chekCollisionGun();
	LoadGuns();
	if(isDamaged) {
		this.damageFrames++;
		if (this.damageFrames == 2) {
			this.damageFrames = 0;
			isDamaged = false;
		}
		
		if (life <= 0) {
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new SpriteSheet("/SpriteSheet.png");
			Game.player = new Player(0,0,Game.xyPixelsByTile,Game.xyPixelsByTile,Game.spritesheet.getSprite(32,0,Game.xyPixelsByTile,Game.xyPixelsByTile));
			Game.entities.add(Game.player);
			Game.world = new World("/mapa20x20.png");
			life = 100;
			return;
		}
		
		if (isShooting) {
			//Criar bala e atirar
			isShooting = false;
			if(hasGun) {
				if(ammo > 0) {
			int dx = 0;
			int px = 0;
			int py = 8;
			if (dir == right_dir) {
				px = 3;
				dx = 1;
			} else {
				dx = -1;
			}
			BulletShoot bullet = new BulletShoot(this.getX() + px,this.getY() + py, 3, 3, null, dx, 0);
			Game.bullets.add(bullet);
			ammo--;
				}
			}
		}
		
		/*if (mouseShoot) {
			//Criar bala e atirar
			int dx = 0;
			int px = 0;
			int py = 8;
			if (dir == right_dir) {
				px = 3;
				dx = 1;
			} else {
				dx = -1;
			}
			BulletShoot bullet = new BulletShoot(this.getX() + px,this.getY() + py, 3, 3, null, dx, 0);
			Game.bullets.add(bullet);
			ammo--;
			
		}*/
	}
		//C�digo para a c�mera acompanhar o jogador e no meio da tela e n�o aparecer espa�o fora do mapa (parte escura sem mapa)(M�todo Clamp)
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*Game.xyPixelsByTile - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*Game.xyPixelsByTile - Game.HEIGHT);
}
	
	public void chekCollisionAmmo(){
		for (int i = 0; i< Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
				if(atual instanceof Bullet){
					if(Entity.isColidding(this, atual)) {
						ammo += 10;
						//System.out.println("Muni��o: " + ammo);
						if (life >= 100) life = 100;
						Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void chekCollisionGun(){
		for (int i = 0; i< Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
				if(atual instanceof Weapon){
					if(Entity.isColidding(this, atual)) {
						hasGun = true;
						//System.out.println("Pegou a arma!");
						Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void chekCollisionLifePack(){
		for (int i = 0; i< Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
				if(atual instanceof LifePack){
					if(Entity.isColidding(this, atual)) {
						life += 10;
						if (life >= 100) life = 100;
						Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(gun[1],this.getX() - Camera.x+7, this.getY() - Camera.y+5,null);//desenha arma para a direita
				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(gun[2],this.getX() - Camera.x-6, this.getY() - Camera.y+5,null);//desenha arma para a esquerda
				}
			} else if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(gun[3],this.getX() - Camera.x+2, this.getY()+4 - Camera.y,null);//desenha arma para a esquerda
				}
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(gun[4],this.getX() - Camera.x+4, this.getY()+6 - Camera.y,null);//desenha arma para a esquerda
				}
			}
		} else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
