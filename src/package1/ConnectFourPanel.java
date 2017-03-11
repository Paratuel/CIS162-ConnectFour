/*****************************************************************
 * The following class controls creates the JPanel, JLabels, and
 * JButtons for the ConnectFourGame. Also asks for user input to
 * establish the size of the board, number of connections required,
 * number of players, and the player's names and chosen color. 
 * @author Patrick Howard Dishaw
 * @version 3rd Oct 2013
*****************************************************************/

package package1;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ConnectFourPanel extends JPanel {
	
	/** JLabel array for holding the board that is seen by the player*/
	private static JLabel[][] board;
	
	/** arrayList for holding each of the player's names */
	private static ArrayList<String> playerNames;
	
	/** arrayList for holding each of the player's colors */
	private static ArrayList<Color> playerColors;
	
	/** arrayList for holding the display of the player's wins */
	private static ArrayList<JLabel> winDisplay;
	
	/** currentColor of a player*/
	private static Color playerColor;
	
	/** currentColor of a player*/
	private static int[] winCount;
	
	/** JButton array for creating "Select" buttons */
	private JButton[] selection;
	
	/** calls ConnectFourGame to create a new game */
	private static ConnectFourGame game;
	
	/** the board size */
	private static int bdSize;
	
	/** the board size default */
	private static int BDSIZEDFT = 10;
	
	/** the connection default */
	private static int CONNECTIONDFT = 4;
	
	/** the default number of players */
	private static int PLAYERSDFT = 2;
	
	/** the default starting player */
	private static int STARTDFT = 1;
	
	/** the player's name */
	private static String name;
	
	/** JMenuItem for starting a new game */
	private JMenuItem newGameItem;
	
	/** JMenuItem for quitting the game */
	private JMenuItem quitItem;
	
	/** the current player */
	private int player;
	
	/** The JButton to exit the game */
	private JButton exit;
	
	/** The JButton to reset the game */
	private JButton reset;
	
	/** The JButton to undo a move */
	private JButton undo;

	/** The top JPanel which displays rest, undo, and exit */
	private JPanel top;
	
	/** The bottom JPanel which displays the grid, select buttons,
	 * and the amount of wins*/
	private static JPanel bottom;
	
	/** the player number that has won */
	private static int winner;
	
	/** the player who plays first */
	private int startingPlayer;
	
	/** the total amount of players in the game */
	private static int playerAmount;
	
	/** the number of placements in a row needed to win */
	private int connections;

	/*****************************************************************
	 * Constructor initializes ArrayList playerNames and playerColors,
	 * sets winCount to the default of 8 long, and starts the game by
	 * initializing newGameQuestions(); and guiSettings();
	 * @param JMenuItem quitItem: for quitting the game
	 * @param JMenuItem newGameItem: for making a new game
	*****************************************************************/
	public ConnectFourPanel
	(JMenuItem quitItem, JMenuItem newGameItem){		

		this.quitItem = quitItem;
		this.newGameItem = newGameItem;
		
		playerNames = new ArrayList<String>();
		playerColors = new ArrayList<Color>();
		winCount = new int[PLAYERSDFT];
		
		newGameQuestions();
		
		guiSettings();
	}
	
	/*****************************************************************
	 * Asks user for the size of the board, number of connections,
	 * number of players, which player is starting, and the desired
	 * name and color for each player. Ends program is given null
	*****************************************************************/
	private void newGameQuestions()	{
		
		//asks for the board size
		String strbdSize = JOptionPane.showInputDialog
				(null, "Enter in the board size: ");
		if(strbdSize == null)
			System.exit(0);
		
		try	{
			bdSize = Integer.parseInt(strbdSize);
		}
		
		//catches error if input is not an integer 
		//and then sets board to default 
		catch(NumberFormatException bdError)	{
			JOptionPane.showMessageDialog
			(null, "Error - Input not valid. Must be an integer. "
				+ "Setting board size to the default of 10.");
			bdSize = BDSIZEDFT;
		}
		
		//sets board to default if the size isn't between 4 and 20
		if(bdSize < 4 || bdSize > 20)	{
			JOptionPane.showMessageDialog
			(null, "Invalid board size: Must be between 4 and 20. "
					+ "Setting board size to the default of 10.");
			bdSize = BDSIZEDFT;
		}
		
		//asks for the number of connections
		String strConnections = JOptionPane.showInputDialog
				(null, "Set number of connections to win: ");
		if(strConnections == null)
			System.exit(0);
		try	{
			connections = Integer.parseInt(strConnections);
		}
		
		//catches error if input is not an integer 
		//and then sets board to default 
		catch(NumberFormatException cError){
			JOptionPane.showMessageDialog
			(null, "Invalid input: Needs to be an integer. "
					+ "Setting connections to the default of 4.");
			connections = CONNECTIONDFT;
		}
		
		// sets connections to default if input is less than two 
		// or greater than the board size
		if(connections < 2 || connections > bdSize){
			JOptionPane.showMessageDialog
			(null, "Invalid connection size: Must not be less than 2 "
					+ "or greater than the board size. "
					+ "Seeting to the default of 4.");
			connections = CONNECTIONDFT;
		}
		
		//asks for the number of players
		String strPlayers = JOptionPane.showInputDialog
				(null, "Enter number of players: ");
		if(strPlayers == null)
			System.exit(0);
		try	{
			playerAmount = Integer.parseInt(strPlayers);
		}
		
		//catches error if input is not an integer 
		//and then sets board to default 
		catch(NumberFormatException plrError){
			JOptionPane.showMessageDialog
			(null, "Error - Input not valid. Must be an integer. "
					+ "Setting player amount to the default of 2.");
			playerAmount = PLAYERSDFT;
		}
		
		//sets player to default if input isn't between 2 and 8
		if(playerAmount < 2 || playerAmount > 8){
			JOptionPane.showMessageDialog
			(null, "Invalid amount of players: "
					+ "Must be an between 2 and 8. "
					+ "Setting player amount to the default of 2.");
			playerAmount = PLAYERSDFT;
		}
		
		//asks which player is playing first
		String strPlayerStart = JOptionPane.showInputDialog
				(null, "Enter the starting player: ");
		if(strPlayerStart == null)
			System.exit(0);
		try{
			startingPlayer = Integer.parseInt(strPlayerStart);
		}
		
		//catches error if input is not an integer 
		//and then sets board to default 
		catch(NumberFormatException plrError){
			JOptionPane.showMessageDialog
			(null, "Error - Input not valid. Must be an integer. "
					+ "Setting starting player to the "
					+ "default of Player 1.");
			startingPlayer = STARTDFT;
		}
		
		//sets to default if input is less than 1 
		//or greater than the player amount
		if(startingPlayer < 1 || startingPlayer > playerAmount){
			JOptionPane.showMessageDialog
			(null, "Invalid amount of players: Must be an between 2 "
					+ "and 8. Setting player amount to "
					+ "the default of 2.");
			startingPlayer = STARTDFT;
		}
		
		//asks for the desired name and color of each player
		for(int x = 1; x <= playerAmount; x++)	{
			playerNames.add(JOptionPane.showInputDialog
					(null, "Enter the name for Player " + x));
			if(playerNames.get(x - 1) == null)
				System.exit(0);
			playerColors.add(JColorChooser.showDialog(this, 
					"Select a color for Player" + x, Color.RED));
			if(playerColors.get(x - 1) == null)
				System.exit(0);
		}
	}
	
	/*****************************************************************
	 * creates the top and button JPanels. The top JPanel displays 
	 * rest, undo, and exit. The bottom JPanel displays the grid, 
	 * select buttons, and the amount of wins
	*****************************************************************/
	private void guiSettings() {
		
		player = startingPlayer;
		
		//initializes the two JPanels
		top = new JPanel();
		bottom = new JPanel();			
		
		//adds Rest, Undo, and Exit to the top panel
		reset = new JButton ("Reset");
		top.add(reset);
		undo = new JButton ("Undo");
		top.add(undo);
		exit = new JButton ("Exit");
		top.add(exit);

		game = new ConnectFourGame(startingPlayer, bdSize); 
		
		//creates GridLayout in the bottom panel
		bottom.setLayout(new GridLayout(bdSize+2,bdSize,1,1));  
		
		//creates listeners for exit, undo, reset, 
		//quitItem, and  newGameItem
		ButtonListener listener = new ButtonListener();
		exit.addActionListener(listener);
		reset.addActionListener(listener);
		undo.addActionListener(listener);
		quitItem.addActionListener(listener);
		newGameItem.addActionListener(listener);
		
		//creates required Select buttons 
		selection = new JButton[bdSize];
		
		for (int col = 0; col < bdSize; col++) {
			selection[col] = new JButton ("Select");
			selection[col].addActionListener(listener);
			bottom.add(selection[col]);
		}

		//initializes board and sets the text to X
		board = new JLabel[bdSize][bdSize];
		for (int row = 0; row < bdSize; row++) {
			for (int col = 0; col < bdSize; col++) {
				board[row][col] = new JLabel("X");
				board[row][col].setForeground(Color.BLACK);
				bottom.add(board[row][col]);					
			}
		}
		setLayout(new BorderLayout());
		add (BorderLayout.NORTH,top);
		add (BorderLayout.CENTER,bottom);
		
	}
	
	/*****************************************************************
	 * resets the board display to show any EMPTY (-1) as an X
	 * and any player spaces to their designated colors
	*****************************************************************/
	public static void resetBoardDisplay(){
		for(int row = 0; row < bdSize; row++)
			for(int col = 0; col < bdSize; col++){
				if(game.getBoard(row, col) == ConnectFourGame.EMPTY)
					board[row][col].setText("X");
					board[row][col].setForeground(Color.BLACK);
				for(int i = 1; i <= playerAmount; i++)	{
					if(game.getBoard(row, col) == i)	{
						playerColor = playerColors.get(i-1);
						board[row][col].setText("" + i);
						board[row][col].setForeground(playerColor);
					}
				}

			}
	}
	
	/*****************************************************************
	 * displays the amount of wins each players has
	 * @param int winningPlayer: the latest player that was won
	*****************************************************************/
	public static void winCounter(int winningPlayer)	{
		
		winCount = new int[8];
		winner = winningPlayer;
		winCount[winner]++;
	}



	/*****************************************************************
	 * Represents a listener for button push (action) events.
	*****************************************************************/
	private class ButtonListener implements ActionListener
	{
		/*****************************************************************
		 * Updates the counter and label when the button is pushed.
		*****************************************************************/
		public void actionPerformed (ActionEvent event)
		{

			JComponent comp = (JComponent) event.getSource();
			//when select is clicked, it sets the first empty space to
			//be occupied by a player and displays the player's number
			//in their chosen color. If column is full, the player
			//can place again
			for (int col = 0; col < bdSize; col++)
				if (comp == selection[col]) {
					int row = game.selectCol(col);
					if (row != -1) {
						board[row][col].setText(""
								+game.getCurrentPlayer());
						playerColor = playerColors.get
								(game.getCurrentPlayer()-1);
						board[row][col].setForeground(playerColor);
						game.nextPlayer(playerAmount);
					}
					else
						JOptionPane.showMessageDialog
							(null,"Column is full!");
				}
				
			//calls the isWinner method after each placement
			//to check if there is a winner. If there is,
			//the board and display will be reset and 
			//that players winCounter will go up by one
			if (game.isWinner(connections, playerAmount) 
					== GameStatus.PlayerWON)	{
				game.previousPlayer(playerAmount);
				int p = game.getCurrentPlayer();
				JOptionPane.showMessageDialog
					(null,playerNames.get(p - 1) + " won!");
				game.reset(startingPlayer);
				resetBoardDisplay();
				winCounter(p);
				}
				
			//calls the isWinner method after each placement
			//to check if there is no winner and the game ends
			//in a tie. The board and display will be reset
			if(game.isWinner(connections, playerAmount) 
					== GameStatus.Cats){
				JOptionPane.showMessageDialog(null, "Tie Game!");
				game.reset(startingPlayer);
				resetBoardDisplay();
			}
			
			//starts a completely new game. Asks for new values
			//and displays a new board
			if(newGameItem == comp){
				top.removeAll();
				bottom.removeAll();
				newGameQuestions();
				guiSettings();
				top.revalidate();
				bottom.revalidate();
			}
			
			//resets the board and display, but keeps the values given
			//from newGameQuestions()
			if(comp == reset){
				game.reset(startingPlayer);
				resetBoardDisplay();
			}
			
			//takes back a previous move
			if((comp == undo)){
				game.undoMove(playerAmount);
			}
								
			//closes and ends the program/game
			if ((comp == exit) || (quitItem == comp))
				System.exit(1);
		}	
	}
}
