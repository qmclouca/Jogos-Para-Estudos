package me.rlbpc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.rlbpc.main.Game;

public class Player extends Entity {
	
	public boolean right,up,left,down,upleft,upright,downleft,downright, moved = false;
	public double speed = 1.2;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	
	//a cada 5 frames muda o sheet do personagem, neste caso são 4 desenhos para direita e quatro para a esqueda e também para cima e para baixo
	private int frames = 0, maxFrames = 4, index = 0, maxIndex = 3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Carregar as sprites para animação do player
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32+(i*16),17,16,16);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*16),34,16,16);
		}
		for (int i = 0; i < 4; i++) {	
			upPlayer[i] = Game.spritesheet.getSprite(32+(i*16),52,16,16);
		}
		for (int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32+(i*16),0,16,16);
		}
	}
	
	public void tick() {
		moved = false;
		if (right) {
			moved = true;
			dir = right_dir;
			x+=speed;
		} else if (left) {
			moved = true;
			dir = left_dir;
			x-=speed;
		} else if (down) {
			moved = true;
			dir = up_dir;
			y-=speed;			
		} else if (up) {
			moved = true;
			dir = down_dir;
			y+=speed;
		} /*else if (upleft) {
			x-=speed;
			y+=speed;
		} else if (upright) {
			x+=speed;
			y+=speed;
		} else if (downleft) {
			x-=speed;
			y-=speed;
		} else if (downright) {
			x+=speed;
			y-=speed;
		}*/ //para implementação posterior de direções diagonais de movimentação
		
		//lógica para animar o personagem trocando a sheet a cada 4 frames
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		} else if (dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		} else if (dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX(), this.getY(), null);
		} else if (dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX(), this.getY(), null);
		}
	}
}
