package Test;

abstract public class Item {

	private double pos_x;
	private double pos_y;

	public Item(double x, double y) {
		this.pos_x = x;
		this.pos_y = y;
	}

	public double getPos_x() {
		return pos_x;
	}

	public double getPos_y() {
		return pos_y;
	}

	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}

	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}

	public void delete() {
		this.pos_x = -100;
		this.pos_y = -100;
		System.out.println("DELETED");
	}

	abstract public void event();

}

class Item_1 extends Item {

	public Item_1(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.round++;
		Game.SCENE_SWITCH = true;
		System.out.println("event_get_item_1 , " + Game.round);

	}

}