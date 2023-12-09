# Project 5 - The TutorFinder

### Submissions:

Sam Balakhanei - Submitted Vocareum Workspace

Niha Raj - Submitted Project Report

Niha Raj - Submitted Project Video 

### Instructions:

1) Compile and run Welcome.java.
2) When looking at the page where actual messaging occurs, make sure to widen the screen to view all of the buttons (including "Go Back", "Refresh", and "Filtering (Additional Feature)")

### User.java
Description: The User class holds the attributes of a User object. This class is used throughout the rest of the project.

#### Fields:
| Field Name | Field Type | Access Modifier | Description                                                                                                                                                                       |
|------------|------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| username   | String     | private         | Username of the user                                                                                                                                                              |
| password   | String     | private         | Password of the user                                                                                                                                                              |
| userType   | boolean    | private         | Whether tutor or student (true - student and false - tutor)  file - accountDetails.txt - and appends all of the users on the file onto a string ArrayList, which it then returns. |


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
Description: This class sets up the GUI for the login and sign up page. It also establishes the client side connection to the server. Communicates with the server to allow the user to login or sign up.  

#### Fields: 
| Field Name        | Field Type     | Access Modifier | Description                                                                                                                                  |
|-------------------|----------------|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| image             | Image          |                 | Created as the base for the rest of the Welcome.java GUI.                                                                                    |
| content           | Container      |                 | Holds the JFrame that sets up the user interface for Welcome.gui.                                                                            |
| frame             | JFrame         |                 | The JFrame that holds all of the text fields and buttons listed below. Sets up the GUI for Welcome.java.                                     |
| username          | JTextField     |                 | Established for user to enter their username                                                                                                 |
| password          | JPasswordField |                 | Established for user to enter their password                                                                                                 |
| student           | JCheckBox      |                 | Established for user to select if student or not. If the checkbox is checked, then user is student. If left unchecked, then user is a tutor. |
| loginButtonWhich  | JButton        |                 | Established for user to select if they want to login.                                                                                        |
| signUpButtonWhich | JButton        |                 | Established for user to select if they want to login.                                                                                        |
| loginButton       | JButton        |                 | Established to allow user to submit their login information.                                                                                 |
| signUpButton      | JButton        |                 | Established to allow user to submit their sign up information.                                                                               |
| socket            | Socket         | private         | Created to establish a connection between the server and the client.                                                                         |
| pw                | PrintWriter    | private         | The PrintWriter that writes information from the client to the server.                                                                       |
| pw                | BufferedReader | private         | The BufferedReader that reads client from the server to the client.                                                                          |

| Methods Name     | Parameters      | Return Type | Access Modifiers | Description                                                                                                                                                              |
|------------------|-----------------|-------------|------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main             |                 | void        | public static    | Starts the program and allows different clients to simultaneously connect to the server.                                                                                 |
| run              |                 | void        | public           | Sets up all the GUI (including the JFrame) and establishes the first screen that the user sees (prompts to select between log in or sign up).                            |
| actionPerformed  | - ActionEvent e | void        | public           | Determines what to do if different buttons are pressed by the user.                                                                                                      |
| showLoginScreen  |                 | void        | private          | Creates a GUI page where the user is able to insert their username and password to login.                                                                                |
| showSignUpScreen |                 | void        | private          | Creates a GUI page where the user is able to insert their username and password and indicate whether they are a student or tutor to sign up.                             |
| connectToServer  |                 | void        | private          | Establishes a connection between the client side and the server side by initializing the socket, br, and pw field.                                                       |
| getResponse      |                 | String      | public           | Using the br field, this method reads anything printed from the server.                                                                                                  |
| userExists       | - User **user** | boolean     | public           | Sends the user’s username and password to the server. Retrieves a boolean of whether that user exists from the server.                                                   |
| validateUser     | - User **user** | boolean     | public           | Sends the user’s username, password, and userType to the server. Retrieves a boolean of whether that user exists and the entered password matches from the server.       |
| getUserType      |                 | boolean     | public           | Sends the user’s userType to the server. Retrieves a boolean that indicates whether or not the user’s type has been successfully acquired from the server.               |
| createUser       | - User **user** | void        | public           | Sends the user’s username, password, and userType to the server. Retrieves a boolean that indicates whether or not the user has been successfully added from the server. |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ||


### View.java
Description: This class sets up the GUI for the view page. Communicates with the server to allow the user to select who they would like to converse with. 


#### Fields
| Field Name     | Field Type     | Access Modifier | Description                                                                                                                             |
|----------------|----------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| userName       | String         | private         | Marks the username of the userTerminal.                                                                                                 |
| userTerminal   | User           | private         | Indicates who the current user is (the user who is utilizing the program).                                                              |
| clientReader   | BufferedReader | private         | The BufferedReader that reads client from the server to the client.                                                                     |
| clientWriter   | PrintWriter    | private         | The PrintWriter that writes information from the client to the server.                                                                  | 
| listOrSearch   | JFrame         |                 | The JFrame that holds all of the text fields and buttons listed below. Sets up the GUI for View.java.                                   |
| searchStudent  | JButton        |                 | If userTerminal is a tutor, this button allows them to select if they would like to search for a student.                               |
| listStudent    | JButton        |                 | If the userTerminal is a tutor, this button allows them to select if they would like the program to list all of the students available. |
| searchTutor    | JButton        |                 | If userTerminal is a student, this button allows them to select if they would like to search for a tutor.                               |
| listTutor      | JButton        |                 | If the userTerminal is a student, this button allows them to select if they would like the program to list all of the tutors available. |
| searchButton   | JButton        |                 | This is the button that takes in what the user has entered into the searchField.                                                        |
| searchField    | JTextField     |                 | This textfield allows the user to enter the keyword they would like to search with.                                                     |
| actionListener | ActionListener |                 | “Listens” to the action performed by the user in the GUI and determines what to do with that action.                                    |

#### Constructors
| Access Modifier | Constructor Name | Parameters                                                                                                                                                    | Description                                                                                                                                                                                                                           |
|-----------------|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| public          | View             | - String **userName**<br/>- User **userTerminal**<br/>- BufferedReader **clientReader**<br/>- PrintWriter **clientWriter**<br/>- boolean **calledSecondTime** | Constructs a newly allocated View object and instantiates the fields userName, userTerminal, clientReader,  and clientWriter to the specified parameters. If the boolean calledSecondTime is false, then sendUserDetails() is called. |

#### Methods 
| Method Name     | Access Modifiers | Parameters                                        | Return Type | Description                                                                                                                                                                                                                                   |
|-----------------|------------------|---------------------------------------------------|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| run             | public           |                                                   | void        | Sets up the user interface page for the selection between listing and searching based on whether the user is a student or tutor. Allows for various threads.                                                                                  |
| sendUserDetails | private          |                                                   | void        | Using the PrintWriter, this method sends all the details pertaining to `userTerminal` such as username, password, and userType.                                                                                                               |
| findTutor       | public           | - String **userName**<br/>- User **userTerminal** | void        | **Note:** It is assumed that the user is a student when this method is called. This method sets up the user interface page for the selection between listing and searching. It also adds any blocked tutors to this user’s blocked ArrayList. |
| findStudent     | public           | - String **userName**<br/>- User **userTerminal** | void        | **Note:** It is assumed that the user is a tutor when this method is called. This method sets up the user interface page for the selection between listing and searching. It also adds any blocked students to this user’s blocked ArrayList. |
| display         | public           |                                                   | void        | Sets up the user interface for the search page. Relays words in the search textfield to the server.                                                                                                                                           |
| list            | public           |                                                   | void        | Communicates with the server to create a dropdown menu of all the **unblocked** tutors/students in the program. Calls options when the userTerminal selects the desired user to further program.                                              |
| search          | public           |                                                   | void        | Communicates with the server to create a dropdown menu of all the **unblocked** tutors/students that contain the keyword specified by userTerminal. Calls options when the userTerminal selects the desired user to further program.          |


### Options.java
Description: This class sets up the GUI for the options menu and the actual messaging. It also communicates with the server to orchestrate any of the messaging feature.  


#### Fields:
| Field Name            | Field Type              | Access Modifier | Description                                                                                              |
|-----------------------|-------------------------|-----------------|----------------------------------------------------------------------------------------------------------|
| frame                 | JFrame                  | private         | The JFrame that holds all of the text fields and buttons listed below. Sets up the GUI for Options.java. |
| viewButton            | JButton                 | private         | Established for the user to select to view the conversation.                                             |
| exportButton          | JButton                 | private         | Established for the user to select to export the conversation to a CSV file.                             |
| blockButton           | JButton                 | private         | Established for the user to select to block userSelected.                                                |
| backButton            | JButton                 | private         | Established for the user to select to return to the view page.                                           |
| userTerminal          | User                    | private         | The user that is currently using the program.                                                            |
| userSelected          | User                    | private         | The user that userTerminal has selected to converse with.                                                |
| blockedList           | ArrayList&lt;String&gt; | private         | An ArrayList that holds all of the users that userTerminal has blocked.                                  |
| senderConvoFileName   | String                  | private         | The name of the file that holds the sender’s (userTerminal) side of the conversation.                    |
| receiverConvoFileName | String                  | private         | The name of the file that holds the receiver’s (userSelected) side of the conversation.                  |
| bfr                   | BufferedReader          | private         | The BufferedReader that reads client data from the server to the client.                                 |
| pw                    | PrintWriter             | private         | The PrintWriter that writes client data/commands to the server.                                          |


#### Constuctor:
| Constructor Name | Access Modifier | Parameters                                                                                                   | Description                                                                                                                                                                                                                                                                                                                         |
|------------------|-----------------|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Options          | public          | - User **userTerminal**<br/> - User **userSelected**<br/> - BufferedReader **bfr**<br/> - PrintWriter **pw** | Creates a new Options object. Initializes userTerminal, userSelected, bfr, and pw fields with given arguments. Initializes filter and replacement to an empty string. Also initializes senderConvoFile to userTerminal's username_userSelected's username and receiverConvoFile to userSelected's username_userTerminal's username. |



#### Methods:
| Method Name       | Access Modifier | Return Type       | Parameters                | Description                                                                                                                                                                              |
|-------------------|-----------------|-------------------|---------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| run               |                 | void              |                           | Sets up all the GUI (including the JFrame) and establishes the options screen (View conversation, Export conversation, Block, Go Back).                                                  |
| actionPerformed   |                 | void              | - ActionEvent **e**       | Occurs every time there is a button that the user interacts with. Communicates different things to the server to perform necessary tasks and sets up further GUI screens when necessary. |
| parseConversation |                 | String            | - String **conversation** | Takes in a String, **conversation**, that is separated by commas, and splits the conversation into separate messages.                                                                    |
| parseMessages     |                 | ArrayList<String> | - String **messages**     | Takes in a string, **messages**, and separates them by an apostrophe and adds them to an ArrayList. Returns this ArrayList.                                                              |
| addBlocked        |                 | void              | - String **toBlock**      | Adds a username of a blocked user by userTerminal to the blocked ArrayList.                                                                                                              |


### Server.java

Description: Does all of the backend work. Connects to the client using a socket connection. Communicates with all of the above classes and calls methods listed below to fulfill necessary functions (starting from login/sign up to the actual messaging). 
#### Fields 
| Field Name            | Field Type | Access Modifier | Description                                                          |
|-----------------------|------------|-----------------|----------------------------------------------------------------------|
| socket                | Socket     |                 | Object to create the Socket connection between Server and the Client |
| senderConvoFileName   | String     | private         | Holds the name of the sender's file                                  |
| receiverConvoFileName | String     | private         | Holds the name of the receiver's  file                               |
| obj                   | Object     | private static  | Static Object for synchronization                                    |

#### Constuctors 
| Access Modifiers | Constructor Name | Return Type | Parameters        | Description                                                                                          |
|------------------|------------------|-------------|-------------------|------------------------------------------------------------------------------------------------------|
| public           | Server           | void        | Socket **socket** | Creates a new Server object. Initializes the socket field with the provided socket in the parameter. |

#### Methods 
| Method Names      | Access Modifiers    | Return Type | Parameters                                                                                              | Description                                                                                                                                                                                                                         |
|-------------------|---------------------|-------------|---------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| checkUserExists   | public              | void        | - String **username**<br/>- String **password**                                                         | Checks if users exist in accountDetails.txt given a username (not case sensitive). Returns boolean of true if they exist.                                                                                                           |
| getUsers          | public              | void        |                                                                                                         | Returns an ArrayList of all users in accountDetails.txt.                                                                                                                                                                            |
| retrieveUserType  | public              | void        | - String **username**                                                                                   | Returns a boolean of whether or not the given user is a student or a tutor.                                                                                                                                                         |
| checkValidateUser | public              | void        | - User **user**                                                                                         | Checks if users exist in accountDetails.txt and if the password is correct (case sensitive). Returns boolean of true if validated.                                                                                                  |
| createUser        | public              | void        | - User **user**                                                                                         | Given User objects, writes their information to accountDetails.txt.                                                                                                                                                                 |
| list              | public              | void        | - String **userName**<br/>- User **userTerminal**                                                       | Given the username of the user and the object representing the user using the program, finds a list of people that they are able to communicate with. Returns a string that is a list of usernames.                                 |
| search            | public              | void        | - String **userName**<br/>- User **userTerminal**<br/>- String **comparisonName**                       | When the user makes a search query, returns a string which is a list of usernames of people they may communicate with.                                                                                                              |
| getConversations  | public              | void        | - String **senderConvoFileName**<br/>- String **receiverConvoFileName**                                 | If one conversation file exists but not the other, method will copy the existing file over. Returns a string containing file contents.                                                                                              |
| export            | public              | void        | - String **senderName**<br/>- String **recipientName**<br/>- String **fileName**<br/>- File **csvFile** | Exports the file as a .csv.                                                                                                                                                                                                         |
| sendMessage       | public              | void        | - String **message**<br/>- User **userTerminal**                                                        | Writes messages to both sender and receiver's files.                                                                                                                                                                                |
| editMessages      | public              | void        | - String **message**<br/>- String **newMessage**<br/>- User **userTerminal**                            | Edits existing messages with new messages and adds changes to both sender and receiver's files.                                                                                                                                     |
| deleteMessages    | public              | void        | - String **message**                                                                                    | Allows the user to delete their own messages and only updates changes to the sender's conversation file.                                                                                                                            |
| displayMessages   | public              | void        | - User **userTerminal**                                                                                 | Returns a string containing the entire conversation from the user's POV.                                                                                                                                                            |
| filterMessage     | public synchronized | boolean     | - String **message**<br/>- String **other**                                                             | Takes in the message to filter and the message to replace it with, updates the sender's file accordingly. Returns false if no messages were filtered, which occurs when the sender file does not contain the phrase to be filtered. |
| main              | public static       | void        | - String[] **args**                                                                                     | Used to start the server and help it connect with the client.                                                                                                                                                                       |
| run               | public              | void        |                                                                                                         | The BufferedReader and PrintWriter are created, establishing a proper connection with the client side. This method contains all of the server execution. Takes any commands from the client side and calls the necessary methods.   |
