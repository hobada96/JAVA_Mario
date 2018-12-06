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

/** 화면크기 조절아이템 */
class Item_1 extends Item {

	public Item_1(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.round++;
		this.delete();
		if (Game.round == 1) { // 창커짐
			Game.MAX_WIDTH = 1600;
			Game.SCENE_SWITCH = true;
			this.setPos_x(100);
			this.setPos_y(50);
		}

		if (Game.round == 2) { // 창줄음
			Game.MAX_WIDTH = 300;
			Game.MAX_HEIGHT = 200;
			Game.SCENE_SWITCH = false;
		}
		System.out.println("event_get_item_1 , round : " + Game.round);

	}
}

/** 윈도우창 이동 조절아이템 */
class Item_2 extends Item {

	public Item_2(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.WINDOW_SWITCH = true;
		System.out.println("event_get_item_2 , WindowMove ");
		this.delete();
	}

}

/** 먹으면 다시시작하는 아이템 */
class Item_Bad extends Item {

	public Item_Bad(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.pos_x = Game.round_x;
		Game.pos_y = Game.round_y;
	}

}
