package org.csc133.a3;

import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

/**
 * Abstract Commands class that extends Commands
 */
public abstract class Commands extends Command{
    public Commands(String command){
        super(command);
    }

    /**
     * Abstract method for actionPerformed
     * @param event ActionEvent object
     */
    @Override
    public abstract void actionPerformed(ActionEvent event);

    /**
     * Abstract method for setTarget
     * @param target Object for target
     */
    public abstract void setTarget(Object target);
    public void setTarget(Object target,Object target2){

    }
}

/**
 *AccelerateCommand class extends Commands and
 * contains info on AccelerateCommand object
 */
 class AccelerateCommand extends Commands {
    private GameWorld gw;

    /**
     * Constructor for AccelerateCommand
     */
    public AccelerateCommand(){
        super("Accelerate");
    }

    /**
     * Call gameWorld method to accelerate
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("\nSpeed accelerated");
        gw.accelerate();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object parameter
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 * ExitCommand class extends Commands and
 * contains info on ExitCommand object
 */
class ExitCommand extends Commands {
    private GameWorld gw;
    private Game game;

    /**
     * Constructor for ExitCommand
     */
    public ExitCommand(){
        super("Exit");
    }

    /**
     * Call gameWorld method to exit
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        game.pauseGame();
        if(Dialog.show("Click Yes Or No", "Select one", "Yes"
                , "No")) {
           gw.exit();
        }
        game.resumeGame();
    }

    public void setTarget(Object target){
    }

    /**
     * Sets the targets for actionPerformed
     * @param target Object for target1
     * @param target2 Object for target2
     */
    public void setTarget(Object target,Object target2){
        if(gw==null && game==null){
            gw=(GameWorld) target;
            game=(Game) target2;
        }
    }
}

/**
 * BrakeCommand class extends Commands and
 * contains info on BrakeCommand object
 */
class BrakeCommand extends Commands {
    private GameWorld gw;

    /**
     * Constructor for BrakeCommand
     */
    public BrakeCommand(){
        super("Brake");
    }

    /**
     * Call the gameWorld method for brake
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        gw.brake();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 *
 * LeftTurnCommand class extends Commands and
 * contains info on LeftTurnCommand object
 */
class LeftTurnCommand extends Commands {
    private GameWorld gw;

    /**
     * Constructor for LeftTurnCommand
     */
    public LeftTurnCommand(){
        super("LeftTurn");
    }

    /**
     * Calls the gameWorld method for turnLeft
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        gw.turnLeft();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 * RightTurnCommand class extends Commands and
 * contains info on RightTurnCommand object
 */
class RightTurnCommand extends Commands {
    private GameWorld gw;

    public RightTurnCommand(){
        super("RightTurn");
    }

    /**
     * Call the method in gameWorld for turnRight
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        gw.turnRight();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 * NphCollisionCommand class extends Commands and
 * contains info on NphCollisionCommand object
 */
class NphCollisionCommand extends Commands {
    private GameWorld gw;

    public NphCollisionCommand(){
        super("NphCollision");
    }

    /**
     * Call the method in gameWorld for helicopterCollide
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        gw.helicopterCollide();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 * SkyScraperCollisionCommand class extends Commands and
 * contains info on SkyScraperCollisionCommand object
 */
class SkyscraperCollisionCommand extends Commands {
    private GameWorld gw;
    private Game game;

    /**
     * Constructor for SkyScraperCollisionCommand
     */
    public SkyscraperCollisionCommand() {
        super("SkyscraperCollision");
    }

    /**
     * Calls the method gameWorld for skyScraperCollision
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        TextField checkpoint = new TextField();
        Command[] cmds = new Command[]{new Command("Ok"),
                new Command("Cancel")};
        game.pauseGame();
        Command c = Dialog.show("Enter SkyScraper #", checkpoint, cmds);
        if (!checkpoint.getText().isEmpty()) {
            try {
               // gw.checkpoint(Integer.parseInt(checkpoint.getText()));
            } catch (NumberFormatException e) {
                System.out.println("ERROR");
            }
        }
        game.resumeGame();
    }


    public void setTarget(Object target){

    }

    /**
     * Sets the targets for actionPerformed
     * @param target Object for target1
     * @param target2 Object for target2
     */
    public void setTarget(Object target, Object target2) {
        if (gw == null && game == null) {
            gw = (GameWorld) target;
            game = (Game) target2;
        }
    }
}

/**
 * BlimpCollisionCommand class extends Commands and
 * contains info on BlimpCollisionCommand object
 */
class BlimpCollisionCommand extends Commands {
    private GameWorld gw;

    /**
     * Constructor for BlimpCollisionCommand
     */
    public BlimpCollisionCommand(){
        super("BlimpCollision");
    }

    /**
     * Call the method in gameWorld for refuel
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
       // gw.refuel();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 * BirdCollisionCommand class extends Commands and
 * contains info on BirdCollisionCommand object
 */
class BirdCollisionCommand extends Commands {
    private GameWorld gw;

    /**
     * Constructor for BirCollisionCommand
     */
    public BirdCollisionCommand(){
        super("BirdCollision");
    }

    /**
     * Call method in gameWorld for birdCollision
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        gw.birdCollision();
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target){
        if(gw==null){
            gw=(GameWorld) target;
        }
    }
}

/**
 * PlayPauseCommand class extends Commands and
 * contains info on PlayPauseCommand object
 */
class PlayPauseCommand extends Commands{
    private Game game;

    public PlayPauseCommand() {
        super("PlayPause");
    }

    /**
     * Call the method pauseGame in Game
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(!game.isPaused()){
            game.pauseGame();
        }else{
            game.resumeGame();
        }
    }

    /**
     * Sets the target for actionPerformed
     * @param target Object for target
     */
    public void setTarget(Object target) {
        if(game==null){
            game=(Game)target;
        }
    }
}

/**
 * HelpCommand class extends Commands and
 * contains info on HelpCommand object
 */
class HelpCommand extends Commands {
    private GameWorld gw;
    private Game game;

    /**
     * Constructor for HelpCommand
     */
    public HelpCommand() {
        super("Help");
    }

    /**
     * Call method for help in Game
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Dialog dialog=new Dialog();
        TextField checkpoint = new TextField();
        game.pauseGame();
        SpanLabel myLabels=new SpanLabel("A-Accelerate \nB-Brake \nL-Left "
                + "Turn \nR-Right Turn \nN-Collision with NPH \nS-Collision "
                + "with Skyscraper \nE-Collision with refueling blimp "
                + "\nG-Collision with Bird \nX-Exit");
        dialog.setLayout(new BorderLayout());
        dialog.add(BorderLayout.CENTER,myLabels);
        dialog.getUnselectedStyle()
                .setPadding(50,50,50,50);
        dialog.show("Basic Controls",dialog,new Command("OK"));
        game.resumeGame();
    }
    public void setTarget(Object target){

    }

    /**
     * Sets the targets for actionPerformed
     * @param target Object for target1
     * @param target2 Object for target2
     */
    public void setTarget(Object target, Object target2) {
        if (gw == null && game == null) {
            gw = (GameWorld) target;
            game = (Game) target2;
        }
    }
}

/**
 * AboutCommand class extends Commands and
 * contains info on AboutCommand object
 */
class AboutCommand extends Commands {
    private GameWorld gw;
    private Game game;

    /**
     * Constructor for AboutCommand
     */
    public AboutCommand() {
        super("About");
    }

    /**
     * Call the method for about in Game
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Dialog dialog=new Dialog();
        game.pauseGame();
        SpanLabel myLabels=new SpanLabel("Name: Sharnpreet Singh \nCourse: "
                + "CSC 133 \nVersion: A2Prj");
        dialog.setLayout(new BorderLayout());
        dialog.add(BorderLayout.CENTER,myLabels);
        dialog.getUnselectedStyle()
                .setPadding(50,50,50,50);
        dialog.show("About Me",dialog,new Command("OK"));
        game.resumeGame();
    }

    public void setTarget(Object target){

    }

    /**
     * Sets the targets for actionPerformed
     * @param target Object for target1
     * @param target2 Object for target2
     */
    public void setTarget(Object target, Object target2) {
        if (gw == null && game == null) {
            gw = (GameWorld) target;
            game = (Game) target2;
        }
    }
}

/**
 * SwitchStrategyCommand class extends Commands and
 * contains info on SwitchStrategyCommand object
 */
class SwitchStrategyCommand extends Commands {
    private GameWorld gw;
    private Game game;

    public SwitchStrategyCommand(){
        super("Switch Strategy");
    }

    /**
     * Call the method for switchStrategy in gameWorld
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Dialog dialog=new Dialog();
        game.pauseGame();
        SpanLabel myLabels=
                new SpanLabel("Strategies: \n1. Attack Strategy " +
                        "\n2. Defense Strategy \n3. Race Strategy");
        dialog.setLayout(new BorderLayout());
        dialog.add(BorderLayout.CENTER,myLabels);
        dialog.getUnselectedStyle()
                .setPadding(50,50,50,50);
        dialog.show("Strategies",dialog,new Command("CHANGE"));
        gw.switchStrategy();
        game.resumeGame();
        gw.switchStrategy();
    }


    @Override
    public void setTarget(Object target) {
    }

    /**
     * Sets the targets for actionPerformed
     * @param target Object for target1
     * @param target2 Object for target2
     */
    public void setTarget(Object target, Object target2) {
        if (gw == null && game == null) {
            gw = (GameWorld) target;
            game = (Game) target2;
        }
    }
}

class SoundCommand extends Commands{
    private GameWorld gw;
    private Game game;

    public SoundCommand(){
        super("Sound");
    }

    /**
     * Call the method for switchStrategy in gameWorld
     * @param event ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent event) {
      gw.toggleSound();
      if(!game.isPaused() && gw.soundOn()){
          gw.playSound();
      }else{
          gw.stopSound();
      }

    }


    @Override
    public void setTarget(Object target) {
    }

    /**
     * Sets the targets for actionPerformed
     * @param target Object for target1
     * @param target2 Object for target2
     */
    public void setTarget(Object target, Object target2) {
        if (gw == null && game == null) {
            gw = (GameWorld) target;
            game = (Game) target2;
        }
    }

}



