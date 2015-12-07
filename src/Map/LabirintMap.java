package Map;

/**
 * ������� � ����������
 */
import java.util.Random;

public class LabirintMap extends ActionMap {
	public LabirintMap() {

		// ��������� �����
		int a = 30, b = 30;
		Random random = new Random();
		map = new int[a][b];

		// ������ ������
		for (int i = 1; i < a; i++) {
			map[1][i] = i;
		}
		for (int i = 2; i < a; i++) {
			if (random.nextInt(2) < 1) {
				map[1][i] = map[1][i - 1];
			} else {
				map[1][i] = -1;
				i++;
			}
		}
		// �� 2 � �� �������������
		for (int j = 2; j < b - 2; j++) {

			for (int i = 1; i < a; i++) {

				if (map[j - 1][i] != -1) {
					map[j][i] = map[j - 1][i];
				} else {
					if (i == 1) {
						map[j][i] = 1;
					} else
						map[j][i] = map[j][i - 1];
				}

			}

			for (int i = 2; i < a - 1; i++) {
				if (map[j][i] == map[j][i - 1]) {
					if (random.nextInt(2) < 1) {
						map[j][i] = -1;
						i++;
					}
				}
			}
		}
		// ��������� ������
		for (int i = 1; i < a; i++) {
			if (map[b - 1][i] != -1)
				if (map[b - 2][i] != -1)
					map[b - 1][i] = map[b - 2][i];
				else
					map[b - 1][i] = map[b - 1][i - 1];

		}
		for (int i = 1; i < a; i++) {
			if (map[b - 1][i] == map[b - 1][i - 1])
				map[b - 1][i] = 0;

		}

		for (int i = 1; i < b - 1; i++) {
			for (int j = 1; j < a - 1; j++) {
				if (map[i][j] != -1) {

					map[i][j] = 2;

				} else {

					map[i][j] = 1;
				}
			}

		}
		// ������ ����� � ����������� ������
		for (int j = 0; j < a; j++) {
			for (int i = 0; i < b; i++) {
				if (((j == 0) || (j == a - 1)) && ((i != 0) && (i != b - 1)))
					map[j][i] = 0;

				if ((i == 0) || (i == b - 1))
					map[j][i] = 11;

			}

		}
		map[0][0] = 10;
		map[5][5] = 1;
		while (true) {
			int i = (random.nextInt(a - 1)) + 3;
			int j = (random.nextInt(b - 8) + 1);
			if (map[i][j] == 2) {
				map[i][j] = 13;
				map[i - 1][j + 3] = 3;
				map[i - 1][j + 4] = 3;
				break;
			}
		}

	}

}
