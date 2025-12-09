import java.util.ArrayList;
import java.util.Random;
import java.time.Duration;

/**
 * Static utility class for performing QuickSort with different pivot selection strategies.
 * This class should never be instantiated.
 */
public class QuickSorter {
    
    /**
     * Enumeration of pivot selection strategies for QuickSort.
     */
    public enum PivotStrategy {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        MEDIAN_OF_THREE_RANDOM_ELEMENTS,
        MEDIAN_OF_THREE_ELEMENTS
    }
    
    // Private constructor to prevent instantiation
    private QuickSorter() {
        throw new AssertionError("QuickSorter should not be instantiated");
    }
    
    /**
     * Sorts the given list in-place using QuickSort with the specified pivot strategy
     * and returns the time taken in nanoseconds.
     * 
     * @param <E> the type of elements in the list, must extend Comparable<E>
     * @param list the list to sort (will be modified in-place)
     * @param pivotStrategy the pivot selection strategy to use
     * @return the Duration representing the time taken to sort
     * @throws NullPointerException if either argument is null
     */
    public static <E extends Comparable<E>> Duration timedQuickSort(
            ArrayList<E> list, PivotStrategy pivotStrategy) throws NullPointerException {
        
        if (list == null) {
            throw new NullPointerException("List cannot be null");
        }
        if (pivotStrategy == null) {
            throw new NullPointerException("PivotStrategy cannot be null");
        }
        
        // If list is empty or has one element, it's already sorted
        if (list.size() <= 1) {
            return Duration.ZERO;
        }
        
        // Record start time
        long startTime = System.nanoTime();
        
        // Perform QuickSort
        quickSort(list, 0, list.size() - 1, pivotStrategy);
        
        // Record end time and calculate duration
        long endTime = System.nanoTime();
        return Duration.ofNanos(endTime - startTime);
    }
    
    /**
     * Recursive QuickSort implementation.
     * 
     * @param <E> the type of elements in the list
     * @param list the list to sort
     * @param low the starting index
     * @param high the ending index
     * @param pivotStrategy the pivot selection strategy
     */
    private static <E extends Comparable<E>> void quickSort(
            ArrayList<E> list, int low, int high, PivotStrategy pivotStrategy) {
        
        if (low < high) {
            // Select pivot based on strategy
            int pivotIndex = selectPivot(list, low, high, pivotStrategy);
            
            // Partition the array around the pivot
            int partitionIndex = partition(list, low, high, pivotIndex);
            
            // Recursively sort elements before and after partition
            quickSort(list, low, partitionIndex - 1, pivotStrategy);
            quickSort(list, partitionIndex + 1, high, pivotStrategy);
        }
    }
    
    /**
     * Selects the pivot index based on the specified strategy.
     * 
     * @param <E> the type of elements in the list
     * @param list the list
     * @param low the starting index
     * @param high the ending index
     * @param pivotStrategy the pivot selection strategy
     * @return the index of the selected pivot
     */
    private static <E extends Comparable<E>> int selectPivot(
            ArrayList<E> list, int low, int high, PivotStrategy pivotStrategy) {
        
        Random random = new Random();
        
        switch (pivotStrategy) {
            case FIRST_ELEMENT:
                return low;
                
            case RANDOM_ELEMENT:
                return low + random.nextInt(high - low + 1);
                
            case MEDIAN_OF_THREE_RANDOM_ELEMENTS:
                // Select three random indices
                int idx1 = low + random.nextInt(high - low + 1);
                int idx2 = low + random.nextInt(high - low + 1);
                int idx3 = low + random.nextInt(high - low + 1);
                // Return median of the three
                return medianOfThree(list, idx1, idx2, idx3);
                
            case MEDIAN_OF_THREE_ELEMENTS:
                // First, center, and last element
                int center = (low + high) / 2;
                return medianOfThree(list, low, center, high);
                
            default:
                return low;
        }
    }
    
    /**
     * Returns the index of the median value among three indices.
     * 
     * @param <E> the type of elements in the list
     * @param list the list
     * @param idx1 first index
     * @param idx2 second index
     * @param idx3 third index
     * @return the index of the median value
     */
    private static <E extends Comparable<E>> int medianOfThree(
            ArrayList<E> list, int idx1, int idx2, int idx3) {
        
        E val1 = list.get(idx1);
        E val2 = list.get(idx2);
        E val3 = list.get(idx3);
        
        // Compare the three values to find the median
        if ((val1.compareTo(val2) <= 0 && val2.compareTo(val3) <= 0) ||
            (val3.compareTo(val2) <= 0 && val2.compareTo(val1) <= 0)) {
            return idx2;
        } else if ((val2.compareTo(val1) <= 0 && val1.compareTo(val3) <= 0) ||
                   (val3.compareTo(val1) <= 0 && val1.compareTo(val2) <= 0)) {
            return idx1;
        } else {
            return idx3;
        }
    }
    
    /**
     * Partitions the array around the pivot element.
     * 
     * @param <E> the type of elements in the list
     * @param list the list to partition
     * @param low the starting index
     * @param high the ending index
     * @param pivotIndex the index of the pivot element
     * @return the final position of the pivot element
     */
    private static <E extends Comparable<E>> int partition(
            ArrayList<E> list, int low, int high, int pivotIndex) {
        
        // Move pivot to the end
        E pivotValue = list.get(pivotIndex);
        swap(list, pivotIndex, high);
        
        // Partition the array
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivotValue) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        
        // Move pivot to its correct position
        swap(list, i + 1, high);
        return i + 1;
    }
    
    /**
     * Swaps two elements in the list.
     * 
     * @param <E> the type of elements in the list
     * @param list the list
     * @param i first index
     * @param j second index
     */
    private static <E> void swap(ArrayList<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    
    /**
     * Generates and returns a new integer ArrayList with the given size
     * that consists of random and unsorted values.
     * 
     * @param size the size of the list to generate (must be nonnegative)
     * @return a new ArrayList of random integers
     * @throws IllegalArgumentException if size is negative
     */
    public static ArrayList<Integer> generateRandomList(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be nonnegative");
        }
        
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>(size);
        
        // Generate random integers across the entire range of int
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt());
        }
        
        return list;
    }
}

