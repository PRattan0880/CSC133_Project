package org.csc133.a3;

/**
 * ICollider interface is used to handle and detect
 * Collision of gameObjects
 */
public interface ICollider {
    public boolean collidesWith(GameObject otherObj);
    public void handleCollision(GameObject otherObj);
}
