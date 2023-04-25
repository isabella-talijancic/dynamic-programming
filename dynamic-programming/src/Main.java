import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Main contains all necessary classes and determines ...
 * 
 * UTSA CS 3343 - Project 3
 * Spring 2023
 * @author Isabella Talijancic (juu530)
 * @author Amalia Talijancic (fwn783)
 */

public class Main {
    
    public static void main(String[] args) {
        // Load the word list from file
        Set<String> dictionary = new HashSet<String>();
        try {
            Scanner sc = new Scanner(new File("src/aliceInWonderlandDictionary.txt"));
            while (sc.hasNext()) {
                dictionary.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // Read input string from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        // Set up dynamic programming table
        List<List<String>> c = new ArrayList<>();
        for (int i = 0; i < input.length() + 1; i++) {
            c.add(new ArrayList<String>());
        }
        c.get(0).add("");
        
        // Fill in table bottom-up
        for (int i = 1; i <= input.length(); i++) {
            for (int j = i-1; j >= 0; j--) {
                if (!c.get(j).isEmpty() && dictionary.contains(input.substring(j, i))) {
                    List<String> split = new ArrayList<>(c.get(j));
                    split.add(input.substring(j, i));
                    if (c.get(i).isEmpty() || split.size() < c.get(i).size()) {
                        c.set(i, split);
                    }
                }
            }
        }
        
        // If no valid split found, output "cannot be split"
        if (c.get(input.length()).isEmpty()) {
            System.out.println(input + " cannot be split into AiW words.");
            return;
        }
        
        // Output the minimum number of splits and the resulting words
        System.out.print(input + " can be split into " + (c.get(input.length()).size()-1) + " AiW words: ");
        System.out.println(String.join(" ", c.get(input.length())));
    }
}