package util;

import java.security.Provider;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic CircularLinkedList class
 * Will be used to rotate technicians for imaging appointments
 *
 * @author Jasmine Saffold
 */
public class CircularLinkedList<E> implements Iterable<E> {
    private Node<E> head; // the head of the list
    private Node<E> last; // the last node in the list
    private int size; // number of nodes in the list

    /**
     * Inner class for Node
     *
     * @param <E>
     */
    public static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }

        // Getter for data
        public E getData() {
            return data;
        }
    }

    /**
     * Constructor for circular linked list class
     */
    public CircularLinkedList() {
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Adds a node object to the end of the list
     *
     * @param object - object to add
     */
    public void add(E object) {
        Node<E> node = new Node<>(object);
        if (isEmpty()) {
            head = node;
            last = node;
            last.next = head;
        } else {
            last.next = node;
            last = node;
            last.next = head;
        }
        size++;
    }

    public void rotate(E object) {
        Node<E> tempNode = head;
        boolean firstIteration = true;

        while(tempNode != head || firstIteration) {
            firstIteration = false;
            if(tempNode.data.equals(object)) {
                head = tempNode.next;
                last = tempNode;
                break;
            }
            tempNode = tempNode.next;
        }
    }

    /**
     * Removes a node object from the list
     *
     * @param object - object to remove
     */
    public void remove(E object) {
        if (isEmpty()) {
            return;
        }

        Node<E> curr = head;
        Node<E> prev = last;

        while (curr != head || prev == last) {
            if (curr.data.equals(object)) {
                if (curr == head) {
                    head = head.next;
                    last.next = head;
                } else if (curr == last) {
                    last = prev;
                    last.next = head;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    /**
     * Gives the node at a certain index
     *
     * @param index - index of node to find
     * @return curr
     */
    public Node<E> get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not a valid index.");
        }
        Node<E> curr = head;
        int count = 0;

        while(count < index) {
            curr = curr.next;
            count ++;
        }
        return curr;
    }

    /**
     * Checks if the list is empty
     *
     * @return true if list is empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Gives the size of the list
     *
     * @return size of the list
     */
    public int size() { return size; }

    /**
     * Reverses a circular linked list
     */
    public void reverse() {
        if (isEmpty() || head.next == head) {
            return;
        }

        Node<E> prev = last;
        Node<E> curr = head;
        Node<E> next = null;

        do {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        } while (curr != head);

        last = head;
        head = prev;
    }
    /**
     * Iterates over objects in the list
     *
     * @return iterator for the list
     */
    @Override
    public Iterator<E> iterator() { return new CircularIterator(); }

    /**
     * Class to make an iterator
     */
    private class CircularIterator implements Iterator<E> {
        private Node<E> curr = head;
        private int count = 0;

        /**
         * Tells you if the current index is at the end of the list or not
         *
         * @return false if it's empty or end of the list, true otherwise
         */
        @Override
        public boolean hasNext() {
            return count < size;
        }
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
            E data = curr.data;
            curr = curr.next;
            count++;
            return data;
        }
    }

    /**
     * Override toString() method for CLL class with StringBuilder class
     *
     * @return string of list
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");

        if (!isEmpty()) {
            Node<E> curr = head;
            boolean firstIteration = true;

            while (curr != head || firstIteration) {
                firstIteration = false;
                sb.append(curr.data);
                curr = curr.next;

                if (curr != head) {
                    sb.append(", ");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}