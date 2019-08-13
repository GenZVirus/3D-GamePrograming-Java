package com.GenZVirus.graphics;

import com.GenZVirus.Game;

public class Render3D extends Render {

	public double[] zBuffer;
	private double renderDistance = 500000;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];

	}

	public void floor(Game game) {

		double floorposition = 256;
		double ceilingposition = 256;
		double forward = game.controls.z;
		double right = game.controls.x;

		double rotation = game.controls.rotation;
		double cousine = Math.cos(rotation);
		double sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;

			double z = floorposition / ceiling;

			if (ceiling < 0) {
				z = ceilingposition / -ceiling;
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				if (z < 30000) {
					double xx = depth * cousine + z * sine;
					double yy = z * cousine - depth * sine;
					int xPix = (int) (xx + right);
					int yPix = (int) (yy + forward);
					zBuffer[x + y * width] = z;
					pixels[x + y * width] = Texture.floor.pixels[(xPix & 255) + (yPix & 255) * 256];
					//pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;
				}
			}
		}

	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));

			if (brightness < 0) {
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}
			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}

	}

}
