package com.brackeen.javagamebook.tilegame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.sound.sampled.AudioFormat;

import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.sound.*;
import com.brackeen.javagamebook.input.*;
import com.brackeen.javagamebook.test.GameCore;
import static com.brackeen.javagamebook.tilegame.SlidingPane.x;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import com.brackeen.javagamebook.tilegame.sprites.*;
import java.awt.image.ImageObserver;

/**
    GameManager manages all parts of the game.
*/
public class GameManager extends GameCore {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SlidingPane fw=new SlidingPane();
        do{
            if(x==0){
                new GameManager().run();
            }
        }while(x==1);
        
    }

    // uncompressed, 44100Hz, 16-bit, mono, signed, little-endian
    private static final AudioFormat PLAYBACK_FORMAT =
        new AudioFormat(44100, 16, 1, true, false);

    private static final int DRUM_TRACK = 1;

    /**
     *
     */
    public static final float GRAVITY = 0; //0.002f;

    private Point pointCache = new Point();
    private TileMap map;
    private MidiPlayer midiPlayer;
    private SoundManager soundManager;
    private ResourceManager resourceManager;
    private Sound keySound;
    private Sound levelSound;
    private Sound deadSound;
    private InputManager inputManager;
    private TileMapRenderer renderer;

    private GameAction moveLeft;
    private GameAction moveRight;
    private GameAction moveUp;
    private GameAction moveDown;
    private GameAction exit;
    private GameAction pause;
    
    private boolean key;
    private boolean paused;
    
    private int score;
    private int vidas;
    private int contNiveles;
    private Image face;
    private Image hearts;
    private Image gameOver;
    private Image won;
    private Image pausa;
    private Image dbgImage;
    private Graphics dbg;
    
    


    public void init() {
        super.init();
        key=false;
        paused=false;
        score=0;
        vidas=3;
        contNiveles=0;
        // set up input manager
        initInput();

        // start resource manager
        resourceManager = new ResourceManager(screen.getFullScreenWindow().getGraphicsConfiguration());

        // load resources
        renderer = new TileMapRenderer();
        renderer.setBackground(resourceManager.loadImage("background.png"));

        // load first map
        map = resourceManager.loadNextMap();

        // load sounds
        soundManager = new SoundManager(PLAYBACK_FORMAT);
        keySound = soundManager.getSound("sounds/mario.wav");
        levelSound = soundManager.getSound("sounds/key.wav");
        deadSound = soundManager.getSound("sounds/ballon.wav");

        //Carga cara personaje
        face = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/face.png"));
        gameOver = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/gameover.png"));
        hearts = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/Corazon.png"));
        won = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/somuchwin.png"));
        pausa = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/PAUSA.png"));
    }
    
    
    /**
        Tests whether the game is paused or not.
    */
    public boolean isPaused() {
        return paused;
    }


    /**
        Sets the paused state.
    */
    public void setPaused(boolean p) {
        if (paused != p) {
            this.paused = p;
            inputManager.resetAllGameActions();
        }
    }


    /**
        Closes any resurces used by the GameManager.
    */
    public void stop() {
        super.stop();
        soundManager.close();
    }


    private void initInput() {
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveUp = new GameAction ("moveUp");
        moveDown = new GameAction ("moveDown");
        pause = new GameAction("pause",
            GameAction.DETECT_INITAL_PRESS_ONLY);
        exit = new GameAction("exit",
            GameAction.DETECT_INITAL_PRESS_ONLY);

        inputManager = new InputManager(
            screen.getFullScreenWindow());
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
        inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);
    }


    private void checkInput(long elapsedTime) {
        if (pause.isPressed()) {
            setPaused(!isPaused());
        }
        if (exit.isPressed()) {
            stop();
        }

        Player player = (Player)map.getPlayer();
        if (player.isAlive()) {
            float velocityX = 0;
            float velocityY = 0;
            if (moveLeft.isPressed()) {
                velocityX-=player.getMaxSpeed();
            }
            if (moveRight.isPressed()) {
                velocityX+=player.getMaxSpeed();
            }
            if (moveUp.isPressed()) {
                velocityY-=player.getMaxSpeed();
            }
            if (moveDown.isPressed()) {
                velocityY+=player.getMaxSpeed();
            }
            player.setVelocityX(velocityX);
            player.setVelocityY(velocityY);
        }
        
        

    }


    @Override
    public void draw(Graphics2D g) {
        renderer.draw(g, map,screen.getWidth(), screen.getHeight());
        g.drawString("Score:"+score, 250, 50);
        g.drawImage(face, 0 ,0, null);
        switch(vidas){
            case 0:{
              g.drawImage(gameOver, 0 ,0, null); 
              break;
            }
            case 1:{
              g.drawImage(hearts, 100 ,25, null); 
              break;
            }
            case 2:{
              g.drawImage(hearts, 100 ,25, null);
              g.drawImage(hearts, 125 ,25, null);
              break;
            }
            case 3:{
              g.drawImage(hearts, 100 ,25, null);
              g.drawImage(hearts, 125 ,25, null);
              g.drawImage(hearts, 150 ,25, null); 
              break;
            }
        }
        if(contNiveles==3){
            g.drawImage(won, 0 ,0, null);
        }
        if(isPaused()){
            g.drawImage(pausa, 300 ,225, null);
        }
        

    }


    /**
        Gets the current map.
     * @return 
    */
    public TileMap getMap() {
        return map;
    }


    /**
        Turns on/off drum playback in the midi music (track 1).
    */


    /**
        Gets the tile that a Sprites collides with. Only the
        Sprite's X or Y should be changed, not both. Returns null
        if no collision is detected.
     * @param sprite
     * @param newX
     * @param newY
     * @return 
    */
    public Point getTileCollision(Sprite sprite,
        float newX, float newY)
    {
        float fromX = Math.min(sprite.getX(), newX);
        float fromY = Math.min(sprite.getY(), newY);
        float toX = Math.max(sprite.getX(), newX);
        float toY = Math.max(sprite.getY(), newY);

        // get the tile locations
        int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
        int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
        int toTileX = TileMapRenderer.pixelsToTiles(
            toX + sprite.getWidth() - 1);
        int toTileY = TileMapRenderer.pixelsToTiles(
            toY + sprite.getHeight() - 1);

        // check each tile for a collision
        for (int x=fromTileX; x<=toTileX; x++) {
            for (int y=fromTileY; y<=toTileY; y++) {
                if (x < 0 || x >= map.getWidth() ||
                    map.getTile(x, y) != null)
                {
                    // collision found, return the tile
                    pointCache.setLocation(x, y);
                    return pointCache;
                }
            }
        }

        // no collision found
        return null;
    }


    /**
        Checks if two Sprites collide with one another. Returns
        false if the two Sprites are the same. Returns false if
        one of the Sprites is a Creature that is not alive.
     * @param s1
     * @param s2
     * @return 
    */
    public boolean isCollision(Sprite s1, Sprite s2) {
        // if the Sprites are the same, return false
        if (s1 == s2) {
            return false;
        }

        // if one of the Sprites is a dead Creature, return false
        if (s1 instanceof Creature && !((Creature)s1).isAlive()) {
            return false;
        }
        if (s2 instanceof Creature && !((Creature)s2).isAlive()) {
            return false;
        }

        // get the pixel location of the Sprites
        int s1x = Math.round(s1.getX());
        int s1y = Math.round(s1.getY());
        int s2x = Math.round(s2.getX());
        int s2y = Math.round(s2.getY());

        // check if the two sprites' boundaries intersect
        return (s1x < s2x + s2.getWidth() &&
            s2x < s1x + s1.getWidth() &&
            s1y < s2y + s2.getHeight() &&
            s2y < s1y + s1.getHeight());
    }


    /**
        Gets the Sprite that collides with the specified Sprite,
        or null if no Sprite collides with the specified Sprite.
     * @param sprite
     * @return 
    */
    public Sprite getSpriteCollision(Sprite sprite) {

        // run through the list of Sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite otherSprite = (Sprite)i.next();
            if (isCollision(sprite, otherSprite)) {
                // collision found, return the Sprite
                return otherSprite;
            }
        }

        // no collision found
        return null;
    }


    /**
        Updates Animation, position, and velocity of all Sprites
        in the current map.
     * @param elapsedTime
    */
    public void update(long elapsedTime) {
        // get keyboard/mouse input
        checkInput(elapsedTime);
        if(!isPaused()){
        Creature player = (Creature)map.getPlayer();


        // player is dead! start map over
        if (player.getState() == Creature.STATE_DEAD) {
            map = resourceManager.reloadMap();
            return;
        }

        

        // update player
        updateCreature(player, elapsedTime);
        player.update(elapsedTime);

        // update other sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite)i.next();
            if (sprite instanceof Creature) {
                Creature creature = (Creature)sprite;
                if (creature.getState() == Creature.STATE_DEAD) {
                    i.remove();
                }
                else {
                    updateCreature(creature, elapsedTime);
                }
            }
            // normal update
            sprite.update(elapsedTime);
        }
    }
    }

    /**
        Updates the creature, applying gravity for creatures that
        aren't flying, and checks collisions.
    */
    private void updateCreature(Creature creature,
        long elapsedTime)
    {


        // change x
        float dx = creature.getVelocityX();
        float oldX = creature.getX();
        float newX = oldX + dx * elapsedTime;
        Point tile =
            getTileCollision(creature, newX, creature.getY());
        if (tile == null) {
            creature.setX(newX);
        }
        else {
            // line up with the tile boundary
            if (dx > 0) {
                creature.setX(
                    TileMapRenderer.tilesToPixels(tile.x) -
                    creature.getWidth());
            }
            else if (dx < 0) {
                creature.setX(
                    TileMapRenderer.tilesToPixels(tile.x + 1));
            }
            creature.collideHorizontal();
        }
        if (creature instanceof Player) {
            checkPlayerCollision((Player)creature, false);
        }

        // change y
        float dy = creature.getVelocityY();
        float oldY = creature.getY();
        float newY = oldY + dy * elapsedTime;
        tile = getTileCollision(creature, creature.getX(), newY);
        if (tile == null) {
            creature.setY(newY);
        }
        else {
            // line up with the tile boundary
            if (dy > 0) {
                creature.setY(
                    TileMapRenderer.tilesToPixels(tile.y) -
                    creature.getHeight());
            }
            else if (dy < 0) {
                creature.setY(
                    TileMapRenderer.tilesToPixels(tile.y + 1));
            }
            creature.collideVertical();
        }
        if (creature instanceof Player) {
            boolean canKill = (oldY < creature.getY());
            checkPlayerCollision((Player)creature, canKill);
        }

    }


    /**
        Checks for Player collision with other Sprites. If
        canKill is true, collisions with Creatures will kill
        them.
     * @param player
     * @param canKill
    */
    public void checkPlayerCollision(Player player,
        boolean canKill)
    {
        if (!player.isAlive()) {
            return;
        }

        // check for player collision with other sprites
        Sprite collisionSprite = getSpriteCollision(player);
        if (collisionSprite instanceof PowerUp) {
            acquirePowerUp((PowerUp)collisionSprite);
        }
        else if (collisionSprite instanceof Creature) {
            Creature badguy = (Creature)collisionSprite;
            if (canKill) {
                // kill the badguy and make player bounce
                score+=100;
                soundManager.play(deadSound);
                badguy.setState(Creature.STATE_DYING);
                player.setY(badguy.getY() - player.getHeight());
            }
            else {
                // player dies!
                player.setState(Creature.STATE_DYING);
                key=false;
                vidas-=1;
            }
        }
    }


    /**
        Gives the player the speicifed power up and removes it
        from the map.
     * @param powerUp
    */
    public void acquirePowerUp(PowerUp powerUp) {
        // remove it from the map
        //map.removeSprite(powerUp);

        if (powerUp instanceof PowerUp.Star) {
            // do something here, like give the player points
            map.removeSprite(powerUp);
            soundManager.play(keySound);
            key=true;
        }
        else if (powerUp instanceof PowerUp.Music) {
            // change the music
            //soundManager.play(prizeSound);
        }
        else if (key && powerUp instanceof PowerUp.Goal) {
            // advance to next map
            map.removeSprite(powerUp);
            soundManager.play(levelSound,
                new EchoFilter(2000, .7f), false);
            map = resourceManager.loadNextMap();
            contNiveles+=1;
            key=false;
        }
    }
}
