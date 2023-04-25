import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Main contains all necessary classes and, when 
 * given a String, can determine if it can be “split” 
 * into one or more words such that each of the words 
 * appear in the book, Alice in Wonderland. 
 * 
 * UTSA CS 3343 - Project 3
 * Spring 2023
 * @author Isabella Talijancic (juu530)
 * @author Amalia Talijancic (fwn783)
 */

public class Main {
    
    public static void main ( String [ ] args ) 
    {
    	
        //Now loading words from the dictionary
        Set < String > aiwDictionary = new HashSet < String > ( ) ;
        
        //Using try-catch block for potential exception
        try 
        {
        	
            Scanner readingInAIW = new Scanner ( new File ( "src/aliceInWonderlandDictionary.txt" ) ) ;
            
            while ( readingInAIW.hasNext ( ) ) 
            {
            	
            	aiwDictionary.add ( readingInAIW.next ( ) ) ;
           
            }
            
        } 
        
        catch ( FileNotFoundException e ) 
        {
        	
            e.printStackTrace ( ) ;
        
        }
        
        //Reading in user input String
        Scanner readingInUI = new Scanner ( System.in ) ;

        System.out.print ( "Enter a string: " ) ;
        
        String textToSplit = readingInUI.nextLine ( ) ;
        
        //Dynamic Programming table building
        List < List < String > > dynamicTable = new ArrayList < > ( ) ;
        
        for ( int i = 0; i < textToSplit.length ( ) + 1; i++ ) 
        {
        	
        	dynamicTable.add ( new ArrayList < String > ( ) ) ;
        
        }
        
        dynamicTable.get ( 0 ) .add ( "" ) ;
        
        //Establishing values inside of the dynamic table
        for ( int i = 1; i <= textToSplit.length ( ); i++ ) 
        {
        	
            for ( int j = i - 1; j >= 0; j-- ) 
            {
            	
                if ( !dynamicTable.get ( j ) .isEmpty ( ) && aiwDictionary.contains ( textToSplit.substring ( j, i ) ) ) 
                {
                	
                    List < String > split = new ArrayList < > ( dynamicTable.get ( j ) ) ;
                    
                    split.add ( textToSplit.substring ( j, i ) ) ;
                    if ( dynamicTable.get ( i ).isEmpty ( ) || split.size ( ) < dynamicTable.get ( i ).size ( ) ) 
                    {
                     
                    	dynamicTable.set ( i, split ) ;
                    
                    }
        
                }
                
            }

        }
        
        // In the case that there is no valid split, print out, "cannot be split".
        if ( dynamicTable.get ( textToSplit.length ( ) ).isEmpty ( ) ) 
        {
        	
            System.out.println ( textToSplit + " cannot be split into AiW words." ) ;
            
            return;
        
        }
        
        //Displaying the minimum amount of splits actual words
        System.out.print ( textToSplit + " can be split into " + ( dynamicTable.get ( textToSplit.length ( ) ).size ( ) - 1 ) + " AiW words: " ) ;
        
        System.out.println ( String.join ( " ", dynamicTable.get ( textToSplit.length ( ) ) ) ) ;
    
    }

}