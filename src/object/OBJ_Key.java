package object;

import java.awt.peer.DialogPeer;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key  extends Entity{
	
	public OBJ_Key(GamePanel gp) {
		
		super(gp);
		
		name = "Key";
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nIt opens a door.";
	}
	
}
