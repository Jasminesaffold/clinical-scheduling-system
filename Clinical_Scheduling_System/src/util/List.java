package util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

/**
 * Generic list class for code reuse
 * Refactored from project 1
 *
 * @author Jasmine Saffold
 */
public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size; // number of appointments in the array
    /**
     * The initial capacity of the appointments list
     */
    private static final int INIT_CAPACITY = 4;
    /**
     * The growth rate for method grow()
     */
    private static final int GROWTH_RATE = 4;
    /**
     * Constant for find() method
     * Will return -1 if not found
     */
    private static final int NOT_FOUND = -1;

    /**
     * Constructor for List class
     */
    public List() {
        this.objects = (E[]) new Object[INIT_CAPACITY];
        this.size = 0;
    }

    /**
     * Helper method
     * Finds an appointment's index if the appointment is found given Appointment object
     *
     * @param e - object to find
     * @return index -- index of the object in array of Appointment objects
     */
    private int find(E e) {
        for(int i = 0; i < size; i++) {
            if(objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Helper method to increase the capacity by 4
     */
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + GROWTH_RATE];

        for(int i = 0; i < objects.length; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    /**
     * Sorts the list using the comparator class using insertion sort
     *
     * @param comparator - Objects to sort through
     */
    public void sort(Comparator<E> comparator) {
        for (int i = 1; i < size; i++) {
            E key = objects[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(objects[j], key) > 0) {
                objects[j + 1] = objects[j];
                j = j - 1;
            }
            objects[j + 1] = key;
        }
    }

    /**
     * Checks to see if an object is in objects array before add/remove
     *
     * @param e - object to check
     * @return true if the object is found, false otherwise
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Adds an object to the end of the objects array
     * Increases size by 1
     *
     * @param e - object to add
     */
    public void add(E e) {
        if(size == objects.length){
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Removes an object from the objects array
     * Decreases the size by 1
     *
     * @param e - object to remove
     */
    public void remove(E e) {
        int index = find(e);

        if(index != NOT_FOUND) {
            for(int i = index; i < size - 1; i++) {
                objects[i] = objects[i + 1];
            }
            objects[size - 1] = null;
            size--;
        }
    }

    /**
     * Checks if the list is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Gives the size of the appointments list
     *
     * @return size of appointments list
     */
    public int size() { return size; }

    /**
     * Iterates over objects in the list
     *
     * @return iterator for the list
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Return the object at the index parameter
     *
     * @param index - from object to get
     * @return object at index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return objects[index];
    }

    /**
     * Put object e at the index
     *
     * @param index - set object at this index
     * @param e - object to set
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public void set(int index, E e) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        objects[index] = e;
    }

    /**
     * Returns the index of the object or -1 if not found
     *
     * @param e - object to find
     * @return index of the object to find
     */
    public int indexOf(E e) { return find(e); }

    /**
     * Class to make an iterator
     */
    private class ListIterator implements Iterator<E> {
        private int currentIndex = 0;

        /**
         * Tells you if the current index is at the end of the list or not
         *
         * @return false if it's empty or end of the list, tru otherwise
         */
        @Override
        public boolean hasNext() { return currentIndex < size; }

        /**
         * Tells you the next object
         *
         * @return next object in the list
         * @throws NoSuchElementException if there is no next object
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentIndex++;
            return objects[currentIndex];
        }
    }

    /**
     * Override toString() method for List class with StringBuilder class
     *
     * @return string of list
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");

        for(int i = 0; i < size; i++) {
            sb.append(objects[i].toString());

            if(i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
