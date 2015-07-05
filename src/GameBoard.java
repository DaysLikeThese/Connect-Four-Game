import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

import javax.swing.JOptionPane;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;


public class GameBoard extends GraphicsProgram {
	public static final int APPLICATION_WIDTH = 700;
	public static final int APPLICATION_HEIGHT = 600;

	private int boxSize = 100;

	private int width = 7;
	private int height = 6;

	private int offset = 5;

	private int[][] grid = new int[width][height];

	private boolean player1Turn = true;
	
	private boolean win = false;
	
	public static void main(String[] args) {
		new GameBoard().start(args);
	}

	public void run() {
		setBackground(Color.BLUE);
		drawGrid(grid);

		Scanner sc = new Scanner(System.in);

		// game loop
		while(true) {
			Font f = new Font("Arial", 1, 40);
			GLabel playAgain = new GLabel("Would you like to play again?", 100, 300);
			playAgain.setColor(Color.BLACK);
			playAgain.setFont(f);
			
			GRoundRect playButton = new GRoundRect(175, 275, 150, 75);
			playButton.setFilled(true);
			playButton.setColor(Color.BLACK);

			if(rowWin() == 1 || colWin() == 1 || diag1Win() == 1 || diag2Win() == 1) {

				//				GRect cover = new GRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);
				//				cover.setFilled(true);
				//				cover.setColor(Color.BLACK);
				//				add(cover);

				GLabel redWin = new GLabel(" Player 1 (red) wins!", 150, 250);
				redWin.setColor(Color.BLACK);
				redWin.setFont(f);
				add(redWin);
				redWin.sendToFront();

				add(playAgain);
				
				add(playButton);

				break;

			} else if(rowWin() == 2 || colWin() == 2 || diag1Win() == 2 || diag2Win() == 2) {

//				GRect cover = new GRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);
//				cover.setFilled(true);
//				cover.setColor(Color.BLACK);
//				add(cover);

				GLabel yelWin = new GLabel(" Player 2 (yellow) wins!", 150, 250);
				yelWin.setColor(Color.BLACK);
				add(yelWin);
				yelWin.setFont(f);

				add(playAgain);
				
				add(playButton);

				break;

			} else {
				System.out.println("What column do you want your piece in? (1-7)");
				int response = sc.nextInt() - 1;
				// player 1 piece in bottom of chosen column
				if(response >= width) {
					System.out.println("Invalid response. Choose again.");
					response = sc.nextInt();
				}
				for(int j = height - 1; j >= 0; j--) {
					if(grid[response][j] == 0) {
						if(player1Turn) {
							grid[response][j] = 1;
						} else {
							grid[response][j] = -1;
						}

						break;
					}

				}

				player1Turn = !player1Turn;
				drawGrid(grid);
				
				if(win) {
					String answer = JOptionPane.showInputDialog("Play Again?");
					if(answer.toLowerCase().equals("Yes")) {
						for(int j = 0; j < height; j++) {
							for(int i = 0; i < height; i++) {
								grid[i][j] = 0;
							}
						}
						removeAll();
					}
					
				}
				
			}
		}

	}

	private void drawGrid(int[][] grid2) {
		GOval holes;
		for(int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if(grid2[i][j] == 1) {
					holes = new GOval(boxSize * i + offset, boxSize * j + offset, 90 , 90);
					add(holes);
					holes.setFilled(true);
					holes.setColor(Color.RED);
				} else if(grid2[i][j] == -1) {
					holes = new GOval(boxSize * i + offset, boxSize * j + offset, 90 , 90);
					add(holes);
					holes.setFilled(true);
					holes.setColor(Color.YELLOW);
				} else {
					holes = new GOval(boxSize * i + offset, boxSize * j + offset, 90 , 90);
					add(holes);
					holes.setFilled(true);
					holes.setColor(Color.WHITE);

				}


			}

		}

	}
	
	private void checkWin() {
			if(rowWin() == 1 || colWin() == 1 || diag1Win() == 1 || diag2Win() == 1 ||
					rowWin() == 2 || colWin() == 2 || diag1Win() == 2 || diag2Win() == 2) {
				win = true;
			}
	}

	private int rowWin() {
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width - 3 ; i++) {
				int total = grid[i][j] + grid[i + 1][j] + grid[i + 2][j] + grid[i + 3][j];
				if(total == 4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * (i + k), boxSize * j, boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 1;
				} else if(total == -4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * (i + k), boxSize * j, boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 2;
				}
			}

		}
		return 0;
	}

	private int colWin() {
		for(int j = 0; j < height - 3; j++) {
			for(int i = 0; i < width; i++) {
				int total = grid[i][j] + grid[i][j + 1] + grid[i][j + 2] + grid[i][j + 3];
				if(total == 4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * i, boxSize * (j + k), boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 1;
				} else if(total == -4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * i, boxSize * (j + k), boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 2;
				} 
			}
		}
		return 0;
	}

	private int diag1Win() {
		for(int j = 0; j < height - 3; j++) {
			for(int i = 0; i < width - 3; i++) {
				int total = grid[i][j] + grid[i + 1][j + 1] + grid[i + 2][j + 2] + grid[i + 3][j + 3];
				if(total == 4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * (i + k), boxSize * (j + k), boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 1;
				} else if(total == -4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * (i + k), boxSize * (j + k), boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 2;
				} 
			}

		}
		return 0;
	}

	private int diag2Win() {
		for(int j = 0; j < height - 3; j++) {
			for(int i = 3 ; i < width; i++) {
				int total = grid[i][j] + grid[i - 1][j + 1] + grid[i - 2][j + 2] + grid[i - 3][j + 3];
				if(total == 4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * (i - k), boxSize * (j + k), boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 1;
				} else if(total == -4) {
					for(int k = 0; k < 4; k++) {
						GRect hl = new GRect(boxSize * (i - k), boxSize * (j + k), boxSize, boxSize);
						hl.setFilled(true);
						hl.setColor(Color.GREEN );
						add(hl);
						hl.sendToBack();
					}
					return 2;
				} 
			}

		}
		return 0;
	}

}