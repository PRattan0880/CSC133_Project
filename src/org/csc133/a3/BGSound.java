package org.csc133.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

/**
 * BGSound class contains the information on BGSound object
 * and extends Runnable in order to make background sound loop
 */
public class BGSound implements Runnable{
    private Media m;

    /**
     * Constructor for BGSound object
     */
    public BGSound(String fileName){
        try{
            InputStream is= Display.getInstance().
                    getResourceAsStream(getClass(),"/"+fileName);
            m= MediaManager.createMedia(is,"audio/wav",
                    this);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void pause(){
        m.pause();
    }

    public void play(){
        m.play();
    }

    @Override
    public void run() {
        m.setTime(0);
        m.play();
    }
}
