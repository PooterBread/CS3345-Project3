CS3345 Project 3 - QuickSort Implementation
Name: Peter Siba
NetID: PXS220108
Professor: Meghana Satpute
============================================

Files in the project:
---------------------
1. QuickSorter.java - This Static utility class containing the QuickSort implementation
   with four different pivot selection strategies
2. QuickSortMain.java - Main class that handles command-line arguments and file I/O

Development environment:
------------------------
IDE/Editor: VS Code
Java Version: Java 23.0.1
Compiler Version and Options: Javac 23.0.1 and Standard javac compilation

How to compile and run:
-----------------------
1. Compile the Java files:
   javac QuickSorter.java QuickSortMain.java

2. Run the program: (Input your own variables)
   java QuickSortMain <array_size> <report_file> <unsorted_file> <sorted_file>
   
   Example run: (Use this to test the program)
   java QuickSortMain 100000 report.txt unsorted.txt sorted.txt

Implementation details:
-----------------------
- QuickSorter is a static utility class that cant be instantiated
- PivotStrategy has these four strategies:
   FIRST_ELEMENT: Uses the first element as the pivot
   RANDOM_ELEMENT: Randomly selects a pivot element
   MEDIAN_OF_THREE_RANDOM_ELEMENTS: Selects median of 3 randomly chosen elements
   MEDIAN_OF_THREE_ELEMENTS: Selects median of first, center, and last elements
- All of the sorting is done in place (This means no copies were made during the sorting)
- Timing is also measured using System.nanoTime() and is returned as a duration object
- generateRandomList makes random integers ranging from entire integer range

Report files test samples:
--------------------------
Example Report Format:
Array Size = 100000
FIRST_ELEMENT : PT0.123456789S
RANDOM_ELEMENT : PT0.234567890S
MEDIAN_OF_THREE_RANDOM_ELEMENTS : PT0.345678901S
MEDIAN_OF_THREE_ELEMENTS : PT0.456789012S

Array Size = 12345
FIRST_ELEMENT : PT0.0048535S
RANDOM_ELEMENT : PT0.003312542S
MEDIAN_OF_THREE_RANDOM_ELEMENTS : PT0.003106917S
MEDIAN_OF_THREE_ELEMENTS : PT0.001422458S


Which of the pivot strategies is fastest?:
------------------------------------------
Based on the QuickSort implementation and algorithm analysis, MEDIAN_OF_THREE_ELEMENTS in my view is 
the fastest pivot strategy for random arrays. This strategy grabs the median of the first, 
center, and last elements, and gives balance between pivot 
quality and computational overhead. It avoids the worst case time complexity(O(n^2)) by making sure the 
pivot isnt an extreme value while keeping the O(1) time complexity for the pivot. 
This also helps make it cache friendly and predictable too. FIRST_ELEMENT actually is the slowest for most 
of the cases since it can easily end up being O(n^2) when the array becomes sorted or even nearly sorted.


Results for the sorted arrays:
------------------------------
For the already sorted arrays, The FIRST_ELEMENT performs the worst with a O(n^2) time complexity.
Since it always selects the smallest remaining element as the pivot it resulted in 
unbalanced partitions. This causes the algorithm to make an n amount of recursive calls, 
where each is processing one less element. RANDOM_ELEMENT and MEDIAN_OF_THREE_RANDOM_ELEMENTS 
performs much better with a time complexity of O(nlogn), since the random pivot selection breaks the sorted 
pattern and creates more balanced partitions as well. 
MEDIAN_OF_THREE_ELEMENTS also much better and has the same time complexity as RANDOM_ELEMENT
since the first, center, and last elements are in sorted order, selecting the median element
still gives it some reasonable balance too.


Results for the mostly sorted arrays: (or also 'almost sorted' arrays)
----------------------------------------------------------------------
For almost sorted arrays, the results are very similar to sorted arrays. 
The FIRST_ELEMENT still struggles just like before since most of the array remains sorted
which cause it to frequently select close to minimum elements and create multiple unbalanced partitions.
But the random swaps sometimes gives reliefs, so it ends up performing slightly better 
than on fully sorted arrays but still worse than other ways. RANDOM_ELEMENT and 
MEDIAN_OF_THREE_RANDOM_ELEMENTS still perform well with O(nlogn), since the random pivot selection 
breaks up the mostly sorted pattern. MEDIAN_OF_THREE_ELEMENTS also performs just as well, 
and in this case is typically the fastest, providing a good balance with a minimum overhead. 
The median-of-three approach is most effective in this case since it samples elements from multiple 
parts of the subarray, which reduces the impact of the mostly sorted structure.

