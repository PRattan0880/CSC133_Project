package org.csc133.a3;

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import java.awt.*;

/**
 * MapView Class extends Container and Implements IObserver
 * in order to display the map method in GameWorld that creates
 * the gameObjects on component
 */
public class MapView extends Container implements IObserver{
    private GameWorld gw;

    /**
     * Constructor for MapView
     * @param gameWorld gameWorld object
     */
    public MapView(GameWorld gameWorld){
       gw=gameWorld;
    }

    /**
     * Update method is called when Observer notifies change
     * in gameWorld to repaint the mapView
     * @param o
     */
    public void update(IObservable o) {
        ((GameWorld) o).displayMap();
        repaint();
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
     * Calls gameWorld draw method to draw all gameObjects
     * to the component
     * @param g Graphics object
     */
    public void paint(Graphics g){
        super.paint(g);
        gw.draw(g,new Point(getX(),getY()));
    }

}
