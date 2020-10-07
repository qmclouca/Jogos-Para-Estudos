package me.rlbpc.jogos.MyPong;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	
	public static final long serialVersionUID = 1L;
	public final int WIDTH = 240;
	public final int HEIGHT = 120;
	public final int SCALE = 4; //Usar o scale para aumentar ou diminuir a janela
	
	public Player player;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		player = new Player();
	}
	
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("MyPong - Rodolfo Bortoluzzi - Estudo de Games");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null); //Janela abre no centro da tela
		frame.setVisible(true);

		new Thread(game).start();
	}
	
	public void tick() {		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		player.render(g);
		bs.show();
		}
		
	@Override
	public void run() {	
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

