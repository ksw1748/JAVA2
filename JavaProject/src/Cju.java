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

/**
 * 캠퍼스 식당 메뉴 및 대기 시간 알리미를 위한 GUI 프로그램입니다. 사용자가 메뉴를 선택하고 대기 시간을 확인할 수 있도록 돕습니다.
 * <p>
 * 메뉴와 대기 시간 데이터는 외부 파일에서 로드되며, 사용자가 선택한 메뉴에 대해 대기 시간을 실시간으로 갱신할 수 있습니다.
 * </p>
 * 
 * @author KimSeungWoo
 * @version 1.0
 * @since 2024-12-13
 */
public class Cju extends JFrame {

	/** 식당 이름 */
	private String restaurant = "학교 식당";

	/** 메뉴 선택을 위한 콤보박스 */
	private JComboBox<String> menuComboBox;

	/** 대기 시간을 표시할 레이블 */
	private JLabel waiting;

	/** 대기 시간 갱신 버튼 */
	private JButton refreshButton;

	/** 메뉴 데이터 리스트 */
	private static ArrayList<Menu> menuData = new ArrayList<>();

	/** 메뉴 이름과 대기 시간을 매핑하는 맵 */
	private static HashMap<String, Integer> menuWaitTimeMap = new HashMap<>();

	/**
	 * Cju 클래스의 생성자로, GUI를 설정하고 초기화합니다.
	 * <p>
	 * 프레임의 레이아웃과 컴포넌트를 설정하며, 외부 파일에서 메뉴와 대기 시간 데이터를 로드하고, 사용자가 선택한 메뉴에 대한 대기 시간을
	 * 표시합니다.
	 * </p>
	 */
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

		// 메뉴 데이터를 외부 파일에서 로드
		RestaurantFile();

		// 메뉴 콤보박스에 메뉴 추가
		for (Menu menu : menuData) {
			menuComboBox.addItem(menu.getName());
		}
	}

	/**
	 * 외부 파일에서 식당 메뉴와 대기 시간을 로드합니다.
	 * <p>
	 * "restaurant.txt" 파일에서 데이터를 읽고, 각 메뉴와 대기 시간을 {@link Menu} 객체로 만들어
	 * {@link #menuData} 리스트와 {@link #menuWaitTimeMap}에 저장합니다.
	 * </p>
	 */
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

	/**
	 * 선택한 메뉴에 대한 대기 시간을 반환합니다.
	 * 
	 * @param menuName 대기 시간을 조회할 메뉴 이름
	 * @return 해당 메뉴의 대기 시간 (분 단위)
	 */
	private int getWaitTime(String menuName) {
		return menuWaitTimeMap.getOrDefault(menuName, 0);
	}

	/**
	 * 프로그램 실행 시 실행되는 메인 메서드입니다.
	 * 
	 * @param args 명령줄 인자
	 */
	public static void main(String[] args) {
		new Cju();
	}
}

/**
 * 메뉴 정보를 저장하는 클래스입니다. 각 메뉴의 이름과 대기 시간을 저장합니다.
 */
class Menu {

	/** 메뉴 이름 */
	private String name;

	/** 대기 시간 (분 단위) */
	private int waiting;

	/**
	 * Menu 객체를 생성합니다.
	 * 
	 * @param name    메뉴 이름
	 * @param waiting 대기 시간 (분 단위)
	 */
	public Menu(String name, int waiting) {
		this.name = name;
		this.waiting = waiting;
	}

	/**
	 * 메뉴 이름을 반환합니다.
	 * 
	 * @return 메뉴 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 메뉴의 대기 시간을 반환합니다.
	 * 
	 * @return 대기 시간
	 */
	public String getWaiting() {
		return name;
	}
}
