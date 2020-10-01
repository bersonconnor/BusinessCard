
public class ContactInfo {
	/**
	 * Individual's full name
	 */
	private String name;
	
	/**
	 * Individual's phone number 
	 */
	private String phoneNumber;
	
	/**
	 * Individual's email address
	 */
	private String emailAddress;

	/**
	 * Constructor for ContactInfo class. Initializes data members with 
	 * the given parameters.
	 * 
	 * @param name the individual's fill name
	 * @param phoneNumber the indivudal's phone number
	 * @param emailAddress the individual's email address
	 */
	public ContactInfo(String name, String phoneNumber, String emailAddress) {
		this.name 			= name;
		this.phoneNumber 	= phoneNumber;
		this.emailAddress 	= emailAddress;
	}
	
	/**
	 * Returns the individual's full name
	 * 
	 * @return the individual's full name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the individual's phone number 
	 * 
	 * @return the individual's phone number
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	
	/**
	 * Returns the individual's email address
	 * 
	 * @return the individual's email address
	 */
	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	/**
	 * Returns the name, phone number, and email address
	 * of this ContactInfo in string format.
	 */
	public String toString() {
		return "Name: " + name 
				+ "\nPhone: " + phoneNumber 
				+ "\nEmail: " + emailAddress;
	}
}
