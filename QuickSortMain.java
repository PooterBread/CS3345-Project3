import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

/**
 * Main class for running QuickSort performance tests.
 * Takes four command line arguments:
 * 1. Array size
 * 2. Report filename
 * 3. Unsorted array filename
 * 4. Sorted array filename
 */
public class QuickSortMain {
    
    public static void main(String[] args) {
        // Validate command line arguments
        if (args.length != 4) {
            System.err.println("Usage: java QuickSortMain <array_size> <report_file> <unsorted_file> <sorted_file>");
            System.exit(1);
        }
        
        try {
            int arraySize = Integer.parseInt(args[0]);
            String reportFile = args[1];
            String unsortedFile = args[2];
            String sortedFile = args[3];
            
            // Generate random list
            ArrayList<Integer> originalList = QuickSorter.generateRandomList(arraySize);
            
            // Save unsorted array to file
            writeListToFile(originalList, unsortedFile);
            
            // Test each pivot strategy
            Duration firstElementTime = testPivotStrategy(
                originalList, QuickSorter.PivotStrategy.FIRST_ELEMENT, "FIRST_ELEMENT");
            Duration randomElementTime = testPivotStrategy(
                originalList, QuickSorter.PivotStrategy.RANDOM_ELEMENT, "RANDOM_ELEMENT");
            Duration medianThreeRandomTime = testPivotStrategy(
                originalList, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_RANDOM_ELEMENTS, 
                "MEDIAN_OF_THREE_RANDOM_ELEMENTS");
            Duration medianThreeTime = testPivotStrategy(
                originalList, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS, 
                "MEDIAN_OF_THREE_ELEMENTS");
            
            // Save the final sorted array (from the last sort) to file
            ArrayList<Integer> finalSorted = new ArrayList<>(originalList);
            QuickSorter.timedQuickSort(finalSorted, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS);
            writeListToFile(finalSorted, sortedFile);
            
            // Write report
            writeReport(reportFile, arraySize, firstElementTime, randomElementTime, 
                       medianThreeRandomTime, medianThreeTime);
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Array size must be a valid integer");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Tests a pivot strategy by making a copy of the list, sorting it, and returning the time.
     * 
     * @param originalList the original unsorted list
     * @param strategy the pivot strategy to test
     * @param strategyName the name of the strategy (for debugging)
     * @return the Duration taken to sort
     */
    private static Duration testPivotStrategy(ArrayList<Integer> originalList, 
                                            QuickSorter.PivotStrategy strategy, 
                                            String strategyName) {
        // Make a deep copy of the list for this test
        ArrayList<Integer> testList = new ArrayList<>(originalList);
        return QuickSorter.timedQuickSort(testList, strategy);
    }
    
    /**
     * Writes a list to a file, one integer per line.
     * 
     * @param list the list to write
     * @param filename the filename to write to
     * @throws IOException if an I/O error occurs
     */
    private static void writeListToFile(ArrayList<Integer> list, String filename) 
            throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Integer value : list) {
                writer.write(value.toString() + "\n");
            }
        }
    }
    
    /**
     * Writes the performance report to a file.
     * 
     * @param filename the report filename
     * @param arraySize the size of the array that was sorted
     * @param firstElementTime time for FIRST_ELEMENT strategy
     * @param randomElementTime time for RANDOM_ELEMENT strategy
     * @param medianThreeRandomTime time for MEDIAN_OF_THREE_RANDOM_ELEMENTS strategy
     * @param medianThreeTime time for MEDIAN_OF_THREE_ELEMENTS strategy
     * @throws IOException if an I/O error occurs
     */
    private static void writeReport(String filename, int arraySize,
                                   Duration firstElementTime,
                                   Duration randomElementTime,
                                   Duration medianThreeRandomTime,
                                   Duration medianThreeTime) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Array Size = " + arraySize + "\n");
            writer.write("FIRST_ELEMENT : " + firstElementTime + "\n");
            writer.write("RANDOM_ELEMENT : " + randomElementTime + "\n");
            writer.write("MEDIAN_OF_THREE_RANDOM_ELEMENTS : " + medianThreeRandomTime + "\n");
            writer.write("MEDIAN_OF_THREE_ELEMENTS : " + medianThreeTime + "\n");
        }
    }
}

