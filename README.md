 # Project 4 - The TutorFinder 

### Description: 

The TutorFinder is a marketplace message system, where students and tutors can message

### Submissions: 

Sam Balakhanei - Submitted Vocareum Workspace

Niha Raj - Submitted Project Report 

### Instructions: 

### User.java
Description:

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
Description: 

| Methods                 | Return Type        | Description                                                                                                                                                                                                                                                                       |
|-------------------------|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main                    | void               | // hardest one - do  last This method goes handles all of the communication with the user. It prompts them with three options: <br/>(1) Log in <br/>(2) Sign up <br/>(3) Exit<br/> Once the user                                                                                  |
| userExists(User user)   | boolean            | This method confirms whether or not the user in the argument exists by traversing through a file - accountDetails.txt - by calling the method getUser().                                                                                                                          |
| validateUser(User user) | boolean            | This method checks whether or not the user in the argument exists and whether the entered password matches the password on file by traversing through the text file: accountDetails.txt.                                                                                          |
| getUser()               | ArrayList<String>  | This method traverses through the file - accountDetails.txt - and appends all of the users on the file onto a string ArrayList, which it then returns.                                                                                                                            |
 | createUser(User user)     | void               | This method takes in a User and checks if their credentials to see if they already exist on accountDetails.txt. If the user exists already, it will return an error message: "User exists!". If the user does not already exist, it adds their credentials to accountDetails.txt. |


### View.java 
Description: 

| Methods                                         | Return Type | Description                                                                                                                            |
|-------------------------------------------------|-------------|----------------------------------------------------------------------------------------------------------------------------------------|
| findTutor(String userName, User userTerminal)   | void           | This method takes in a wh                                                                                                              |
| findStudent(String userName, User userTerminal) | void        | This method confirms whether or not a user exists by traversing through a file - accountDetails.txt - by calling the method getUser(). | |

### Options.java 
Description: 


#### Fields: 
| Field Name            | Field Type        | Access Modifier | Description                                                                 |
|-----------------------|-------------------|-----------------|-----------------------------------------------------------------------------|
| userTerminal          | User              | private         | Indicates the User that is currently using the program                      |
| userSelected          | User              | private         | Indicates the User that is selected to converse with                        |
| blockedList           | ArrayList<String> | private static  | Holds a group of Users that have been blocked by the userTerminal           |
| senderConvoFileName   | String            | private         | Indicates the name of the file that holds the conversation for the sender   |
| receiverConvoFileName | String            | private         | Indicates the name of the file that holds the conversation for the receiver |

#### Constuctors:
| Access Modifier | Constructor Name | Parameters                                           | Description                                                                                                                |
|-----------------|------------------|------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| public          | Options          | - User **userTerminal**<br/> - User **userSelected** | Constructs a newly allocated Options object and instantiates the fields username and password to the specified parameters. |


#### Methods: 

| Method Name     | Parameters                                                                                                 | Return Type        | Access Modifiers | Description                                                                                                                                                                                                                                                                                                                                                                                         |
|-----------------|------------------------------------------------------------------------------------------------------------|--------------------|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| getBlocked      |                                                                                                            | ArrayList <String? | public           | Returns all of userTerminal's blocked Users in a String ArrayList.                                                                                                                                                                                                                                                                                                                                  |
| getConversation |                                                                                                            | String             | public           | Checks if the userTerminal and userSelected have had a conversation prior. Updates conversation on userSelected's side to match userTerminal's side. Then, it returns any conversations between the userSelected and userTerminal.                                                                                                                                                                  |
| export          | - String **senderName**<br/> - String **recipientName**<br/> - String **fileName**<br/> - File **csvFile** | void               | public           | Exports the conversation between userTerminal and userSelected as a csv file. <br/> Format:                                                                                                                                                                                                                                                                                                         |
| sendMessage     | - String **message**                                                                                       | void               | public           | Adds the message argument on both the file with senderConvoFileName and the file with receiverConvoFileName.                                                                                                                                                                                                                                                                                        | 
| editMessage     | - String **message**<br/> - String **newMessage**                                                          | void               | public           | Replaces the message argument with the newMessage argument on both the file with senderConvoFileName and the file with receiverConvoFileName.                                                                                                                                                                                                                                                       |
| deleteMessage   | - String **message**                                                                                       | void               | public           | Deletes the message argument on both the file with senderConvoFileName and the file with receiverConvoFileName                                                                                                                                                                                                                                                                                      |
| displayMessages |                                                                                                            | void               | public           | Traverses through the file with senderConvoFileName and displays all conversations between userTerminal and userSelected. <br/> Example Format:<br/>(2023-11-13 00:44:12) Student-student1: Hi. I'm Bob, and I'm looking to get help for Computer Science.<br/>(2023-11-13 00:49:53) Tutor-tutor1: Got it. I have sessions during Monday, Wednesdays, and Friday. Which one would you like to book? |
| findMessage     | - int **index**                                                                                            | void               | public           | Traverses through the file with senderConvoFileName and finds the line containing the message argument.                                                                                                                                                                                                                                                                                             | 
| import          | - String **filename**                                                                                      | void               | public           | Reads message from the given filename and returns that.                                                                                                                                                                                                                                                                                                                                             |



