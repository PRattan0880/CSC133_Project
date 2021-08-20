package org.csc133.a3;

import com.codename1.ui.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.Component;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

import java.io.IOException;
import java.util.Vector;

/**
 * The GlassCockpit class extends Container containing
 * info on all of the objects within the GlassCockpit
 * including GameClockComponent, FuelLevelComponent,
 * DamageLevelComponent, SingleDigitComponent & HeadingComponent,
 * and using IObserver to update current fields based on data
 * from gameWorld
 */
public class GlassCockpit extends Container implements IObserver,IGameClock {

   private GameClockComponent gameTimeComp =new GameClockComponent();
   private FuelLevelComponent fuelLevelComp=new FuelLevelComponent();
   private DamageLevelComponent damageLevelComp=new DamageLevelComponent();
   private SingleDigitComponent livesComp=new SingleDigitComponent();
   private SingleDigitComponent lastComp=new SingleDigitComponent();
   private HeadingComponent headingComp=new HeadingComponent();

    public GlassCockpit(){
        Label time=new Label("GAME TIME");
        Label fuel=new Label("FUEL");
        Label damage=new Label("DAMAGE");
        Label lives=new Label("LIVES");
        Label last=new Label("LAST");
        Label heading=new Label("HEADING");

         Container gameTimeContainer =new Container(new BoxLayout(BoxLayout.
                Y_AXIS));
         Container fuelLevelContainer =new Container(new BoxLayout(BoxLayout.
                Y_AXIS));
         Container damageLevelContainer =new Container(new BoxLayout(
                BoxLayout.Y_AXIS));
         Container livesContainer =new Container(new BoxLayout(BoxLayout.
                Y_AXIS));
         Container lastContainer = new Container(
                new BoxLayout(BoxLayout.Y_AXIS));

         Container headingContainer = new Container(
                new BoxLayout(BoxLayout.Y_AXIS));

        this.getAllStyles().setPadding(0,10,20,0);
        gameTimeComp.setLedColor(ColorUtil.CYAN,ColorUtil.GREEN);
        gameTimeComp.startElapsedTime();
        fuelLevelComp.setLedColor(ColorUtil.rgb(255,0,0));
        fuelLevelContainer.addAll(fuel,fuelLevelComp);
        damageLevelContainer.addAll(damage,damageLevelComp);
        livesContainer.addAll(lives,livesComp);
        lastContainer.addAll(last,lastComp);
        headingContainer.addAll(heading,headingComp);


        time.getUnselectedStyle().setBgTransparency(255);
        time.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
        gameTimeContainer.add(time);
        gameTimeContainer.add(gameTimeComp);

        addAll(gameTimeContainer,fuelLevelContainer,damageLevelContainer,
                livesContainer,lastContainer,headingContainer);

        getUnselectedStyle().setBorder(Border.createLineBorder(2,
                ColorUtil.GREEN));
   }


    @Override
    /**
     * Update the GlassCockpit data using Observable
     * Pattern in order to get data from GameWorld
     */
    public void update(IObservable o) {
        float damage=((GameWorld) o).getHelicopterDamage();
        float maxDamage=((GameWorld) o).getMaximumDamage();

       damageLevelComp.setDamageLevel((int) (damage/maxDamage *100));
       livesComp.setSingleDigit(((GameWorld) o).getLives());
       fuelLevelComp.setFuelLevel(((GameWorld) o).getHelicopterFuelLevel());
       headingComp.setHeading(((GameWorld) o).getHelicopterHeading());
       lastComp.setSingleDigit(((GameWorld) o).getLastCheckpoint());
        ((GameWorld) o).setElapsedTime(getElapsedTime());

    }


    /**
     * @return current instance of GameClockComponent
     */
    public GameClockComponent getDigitComponent(){
        return gameTimeComp;
    }

    /**
     * Get Current ElapsedTime for the game
     */
    @Override
    public String getElapsedTime() {
        return gameTimeComp.getMinutes()+ ":"+ gameTimeComp.getSeconds() +
                ":" + gameTimeComp.getTenthOfSeconds();
    }
}


/**
 * DigitImageComponent is an abstract class that extends
 * Component and contains fields to be used by sub-classes
 */
abstract class DigitImageComponent extends Component{
    private Image[] digitImages=new Image[10];
    private int ledColor;
    private boolean animate=false;


    /**
     * Constructor for DigitImageComponent
     */
    public DigitImageComponent(){
        try{
            digitImages[0]=Image.createImage("/LED_Digit_0.png");
            digitImages[1]=Image.createImage("/LED_Digit_1.png");
            digitImages[2]=Image.createImage("/LED_Digit_2.png");
            digitImages[3]=Image.createImage("/LED_Digit_3.png");
            digitImages[4]=Image.createImage("/LED_Digit_4.png");
            digitImages[5]=Image.createImage("/LED_Digit_5.png");
            digitImages[6]=Image.createImage("/LED_Digit_6.png");
            digitImages[7]=Image.createImage("/LED_Digit_7.png");
            digitImages[8]=Image.createImage("/LED_Digit_8.png");
            digitImages[9]=Image.createImage("/LED_Digit_9.png");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return true or false, based on if animate is true
     * or not
     */
    public boolean getAnimate(){
        return animate;
    }

    /**
     * Sets animate to value of parameter
     * @param animate Boolean value
     */
    public void setAnimate(boolean animate){
        this.animate=animate;
    }

    /**
     * Sets the ledColor to value of parameter
     * @param ledColor Int value for color
     */
    public void setLedColor(int ledColor){
        this.ledColor =ledColor;
    }

    /**
     * @param i Index for current image from array
     * @return Image from array ay index i
     */
    public Image getDigitImage(int i){
        return digitImages[i];
    }

    /**
     * @return the ledColor
     */
    public int getLedColor() {
        return ledColor;
    }

    /**
     * Register Animation on current Component
     */
    public void start(){
        getComponentForm().registerAnimated(this);
    }

    /**
     *Deregister Animation on current Component
     */
    public void stop(){
        getComponentForm().deregisterAnimated(this);
    }

    /**
     * Calls starts after the component exists using callback
     */
    public void laidOut(){
        this.start();
    }

    /**
     * @param digit Image of digit
     * @param numDigitsShowing int value
     * @return component width of current component
     */
    public int getComponentWidth(Image digit, int numDigitsShowing){
        return digit.getWidth()*
                numDigitsShowing;
    }

    /**
     * @param digit Image of digit
     * @return width of digit Image
     */
    public int getDigitWidth(Image digit){
        return digit.getWidth();
    }

    /**
     * @param digit Image of digit
     * @return height of digit Image
     */
    public int getDigitHeight(Image digit){
        return digit.getHeight();
    }

    /**
     * @param digit Image of digit
     * @param numDigitsShowing int value
     * @return scaleFactor for digit
     */
    public float getScaleFactor(Image digit,int numDigitsShowing){
        return Math.min(getInnerHeight()/(float)getDigitHeight(digit),
                getInnerWidth()/(float) getComponentWidth(digit,
                        numDigitsShowing));
    }

    /**
     * @param digit Image of digit
     * @param numDigitsShowing Int value
     * @return DisplayDigitHeight of digit
     */
    public int getDisplayDigitHeight(Image digit,int numDigitsShowing){
        return (int)(getScaleFactor(digit,numDigitsShowing)*
                getDigitHeight(digit));
    }

    /**
     * @param digit Image of digit
     * @param numDigitsShowing Int value
     * @return DisplayDigitWidth of digit
     */
    public int getDisplayDigitWidth(Image digit,int numDigitsShowing){
        return (int)(getScaleFactor(digit,numDigitsShowing) *
                getDigitWidth(digit));
    }

    /**
     * @param digit Image of digit
     * @param numDigitsShowing Int value
     * @return DisplayComponentWidth of digit
     */
    public int getDisplayComponentWidth(Image digit,int numDigitsShowing){
        return getDisplayDigitWidth(digit,numDigitsShowing)*numDigitsShowing;
    }
}

/**
 * GameClockComponent class that extends DigitImageComponent
 * and contains info for GameClockComponent object
 */
class GameClockComponent extends DigitImageComponent{
    private static final int numDigitsShowing=6;
    private static final int MS_COLON_IDK=2;
    private Image[] digitWithDot=new Image[10];
    private Image[] clockDigits=new Image[numDigitsShowing];
    private Image colonImage;
    private int minutes,seconds,tenthOfSecond;
    private int ledColor2;
    private long start=System.currentTimeMillis();
    private long paused=0;
    private long elapsedTime=0;
    private Vector<Integer> sumOfElapsed =new Vector<>();

    /**
     * Constructor for GameClockComponent
     */
    public GameClockComponent(){
        super();

        try{
            digitWithDot[0]=Image.createImage("/LED_Digit_0_dot.png");
            digitWithDot[1]=Image.createImage("/LED_Digit_1_dot.png");
            digitWithDot[2]=Image.createImage("/LED_Digit_2_dot.png");
            digitWithDot[3]=Image.createImage("/LED_Digit_3_dot.png");
            digitWithDot[4]=Image.createImage("/LED_Digit_4_dot.png");
            digitWithDot[5]=Image.createImage("/LED_Digit_5_dot.png");
            digitWithDot[6]=Image.createImage("/LED_Digit_6_dot.png");
            digitWithDot[7]=Image.createImage("/LED_Digit_7_dot.png");
            digitWithDot[8]=Image.createImage("/LED_Digit_8_dot.png");
            digitWithDot[9]=Image.createImage("/LED_Digit_9_dot.png");

            colonImage=Image.createImage("/LED_Colon.png");
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<numDigitsShowing;i++){
            clockDigits[i]=super.getDigitImage(0);
        }

        clockDigits[MS_COLON_IDK]=colonImage;
        setLedColor(ColorUtil.CYAN);
    }

    /**
     * Sets the time and display it using 7-segment
     * Digit images
     * @param ms Long value for ms
     */
   private void setTime(long ms){
       int milliSeconds=(int) ms;
       minutes=(milliSeconds/60000);
       seconds=(milliSeconds/1000)%60;
       tenthOfSecond=(milliSeconds/100)%10;

       clockDigits[0]=getDigitImage(minutes/10);
       clockDigits[1]=getDigitImage(minutes%10);
       clockDigits[3]=getDigitImage((seconds/10));
       clockDigits[4]=digitWithDot[(seconds%10)];
       clockDigits[5]=getDigitImage(tenthOfSecond);
   }

    /**
     * Calculates current elapsed time in real
     * time
     */
    private void setCurrentTime(){
        long paused=0;
        if(this.paused<=0){
            paused=System.currentTimeMillis();
            sumOfElapsed.add((int) (paused-start));
            start=paused;
        }

        for(int i = sumOfElapsed.size()-1; i>0; i--){
            elapsedTime+= sumOfElapsed.remove(i);
        }
       setTime(elapsedTime);
    }

    /**
     * Sets the led colors to parameter values
     * @param ledColor Int value
     * @param ledColor2 Int value
     */
    public void setLedColor(int ledColor,int ledColor2){
        super.setLedColor(ledColor);
        this.ledColor2=ledColor2;
    }


    /**
     * Starts the animation
     */
    public void startElapsedTime(){
       super.setAnimate(true);
    }

    public void stopElapsedTime(){
        super.setAnimate(false);
    }

    /**
     * @return True in order to repaint the clock
     */
    public boolean animate(){
        setCurrentTime();
        return getAnimate();
    }

    /**
     * Sets the isPaused to parameter value
     * @param isPaused Boolean
     */
    public void setPaused(boolean isPaused){
        if(isPaused){
            setAnimate(false);
            paused=System.currentTimeMillis();
        }else{
            setResume();
            super.setAnimate(true);
        }
    }

    /**
     * Resume elapsed time
     */
    private void setResume(){
        sumOfElapsed.addElement((int)(paused-start));
        start=System.currentTimeMillis();
        paused=0;
    }

    /**
     * @return Dimension of the clock
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(colonImage.getWidth()*numDigitsShowing,
                colonImage.getHeight());
    }

    /**
     * Change color of time
     * @param m Int value for minutes
     * @param g Graphics for repaint
     */
    private void changeTimeColor(int m,Graphics g){
        if(minutes>=m){
            g.setColor(ColorUtil.rgb(255,0,0));
        }else{
            g.setColor(ColorUtil.BLACK);

        }
    }

    /**
     * @return minutes
     */
    public int getMinutes(){
        return minutes;
    }

    /**
     * @return seconds
     */
    public int getSeconds(){
        return seconds;
    }

    /**
     * @return tenthOfSeconds
     */
    public int getTenthOfSeconds(){
        return tenthOfSecond;
    }

    /**
     * Overrides the paint method to draw the digit for clock
     * @param g Graphics object
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD=1;

        //Used concept of clean Code of creating smaller functions
        //in order to get rid of all of variables and rely on
        //functions to return those values

        changeTimeColor(10,g);
        g.fillRect(getX(),getY(),getWidth(),getHeight());

        g.setColor(getLedColor());

        g.fillRect(getDisplayX(0)+COLOR_PAD,getDisplayY(0)+
                COLOR_PAD, getDisplayComponentWidth(clockDigits[0],
                numDigitsShowing)-COLOR_PAD*2,
                getDisplayDigitHeight(clockDigits[0],numDigitsShowing)
                        -COLOR_PAD*2);

        g.setColor(ledColor2);
        g.fillRect(getX()+getDisplayComponentWidth(clockDigits[0],
                numDigitsShowing)+getDisplayX(0)-
                        getDisplayDigitWidth(clockDigits[0],numDigitsShowing)
                        -COLOR_PAD*2,
                getDisplayY(0)+COLOR_PAD,
                getDisplayDigitWidth(clockDigits[0],numDigitsShowing)
                        -COLOR_PAD*2,
                getDisplayDigitHeight(clockDigits[0],numDigitsShowing)
                        -COLOR_PAD);

        for(int digitIndex=0;digitIndex<numDigitsShowing;digitIndex++){
            g.drawImage(clockDigits[digitIndex],
                    getDisplayX(0)+digitIndex*
                            getDisplayDigitWidth(clockDigits[0],
                                    numDigitsShowing),
                    getDisplayY(0), getDisplayDigitWidth(clockDigits[0],
                            numDigitsShowing),
                    getDisplayDigitHeight(clockDigits[0],numDigitsShowing));
        }
    }


    /**
     * @param i index for array
     * @return X value of the digit width
     */

    private int getDisplayX(int i){
        return getX() + (getWidth()-getDisplayComponentWidth(clockDigits[i],
                numDigitsShowing))/2;
    }

    /**
     * @param i index for array
     * @return Y value of the digit height
     */

    private int getDisplayY(int i){
        return getY() + (getHeight()-getDisplayDigitHeight(clockDigits[i],
                numDigitsShowing))/2;
    }
}



/**
 * FuelLevelComponent class that extends DigitImageComponent
 * and contains info for FuelLevelComponent object
 */
class FuelLevelComponent extends DigitImageComponent{
    public static final int numDigitsShowing=4;
    private Image[] fuelDigits =new Image[numDigitsShowing];

    /**
     * Constructor for FuelLevelComponent
     */
    public FuelLevelComponent(){
        super();
        for(int i=0;i<numDigitsShowing;i++){
            fuelDigits[i]=super.getDigitImage(0);
        }
        setLedColor(ColorUtil.CYAN);
    }

    /**
     * Sets the fuelLevel and create array of
     * 7-segment digits to display
     * @param fuel Int value for fuel
     */
    public void setFuelLevel(int fuel){
        fuelDigits[3]=getDigitImage(fuel%10);
        fuelDigits[2]=getDigitImage((fuel/10)%10);
        fuelDigits[1]=getDigitImage((fuel/100)%10);
        fuelDigits[0]=getDigitImage(fuel/1000);
    }


    /**
     * @param ledColor Int value for color
     */
    public void setLedColor(int ledColor){
        super.setLedColor(ledColor);
    }


    /**
     * @return True in order to repaint the clock
     */
    public boolean animate(){
        return true;
    }

    /**
     * @return Dimension of the clock
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(getDigitImage(0).getWidth()*
                numDigitsShowing, getDigitImage(0).getHeight());
    }


    /**
     * Overrides the paint method to draw the digit for clock
     * @param g Graphics object
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD=1;

        //Used concept of clean Code of creating smaller functions
        //in order to get rid of all of variables and rely on
        //functions to return those values

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(),getY(),getWidth(),getHeight());

        g.setColor(getLedColor());

        g.fillRect(getDisplayX(0)+COLOR_PAD,getDisplayY(0)+
                COLOR_PAD, getDisplayComponentWidth(fuelDigits[0],
                numDigitsShowing)-COLOR_PAD
                *2, getDisplayDigitHeight(fuelDigits[0],numDigitsShowing)
                -COLOR_PAD*2);

        for(int digitIndex=0;digitIndex<numDigitsShowing;digitIndex++){
            g.drawImage(fuelDigits[digitIndex],
                    getDisplayX(0)+digitIndex*
                            getDisplayDigitWidth(fuelDigits[0],
                                    numDigitsShowing),
                    getDisplayY(0), getDisplayDigitWidth(fuelDigits[0],
                            numDigitsShowing),
                    getDisplayDigitHeight(fuelDigits[0],numDigitsShowing));
        }
    }

    /**
     * @param i index for array
     * @return X value of the digit width
     */
    private int getDisplayX(int i){
        return getX() + (getWidth()- getDisplayComponentWidth(fuelDigits[i],
                numDigitsShowing))/2;
    }

    /**
     * @param i index for array
     * @return Y value of the digit height
     */
    private int getDisplayY(int i){
        return getY() + (getHeight()-getDisplayDigitHeight(fuelDigits[i],
                numDigitsShowing))/2;
    }


}

/**
 * DamageLevelComponent class that extends DigitImageComponent
 * and contains info for DamageLevelComponent object
 */
class DamageLevelComponent extends DigitImageComponent{
    public static final int numDigitsShowing=2;
    private Image[] damageDigits =new Image[numDigitsShowing];

    /**
     * Constructor for DamageLevelComponent
     */
    public DamageLevelComponent(){
        super();
        for(int i=0;i<numDigitsShowing;i++){
            damageDigits[i]=super.getDigitImage(0);
        }
        setLedColor(ColorUtil.CYAN);
    }

    /**
     * Sets the damageLevel and create array of
     * 7-segment digits to display
     * @param damage Int value for damage
     */
    public void setDamageLevel(int damage){
        if(damage!=100) {
            damageDigits[1] = getDigitImage(damage % 10);
            damageDigits[0] = getDigitImage(damage / 10);
            setDamageColor(damage);
        }

    }

    /**
     * Changes the color of damage digits
     * based on current damage level
     * @param damage Int value for damage
     */
    private void setDamageColor(int damage){
        if(damage<50){
            setLedColor(ColorUtil.GREEN);
        }else if(damage>=50 && damage<85){
            setLedColor(ColorUtil.YELLOW);
        }else{
            setLedColor(ColorUtil.rgb(255,0,0));
        }
    }


    /**
     * @param ledColor Int value for color
     */
    public void setLedColor(int ledColor){
        super.setLedColor(ledColor);
    }

    /**
     * @return True in order to repaint the clock
     */
    public boolean animate(){
        return true;
    }

    /**
     * @return Dimension of the clock
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(getDigitImage(0).getWidth()*
                numDigitsShowing, getDigitImage(0).getHeight());
    }

    /**
     * Overrides the paint method to draw the digit for clock
     * @param g Graphics object
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD=1;

        //Used concept of clean Code of creating smaller functions
        //in order to get rid of all of variables and rely on
        //functions to return those values

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(),getY(),getWidth(),getHeight());

        g.setColor(getLedColor());

        g.fillRect(getDisplayX(0)+COLOR_PAD,getDisplayY(0)+
                COLOR_PAD,
                getDisplayComponentWidth(damageDigits[0],numDigitsShowing)
                        -COLOR_PAD *2,
                getDisplayDigitHeight(damageDigits[0],numDigitsShowing)
                        -COLOR_PAD*2);

        for(int digitIndex=0;digitIndex<numDigitsShowing;digitIndex++){
            g.drawImage(damageDigits[digitIndex],
                    getDisplayX(0)+digitIndex*
                            getDisplayDigitWidth(damageDigits[0],
                                    numDigitsShowing),
                    getDisplayY(0), getDisplayDigitWidth(damageDigits[0],
                            numDigitsShowing),
                    getDisplayDigitHeight(damageDigits[0],numDigitsShowing));
        }
    }

    /**
     * @param i index for array
     * @return X value of the digit width
     */
    private int getDisplayX(int i){
        return getX() + (getWidth()- getDisplayComponentWidth(damageDigits[i]
                ,numDigitsShowing))/2;
    }

    /**
     * @param i index for array
     * @return Y value of the digit height
     */
    private int getDisplayY(int i){
        return getY() + (getHeight()-getDisplayDigitHeight(damageDigits[i],
                numDigitsShowing))/2;
    }
}

/**
 * SingleDigitComponent class that extends DigitImageComponent
 * and contains info for SingleDigitComponent object
 */
class SingleDigitComponent extends DigitImageComponent{
    public static final int numDigitsShowing=1;
    private Image[] singleDigit =new Image[numDigitsShowing];

    /**
     * Constructor for SingleDigitComponent
     */
    public SingleDigitComponent(){
        super();

        for(int i=0;i<numDigitsShowing;i++){
            singleDigit[i]=super.getDigitImage(0);
        }
        setLedColor(ColorUtil.CYAN);
    }

    /**
     * Sets the digit and create array of
     * 7-segment digits to display
     * @param digit Int value for digit
     */
    public void setSingleDigit(int digit){
        singleDigit[0]=getDigitImage(digit);
    }

    /**
     * @param ledColor Int value for color
     */
    public void setLedColor(int ledColor){
        super.setLedColor(ledColor);
    }

    /**
     * @return True in order to repaint the clock
     */
    public boolean animate(){
        return true;
    }

    /**
     * @return Dimension of the clock
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(getDigitImage(0).getWidth()*
                numDigitsShowing, getDigitImage(0).getHeight());
    }

    /**
     * Overrides the paint method to draw the digit for clock
     * @param g Graphics object
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD=1;

        //Used concept of clean Code of creating smaller functions
        //in order to get rid of all of variables and rely on
        //functions to return those values

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(),getY(),getWidth(),getHeight());

        g.setColor(getLedColor());

        g.fillRect(getDisplayX(0)+COLOR_PAD,getDisplayY(0)+
                        COLOR_PAD,
                getDisplayComponentWidth(singleDigit[0],numDigitsShowing)
                        -COLOR_PAD *2,
                getDisplayDigitHeight(singleDigit[0],numDigitsShowing)
                        -COLOR_PAD*2);

        for(int digitIndex=0;digitIndex<numDigitsShowing;digitIndex++){
            g.drawImage(singleDigit[digitIndex],
                    getDisplayX(0)+digitIndex*
                            getDisplayDigitWidth(singleDigit[0],
                                    numDigitsShowing),
                    getDisplayY(0), getDisplayDigitWidth(singleDigit[0],
                            numDigitsShowing),
                    getDisplayDigitHeight(singleDigit[0],numDigitsShowing));
        }
    }

    /**
     * @param i index for array
     * @return X value of the digit width
     */
    private int getDisplayX(int i){
        return getX() + (getWidth()- getDisplayComponentWidth(singleDigit[i]
                ,numDigitsShowing))/2;
    }

    /**
     * @param i index for array
     * @return Y value of the digit height
     */
    private int getDisplayY(int i){
        return getY() + (getHeight()-getDisplayDigitHeight(singleDigit[i],
                numDigitsShowing))/2;
    }
}

/**
 * HeadingComponent class that extends DigitImageComponent
 * and contains info for HeadingComponent object
 */
class HeadingComponent extends DigitImageComponent{
    public static final int numDigitsShowing=3;
    private Image[] headingDigit =new Image[numDigitsShowing];

    /**
     * Constructor for HeadingComponent
     */
    public HeadingComponent(){
        super();

        for(int i=0;i<numDigitsShowing;i++){
            headingDigit[i]=super.getDigitImage(0);
        }
        setLedColor(ColorUtil.CYAN);
    }

    /**
     * Sets the heading and create array of
     * 7-segment digits to display
     * @param heading Int value for heading
     */
    public void setHeading(int heading){
        headingDigit[0]=getDigitImage(heading/100);
        headingDigit[1]=getDigitImage((heading/10)%10);
        headingDigit[2]=getDigitImage(heading%10);

    }


    /**
     * @param ledColor Int value for color
     */
    public void setLedColor(int ledColor){
        super.setLedColor(ledColor);
    }

    /**
     * @return True in order to repaint the heading
     */
    public boolean animate(){
        return true;
    }

    /**
     * @return Dimension of the clock
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(getDigitImage(0).getWidth()*
                numDigitsShowing, getDigitImage(0).getHeight());
    }

    /**
     * Overrides the paint method to draw the digit for clock
     * @param g Graphics object
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD=1;

        //Used concept of clean Code of creating smaller functions
        //in order to get rid of all of variables and rely on
        //functions to return those values

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(),getY(),getWidth(),getHeight());

        g.setColor(getLedColor());

        g.fillRect(getDisplayX(0)+COLOR_PAD,getDisplayY(0)+
                        COLOR_PAD,
                getDisplayComponentWidth(headingDigit[0],numDigitsShowing)
                        -COLOR_PAD *2,
                getDisplayDigitHeight(headingDigit[0],numDigitsShowing)
                        -COLOR_PAD*2);

        for(int digitIndex=0;digitIndex<numDigitsShowing;digitIndex++){
            g.drawImage(headingDigit[digitIndex],
                    getDisplayX(0)+digitIndex*
                            getDisplayDigitWidth(headingDigit[0],
                                    numDigitsShowing),
                    getDisplayY(0), getDisplayDigitWidth(headingDigit[0],
                            numDigitsShowing),
                    getDisplayDigitHeight(headingDigit[0],numDigitsShowing));
        }
    }

    /**
     * @param i index for array
     * @return X value of the digit width
     */
    private int getDisplayX(int i){
        return getX() + (getWidth()- getDisplayComponentWidth(headingDigit[i]
                ,numDigitsShowing))/2;
    }

    /**
     * @param i index for array
     * @return Y value of the digit height
     */
    private int getDisplayY(int i){
        return getY() + (getHeight()-getDisplayDigitHeight(headingDigit[i],
                numDigitsShowing))/2;
    }

}

/**
 * IGameClock interface used to
 * expose method to GlassCockpit
 */
interface IGameClock{
    public String getElapsedTime();
}


