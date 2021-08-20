package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.UITimer;

import java.util.Vector;

/**
 * Game class extends Form and contains state variables
 * in order to add Commands, buttons, and layout to the
 * form and construct GlassCockpit,Mapview for the game.
 */
public class Game extends Form implements Runnable {
    private GameWorld gw;
    private GlassCockpit glassCockpit;
    private MapView mapView;
    private boolean isPaused=false;
    private UITimer timer;
    private Vector<Commands> gameCommands;
    private Button leftArrow;
    private Button upArrow;
    private Button rightArrow;
    private Button downArrow;

    /**
     * Construct a GameWorld object,add Commands and Key
     * Binding for input
     */
    public Game(){
        gw=new GameWorld();
        gameCommands= new Vector<>();
        mapView=new MapView(gw);
        Image leftArrowIcon=null;
        Image rightArrowIcon=null;
        Image upArrowIcon=null;
        Image downArrowIcon=null;

        try{
            leftArrowIcon= Image.createImage("/Left_Arrow.png");
            rightArrowIcon=Image.createImage("/Right_Arrow.png");
            upArrowIcon=Image.createImage("/Up_Arrow.png");
            downArrowIcon=Image.createImage("/Down_Arrow.png");
        }catch(Exception e){
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());

        leftArrow=new Button(leftArrowIcon);
        rightArrow=new Button(rightArrowIcon);
        upArrow=new Button(upArrowIcon);
        downArrow=new Button(downArrowIcon);

        getToolbar().setTitle("SKYMAIL 3000");
        CheckBox soundCheck=soundCheckBox();
        getToolbar().addComponentToLeftSideMenu(soundCheck);
        soundCheckBox();

        Container buttonBar=new Container(new FlowLayout(Component.CENTER));

        add(BorderLayout.SOUTH,buttonBar);

        glassCockpit=new GlassCockpit();
        add(BorderLayout.NORTH,glassCockpit);
        add(BorderLayout.CENTER,mapView);
        buttonBar.addAll(leftArrow,upArrow,downArrow,rightArrow);

        setLayout(new BorderLayout(BASELINE));

        AccelerateCommand accelerateCommand=new AccelerateCommand();
        addCommand('a',accelerateCommand);
        gameCommands.add(accelerateCommand);

        ExitCommand exitCommand=new ExitCommand();
        addKeyListener('x',exitCommand);
        addCommandToLeftSide(exitCommand);
        gameCommands.add(exitCommand);

        SwitchStrategyCommand switchStrategyCommand=new SwitchStrategyCommand();
        addCommandToLeftSide(switchStrategyCommand);

        BrakeCommand brakeCommand=new BrakeCommand();
        addCommand('b',brakeCommand);
        gameCommands.add(brakeCommand);

        LeftTurnCommand leftTurnCommand=new LeftTurnCommand();
        addCommand('l',leftTurnCommand);
        gameCommands.add(leftTurnCommand);

        RightTurnCommand rightTurnCommand=new RightTurnCommand();
        addCommand('r',rightTurnCommand);
        gameCommands.add(rightTurnCommand);

        HelpCommand helpCommand=new HelpCommand();
        helpCommand.setTarget(gw,this);
        getToolbar().addCommandToRightBar(helpCommand);

        AboutCommand aboutCommand=new AboutCommand();
        addCommandToLeftSide(aboutCommand);

        PlayPauseCommand playPauseCommand=new PlayPauseCommand();

        SoundCommand soundCommand=new SoundCommand();
        soundCommand.setTarget(gw,this);
        soundCheck.setCommand(soundCommand);

        leftArrow.setCommand(leftTurnCommand);
        rightArrow.setCommand(rightTurnCommand);
        downArrow.setCommand(brakeCommand);
        upArrow.setCommand(accelerateCommand);

        scheduleTimer();

        gw.addObserver(glassCockpit);
        gw.addObserver(mapView);

        this.show();
        setUpGameWorld();
    }

    /**
     * @return true or false, if Game is currently
     * paused or not
     */
    public boolean isPaused(){
        return isPaused;
    }

    /**
     * Pause the Game by disabling timer,buttons
     * and commands
     */
    public void pauseGame(){
        isPaused=true;
        for(int i=0;i<gameCommands.size();i++){
            if(!(gameCommands.elementAt(i) instanceof ExitCommand) ||
                !(gameCommands.elementAt(i) instanceof PlayPauseCommand)){
                gameCommands.elementAt(i).setEnabled(false);
            }
        }
        timer.cancel();
        toggleButton(isPaused);
        glassCockpit.getDigitComponent().setPaused(isPaused);
        gw.stopSound();
    }

    private CheckBox soundCheckBox(){
        CheckBox soundCheck=new CheckBox("Sound");
        soundCheck.setSelected(true);
        Style checkboxStyle=new Style();
        checkboxStyle.setFgColor(ColorUtil.rgb(255,0,0));
        checkboxStyle.setBgColor(ColorUtil.LTGRAY);
        checkboxStyle.setFont(Font.createSystemFont(CN.FACE_SYSTEM,
                CN.STYLE_BOLD,Font.SIZE_LARGE));
        soundCheck.setUnselectedStyle(checkboxStyle);
        return soundCheck;
    }

    /**
     * Resume the Game by enabling commands, timer
     * and buttons
     */
    public void resumeGame(){
        isPaused=false;
        for(int i=0;i<gameCommands.size();i++){
            if(!(gameCommands.elementAt(i) instanceof ExitCommand) ||
                    !(gameCommands.elementAt(i) instanceof PlayPauseCommand)){
                gameCommands.elementAt(i).setEnabled(true);
            }
        }
        scheduleTimer();
        toggleButton(!isPaused);
        glassCockpit.getDigitComponent().setPaused(isPaused);
        gw.playSound();
    }


    /**
     * Add KeyListener to commands
     * @param character Char for the key-binding input
     * @param cmd Commands to add key-binding to
     */
    private void addCommand(char character,Commands cmd){
        cmd.setTarget(gw);
        addKeyListener(character,cmd);
    }

    private void addCommandToLeftSide(Commands cmd){
        cmd.setTarget(gw,this);
        getToolbar().addCommandToLeftSideMenu(cmd);
    }

    private void setUpGameWorld(){
        gw.setMaxWidth(mapView.getWidth());
        gw.setMaxHeight(mapView.getHeight());
        gw.init();
        gw.createSound();
        gw.playSound();
    }

    private void scheduleTimer(){
        timer=new UITimer(this);
        timer.schedule(20,true,this);
    }

    private void toggleButton(boolean isActive){
        leftArrow.setEnabled(isActive);
        rightArrow.setEnabled(isActive);
        upArrow.setEnabled(isActive);
        downArrow.setEnabled(isActive);
    }

    private void createButton(){

    }

    @Override
    /**
     * Implements the runnable, in order to make
     * gameWorld tick
     */
    public void run() {
        gw.tick();
    }
}
