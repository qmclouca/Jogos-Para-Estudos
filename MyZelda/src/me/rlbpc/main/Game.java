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
import me.rlbpc.world.World;

public class Game extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int xyPixelsByTile = 16;
	public static final int WIDTH = 220;
	public static final int HEIGHT = 160;
	private final int SCALE = 2; //Usar o scale para aumentar ou diminuir a janela
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	
	public static SpriteSheet spritesheet;
	
	public static World world;
	
	public static Player player;
			
	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(getWIDTH() * getSCALE(), getHEIGHT() * getSCALE()));
		initFrame();
		//Inicializando Objetos devem obedecer a ordem correta devido ao uso de variáveis estáticas
		
		image = new BufferedImage(getWIDTH(),getHEIGHT(),BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		spritesheet = new SpriteSheet("/SpriteSheet.png");
		player = new Player(0,0,xyPixelsByTile,xyPixelsByTile,spritesheet.getSprite(32,0,xyPixelsByTile,xyPixelsByTile));
		entities.add(player);
		world = new World("/mapa20x20.png");
	
	}
	
	public void initFrame() {
		frame = new JFrame("MyZelda - Rodolfo Bortoluzzi - Estudo de Games");
		frame.add(this);
		frame.setResizable(false);
		frame.pack(); //calibra as dimensões do frame usando o canvas
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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
/*
	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Game.frame = frame;
	}
*/
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
		g.setColor(new Color(35,35,0));
		g.fillRect(0,0,getWIDTH(),getHEIGHT());
		
		//Renderização do jogo
		//Graphics2D g2 = (Graphics2D) g; //casting do g para gráficos 2D
		world.render(g);
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
		stop(); //segurança para que as threads parem se ocorrer algum problema e liberem os recursos do computador
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
		//implementar movimentos diagonais posteriormente criar tabela de estados de teclas e comparar tabelas para verificar qual a combinação de teclas
		//outra solução é criar um timer e verificar se duas teclas foram pressionadas em um intervalo baixo o suficiente para considerar que é um único evento, é uma solução mais pobre mas consome menos recurso
		switch(e.getKeyCode()) {
		  case KeyEvent.VK_UP:
			  System.out.println("Para baixo");
				player.down = true;
		    break;
		  case KeyEvent.VK_RIGHT:
			  System.out.println("Direita");
			  player.right = true;
		    break;
		  case KeyEvent.VK_LEFT:
			  System.out.println("Esquerda");
				player.left = true;
			    break;
		  case KeyEvent.VK_DOWN:
			  System.out.println("Para cima");
				player.up = true;
			    break;
			    
		  default:
		    // code block
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		  case KeyEvent.VK_UP:
			  System.out.println("Para baixo");
				player.down = false;
		    break;
		  case KeyEvent.VK_RIGHT:
			  System.out.println("Direita");
			  player.right = false;
		    break;
		  case KeyEvent.VK_LEFT:
			  System.out.println("Esquerda");
				player.left = false;
			    break;
		  case KeyEvent.VK_DOWN:
			  System.out.println("Para cima");
				player.up = false;
			    break;
		   default:
		    // code block
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
