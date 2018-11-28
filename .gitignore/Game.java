package Test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	JFrame frame;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = (int) (WIDTH * 0.618);
	public static final double SPEED = 0.5;
	public static final int SIZE = 25;
	public static final int JUMP_POWER = 50;
	public static final double GRAVITY = 9.8;
	public static final int GROUND = HEIGHT - 100; // 650쯤
	public static double pos_x = WIDTH / 2;
	public static double pos_y = HEIGHT / 2;
	public static double vec_x = 0;
	public static double vec_y = 0;
	public static double vec2_y = 0;
	public static double vec_jump = 0;
	public static double time = 0;

	ArrayList<PlatForm> list_plat = new ArrayList<>();

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

	}

	public void start() {
		while (true) {

			time += 0.0166666666667;
			Ground();
			Jump();

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

	public void Jump() {
		if (vec_jump > 0) {
			vec_jump -= GRAVITY * time * 10 / 60;
		} else
			vec_jump = 0;
	}

	private class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(Color.RED);
			g.fillOval((int) pos_x, (int) pos_y, SIZE, SIZE);

			g.setColor(Color.BLUE);
			g.fillRect(100, 580, 150, 30);// (100,610)-(250,610)-(250,640)-(100,640) ==> (x,y+30)....
			list_plat.add(new PlatForm(100, 610, 250, 640));

			g.setColor(Color.BLUE);
			g.fillRect(900, 580, 150, 30);
			list_plat.add(new PlatForm(900, 610, 1050, 640));

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
