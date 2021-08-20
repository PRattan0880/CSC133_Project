package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;

import java.awt.geom.Point2D;

/**
 * PlayerHelicopter class used the Singleton
 * Design Pattern in order to ensure that there is
 * only one instance of PlayerHelicopter
 */
public class PlayerHelicopter {
    private static Helicopter instance;

    private PlayerHelicopter(){

    }

    /**
     * @return instance of Helicopter
     */
    public static Helicopter getInstance(){
        if(instance==null){
            instance=new Helicopter(100, ColorUtil.rgb(0,255,0),
                    new Point2D.Double(1000,500),0, 0,0,
                    250,1000,1,0,
                    0);
        }
        return instance;
    }

    public static void removeInstance(){
        instance=null;
    }

}
