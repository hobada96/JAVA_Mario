package Test;

public class PlatForm {
	private int point_1_x;
	private int point_1_y;
	private int point_2_x;
	private int point_2_y;

	public PlatForm(int x1, int y1, int x2, int y2) {
		point_1_x = x1 - 10;
		point_1_y = y1;
		point_2_x = x2 - 10;
		point_2_y = y2;
	}

	public int getPoint_1_x() {
		return point_1_x;
	}

	public int getPoint_1_y() {
		return point_1_y;
	}

	public int getPoint_2_x() {
		return point_2_x;
	}

	public int getPoint_2_y() {
		return point_2_y;
	}

}
