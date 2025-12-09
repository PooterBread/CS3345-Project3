CS3345 Project 3 - QuickSort Implementation
============================================

Student Information:
--------------------
Name: [Your Name]
NetID: [Your NetID]
Project: CS3345 Project 3 - QuickSort

Files Comprising the Project:
------------------------------
1. QuickSorter.java - Static utility class containing the QuickSort implementation
   with four different pivot selection strategies
2. QuickSortMain.java - Main class that handles command-line arguments and file I/O

Development Environment:
------------------------
IDE/Editor: [Your IDE/Editor, e.g., IntelliJ IDEA, Eclipse, VS Code, etc.]
Java Version: [Your Java version, e.g., Java 17, Java 11, etc.]
Compiler Options: Standard javac compilation
Operating System: [Your OS]

How to Compile and Run:
-----------------------
1. Compile the Java files:
   javac QuickSorter.java QuickSortMain.java

2. Run the program:
   java QuickSortMain <array_size> <report_file> <unsorted_file> <sorted_file>
   
   Example:
   java QuickSortMain 100000 report.txt unsorted.txt sorted.txt

Implementation Details:
-----------------------
- QuickSorter is a static utility class that cannot be instantiated
- PivotStrategy is an inner enum with four strategies:
  * FIRST_ELEMENT: Uses the first element as the pivot
  * RANDOM_ELEMENT: Randomly selects a pivot element
  * MEDIAN_OF_THREE_RANDOM_ELEMENTS: Selects median of 3 randomly chosen elements
  * MEDIAN_OF_THREE_ELEMENTS: Selects median of first, center, and last elements
- All sorting is performed in-place (no copies made during sorting)
- Timing is measured using System.nanoTime() and returned as a Duration object
- generateRandomList creates random integers across the entire int range

Report Files:
-------------
[Include copies of report files generated for different array sizes here]

Example Report Format:
Array Size = 100000
FIRST_ELEMENT : PT0.123456789S
RANDOM_ELEMENT : PT0.234567890S
MEDIAN_OF_THREE_RANDOM_ELEMENTS : PT0.345678901S
MEDIAN_OF_THREE_ELEMENTS : PT0.456789012S

Performance Analysis:
--------------------
[After running tests, summarize your findings here]

Which Pivot Strategy is Fastest:
---------------------------------
Based on testing with random arrays of various sizes, [strategy name] appears to be 
the fastest pivot selection strategy. This is because [explain why - e.g., it provides 
better balance, avoids worst-case scenarios, etc.].

Results for Sorted Arrays:
--------------------------
When testing with already sorted arrays (using Arrays.sort() first), the results show:
[Describe which strategy performs best on sorted arrays and why]

Results for Almost Sorted Arrays:
---------------------------------
For almost sorted arrays (sorted then 10% randomly swapped), the results show:
[Describe which strategy performs best on almost sorted arrays and why]

Notes:
------
- The program makes copies of the original list for each pivot strategy test to ensure
  fair comparison, as QuickSort is in-place
- Very small array sizes may show identical timings due to nanosecond resolution limits
- Random variations may cause inferior strategies to occasionally outperform superior ones

