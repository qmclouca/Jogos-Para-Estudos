package me.rlbpc.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	private int Ang=90; 
	private int x=0;
	private int y=0;
	private final int WIDTH = 180;
	private final int HEIGHT = 160;
	private final int SCALE = 4; //Usar o scale para aumentar ou diminuir a janela
	
	private BufferedImage image;
	
	private SpriteSheet sheet;
	private BufferedImage[] player;
	private int frames = 0; // contador de frames
	private int maxFrames = 5; //de quantos em quantos frames o personagem ser� animado
	private int currentAnimation = 0, maxAnimation = 3;
		
	public Game() {
		sheet = new SpriteSheet("/SpriteSheet.png");
		player = new BufferedImage[4]; //mudar quantidade de players
		player[0]= sheet.getSprite(0, 0, 16, 16);
		player[1]= sheet.getSprite(16, 0, 16, 16);
		player[2]= sheet.getSprite(32, 0, 16, 16);
		player[3]= sheet.getSprite(48, 0, 16, 16);
		this.setPreferredSize(new Dimension(getWIDTH() * getSCALE(), getHEIGHT() * getSCALE()));
		initFrame();
		image = new BufferedImage(getWIDTH(),getHEIGHT(),BufferedImage.TYPE_INT_BGR);
	}
	public void initFrame() {
		setFrame(new JFrame("Primeira Janela do Jogo"));
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
		frames++;
		if(frames > maxFrames) {
			frames = 0;
			currentAnimation++;
			if(currentAnimation > maxAnimation) {
				currentAnimation = 0;
			}
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
		//g.setColor(new Color(19,19,19));
		g.fillRect(0,0,getWIDTH(),getHEIGHT());
		
		//Renderiza��o do jogo
		Ang = 0; x = 90; y = 90; //posi��o do player
		Graphics2D g2 = (Graphics2D) g; //casting do g para gr�ficos 2D
		g2.setColor(new Color(0,0,0,180)); //Fundo, renderizar por ordem de tr�s para frente 
		g2.fillRect(0, 0, getWIDTH(), getHEIGHT());
		g2.rotate(Math.toRadians(Ang),90+8,90+8); //Os �ngulos devem ser convertidos em radiandos
		g2.drawImage(player[currentAnimation], x, y, null);
		
		//g2.drawImage(player, x, y, null);
		//x++;y++;
		//fim renderiza��o jogo
		//g.setFont(new Font("Arial",Font.BOLD,24));
		//g.setColor(Color.white);
		//g.drawString("Ol� Mundo", 50, 50);
		//g.setColor(Color.blue);
		//g.fillRect(30, 40, 80, 90);
		
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
}
