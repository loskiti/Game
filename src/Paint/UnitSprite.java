package Paint;

import java.awt.Graphics;

import Base.Game;

public abstract class UnitSprite extends Unit {

	private int imageWidth = 128;
	private int imageHeight = 128;
	protected int imageX = 0;
	protected int imageY = 0;
	/**
	 * position by cycling
	 */
	protected int imageMinX = 0;
	protected int imageMinY = 0;
	protected int imageMaxX = 0;
	protected int imageMaxY = 0;
	/**
	 * size for paint
	 */

	protected int paintWidth = 100;
	protected int paintHeight = 100;

	@Override
	public void render(Graphics g) {
		update();
		// static position
		int isoX = posCanvasX, isoY = posCanvasY;
		if (Game.USE_ISO) {
			isoX = (posCanvasX - posCanvasY);
			isoY = (posCanvasX + posCanvasY) / 2;
		}

		g.drawImage(imageUnit, isoX + Game.OFFSET_MAP_X + offsetRenderX, isoY + offsetRenderY,
				isoX + paintWidth + Game.OFFSET_MAP_X + offsetRenderX, isoY + paintHeight + offsetRenderY,
				imageX * imageWidth, imageY * imageHeight, imageX * imageWidth + imageWidth,
				imageY * imageHeight + imageHeight, null);
	}

	@Override
	public void update() {
		// cadr cycling
		imageX++;
		if (imageX > imageMaxX) {
			imageX = imageMinX;
		}
		imageY++;
		if (imageY > imageMaxY) {
			imageY = imageMinY;
		}
		super.update();
	}
}
