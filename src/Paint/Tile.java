package Paint;

// ���� ������ �����
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

import Base.Game;

public class Tile implements IRenderToConvas {
	// ������ ������
	final public static int SIZE = 40;
	public String image;
	// ���������� ������
	public int X;
	public int Y;
	public boolean isPossible;
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	public int door = 0;
	public int layerDeep;

	public Tile(String Image, int X, int Y, boolean isPossible) {
		this.image = Image;
		this.X = X;
		this.Y = Y;
		this.isPossible = isPossible;

	}

	public Tile(String Image, int X, int Y, boolean isPossible, int door, int layerDeep) {
		this.image = Image;
		this.X = X;
		this.Y = Y;
		this.isPossible = isPossible;
		this.door = door;
		this.layerDeep = layerDeep;
	}

	@Override
	public void render(Graphics g) {

		int isoX = X, isoY = Y;
		if (Game.USE_ISO) {
			isoX = (X - Y);
			isoY = (X + Y) / 2;
		}
		g.drawImage(getImage(image), isoX + Game.OFFSET_MAP_X, isoY, SIZE, SIZE, null);
	}

	@Override
	public int getDeep() {
		return layerDeep;
	}

	// ������� ����������� �� ������
	public static Image getImage(String name) {
		if (images.get(name) == null) {
			images.put(name, new ImageIcon(Tile.class.getResource(name)).getImage());
		}
		return (Image) images.get(name);
	}

	public static Tile getTileId(int tileId) {

		switch (tileId) {
		case 0:
			return new TileSprite("/data/grassland.png", 0, 0, 40, 51, 704, 256, false, 0, 10);
		case 1:

			return new TileSprite("/data/grassland.png", 0, 0, 55, 32, 327, 347, false, 0, 10);
		case 2:

			return new TileSprite("/data/grassland.png", 0, 0, 64, 32, 192, 32, true, 0, 10);
		case 3:
			return new Tile("/data/black.png", 0, 0, true, 2, 10);
		case 4:

			return new TileSprite("/data/BlackDragon.png", 0, 0, 100, 110, 32, 646, false, 0, 100);
		case 5:
			return new Tile("/data/black.png", 0, 0, false, 0, 1);
		case 6:
			return new TileSprite("/data/grassland.png", 0, 0, 64, 90, 570, 70, false, 0, 100);
		case 7:
			return new TileSprite("/data/grassland.png", 0, 0, 64, 96, 64, 64, false, 0, 100);
		case 8:
			return new TileSprite("/data/grassland.png", 0, 0, 64, 90, 513, 70, false, 0, 150);
		case 9:
			return new TileSprite("/data/grassland.png", 0, 0, 64, 90, 704, 70, false, 0, 100);
		case 10:
			return new TileSprite("/data/grassland.png", 0, 0, 54, 51, 714, 387, false, 0, 100);

		case 11:
			return new TileSprite("/data/grassland.png", 0, 0, 40, 45, 534, 263, false, 0, 10);
		case 12:

			return new TileSprite("/data/grassland.png", 0, 0, 64, 96, 0, 64, false, 0, 10);
		case 13:
			return new TileSprite("/data/grassland.png", 0, 0, 270, 180, 740, 710, false, 0, 100);

		case 14:

			return new TileSprite("/data/grassland.png", 0, 0, 64, 32, 192, 32, true, 0, 10);
		case 15:
			// return new TileSprite("/data/elfire.gif", 0, 0, 72, 40, 5, 200,
			// true, 0, 10);
			return new TileSprite("/data/4.png", 0, 0, 51, 40, 73, 0, true, 0, 10);

		case 16:
			// return new TileSprite("/data/elfire.gif", 0, 0, 72, 40, 421, 99,
			// true, 0, 10);
			return new TileSprite("/data/4.png", 0, 0, 51, 40, 8, 0, true, 0, 10);
		default:
			return null;

		}

	}
}