package me.rlbpc.jogos.MyPong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	public double x,y;
	public int width,height;
	
	public double dx,dy; //direções da bola
	public double speed = 1.5;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 3;
		this.height = 3;
		int angle = new Random().nextInt(120 - 45) + 45;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.cos(Math.toRadians(angle));		
	}
	//lógica do inimigo
	public void tick() {
		//colisões com paredes
		if ((x+(dx*speed) + width >= Game.WIDTH)) {
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
			int angle = new Random().nextInt(120 - 45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.cos(Math.toRadians(angle));
			if(dy>0) dy*=-1;
		} else if(bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120 - 45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.cos(Math.toRadians(angle));
			if(dy<0) dy*=-1;
			
		}
		
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x,(int)y,width,height);
	}
}
