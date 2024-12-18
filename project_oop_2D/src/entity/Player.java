package entity;


import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player extends Entity {
    
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    
    public int haveKey = 0;
    
    public Player(GamePanel gp, KeyHandler keyH) {
    	this.gp = gp;
        this.keyH = keyH;
        
        // MAKE CAMERA TO FOCUS ONLY TO THE CHARACTER
        screenX= gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2;
        
        // MAKE THE CHARACTER SMALLER 
        solidArea = new Rectangle(0, 0, 32, 32);
        solidArea.x = 8;
        solidArea.y = 16;
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
	}
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }

	public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction =  "down";
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

            
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

    public void update(){
    	if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
    	     if(keyH.upPressed == true){
    	            direction = "up";
    	        }else if(keyH.downPressed == true){
    	            direction = "down";
    	        }else if(keyH.leftPressed == true){
    	            direction = "left";
    	        }else if(keyH.rightPressed == true){
    	            direction = "right";
    	        }
    	     	//CHECK TILE COLLISION
    	     	collisionOn = false;
    	     	gp.cChecker.checkTile(this);
    	     	
    	     	int objIndex = gp.cChecker.checkObject(this, true); 
    	     	pickupObject(objIndex);
    	     	// IF COLLISION IS FALSE. PLAYER CA MOVE
    	     	if(collisionOn == false) {
    	     		
    	     		switch(direction) {
	    	     		case "up": worldY -= speed; break;
	    	     		case "down": worldY += speed; break;
	    	     		case "left": worldX -= speed; break;
	    	     		case "right": worldX += speed; break;
    	     		}
    	     	}
    	     	
    	        spriteCounter++;
    	        if (spriteCounter >= 12) { 
    	            if (spriteNum == 1) {
    	                spriteNum = 2;
    	            } else {
    	                spriteNum = 1;
    	            }
    	            spriteCounter = 0;
    	        }
    	}
    }
    
    public void pickupObject(int i) {
    	
    	// WE TOUCHED ANY OBJECT
    	if(i != 999) {
    		String objectName = gp.obj[i].name;
    		
    		switch(objectName) {
    		case "Key":
    			gp.playSE(1);
    			haveKey++;
    			gp.obj[i] = null;
    			gp.ui.showMessage("You Got A Key!");
    			break;
    		case "Door":
    			if(haveKey > 0) {
    				gp.playSE(3);
    				gp.obj[i] = null;
    				haveKey--;
    				gp.ui.showMessage("You Opened A Door!");
    			}else {
    				gp.ui.showMessage("You Need A Key!");
    			}
    			break;
    		case "Boots":
    			gp.playSE(2);
    			speed += 4;
    			gp.obj[i] = null;
    			gp.ui.showMessage("SPEEDUPPPP!");
    			break;
    		case "Chest":
    			if(haveKey > 0) {
    				gp.playSE(4);
    				
    				gp.gamePause();
    				
    				gp.ui.showMessage("You Found A Pong Game!");
    				
    				gp.startPongGame();
    				
    			}
    			break;
    			
    		}
    	}
    	
    }
    
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction){
            case "up": {
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            }
            case "down": {
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            }
            case "left":{
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            }		
            case "right":{
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
            }
        }
        if(image != null)
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
