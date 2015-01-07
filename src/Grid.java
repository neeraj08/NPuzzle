import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Grid extends JPanel {
	private static final long serialVersionUID = 1L;
	int noOfRows;
	int noOfColumns;
	int height;
	int width;
	int blankCellNo;
	int totalNoOfCells;
	int noOfMoves;
	Color rightColor;
	Color wrongColor;
	Color nextCellColor;
	JButton[][] rect;
	GridLayout gl;
	Font myfont;
	Random rand=new Random();
	Grid()
	{
		System.out.println("Grid() called");
		noOfRows=4;
		noOfColumns=4;
		init();
	}
	Grid(int x)
	{
		if(x<2)
			x=2;
		else if(x>10)
			x=10;
		System.out.println("Grid(int) called");
		noOfRows=x;
		noOfColumns=x;
		init();
	}
	
	public void init()
	{
		rightColor=new Color(20,150,20);
		wrongColor=new Color(150,20,20);
		nextCellColor=new Color(220,220,20);
		myfont=new Font("Comic-Sans", 31, 344/noOfRows);
		totalNoOfCells=noOfRows*noOfColumns;
		noOfMoves=0;
		System.out.println("init() called");
		setSize(800, 800);
		System.out.println("size set");
		height=getHeight()/noOfRows;
		width=getWidth()/noOfColumns;
		System.out.println("width: "+width+"\theight: "+height);
		setLayout(new GridLayout(noOfRows,noOfColumns));
		System.out.println("before loop");
		blankCellNo=totalNoOfCells-1;//last cell is blank
		int i,j;
		rect=new JButton[noOfRows][noOfColumns];
		for(i=0;i<noOfRows;i++)
			for(j=0;j<noOfColumns;j++)
			{
			//	System.out.println("in loop");
				
				rect[i][j]=new JButton("Button"+(i*(noOfColumns)+j+1));
		//		rect[i][j].setSize(50, 50);
				//System.out.println("adding rect");
				rect[i][j].setVisible(true);
				
				rect[i][j].setFont(myfont);
				rect[i][j].setBackground(Color.CYAN);
				add(rect[i][j]);
				System.out.println("added rect: "+rect[i][j].getText());
				rect[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int i,j=0;
						for(i=0;i<totalNoOfCells;i++)
							if(arg0.getSource()==rect[i/noOfColumns][i%noOfColumns])
								break;
							System.out.println("Button "+i+" clicked");
							j=i%noOfColumns;
							i=i/noOfColumns;
							//now we have i,j to index into rect[][] array;
							//we need to check if any adjacent cell is empty(not Visible)
							boolean flag=false;
							int pos_i=0,pos_j=0;
							if(j<noOfColumns-1 && !rect[i][j+1].isVisible())
							{
								flag=true;
								pos_i=i;
								pos_j=j+1;
							}
							else if(j>0 && !rect[i][j-1].isVisible())
							{
								flag=true;
								pos_i=i;
								pos_j=j-1;								
							}
							else if(i>0 && !rect[i-1][j].isVisible())
							{
								flag=true;
								pos_i=i-1;
								pos_j=j;								
							}
							else if(i<noOfRows-1 && !rect[i+1][j].isVisible())
							{
								flag=true;
								pos_i=i+1;
								pos_j=j;								
							}
							if(flag)//if one of the adjacent cells is empty then swap texts of these 2 buttons
							{
								noOfMoves++;
								System.out.println("Swapping texts");
								rect[pos_i][pos_j].setText(rect[i][j].getText());
								rect[pos_i][pos_j].setVisible(true);
								rect[i][j].setText("");
								rect[i][j].setVisible(false);
								blankCellNo=i*noOfColumns+j;//starting from 0
							}
							checkForWin();
							setColors();
					}

			
					private void checkForWin() {
						int i,j=0;
						if(isSolved())
						{//won!!!!!!!!
							//display a pop telling no of moves
//!!!!!!!!!remove action listener from buttons after Win
							
							for(i=0;i<noOfRows;i++)
								for(j=0;j<noOfColumns;j++)
									rect[i][j].setEnabled(false);
							System.out.println("Victory...............!!!!!!!!!!!!");
							JFrame d=new Win(noOfMoves);
							d.setTitle("You Wonnnnnnnnnnnnnnnnnnnn!!!!!!!!!");
							d.setVisible(true);
						}
					}
				});
			}
		rect[blankCellNo/noOfColumns][blankCellNo%noOfColumns].setVisible(false);
		rect[blankCellNo/noOfColumns][blankCellNo%noOfColumns].setText("");
		setTextsOnButtons();
		setColors();
	}
	private void setColors() {
		int i,j,k=0,firstWrong=0;
		Boolean flag=false;//first wrong cell not found yet
		for(i=0;i<noOfRows;i++)
			for(j=0;j<noOfColumns;j++)
			{
				k++;
				if(!rect[i][j].isVisible())
				{
					if(flag==false)
						firstWrong=k;
					flag=true;
					
					continue;
				}
				if(Integer.parseInt(rect[i][j].getText())==k)
				{
					rect[i][j].setBackground(rightColor);
				}
				else 
				{
					if(flag==false)
						firstWrong=k;
					flag=true;
					if(firstWrong==Integer.parseInt(rect[i][j].getText()))
						rect[i][j].setBackground(nextCellColor);
					else
						rect[i][j].setBackground(wrongColor);
				}
			}
		
	}
	
	private void setTextsOnButtons() {
		int i;
		for(i=0;i<totalNoOfCells-1;i++)
			{
				rect[i/noOfColumns][i%noOfColumns].setText(Integer.toString(i+1));
			}
		for(i=0;i<2*totalNoOfCells;i++)
		{
			int j,k;
			j=rand.nextInt(totalNoOfCells-1);
			k=rand.nextInt(totalNoOfCells-1);
			swapTexts(rect[j/noOfColumns][j%noOfColumns],rect[k/noOfColumns][k%noOfColumns]);
		}
		while(!isSolvable() ||isSolved())
		{
			modifyGrid();
		}
 
	}
	private boolean isSolved() {
		int val=1,i,j=0;
		if(rect[noOfRows-1][noOfColumns-1].isVisible())
			return false;
		label:{
		for(i=0;i<noOfRows;i++)
			for(j=0;j<noOfColumns;j++)
			{
				if(!rect[i][j].isVisible() || Integer.parseInt(rect[i][j].getText())!=val++)
					break label;
			}
		}
		if(i==noOfRows-1 && j==noOfColumns-1)
			return true;
		return false;
	}
	private boolean isSolvable() {
		Boolean isGridWidthOdd=new Boolean(false);
		Boolean isBlankOnEvenRow=new Boolean(true);
		int iBlank=blankCellNo/noOfColumns;
		int noOfInversions=calcNoOfInversions();
		if(noOfRows%2==1)
			isGridWidthOdd=true;
		
		if((iBlank%2==0 && noOfRows%2==1)||(iBlank%2==1 && noOfRows%2==0))
			isBlankOnEvenRow=false;
		
		if(isGridWidthOdd)
			if(noOfInversions%2==0)
				return true;
			else
				return false;
		else //for even grid width
		{
			if(isBlankOnEvenRow)
				if(noOfInversions%2==1)
					return true;
				else
					return false;
			else//for even grid width and blank on odd row from bottom		
			{
				if(noOfInversions%2==0)
					return true;
				else
					return false;
			}
		}
	}
	private int calcNoOfInversions() {
	//merge sort not used since it will make the code look complicated and also typically n<=8
		int[] vals=new int[totalNoOfCells];
		int i,j,pos=0;
		int noOfInversions=0;
		for(i=0;i<noOfRows;i++)
			for(j=0;j<noOfColumns;j++)
			{
				if(rect[i][j].isVisible())
					vals[pos++]=Integer.parseInt(rect[i][j].getText());
			}
		for(i=0;i<totalNoOfCells-2;i++)
			for(j=i+1;j<totalNoOfCells-1;j++)
				if(vals[i]>vals[j])
					noOfInversions++;
		return noOfInversions;
	}
	private void modifyGrid() {
		int x,y;
		do{//select a random index in grid  which is not the empty cell
			x=rand.nextInt(noOfRows);
			y=rand.nextInt(noOfColumns);
		}
		while(!rect[x][y].isVisible());
		
		//now swap its text with one of the adjacent cells so that the gird may possibly become solvable now
		if(y<noOfColumns-1 &&rect[x][y+1].isVisible())
			swapTexts(rect[x][y], rect[x][y+1]);
		else if(y>0 && rect[x][y-1].isVisible())
			swapTexts(rect[x][y], rect[x][y-1]);
		else if(x<noOfRows-1 && rect[x+1][y].isVisible())
			swapTexts(rect[x][y],rect[x+1][y]);
		else swapTexts(rect[x][y],rect[x-1][y]);
		
	}
	private void swapTexts(JButton b1, JButton b2) {
		String tmp=b1.getText();
		b1.setText(b2.getText());
		b2.setText(tmp);
	}	
}