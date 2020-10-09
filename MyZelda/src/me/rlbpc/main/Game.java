package me.rlbpc.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import me.rlbpc.entities.Entity;
import me.rlbpc.entities.Player;
import me.rlbpc.graficos.SpriteSheet;

public class Game extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	private final int WIDTH = 180;
	private final int HEIGHT = 160;
	private final int SCALE = 3; //Usar o scale para aumentar ou diminuir a janela
	
	private BufferedImage image;
	
	public List<Entity> entities;
	public SpriteSheet spritesheet;
	
	private Player player;
			
	public Game() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(getWIDTH() * getSCALE(), getHEIGHT() * getSCALE()));
		initFrame();
		//Inicializando Objetos
		image = new BufferedImage(getWIDTH(),getHEIGHT(),BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		spritesheet = new SpriteSheet("/SpriteSheet.png");
		player = new Player(0,0,16,16,spritesheet.getSprite(32,0,16,16));		
		entities.add(player);
	}
	public void initFrame() {
		setFrame(new JFrame("MyZelda - Rodolfo Bortoluzzi - Estudo de Games"));
		getFrame().add(this);
		getFrame().setResizable(false);
		getFrame().pack(); //calibra as dimens�es do frame usando o canvas
		getFrame().setLocationRelativeTo(null);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setVisible(true);
	}
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main (String args[]) {
		Game game = new Game();
		game.start();
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Game.frame = frame;
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
	}
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);//usar de 2 a 3
			return;
		}
		Graphics g = image.getGraphics();
		//PREPARA As IMAGENS PARA SEREM APRESENTADAS
		g.setColor(new Color(0,255,0));
		g.fillRect(0,0,getWIDTH(),getHEIGHT());
		
		//Renderiza��o do jogo
		//Graphics2D g2 = (Graphics2D) g; //casting do g para gr�ficos 2D
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		//APRESENTA A IMAGEM NO FRAME
		g.dispose(); //limpar dados de imagem otimiza a performance
		g = bs.getDrawGraphics();
		g.drawImage(image,0,0,getWIDTH()*SCALE,getHEIGHT()*SCALE,null);
		bs.show();
		}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks=60.0; //FPS rate
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		//Looping principal do jogo
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime=now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		stop(); //seguran�a para que as threads parem se ocorrer algum problema e liberem os recursos do computador
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getSCALE() {
		return SCALE;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {	
			System.out.println("Direita");
			player.right = true;
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			System.out.println("Esquerda");
			player.left = true;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_W) {
			System.out.println("Para cima");
			player.up = true;
		}else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_S) {
			System.out.println("Para baixo");
			player.down = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {	
			System.out.println("Soltando Direita");
			player.right = false;
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			System.out.println("Soltando Esquerda");
			player.left = false;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_W) {
			System.out.println("Soltando Para cima");
			player.up = false;
		}else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_S) {
			System.out.println("Soltando Para baixo");
			player.down = false;
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
