package Paint;
/**
 * �������� ���������� ������ ��������� �� ������
 */

import java.awt.Graphics;

public interface IRenderToConvas {

	void render(Graphics g);

	/**
	 * ������� ����������������
	 */

	int getDeep();

}
