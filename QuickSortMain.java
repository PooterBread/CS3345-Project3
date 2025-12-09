import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class QuickSortMain {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Format error: java QuickSortMain <array_size> <report_file> <unsorted_file> <sorted_file>");
            System.exit(1);
        }
        
        try {
            int arraySize = Integer.parseInt(args[0]);
            String reportFile = args[1];
            String unsortedFile = args[2];
            String sortedFile = args[3];
            
            // This will generate a random list
            ArrayList<Integer> originalList = QuickSorter.generateRandomList(arraySize);
            
            writeListToFile(originalList, unsortedFile);
            
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
            
            ArrayList<Integer> finalSorted = new ArrayList<>(originalList);
            QuickSorter.timedQuickSort(finalSorted, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS);
            writeListToFile(finalSorted, sortedFile);
            
            // Creates the report with the data
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
    
    private static Duration testPivotStrategy(ArrayList<Integer> originalList, 
        QuickSorter.PivotStrategy strategy, String strategyName) {
        ArrayList<Integer> testList = new ArrayList<>(originalList);
        return QuickSorter.timedQuickSort(testList, strategy);
    }
    
    private static void writeListToFile(ArrayList<Integer> list, String filename) 
            throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Integer value : list) {
                writer.write(value.toString() + "\n");
            }
        }
    }
   
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

