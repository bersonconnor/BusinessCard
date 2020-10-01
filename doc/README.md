# Table of Contents
- [Implementation Choices](#ImplementChoice)
  - [Solution Approach](##Approach)
  - [Identification](#Identification)
  	- [Identifying Phone Number and Email Address](###PhoneEmail)
  	- [Identifying Name](###Name)
- [Testing](#Testing)

# Implementation Choices<a name="ImplementChoice"></a>
## Solution Approach<a name="Approach"></a>
Before I began programming, I considered two possible approaches to this problem:
- **Point System**: Each line of a given business card text would be awarded points for it's likeness to a name, a phone number, and a email address. The line that had the highest number of points for name, phone number, and email address would each be selected for the respective category as output. 
  - **Advantages**: If the business card scanning application decided to also grab other parts of the card as part of the contact information, the new parts could be easily integrated into the point system after determining how to evaluate likeness of them. The application could also be extremely accurate in identifying what type of data each line is.
  - **Disadvantages**: This approach requires each line to be evaluated as a possible candidate for all data being collected, which could lead to a slow application. Furthermore, even though the application could be very precise, the fine tuning for the point system would be complex and would need to be reconfigured if it was decided later that other additional parts of a business card would also be parsed.
- **Single Pass Identification**: Each line of a given business card text would be viewed once. If it matches a name, phone number, or email address, it would be selected for the respective category as output. Even if another line after the selected line also would identify as the same category, it will not be considered or selected for that category. 
  - **Advantages**: This approach has obvious speed benefits as it evaluates the text during a single pass. Furthermore, once the methods for identificaiton are determined, even if it is decided later that other additional parts of a business card would also be parsed, they do not need to be adjusted. 
  - **Disadvantages**: There is a higher chance of misidentification if the methods for identification are either too restrictive or not restrictive enough. 

I chose to use the **Single Pass Identification** approach as the advantages are very applicable to the context of a consumer application as well as for limiting future maintence. Additionally, the disadvantages could be address during the development of the application. 
## Identification<a name="Identification"></a>
In this section, I outline the process of identifying names, phone numbers and email addresses. The choices for how they would be identified was informed by referencing several business cards from diverse backgrounds. The many possible formats for each category being considered was taken into account accordingly. The relevant code is located [here](https://github.com/bersonconnor/BusinessCard/edit/master/src/BusinessCardParser.java).
### Identifying Phone Number and Email Address<a name="PhoneEmail"></a>
Since phone numbers and email addresses have so many possible yet standardized formats, I used a regular expression to identify them. 
- **Phone Number**: I found a comprehensive regular expression for phone numbers and modified it for the context of a business card. I started with the regular expression found [here](https://www.baeldung.com/java-regex-validate-phone-numbers) and modified it as follows: 
```java
String phoneNumberRegex = 
	"^(Phone|Tel|Telephone|Cell|Cellphone|Cellular|Cell-phone|Cell Phone|Cell phone|Mobile|Mobile Phone|)\\s*(:|\\||)\\s*"
	+"((\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
	+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
	+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2})$"
	+ "(\\s*(phone|Phone|cell|Cell|))$";
	
```
Each line of the regular expression represents an optional label followed by a possible format for a phone number and ending with an optional label. 

- **Email Address**: Similarly, after researching what characters are typically allowed in an emai address, I wrote a regular expression to identify them as follows: 
```java
String emailAddressRegex = "^(Email(:|\\||)|)\\s*([\\w\\-]+\\.*[\\w\\-]+)@([\\w\\-]+)((\\.(\\w){2,})+)$";

```
This regular expression represents an optional _Email_ label followed by an email address.
### Identifying Names<a name="Name"></a>
Names are not as standardized as phone numbers and email addresses, so identifying them required a more complex solution. I implemented both of the following approaches and decided to use the latter: 
- **Natural Language Processing (NLP)**: NLP is a field concerned with analyzing human language with a computer. My NLP implemention used [Stanford's Named Entity Recognizer (NER)](https://nlp.stanford.edu/software/CRF-NER.html). In this implementation, I would feed the NER a line of a given business card. If any of the words were identified as `/PERSON`, then that line was be stored as the name of that card. The issue with the implementation came from 2 limitations of NLP. Generally, NLP uses context such as grammar to identify a given word. Since a business card is made of fragments, there is no context. As a result, though it was rare, misidentifications occurred. Additionally, NLP is not quick process, so using NER slowed the performance of my implementation significantly. 
- **Regular Expression Matching and List Existence**: In this implementation, each line of a given business card is matched to the following regular expression: 
```java
String nameRegex = "^([A-Z][a-zA-Z-']+)((\\s[A-Z]([a-zA-Z-']+|\\.))|)\\s([A-Z][a-zA-Z-']+)$";
```
This regular expression represents a first name followed by an optional middle name or initial and ended by a last name. Additionally, if a line matches the regular expression, then each word in the line is checked for containment in in this [list of names](https://www.usna.edu/Users/cs/roche/courses/s15si335/proj1/files.php%3Ff=names.txt.html) that contains around 18,000 names and is generally organized by commonality in decreasing order. The following algorithm is used when identifying a name: 
``` PROGRAM
def find_name():
	possible_name = ""
	for each line in the business card:
		if the line matches the above regular expression
			for each word in the line
				if the word is contained in the name list
					return name
			if we have not found a possible_name
				possible_name = line
	return possible_name
	
```
The algorithm takes into account that not all names are in the list by returning a line that matches the regular expression but is not contained in the list if a line that does not satisfy both conditions is not found. 

# Testing<a name="Testing"></a>
Testing of the application was completed by manually running odd/difficult examples, the three provided examples, and six more real business card examples. The provided examples and real business card examples can be found in text files [here](https://github.com/bersonconnor/BusinessCard/edit/master/src/example). 
