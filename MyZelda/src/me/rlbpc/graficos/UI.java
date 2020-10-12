/*
  Cria a interface de usuário mostrando a vida do jogados e outros itens para melhorar a jogabilidade
 */
package me.rlbpc.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.rlbpc.entities.Player;
import me.rlbpc.main.Game;

/**
 * @author Rodolfo Bortoluzzi
 *12/10/2020
 */
public class UI {
	public void render (Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(10, 2, 50,10);
		g.setColor(Color.GREEN);
		g.fillRect(10, 2, (int)((Player.life / Player.maxLife)*50), 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD,9));
		g.drawString((int) Player.life +" / "+(int) Player.maxLife, 10, 10);
		
	}

}
