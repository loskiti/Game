package Paint;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import Base.Game;
import Map.MapWay;

public abstract class Unit implements IRenderToConvas {

	
	protected Image imageUnit;
	/**
	 * real position of the player
	 */

	private int realX;
	private int realY;
	protected int layerDeep;
	/**
	 * position on the canvas
	 */
	protected int posCanvasY;
	protected int posCanvasX;
	protected int speed = 5;
	private int width = 32;
	private int height = 32;
	protected int directX = 0;
	protected int directY = 0;
	// protected String direct;
	public MapWay mapway;

	/**
	 * Indent by painting in order to taking place in the middle of the tile
	 */
	public int offsetRenderX = 0;
	public int offsetRenderY = 0;

	public void initiation(String name) {
		imageUnit = new ImageIcon(getClass().getResource(name)).getImage();
	}

	@Override
	public void render(Graphics g) {

		update();

		int isoX = posCanvasX, isoY = posCanvasY;
		if (Game.USE_ISO) {
			isoX = (posCanvasX - posCanvasY);
			isoY = (posCanvasX + posCanvasY) / 2;
		}
		g.drawImage(imageUnit, isoX + offsetRenderX + Game.OFFSET_MAP_X, isoY + offsetRenderY, width, height, null);
	}

	/**
	 *  next direction for mouse
	 */

	public void update() {
		if (mapway != null && mapway.isNextPoint()) {
			if (mapway.getP() == null) {
				mapway.startPoint(realX, realY);
			} else {
				mapway.nextPoint(speed);
				if (realX != mapway.getP().x) {
					directX = (realX > mapway.getP().x) ? -1 : 1;
				} else {
					directX = 0;
				}
				if (realY != mapway.getP().y) {
					directY = (realY > mapway.getP().y) ? -1 : 1;
				} else {
					directY = 0;
				}
			}
		} else {
			directX = 0;
			directY = 0;
		}
	}

	public int getDeep() {

		return layerDeep;

	}

	public int getX() {
		return realX;
	}

	public int getY() {
		return realY;
	}

	public void setPosition(int X, int Y) {
		this.realY = Y;
		this.realX = X;
	}

	public int getPosUnitY() {
		return posCanvasY;
	}

	public void setPosUnit(int posUnitX, int posUnitY) {
		this.posCanvasX = posUnitX;
		this.posCanvasY = posUnitY;
	}

	public int getPosUnitX() {
		return posCanvasX;
	}

	public int getSpeed() {
		return speed;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDirectX() {
		return directX;
	}


	public int getDirectY() {
		return directY;
	}


	public void setDirect(String direct) {
		switch (direct) {
		case "UP": {
			this.directY = -1;
			break;
		}
		case "DOWN": {			
			this.directY = 1;
			break;
		}
		case "RIGHT": {
			this.directX = 1;
			break;
		}
		case "LEFT": {
			this.directX = -1;
			break;
		}
		case "Null1": {
			this.directX = 0;
			break;			
		}
		case "Null2": {
			this.directY = 0;
			break;
		}
		}

	}
}
