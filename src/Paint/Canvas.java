package Paint;
/**
 * �������
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	/**
	 * ������ �����������
	 */
	public Image bufer = null;
	public Color backGround = Color.black;

	/**
	 * ��������, ������� ���� �������� �� �����
	 */
	public ArrayList<IRenderToConvas> renders = new ArrayList<IRenderToConvas>();
	final public static int LAYER_DEEP1 = 100;

	public void addRenders(IRenderToConvas render) {
		renders.add(render);
	}

	public void cleaneRenders() {
		renders.clear();
	}

	/**
	 * ���������� �������� �� �������
	 */
	public static Comparator<IRenderToConvas> comparatorDeep = new Comparator<IRenderToConvas>() {
		@Override
		public int compare(IRenderToConvas o1, IRenderToConvas o2) {

			return (o1.getDeep() < o2.getDeep()) ? -1 : 1;
		}
	};

	/**
	 * ����������� �����
	 */

	public void paintWorld(Graphics g) {

		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(backGround);
		g.fillRect(0, 0, getWidth(), getHeight());
		// ���������� ����� ����������
		Collections.sort(renders, comparatorDeep);
		for (IRenderToConvas render : renders) {
			render.render(g);
		}
	}

	/**
	 * ������� �����������
	 */

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (bufer == null) {
			bufer = createImage(getWidth(), getHeight());
		}

		paintWorld(bufer.getGraphics());
		g.drawImage(bufer, 0, 0, null);
	}

}
