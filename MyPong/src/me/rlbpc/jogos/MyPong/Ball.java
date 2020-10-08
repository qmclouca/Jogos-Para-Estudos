package me.rlbpc.jogos.MyPong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	public double x,y;
	public int width,height;
	
	public double dx,dy; //direções da bola
	public static double speed = 0.8;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 3;
		this.height = 3;
		
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
		
	}
	//lógica do inimigo
	public void tick() {
		//colisões com paredes
		if ((x+dx*speed) + width >= Game.WIDTH) {
			dx*=-1;
		} else if (x+(dx*speed) < 0) {
			dx*=-1;			
		}
		
		if(y >= Game.HEIGHT) {
			//PONTO INIMIGO
			System.out.println("Ponto do Adversário");
			new Game();
			return;
		} else if (y < 0) {
			//PONTO JOGADOR
			System.out.println("Ponto do Jogador");
			new Game();
			return;
		}
		
		//Colisão com os blocos
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)),(int)(y+(dy*speed)),width,height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.x,Game.player.y,Game.player.width,Game.player.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x,(int)Game.enemy.y,Game.enemy.width,Game.enemy.height);
		
		if(bounds.intersects(boundsPlayer)) {
			dy*=-1;
		} else if(bounds.intersects(boundsEnemy)) {
			dy*=-1;
		}
		
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x,(int)y,width,height);
	}
}
