import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cju extends JFrame {
	private String restaurant = "학교 식당";
	private JComboBox<String> menuComboBox;
	private JLabel waiting;
	private JButton refreshButton;
	private static ArrayList<Menu> menuData = new ArrayList<>();
	private static HashMap<String, Integer> menuWaitTimeMap = new HashMap<>();

	public Cju() {

		JPanel p = new JPanel();
		p.setBackground(Color.decode("#003B5C"));
		p.setLayout(new BorderLayout());

		JLabel headerLabel = new JLabel("캠퍼스 식당 메뉴 및 대기 시간 알리미", JLabel.CENTER);
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
		p.add(headerLabel, BorderLayout.CENTER);

		p.setPreferredSize(new java.awt.Dimension(400, 50));
		add(p, BorderLayout.NORTH);

		JLabel menuLabel = new JLabel("메뉴 선택:");
		menuLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
		add(menuLabel);

		menuComboBox = new JComboBox<>();
		add(menuComboBox);

		waiting = new JLabel("대기 시간: ");
		waiting.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
		add(waiting);

		refreshButton = new JButton("대기 시간 갱신");
		refreshButton.setFont(new Font("Malgun Gothic", Font.BOLD, 11));
		refreshButton.setBackground(Color.decode("#1E90FF"));
		refreshButton.setForeground(Color.WHITE);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectMenu = (String) menuComboBox.getSelectedItem();
				int waitTime = getWaitTime(selectMenu);
				waiting.setText("대기 시간: " + waitTime + "분");
			}
		});
		add(refreshButton);

		menuComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectMenu = (String) menuComboBox.getSelectedItem();
				int waitTime = getWaitTime(selectMenu);
				waiting.setText("대기 시간: " + waitTime + "분");

			}

		});
		ImageIcon logoIcon = new ImageIcon("image/logo.jpg");
		Image logoImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledLogoIcon = new ImageIcon(logoImage);
		JLabel logoLabel = new JLabel(scaledLogoIcon);
		add(logoLabel);

		setTitle("캠퍼스 식당 메뉴 및 대기 시간 알리미");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
		setLayout(new FlowLayout());

		getContentPane().setBackground(Color.WHITE);

		RestaurantFile();

		for (Menu menu : menuData) {
			menuComboBox.addItem(menu.getName());
		}

	}

	private static void RestaurantFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader("restaurant.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {

				String[] parts = line.split(",");
				if (parts.length == 2) {
					String menuName = parts[0].trim();
					int waitingTime = Integer.parseInt(parts[1].trim());

					Menu menu = new Menu(menuName, waitingTime);
					menuData.add(menu);
					menuWaitTimeMap.put(menuName, waitingTime);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	private int getWaitTime(String menuName) {
		return menuWaitTimeMap.getOrDefault(menuName, 0);

	}

	public static void main(String[] args) {
		new Cju();

	}
}

class Menu {
	private String name;
	private int waiting;

	public Menu(String name, int waiting) {
		this.name = name;
		this.waiting = waiting;
	}

	public String getName() {
		return name;
	}

	public String getWaiting() {
		return name;
	}

}
