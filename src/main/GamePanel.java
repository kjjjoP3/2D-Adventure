package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tiles_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 960 px
	public final int screenHeight = tileSize * maxScreenRow; // 576px
	
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	
	BufferedImage tempScreen;
	Graphics2D g2;
	
	public boolean fullScreenOn = false;
	
	
	//FPS
	int FPS = 60;
	
	
	//SYSTEM
	
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	
	Sound music = new Sound();
	Sound se = new Sound();
	
	Thread gameThread;
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler ehHandler = new EventHandler(this);
	public Config config = new Config(this);
	
	//Entity and Object
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[20];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public InteractiveTile iTile[] = new InteractiveTile[50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	public ArrayList<Entity> entityList = new ArrayList<>();
	
	
	// game state
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	
	//set player's default posotion
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
//		playMusic(0);
		gameState = titleState;
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
	
		if(fullScreenOn == true) {
			setFullscreen();	
		}
		
	}

	
	public void setFullscreen() {
		// GET LOCAL SCREEN DEVICE
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		// GET FULL SCREEN DEVICE
		
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight(); 
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;	
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				drawToTempScreen(g2); //draw everything to the buffered image
				drawToScreen(); // draw the buffered image to the screen 
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
//				System.out.println("FPS:" +drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
	}
	
	public void update() {
		
		if(gameState == playState) {
			//Player
			player.update();
			//npc
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
		}
		
		for(int i = 0; i < monster.length; i++) {
			if(monster[i] != null) {
				if(monster[i].alive == true && monster[i].dying == false) {
					monster[i].update();
				}
				if(monster[i].alive == false) {
					monster[i].checkDrop();
					monster[i] = null;
				}
				
			}
		}
		
		for(int i = 0; i < projectileList.size(); i++) {
			if(projectileList.get(i) != null) {
				if(projectileList.get(i).alive == true) {
					projectileList.get(i).update();
				}
				if(projectileList.get(i).alive == false) {
					projectileList.remove(i);
				}
				
			}
		}
		
		for(int i = 0; i < particleList.size(); i++) {
			if(particleList.get(i) != null) {
				if(particleList.get(i).alive == true) {
					particleList.get(i).update();
				}
				if(particleList.get(i).alive == false) {
					particleList.remove(i);
				}
				
			}
		}
		
		
		for(int i = 0; i < iTile.length; i++) {
			if(iTile[i] != null) {
					iTile[i].update();
				
			}
		}
		
		if(gameState == pauseState) {
			// nothing
		}
	}
	
	public void drawToTempScreen(Graphics g) {
		
		super.paintComponent(g);

	    Graphics2D g2 = (Graphics2D)g;
	    
	    //Debug
	    long drawStart = 0;
	    if(keyH.checkDrawTime == true) {
	    	drawStart = System.nanoTime();
	    }
	    
	    //TITLE SCREEN
	    if(gameState == titleState) {
	    	ui.draw(g2);
	    }
	    
	    //OTHERS
	    else {
	    	
	    	//TILE
		    tileM.draw(g2);
		    
		    
		    // INTERACTIVE TILE
		    for (int i = 0; i < iTile.length; i++) {
				if(iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}
		    
		    //ADD ENTITY TC THE LIST
		    entityList.add(player);
		    
		    for(int i = 0; i < npc.length; i++) {
		    	if(npc[i] != null) {
		    		entityList.add(npc[i]);
		    	}
		    }
		    
		    
		    for(int i = 0; i < obj.length; i++) {
		    	if(obj[i] != null) {
		    		entityList.add(obj[i]);
		    	}
		    }
		    
		    for(int i = 0; i < monster.length; i++) {
		    	if(monster[i] != null) {
		    		entityList.add(monster[i]);
		    	}
		    }
		    
		    for(int i = 0; i < projectileList.size(); i++) {
		    	if(projectileList.get(i) != null) {
		    		entityList.add(projectileList.get(i));
		    	}
		    }
		    
		    for(int i = 0; i < particleList.size(); i++) {
		    	if(particleList.get(i) != null) {
		    		entityList.add(particleList.get(i));
		    	}
		    }
		    
		    //SORT
		    
		    Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity o1, Entity o2) {
					int result = Integer.compare(o1.worldY, o2.worldX);
					return result;
				}
		    	
			});
		    
		    
		    //DRAW ENTITYs
		    
		    for(int i = 0; i < entityList.size(); i++) {
		    	entityList.get(i).draw(g2);
		    }
		    
		    //EMTY ENTITYS LIST
//		    for(int i = 0; i < entityList.size(); i++) {
//		    	entityList.remove(i);
//		    }
		    
		    entityList.clear();
		    
		    
		    
//		    //Player
//		    player.draw(g2);
		    
		    //UI
		    ui.draw(g2);
	    	
	    }
	    
	    
	    
	    
	    //Debug
//	    if(keyH.checkDrawTime == true) {
//	    	long drawEnd = System.nanoTime();
//		    long passed = drawEnd - drawStart;
//		    g2.setColor(Color.white);
//		    g2.drawString("Draw Time: "+ passed, 10, 400);
//		    System.out.println("Draw Time: "+ passed);
//	    }
	    
	    if(keyH.showDebugText == true) {
	    	long drawEnd = System.nanoTime();
	    	long passed = drawEnd - drawStart;
	    	
	    	 g2.setFont(new Font("Arial",Font.PLAIN,20));
	    	 g2.setColor(Color.white);
	    	 int x = 10;
	    	 int y = 400;
	    	 int lineHeight = 20;
	    	 
	    	 g2.drawString("WorldX: " + player.worldX, x, y); y+= lineHeight;
	    	 g2.drawString("WorldY" + player.worldY, x, y); y+= lineHeight;
	    	 g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y);y+= lineHeight;
	    	 g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y);y+= lineHeight;
	    	 g2.drawString("Draw Time" + passed, x, y);
	    	
	    }
		
	}
	
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}
