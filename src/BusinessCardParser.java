import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class BusinessCardParser {
	/**
	 * An array that holds the name, phone number
	 * and email address at index 0, 1, and 2 respectively
	 */
	private static String[] data;
	
	/**
	 * The last string that matched the nameRegex, but was not 
	 * contained within the nameList
	 */
	private static String seenName;
	
	/**
	 * An exhaustive list of names
	 */
	private static ArrayList<String> nameList;
	
	/**
	 * Regular expression that represents a full name
	 */
	private static final String nameRegex = "^([A-Z][a-zA-Z-']+)((\\s[A-Z]([a-zA-Z-']+|\\.))|)\\s([A-Z][a-zA-Z-']+)$";
	
	/**
	 * Regular expression that represents many possible formats of a phone number
	 */
	private static final String phoneNumberRegex = 
			"^(Phone|Tel|Telephone|Cell|Cellphone|Cellular|Cell-phone|Cell Phone|Cell phone|Mobile|Mobile Phone|)\\s*(:|\\||)\\s*(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}(\\s*(phone|Phone|cell|Cell|))$" 
			+ "|^(Phone|Tel|Telephone|Cell|Cellphone|Cellular|Cell-phone|Cell Phone|Cell phone|Mobile|Mobile Phone|)\\s*(:|\\||)\\s*(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}(\\s*(phone|Phone|cell|Cell|))$" 
			+ "|^(Phone|Tel|Telephone|Cell|Cellphone|Cellular|Cell-phone|Cell Phone|Cell phone|Mobile|Mobile Phone|)\\s*(:|\\||)\\s*(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}(\\s*(phone|Phone|cell|Cell|))$";
	
	/**
	 * Regular expression that represents many possible formats of an email address
	 */
	private static final String emailAddressRegex = "^(Email(:|\\||)|)\\s*([\\w\\-]+\\.*[\\w\\-]+)@([\\w\\-]+)((\\.(\\w){2,})+)$";
	
	/**
	 * Regular expression that represents many possible formats of an email address strictly without a suffix
	 */
	private static final String emailAddressNoSuffixRegex = "([\\w\\-]+\\.*[\\w\\-]+)@([\\w\\-]+)((\\.(\\w){2,})+)";
	
	/**
	 * Takes the business card text as a text file, formats
	 * it as a string with each line separated by a new line 
	 * character, and then calls getContactInfo on the formatted
	 * string. 
	 * 
	 * @param file business card text file
	 * @return a ContactInfo initialized with the extracted name, 
	 * phone number, and email address as data members 
	 */
	public static ContactInfo getContactInfo(File file) {
		StringBuilder sb = new StringBuilder();
		
		BufferedReader reader;
		try {
			// Initialize the reader for the given file
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			// For every line in the file, append it to the string builder
			while ( (line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return ContactInfo by parsing the contents of the given file
		return getContactInfo(sb.toString());
	}
	
	/**
	 * Takes the given business card text as a string, extracts the name, 
	 * phone number, and email address from document, and returns a
	 * a ContactInfo object constructed with the extracted data
	 * 
	 * @param document the text from the scanned business card
	 * @return a ContactInfo initialized with the extracted name, 
	 * phone number, and email address as data members 
	 */
	public static ContactInfo getContactInfo(String document) {
		// Initialize name list
		initNameList();
		
		// Extract the data from document
		String[] data = extract(document); 
		
		// Unpack the extracted data. If the value of the 
		// is null, set it to be empty string instead.
		String name 			= data[0] == null ? "" : data[0];
		String phoneNumber 		= data[1] == null ? "" : data[1];
		String emailAddress 		= data[2] == null ? "" : data[2];
		
		// Construct and return ContactInfo 
		return new ContactInfo(name, phoneNumber, emailAddress);
	}
	
	/**
	 * Initializes a list of names that will be used later 
	 * to verify if a line is a name
	 */
	private static void initNameList() {
		// Initializes the list
		nameList = new ArrayList<String>();
		
		BufferedReader reader;
		try {
			// Initializes the reader for the names file
			reader = new BufferedReader(new FileReader("./names.txt"));
			String name = reader.readLine();
			// For every name in the file, add it to the list
			while (name != null) {
				nameList.add(name.toLowerCase());
				name = reader.readLine();
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes the given business card text document, extracts the name, 
	 * phone number and email address from document, and returns a String
	 * array of the format {name, phoneNumber, emailAddress}. If any of the
	 * data is not found, the returned array will have a null in it's 
	 * designated position instead.
	 * 
	 * @param document the text from the scanned business card
	 * @return a String array of the format {name, phoneNumber, emailAddress}
	 */
	private static String[] extract(String document) {
		// Initialize the data array that will be returned
		data = new String[3];
		
		// Split each line of the document by the newline character
		String[] lines = document.split("\n");
		
		// For every line in the document
		for(String line : lines) {
			// If we have already found all the required data, exit the loop
			if (data[0] != null && data[1] != null && data[2] != null) {
				break;
			}
			
			// Otherwise, analyze the next line
			analyze(line);
		}
		
		// If there is not a string at index 0 (the name position), 
		// and we have seen a line that matched the nameRegex
		if (data[0] == null && seenName != null) {
			// Store the name
			data[0] = seenName;
		}
		
		return data;
	}
	
	/**
	 * Analyzes the given string line. If the line matches 
	 * a name, phone number, email address, the line is formatted, 
	 * if applicable, and inserted into data at index 0, 1, or 2 
	 * respectively.
	 * 
	 * @param line the line from the business card being analyzed.
	 */
	private static void analyze(String line) {
		// If we have not found a name and the line is a name
		if (data[0] == null && isName(line)) {
			data[0] = line;
		}
		// If we have not found a phone number and the line is a phone number
		else if (data[1] == null && isPhoneNumber(line)) {
			data[1] = formatPhoneNumber(line);
		}
		// If we have not found an email address the line is an email address
		else if (data[2] == null && isEmailAddress(line)) {
			data[2] = formatEmailAddress(line);
		}
	}

	/**
	 * Returns true if the given line is a name and false otherwise
	 * 
	 * @param line the line from the business card that is being analyzed
	 * @return true if the given line is a name and false otherwise
	 */
	private static boolean isName(String line) {
		// Compile the pattern for a name
		Pattern namePattern = Pattern.compile(nameRegex, Pattern.CASE_INSENSITIVE);
		
		// Initialize the matcher that matches the name pattern to the given line
		Matcher matcher = namePattern.matcher(line);
		
		// If the matcher does not match the name pattern, it is not a name
		if(!matcher.matches()) {
			return false;
		}
		// Otherwise, if is of the format of a name, check if any part of 
		// the line is in the namesList
		else {
			// Split the full name into each part
			String[] names = line.split(" ");
			
			// If any part of the name is in the namesList, a name was found
			for(String name : names) {
				if (nameList.contains(name.toLowerCase())) {
					return true;
				}
			}
			// Otherwise, a name was not found, so store the line 
			// as a line that matched the nameRegex and return false
			seenName = line;
			return false;
		}
	}

	/**
	 * Returns true if the given line is a phone number and false otherwise
	 * 
	 * @param line the line from the business card that is being analyzed
	 * @return true if the given line is a phone number and false otherwise
	 */
	private static boolean isPhoneNumber(String line) {
		// Compile the pattern for a phone number
		Pattern phoneNumberPattern = Pattern.compile(phoneNumberRegex, Pattern.CASE_INSENSITIVE);
		
		// Initialize the matcher that matches the phone number pattern to the given line
		Matcher matcher = phoneNumberPattern.matcher(line);
		
		// Return if the line matches the phone number pattern
		return matcher.matches();
	}

	/**
	 * Returns true if the given line is an email address and false otherwise
	 * 
	 * @param line the line from the business card that is being analyzed
	 * @return true if the given line is an email address and false otherwise
	 */
	private static boolean isEmailAddress(String line) {
		// Compile the pattern for an email address
		Pattern emailAddressPattern = Pattern.compile(emailAddressRegex, Pattern.CASE_INSENSITIVE);
		
		// Initialize the matcher that compares the email address pattern to the given line
		Matcher matcher = emailAddressPattern.matcher(line);
		
		// Return if the line matches the email address pattern
		return matcher.matches();
	}
	
	/**
	 * Returns the given phone number as a sequence of digits
	 * 
	 * @param phoneNumber the given phone number
	 * @return the given phone number as a sequence of digits
	 */
	private static String formatPhoneNumber(String phoneNumber) {
		String result = "";
		char[] phoneChars = phoneNumber.toCharArray();
		// For every character in the phone number line
		for (char ch : phoneChars) {
			// If the character is a digit, append it to the result
			if (Character.isDigit(ch))
				result += ch;
		}
		return result;
	}
	
	/**
	 * Returns the given email address without any suffix
	 * 
	 * @param phoneNumber the given phone number
	 * @return the given phone number as a sequence of digits
	 */
	/**
	 * Returns the given email address without any suffix
	 * 
	 * @param emailAddress the given email address
	 * @return the given email address without any suffix
	 */
	private static String formatEmailAddress(String emailAddress) {
		// Compile the pattern for an email address without a suffix
		Pattern emailAddressPattern = Pattern.compile(emailAddressNoSuffixRegex, Pattern.CASE_INSENSITIVE);
		
		// Create a matcher that matches the email address pattern to the given email address
		Matcher matcher = emailAddressPattern.matcher(emailAddress);
		
		// Find the email address and save it's start and end indices
		matcher.find();
		int start = matcher.start();
		int end = matcher.end();
		
		// Return just the email address
		return emailAddress.substring(start, end);
	}
}
