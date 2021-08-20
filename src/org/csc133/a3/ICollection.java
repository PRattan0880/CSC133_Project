package org.csc133.a3;

/**
 * ICollection interface for Collection object
 */
public interface ICollection {
    public void add(Object newObj);
    public boolean remove(Object o);
    public Object getElement(int index);

    public void removeAll();
    public int size();
}
