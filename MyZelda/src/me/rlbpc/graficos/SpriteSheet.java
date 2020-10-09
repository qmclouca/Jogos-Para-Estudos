package me.rlbpc.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage spritesheet;
	public SpriteSheet (String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x,y,width,height);
	}
}
