package Chapter13_Ex03;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;

class MyContentPanTest01 extends JPanel {
	JLabel label;

	public MyContentPanTest01() {
		label = new JLabel("�츮�� �ϳ���!");
		Font font = new Font("�ü�ü", Font.ITALIC, 48);
		label.setFont(font);
		this.add(label);
	}
}

class Airplan01 extends JPanel {
	Image img;
	String imagePath = "C:\\Users\\Administrator\\eclipse-workspace\\day220114\\image\\airplan01.png";
	BufferedImage bfImg;
	public int x = 300, y = 300;
	public JLabel imgLbl = null;

	public Airplan01() {
		// img = Toolkit.getDefaultToolkit().getImage(imagePath);
		// �̹����� �׷��� ���� �޼ҵ�� ����������Ŭ�� ���ؼ� �ڵ� ���� �ȴ�.
		// paint(), repaint(), paintComponent()
//	      try {
//	         bfImg = ImageIO.read(this.getClass().getResource("airplan01.png"));
//	      } catch (IOException e) {
//	         e.printStackTrace();
//	      }
		// ImageIcon imgIcon = new ImageIcon("src/org/comtudy21/day23/airplan01.png");
		ImageIcon imgIcon = new ImageIcon("image/airplan01.png");

		// ImageIcon imgIcon = new ImageIcon(getClass().getResource("airplan01.png"));
		// �̹��� �������� �̹����� �ƴϴ�.
		img = imgIcon.getImage();
		// �̹��� ũ�⵵ ����
		Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(newImg);
		// JLabel()�� ImageIcon ��ü ��� ����.
		imgLbl = new JLabel(imgIcon);
		this.add(imgLbl);
	}

	// ��ü������ - �ϸ�ȴ�!
	// @Override
	// protected void paintComponent(Graphics g) {
	// g.drawImage(img, x, y, 100, 100, this);
	// }
}

class Airplan02 extends JPanel implements Runnable {
	String imagePath = "image/airplan02.png";
	Image img = null;
	ImageIcon imgIco = null;
	int x = 100, y = -100, w = 100, h = 100;
	MyCenterPan centerPan;
	Random rand = new Random();

	public Airplan02(MyCenterPan centerPan) {
		this.centerPan = centerPan;
		x = 100 + (int) rand.nextInt(500);
		imgIco = new ImageIcon(imagePath);
		img = imgIco.getImage();
		Image newImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		imgIco = new ImageIcon(newImg);
		add(new JLabel(imgIco));
	}

	public void movePlain() {
		this.setBounds(x, y, w, h);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10);
				// ��ǥ�� �����ϰ�, �ٽ� �׸���
				y += 3;
				if (y >= 600) {
					y = -100;
					x = 100 + (int) rand.nextInt(500);
				}
				movePlain();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class AirPlanStarterThread extends Thread {
	Airplan02[] airplan02;

	public AirPlanStarterThread(Airplan02[] airplan02) {
		this.airplan02 = airplan02;
	}

	@Override
	public void run() {
		for (int i = 0; i < airplan02.length; i++) {
			Thread th = new Thread(airplan02[i]);
			th.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}

class Bulet extends JPanel implements Runnable {
	MyCenterPan centerPan;
	String imagePath = "image/bulet.png";
	Image img = null;
	ImageIcon imgIco = null;
	int x = 20, y = 400, w = 50, h = 70;

	public Bulet(MyCenterPan centerPan) {
		this.centerPan = centerPan;
		x = centerPan.x1 + 20;
		imgIco = new ImageIcon(imagePath);
		img = imgIco.getImage();
		Image newImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		imgIco = new ImageIcon(newImg);
		add(new JLabel(imgIco));
	}

	public void movePlain() {
		this.setBounds(x, y, w, h);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10);
				// ��ǥ�� �����ϰ� �ٽ� �׸���
				y -= 10;
				if (y < -100) {
					y = 400;
					x = centerPan.x1 + 20;
				}
				movePlain();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class BuletStartThread extends Thread {
	Bulet[] bulet = null;

	public BuletStartThread(Bulet[] bulet) {
		this.bulet = bulet;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < bulet.length; i++) {
			Thread buleTh = new Thread(bulet[i]);
			buleTh.start();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class MyCenterPan extends JPanel {
	int x1 = 350, y1 = 400, w1 = 100, h1 = 100;
	
	public Airplan01 airplan01 = new Airplan01();
	public Airplan02[] airplan02 = new Airplan02[10];
	public Bulet[] bulet = new Bulet[7];

	public MyCenterPan() {
		setLayout(null);
		add(airplan01);

		airplan01.setBounds(x1, y1, w1, h1);

		// airplan2 �̹��� 10�� �����
		for (int i = 0; i < airplan02.length; i++) {
			airplan02[i] = new Airplan02(this);
			add(airplan02[i]);
			airplan02[i].movePlain();
			Thread airplan02th = new Thread(airplan02[i]);

		}

		for (int i = 0; i < bulet.length; i++) {
			bulet[i] = new Bulet(this);
			add(bulet[i]);
		}

		AirPlanStarterThread starter = new AirPlanStarterThread(airplan02);
		starter.start();

		BuletStartThread buletStarter = new BuletStartThread(bulet);
		buletStarter.start();
	}

	public void ariplan01MoveLeft() {
		x1 -= 10;
		airplan01.setBounds(x1, y1, w1, h1);

	}

	public void ariplan01MoveRight() {
		x1 += 10;
		airplan01.setBounds(x1, y1, w1, h1);
	}
}

class MyContentPan extends JPanel {
	MyCenterPan centerPan = new MyCenterPan();
	JButton leftBtn = new JButton("Left");
	JButton rightBtn = new JButton("Right");

	public MyContentPan() {
		setBackground(Color.PINK);
		layoutComponent();
		actionEvetn();
	}

	private void actionEvetn() {
		leftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ư�� ������ ����Ⱑ ȭ���� �������� �̵��Ѵ�.
				centerPan.ariplan01MoveLeft();
				repaint(); // �ش� ������Ʈ�� ȭ���� �ٽ� �׸���.

			}
		});

		rightBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ư�� ������ ����Ⱑ ȭ���� �������� �̵��Ѵ�.
				centerPan.ariplan01MoveRight();
				repaint(); // �ش� ������Ʈ�� ȭ���� �ٽ� �׸���.

			}
		});

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_LEFT) {
					centerPan.ariplan01MoveLeft();
					repaint();
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					centerPan.ariplan01MoveRight();
					repaint(); // �ش� ������Ʈ�� ȭ���� �ٽ� �׸���.
				}

			}
		});

		// Focus�� ���� �� �ֵ��� �����Ѵ�.
		this.setFocusable(true);
		this.requestFocus();
	}

	private void layoutComponent() {
		setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH, new JLabel("�̻��Ϸ� ����� ���߱� ���� - ����Ű�� ����� ����"));
		this.add(centerPan);
		JPanel bottomPan = new JPanel(new FlowLayout());
		bottomPan.add(leftBtn);
		bottomPan.add(rightBtn);
		this.add(BorderLayout.SOUTH, bottomPan);
	}
}

public class Ex01ImgIO extends JFrame {
	Container contentPan;

	public Ex01ImgIO() {
		setTitle("�̹��� �ҷ����� �ǽ�");
		setSize(800, 600);

		// contentPan = getContentPane();
		MyContentPan myContentPan = new MyContentPan();
		setContentPane(myContentPan);
	}

	public static void main(String[] args) {
		new Ex01ImgIO().setVisible(true);
	}

}
