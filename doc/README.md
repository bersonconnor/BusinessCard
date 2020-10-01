# Table of Contents
- [Implementation Choices](#ImplementChoice)
  - [Solution Approach](##Approach)
  - [Identifying Phone Number and Email Address](##PhoneEmail)
  - [Identifying Name](##Name)
- [Referenced Resources](#Resources)

# Implementation Choices
## Solution Approach
Before I began programming, I considered two possible approaches to this problem:
- **Point System**: Each line of a given business card text would be awarded points for it's likeness to a name, a phone number, and a email address. The line that had the highest number of points for name, phone number, and email address would each be selected for the respective category as output. 
  - **Advantages**: If the business card scanning application decided to also grab other parts of the card as part of the contact information, the new parts could be easily integrated into the point system after determining how to evaluate likeness of them. The application could also be extremely accurate in identifying what type of data each line is.
  - **Disadvantages**: This approach requires each line to be evaluated as a possible candidate for all data being collected, which could lead to a slow application. Furthermore, even though the application could be very precise, the fine tuning for the point system would be complex and would need to be reconfigured if it was decided later that other additional parts of a business card would also be parsed.
- **Single Pass Identification**: Each line of a given business card text would be viewed once. If it matches a name, phone number, or email address, it would be selected for the respective category as output. Even if another line after the selected line also would identify as the same category, it will not be considered or selected for that category. 
  - **Advantages**: This approach has obvious speed benefits as it evaluates the text during a single pass. Furthermore, once the methods for identificaiton are determined, even if it is decided later that other additional parts of a business card would also be parsed, they do not need to be adjusted. 
  - **Disadvantages**: There is a higher chance of misidentification if the methods for identification are either too restrictive or not restrictive enough. 

I chose to use the **Single Pass Identification** approach as the advantages are very applicable to the context of a consumer application as well as for limiting future maintence. Additionally, the disadvantages could be address during the development of the application. 
## Identifying Phone Number and Email Address
## Identifying Name
# Referenced Resources
