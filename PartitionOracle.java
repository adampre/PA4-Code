
// These are some imports that the course staff found useful, feel free to use them
// along with other imports from java.util.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PartitionOracle {

    /**
     * Feel free to use this method to call partition. It catches any exceptions or
     * errors and returns a definitely-invalid pivot (-1) to turn errors into
     * potential failures. For example, in testPartition, you may use
     * 
     * runPartition(p, someArray, someLow, someHigh)
     * 
     * instead of
     * 
     * p.partition(someArray, someLow, someHigh)
     * 
     * @param p - the pivot
     * @param strs - the array
     * @param low - the low
     * @param high - the high
     * @return - a definetly invalid pivot (-1)
     */
    public static int runPartition(Partitioner p, String[] strs, int low, int high) {
        try {
            return p.partition(strs, low, high);
        } catch (Throwable t) {
            return -1;
        }
    }

    // The three methods below are for you to fill in according to the PA writeup.
    // Feel free to make other helper methods as needed.

    /**
     * @Return null if the pivot and after array reflect a correct partitioning of 
     * the before array between low and high.
     * 
     * @Return a non-null String (your choice) describing why it isn't a valid
     * partition if it is not a valid result. 
     * 
     * You might choose Strings like these, though there may be more you want to report:
     * - "after array doesn't have same elements as before"
     * - "Item before pivot too large"
     * - "Item after pivot too small"
    */
    public static String isValidPartitionResult(String[] before, int low, int high, int pivot, String[] after) 
    {
        if(pivot < low || high <= pivot) return "pivot index outside of low/high range";

        //compares size of before and after and their elements
        if(!containsSameElements(before, after)) return "after array doesn't have same elements as before";

        for(int i = 0; i < low; i++)
        {
            if(!before[i].equals(after[i])) return "changed elements before low limit";
        }

        for(int i = high; i < before.length; i++)
        {
            if(!before[i].equals(after[i])) return "changed elements after high limit";
        }

        int valueOfPivot = valueOf(after[pivot]);

        for(int i = low; i < pivot; i++)
        {
            if(valueOf(after[i]) > valueOfPivot) return "Item before pivot too large";
        }

        for(int i = pivot; i < high; i++)
        {
            if(valueOf(after[i]) < valueOfPivot) return "Item after pivot too small";
        }

        return null;
    }

    /**
     * Generate a list of items of size n
     */
    public static String[] generateInput(int n) 
    {
        String[] items = new String[n];

        Random r = new Random();

        for(int i = 0; i < items.length; i++)
        {
            int asciiForACapLetter = r.nextInt(26) + 65;  // Generates a random letter from A - Z
            items[i] = Character.toString((char)(asciiForACapLetter));
        }
        
        return items;
    }

    /**
     * 
     * @return - null for any reasonable good partition implementation.
     * @return - a CounterExample for any bad partition implementation.
     */
    public static CounterExample findCounterExample(Partitioner p) 
    {
        int testAmount = 100; 
        int arrayLength = 10;

        Random r = new Random();

        for(int i = 0; i < testAmount; i++)
        {
            String[] before = generateInput(r.nextInt(arrayLength) + 5); 
            String[] after = Arrays.copyOf(before, before.length);

            int low = r.nextInt(before.length - 1);
            int high = r.nextInt(before.length - (low + 1)) + low + 1;

            int pivot = runPartition(p, after, low, high);

            if(pivot == -1)
            {
                return new CounterExample(before, low, high, pivot, after, "exception or error occurred");
            }

            String reason = isValidPartitionResult(before, low, high, pivot, after); 

            if(reason != null)
            {
                return new CounterExample(before, low, high, pivot, after, reason);
            }
        }   

        return null;
    }

    /**
     * @return boolean value whether or not arr1 and arr2 contain the same elements
     */
    private static boolean containsSameElements(String[] arr1, String[] arr2)
    {
        if(arr1.length != arr2.length) return false;

        List<String> list2 = new ArrayList<>(Arrays.asList(arr2));

        for(int i = 0; i < arr1.length; i++)
        {
            if(!list2.contains(arr1[i])) return false;

            list2.remove(arr1[i]);
        }

        return true;
    }

    /**
     * @return int value associated with element
     */
    private static int valueOf(String element)
    {
        int value = 0; 

        for(int i = 0; i < element.length(); i++)
        {
            value += (int)(element.charAt(i));
        }

        return value;
    }
}