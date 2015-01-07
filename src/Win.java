import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Win extends JFrame{//frame that pops up on winning the game
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int noOfMoves;
	Win(int x)
	{
		noOfMoves=x;
		setTitle("You won!!!!!!");
		setResizable(false);
		setSize(200,80);
		JPanel panel=new JPanel();
		panel.setSize(200,80);
		panel.setVisible(true);
		JLabel label=new JLabel();
		label.setSize(80, 50);
		label.setText("No of moves "+noOfMoves);
		label.setVisible(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(label);
		add(panel);
		}
}
