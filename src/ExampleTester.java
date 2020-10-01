

public class ExampleTester {
	// Constants used for printing
	private static final String name 	= "Name";
	private static final String phone 	= "Phone";
	private static final String email	= "Email";
	
	// Constants for example numbers
	/**
	 * Constant defined as the minimum example number
	 */
	public static final int min = 1;
	/**
	 * Constant defined as the maximum example number
	 */
	public static final int max = 9;
	
	/**
	 * Returns a string format for if the parsedResult matched the answer 
	 * 
	 * @param answer the correct value for the given business card text
	 * @param parsedResult the parsed value for the given business card text
	 * @param type the type of data that was matched
	 * @param isMatch true if the two strings are equal and false otherwise
	 * @return a string format for if the parsedResult matched the answer 
	 */
	private static String matchToString(String answer, String parsedResult, String type, boolean isMatch) {
		String result = type + ":\t";
		if (isMatch) {
			result += parsedResult + " = " + answer;
		}
		else {
			result += parsedResult + " != " + answer;
		}
		return result;
	}
	
	/**
	 * Tests the given document by comparing the data found by parsing the 
	 * business card text with the given answers.  
	 * 
	 * @param document the business card in text format
	 * @param exampleNumber an integer representing the example that is currently being tested
	 * @param nameAnswer the correct value of the name on the business card
	 * @param phoneAnswer the correct value of the phone number on the business card
	 * @param emailAnswer the correct value of the email address on the business card
	 */
	private static void testExample(String document, int exampleNumber, String nameAnswer, String phoneAnswer, String emailAnswer) {
		// Construct ContactInfo that has the correct answer and one that is made by the parser
		ContactInfo exampleTest 	= BusinessCardParser.getContactInfo(document);
		ContactInfo exampleAnswer 	= new ContactInfo(nameAnswer, phoneAnswer, emailAnswer);

		// Test to see if the parsed data matches the answer
		boolean isCorrectName 		= exampleTest.getName().equals(exampleAnswer.getName());
		boolean isCorrectPhone 		= exampleTest.getPhoneNumber().equals(exampleAnswer.getPhoneNumber());
		boolean isCorrectAddress	= exampleTest.getEmailAddress().equals(exampleAnswer.getEmailAddress());

		// Print the results
		System.out.println("\n----- EXAMPLE " + exampleNumber + " -----\nBusiness Card Text: \n" + document + "\n");
		System.out.println("--- Answer ---\n" + exampleAnswer + "\n");
		System.out.println("--- Parsed Result ---\n" + exampleTest + "\n");
		System.out.println("--- Results ---\n" + ExampleTester.matchToString(exampleAnswer.getName(), exampleTest.getName(), name, isCorrectName));
		System.out.println(ExampleTester.matchToString(exampleAnswer.getPhoneNumber(), exampleTest.getPhoneNumber(), phone, isCorrectPhone));
		System.out.println(ExampleTester.matchToString(exampleAnswer.getEmailAddress(), exampleTest.getEmailAddress(), email, isCorrectAddress));
	}
	
	/**
	 * Gets the example number from args[0] and runs the
	 * corresponding example.	 
	 */
	public static void main (String[] args) {
		int exampleNumber = Integer.parseInt(args[0]);
		
		String document = "";
		String nameAnswer = "";
		String phoneAnswer = "";
		String emailAnswer = "";
		
		// If we have an invalid example number, print error
		if (exampleNumber < min || exampleNumber > max) {
			System.out.println(exampleNumber + " is not a valid example number.");
		}
		// Otherwise, we have a valid number
		else {
			// ----- EXAMPLE 1 -----
			if (exampleNumber == 1) {
				document = 
						"ASYMMETRIK LTD\n" + 
						"Mike Smith\n" + 
						"Senior Software Engineer\n" + 
						"(410)555-1234\n" + 
						"msmith@asymmetrik.com";
				
				nameAnswer 	= "Mike Smith";
				phoneAnswer	= "4105551234";
				emailAnswer = "msmith@asymmetrik.com";
			}
			// ----- EXAMPLE 2 -----
			else if (exampleNumber == 2) {
				document = 
						"Foobar Technologies\n" + 
						"Analytic Developer\n" + 
						"Lisa Haung\n" + 
						"1234 Sentry Road\n" + 
						"Columbia, MD 12345\n" + 
						"Phone: 410-555-1234\n" + 
						"Fax: 410-555-4321\n" + 
						"lisa.haung@foobartech.com";
				
				nameAnswer	= "Lisa Haung";
				phoneAnswer	= "4105551234";
				emailAnswer = "lisa.haung@foobartech.com";
			}
			// ----- EXAMPLE 3 -----
			else if (exampleNumber == 3) {
				document = 
						"Arthur Wilson\n" + 
						"Software Engineer\n" + 
						"Decision & Security Technologies\n" + 
						"ABC Technologies\n" + 
						"123 North 11th Street\n" + 
						"Suite 229\n" + 
						"Arlington, VA 22209\n" + 
						"Tel: +1 (703) 555-1259\n" + 
						"Fax: +1 (703) 555-1200\n" + 
						"awilson@abctech.com";
				
				nameAnswer	= "Arthur Wilson";
				phoneAnswer	= "17035551259";
				emailAnswer = "awilson@abctech.com";
			}
			// ----- EXAMPLE 4 -----
			else if (exampleNumber == 4) {
				document = 
						"Pep Boyz\n" + 
						"Gilbert Cozino\n" + 
						"Store Manager of Service\n" + 
						"Store #525\n" + 
						"(540)732-6600 phone\n" + 
						"SVCMNGRO525@pepboys.com";
				
				nameAnswer	= "Gilbert Cozino";
				phoneAnswer	= "5407326600";
				emailAnswer = "SVCMNGRO525@pepboys.com";
				
			}
			// ----- EXAMPLE 5 -----
			else if (exampleNumber == 5) {
				document = 
						"Neuma DOORS\n" + 
						"Jeremy Tatelbaum\n" + 
						"Sales Representative\n" + 
						"898 North Loop East, HOUSTON TX 77029\n" + 
						"Toll free: 1-866-366-7715\n" +
						"Cell Phone: (713)674-7823\n" + 
						"Fax: (713) 674-7823\n" +
						"Email: neumadoorsflorida@gmail.com\n" + 
						"www.neumadoors.com";
				
				nameAnswer	= "Jeremy Tatelbaum";
				phoneAnswer	= "7136747823";
				emailAnswer = "neumadoorsflorida@gmail.com";
				
			}
			// ----- EXAMPLE 6 -----
			else if (exampleNumber == 6) {
				document = 
						"ELEVATE WEALTH PLANNING\n" + 
						"Christopher Scalzo\n" + 
						"Financial Advisor\n" + 
						"21 Main Street, Clinton NJ 08809\n" + 
						"Fax (908) 323-2616\n" + 
						"Tel (908) 323-2585 \n" + 
						"chris@elevatewealthplanning.com\n" + 
						"www.elevatewealthplanning.com";
						
				
				nameAnswer	= "Christopher Scalzo";
				phoneAnswer	= "9083232585";
				emailAnswer = "chris@elevatewealthplanning.com";
				
			}
			// ----- EXAMPLE 7 -----
			else if (exampleNumber == 7) {
				document = 
						"Martin O'Hara\n" + 
						"Senior Practice Lead eCommerce\n" + 
						"Trifecta Technologies, Incorporated\n" + 
						"612 Hamilton Street, Suite 600\n" + 
						"Allentown, Pennsylvania 18101\n" + 
						"Office: 610.530.2814\n" + 
						"Mobile: 610.393.6026\n" + 
						"marty.ohara@trifecta.com"; 
						
				
				nameAnswer	= "Martin O'Hara";
				phoneAnswer	= "6103936026";
				emailAnswer = "marty.ohara@trifecta.com";
				
			}
			// ----- EXAMPLE 8 -----
			else if (exampleNumber == 8) {
				document = 
						"Gerent\n" + 
						"Gopi Ramineni\n" + 
						"Founder & CEO\n" + 
						"gopikrishna@gerentllc.com\n" + 
						"(919) 395-0603\n" + 
						"gerentllc.com";
				
				nameAnswer	= "Gopi Ramineni";
				phoneAnswer	= "9193950603";
				emailAnswer = "gopikrishna@gerentllc.com";
				
			}
			// ----- EXAMPLE 9 -----
			else if (exampleNumber == 9) {
				document = 
						"3 PILLAR GLOBAL\n" + 
						"LANG CAMPBELL\n" + 
						"Client Partner\n" + 
						"Mobile | 757.784.1970\n" +
						"3975 Fair Ridge Drive\n" + 
						"Suite 200 South Building\n" + 
						"Fairfax, VA 22033\n" + 
						"lang.camppbell@3pillarglobal.com";
				
				nameAnswer	= "LANG CAMPBELL";
				phoneAnswer	= "7577841970";
				emailAnswer = "lang.camppbell@3pillarglobal.com";
				
			}
			ExampleTester.testExample(document, exampleNumber, nameAnswer, phoneAnswer, emailAnswer);
		}
		
	}
}
