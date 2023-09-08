package entites;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{

    private  BufferedImage[][] animations;

    private int aniTick, aniIndex , aniSpeed = 30;
    private int playerAction = JUMP;
    private boolean attacking = false;
    private boolean moving = false;

    private boolean jumping = false;
    private float playerSpeed = 2.0f;

    private boolean left, up, right, down;
    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 120,80, null);
    }
    public void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[9][6];

            for(int j = 0; j< animations.length; j++) {
                for(int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
                }
            }

        } catch (IOException e) {
            System.out.println("Not found image");
            e.printStackTrace();
        }
    }


    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
            }
        }
    }


    private void setAnimation() {
        int startAni = playerAction;

        if(moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if(attacking) {
            playerAction = ATTACK_1;
        }

        if(jumping) {
            playerAction = JUMP;
        }

        if(startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private  void updatePos() {

        moving = false;

        if(left && !right) {
            x-= playerSpeed;
            moving = true;
        } else if(right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if(up && !down) {
            y-=playerSpeed;
            moving = true;
        } else if (down && !up) {
            y+= playerSpeed;
            moving = true;
        }

    }

    public void setAttack(boolean att) {
        this.attacking = att;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean j) {
        this.jumping = j;
    }

    public boolean getJumping() {
        return this.jumping;
    }
    public void resetDirBoolean() {
        left = false;
        right = false;
        down = false;
        up = false;
    }
}