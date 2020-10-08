package me.rlbpc.jogos.MyPong;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	public boolean right, left;
	public int x,y, width,height;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y-2;
		this.width = 40;
		this.height = 5;
	}
	//a lógica do jogador fica neste tick
	public void tick() {
		if (right)
		{
			x++;
		} else if(left) {
			x--;
		}
		//Colisão com as bordas do frame
		if(x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		} else if(x < 0) { 
			x = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x,y,width,height);
	}
	
	
	}
	
	



