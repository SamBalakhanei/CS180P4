 # Project 4 - The TutorFinder 

### Description: 

The TutorFinder is a marketplace message system, where students and tutors can message

### Submissions: 

Sam Balakhanei - Submitted Vocareum Workspace

Niha Raj - Submitted Project Report 

### Instructions: 

### User.java
Description: The User class holds the attributes of a User object. This class is used throughout the rest of the project.  

#### Fields:
| Field Name | Field Type | Access Modifier | Description                                                                                                                                                                      |
|------------|------------|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| username   | String     | private         | Username of the user                                                                                                                                                             |
| password   | String     | private         | Password of the user                                                                                                                                                             |
| userType   | boolean    | private         | Whether tutor or student (true - student and false - tutor)  ile - accountDetails.txt - and appends all of the users on the file onto a string ArrayList, which it then returns. |


#### Constructors:

| Access Modifier | Constructor Name | Parameters                                                                   | Description                                                                                                                       |
|-----------------|------------------|------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| public          | User             | - String **username** <br/> - String **password**                            | Constructs a newly allocated User object and instantiates the fields username and password to the specified parameters.           |
| public          | User             | - String **username**<br/> - boolean **userType**                            | Constructs a newly allocated User object and instantiates the fields username and userType to the specified parameters.           |
| public          | User             | - String **username**<br/> - String **password**<br/> - boolean **userType** | Constructs a newly allocated User object and instantiates the fields username, password, and userType to the specified parameters |                                                              

#### Methods: 
| Method Name | Return Type | Access Modifiers | Description                                     |
|-------------|-------------|------------------|-------------------------------------------------|
| getUsername | User        | public           | Returns the username of this User               |
| getPassword | User        | public           | Returns the password of this User               |
| getUserType | User        | public           | Returns whether this User is a student or tutor | 
| setUserType | void        | public           | Sets whether this User is a student or tutor    |


### Welcome.java 
Description: This class begins the communication with the user and takes care of the login and signup page. 

| Methods Name | Parameters      | Return Type       | Access Modifiers | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|--------------|-----------------|-------------------|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main         |                 | void              | public static    | Handles communication related to login or signup. Prompts the user with the following menu:<br/>(1) Log in<br/>&nbsp;&nbsp;&nbsp;&nbsp;Asks the user for their userName and password, and calls validateUser(User user) to see if they exist within the program.<br/>(2) Sign up<br/>&nbsp;&nbsp;&nbsp;&nbsp;Asks for the user to create a username and password. Calls userExists(User user) to ensure that the User does not already exists and then calls createUser(User user) to create a new User and adds them to accountDetails.txt<br/>(3)Exit<br/>&nbsp;&nbsp;&nbsp;&nbsp;Prints a farewell message and exits the program |
| userExists   | - User **user** | boolean           | public static    | This method confirms whether or not the user in the argument exists by traversing through a file - accountDetails.txt - by calling the method getUser().                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| validateUser | - User **user** | boolean           | public static    | This method checks whether or not the user in the argument exists and whether the entered password matches the password on file by traversing through the text file: accountDetails.txt.                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| getUser      |                 | ArrayList<String> | public static    | This method traverses through the file - accountDetails.txt - and appends all of the users on the file onto a string ArrayList, which it then returns.                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| createUser   | - User **user** | void              | public static    | This method takes in a User and checks if their credentials to see if they already exist on accountDetails.txt. If the user exists already, it will return an error message: "User exists!". If the user does not already exist, it adds their credentials to accountDetails.txt.                                                                                                                                                                                                                                                                                                                                                   |


### View.java 
Description: This class displays the view page and allows for the user to select who to converse with.  

| Methods     | Parameters                                         | Return Type | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|-------------|----------------------------------------------------|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| findTutor   | - String **userName**<br/> - User **userTerminal** | void        | **Note:** It is assumed that the user is a student when this method is called. <br/> Prompts the user with the following menu:<br/>(1) View list of tutors<br/>&nbsp;&nbsp;&nbsp;&nbsp;Traverses through accountDetails.txt and prints the usernames for all of the tutors, unless they have been blocked by the student. The student will then select which tutor to converse with and the program will move forward<br/>2. Search for a tutor<br/>&nbsp;&nbsp;&nbsp;&nbsp;Asks the student to search for a tutor. The method will traverse through accountDetails.txt to find any usernames with the keyword entered by the student, provided that they are not blocked. If there are results, the student can select a tutor from a produced list and continue. If there are no results, the student will be asked to search again or exit.<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search again: Will do the searching process again<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exit: Will display a farewell message and exit the program. |
| findStudent | - String **userName**<br/> - User **userTerminal** | void        | **Note:** It is assumed that the user is a tutor when this method is called. <br/> Prompts the user with the following menu:<br/>(1) View list of students<br/>&nbsp;&nbsp;&nbsp;&nbsp;Traverses through accountDetails.txt and prints the usernames for all of the students, unless they have been blocked by the tutor. The tutor will then select which student to converse with and the program will move forward<br/>2. Search for a student<br/>&nbsp;&nbsp;&nbsp;&nbsp;Asks the tutor to search for a student. The method will traverse through accountDetails.txt to find any usernames with the keyword entered by the tutor, provided that they are not blocked. If there are results, the tutor can select a student from a produced list and continue. If there are no results, the student will be asked to search again or exit.<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search again: Will do the searching process again<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exit: Will display a farewell message and exit the program. |

### Options.java 
Description: This class is where the actual messaging happens. The user is given several options related to messaging (covered in the descriptions below) including, but not limited to, sending a message, editing a message, and blocking a user. 


#### Fields: 
| Field Name            | Field Type        | Access Modifier | Description                                                                 |
|-----------------------|-------------------|-----------------|-----------------------------------------------------------------------------|
| userTerminal          | User              | private         | Indicates the User that is currently using the program                      |
| userSelected          | User              | private         | Indicates the User that is selected to converse with                        |
| blockedList           | ArrayList<String> | private static  | Holds a group of Users that have been blocked by the userTerminal           |
| senderConvoFileName   | String            | private         | Indicates the name of the file that holds the conversation for the sender   |
| receiverConvoFileName | String            | private         | Indicates the name of the file that holds the conversation for the receiver |

#### Constuctor:
| Access Modifier | Constructor Name | Parameters                                           | Description                                                                                                                                                                                                                                    |
|-----------------|------------------|------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| public          | Options          | - User **userTerminal**<br/> - User **userSelected** | Constructs a newly allocated Options object and instantiates the fields userTerminal and userSelected to the specified parameters. Also instantiates senderFileName and receiverFileName using the arguments of userTerminal and userSelected. |


#### Methods:
| Method Name     | Parameters                                                                                                 | Return Type        | Access Modifiers | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
|-----------------|------------------------------------------------------------------------------------------------------------|--------------------|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| getBlocked      |                                                                                                            | ArrayList <String? | public           | Returns all of userTerminal's blocked Users in a String ArrayList.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| getConversation |                                                                                                            | String             | public           | Checks if the userTerminal and userSelected have had a conversation prior. Updates conversation on userSelected's side to match userTerminal's side. Then, it returns any conversations between the userSelected and userTerminal.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| export          | - String **senderName**<br/> - String **recipientName**<br/> - String **fileName**<br/> - File **csvFile** | void               | public           | Exports the conversation between userTerminal and userSelected as a csv file. <br/> Format:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| sendMessage     | - String **message**                                                                                       | void               | public           | Adds the message argument on both the file with senderConvoFileName and the file with receiverConvoFileName.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | 
| editMessage     | - String **message**<br/> - String **newMessage**                                                          | void               | public           | Replaces the message argument with the newMessage argument on both the file with senderConvoFileName and the file with receiverConvoFileName.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| deleteMessage   | - String **message**                                                                                       | void               | public           | Deletes the message argument on both the file with senderConvoFileName and the file with receiverConvoFileName                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| displayMessages |                                                                                                            | void               | public           | Traverses through the file with senderConvoFileName and displays all conversations between userTerminal and userSelected. <br/> Example Format:<br/>(2023-11-13 00:44:12) Student-student1: Hi. I'm Bob, and I'm looking to get help for Computer Science.<br/>(2023-11-13 00:49:53) Tutor-tutor1: Got it. I have sessions during Monday, Wednesdays, and Friday. Which one would you like to book?                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| findMessage     | - int **index**                                                                                            | void               | public           | Traverses through the file with senderConvoFileName and finds the line containing the message argument.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | 
| import          | - String **filename**                                                                                      | void               | public           | Reads message from the given filename and returns that.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| viewMenu        |                                                                                                            | void               | public           | Handles the communication in the terminal that handles Option.java's functionalities. First, it displays a menu of options for the user. The user's response to this prompt will lead to different outcomes: <br/> (1) View Conversation<br/>&nbsp;&nbsp;&nbsp;&nbsp;Will prompt the user to select from another menu:<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1) Send Message (If selected, sendMessage(String message) will be called) <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2) Edit Message (If selected, editMessage(String message, String newMessage) will be called)<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3) Delete Message (If selected, deleteMessage(String message) will be called)<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4) Import message from text file  (If selected, import(String filename) will be called)<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(5) Go Back (If selected, the user will be taken back to the first menu)<br/>(2) Export Conversation<br/>&nbsp;&nbsp;&nbsp;&nbsp;After prompting the user for a filename to export the conversation to, export(String senderName, String recipientName, String filename, File csvFile) will be called to export the contents of the conversation.<br/>(3) Block tutor<br/>&nbsp;&nbsp;&nbsp;&nbsp;Double checks with the user that they would like to block the selected user and then adds the selected user's username to blockedList.<br/>(4) Go Back<br/>&nbsp;&nbsp;&nbsp;&nbsp;Will take the user back to their respective view page.<br/>(5) Exit<br/>&nbsp;&nbsp;&nbsp;&nbsp;Will exit the program and display a goodbye message. |


                                                                                                                                                                                                                                                                                                                                                                                                                                                 |

                   


