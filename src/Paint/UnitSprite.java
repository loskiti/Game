package Paint;

import java.awt.Graphics;

import Base.Game;

public abstract class UnitSprite extends Unit {
	// ������� ��������
	protected int imageWidth = 128;
	protected int imageHeight = 128;
	protected int imageX = 0;
	protected int imageY = 0;
	// ������� ��� ������������
	protected int imageMinX = 0;
	protected int imageMinY = 0;
	protected int imageMaxX = 0;
	protected int imageMaxY = 0;
	// ��� ��������� ������� (��� ������� ������)
	protected int paintWidth = 100;
	protected int paintHeight = 100;

	@Override
	public void render(Graphics g) {
		update();
		// ����������� ����������������
		int isoX = posUnitX, isoY = posUnitY;
		if (Game.USE_ISO) {
			isoX = (posUnitX - posUnitY);
			isoY = (posUnitX + posUnitY) / 2;
		}

		g.drawImage(imageUnit, isoX + Game.OFFSET_MAP_X + offsetRenderX, isoY + offsetRenderY,
				isoX + paintWidth + Game.OFFSET_MAP_X + offsetRenderX, isoY + paintHeight + offsetRenderY,
				imageX * imageWidth, imageY * imageHeight, imageX * imageWidth + imageWidth,
				imageY * imageHeight + imageHeight, null);
	}

	@Override
	public void update() {
		// ����������� ������� �� ������
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
