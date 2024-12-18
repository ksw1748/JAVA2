import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Cju extends JFrame {
	private String restaurant = "학교 식당";
	private JComboBox<String> menuComboBox;
	private JLabel waiting;
	private static ArrayList<Menu> menuData = new ArrayList<>();

	public Cju() {

		menuComboBox = new JComboBox<>();
		add(new JLabel("메뉴 선택:"));
		add(menuComboBox);

		waiting = new JLabel("대기 시간: ");
		add(waiting);
		
		menuComboBox.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 
			 }
			
		});

		setTitle("캠퍼스 식당 메뉴 및 대기 시간 알리미");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
		setLayout(new FlowLayout());
	}

	public static void main(String[] args) {
		new Cju();

	}
}

class Menu {
	private String name;
	private int waiting;
}