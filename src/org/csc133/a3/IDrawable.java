package org.csc133.a3;

import com.codename1.ui.Graphics;

import java.awt.*;

/**
 * IDrawable interface is used to draw gameObjects
 * to component
 */
public interface IDrawable {
    public void draw(Graphics g, Point containerOrigin);
}
