// Test file originally written by Colby CS Department Professors.


// Libraries used. 
import java.util.Arrays;
import java.util.Random;


// The purpose of the HeapTest class is to test the Heap class and methods. 
public class HeapTest {
    
    public static void test(int n) {

        PriorityQueue<Double> test = new Heap<>();
        double[] control = new double[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            control[i] = rand.nextDouble();
            test.offer(control[i]);
        }
        Arrays.sort(control);
        for(int i = 0; i < control.length; i++)
            if (test.size() == 0 || !test.peek().equals(control[i]) || !test.poll().equals(control[i]))
                System.out.println("ERROR for n == " + n + " after removing " + (1000 - i) + " items.");
    }


    public static void main(String[] args){
        for(int n : new int[] {3, 20, 100000}) test(n);
    }
}