import javax.swing.JFrame;

public class Cju extends JFrame {
	public Cju() {
		setTitle("캠퍼스 식당 메뉴 및 대기 시간 알리미");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Cju();

	}
}