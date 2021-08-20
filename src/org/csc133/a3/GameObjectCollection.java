package org.csc133.a3;
import java.util.Vector;

/**
 * GameObjectCollection class implements ICollection and
 * contains all information on GameObjectCollection
 * object
 */
public class GameObjectCollection implements ICollection {

    private Vector gameCollection;

    /**
     * Constructor for GameObjectCollection
     */
    GameObjectCollection(){

        gameCollection=new Vector();
    }

    /**
     * Add object to the GameCollectionObject
     * @param o Object o
     */
    @Override
    public void add(Object o) {
        gameCollection.addElement(o);
    }

    /**
     * Removes the object from GameObjectCollection
     * @param o Object o
     * @return true or false, true if object exists,
     * false otherwise
     */
    @Override
    public boolean remove(Object o) {
        return gameCollection.remove(o);
    }

    /**
     * Gets the element at current index in GameObjectCollection
     * @param index Int value for index
     * @return the object at index in GameObjectCollection
     */
    @Override
    public Object getElement(int index){
        return gameCollection.elementAt(index);
    }

    /**
     * Removes all element in GameObjectCollection object
     */
    @Override
    public void removeAll(){
        gameCollection.removeAllElements();
    }

    /**
     * @return Size of GameObjectCollection object
     */
    @Override
    public int size(){
        return gameCollection.size();
    }
}
