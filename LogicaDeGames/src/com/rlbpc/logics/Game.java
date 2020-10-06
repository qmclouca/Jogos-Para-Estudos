package com.rlbpc.logics;

import java.util.ArrayList;

import com.rlbpc.entities.EntidadesGerais;

public class Game implements Runnable{
	
	private boolean isRunning;
	private Thread thread;
	
	private ArrayList<EntidadesGerais> entidades = new ArrayList<>();
	
	public Game() {
		entidades.add(new EntidadesGerais());
		entidades.add(new EntidadesGerais());
		entidades.add(new EntidadesGerais());
		entidades.add(new EntidadesGerais());
		for (int i=0; i<entidades.size();i++) {
			EntidadesGerais e = entidades.get(i);
			System.out.println("Entidades OK!" + i + ", ");
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void tick() {
		//Atualizar o jogo (fps)
		System.out.println("Tick");
	}
	public void render() {
		//Renderizar o jogo
		System.out.println("Render");
	}
	
	@Override
	public void run() {
		
		while(isRunning) {
			tick();
			render();
			/* Taxa de atualização simples
			 * try {
			 	Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
	}
	

}
