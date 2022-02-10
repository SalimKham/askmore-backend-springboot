package io.khaminfo.askmore.services;

import java.awt.AlphaComposite;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageUtils {

	public static BufferedImage crop(Image img, int x_crop, int y_crop, int w_crop, int h_crop) {
		System.out.println("crop "+x_crop+"   "+y_crop+"  "+w_crop+"  "+h_crop);
		BufferedImage b_crop = new BufferedImage(w_crop, h_crop, BufferedImage.TYPE_INT_RGB);
		Graphics g = b_crop.getGraphics();
	
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(img,-x_crop,-y_crop, //draw the section of the image between (100, 100) and (400, 400)
		        null);
		return b_crop;
	}

	public static BufferedImage resize(Image img, int w, int h, int to_w, int to_h) {
		float scaleW = (float) to_w / w;
		float scaleH = (float) to_h / h;
		int new_w = (int) (w * scaleW), new_h = (int) (h * scaleH);
		BufferedImage b = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		Graphics g = b.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.scale(scaleW, scaleH);
		g2.drawImage(img,0,0, null);
		return b;
	}

	public static String getRandomName() {
		String s = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String name = "";
		Random r = new Random();
		for (int i = 0; i < 20; i++) {
			name += s.charAt(r.nextInt(62));
		}
		return name;
	}

}
