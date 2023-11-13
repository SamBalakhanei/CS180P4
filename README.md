 # Project 4 - The TutorFinder 

### Description: 

The TutorFinder is a marketplace message system, where students and tutors can message

### Submissions: 

Sam Balakhanei - Submitted Vocareum Workspace

Niha Raj - Submitted Project Report 

### Instructions: 

### Welcome.java 
Description: 

| Methods                 | Return Type        | Description                                                                                                                                                                                      |
|-------------------------|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main                    | void               | // hardest one - do  last This method goes handles all of the communication with the user. It prompts them with three options: <br/>(1) Log in <br/>(2) Sign up <br/>(3) Exit<br/> Once the user |
| userExists(User user)   | boolean            | This method confirms whether or not the user in the argument exists by traversing through a file - accountDetails.txt - by calling the method getUser().                                         |
| validateUser(User user) | boolean            | This method checks whether or not the user in the argument exists and whether the entered password matches the password on file by traversing through the text file: accountDetails.txt.         |
| getUser()               | ArrayList<String>  | This method traverses through the file - accountDetails.txt - and appends all of the users on the file onto a string ArrayList, which it then returns.                                           |
 | createUser(User user)     | void               | This method takes in a User and check                                                                                                                                                            |


### View.java 
Description: 

| Methods                                         | Return Type | Description                                                                                                                                                                                      |
|-------------------------------------------------|-------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| findTutor(String userName, User userTerminal)   | void        | // hardest one - do  last This method goes handles all of the communication with the user. It prompts them with three options: <br/>(1) Log in <br/>(2) Sign up <br/>(3) Exit<br/> Once the user |
| userExists(String userName, User userTerminal)  | void        | This method confirms whether or not a user exists by traversing through a file - accountDetails.txt - by calling the method getUser().                                                           | |

### Option.java 
Description: 

| Methods                  | Return Type       | Description                                                                                                                                                                                      |
|--------------------------|-------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main                     | void              | // hardest one - do  last This method goes handles all of the communication with the user. It prompts them with three options: <br/>(1) Log in <br/>(2) Sign up <br/>(3) Exit<br/> Once the user |
| userExists(User user)    | boolean           | This method confirms whether or not a user exists by traversing through a file - accountDetails.txt - by calling the method getUser().                                                           |
| validateUser(User user)  | boolean           | This method checks whether or not a user exists and whether the entered password matches the password on file by traversing through the text file: accountDetails.txt.                           |
| getUser()                | ArrayList<String> | This method traverses through the file - accountDetails.txt - and appends all of the users on the file onto a string ArrayList, which it then returns.                                           |
| createUser()             | void              |     
