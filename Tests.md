## Test Cases:
_____
### View Page

**Test 1**: Close The Window

** Should work regardless of whether user is a student or tutor
1) User closes the window with the option of List or Search

Expected result: Client side of application ends. Server continues to keep running.

Test status: Passed

**Test 2**: List Tutor

** Assume that the user is a student
1) User selects the "List Tutor" button 
2) User receives a dropdown menu of all the unblocked tutors 
3) User selects a tutor 
4) User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 3**: List Student

**Assume that the user is a tutor
1) User selects the "List Student" button 
2) User sees a dropdown menu of all the unblocked students 
3) User selects a student 
4) User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 4**: List Tutor if All Tutors are Blocked OR No Tutors Have Been Input Prior

** Assume that the user is a student
1) User selects the "List Tutor" button 
2) User receives a JOptionPane message: “Please add at least one tutor before using the program”. 
3) User selects “Ok” button

Expected result: Client side of program ends. Server continues to keep running.

Test status: Passed

**Test 5**: List Students if All Students are Blocked OR No Students Have Been Input Prior

** Assume that the user is a tutor
1) User selects the "List Student" button 
2) User receives a JOptionPane message: “Please add at least one student before using the program”. 
3) User selects “Ok” button

Expected result: Client side of program ends. Server continues to keep running.

Test status: Passed

**Test 6**: List Tutor then Cancel

** Assume that the user is a student
1) User selects the "List Tutor" button 
2) User sees a dropdown menu of all the unblocked tutors 
3) User selects the "cancel" button 
4) User selects the "List Tutor" button 
5) User sees a dropdown menu of all the unblocked tutors 
6) User selects a tutor 
7) User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 7**: List Student then Cancel

** Assume that the user is a tutor  
1) User selects the "List Student" button 
2) User sees  a dropdown menu of all the unblocked students
3) User selects the "cancel" button 
4) User selects the "List Student" button 
5) User sees a JOptionPane window with a dropdown menu of all the unblocked students 
6) User selects a student 
7) User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 8**: Search Tutor

** Assume that the user is a student

1. User selects the "Search Tutor" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a dropdown menu of all the unblocked tutors
7. User selects a tutor
8. User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 9**: Search Student

** Assume that the user is a tutor
1. User selects the "Search Student" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a dropdown menu of all the unblocked students
7. User selects a student
8. User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 10**: Search Tutor if All Tutors are Blocked OR No Tutors Have Been Input Prior

** Assume that the user is a student
1. User selects the "Search Tutor" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a JOptionPane message: “Please add at least one tutor before using the program”.
7. User selects “Ok” button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 11**: Search Student if All Students are Blocked OR No Tutors Have Been Input Prior

** Assume that the user is a tutor

1. User selects the "Search Student" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a JOptionPane message: “Please add at least one student before using the program”.
7. User selects “Ok” button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 12**: Search Tutor But No Users Found and User Tries Again

** Assume that the user is a student

1. User selects the "Search Tutor" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a JOptionPane message: “User not found! Would you like to try again?”.
7. User selects "yes" button
8. User sees the page with a search textbox (default text being: exampleUser)
9. User selects the search textbox
10. User enters a different desired keyword
11. User selects "search" button
12. User receives a dropdown menu of all the unblocked tutors
13. User selects a tutor
14. User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 13**: Search Student But No Students Found and User Tries Again

** Assume that the user is a tutor

1. User selects the "Search Student" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a JOptionPane message: “User not found! Would you like to try again?”.
7. User selects "yes" button
8. User sees the page with a search textbox (default text being: exampleUser)
9. User selects the search textbox
10. User enters a different desired keyword
11. User selects "search" button
12. User receives a dropdown menu of all the unblocked students
13. User selects a student
14. User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 14**: Search Tutor But No Tutors Found and User Does Not Try Again

** Assume that the user is a student

1. User selects the "Search Tutor" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a JOptionPane message: “User not found! Would you like to try again?”.
7. User selects "no" button

Expected result: Client side of program ends. Server continues to keep running.
Test status: Passed

**Test 15**: Search Student But No Students Found and User Does Not Try Again

** Assume that the user is a tutor

1. User selects the "Search Student" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a JOptionPane message: “User not found! Would you like to try again?”.
7. User selects "no" button


Expected result: Client side of program ends. Server continues to keep running.

Test status: Passed

**Test 16**: Search Tutor Then User Closes The Window

** Assume that the user is a student

1. User selects the "Search Tutor" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the red X button to close the window

Expected result: Client side of program ends. Server continues to keep running.

Test status: Passed

**Test 17**: Search Student Then User Closes The Window

** Assume that the user is a tutor

1. User selects the "Search Student" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the red X button to close the window

Expected result: Client side of program ends. Server continues to keep running.

Test status: Passed

**Test 18**: Search Tutor Then User Closes The Dropdown Menu OR Selects Cancel

** Assume that the user is a student

1. User selects the "Search Tutor" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a dropdown menu of all the unblocked tutors
7. User closes the dropdown menu OR selects the "cancel" button 
8. User sees the page with a search textbox (default text being: exampleUser)
9. User selects the search textbox
10. User enters a different desired keyword
11. User selects "search" button
12. User selects a tutor  
13. User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed

**Test 19**: Search Student Then User Closes The Dropdown Menu OR Selects Cancel

** Assume that the user is a tutor

1. User selects the "Search Student" button
2. User sees a new page with a search textbox (default text being: exampleUser)
3. User selects the search textbox
4. User enters desired keyword
5. User selects "search" button
6. User receives a dropdown menu of all the unblocked students
7. User closes the dropdown menu OR selects the "cancel" button
8. User sees the page with a search textbox (default text being: exampleUser)
9. User selects the search textbox
10. User enters a different desired keyword
11. User selects "search" button
12. User selects a student
13. User selects the "Ok" button

Expected result: Application loads a page with several options related to messaging.

Test status: Passed
_____

# Options Page Test Cases

## Test Cases for the Options Class - General Features

**Test 1: View Conversation**
- **Should work regardless of user type**
- **Steps**:
  1. User clicks the "View Conversation" button.
- **Expected Result**: 
  - The conversation history with the selected user is displayed on a new screen with a panel of buttons on the bottom related to messaging.
- **Test Status**: Passed

**Test 2: Export Conversation to CSV**
- **Should work when a conversation is selected**
- **Steps**:
  1. User selects a conversation.
  2. User clicks the "Export to CSV" button.
  3. User is prompted with a dialog box that asks which file path they want to export the file to.
- **Expected Result**: 
  - The selected conversation is exported and saved as a CSV file with the given path.
- **Test Status**: Passed

**Test 3: Block User**
- **Steps**:
  2. User clicks the "Block User" button.
- **Expected Result**: 
  - The selected user is added to the user's block list.
  - The user can no longer communicate with the blocked user, and the blocked user can no longer communicate with the user
- **Test Status**: Passed

**Test 4: Go Back to View Page**
- **Should return user to the View page**
- **Steps**:
  1. User clicks the "Back" button on the Options page.
- **Expected Result**: 
  - The user is redirected back to the View page.
- **Test Status**: Passed

## Test Cases for the Options Class - View Conversation Screen

**Test 1: Send Message**
- **Should send a typed message**
- **Steps**:
  1. User types a message in the message input area.
  2. User clicks the "Send Message" button.
- **Expected Result**: 
  - The typed message is sent and appears in the conversation text area.
- **Test Status**: Passed

**Test 2: Edit Message**
- **Should allow editing of a sent message**
- **Steps**:
  1. User clicks the "Edit Message" button
  2. User sees a dialog box prompting them for the message they want to edit in a dropdown box that contains all of their previously sent messages.
  3. User selects a message and then presses OK.
  4. User sees a dialog box prompting them to enter the new message in a text box.
  5. User enters the new message and presses OK.
- **Expected Result**: 
  - The selected message is updated with the new content in the conversation text area.
- **Test Status**: Passed

**Test 3: Delete Message**
- **Should delete a selected message**
- **Steps**:
  1. User clicks the "Delete Message" button
  2. User sees a dialog box prompting them for the message they want to delete in a dropdown box that contains all of their previously sent messages.
  3. User selects a message and then presses OK.
- **Expected Result**: 
  - The selected message is removed from the conversation text area.
- **Test Status**: Passed

**Test 4: Import Message**
- **Should import messages from a selected file**
- **Steps**:
  1. User clicks the "Import Message" button.
  2. User sees a dialog box that prompts them for the name of the file they want to import from.
- **Expected Result**: 
  - The content of the selected files are sent as a message and the conversation area is updated with the sent message.
  - If the file does not exist and error box appears that tells the User that there was an error importing the file.
- **Test Status**: Passed

**Test 5: Refresh Conversation**
- **Should refresh the conversation text area**
- **Steps**:
  1. User clicks the "Refresh" button.
- **Expected Result**: 
  - The conversation text area is refreshed to display the latest messages.
- **Test Status**: Passed

**Test 6: Go Back**
- **Should return user to the previous screen**
- **Steps**:
  1. User clicks the "Go Back" button.
- **Expected Result**: 
  - User exits the conversation screen and returns to the previous screen.
- **Test Status**: Passed


