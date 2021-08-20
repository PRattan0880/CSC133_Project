package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CN;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GameObject implements IDrawable,ICollider{
    private int size;
    private int color;
    private Point2D location;
    private static GameWorld gameWorld;

    /**
     * Construct & initialize a GameObject
     * @param size Size of the gameObject
     * @param color Color of the gameObject
     * @param location Location of gameObject represented in Point2D
     */
    public GameObject(int size,int color, Point2D location){
        this.size=size;
        this.color=color;
        this.location=location;
    }

    public static void gameWorldInstance(GameWorld gw){
        gameWorld=gw;
    }

    /**
     * @return Size of the gameObject
     */
    public int getSize(){
        return size;
    }

    /**
     * Sets the color of gameObject
     * @param color Color of gameObject represented in Int
     */
   public void setColor(int color){
        this.color=color;
   }

    /**
     * Sets the location of gameObject
     * @param location Location of gameObject of type Point2D
     */
    public void setLocation(Point2D location){
        this.location=location;
    }

    /**
     * @return Location of gameObject
     */
    public Point2D getLocation(){
        return location;
    }

    /**
     * @return String representation of gameObject
     */
    public String toString(){
        double xVal=Math.round(location.getX()*10.0)/10.0;
        double yVal=Math.round(location.getY()*10.0)/10.0;
        return "Loc="+xVal+ "," + yVal+" color= [" +
                ColorUtil.red(color) + "," + ColorUtil.green(color) + "," +
                ColorUtil.blue(color)+"]" +" size=" + size;
    }

    public int getColor(){
        return color;
    }

    public Rectangle getBoundingRectangle(int x,int y,int w,int h){
        return new Rectangle(x,y,w,h);
    }

    public GameWorld getGameWorld(){
        return gameWorld;
    }

    public abstract boolean collidesWith(GameObject otherObj);

    public abstract void handleCollision(GameObject otherObj);
}

/**
 * The abstract Fixed class extends the gameObject containing
 * info on all of the fixed objects
 */
abstract class Fixed extends GameObject{

    /**
     * Construct & initialize a Fixed Object
     * @param size Size of the gameObject
     * @param color Color of the gameObject
     * @param location Location of gameObject represented in Point2D
     */
    public Fixed(int size,int color,Point2D location){
        super(size,color,location);
    }

    /**
     * Overrides the setLocation of gameObject
     * @param location Location of gameObject of type Point2D
     */
    @Override
    public void setLocation(Point2D location) {}


}

/**
 * The SkyScraper class extends Fixed class and contains
 * info on the SkyScraper object
 */
class SkyScraper extends Fixed{
    private int sequenceNumber;
    private boolean isReached=false;
    private static final int NUMBER_OF_SKYSCRAPER=10;
    private Image[] reachedSkyScraper=new Image[NUMBER_OF_SKYSCRAPER];
    private Image[] unreachedSkyScraper=new Image[NUMBER_OF_SKYSCRAPER];

    /**
     * Construct & initialize SkyScraper object
     * @param size Size of the SkyScraper Object
     * @param color Color of the SkyScraper Object
     * @param location Location of the SkyScraper Object in Point2D
     * @param sequenceNumber sequenceNumber of the SkyScraper Object
     */
    public SkyScraper(int size,int color,Point2D location,int sequenceNumber){
        super(size,color,location);
        this.sequenceNumber=sequenceNumber;

        try{
            unreachedSkyScraper[0]=Image.createImage("/SkyScraperShape_0.png");
            unreachedSkyScraper[1]=Image.createImage("/SkyScraperShape_1.png");
            unreachedSkyScraper[2]=Image.createImage("/SkyScraperShape_2.png");
            unreachedSkyScraper[3]=Image.createImage("/SkyScraperShape_3.png");
            unreachedSkyScraper[4]=Image.createImage("/SkyScraperShape_4.png");
            unreachedSkyScraper[5]=Image.createImage("/SkyScraperShape_5.png");
            unreachedSkyScraper[6]=Image.createImage("/SkyScraperShape_6.png");
            unreachedSkyScraper[7]=Image.createImage("/SkyScraperShape_7.png");
            unreachedSkyScraper[8]=Image.createImage("/SkyScraperShape_8.png");
            unreachedSkyScraper[9]=Image.createImage("/SkyScraperShape_9.png");

            reachedSkyScraper[0]=Image.createImage("/SkyScraperShapeReached_0" +
                    ".png");
            reachedSkyScraper[1]=Image.createImage("/SkyScraperShapeReached_1" +
                    ".png");
            reachedSkyScraper[2]=Image
                    .createImage("/SkyScraperShapeReached_2.png");
            reachedSkyScraper[3]=Image
                    .createImage("/SkyScraperShapeReached_3.png");
            reachedSkyScraper[4]=Image
                    .createImage("/SkyScraperShapeReached_4.png");
            reachedSkyScraper[5]=Image
                    .createImage("/SkyScraperShapeReached_5.png");
            reachedSkyScraper[6]=Image
                    .createImage("/SkyScraperShapeReached_6.png");
            reachedSkyScraper[7]=Image
                    .createImage("/SkyScraperShapeReached_7.png");
            reachedSkyScraper[8]=Image
                    .createImage("/SkyScraperShapeReached_8.png");
            reachedSkyScraper[9]=Image
                    .createImage("/SkyScraperShapeReached_9.png");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @return String representation of SkyScraper object
     */
    public String toString(){
        return "SkyScraper: " + super.toString() + " seqNum=" + sequenceNumber;
    }

    /**
     * @param otherObj GameObject object
     * @return true if GameObject has collided with other
     * gameObject, else false
     */
    public boolean collidesWith(GameObject otherObj) {
        return intersects(this,otherObj);
    }

    /**
     * Checks if GameObject overlaps other GameObject object
     * @param thisRect GameObject object
     * @param thatRect GameObject object
     * @return Boolean if collision
     */
    public boolean intersects(GameObject thisRect,GameObject thatRect){
        Rectangle rectangle1=
                thisRect.getBoundingRectangle((int) thisRect
                                .getLocation().getX(),
                        (int) thisRect.getLocation().getY(),
                        thisRect.getSize(),
                        thisRect.getSize());
        Rectangle rectangle2=
                thatRect.getBoundingRectangle((int)thatRect.
                                getLocation().getX(),
                        (int) thatRect.getLocation().getY(),
                        thatRect.getSize(), thatRect.getSize());
        return rectangle1.intersects(rectangle2);
    }

    @Override
    public void handleCollision(GameObject otherObj) {
        if(otherObj instanceof Helicopter){
            getGameWorld().checkpoint(this,otherObj);
        }
    }

    public void setReached(boolean isReached){
        this.isReached=isReached;
    }

    protected Dimension calcPreferredSize(){
        return new Dimension(reachedSkyScraper[0].getWidth()
                , reachedSkyScraper[0].getHeight());
    }


    /**
     * Draws SkyScraper Shape at relative location
     * @param g Graphics object
     * @param containerOrigin Point object
     */
    @Override
    public void draw(Graphics g, Point containerOrigin){
        if(isReached){
            g.drawImage(reachedSkyScraper[sequenceNumber],
                    (int) (containerOrigin.getX()+getLocation().getX()),
                    (int) (containerOrigin.getY()+getLocation().getY()),
                    getSize(),getSize());
        }else{
            g.drawImage(unreachedSkyScraper[sequenceNumber],
                    (int) (containerOrigin.getX()+getLocation().getX()),
                    (int) (containerOrigin.getY()+getLocation().getY()),
                    getSize(),getSize());
        }



    }
}

class Helipad extends Fixed{
    private Image helipadImage;

    public Helipad(int size,int color,Point2D location){
        super(size,color,location);
        try{
            helipadImage=Image.createImage("/helipadShape.png");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean collidesWith(GameObject otherObj) {
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObj) {

    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.drawImage(helipadImage,
                (int) (containerOrigin.getX()+getLocation().getX()),
                (int) (containerOrigin.getY()+getLocation().getY()),
                getSize(),getSize());

    }

    /**
     * @return String representation of Helipad object
     */
    public String toString(){
        return "Helipad: " + super.toString();
    }


}


/**
 * The RefuelingBlimp class extends the Fixed class & contains
 * info on the RefuelingBlimp object
 */
class RefuelingBlimp extends Fixed{
    private int capacity;
    private Image blimp;
    private Image collidedBlimp;

    /**
     * Construct & initialize RefuelingBlimp object
     * @param size Size of the RefuelingBlimp Object
     * @param color Color of the RefuelingBlimp Object
     * @param location Location of the RefuelingBlimp Object in Point2D
     * @param capacity capacity of the RefuelingBlimp Object
     */
    public RefuelingBlimp(int size,int color,Point2D location, int capacity){
        super(size,color,location);
        this.capacity=capacity;

        try{
            blimp=Image.createImage("/RefuelingBlimp.png");
            collidedBlimp=Image.createImage("/RefuelingBlimpCollided.png");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the capacity of RefuelingBlimp object
     * @param capacity capacity of the RefuelingBlimp
     */
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }

    /**
     * @return Current capacity of the RefuelingBlimp
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * @return String representation of RefuelingBlimp Object
     */
    public String toString(){
        return "RefuelingBlimp: " + super.toString()+" capacity="+capacity;
    }

    /**
     * @param otherObj GameObject object
     * @return true if GameObject has collided with other
     * gameObject, else false
     */
    public boolean collidesWith(GameObject otherObj) {
        return intersects(this,otherObj);
    }

    /**
     * Checks if GameObject overlaps other GameObject object
     * @param thisRect GameObject object
     * @param thatRect GameObject object
     * @return Boolean if collision
     */
    public boolean intersects(GameObject thisRect,GameObject thatRect){
        Rectangle rectangle1=
                thisRect.getBoundingRectangle((int) thisRect
                                .getLocation().getX(),
                        (int) thisRect.getLocation().getY(),
                        thisRect.getSize(),
                        thisRect.getSize());
        Rectangle rectangle2=
                thatRect.getBoundingRectangle((int)thatRect.
                                getLocation().getX(),
                        (int) thatRect.getLocation().getY(),
                        thatRect.getSize(), thatRect.getSize());
        return rectangle1.intersects(rectangle2);
    }

    @Override
    public void handleCollision(GameObject otherObj) {
    }


    /**
     * Draws RefuelingBlimp Shape at relative location
     * @param g Graphics object
     * @param containerOrigin Point object
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        int centerX=
                (int)(containerOrigin.getX()+getLocation().getX()+getSize());
        int centerY=
                (int)(containerOrigin.getY()+getLocation().getY()+getSize());

        if(capacity==0){
            g.drawImage(collidedBlimp,centerX,centerY,getSize(),getSize()/2);
        }else{
            g.drawImage(blimp,centerX,centerY,getSize(),getSize()/2);
        }

        g.setColor(ColorUtil.WHITE);
        g.setFont(Font.createSystemFont(CN.FACE_SYSTEM,CN.STYLE_BOLD,
                CN.SIZE_SMALL));
        g.drawString(Integer.toString(capacity),centerX+getSize()/2,
                centerY+getSize()/6);
    }
}


/**
 * The movable class extends GameObject and contains information
 * on the movable object
 */
abstract class Movable extends GameObject{
    private int heading,speed;

    /**
     * Construct & initialize movable object
     * @param size  Size of the movable object
     * @param color Color of the movable object represented in Int
     * @param location  Location of the movable object in Point2D
     * @param heading   Heading of the movable object
     * @param speed Speed of the movable object
     */
    public Movable(int size,int color,Point2D location,int heading, int speed){
        super(size,color,location);
        this.heading=heading;
        this.speed=speed;
    }

    /**
     * Set the speed of the movable object
     * @param speed Speed of the movable object in Int
     */
    public void setSpeed(int speed){
        this.speed=speed;
    }

    /**
     * @return current heading of the movable object
     */
    public int getHeading(){
        return heading;
    }

    /**
     * @return Current speed of the movable object
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Moves the movable object to a new location(Point2D) based
     * on current speed and heading
     */
    public void move(double milliSec){
        double distance=(double)speed*(milliSec)/1000.0;
        double theta=Math.toRadians(90-heading);
        double deltaX=Math.cos(theta)*distance;
        double deltaY=Math.sin(theta)*distance;
        setLocation(new Point2D.Double((deltaX+(getLocation().getX())),
                deltaY+getLocation().getY()));
    }

    /**
     * Sets the heading of the movable object
     * @param heading Heading of movable object
     */
    public void setHeading(int heading){
        if(heading>=0 && heading<360) {
            this.heading = heading;
        }
    }
    /**
     * @return String representation of movableObject
     */
    public String toString(){
        return super.toString()+ " heading= "+heading + " speed=" + speed;
    }


}

/**
 * The Helicopter class extends movable Object and contains
 * information on the Helicopter object and implements the
 * ISteerable interface
 */
class Helicopter extends Movable implements ISteerable{
    private static final int NUMBER_OF_FRAMES=8;
    private int stickAngle,maximumSpeed,fuelLevel;
    private int fuelConsumptionRate,damageLevel,lastSkyScraperReached;
    private Image[] animateHelicopter=new Image[NUMBER_OF_FRAMES];
    private Image myHelicopter;
    private int index=0;

    /**
     * Construct & initialize Helicopter Object
     * @param size  Size of the helicopter object
     * @param color Color of the helicopter object represented in rgb(r,g,b)
     * @param location  Location of helicopter object in Point2D
     * @param heading   Heading of the helicopter object
     * @param speed Speed of the helicopter object
     * @param stickAngle    Stick Angle of the helicopter object
     * @param maximumSpeed  Maximum speed of the helicopter object
     * @param fuelLevel Fuel level of the helicopter object
     * @param fuelConsumptionRate   Fuel consumption rate of the helicopter
     *                              object
     * @param damageLevel   Damage level of the helicopter object
     * @param lastSkyScraperReached Last skyScraper reached by the helicopter
     *                             object
     */




   public Helicopter(int size,int color,Point2D location,
                      int heading,int speed,int stickAngle,
                      int maximumSpeed,int fuelLevel,int fuelConsumptionRate,
                      int damageLevel,int lastSkyScraperReached){

        super(size,color,location,heading,speed);
        this.stickAngle=stickAngle;
        this.maximumSpeed=maximumSpeed;
        this.fuelLevel=fuelLevel;
        this.fuelConsumptionRate=fuelConsumptionRate;
        this.damageLevel=damageLevel;
        this.lastSkyScraperReached=lastSkyScraperReached;

        try{
            myHelicopter=Image.createImage("/HelicopterShape.png");
            animateHelicopter[0]=Image.createImage("/HelicopterShape1.png");
            animateHelicopter[1]=Image.createImage("/HelicopterShape2.png");
            animateHelicopter[2]=Image.createImage("/HelicopterShape3.png");
            animateHelicopter[3]=Image.createImage("/HelicopterShape4.png");
            animateHelicopter[4]=Image.createImage("/HelicopterShape5.png");
            animateHelicopter[5]=Image.createImage("/HelicopterShape6.png");
            animateHelicopter[6]=Image.createImage("/HelicopterShape7.png");
            animateHelicopter[7]=Image.createImage("/HelicopterShape8.png");
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the lastSkyScraperReached of the helicopter object
     * @param lastSkyScraperReached lastSkyScraperReached by Helicopter
     */
    public void setLastSkyScraperReached(int lastSkyScraperReached){
        this.lastSkyScraperReached=lastSkyScraperReached;
    }

    /**
     * Sets the remaining fuel level of the helicopter object
     */
    public void setRemainingFuel(){
        fuelLevel-=fuelConsumptionRate;
    }

    /**
     * @return Current stickAngle of the helicopter object
     */
    public int getStickAngle(){
        return stickAngle;
    }

    @Override
    /**
     * Decrement the StickAngle by -5 by implementing ISteerable Interface
     */
    public void turnLeft() {
        if(stickAngle>-40){
            stickAngle-=5;
        }
    }

    /**
     * Sets the heading of the helicopter object
     * @param stickAngle    StickAngle of the helicopter object
     */
    public void setHeading(int heading,int stickAngle){
        super.setHeading(heading+stickAngle);
    }

    @Override
    /**
     * Increment the StickAngle by 5 by implementing ISteerable Interface
     */
    public void turnRight() {
        if(stickAngle<40){
            stickAngle+=5;
        }
    }

    /**
     * @return Max Speed of the helicopter object
     */
    public int getMaxSpeed(){
        return maximumSpeed;
    }

    /**
     * @return Current damage level of the helicopter object
     */
    public int getDamageLevel(){
        return damageLevel;
    }

    /**
     * Sets the damage level of the helicopter object
     * @param damageLevel   damageLevel of helicopter object
     */
    public void setDamageLevel(int damageLevel){
        this.damageLevel=damageLevel;
    }

    /**
     * @return Current fuel level of the helicopter object
     */
    public int getFuelLevel(){
        return fuelLevel;
    }

    /**
     * Sets the fuellevel of the helicopter object
     * @param fuelLevel fuelLevel of helicopter object
     */
    public void setFuelLevel(int fuelLevel){
        this.fuelLevel+=fuelLevel;
    }

    /**
     * @return String representation of helicopter object
     */
    public String toString(){
        return "\nHelicopter:\t\t" + super.toString() + " maxSpeed="+
                (maximumSpeed-damageLevel)
                +" stickAngle="+stickAngle+" fuelLevel="+fuelLevel+" " +
                "\ndamageLevel=" + damageLevel;
    }





    /**
     * @return  LastSkyScraperReached by the helicopter object
     */
    public int getLastSkyScraperReached(){
        return lastSkyScraperReached;
    }


    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        int centerX=
                (int)(containerOrigin.getX()+getLocation().getX()+getSize()/2);
        int centerY=
              (int)(containerOrigin.getY()+getLocation().getY()+getSize()/2);
        g.drawImage(animateHelicopter[index].rotate(getHeading()),centerX,
                centerY,getSize(),getSize());
        if(getSpeed()!=0) {
            if(getSpeed()<getMaxSpeed()/2){
                if (index == NUMBER_OF_FRAMES - 1) {
                    index = 0;
                } else {
                    index++;
                }
            }else if(getSpeed()>=getMaxSpeed()/2){
                if(getSpeed()==getMaxSpeed()/2){
                    index=0;
                }else if(index==NUMBER_OF_FRAMES-2){
                    index=0;
                }else{
                    index+=2;
                }
            }else if(getSpeed()<getMaxSpeed()*(.80)){
                if(getSpeed()==getMaxSpeed()*(.80)){
                    index=0;
                }else if(index==NUMBER_OF_FRAMES-3){
                    index=0;
                }else{
                    index+=3;
                }
            }

        }
       /* if(getSpeed()!=0){
            if(index==NUMBER_OF_FRAMES-1){
                index=0;
            }else{
                index++;
            }
        }else if(getSpeed()<=getMaxSpeed()/2){
            if(index==NUMBER_OF_FRAMES-2){
                index=0;
            }else{
                index+=2;
            }
        }*/
    }

    /**
     * @param otherObj GameObject object
     * @return true if GameObject has collided with other
     * gameObject, else false
     */
    public boolean collidesWith(GameObject otherObj) {
        return intersects(this,otherObj);
    }

    /**
     * Checks if GameObject overlaps other GameObject object
     * @param thisRect GameObject object
     * @param thatRect GameObject object
     * @return Boolean if collision
     */
    public boolean intersects(GameObject thisRect,GameObject thatRect){
        Rectangle rectangle1=
                thisRect.getBoundingRectangle((int) thisRect
                                .getLocation().getX(),
                        (int) thisRect.getLocation().getY(),
                        thisRect.getSize(),
                        thisRect.getSize());
        Rectangle rectangle2=
                thatRect.getBoundingRectangle((int)thatRect.
                                getLocation().getX(),
                        (int) thatRect.getLocation().getY(),
                        thatRect.getSize(), thatRect.getSize());
        return rectangle1.intersects(rectangle2);
    }

    @Override
    public void handleCollision(GameObject otherObj) {
        if(otherObj instanceof NonPlayerHelicopter){
            getGameWorld().helicopterCollide();
        }else if(otherObj instanceof SkyScraper){
            getGameWorld().checkpoint(otherObj,this);
            if(getLastSkyScraperReached()==5){
                getGameWorld().gameOver("Congrats!, You have Won in "
                  + getGameWorld().getTimer());
            }
        }else if(otherObj instanceof RefuelingBlimp){
            int fuel=((RefuelingBlimp) otherObj).getCapacity();
            ((RefuelingBlimp) otherObj).setCapacity(0);
            getGameWorld().refuel(fuel);
        }else if(otherObj instanceof Bird){
            getGameWorld().birdCollision();
        }
    }

    public int getWidth(){
        return myHelicopter.getWidth();
    }

    public int getHeight() {
        return myHelicopter.getHeight();
    }

}

/**
 * The Bird class extends the movable class and contains
 * information on the Bird object
 */
class Bird extends Movable{
    private Image[] animateBird=new Image[3];
    private int index=0;

    /**
     * Construct & initialize Bird Object
     * @param size  Size of the bird object
     * @param color Color of the bird object represented in rgb(r,g,b)
     * @param location Location of the bird object in Point2D
     * @param heading   Heading of the bird object
     * @param speed Speed of the bird object
     */
    public Bird(int size,int color,Point2D location,int heading,int speed){
        super(size,color,location,heading,speed);

        try{
            animateBird[0]=Image.createImage("/BirdShape1.png");
            animateBird[1]=Image.createImage("/BirdShape2.png");
            animateBird[2]=Image.createImage("/BirdShape3.png");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * @return String representation of bird object
     */
    public String toString(){
        return "Bird: " + super.toString();
    }

    /**
     * Sets the heading of the bird object
     * @param heading Heading of movable object
     */
    public void setHeading(int heading){
        super.setHeading(super.getHeading()+heading);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        int centerX=
                (int)(containerOrigin.getX()+getLocation().getX()+getSize()/2);
        int centerY=
                (int)(containerOrigin.getY()+getLocation().getY()+getSize()/2);

        g.drawImage(animateBird[index],centerX,centerY,getSize(),
                getSize()/3);

        if(index==animateBird.length-1){
            index=0;
        }else{
            index++;
        }
    }
    /**
     * @param otherObj GameObject object
     * @return true if GameObject has collided with other
     * gameObject, else false
     */
    public boolean collidesWith(GameObject otherObj) {
        return intersects(this,otherObj);
    }

    /**
     * Checks if GameObject overlaps other GameObject object
     * @param thisRect GameObject object
     * @param thatRect GameObject object
     * @return Boolean if collision
     */
    public boolean intersects(GameObject thisRect,GameObject thatRect){
        Rectangle rectangle1=
                thisRect.getBoundingRectangle((int) thisRect
                                .getLocation().getX(),
                        (int) thisRect.getLocation().getY(),
                        thisRect.getSize(),
                        thisRect.getSize());
        Rectangle rectangle2=
                thatRect.getBoundingRectangle((int)thatRect.
                                getLocation().getX(),
                        (int) thatRect.getLocation().getY(),
                        thatRect.getSize(), thatRect.getSize());
        return rectangle1.intersects(rectangle2);
    }


    /**
     * Handle the Collision and invoke appropriate response
     * to collision with GameObject
     * @param otherObj GameObject Object
     */
    public void handleCollision(GameObject otherObj) {
    }

}

/**
 * NonPlayerHelicopter extends Helicopter and contains
 * info on NonPlayerHelicopter Object
 */
class NonPlayerHelicopter extends Helicopter{
    private IStrategy curStrategy;
    private Image[] animateNph=new Image[5];
    private int index=0;

    public NonPlayerHelicopter(int size,int color,Point2D location,
                               int heading,int speed,int stickAngle,
                               int maximumSpeed,int fuelLevel,
                               int fuelConsumptionRate,
                               int damageLevel,int lastSkyScraperReached){
        super(size,color,location,heading,speed,stickAngle,maximumSpeed,
                fuelLevel,fuelConsumptionRate,damageLevel,
                lastSkyScraperReached);

        try{
            animateNph[0]=Image.createImage("/NphShape1.png");
            animateNph[1]=Image.createImage("/NphShape2.png");
            animateNph[2]=Image.createImage("/NphShape3.png");
            animateNph[3]=Image.createImage("/NphShape4.png");
            animateNph[4]=Image.createImage("/NphShape5.png");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void turnRight(){

    }
    public void turnLeft(){

    }

    public void setRemainingFuel(){

    }

    public void setSpeed(int speed){
    }


    /**
     * Sets the strategy to parameter
     * @param s IStrategy Object
     */
    public void setStrategy(IStrategy s){
        curStrategy=s;
    }

    /**
     * Invokes the current strategy for NPH
     */
    public void invokeStrategy(){
        curStrategy.apply();
    }

    /**
     * @return The Current Strategy of NPH
     */
    public IStrategy getStrategy(){
        return curStrategy;
    }

    /**
     * Draws the Image of NPH relative to its location
     * @param g Graphics Object
     * @param containerOrigin Point Object
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        int centerX=
                (int)(containerOrigin.getX()+getLocation().getX()+getSize()/2);
        int centerY=
                (int)(containerOrigin.getY()+getLocation().getY()+getSize()/2);


        if(index==animateNph.length-1){
            index=0;
        }else{
            index++;
        }
        g.drawImage(animateNph[index].rotate(getHeading()),centerX, centerY,
                    getSize(),getSize());
    }


    /**
     * @param otherObj GameObject object
     * @return true if GameObject has collided with other
     * gameObject, else false
     */
    public boolean collidesWith(GameObject otherObj) {
        return intersects(this,otherObj);
    }

    /**
     * Checks if GameObject overlaps other GameObject object
     * @param thisRect GameObject object
     * @param thatRect GameObject object
     * @return Boolean if collision
     */
    public boolean intersects(GameObject thisRect,GameObject thatRect){
        Rectangle rect1=
                thisRect.getBoundingRectangle((int) thisRect
                                .getLocation().getX(),
                        (int) thisRect.getLocation().getY(),
                        thisRect.getSize(),
                        thisRect.getSize());
        Rectangle rectangle2=
                thatRect.getBoundingRectangle((int)thatRect.
                                getLocation().getX(),
                        (int) thatRect.getLocation().getY(),
                        thatRect.getSize(),
                        thatRect.getSize());

        Boolean collided=rect1.intersects(rectangle2);
        return collided;
    }


    /**
     * Handle the Collision and invoke appropriate response
     * to collision with GameObject
     * @param otherObj GameObject Object
     */
    public void handleCollision(GameObject otherObj) {
        if(otherObj instanceof SkyScraper && curStrategy instanceof
                RaceStrategy){
            getGameWorld().checkpoint(((SkyScraper) otherObj)
                    .getSequenceNumber(),this);
            if(getLastSkyScraperReached()==5){
                getGameWorld().gameOver("Game over, a non-player " +
                        "helicopter  wins!\n");
            }
        }

        if(otherObj instanceof SkyScraper && curStrategy instanceof
                DefenseStrategy){
            if(getLastSkyScraperReached()+1==((DefenseStrategy) curStrategy)
                    .getSkyScraper1()
             && getLastSkyScraperReached()+1==((SkyScraper) otherObj)
                    .getSequenceNumber()){
                setLastSkyScraperReached(getLastSkyScraperReached()+1);
            }else if(getLastSkyScraperReached()+1== ((DefenseStrategy)
                    curStrategy).getSkyScraper2()
                    && getLastSkyScraperReached()+1==((SkyScraper) otherObj)
                    .getSequenceNumber()){
                    setLastSkyScraperReached(getLastSkyScraperReached()-1);
            }
        }
    }

    /**
     * @return String representation of current strategy
     */
    public String toString(){
        return "NPH " + super.toString() + " Strategy:"
                +curStrategy.getStrategyType();
    }


}

/**
 * The ISteerable interface contains abstract methods for
 * Steerable objects
 */
interface ISteerable{
    public void turnLeft();
    public void turnRight();
}




