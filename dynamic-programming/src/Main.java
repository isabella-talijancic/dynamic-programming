import java.util.Arrays;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
        int[] c = new int[input.length() + 1];
        Arrays.fill(c, Integer.MAX_VALUE);
        c[0] = 0;
        
        // Fill in table bottom-up
        for (int i = 1; i <= input.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (c[j] != Integer.MAX_VALUE && dictionary.contains(input.substring(j, i))) {
                    c[i] = Math.min(c[i], c[j] + 1);
                }
            }
        }
        
        // If no valid split found, output "cannot be split"
        if (c[input.length()] == Integer.MAX_VALUE) {
            System.out.println(input + " cannot be split into AiW words.");
            return;
        }
        
        // Output the minimum number of splits and the resulting words
        List<String> words = new ArrayList<>();
        int i = input.length();
        while (i > 0) {
            for (int j = i-1; j >= 0; j--) {
                if (c[j] != Integer.MAX_VALUE && dictionary.contains(input.substring(j, i))) {
                    words.add(input.substring(j, i));
                    i = j;
                    break;
                }
            }
        }
        Collections.reverse(words);
        System.out.print(input + " can be split into " + c[input.length()] + " AiW words: ");
        System.out.println(String.join(" ", words));

    }
}