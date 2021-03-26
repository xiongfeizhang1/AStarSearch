/* @author Xiongfei Zhang
 * @version 1.0
 * 
 * A Star Search
 */


//imports all of the util class
import java.util.*;
public class Main {

	//global variables
	static int gRow;
	static int gCol;
	static boolean found;
	//create open and closed list
	public static ArrayList<Node> openList = new ArrayList<Node>();
	public static ArrayList<Node> closedList = new ArrayList<Node>();



	//method to calculate f, g, h, and parent of node, and add to openList
	public static void calcNode(Node current, int parentG, Node parent, int addG)
	{
		if(current.getType() == 3) //if currently at goal
		{
			current.setParent(parent);
			found = true;
		}
		else if (current.getType() == 1 && closedList.contains(current) == false)
		{
			//checks if node is already in openList
			if(openList.contains(current))
			{
				//manhattan method to find heuristic
				int h = current.getH();
				int g = parentG + addG;

				//compares which path of getting to current node is faster
				if (current.getF() > h+g)
				{
					//replace previous parent and g value if current path is faster
					current.setG(g);
					current.setF();
					current.setParent(parent);
				}
			}
			else
			{
				//manhattan method to find heuristic
				int h = Math.abs(gRow - current.getRow());
				h += Math.abs((gCol - current.getCol()));
				int g = parentG + addG;
				current.setH(h);
				current.setG(g);
				current.setF();
				current.setParent(parent);

				openList.add(current);
			}
			
		}

		
	}


	
	public static void addNeighbors(Node current, Node[][] board)
	{
		int tempRow = current.getRow();
		int tempCol = current.getCol();


		//checks the 8 adjacent nodes to current node and sets their values
		calcNode(board[tempRow-1][tempCol-1], current.getG(), current, 14);
		calcNode(board[tempRow-1][tempCol+1], current.getG(), current, 14);
		calcNode(board[tempRow+1][tempCol-1], current.getG(), current, 14);
		calcNode(board[tempRow+1][tempCol+1], current.getG(), current, 14);

		calcNode(board[tempRow+1][tempCol], current.getG(), current, 10);
		calcNode(board[tempRow-1][tempCol], current.getG(), current, 10);
		calcNode(board[tempRow][tempCol-1], current.getG(), current, 10);
		calcNode(board[tempRow][tempCol+1], current.getG(), current, 10);
		
		openList.remove(current);
		closedList.add(current);
	}

	public static boolean checkCurrent(Node board)
	{
		if(openList.contains(board))
		{
			return true;
		}
		return false;
	}
	
	//method to show board
	public static void displayBoard(Node[][] board)
	{	
		System.out.println("    1  2  3  4  5  6  7  8  9  10 11 12 13 14 15");
		for(int i = 1; i < 16; i++) //row
		{
			for(int k = 1; k < 16; k++) //column
			{
				if(k == 1)
				{
					if(i < 10)
					{
						System.out.print(i + "  ");
					}
					else{
						System.out.print(i + " ");
					}
					
				}
				//display different tiles based on node type
				int x = board[i][k].getType();
				if(x == 1)
				{
					System.out.print("[ ]");
				}
				else if (x == 0)
				{
					System.out.print("███");
				}
				else if (x == 2)
				{
					System.out.print("[S]");
				}
				else if (x == 3)
				{
					System.out.print("[G]");
				}
				else if (x == 4)
				{
					System.out.print("[X]");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
	
		//initializes empty board 
		Node[][] board = new Node[17][17];
		for(int i = 0; i < 17; i++) //row
		{
			for(int k = 0; k < 17; k++) //column
			{
					board[i][k] = new Node(i,k,1);


				if(i == 0 || i == 16 || k == 0 || k == 16)
				{
					board[i][k] = new Node(i,k,0); //sets the padding tiles to be untraversable
				}
			}
		}

		System.out.println("\n==============GENERATING BOARD===============\n");
		Random rand = new Random();
		for(int x = 0; x < 23; x++) //randomly generates 10% of total(23) nodes that are unpathable
		{
			int upper = 15;
			board[rand.nextInt(upper)+1][rand.nextInt(upper)+1].setType(0);
		}
		displayBoard(board);
	

		//prompt user values for start and goal nodes
		Scanner scan = new Scanner(System.in); //initialize scanner
		System.out.println("\nEnter the row of the starting node(1-15): ");
		int sRow = scan.nextInt();
		System.out.println("Enter the column of the starting node (1-15): ");
		int sCol = scan.nextInt();
		board[sRow][sCol].setType(2);
		System.out.println("Enter the row of the goal node(1-15): ");
		gRow = scan.nextInt();
		System.out.println("Enter the column of the goal node (1-15): ");
		gCol = scan.nextInt();
		board[gRow][gCol].setType(3);

		//sets g of start node to 0
		board[sRow][sCol].setG(0);
		openList.add(board[sRow][sCol]);
		calcNode(board[sRow][sCol], board[sRow][sCol].getG(), null, 0);
		addNeighbors(board[sRow][sCol], board);


		found = false;

		//while loop to keep adding and removing from openlist
		while(openList.size() > 0 && found == false)
		{
			int leastF = 0;
			//for loop to find smallest f value in openList
			for(int i = 1; i < openList.size(); i++)
			{
				if(openList.get(leastF).getF() > openList.get(i).getF())
				{
					leastF = i;
				}
			}
			addNeighbors(openList.get(leastF), board);
		}
		//if openlist is empty
		if(openList.size() == 0)
		{
			System.out.println("\nNo path could be found");
		}
		else
		{


			ArrayList<Node> path = new ArrayList<Node>();
			int pathCounter = 0;
			path.add(board[gRow][gCol]);
			boolean start = false;
			//loop to add path to array

			while(start == false)
			{
				Node temp = path.get(pathCounter).getParent();
				
				if( temp == null)
				{
					start = true;
				}
				else
				{
					if(temp == board[sRow][sCol])
					{
						
					}
					else
					{
						temp.setType(4);
					}
					path.add(temp);
					pathCounter++;
				}

			}
			System.out.println("==============KEY============ \nS = Start Node\nX = Path Node\nG = Goal Node");
			//shows board with path
			displayBoard(board);
			
		}
		

	}

}
