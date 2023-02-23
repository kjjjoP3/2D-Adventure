package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	
	KeyHandler keyH;
	
	public static int screenX;
	public static int screenY;
	
	public int hasKey = 0;
	int standCounter = 0;
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
		
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
		
	}
	
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
		
	}
	
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true ) {
			if(keyH.upPressed == true) {
				direction = "up";
				

			}else if (keyH.downPressed == true) {
				direction = "down";
				
			}
			else if (keyH.leftPressed == true) {
				direction = "left";
				
			}
			else if (keyH.rightPressed == true) {
				direction = "right";
				
			}
			
			//Check Tile Collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			
			//Check npc Collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			
			// If Collision is false, player can move 
			if(collisionOn == false) {
				
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
					
				}
				
			}
			
			
			spriteCouter ++;
			if(spriteCouter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCouter = 0;
			}
		}
		
		
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
				
		}
	}
	
	public void interactNPC(int i){
		if(i != 999) {
			if(gp.keyH.enterPressed == true) {
				System.out.println("You are hitting an npc!");
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		gp.keyH.enterPressed = false;
	}
	
	public void draw(Graphics2D g2) {

		
		
		BufferedImage image = null;
		
		if (direction.equals("up")) {
		    if (spriteNum == 1) {
		        image = up1;
		    } else if (spriteNum == 2) {
		        image = up2;    
		    }
		} else if (direction.equals("down")) {
		    if (spriteNum == 1) {
		        image = down1;
		    } else if (spriteNum == 2) {
		        image = down2;    
		    }
		} else if (direction.equals("left")) {
		    if (spriteNum == 1) {
		        image = left1;
		    } else if (spriteNum == 2) {
		        image = left2;
		    }
		} else if (direction.equals("right")) {
		    if (spriteNum == 1) {
		        image = right1;
		    } else if (spriteNum == 2) {
		        image = right2;
		    }
		}

		
		g2.drawImage(image, screenX, screenY, null);
		
	}
}
