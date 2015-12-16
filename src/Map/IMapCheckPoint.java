package Map;

/**
 * is move possible on this tile or no
 */
public interface IMapCheckPoint {
	boolean check(int x, int y);
}
