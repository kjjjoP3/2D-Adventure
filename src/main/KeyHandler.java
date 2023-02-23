package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	GamePanel gp;
	//debug
	boolean checkDrawTime = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			
			if(gp.ui.titleScreenState == 0 ) {
				if(code == KeyEvent.VK_W) {
					gp.ui.comandNum--;
					if(gp.ui.comandNum < 0) {
						gp.ui.comandNum = 2;
					}
				}
				if(code == KeyEvent.VK_S) {
					gp.ui.comandNum++;	
					if(gp.ui.comandNum > 2) {
						gp.ui.comandNum = 0;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if(gp.ui.comandNum == 0) {
						gp.ui.titleScreenState = 1;
					}
					if(gp.ui.comandNum == 1) {
						// add later
						
						
					}
					if(gp.ui.comandNum == 2) {
						System.exit(0);
						
						
					}
					
				}
			}
			
			else if(gp.ui.titleScreenState == 1 ) {
				if(code == KeyEvent.VK_W) {
					gp.ui.comandNum--;
					if(gp.ui.comandNum < 0) {
						gp.ui.comandNum = 3;
					}
				}
				if(code == KeyEvent.VK_S) {
					gp.ui.comandNum++;	
					if(gp.ui.comandNum > 3) {
						gp.ui.comandNum = 0;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if(gp.ui.comandNum == 0) {
						System.out.println("Do some fighter specific stuff");
						gp.gameState = gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.comandNum == 1) {
						// add later
						System.out.println("Do some thief specific stuff");
						gp.gameState = gp.playState;
						gp.playMusic(0);
						
					}
					if(gp.ui.comandNum == 2) {
						System.out.println("Do some sorcerer specific stuff");
						gp.gameState = gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.comandNum == 3) {
						gp.ui.titleScreenState = 0;
					}
					
				}
			}
			
			
		}
		
		
		//PLAY STATE
		
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W) {
				upPressed = true;
			}
			if(code == KeyEvent.VK_S) {
				downPressed = true;		
			}
			if(code == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if(code == KeyEvent.VK_D) {
				rightPressed = true;
			}
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			
			//Debug
			if(code == KeyEvent.VK_T) {
				if(checkDrawTime == false) {
					checkDrawTime = true;
				}else if (checkDrawTime == true) {
					checkDrawTime = false;
				}
			}
		}
		
		// PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
			}
		}
		
		//DIALOGUE STATE
		
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
					gp.gameState = gp.playState;
			}
		}
		
		
		
	}

	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;		
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
	}

}
