package org.csc133.a3;

/**
 * IObserver Interface used to update
 * IObservable gameWorld is changed
 */
public interface IObserver {
    public void update(IObservable o);
}