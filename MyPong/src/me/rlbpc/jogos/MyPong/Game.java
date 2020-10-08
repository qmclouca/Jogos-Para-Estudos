package me.rlbpc.jogos.MyPong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	public static final long serialVersionUID = 1L;
	public final static int WIDTH = 240;
	public final int HEIGHT = 120;
	public final int SCALE = 4; //Usar o scale para aumentar ou diminuir a janela
	
	public BufferedImage layer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); //Cria uma layer onde serão renderizados os gráficos
	
	public Player player;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);
		player = new Player(100,HEIGHT-5);// -5 é a altura do jogador pois a renderização começa do topo esquerdo do frame
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
	
	//A lógica do jogo fica dentro desta classe tick principal
	public void tick() {
		player.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		g.setColor(Color.black); //limpar o frame para a renderizar a próxima posição
		g.fillRect(0, 0, WIDTH, HEIGHT); //limpar o frame para a renderizar a próxima posição
		player.render(g);
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE,null);
		
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


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

