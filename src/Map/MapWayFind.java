package Map;
/**
 * ���������� ������������ ��������� ������ ����
 */

import java.awt.Point;
import java.util.ArrayList;

public class MapWayFind {
	public MapWay mapway;
	/**
	 * ����� �� ������ �� ������
	 */
	public IMapCheckPoint checkPoint;
	/**
	 * ����� �� �� �����
	 */
	public boolean isFinish;
	/**
	 * ������������ ��������� ����� ����
	 */
	public ArrayList<WayPoint> nextPoint;
	/**
	 * ��� ��������� ����� ����
	 */
	protected ArrayList<WayPoint> backPoint;

	public MapWay getWay() {
		return mapway;
	}

	/**
	 * �������� �����������
	 */
	public void setcheckPoint(IMapCheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	/**
	 * ����� ����
	 */
	public boolean findWay(int startx, int starty, int endx, int endy) {
		isFinish = false;
		mapway = new MapWay();
		nextPoint = new ArrayList<WayPoint>();
		backPoint = new ArrayList<WayPoint>();

		WayPoint p = new WayPoint(startx, starty, -1, -1, Math.abs(startx - endx) + Math.abs(starty - endy), true);
		nextPoint.add(p);
		backPoint.add(p);
		WayPoint node;
		while (nextPoint.size() > 0) {
			// ����� ������ ������������� ����� �� ������.

			node = nextPoint.get(0);
			nextPoint.remove(0);

			// ��������� ����� �� �� �� �����

			if (node.x == endx && node.y == endy) {

				// ������� ����
				makeWay(node);
				isFinish = true;
				break;
			} else {
				// �������� ����� ��� �������������, ��� �� �� ���� � ������
				// ����
				node.visited = true;
				// ������ ��� ������������ ��������� �����
				addNode(node, node.x + 1, node.y, endx, endy);
				addNode(node, node.x - 1, node.y, endx, endy);
				addNode(node, node.x, node.y + 1, endx, endy);
				addNode(node, node.x, node.y - 1, endx, endy);

			}
		}

		backPoint = null;
		nextPoint = null;

		return isFinish;
	}

	/**
	 * �������� ���� �� ���������� �����
	 */

	public void makeWay(WayPoint node) {
		mapway.clear();
		while (node.px != -1) {
			mapway.addPoint(new Point(node.x, node.y));
			// ���� ����������
			for (WayPoint p : backPoint) {
				if (p.x == node.px && p.y == node.py) {
					node = p;
					break;
				}
			}
		}
	}

	/**
	 * ��������� ��������� �����
	 */

	public void addNode(WayPoint node, int x, int y, int endx, int endy) {
		if (checkPoint.check(x, y)) {
			int cost = Math.abs(x - endx) + Math.abs(y - endy);
			WayPoint px = new WayPoint(x, y, node.x, node.y, cost, false);
			WayPoint old = null;

			// ��������� ����� �� ������������ (p-������)
			for (WayPoint p : backPoint) {
				if (p.x == px.x && p.y == px.y) {
					old = p;
					break;
				}
			}
			// ����� ���������, ��� ��������� ����� ����� ������ ������
			if (old == null || old.cost > cost) {
				backPoint.add(px);
				int i = 0;
				for (i = 0; i < nextPoint.size(); i++) {
					// ������ ����� � ������� ���������� � ��������� ������
					// ������������� �����.
					if (cost < nextPoint.get(i).cost) {
						nextPoint.add(i, px);
						break;
					}
				}
				// ���� ����� �� ���� ���������, ������ �� ������
				if (i >= nextPoint.size()) {
					nextPoint.add(px);
				}
			}
		}
	}
}
