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
