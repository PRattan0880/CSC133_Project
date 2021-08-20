package org.csc133.a3;

//import com.codename1.ui.Form;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;

/**
 * The gameWorld class contains collection of the game objects
 * and state variables
 */
public class GameWorld implements IObservable{
    private BGSound backgroundSound;
    private Sound helicopterCrash;
    private Sound blimpCrash;
    private Sound heliCheckpoint;
    private Sound birdCrash;
    private int maxWidth;
    private int maxHeight;
    private int lives=3;
    private boolean soundPlaying =true;
    private String timer="";
    private int maximumDamage=50;
    private GameObjectCollection gameObjects=new GameObjectCollection();
    private Vector gameObserver=new Vector();
    private static final Point2D SKYSCRAPER_LOC1= new Point2D
            .Double(200,0);
    private static final Point2D SKYSCRAPER_LOC2=new Point2D.
            Double(400,400);
    private static final Point2D SKYSCRAPER_LOC3=new Point2D.
            Double(1000,800);
    private static final Point2D SKYSCRAPER_LOC4=new Point2D.
            Double(1600,200);
    private static final Point2D SKYSCRAPER_LOC5=new Point2D.
            Double(1600,800);
    private static final Point2D HELIPAD_LOC=new Point2D.Double(1000,500);

    private Vector<GameObject> blimpCollision=new Vector<>();
    private Vector<GameObject> helicopterCollision=new Vector<>();
    private Vector<GameObject> skyScraperCollision=new Vector<>();
    private Vector<GameObject> nphCollision=new Vector<>();
    private Vector<GameObject> birdCollision=new Vector<>();



    /**
     *Initialize the state of the game
     */
    public void init(){

        NonPlayerHelicopter[] nph=new NonPlayerHelicopter[3];
        nph[0]=new NonPlayerHelicopter(100,
                ColorUtil.rgb(0,255,0),
                new Point2D.Double(800,800),90, 100,
                0, 150,150,1,
                0, 1);
        nph[1]=new NonPlayerHelicopter(100,
                ColorUtil.rgb(0,255,0),
               new Point2D.Double(600,600),90,100,
                50, 200, 150,150,
                1, 0);

        nph[2]=new NonPlayerHelicopter(100,
                ColorUtil.rgb(0,255,0),
                new Point2D.Double(200,800),0, 80,0,
                150,150,1,0,
                2);


        int size= randomNumber(100,200);
        gameObjects.add(new RefuelingBlimp(size,
                ColorUtil.rgb(0,0,255),
                new Point2D.Double(randomNumber(0,maxWidth),randomNumber(0,
                        maxHeight)),size));
        gameObjects.add(new Helipad(200,ColorUtil.rgb(0,0,255),
                HELIPAD_LOC));
        size= randomNumber(100,200);
        gameObjects.add(new RefuelingBlimp(size, ColorUtil.rgb(0,0,255)
                , new Point2D.Double(randomNumber(0,maxWidth),
                randomNumber(0, maxHeight)),size));
        gameObjects.add(new Bird(randomNumber(100,150),
                ColorUtil.rgb(255,0,0),
                new Point2D.Double(randomNumber(0, maxWidth),
                        randomNumber(0,maxHeight)), randomNumber(0,360),
                randomNumber(100,150)));
        gameObjects.add(new Bird(randomNumber(100,150),
                ColorUtil.rgb(255,0,0),
                new Point2D.Double(randomNumber(0,maxWidth),
                        randomNumber(0,maxHeight)),
                randomNumber(0,360),randomNumber(100,150)));
        gameObjects.add(new SkyScraper(200,ColorUtil.rgb(0,0,0),
                SKYSCRAPER_LOC1,1));
        gameObjects.add(new SkyScraper(200,ColorUtil.rgb(0,0,0),
                SKYSCRAPER_LOC2,2));
        gameObjects.add(new SkyScraper(200,ColorUtil.rgb(0,0,0),
                SKYSCRAPER_LOC3,3));
        gameObjects.add(new SkyScraper(200,ColorUtil.rgb(0,0,0),
                SKYSCRAPER_LOC4,4));
        gameObjects.add(new SkyScraper(200,ColorUtil.rgb(0,0,0),
                SKYSCRAPER_LOC5,5));

        gameObjects.add(PlayerHelicopter.getInstance());

        nph[0].setStrategy(new AttackStrategy(nph[0], PlayerHelicopter
                .getInstance()));
        nph[0].invokeStrategy();
        nph[1].setStrategy(new RaceStrategy(nph[1],this));
        nph[2].setStrategy(new DefenseStrategy(nph[2],this));

        gameObjects.add(nph[0]);
        gameObjects.add(nph[1]);
        gameObjects.add(nph[2]);
        GameObject.gameWorldInstance(this);
        notifyObservers();
    }

    /**
     * Terminates the program
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Accelerates the helicopter object by increasing current speed
     */
    public void accelerate() {
        for(int i = 0; i< gameObjects.size(); i++) {
            if (gameObjects.getElement(i) instanceof Helicopter) {
                accelerate(i);
            }
        }
    }

    /**
     * Increase the speed of the Helicopter object
     * @param i Integer value for current element in the gameVector
     *          Vector
     */
    private void accelerate(int i){
        int speed= ((Helicopter) gameObjects.getElement(i)).
                getMaxSpeed()-((Helicopter)gameObjects.getElement(i))
                .getDamageLevel();
        if(((Helicopter)gameObjects.getElement(i)).getSpeed()+5<
                speed
        ){
            int initialSpeed=((Helicopter)gameObjects.getElement(i)).
                    getSpeed();
            ((Helicopter) gameObjects.getElement(i)).setSpeed(initialSpeed+5);
        }else{
            System.out.println("\nMax Speed Reached");
        }
    }


    /**
     * Decelerate the helicopter object
     */
    public void brake() {
        for(int i = 0; i< gameObjects.size(); i++) {
            if (gameObjects.getElement(i) instanceof Helicopter) {
                brake(i);
            }
            notifyObservers();
        }
    }

    public void setMaxWidth(int maxWidth){
        this.maxWidth=maxWidth;
    }

    public void setMaxHeight(int maxHeight){
        this.maxHeight=maxHeight;
    }

    /**
     * Decrease the current speed of the helicopter object
     * @param i Integer value for current element in the gameVector
     *          Vector
     */
    private void brake(int i) {
        if(((Helicopter)gameObjects.getElement(i)).getSpeed()>0
        ){
            System.out.println("\nBreaks Applied");
            int speed=((Helicopter) gameObjects.getElement(i)).getSpeed()-5;
            ((Helicopter)gameObjects.getElement(i)).setSpeed(speed);
        }
    }

    /**
     * Turns the Helicopter object to the left (in degrees) &
     * calls turn left method in GameObject to turn left
     */
    public void turnLeft() {
        for(int i = 0; i< gameObjects.size(); i++){
            if(gameObjects.getElement(i) instanceof ISteerable){
                System.out.println("\nHelicopter Turned Left");
                ((ISteerable) gameObjects.getElement(i)).turnLeft();
            }
            notifyObservers();
        }
    }

    /**
     * Turns the Helicopter object to the right (in degrees) &
     * calls turn right method in GameObject to turn left
     */
    public void turnRight() {
        for(int i = 0; i< gameObjects.size(); i++){
            if(gameObjects.getElement(i) instanceof ISteerable){
                System.out.println("\nHelicopter Turned Right");
                ((ISteerable) gameObjects.getElement(i)).turnRight();
            }
            notifyObservers();
        }
    }

    /**
     * Helicopter object collides with another NPC helicopter
     */
    public void helicopterCollide() {
        System.out.println("\nHelicopter collided with another " +
                "helicopter");
         if(soundOn()){
             try{
                 helicopterCrash.play();
             }catch (Exception e){
                 System.out.println("Sound not initialized");
             }

        }
        for(int i = 0; i< gameObjects.size(); i++){
            if(gameObjects.getElement(i) instanceof ISteerable){
                if(!hasMaxDamage(i)){
                    collisionDamage(10,i);
                    ((Helicopter) gameObjects.getElement(i)).
                            setColor(ColorUtil.rgb(102,255, 102));
                }else{
                    reinitialize(i);
                }

            }
        }
    }

    public void createSound(){
        backgroundSound = new BGSound("background.wav");
        blimpCrash=new Sound("blimpExplosion.wav");
        helicopterCrash = new Sound("heliExplosion.wav");
        heliCheckpoint=new Sound("Tone.wav");
        birdCrash=new Sound("birdCrash.wav");
    }

    public void playSound(){
        if(soundOn()){
            try{
                backgroundSound.play();
            }catch (Exception e){
                System.out.println("Sound not initialized");
            }
        }
    }

    /**
     * Increase the damage level of the movable objects
     * @param damage Damage dealt by movable object
     * @param i Integer value for current element in the gameVector
     *          Vector
     */
    private void collisionDamage(int damage,int i){
        int damageLevel=
                ((Helicopter) gameObjects.getElement(i))
                        .getDamageLevel();
        int dmg=damage+damageLevel;
        if(dmg > maximumDamage){
            damageLevel=dmg-maximumDamage;
            dmg=dmg-damageLevel;
        }
        ((Helicopter) gameObjects.getElement(i)).
                setDamageLevel(dmg);
        notifyObservers();
    }

    /**
     * @param i Integer value for current element in the gameVector
     *          Vector
     * @return True or False, whether helicopter object has sustained
     *          max damage
     */
    private boolean hasMaxDamage(int i){
        return ((Helicopter) gameObjects.getElement(i)).getDamageLevel()==
                maximumDamage;
    }

    public int getMaximumDamage(){
        return maximumDamage;
    }


    /**
     * Sets the lives remaining of the gameWorld object
     * @param i
     */
    private void setLives(int i){
        if(lives>0){
            lives--;
            ((Helicopter) gameObjects.getElement(i)).setDamageLevel(0);

        }else{
            gameOver("\nGame over,better luck next time!");
        }
        notifyObservers();

    }

    /**
     * Display game over message & terminate the program by
     * calling the exit method
     * @param gameStatus
     */
    public void gameOver(String gameStatus){
        System.out.println(gameStatus);
        exit();
    }

    /**
     * Refuels the helicopter object by calling the
     * setFuelLevel method of the helicopter object
     */
    public void refuel(int fuel) {
        boolean refueled=false;
        System.out.println("\nHelicopter Refueled");
        for(int i = 0; i< gameObjects.size() && !refueled; i++) {
            if(gameObjects.getElement(i)instanceof RefuelingBlimp){
                if(hasFuel(i)){
                    if(soundOn()){
                        try{
                            blimpCrash.play();
                        }catch (Exception e){
                            System.out.println("Sound not initialized");
                        }
                    }
                    refueled=true;
                }
                ((RefuelingBlimp) gameObjects.getElement(i)).
                        setColor(ColorUtil.rgb(51,51,255));
            }
        }
        refuelHelicopter(fuel);
        int size= randomNumber(100,200);
        gameObjects.add(new RefuelingBlimp(size,
                ColorUtil.rgb(0,0,255),
                new Point2D.Double(randomNumber(0,maxWidth),
                        randomNumber(0,maxHeight)),size));
        notifyObservers();
    }

    /**
     * @param i
     * @return True or False, whether refuelingBlimp object has fuel
     */
    private boolean hasFuel(int i){
        return ((RefuelingBlimp) gameObjects.getElement(i)).getCapacity()!=0;
    }

    /**
     * Refuels the helicopter object
     * @param capacity Fuel level that refuelingBlimp contains
     */
    private void refuelHelicopter(int capacity){
        for(int i = 0; i< gameObjects.size(); i++) {
            if (gameObjects.getElement(i) instanceof Helicopter) {
                ((Helicopter) gameObjects.getElement(i)).setFuelLevel(capacity);
            }
        }
    }

    /**
     * @param i
     * @return True or False, whether or not helicopter has
     * fuel
     */
    private boolean helicopterHasFuel(int i){
        return ((Helicopter) gameObjects.getElement(i)).getFuelLevel()!=0;
    }

    /**
     * Helicopter collides with a bird object
     */
    public void birdCollision() {
        System.out.println("\nHelicopter collided  with Bird");
        for(int i = 0; i< gameObjects.size(); i++){
            if(gameObjects.getElement(i) instanceof ISteerable){
                if(!hasMaxDamage(i)){
                    if(soundOn()){
                        try{
                            birdCrash.play();
                        }catch (Exception e){
                            System.out.println("Sound not initialized");
                        }
                    }
                    collisionDamage(5,i);
                    ((Helicopter) gameObjects.getElement(i)).
                            setColor(ColorUtil.rgb(102,255,102));
                }else{
                    reinitialize(i);
                }
            }
            notifyObservers();
        }
    }

    /**
     * Reinitialize GameWorld
     * @param i Integer i for index
     */
    public void reinitialize(int i){
        setLives(i);
        gameObjects.removeAll();
        PlayerHelicopter.removeInstance();
        init();
        createSound();
    }
    public void draw(Graphics g, Point containerOrigin){
        for(int i = 0; i< gameObjects.size(); i++){
            ((GameObject) gameObjects.getElement(i)).draw(g,containerOrigin);
        }

    }

    /**
     * Increments the timer & calls move methods on the
     * movable objects in order to move them
     */
    public void tick() {
        System.out.println("\nGame clock has ticked");
        for(int i = 0; i< gameObjects.size(); i++){
            if(gameObjects.getElement(i) instanceof Movable){
                if(gameObjects.getElement(i) instanceof Helicopter){
                    tick(i);
                }
                if(gameObjects.getElement(i) instanceof Bird){
                    changeBirdHeading(i);
                    ((Bird) gameObjects.getElement(i)).move(20);
                }
                collision(i);

            }
        }


    }

    public void setElapsedTime(String elapsedTime){
        timer=elapsedTime;
    }

    public String getTimer(){
        return timer;
    }

    /**
     * Moves the movable objects to new location based
     * on current heading and speed
     * @param i Integer value for current element in the gameVector
     *          Vector
     */
    private void tick(int i){
        if(!hasMaxDamage(i) && helicopterHasFuel(i)){
            Point2D tempHeading=
                    ((Movable)(gameObjects).getElement(i)).getLocation();
            Helicopter tempHeli=(Helicopter) gameObjects.getElement(i);
            
            if(tempHeli.getSpeed()<=tempHeli.getMaxSpeed()/4){
                accelerate();
            }

            if(inBounds(tempHeading,tempHeli)){
                changeHeading(i);
                ((Helicopter) gameObjects.getElement(i)).setRemainingFuel();
                if(gameObjects.getElement(i) instanceof NonPlayerHelicopter){
                    ((NonPlayerHelicopter) gameObjects.getElement(i))
                            .invokeStrategy();
                }
                ((Helicopter) gameObjects.getElement(i)).move(20);
            }else{
                changeHeading(i);
                changeInBounds(i);
            }
        }else{
            reinitialize(i);
        }
        notifyObservers();
    }

    /**
     * Changes the heading based on stick angle of Movable object
     * @param i Integer for index
     */
    private void changeHeading(int i){

        int stickAngle =
                ((Helicopter) gameObjects.getElement(i))
                        .getStickAngle();
        int heading =
                ((Helicopter) gameObjects.getElement(i))
                        .getHeading();
        if(heading<=0){
            if(stickAngle<0 && stickAngle>=-40){
                ((Helicopter) gameObjects.getElement(i))
                        .setHeading(360,stickAngle);
            }else if(stickAngle>=0 && stickAngle<=40){
                ((Helicopter) gameObjects.getElement(i))
                        .setHeading(heading,stickAngle);
            }

        }else if(heading>0 && heading+stickAngle<360){
            if(heading+stickAngle<360){
                ((Helicopter) gameObjects.getElement(i))
                        .setHeading(stickAngle,heading);
            }
        }else{
            ((Helicopter) gameObjects.getElement(i))
                    .setHeading(0,stickAngle);
        }
    }


    /**
     * Change current location if GameObject goes out of bounds
     * based on current heading
     * @param i
     */
    private void changeInBounds(int i){
        Helicopter temp= (Helicopter) gameObjects.getElement(i);
        Point2D tempLocation=null;
        double distance=(double)temp.getSpeed()*(20.0)/1000.0;
        double theta=Math.toRadians(90-temp.getHeading());
        double deltaX=Math.cos(theta)*distance;
        double deltaY=Math.sin(theta)*distance;
        tempLocation=
                new Point2D.Double(temp.getLocation().getX()+deltaX,
                        temp.getLocation().getY()+deltaY);

        if (inBounds(tempLocation, temp)) {
            temp.setLocation(tempLocation);
        }
    }

    /**
     * @param tempLocation Point2D for location of GameObject
     * @param tempHeli Helicopter object
     * @return true or false based of whether its in bounds or not
     */
    private boolean inBounds(Point2D tempLocation,Helicopter tempHeli){
        return (tempLocation.getX()>=0 && tempLocation.getX()+
                tempHeli.getWidth() <maxWidth
                && tempLocation.getY()>0 && tempLocation.getY()+
                tempHeli.getHeight()
                <maxHeight);
    }

    /**
     * Displays the map which shows location and other stats
     * of all of the gameObjects
     */
    public void displayMap() {
        System.out.print("\nDisplaying Map:");
        for(int i = 0; i< gameObjects.size(); i++){
            System.out.println(gameObjects.getElement(i));
        }
    }

    /**
     *
     * @param min Minimum value for random range
     * @param max Maximum value for random range
     * @return random value between min and max
     */
    private int randomNumber(int min,int max){
        Random random=new Random();
        return random.nextInt(max-min+1)+min;
    }

    public int getLives(){
        return lives;
    }

    /**
     * Updates the lastSkyScraperReached for the Helicopter object
     * until it reaches the last SkyScraper
     * @param skyScraper current SkyScraper that helicopter has
     *                   collided with
     */
    public void checkpoint(int skyScraper,GameObject curObj) {
        System.out.println("\nHelicopter has reached checkpoint");
        if (hasReachedLastCheckpoint(skyScraper, curObj)) {
            if(soundOn()){
                try {
                    heliCheckpoint.play();
                }catch (Exception e){
                   System.out.println("System out initialized");
                }
            }
            ((Helicopter) curObj).setLastSkyScraperReached(skyScraper++);
            notifyObservers();
        }
    }

    /**
     * Checks if Helicopter collides with SkyScraper in Sequence
     * @param obj GameObject
     * @param curObj GameObject
     */
    public void checkpoint(GameObject obj,GameObject curObj) {
        System.out.println("\nHelicopter has reached checkpoint");
        if (hasReachedLastCheckpoint(((SkyScraper)obj).getSequenceNumber(),
                curObj)) {
            if(soundOn()){
                try {
                    heliCheckpoint.play();
                }catch (Exception e){
                   System.out.println("Sound not initialized");
                }
            }
            ((Helicopter) curObj).setLastSkyScraperReached(((SkyScraper)obj)
                    .getSequenceNumber());

            if(!(curObj instanceof NonPlayerHelicopter)){
                ((SkyScraper) obj).setReached(true);
            }
            notifyObservers();
        }
    }


    /**
     * @param skyScraper Int value for Skyscraper Number
     * @param curObj Current gameObject
     * @return True or False, whether current SkyScraper Reached is
     *          exactly one greater than lastSkyScraperReached
     */
    private boolean hasReachedLastCheckpoint(int skyScraper,
                                             GameObject curObj){
        return skyScraper==
                ((Helicopter) curObj).getLastSkyScraperReached()+1;
    }

    /**
     * changes the bird heading by randomly incrementing &
     * decrementing bird heading by 5
     * @param i Integer value for current element in the gameVector
     *          Vector
     */
    private void changeBirdHeading(int i) {
        if(checkBoundary(i)){
            Random random = new Random();
            if (random.nextInt(2) == 1) {
                ((Bird) gameObjects.getElement(i)).setHeading(5);
            } else {
                ((Bird) gameObjects.getElement(i)).setHeading(-5);
            }
        }else{
            if (((Movable) gameObjects.getElement(i)).getHeading() > 270) {
                ((Movable) gameObjects.getElement(i)).setHeading(-180);
            } else {
                ((Movable) gameObjects.getElement(i)).setHeading(180);
            }
        }
    }

    /**
     * @param i Integer i for index of Vector
     * @return  True or false, whether bird object is whether or not
     *          bird object is within gameWorld boundaries
     */
    private boolean checkBoundary(int i){
        int size=((Movable)gameObjects.getElement(i)).getSize();
        return ((Movable) gameObjects.getElement(i)).getLocation().getX()>0.0 &&
                ((Movable) gameObjects.getElement(i)).getLocation().getX()
                        +size<maxWidth
                &&((Movable) gameObjects.getElement(i)).getLocation().getY()
                >0.0 &&
                ((Movable) gameObjects.getElement(i)).getLocation().getY()
                        +size<maxHeight;
    }

    /**
     * @return Current DamageLevel of Helicopter
     */
    public int getHelicopterDamage(){
        return PlayerHelicopter.getInstance().getDamageLevel();
    }

    /**
     * @return Current fuelLevel of Helicopter
     */
    public int getHelicopterFuelLevel(){
        return PlayerHelicopter.getInstance().getFuelLevel();
    }

    /**
     * @return current heading of Helicopter
     */
    public int getHelicopterHeading(){
        return PlayerHelicopter.getInstance().getHeading();
    }

    /**
     * @return Last skyScraper reached by the Helicopter
     */
    public int getLastCheckpoint(){
        return PlayerHelicopter.getInstance().getLastSkyScraperReached();
    }

    /**
     * @return GameObjecCollection object
     */
    public GameObjectCollection getObjectCollection(){
        return gameObjects;
    }

    /**
     * Switches the current NPH strategy to a different
     * NPH strategy
     */
    public void switchStrategy(){
        for(int i=0;i<gameObjects.size();i++){
            if(gameObjects.getElement(i) instanceof NonPlayerHelicopter){
                if(((NonPlayerHelicopter) gameObjects.getElement(i))
                        .getStrategy() instanceof AttackStrategy){
                    ((NonPlayerHelicopter) gameObjects.getElement(i)).
                            setStrategy(new DefenseStrategy(
                                    (NonPlayerHelicopter) gameObjects.
                                            getElement(i),this));
                    ((NonPlayerHelicopter) gameObjects.getElement(i))
                            .setLastSkyScraperReached(2);

                }else if(((NonPlayerHelicopter) gameObjects.getElement(i)).
                        getStrategy() instanceof DefenseStrategy){
                    ((NonPlayerHelicopter) gameObjects.getElement(i))
                            .setStrategy(new RaceStrategy((NonPlayerHelicopter)
                                    gameObjects.getElement(i),this));
                    ((NonPlayerHelicopter) gameObjects.getElement(i))
                            .setLastSkyScraperReached(0);
                }else{
                    ((NonPlayerHelicopter) gameObjects.getElement(i))
                            .setStrategy(new AttackStrategy(
                                    (NonPlayerHelicopter) gameObjects
                                            .getElement(i),PlayerHelicopter
                                    .getInstance()));
                }
            }
        }
    }

    /**
     * Detect collision between gameObjects
     * @param i Integer for index for Vector
     */
    private void collision(int i) {
        for (int j = 0; j < gameObjects.size(); j++) {
            GameObject o1 = (GameObject) gameObjects.getElement(i);
            GameObject o2 = (GameObject) gameObjects.getElement(j);
            if (o1 != o2) {
                if (o1.collidesWith(o2)) {
                    if (o1 instanceof NonPlayerHelicopter &&
                            !nphCollision.contains(o2)) {
                        if(!(o2 instanceof SkyScraper)){
                            nphCollision.add(o2);
                        }
                        o1.handleCollision(o2);
                    }else if (o1 instanceof Bird &&
                            !birdCollision.contains(o2)){
                        birdCollision.add(o2);
                        o1.handleCollision(o2);
                    }else if (o1 instanceof SkyScraper &&
                            skyScraperCollision.contains(o2)) {
                        skyScraperCollision.add(o2);
                        o1.handleCollision(o2);
                    }else if (o1 instanceof RefuelingBlimp &&
                            !blimpCollision.contains(o2)) {
                        blimpCollision.add(o2);
                        o1.handleCollision(o2);
                    }else if (o1 instanceof Helicopter &&
                            !(o1 instanceof NonPlayerHelicopter)
                            && !helicopterCollision.contains(o2)) {
                        if(!(o2 instanceof SkyScraper)){
                            helicopterCollision.add(o2);
                        }
                        o1.handleCollision(o2);
                    }
                }else{
                    skyScraperCollision.remove(o2);
                    birdCollision.remove(o2);
                    nphCollision.remove(o2);
                }
            }
        }
    }

    /**
     * Stop Sound
     */
    public void stopSound(){
        backgroundSound.pause();
    }


    @Override
    public void addObserver(IObserver obs) {
        gameObserver.add(obs);
    }

    @Override
    public void notifyObservers() {
        Object theObservers[];
        theObservers=gameObserver.toArray();
        if(!gameObserver.isEmpty()){
            for(int i=0;i<gameObserver.size();i++){
                ((IObserver) theObservers[i]).update(this);
            }
        }
    }

    public boolean soundOn(){
        return soundPlaying;
    }

    public void toggleSound() {
        soundPlaying=!soundPlaying;
    }
}