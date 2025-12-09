import java.util.ArrayList;
import java.util.Random;
import java.time.Duration;

public class QuickSorter {
    public enum PivotStrategy {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        MEDIAN_OF_THREE_RANDOM_ELEMENTS,
        MEDIAN_OF_THREE_ELEMENTS
    }
    
    private QuickSorter() {
        throw new AssertionError("The QuickSorter cant be instantiated");
    }
    
    public static <E extends Comparable<E>> Duration timedQuickSort(
            ArrayList<E> list, PivotStrategy pivotStrategy) throws NullPointerException {
        
        if (list == null) {
            throw new NullPointerException("The list cant be null");
        }
        if (pivotStrategy == null) {
            throw new NullPointerException("The pivot strategy cant be null");
        }
        
        // If list is less than or equal to one element, then it's already sorted
        if (list.size() <= 1) {
            return Duration.ZERO;
        }
        long startTime = System.nanoTime();
        
        // Starts the quicksort
        quickSort(list, 0, list.size() - 1, pivotStrategy);
        
        long endTime = System.nanoTime();
        return Duration.ofNanos(endTime - startTime);
    }
    
    private static <E extends Comparable<E>> void quickSort(
            ArrayList<E> list, int low, int high, PivotStrategy pivotStrategy) {
        
        if (low < high) {
            // Selects the pivot based on the strategy
            int pivotIndex = selectPivot(list, low, high, pivotStrategy);
            
            // Partitions the array around the pivot
            int partitionIndex = partition(list, low, high, pivotIndex);
            
            // Recursively sorts the elements before and after the partition
            quickSort(list, low, partitionIndex - 1, pivotStrategy);
            quickSort(list, partitionIndex + 1, high, pivotStrategy);
        }
    }
    
    private static <E extends Comparable<E>> int selectPivot(
            ArrayList<E> list, int low, int high, PivotStrategy pivotStrategy) {
        
        Random random = new Random();
        
        switch (pivotStrategy) {
            case FIRST_ELEMENT:
                return low;
                
            case RANDOM_ELEMENT:
                return low + random.nextInt(high - low + 1);
                
            case MEDIAN_OF_THREE_RANDOM_ELEMENTS:
                // Selects three random indexes
                int idx1 = low + random.nextInt(high - low + 1);
                int idx2 = low + random.nextInt(high - low + 1);
                int idx3 = low + random.nextInt(high - low + 1);
                // Returns the median of the three values
                return medianOfThree(list, idx1, idx2, idx3);
                
            case MEDIAN_OF_THREE_ELEMENTS:
                int center = (low + high) / 2;
                return medianOfThree(list, low, center, high);
                
            default:
                return low;
        }
    }
    
    private static <E extends Comparable<E>> int medianOfThree(
            ArrayList<E> list, int idx1, int idx2, int idx3) {
        
        E val1 = list.get(idx1);
        E val2 = list.get(idx2);
        E val3 = list.get(idx3);
        
        // Compares the three values to find the median value
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
    
    private static <E extends Comparable<E>> int partition(
            ArrayList<E> list, int low, int high, int pivotIndex) {
        
        // Moves the pivot to the end of the array
        E pivotValue = list.get(pivotIndex);
        swap(list, pivotIndex, high);
        
        // Partitions the array
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivotValue) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        
        // Moves the pivot to its right position
        swap(list, i + 1, high);
        return i + 1;
    }
    
    private static <E> void swap(ArrayList<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    
    public static ArrayList<Integer> generateRandomList(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("The size must be nonnegative");
        }
        
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>(size);
        
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt());
        }
        
        return list;
    }
}

