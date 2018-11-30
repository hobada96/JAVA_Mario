package Test;

public class Point {

	private double pos_x;
	private double pos_y;
	private double size;

	Point(double x, double y) {
		this.pos_x = x;
		this.pos_y = y;
	}

	Point(double x, double y, double size) {
		this(x, y);
		this.size = size;
	}

	public double getPos_x() {
		return pos_x;
	}

	public double getPos_y() {
		return pos_y;
	}

	public double getSize() {
		return size;
	}

	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}

	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}

	public void setSize(double size) {
		this.size = size;
	}
}
