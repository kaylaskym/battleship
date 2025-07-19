package battleship;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;


public class Board {
	private static char[][] board = new char[10][10];
	private static Ship[][] shipLocations = new Ship[10][10];
	private static int shipsSunk = 0;
	private static int guessCount = 0; 
	
	
			public static void main(String[] args) {
				

				//fill board with not guessed
				for(int row = 0; row < 10; row++) {
					for(int col = 0; col < 10; col++) {
						board[row][col] = ' ';
					}
				}
				
				//place ships
				placeShip(new Ship("Carrier", 5), shipLocations);
				placeShip(new Ship("Battleship", 4), shipLocations);
				placeShip(new Ship("Cruiser", 3), shipLocations);
				placeShip(new Ship("Submarine", 3), shipLocations);
				placeShip(new Ship("Destroyer", 2), shipLocations);
				
				//run game
					printBoard();
				
					
					//input guess and process string
					
					//String guess = JOptionPane.showInputDialog("Enter your guess: ");
					
				
				//final message
				//JOptionPane.showMessageDialog(null,"Victory! You sunk all ships in " + guessCount + " guesses.");

				
			} //ends main function 
			
			
			
			//displays the board to the console
			private static void printBoard() {
				JFrame grid = new JFrame(); 
				JPanel points = new JPanel();
				points.setLayout(new GridLayout(11, 11));
				
				points.add(new JLabel(""));
				//col labels 1-10
				for (int c=1; c<= 10; c++) {
					// sets label to be a number 1-10
					JLabel col = new JLabel(String.valueOf(c)); 
					col.setBackground(Color.WHITE);
					col.setOpaque(true); 
					
					points.add(col); 
					
				}
				//row labels A-J
				char [] rowLabel = {'A', 'B', 'C', 'D', 'E', 'F','G', 'H', 'I', 'J'};	
				
				for (int r = 0; r < 10; r++)
				{
					JLabel row = new JLabel(String.valueOf(rowLabel[r])); 
					row.setBackground(Color.WHITE);
					row.setOpaque(true);
					points.add(row); 
				
					for(int c=0; c<10; c++) {
						//sets label to be A-J
						//JButton cell = new JButton(String.valueOf(board[r][c]));
						JButton cell = new JButton(".");
						cell.setBackground(Color.CYAN);
						cell.setOpaque(true);
						cell.addActionListener(new clickCell(r,c,cell));
						points.add(cell);
					}
				}
				
					
				grid.add(points); 
				grid.setSize(400,400);
				grid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				grid.setVisible(true); 
				
			}
			
			//randomly places a ship on the board, avoiding conflicts with other ships
			private static void placeShip(Ship ship, Ship[][] shipLocations) {
				boolean conflict;
				int startRow, startCol;
				
				//horizontal ship placement
				if(Math.random() < 0.5) {
					do {
						conflict = false;
						startRow = (int)(Math.random() * 10);
						startCol = (int)(Math.random() * (10 - ship.getSize()));
						for(int i = 0; i < ship.getSize(); i++) {
							if(shipLocations[startRow][startCol + i] != null) {
								conflict = true;
							}
						}
					}while(conflict);
					for(int i = 0; i < ship.getSize(); i++) {
						shipLocations[startRow][startCol + i] = ship;
					}
					
				//vertical ship placement
				}else {
					do {
						conflict = false;
						startRow = (int)(Math.random() * (10 - ship.getSize()));
						startCol = (int)(Math.random() * 10);
						for(int i = 0; i < ship.getSize(); i++) {
							if(shipLocations[startRow + i][startCol] != null) {
								conflict = true;
							}
						}
					}while(conflict);
					for(int i = 0; i < ship.getSize(); i++) {
						shipLocations[startRow + i][startCol] = ship;
					}
				}
			}
		
			
			private static void checkGuess(int guessRow, int guessCol, JButton button) {
				if (board[guessRow][guessCol] != ' ') {
					JOptionPane.showMessageDialog(null, "You already guessed that space!");
				}
				
				else {
					guessCount++;
					Ship shipHit = shipLocations[guessRow][guessCol];
					
					if (shipHit == null) {
						board[guessRow][guessCol] = '.'; 
						JOptionPane.showMessageDialog(null, "Miss!");
						button.setBackground(Color.WHITE);	
					}
					else {
						board[guessRow][guessCol] = 'X'; 
						JOptionPane.showMessageDialog(null, "Hit!");
						button.setBackground(Color.RED);
						shipHit.hit();
					 
					
						if(shipHit.isSunk()) {
							shipsSunk++;
							JOptionPane.showMessageDialog(null, "You sunk the " + shipHit + "ship!");
					}
					guessCount++; 
					
					if(shipsSunk == 5) {
						JOptionPane.showMessageDialog(null, "Victory! You sunk all ships in " + guessCount + "guesses. ");
					}
				}
				
					
				}
			
			} // ends checkGuess 
	 
			
			private static class clickCell implements ActionListener{
				private int row;
				private int col;
				private JButton button; 
				//private char[][]board; 
				//private Ship[][] ship; 
				
				public clickCell(int row, int col, JButton button) {
					this.row = row; 
					this.col = col; 
					this.button = button; 
					//this.board = board; 
					//this.ship = ship; 
				}
				
				public void actionPerformed(ActionEvent e) {
					//specific check for guess in another method
					checkGuess(row,col,button); 
					
					if (shipsSunk == 5) {
						JOptionPane.showMessageDialog(null,"Victory! You sunk all ships in " + guessCount + "guesses. "); 
					}
				} 
				
			} // ends action listener

			

} // ends board class 



	
	



