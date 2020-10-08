package me.rlbpc.jogos.MyPong;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	public double x,y;
	public int width,height;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 40;
		this.height = 5;
	}
	//lógica do inimigo
	public void tick() {
	}
	
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x,(int)y,width,height);
	}

}
