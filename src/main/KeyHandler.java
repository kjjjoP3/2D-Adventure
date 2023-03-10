package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	GamePanel gp;
	// debug
	boolean checkDrawTime = false;
	
	boolean showDebugText = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		// CHARATER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		// OPTIONS STATE
		else if (gp.gameState == gp.optionsState) {
			optionsState(code);
		}

	}

	public void titleState(int code) {

		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.comandNum--;
				if (gp.ui.comandNum < 0) {
					gp.ui.comandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.comandNum++;
				if (gp.ui.comandNum > 2) {
					gp.ui.comandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.comandNum == 0) {
					gp.gameState = gp.playState;
//					gp.ui.titleScreenState = 1;
					gp.playMusic(0);
				}
				if (gp.ui.comandNum == 1) {
					// add later

				}
				if (gp.ui.comandNum == 2) {
					System.exit(0);

				}

			}
		}

		else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_W) {
				gp.ui.comandNum--;
				if (gp.ui.comandNum < 0) {
					gp.ui.comandNum = 3;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.comandNum++;
				if (gp.ui.comandNum > 3) {
					gp.ui.comandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.comandNum == 0) {
					System.out.println("Do some fighter specific stuff");
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.comandNum == 1) {
					// add later
					System.out.println("Do some thief specific stuff");
					gp.gameState = gp.playState;
					gp.playMusic(0);

				}
				if (gp.ui.comandNum == 2) {
					System.out.println("Do some sorcerer specific stuff");
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.comandNum == 3) {
					gp.ui.titleScreenState = 0;
				}

			}
		}

	}

	public void playState(int code) {

		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}

		// Debug
		if (code == KeyEvent.VK_T) {
			if (checkDrawTime == false) {
				checkDrawTime = true;
			} else if (showDebugText == true) {
				showDebugText = false;
			}
		}

	}

	public void pauseState(int code) {

		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}

	}

	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}

	public void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_W) {
			if (gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_S) {
			if (gp.ui.slotRow != 3) {
				gp.ui.slotRow++;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
	}
	
	public void optionsState(int code) {
		
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;			
		}
		
		int maxCommancdNum = 0;
		switch (gp.ui.subState) {
		case 0: maxCommancdNum = 5; break;
		case 3: maxCommancdNum = 1; break;
		}
		
		if (code == KeyEvent.VK_W) {
			gp.ui.comandNum--;
			gp.playSE(9);
			if(gp.ui.comandNum < 0) {
				gp.ui.comandNum = maxCommancdNum;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.comandNum ++;
			gp.playSE(9);
			if(gp.ui.comandNum > maxCommancdNum) {
				gp.ui.comandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_A) {
			if(gp.ui.subState == 0) {
				if(gp.ui.comandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if(gp.ui.comandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(9);
				}
			}
		}
		
		if(code == KeyEvent.VK_D) {
			if(gp.ui.subState == 0) {
				if(gp.ui.comandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if(gp.ui.comandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(9);
				}
			}
		}
		
	}

	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}

	}

}
