import java.awt.Color;
import javax.swing.JFrame;
//this puzzle is generally known as 'N puzzle' or '15 puzzle'
class BlkPuzzle{
	public static final int n=4;
//	int height,width,blankCellNo,totalNoOfCells;??????
	public static void main(String args[])
	{
		JFrame frame=new JFrame();
		frame.setBackground(Color.GREEN);
		frame.setResizable(true);
		frame.setSize(800,800);
		Grid grid=new Grid(n);
		frame.setTitle(Integer.toString(n*n-1)+" puzzle");
		grid.setBackground(Color.MAGENTA);
		frame.add(grid);
		frame.setVisible(true);
	}
}