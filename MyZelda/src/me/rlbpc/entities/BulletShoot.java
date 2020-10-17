/**
 * Estudo de Games
 */
package me.rlbpc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.rlbpc.world.Camera;

/**
 * @author Rodolfo Bortoluzzi 13/10/2020
 *
 */
public class BulletShoot extends Entity{
	private double dx, dy;
	private double bulletSpeed = 4;

	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
	}
	
	public void tick() {
		x += dx * bulletSpeed;
		y += dy * bulletSpeed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}
