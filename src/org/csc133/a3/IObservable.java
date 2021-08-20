package org.csc133.a3;

/**
 * IObservable interface is used to add and
 * notify observers for any changes in gameWorld
 */
public interface IObservable
{
    public void addObserver(IObserver obs);
    public void notifyObservers();
}
