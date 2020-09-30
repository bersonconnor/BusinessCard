import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BusinessCardParserApplication {
	/**
	 * Scanner used for the command line application
	 */
	private static Scanner sc;

	/**
	 * Runs the command line compatible Business Card Parser application.
	 * The user can either (1) manually enter business card text, (2) enter
	 * a business card text text file path, (3) enter a number that corresponds
	 * to an example, or (4) exit the application
	 */
	public static void main(String[] args) {
		printWelcome();
		
		// Initialized variables used in application loop
		sc = new Scanner(System.in);
		int optionInt = -1; 
		String option = "";
		while(true) {
			printMainMenu();
			option = sc.next(); // Get what the user inputed
			
			// If the user entered an integer that is between 1 and 3
			if (tryParseInt(option) && (optionInt = Integer.parseInt(option)) >= 1 && optionInt <= 3) {
				// Call the selected option
				optionInt = Integer.parseInt(option);
				if (optionInt == 1) {
					manualOption();
				}
				else if (optionInt == 2) {
					fileOption();
				}
				else if (optionInt == 3) {
					exampleOption();
				}
			}
			// If the user entered 4 or exit, end the application
			else if (optionInt == 4 || option.contentEquals("exit")) {
				printThankYou();
				break;
			}
			// Otherwise, the entered option is not valid
			else {
				printMainMenuError(option);
			}
		} 
		sc.close();
	}
	
	/**
	 * Runs the manual business card text option: parses 
	 * the user's input and prints out the extracted data.
	 */
	private static void manualOption() {
		// Prompt the user to manually enter the data
		printManualMenu();
		
		StringBuilder sb = new StringBuilder();
		String line = "";
		
		// While the user hasn't entered done
		while (!line.toLowerCase().trim().equals("done")) {
			// Append each line for input into parser
			line = sc.nextLine();
			sb.append(line + "\n");
		}
		
		// Get the input string from the string builder, parse 
		// the input and print the result
		String manualStr = sb.toString();
		ContactInfo result = BusinessCardParser.getContactInfo(manualStr);
		System.out.println("\n--- Parsed Result ---\n" + result);
	}

	/**
	 * Runs the business card text file option: retrieves
	 * the given file, parses it's contents and prints out
	 * the extracted data.
	 */
	private static void fileOption() {
		String fileStr = "";
		
		while(true) {
			// Prompt the user for a file name
			printFileMenu();
			
			// Get the file name and create file
			fileStr = sc.next();
			File file = new File(fileStr);
			// If the entered exit, return to main application
			if (isExit(fileStr)) {
				printExit();
				break;
			}
			// If the user enters a real file
			else if (file.exists() && !file.isDirectory()) {
				// Print the path being parsed, the contents of 
				// the file, and the parsed results
				printFileParsing(fileStr);
				ContactInfo result = BusinessCardParser.getContactInfo(file);
				System.out.println(result);
			}
			// Otherwise, it was not a valid input, so let the user know
			else {
				printFileError(fileStr);
			}
		}
	}
	
	/**
	 * Runs the example option: takes the user input as an argument 
	 * for the ExampleTester's main and runs the corresponding fixed
	 * example
	 */
	private static void exampleOption() {
		String exampleStr = "";
		while(true) {
			// Prompt the user for what example they want to run
			printExampleMenu();
			
			// Get the users input
			exampleStr = sc.next();
			
			// If the user entered exit, return to main application
			if (isExit(exampleStr)) {
				printExit();
				break;
			}
			
			int exampleInt = -1;
			// If the user entered an integer between the min and max example, run the example
			if (tryParseInt(exampleStr) && (exampleInt = Integer.parseInt(exampleStr)) >= ExampleTester.min && exampleInt <= ExampleTester.max) {
				ExampleTester.main(new String[] {exampleInt + ""});
			}
			// Otherwise, it was not a valid input, so let the user know
			else {
				printExampleError(exampleStr);
			}
		} 
	}

	// ----- AUXILLARY METHODS -----
	
	/**
	 * Returns true if the input can be parsed as an integer
	 * and false otherwise
	 * 
	 * @param input the string being parsed
	 * @return true if the input can be parsed as an integer
	 * and false otherwise
	 */
	private static boolean tryParseInt(String input) {
		try {
			Integer.parseInt(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if the input is "exit" and false otherwise
	 * 
	 * @param input the string being compared
	 * @return true if the input is "exit" and false otherwise
	 */
	private static boolean isExit(String input) {
		return input.toLowerCase().trim().equals("exit");
	}
	
	// ----- PRINTING METHODS -----
	
	/**
	 * Prints welcome message for main menu
	 */
	private static void printWelcome() {
		System.out.println("\nWelcome to the Business Card Parser!");
	}
	
	/**
	 * Prints main menu options
	 */
	private static void printMainMenu() {
		System.out.println("\nPlease enter the number corresponding to one of the following options: ");
		System.out.println("\t(1) Manually enter Business Card Text");
		System.out.println("\t(2) Provide Business Card Text as a text file");
		System.out.println("\t(3) Run Business Card Text examples");
		System.out.println("\t(4) Exit Business Card Parser");
	}
	
	/**
	 * Prints thank you message for when the user exits the application
	 */
	private static void printThankYou() {
		System.out.println("\nThank you for using the Business Card Parser! Have a good day!");
	}
	
	/**
	 * Prints an error message along with the invalid input for the main menu
	 * 
	 * @param input invalid user input
	 */
	private static void printMainMenuError(String input) {
		System.out.println("\nInvalid input: " + input + "\nPlease enter a numeric option between 1 and 4");
	}
	
	/**
	 * Prints message to tell the user that they are returning to the main menu
	 */
	private static void printExit() {
		System.out.println("\nReturning to Business Card Parser main menu");
	}
	
	/**
	 * Prints message to prompt user to manually enter the business card text
	 */
	private static void printManualMenu() {
		System.out.println("\nPlease enter the business card text line by line and done to finish entering lines: ");
	}
	
	/**
	 * Prints message to prompt user to enter a file path to a business card text file
	 */
	private static void printFileMenu() {
		System.out.print("\nPlease enter a valid file path to a business card text file or exit to return to main menu: ");
	}
	
	/**
	 * Prints an error message along with the invalid input for the file option
	 * 
	 * @param path invalid path name
	 */
	private static void printFileError(String path) {
		System.out.println("\nInvalid file path: " + path + "\nPlease enter a valid file path");
	}
	
	/**
	 * Prints the file that will be parsed and it's contents
	 * 
	 * @param path file that will be parsed
	 */
	private static void printFileParsing(String path) {
		System.out.println("\nParsing: " + path);
		StringBuilder sb = new StringBuilder();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			while ( (line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(sb.toString());
	}
	
	/**
	 * Prints message to prompt the user to enter an example number
	 */
	private static void printExampleMenu() {
		System.out.print("\nPlease enter an example number between " + ExampleTester.min + " and " + ExampleTester.max + " or exit: ");
	}
	
	/**
	 * Prints an error message along with the invalid input for the example option
	 * 
	 * @param input invalid user input
	 */
	private static void printExampleError(String input) {
		System.out.println("\nInvalid input: " + input + ".\nPlease enter a numeric option between " + ExampleTester.min + " and " + ExampleTester.max);
	}
}
