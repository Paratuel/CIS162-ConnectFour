/*****************************************************************
 * The following class creates the JFrame and Menu items for the 
 * ConnectFourGamePanel to be displayed in
 * @author Patrick Howard Dishaw
 * @version 3rd Oct 2013
*****************************************************************/

package package1;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ConnectFour{
	
    /*****************************************************************
     * Creates and displays the main program frame.
    *****************************************************************/
	public static void main (String[] args)
	{
	    /** the menu bar for the frame */
		JMenuBar menus;
		
		/** file drop down menu from the JMenuBar */
		JMenu fileMenu;
		
		/** option from fileMenu to quit the game */
		JMenuItem quitItem;
		
		/** option from fileMenu to start a New Game */
		JMenuItem gameItem;
		
		fileMenu = new JMenu ("File");
		
		gameItem = new JMenuItem("New Game");
		
		quitItem = new JMenuItem("Quit");
		
		/** creates JFrame with fileMenu */
		JFrame frame = new JFrame ("Connect Four");
		
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		//adds fileMenu to the frames
		fileMenu.add(gameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		menus.add(fileMenu);
		frame.setJMenuBar(menus);
		
		/** call ConnectFourPanel to create the panel */
		ConnectFourPanel panel 
			= new ConnectFourPanel(quitItem, gameItem);
		
		frame.getContentPane().add(panel);
		frame.setSize(1000, 500);
		frame.setVisible(true);
	}
}
