import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int max;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 2;
        queue = (Item[]) new Object[capacity];
        max = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return max == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return max;
    }

    // add the item
    public void enqueue(final Item item) {
        // System.out.println("Enqueue: N=" + N + ", capacity=" + capacity + ", length=" + queue.length);
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (queue.length == max) {
            capacity = capacity * 2;
            resize();
        }
        queue[max++] = item;
    }

    private void resize() {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < max; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (max == 0) {
            throw new java.util.NoSuchElementException();
        }

        int r = StdRandom.uniform(max);
        Item item = queue[r];
        if (r != max - 1) {
            queue[r] = queue[max - 1];
        }
        queue[--max] = null;
        if (max > 0 && max == queue.length / 4) {
            capacity = capacity / 2;
            resize();
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (max == 0) {
            throw new java.util.NoSuchElementException();
        }
        int r = StdRandom.uniform(max);
        Item item = queue[r];
        return item;
    }

    private class ArrayRandomIterator implements Iterator<Item> {
        int n;
        Item[] randQueue = (Item[]) new Object[max];

        public ArrayRandomIterator() {
            n = 0;
            for (int i = 0; i < max; i++) {
                randQueue[i] = queue[i];
            }
            StdRandom.shuffle(randQueue);
        }

        public boolean hasNext() {
            return n < max;
        }

        public Item next() {
            if (n == max) {
                throw new java.util.NoSuchElementException();
            }
            return randQueue[n++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ArrayIterator implements Iterator<Item> {
        int n;

        public ArrayIterator() {
            n = 0;
        }

        public boolean hasNext() {
            return n < max;
        }

        public Item next() {
            if (n == max) {
                throw new java.util.NoSuchElementException();
            }
            return queue[n++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayRandomIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int j = 1; j <= 100; j++)
            queue.enqueue(j);
        while (!queue.isEmpty()) {
            queue.dequeue();
        }

        /*
        randQueue.dequeue();
        randQueue.size();
        randQueue.enqueue(351);
        System.out.println(randQueue.dequeue());
        System.out.println(randQueue.isEmpty());
        System.out.println(randQueue.isEmpty());
        System.out.println(randQueue.size());
        randQueue.enqueue(416);
        System.out.println(randQueue.isEmpty());
        System.out.println(randQueue.size());
        for (Object e : randQueue) {
            System.out.println(e);
        }

        randQueue.dequeue();
        for (Object e : randQueue) {
            System.out.println(e);
        }*/
    }

}
