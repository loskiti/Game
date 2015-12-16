package Paint;
/**
 * graphic
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	/**
	 * double buffering
	 */
	private Image bufer = null;
	private Color backGround = Color.black;

	/**
	 * elements, which we must add to canvas
	 */
	private List<IRenderToConvas> renders = new ArrayList<IRenderToConvas>();

	public void addRenders(IRenderToConvas render) {
		renders.add(render);
	}

	public void cleaneRenders() {
		renders.clear();
	}

	/**
	 * deep sort
	 */
	private static Comparator<IRenderToConvas> comparatorDeep = new Comparator<IRenderToConvas>() {
		@Override
		public int compare(IRenderToConvas o1, IRenderToConvas o2) {

			return (o1.getDeep() < o2.getDeep()) ? -1 : 1;
		}
	};

	/**
	 * repaint
	 */

	private void paintWorld(Graphics g) {

		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(backGround);
		g.fillRect(0, 0, getWidth(), getHeight());
		// sort before repaint
		Collections.sort(renders, comparatorDeep);
		for (IRenderToConvas render : renders) {
			render.render(g);
		}
	}

	/**
	 * double buffering
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
