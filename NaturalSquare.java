/**
 * This programm finds and writes all the natural square number in a list of integers
 * from user selected file to a user created file in the same directory
 * It uses a recursive algorithm to find the natural square numbers
 * Referred oracle.com for JFileChooser and FileWriter
 * @author Saajine Sathappan
 */
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.lang.StrictMath;

/**
 * Creates two array list to read and write integers Uses JFileChooser to select
 * file and FileWriter to write to a file
 */
public class NaturalSquare {

    public static void main(String[] args) {
        ArrayList<Double> givenNumbers = new ArrayList<>();
        ArrayList<Integer> squareNumbers = new ArrayList<>();
        inputFile(givenNumbers, squareNumbers);
    }

    /**
     * graphically prompts the user for an input file
     * 
     * @param givenNumbers  ArrayList of type double for numbers read from the file
     * @param squareNumbers ArrayList of type int for natural square numbers
     */
    public static void inputFile(ArrayList<Double> givenNumbers, ArrayList<Integer> squareNumbers) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            readUsingScanner(selectedFile, givenNumbers, squareNumbers);
            outputFile(selectedFile, squareNumbers);
        } else {
            System.out.println("File selection failed");
        }

    }

    /**
     * reads amd writes the number as double from user selected file into an
     * ArrayList if the a line contains input other than int, it skips the current
     * line.
     * 
     * @param fileName      user selected file
     * @param givenNumbers  ArrayList of type double for numbers read from the file
     * @param squareNumbers ArrayList of type int for natural square numbers
     */
    private static void readUsingScanner(File fileName, ArrayList<Double> givenNumbers,
            ArrayList<Integer> squareNumbers) {
        try {
            Scanner scanner = new Scanner(fileName);
            System.out.println("Reading text file using Scanner" + "\n");
            while (scanner.hasNext()) {
                // process each line
                if (scanner.hasNextDouble()) {
                    givenNumbers.add(scanner.nextDouble());
                }
                // skips current line if input contains other than int or double
                else {
                    scanner.nextLine();
                }

            }
            scanner.close();
        } catch (IOException exc) {
            System.out.println("Failed to read the selected file" + "\n");
        } catch (InputMismatchException e) {
            System.out.println(
                    "Attention! Selected file has an input mismatch. Input file must contain 1 integer or double per line"
                            + "\n");
        } catch (NoSuchElementException ex) {
            System.out.println("The input is exhausted" + "\n");
        } catch (IllegalStateException iex) {
            System.out.println("Illegal state exception. Scanner is closed" + "\n");
        }

        calculate(givenNumbers, squareNumbers, 0);
    }

    // In this method, it takes O(1) or constant time to calculate the sqrt and
    // constant time to check if the number is a natural square number.
    // O(1)is the estimated complexity for the algorithm to determine if one integer
    // is a natural square.
    // This is recursively done for n integers in the arraylist.
    // For n integers, the complexity of determining the natural square of n integers
    // will be O(n).

    /**
     * uses a recursive algorithm to find and write all the natural square numbers
     * from the given arraylist of integers to another arraylist *
     * 
     * @param givenNumbers  ArrayList of double from given file
     * @param squareNumbers ArrayList of integers for the output file
     * @param position      number position in the ArrayList givenNumbers
     */
    private static void calculate(ArrayList<Double> givenNumbers, ArrayList<Integer> squareNumbers, int position) {
        if (position >= givenNumbers.size()) {
            return;
        }
        double n = givenNumbers.get(position); // accessing a value with an array index gives complexity of O(1) -
                                               // Constant Time

        double j = StrictMath.sqrt(n); // it takes constant time O(1) to calculate the sqrt using the sqrt function

        // considering 0 to be a natural square number
        if ((n % j == 0.0) && (n / j == j) || (j == 0.0)) { // this is O(1) to check if the number is a natural square
            squareNumbers.add((int) n);
        }

        position++;
        calculate(givenNumbers, squareNumbers, position);
    }

    /**
     * creates an output file in the same directory as input file
     * 
     * @param selectedFile  input file selected by the user
     * @param squareNumbers ArrayList of type int for natural square numbers
     */
    public static void outputFile(File selectedFile, ArrayList<Integer> squareNumbers) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the desired name of your output file: ");
        String fileName = scan.nextLine();
        fileName = fileName + ".txt";
        File outputFile = new File(selectedFile.getParent(), fileName);
        try {
            boolean fileCheck = outputFile.createNewFile();
            if (fileCheck) {
                System.out.println("Output file has been created successfully");
            } else {
                System.out.println("Output file already present at the specified location");
            }
            scan.close();
        } catch (IOException ex) {
            System.out.println("Output file cannot be created");
        }
        writeOutputFile(outputFile, squareNumbers);
    }

    /**
     * writes the natural square number to the ouput file
     * 
     * @param outputFile    file created by the user to write the output
     * @param squareNumbers ArrayList of type int for natural square numbers
     */
    public static void writeOutputFile(File outputFile, ArrayList<Integer> squareNumbers) {
        try {
            FileWriter writer = new FileWriter(outputFile);
            int len = squareNumbers.size();
            for (int i = 0; i < len; i++) {
                writer.write(squareNumbers.get(i) + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Failed to write output in the choosen file");
        }
    }
}