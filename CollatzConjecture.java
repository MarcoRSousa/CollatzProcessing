// Imports ArrayList class
// Imports Scanner class for number input; this is especially useful for Eclipse as well
// Imports array methods like Arrays.toString
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

// This program is a Summer personal hobby project I did going into Freshman year of college. It explores various properties of the Collatz Conjecture.
/* List of processes this program provides:
/* Collatz test on one integer
/* Collatz test on range of integers (generates countList, showing HOW MANY computations each number took)
/* Finds maximum and Max Index values of countList
/* Finds the number of "occurances." How many times a certain number of computations appears."It took 7 computations for 3 different numbers".
/* Has a primitive recursiveCount, which checks for repeated series of numbers of occurances in a row but as of now extra counts
/* Improved recursiveCount. If you wish, observe how recursiveCount1() has additional counts for every repeated number AFTER two repeats of the same number in a row.
/* Matrix Method (Functional) recursivecounts5();
*/

public class CollatzConjecture
{
    
    /* Initially I wanted this to be NON-static.However, static functions like findMax() do not work globally unless my countList is static. 
    /* An annoyance is that I have to recreate a local max variable each time. A benefit is I can globally access my max value.
    */
    static ArrayList<Integer> countList = new ArrayList<Integer>();
    
    // Used as the input number and the count for single, interval, and recursiveCount collatz functions
    public int num = 0;
    public int count = 0;
    
    // to save time not reinstancing local loops; not referenced in static cases of findMax
    public int i;
    public int j;
    
    public int recursiveCount;
    
    public int maxrecursiveCount = 0;
    
    //public int matrixNum;
    
    public static void main(String[] args)
    {
    	CollatzConjecture collatz = new CollatzConjecture();
        collatz.singleCollatzConjecture();
        collatz.intervalCollatzConjecture();
        System.out.println("Max of countList is: " + findMax(countList));
        System.out.println("Max Index of countList is: " + findMaxIndex(countList));
        // This means the number with MAXIMUM computations is the Max Index + 1 !
        //collatz.collatzEquivalenceCount();
        collatz.collatzOccuranceCount();
        //collatz.recursiveCounts1();
        //collatz.recursiveCounts2();
        //collatz.recursiveCounts3();
        collatz.recursiveCounts4();
        collatz.recursiveCounts5();
    }
    
    // Performs collatz computations on a single number by input.
    // The nextInt Scanner allows you to input values at different times for different functions.
    
    public void singleCollatzConjecture() {
        // num = readInt("What number would you like to test?");
        Scanner scan = new Scanner(System.in);
        num = scan.nextInt();
        if (num <= 1) {
            System.out.println("That is not a valid number.");
        }
        while (num > 1) {
            if (num % 2 == 0) {
                num = num/2;
                count++;
                System.out.println(num);
            } else {
                num = ((3*num)+1)/2;
                count = count + 2;
                System.out.println(num);
            }
        }
        System.out.println("It took " + count + " to finish.");
        System.out.println("The final number is " + num + ".");
    }
    
    //Performs the collatz computations along an interval
    public void intervalCollatzConjecture() {
        
        //Integer a is used as a replacement for i along the list so the for loop does not interfere with the while loop
        int a;
        
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        
        Scanner scan = new Scanner(System.in);
        num = scan.nextInt();
        //num = readInt("What number would you like to test to?");
        if (num <= 1) {
            System.out.println("That is not a valid number.");
        }
        for (i = 1; i <= num; i++) {
                
            valueList.add(i);
            a = i;
            count = 0;
            while (a > 1) {
                if (a % 2 ==0) {
                    a = a/2;
                    count++;
                } else {
                    a = ((3*a)+1)/2;
                    count = count + 2;
                }
            }
            countList.add(count);
        }
        System.out.println(valueList);
        System.out.println("Below shows an arraylist describing how many computations it took for each number to reach 1 on the interval:");
        System.out.println(countList);
    }
    
    // Method to determine maximum number of computations along the countList. Used with collatz.occurances when countList wasn't static.
    /*public int findMax(int[] countList) {
        max = countList.get(0);
        
        for(int i = 0; i < countList.size(); i++) {
            if (countList.get(i) > max) {
                maxIndex = i;
                max = countList.get(i);
            }
        }
        return max;
    }*/
    
    
    public static Integer findMax(ArrayList<Integer> countList) {
        int max = countList.get(0);
        for (int i = 0; i < countList.size(); i++) {
            if (max < countList.get(i)) {
                max = countList.get(i);
            }
        }
        return max;
    }
    
    public static Integer findMaxIndex(ArrayList<Integer> countList) {
        int max = countList.get(0);
        int maxIndex = 0;
        for (int i = 0; i < countList.size(); i++) {
            if (max < countList.get(i)) {
                max = countList.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    // This is an occuranceCount ArrayList.
    // The way to read this is that a number of the index THAT took that many computations.
    // Example: Assume you ran interval to 16. You will see two 17s. On occurances you will see a 2 at Index 17.
    public void collatzOccuranceCount() { 
        int max = findMax(countList);
        int[] occurances = new int[max+1];
        
        for (int c : countList) {
            occurances[c]++;
        }
        System.out.println("Below shows an array describing how many times each count occured:");
    	System.out.println(Arrays.toString(occurances));
    }    
    
    // Could have checked valueList versus countList but blegh this isn't important anyhow.
    
    public void collatzEquivalenceCount() {

        ArrayList<Integer> EquivalenceList = new ArrayList<Integer>();
        
        for(int i = 0; i < countList.size(); i++) {
            //System.out.println("Index:" + i);
            //System.out.println("Number of comps:" + countList.get(i));
            if(i+1 == countList.get(i)) {
                EquivalenceList.add(countList.get(i));
            }
        }
        System.out.println("Below shows an arraylist describing when the index equals the count (num computations = to number)");
        System.out.println(EquivalenceList);
    }
    
    
    // The recursiveCount creates an array of how many times a number REPEATED themselves at ANY length
    /* I created multiple recursiveCounts and these comments show how I came to my final count.
    / I first used 2 nested loops, which checked each Index and the next. However, I check additional times when there are 3 or more of the same number in a row
    / I created recursiveCounts to determine how much I needed to skip in my initial loop. Was difficult for the nested however.
    / Instead I created a new method of a loop and a while. recursiveCounts3 shows how the process works where recursive4 is the practical functionality
    */
    
    public void recursiveCounts1() {
        int max = findMax(countList);
        int[] duplicates = new int[max+1];
        recursiveCount = 0;
        for (i = 0; i < num; i++) {
            for (j = i + 1; j < num; j++, i++) {
                if (countList.get(i).equals(countList.get(j))) {
                    duplicates[countList.get(i)]++;
                } else {
                    break;
                }
            }
        }
        System.out.println("Below shows an array describing how many times a count occurs subsequently:");
        System.out.println(Arrays.toString(duplicates));
    }

    public void recursiveCounts2() {
        int max = findMax(countList);
        int[] duplicates = new int[max+1];
        recursiveCount = 0;
        for (i = 0; i < num; i++) {
            for (j = i + 1; j < num; j++, i++) {
                if (countList.get(i).equals(countList.get(j))) {
                    //System.out.println("We got a winner!");
                    recursiveCount++;
                    duplicates[countList.get(i)]++;
                    System.out.println(recursiveCount);
                    System.out.println("The Index right now is: " + i);
                    //Maybe I can check the future to see when to stop for 3 or more numbers in a row? (Was the idea at least here)
                    if (j+1 < num) {
                        if(countList.get(i+1).equals(countList.get(j+1))){
                            System.out.println("Yes: " + i);
                        } else {
                            System.out.println("No: " + i);
                            i = i + recursiveCount;
                            recursiveCount = 0;
                        }
                    }
                } else {
                    System.out.println("The Index right now is: " + i);
                    break;
                }
            }
        }
        System.out.println("Below shows an array describing how many times a count occurs subsequently:");
        System.out.println(Arrays.toString(duplicates));
    }
    
    /* New idea of recursiveCounts3() and 4() instead of nested for loop checking 2 adjacent indeces
    /* 1. Iterate through the entire countList with a for Loop
    /* 2. IF we see a repeat, do the next thing
    /* store the number at the first index
    /* while that number equals the number of the NEXT index, add to a count to know how much to skip
    /* Increment to the recursiveCount Array
    /* Add to the initial for Loop to skip past the line of repeated numbers
    /* max+1 provbably shows all possible repeats No matter where stopped, is needed for counts4() but not 5() because we wouldn't know at set 4()
    */
    
    public void recursiveCounts3() {
        int max = findMax(countList);
        int[] duplicates = new int[max+1];
        recursiveCount = 0;
        for (i = 0; i < num; i++) {
            System.out.println("Here we go!");
            // This is simply to show the start of each increment of our for loop
            int k = i + 1;
            if (k < num) {
                if(countList.get(i).equals(countList.get(k))) {
                    System.out.println("We have two identical numbers.");
                    System.out.println("i is: " + i);
                    System.out.println("k is: " + k);
                    System.out.println("i value is: " + countList.get(i));               
                    System.out.println("k value is: " + countList.get(k));
                    System.out.println("=========================");
                    // Used to see what is going on in the beginning
                    int tempNum = countList.get(i);
                    
                    while (tempNum == countList.get(k)) {
                        recursiveCount++;
                        if (k < num - 1){
                            k++;
                        } else {
                            break;
                        }
                        System.out.println("recursiveCount is: " + recursiveCount);
                        System.out.println("k is: " + k);
                        System.out.println("i is: " + i);
                    }
                    System.out.println("=========================");
                    System.out.println("k is now: " + k);
                    System.out.println("i is: " + i);
                    System.out.println("THEN (after adding our recursiveCount)");
                    duplicates[countList.get(i)]++;
                    i = i + recursiveCount;
                    recursiveCount = 0;
                    System.out.println("i is: " + i);
                    System.out.println(Arrays.toString(duplicates));
                }
            }
        }
    }
    
    //Practical function to show how many times a count occurs subsequently.
    public void recursiveCounts4() {
        int max = findMax(countList);
        int[] duplicates = new int[max+1];
        recursiveCount = 0;
        
        
        for (i = 0; i < num; i++) {
            int k = i + 1;
            if (k < num) {
                if(countList.get(i).equals(countList.get(k))) {
                    int tempNum = countList.get(i);
                    while (tempNum == countList.get(k)) {
                        recursiveCount++;
                        if (k < num-1){
                            k++;
                        } else {
                            break;
                        }
                    }
                    duplicates[countList.get(i)]++;
                    i = i + recursiveCount;
                    if(recursiveCount > 0){
                    }
                    if (recursiveCount > maxrecursiveCount) {
                        maxrecursiveCount = recursiveCount;
                    }
                    //This is used for determining matrix size in recursiveCount5()
                    recursiveCount = 0;
                }
            }
        }
        System.out.println("Below shows an array describing how many times a count occurs subsequently (actually):");
        System.out.println(Arrays.toString(duplicates));
        int lastUsefulIndex = 0;
        
        for(int i = 0; i < duplicates.length; i++) {
            if(duplicates[i] > 0) {
                lastUsefulIndex = i;
            }
        }
        
        System.out.println("Here is lastUsefulIndex:" + lastUsefulIndex);
        System.out.println("Here is actually the last index based on the max # of computations:" + (max+1));
    }
    
    
    // I can't access duplicates if I make this a nested function...I KNOW I can find an easy way however...
    /* 
    public void lastIndexOptimization() {
        int lastIndex = 0;
        
        for(int i = 0; i < duplicates.length; i++) {
            if(duplicates.get(i) > 0) {
                lastIndex = i;
            }
        }
        
        System.out.println("Here is lastIndex:" + lastIndex);
    }
    */
    
    
    
    // recursiveCounts5 creates a matrix that compares repeats of a computations versus length. Location is not displayed here.
    // "How many times did the number (of computations) x repeat length 1,2,3,4,5..."
    // In this way we're simply expanding out (vertically) our prior duplicates array from recursiveCounts4
    // Note that recursive5() does not run without recursive4() to set its how many rows it will have
    // Note that the matrix sums vertically to be recursiveCount(4)
    
        public void recursiveCounts5() {
        int max = findMax(countList);
        int[] duplicates = new int[max+1];
        recursiveCount = 0;
        
        int[][] matrix = new int[maxrecursiveCount+1][max+1];
        
        for (i = 0; i < num; i++) {
            int k = i + 1;
            if (k < num) {
                if(countList.get(i).equals(countList.get(k))) {
                    int tempNum = countList.get(i);
                    while (tempNum == countList.get(k)) {
                        recursiveCount++;
                        if (k < num - 1){
                            k++;
                        } else {
                            break;
                        }
                    }
                    matrix[recursiveCount][countList.get(i)]++;
                    i = i + recursiveCount;
                    recursiveCount = 0;
                }
            }
        }
        
        
        printMatrix(matrix);
    }
// Need to figure out dimensions of matrix a little better

    private void printMatrix(int[][] matrix) {
        System.out.println("_____________________");
        System.out.print("[");
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if (j < matrix[0].length -1){
                    System.out.print(matrix[i][j]+", ");
                } else {
                    System.out.print(matrix[i][j]+"]");
                }
            }
            System.out.println();
            if(i < matrix.length-1) {
                System.out.print("[");
            }
        }
        System.out.println("_____________________");
    }
    
    //I am initially making this to organize small matrices to line up to sum above
    //Number of digits in an integer
    /*public void numberDigits() {
        
        while (matrixNum != 0) {
            matrixNum /= 10;
            matrixDigitCount++;
        }
        
    }*/
    // If less than maxDigit, then put 0s in front of it accordingly?
    
    // Investigation of peculiarity of repeats:
    // (finding patterns in repeats)
    //Basic Graph as increases, are we converging to something in 2D? 3D?
    //ALSO make a graph to see if its statistically like a bell in repeated number of value (index - x) versus recursiveRepeats (y)? 
    //***ADDITIONALLY consider connections less than epsilon in 3D to cluster? Take a statistical approach.***
    
    // Right now, the matrix offers this sort of data:
    // We can interpret the results as a 3d Grid (x,y,z),(what number n is repeated r times,length of repeat of n,how many repeats of r)
    // it would be interesting to table the data as a 4D data set, and also keep track of WHERE the repeats occur, perhaps.
    
    // How can we convert this to python to graph....?
    // How can we convert this to mathematica to epsilon distance cluster....?
    
    // There are a good number of "non-points", I'm wondering how methods ajudicate for this
    // When I cluster, I should consider introducing in two forms:
    // Ask for it to cluster distances among points WITH repeats
    // Ask for it to cluster distances among points WITHOUT repeats
    
    // Last bit of thoughts: We can take this data in two forms, in regularity, and proceed with typical numerical investigation
    // or be strange and consider it like a statistical data set, clustering, describing, MAYBE even comparing columns...
}