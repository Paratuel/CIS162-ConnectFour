/*****************************************************************
 * The following class controls the math and logic that runs and
 * works the Connect Four Game 
 * @author Patrick Howard Dishaw
 * @version 3rd Oct 2013
*****************************************************************/

package package1;

import java.awt.Point;
import java.util.*;
import javax.swing.JOptionPane;

public class ConnectFourGame {
	
	/** array for holding the value of each column and row */
	private int[][] board;
	
	/** current player in the game */
	private int player;
	
	/** contains every move made */
	private ArrayList<Point> undoList;
	
	/** records the row and column of each move and adds to undoList */
	private Point undoPoint;
	
	/** constant value for empty spaces */
	public static int EMPTY = -1;
	
	/*****************************************************************
	 * Constructor creates a new board with specific size X size,
	 * creates a new undoList, and calls the reset method
	 * @param int startingPlayer: number of the starting player
	 * @param int bdSize: size x size of the board
	*****************************************************************/
	public ConnectFourGame (int startingPlayer, int bdSize) {
		
		board = new int[bdSize][bdSize];
		undoList = new ArrayList<Point>();
		reset(startingPlayer);
	}

	/*****************************************************************
	 * The following method sets the player back to the startingPlayer,
	 * sets the value of everything in the board to EMPTY (-1), and
	 * clears undoList
	 * @param int startingPlayer: number of the starting player
	*****************************************************************/
	public void reset(int startingPlayer) {
		
		player = startingPlayer;
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[0].length; c++)
				board[r][c] = EMPTY;
		undoList.clear();
	}
	
	/*****************************************************************
	 * The following method returns the row of the first empty space
	 * in a given column and adds the space to undoList
	 * @param int pCOl: the user selected column
	 * @return the row and EMPTY (-1)
	*****************************************************************/
	public int selectCol (int pCol) {
		
		for (int r = board.length - 1; r >= 0; r--)
			if (board[r][pCol] == EMPTY) {
				undoPoint = new Point(r, pCol);
				undoList.add(undoPoint);
				board[r][pCol] = player;
				return r;
			}				
		return EMPTY;
	}

	/*****************************************************************
	 * The following method advances the player by one and sets the
	 * player to one once the last player is reached
	 * @param int maxPlayer: the last player in the game
	 * @return the player
	*****************************************************************/
	public int nextPlayer(int maxPlayer) {		
		if (player == maxPlayer)
			player = 1;
		else
			player++;
		
		return player;
	}
	
	/*****************************************************************
	 * The following method decreases the player by one and sets the
	 * player to the last plater once player 1 is reached
	 * @param int maxPlayer: the last player in the game
	 * @return the player
	*****************************************************************/
	public int previousPlayer(int maxPlayer) {
		
		if (player == 1)
			player = maxPlayer;
		else
			player --;
		return player;
	}

	/*****************************************************************
	 * The following method returns the player who is current taking 
	 * their turn
	 * @return the player
	*****************************************************************/
	public int getCurrentPlayer() {
		
		return player;
	}
	
	/*****************************************************************
	 * The following method takes back the previous move, all the way
	 * until there are no moves left in the ArrayList undoPoint, at 
	 * which it will display that you cannot undo any further
	 * @param int playerAmount: the amount of players in the game
	*****************************************************************/
	public void undoMove(int playerAmount) {
		
		if(undoList.size() != 0) {
		undoPoint = undoList.remove(undoList.size() - 1);
		board[undoPoint.x][undoPoint.y] = -1;
		ConnectFourPanel.resetBoardDisplay();
		previousPlayer(playerAmount);
		}
		else
			JOptionPane.showMessageDialog(null, "You cannot undo any further.");
	}

	/*****************************************************************
	 * The following method checks to see if a player has won. This 
	 * includes horizontal, vertical and diagonal. Returns a tie if
	 * there is no winner.
	 * @param int winConnections: the number in a row required to win
	 * @param int playerAmount: the amount of players playing
	 * @return GameStatus.PlayerWON: declares that a player won
	 * @return GameStatus.Cats: declares the game is a tie
	 * @return Gamestatus.InProgress:declares there is not yet a winner
	*****************************************************************/
	public GameStatus isWinner(int winConnections, int playerAmount) {	

		int connections = winConnections;
		
		//checks for a horizontal winner
		int count = 0;
		for(int p = 1; p <= playerAmount; p++)	{
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[0].length - (connections - 1); c++){
			count = 0;
			for(int i = 0; i < connections; i++)
				if (board[r][c+i] == p){
					count++;
					if(count == connections)
						return GameStatus.PlayerWON;
				}
			}
		}

		//checks for a vertical winner
		for(int p = 1; p <= playerAmount; p++)	{
		for (int r = 0; r < board.length - (connections - 1); r++)
			for (int c = 0; c < board[0].length; c++){
				count = 0;
				for(int i = 0; i < connections; i++)
				if (board[r+i][c] == p)
				{
					count++;
					if(count == connections)
						return GameStatus.PlayerWON;
				}
			}
		}

		//checks for a positive diagonal winner
		for(int p = 1; p <= playerAmount; p++)	{
		for (int r = 0; r < board.length - (connections - 1); r++)
			for (int c = (connections - 1); c < board[0].length; c++){
				count = 0;
				for(int i = 0; i < connections; i++)
				if (board[r+i][c-i] == p)
				{
					count++;
					if(count == connections)
						return GameStatus.PlayerWON;
				}
			}
		}
		
		//checks for a negative diagonal winner
		for(int p = 1; p <= playerAmount; p++)	{
		for (int r = 0; r < board.length - (connections - 1); r++)
			for (int c = 0; c < board[0].length - (connections - 1); c++){
				count = 0;
				for(int i = 0; i < connections; i++)
				if (board[r+i][c+i] == p)
				{
					count++;
					if(count == connections)
						return GameStatus.PlayerWON;
				}
			}
		}
		
		//checks for a tie game/"Cats"
		count = 0;
		for(int i = 0; i < board[0].length; i++){
				if(board[0][0+i] != -1)
				{
					count++;
					if(count == (board.length))
						return GameStatus.Cats;
				}
			}
		
		//returns InProgress if there is not yet a winner
		return GameStatus.InProgress;
	}
	
	/*****************************************************************
	 * The following method returns the value at the given row and
	 * column
	 * @param int row: the row of the requested value
	 * @param int col: the column of the requested value
	 * @return the value at the given row and column (-1 for empty,
	 * 1 for player 1, 2 for player 2, etc)
	*****************************************************************/	
	public int getBoard(int row, int col) {
		
		return board[row][col];
	}
}
	
