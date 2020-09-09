/**
 * pa1.java
 *
 * @author Anna Colman
 * @author August Brigantino
 *
 * USD COMP 370: Automata
 *
 * This java program simulates the computation of a DFA
 *
 * Created 2/10/2019
 */

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class pa1 
{
	public static void main(String[] args) 
	{
		
		// Initialize variables and data structures
		int numStates = 0;
		String alph = "";
		int startState = 0;
		int currState = 0;
		String acceptStates = "";
		ArrayList<String> transitions = new ArrayList<String>();
		ArrayList<String> inputValues = new ArrayList<String>();
		String[] accepts = null;

		// Attempt to read in input file
		try
		{
			// Read input file
			if (0 < args.length) 
			{
				// Open file with specified name 
				File inputFile = new File(args[0]);

				BufferedReader bf = new BufferedReader(new FileReader(inputFile));

				// Read number of states
				numStates = Integer.parseInt(bf.readLine());

				// Read alphabet 
				alph = bf.readLine();

				// Read all transition into an ArrayList
				String holder = "";
				while((holder = bf.readLine()).length() != 1)
					transitions.add(holder);

				// Save start state 
				startState = Integer.parseInt(holder);

				// Read in accept states
				acceptStates = bf.readLine();
				accepts = acceptStates.split(" ");

				// Reads all input values into an Arraylist
				String inputLine = "";
				while((inputLine = bf.readLine()) != null) 
					inputValues.add(inputLine);
			}
		}

		// catch exception
		catch(IOException e)
		{
			System.out.println("Ya boi's been caught");
		}

		// Create arrays to handle transitions
		int[] transitionStart = new int[transitions.size()];
		int[] transitionEnd = new int[transitions.size()];
		char[] transitionInput = new char[transitions.size()];

		// Separate transition information into correct arrays 
		for(int i = 0; i < transitions.size(); i++)
		{
			int transitionInputIndex = transitions.get(i).indexOf("'") + 1;
			transitionInput[i] = transitions.get(i).charAt(transitionInputIndex);
			transitionStart[i] = Integer.parseInt(transitions.get(i).substring(0, (transitionInputIndex - 2)));
			transitionEnd[i] = Integer.parseInt(transitions.get(i).substring((transitionInputIndex + 3)));
		}

		// Run the DFA
		int flag = 0;

		// For each given string input
		for(int i = 0; i < inputValues.size(); i++) {
			currState = startState;

			// For each character in the string
			for(int j = 0; j < inputValues.get(i).length(); j++)
			{
				// Make transition
				for(int k = 0; k < transitionStart.length; k++)
				{  
					if(currState == transitionStart[k])
					{
						if(inputValues.get(i).charAt(j) == transitionInput[k])
						{
							currState = transitionEnd[k];
							break;
						}
					}
				}      
			}

			// Accept or reject the string
			flag = 0;
			for(int j = 0; j < accepts.length; j++) {
				if(currState == Integer.parseInt(accepts[j])) {
					System.out.println("Accept");
					flag = 1;
					break;
				}
			}
			if(flag == 0) {
				System.out.println("Reject");
			}
		}
	}
}
