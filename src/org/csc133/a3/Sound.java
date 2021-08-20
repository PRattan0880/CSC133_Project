package org.csc133.a3;


import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/**
 * Sound class contains the information on Sound object
 */
public class Sound {
    private Media m;

    /**
     * Constructor for Sound object
     */
    public Sound(String fileName){
        try{
            InputStream is= Display.getInstance().
                    getResourceAsStream(getClass(),"/"+fileName);
            m= MediaManager.createMedia(is,"audio/wav");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Plays the media from beginning
     */
    public void play(){
        m.setTime(0);
        m.play();
    }

    /**
     * Pauses the media
     */
    public void pause(){
        m.pause();
    }


}
