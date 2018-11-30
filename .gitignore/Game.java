package Test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Game {

	JFrame frame;
	public static int WIDTH = 1200;
	public static int HEIGHT = (int) (WIDTH * 0.618);
	public static final double SPEED = 0.5;
	public static final int SIZE = 25;
	public static final int JUMP_POWER = 50;
	public static final double GRAVITY = 9.8;
	public static int GROUND = HEIGHT - 100; // 650쯤
	public static double pos_x = WIDTH / 2;
	public static double pos_y = HEIGHT - 110;
	public static double vec_x = 0;
	public static double vec_y = 0;
	public static double vec2_y = 0;
	public static double vec_jump = 0;
	public static double time = 0;
	public static int MAX_WIDTH = WIDTH;
	public static int MAX_HEIGHT = HEIGHT;
	public static boolean SCENE_SWITCH = false;
	public static int round = 0;

	ArrayList<PlatForm> list_plat = new ArrayList<>();
	Hashtable<String, Item> table_item = new Hashtable<>();

	public Game() {
		frame = new JFrame("GAME");
		MyPanel panel = new MyPanel();
		frame.setContentPane(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);

		listen_key listener_key = new listen_key();
		listen_click listener_click = new listen_click();
		frame.addKeyListener(listener_key);
		frame.addMouseListener(listener_click);

		/// 단 한번 초기화 되는부분
		setMap();

		///
	}

	public void start() {
		while (true) {
			// 0.016초마다 계속 초기화 되는부분
			time += 0.0166666666667;

			Ground();
			Jump();
			GetItems();

			// if (SCENE_SWITCH) {
			if (Scene_change(MAX_WIDTH, MAX_HEIGHT)) {
				SCENE_SWITCH = false;
			}
			// }

			pos_x += vec_x * 5 * SPEED;
			pos_y += -vec_jump / 10 + (vec_y * SPEED);

			frame.repaint();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("EXCEPTION OCCURED");
				e.printStackTrace();
			}
		}
	}

	public void Ground() {
		if (pos_y <= GROUND) {
			vec_y = 0.5 * GRAVITY * time * time;

			// System.out.println("GRAVITY~~" + vec_jump);
		} else
			vec_y = 0;

		for (PlatForm plat : list_plat) {
			// 위에서 아래로 충돌
			if (pos_x >= plat.getPoint_1_x() && pos_x <= plat.getPoint_2_x()
					&& pos_y + SIZE * 1.5 <= plat.getPoint_1_y() && pos_y + SIZE * 2.2 >= plat.getPoint_1_y()) {
				vec_y = 0;
				time = 0;
			}
			// 아래에서 위로 충돌
			if (pos_x >= plat.getPoint_1_x() && pos_x <= plat.getPoint_2_x() && pos_y + 45 >= plat.getPoint_2_y() + 5
					&& pos_y + 45 <= plat.getPoint_2_y() + 15) {
				vec_jump = 0;
				if (time > 1) {
					time = 0;
				}
			}
			// 왼쪽에서 오른쪽으로 충돌
			if (pos_y + 45 >= plat.getPoint_1_y() && pos_y + 45 <= plat.getPoint_2_y() && pos_x <= plat.getPoint_1_x()
					&& pos_x >= plat.getPoint_1_x() - SIZE / 1.5) {
				pos_x = plat.getPoint_1_x() - SIZE / 1.5;
			}
			// 오른쪽에서 왼쪽으로 충돌
			if (pos_y + 45 >= plat.getPoint_1_y() && pos_y + 45 <= plat.getPoint_2_y() && pos_x >= plat.getPoint_2_x()
					&& pos_x <= plat.getPoint_2_x() + SIZE / 1.8) {
				pos_x = plat.getPoint_2_x() + SIZE / 1.8;
			}
		}
	}

	public void setMap() {

		list_plat.add(new PlatForm(100, 610, 250, 640));
		list_plat.add(new PlatForm(900, 610, 1050, 640));
		list_plat.add(new PlatForm(700, 510, 850, 540));
		list_plat.add(new PlatForm(500, 410, 650, 440));
		list_plat.add(new PlatForm(700, 310, 850, 340));
		list_plat.add(new PlatForm(900, 210, 1050, 240));
		table_item.put("item_1", new Item_1(965, 150));

	}

	public void GetItems() {

		if (table_item.isEmpty())
			return;

		Set<String> keys = table_item.keySet();
		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String str = iterator.next();
			Item item = table_item.get(str);
			if (contains(item)) {
				item.event();
				item.delete();
			}

		}
	}

	public void Jump() {
		if (vec_jump > 0) {
			vec_jump -= GRAVITY * time * 10 / 60;
		} else
			vec_jump = 0;
	}

	public boolean contains(Item item) {
		double item_x = item.getPos_x();
		double item_y = item.getPos_y();
		if (pos_x - SIZE <= item_x && pos_x + SIZE >= item_x && pos_y - SIZE <= item_y && pos_y + SIZE >= item_y) {

			return true;
		}

		else
			return false;
	}

	public boolean Scene_change(int max_width, int max_height) {
		if (round == 1) {
			Game.MAX_WIDTH = 1600;
			max_width = 1600;
			
		}

		if (max_width > WIDTH)
			WIDTH = WIDTH + 5;
		if (max_height > HEIGHT)
			HEIGHT++;

		frame.setSize(WIDTH, HEIGHT);

		if (WIDTH < max_width || HEIGHT < max_height) {
			return false;
		} else {

			return true;
		}
	}

	private class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(Color.RED);
			g.fillOval((int) pos_x, (int) pos_y, SIZE, SIZE);

			g.setColor(Color.BLUE);
			g.fillRect(100, 580, 150, 30);// (100,610)-(250,610)-(250,640)-(100,640) ==> (x,y+30)....

			g.setColor(Color.BLUE);
			g.fillRect(900, 580, 150, 30);

			g.setColor(Color.BLUE);
			g.fillRect(700, 480, 150, 30);

			g.setColor(Color.BLUE);
			g.fillRect(500, 380, 150, 30);

			g.setColor(Color.BLUE);
			g.fillRect(700, 280, 150, 30);

			g.setColor(Color.BLUE);
			g.fillRect(900, 180, 150, 30);

			g.setColor(Color.BLACK);
			g.fillOval((int) table_item.get("item_1").getPos_x(), (int) table_item.get("item_1").getPos_y(),
					(int) (SIZE / 1.5), (int) (SIZE / 1.5));

			// Graphics2D g2 = (Graphics2D) g;

		}
	}

	public class listen_key implements KeyListener {

		@Override
		public void keyPressed(KeyEvent k) {
			// TODO Auto-generated method stub
			if (k.getKeyCode() == k.VK_LEFT) {
				if (vec_x == -1)
					return;
				vec_x--;
				System.out.println("KEY_LEFT_PRESSED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_RIGHT) {
				if (vec_x == 1)
					return;
				vec_x++;
				System.out.println("KEY_RIGHT_PRESSED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_UP) {
				System.out.println("KEY_UP_PRESSED");
			}
			if (k.getKeyCode() == k.VK_DOWN) {
				System.out.println("KEY_DOWN_PRESSED");
			}
			if (k.getKeyCode() == k.VK_SPACE) {
				if (vec_y == 0) {
					time = 0;
					vec_jump = JUMP_POWER;
					System.out.println("KEY_SPACE_PRESSED");
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent k) {
			// TODO Auto-generated method stub
			if (k.getKeyCode() == k.VK_LEFT) {
				if (vec_x == 1)
					return;
				vec_x++;
				System.out.println("KEY_LEFT_RELEASED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_RIGHT) {
				if (vec_x == -1)
					return;
				vec_x--;
				System.out.println("KEY_RIGHT_RELEASED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_UP) {
				System.out.println("KEY_UP_RELEASED");
			}
			if (k.getKeyCode() == k.VK_DOWN) {
				System.out.println("KEY_DOWN_RELEASED");
			}
			if (k.getKeyCode() == k.VK_SPACE) {
				System.out.println("KEY_SPACE_RELEASED");
			}
			if (k.getKeyCode() == k.VK_ESCAPE) {
				System.out.println(pos_x + "," + pos_y);
			}
		}

		@Override
		public void keyTyped(KeyEvent k) {
			// TODO Auto-generated method stub

		}

	}

	public class listen_click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent k) {
			// TODO Auto-generated method stub
			System.out.println("(" + k.getX() + "," + k.getY() + ") PRESSED");
		}

		@Override
		public void mouseReleased(MouseEvent k) {
			// TODO Auto-generated method stub

		}

	}

}
