package Map;

/**
 * action with map
 */
public abstract class ActionMap {
	protected int map[][];
	//protected String map[][];
	public int getWidth() {
		return map[0].length;
	}

	public int getHeight() {
		return map.length;
	}

	/**
	 * is this tile or no
	 */
	private boolean isTile(int x, int y) {
		if ((x >= 0) && (y >= 0) && (y < getHeight()) && (x < getWidth()))
			return true;
		return false;
	}

	/**
	 * get value of the tile
	 */
	public int getTile(int x, int y) {
		if (isTile(x, y))
			return map[y][x];
		return 0;

	}
	/*public String getTile(int x, int y) {
		if (isTile(x, y))
			return map[y][x];
		return null;

	}*/
}
