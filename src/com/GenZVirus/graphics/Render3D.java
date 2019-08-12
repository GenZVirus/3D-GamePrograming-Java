package com.GenZVirus.graphics;

import com.GenZVirus.Game;

public class Render3D extends Render {

	public Render3D(int width, int height) {
		super(width, height);

	}

	public void floor(Game game) {

		double floorposition = 18;
		double ceilingposition = 18;
		double forward = game.time / 5.0;
		double right = game.time / 5.0;

		double rotation = game.time / 100.0;
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
				double xx = depth * cousine + z * sine + forward;
				double yy = z * cousine - depth * sine + right;
				int xPix = (int) (xx);
				int yPix = (int) (yy);
				pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;
			}
		}
	}

}
