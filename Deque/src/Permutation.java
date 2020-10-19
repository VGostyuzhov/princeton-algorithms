import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        int k = Integer.valueOf(args[0]);

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            randQueue.enqueue(value);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randQueue.dequeue());
        }
    }
}
