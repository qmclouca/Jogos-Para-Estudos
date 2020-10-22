/**
 * 
 */
package me.rlbpc.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author Rodolfo Bortoluzzi
 *
 */
public class Menu {
	
	public String[] options = {"Novo Jogo","Carregar Jogo","Sair"};
	public static boolean up, down;
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) currentOption = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.BLUE);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("MyZelda - Rodolfo Bortoluzzi", (Game.WIDTH*Game.SCALE/2) - 250, (Game.HEIGHT*Game.SCALE/2)-200);
		g.setColor(Color.WHITE);
		g.drawString("Estudo de Caso 1 - Games 2D", (Game.WIDTH*Game.SCALE/2) - 260, (Game.HEIGHT*Game.SCALE/2)-150);
		g.drawString("Tubarão, outubro 2020", (Game.WIDTH*Game.SCALE/2) - 200, (Game.HEIGHT*Game.SCALE/2)-100);
		g.setFont(new Font("arial", Font.BOLD, 24));
		//Opções do menu
		g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE/2)-75, (Game.HEIGHT*Game.SCALE/2));
		g.drawString("Carregar jogo", (Game.WIDTH*Game.SCALE/2)-75, (Game.HEIGHT*Game.SCALE/2)+25);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE/2) - 75, (Game.HEIGHT*Game.SCALE/2)+50);

		if(options[currentOption] == "Novo Jogo") {
			g.drawString("> ", (Game.WIDTH*Game.SCALE/2) - 90, (Game.HEIGHT*Game.SCALE/2));
		}else if(options[currentOption] == "Carregar Jogo") {
			g.drawString("> ", (Game.WIDTH*Game.SCALE/2) - 90, (Game.HEIGHT*Game.SCALE/2)+25);
		}else if(options[currentOption] == "Sair") {
			g.drawString("> ", (Game.WIDTH*Game.SCALE/2) - 90, (Game.HEIGHT*Game.SCALE/2)+50);
		}
	}
}
