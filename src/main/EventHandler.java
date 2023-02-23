package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	Rectangle evenRect;
	int eventRectDefaultX, eventRectDefaultY;
	
	public EventHandler(GamePanel gp) {
		this.gp =gp;
		
		evenRect = new Rectangle();
		evenRect.x = 23;
		evenRect.y = 23;
		evenRect.width =2;
		evenRect.height =2;
		eventRectDefaultX = evenRect.x;
		eventRectDefaultY = evenRect.y;
	}
	
	
	public void checkEvent() {
		
//		if(hit(27,16,"right") == true) {
//			//event happens
//			damagePit(gp.dialogueState);
//			
//		}
		
		if(hit(27,16,"right") == true) {
			//event happens
			teleport(gp.dialogueState);
			
		}
		
		if(hit(23,12,"up") == true) {
			healingPool(gp.dialogueState);
		}
		
	}
	
	
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		evenRect.x = eventCol*gp.tileSize + evenRect.x;
		evenRect.y = eventRow*gp.tileSize + evenRect.y;
		
		if(gp.player.solidArea.intersects(evenRect)) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;;
		evenRect.x = eventRectDefaultX;
		evenRect.y = eventRectDefaultY;
		
		return hit;
	}
	
	
	public void teleport(int gameState) {
		
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport!";
		gp.player.worldX = gp.tileSize*37;
		gp.player.worldY = gp.tileSize*10;
		
	}
	
	
	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fail into a pit!";
		gp.player.life -= 1;
		
	}
	
	public void healingPool(int gameState) {
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You drink the water! \n Your life has been recovered.";
			gp.player.life = gp.player.maxLife;
			
		}
		
		
	}
}
