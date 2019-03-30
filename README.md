# MARKDOWN SUDOKU VOCABULARY

## **FOR ITERATION 1**

## 1. SUDOKU DUPLICATE IDENTIFIER

### Status: **Implemented**

### `User Story`

- As a beginner in Sudoku, I want to be able to quickly see if I have inputted duplicate words, so that I can quickly fix the mistake

### `Test Driven Development`

- When a beginner in Sudoku inputs a duplicate entry either in the row or column or the 3x3 box, the duplicates will be highlighted in red.

### `Scenario`

### **Given**: The user is filling the grid in either the listening compreshsion mode or the normal mode

![Screenshot_20190328-163633](/uploads/4c5619902e4f69c1722cc5d40968bfcd/Screenshot_20190328-163633.png)

### **When**: The user fills in a cell with a duplicate value, a value that already exists in the row or the column or the sub-cell
### **Then**: All the cells containing duplicate values and violate the row, column or cell rules get highlighted in red color 

![Screenshot_20190328-163644](/uploads/0e1bf7f350d9b20c133db72a88f96e90/Screenshot_20190328-163644.png)

## 2. BOLDED PRE-FILLED WORDS

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to differentiate between pre-filled cells and the cells that I filled out

### `Test Driven Development`

- When a user tries to change a pre-filled cell, a toast will state that the pre-filled cell cannot be changed and the pre-filled cells will be bolded

### `Scenario 1`

### **Given**: The user started the application and pressed the 'New Game' button

![Screenshot_20190328-163731](/uploads/ff95dc6050d64077879956a6b32849d2/Screenshot_20190328-163731.png)

### **When**: The user chose the preferred settings and clicked the 'START' button 
### **Then**: The application will display a sudoku grid with pre-filled values that are bolded to distinguish them from the values the user will input

![Screenshot_20190328-163742](/uploads/7ef42e736036f3cc669169fdf6d45774/Screenshot_20190328-163742.png)

### `Scenario 2`

### **Given**: The user has started a new game and a pre-filled cell has been selected 

![Screenshot_20190328-163742](/uploads/fe1123ba68027f6b8465fbae684eb6f5/Screenshot_20190328-163742.png)

### **When**: The user presses on one of the word buttons at the bottom of the screen to fill it in the cell
### **Then**: The application will not allow changing the value while displaying a toast stating 'Can't fill in a pre-filled cell'

![Screenshot_20190328-163749](/uploads/7ca36787ff97e00b5ac191c78b0c4b8c/Screenshot_20190328-163749.png)

## 3. UNIQUE PUZZLES*

### Status: **Implemented**

### `User Story`

- As a user, I want to play a unique game each time so that the games will be different each time

### `Test Driven Development`

- When a user starts a game, a unique Sudoku board will be generated each time the user starts a game

### `Scenario`

### **Given**: The user started a game by pressing 'New Game' button and a puzzle has been generated

![Screenshot_20190328-163633](/uploads/d47294893d174126f394d7294e297052/Screenshot_20190328-163633.png)

### **When**: The user presses the back button and then, presses the 'New Game' followed by 'START'
### **Then**: The application will display generate and display a different puzzle than the one the user saw before

![Screenshot_20190328-163621](/uploads/345d247a4e2469ff66add5ceeaa359ee/Screenshot_20190328-163621.png)

## 4. MAIN MENU

### Status: **Implemented**

### `User Story`

- As a user, I want to see a main menu so that I can choose when to start my game

### `Test Driven Development`

- When a user opens the app, a main menu will be presented to the user so that the user can decide when to start the game

### `Scenario`

### **Given**: The user is at the menu of applications that exist on their photo

![Screenshot_20190328-163852](/uploads/65f34e1a554c947db3aaf2981210de95/Screenshot_20190328-163852.png)

### **When**: The user clicks on the icon of the 'Sudoku Vocabulary' game
### **Then**: The application displays a main menu with three options to choose from ('New Game', 'Continue Game', 'Import Word List')

![Screenshot_20190328-163859](/uploads/23333405e8c54d1b52ac304755f4aa0b/Screenshot_20190328-163859.png)

## 5. TIMER

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to know how long it took me to solve the Sudoku puzzle, so that I can try to improve for next time and see how much time has elapsed

### `Test Driven Development`

- When a user starts a Sudoku puzzle, a timer is initiated and displayed above the Sudoku board

### `Scenario`

### **Given**: The user is at the menu 'New Game' and has chose their desired options for the game they want to play

![Screenshot_20190328-163923](/uploads/b5208969c61e845655ea7c9ac2a890e1/Screenshot_20190328-163923.png)

### **When**: The user clicks on the 'START' button
### **Then**: The application displays the game playing screen with a timer in the right upper corner

![Screenshot_20190328-163949](/uploads/4342330c194bbd0fcc7bd6362976c5db/Screenshot_20190328-163949.png)

## 6. WORD TRANSLATION HINTS

### Status: **Implemented**

### `User Story`

- As a language learner, I want to be able to peek at the translation of the pre-filled cell so that I can remind myself what the translation is

### `Test Driven Development`

- When a user presses and hold a Sudoku cell from the app on the phone, the translation of that word is momentarily displayed and a vibration will let the user know that the hint is displayed

- When user selects a Sudoku cell from the Android Studio Emulator, the users mouse pointer must slightly move within the cell for the hint to popup 

### `Scenario`

### **Given**: The user is in the game playing screen

![Screenshot_20190328-163957](/uploads/9060dc62e8213758436139e850d6ab2f/Screenshot_20190328-163957.png)

### **When**: The user holds one of the pre-filled cells
### **Then**: A toast appears on the screen with a message that includes the word selected and its translation to the other language

![Screenshot_20190328-164008](/uploads/c0a6cee91a7f8306236b406b8c08708a/Screenshot_20190328-164008.png)

## 7. FINISH SCREEN

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to know when I complete a puzzle, so that I can stop with the puzzle and move on

### `Test Driven Development`

- When a user completes the board, the user can click the "check" button and if the board is correct, a victory toast will pop up and a victory sound file will play.

- When a user does not complete the board, if the user clicks the "check" button, a toast will prompt the user that the puzzle is not completed

### `Scenario 1`

### **Given**: The user has finished solving the puzzle (filled in all the cells that were not pre-filled)
### **When**: The user clicks on the 'check' button 
### **Then**: A vicotry toast will appear on the screen accompanied by a victory sound

### `Scenario 2`

### **Given**: The user has not finished solving the puzzle (has not filled in all the cells that were not pre-filled) 
### **When**: The user clicks on the 'check' button 
### **Then**: A toast will appear on the screen telling the user that the puzzle is not completed yet

### `Scenario 3`

### **Given**: The user has finished solving the puzzle, but solved it incorrectly (filled in some or all the cells that were not prefilled with incorrect values)
### **When**: The user clicks on the 'check' button 
### **Then**: A toast will appear on the screen telling the user that the puzzle is not completed yet

## 8. APP ICON

### Status: **Implemented**

### `User Story`

- As a user, I want to see a unique icon for the app rather, so that it looks nicer than the default icon

### `Test Driven Development`

- When a user decides to open the app, a unique icon will be displayed rather than the default Android icon

## 9. Difficulty Settings

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to choose the difficulty of the Sudoku puzzle, so that I can either challenge myself or make it easier for myself.

### `Test Driven Development`

- When a user opens a board, the app will prompt the user with a seeking bar, so that the user can choose the difficulty of the board.

### `Scenario 1`

### **Given**: The user wants to solve an easy sudoku puzzle and is in the 'New Game' menu of the application

![56164140_573351086495972_743653044473823232_n](/uploads/12ba577869f0ae7cab8f280671921eb9/56164140_573351086495972_743653044473823232_n.png)

### **When**: The user presses on the level of difficulty with value '1'
### **Then**: The application will generate an easy puzzle for the user to solve 

![55576090_360747611208159_8819146534047186944_n](/uploads/210c1007c6df61d6afd05d69809bd8f6/55576090_360747611208159_8819146534047186944_n.png)

### `Scenario 2`

### **Given**: The user wants to solve a medium difficulty sudoku puzzle and is in the 'New Game' menu of the application

![55798221_430762367732051_8910319008884981760_n](/uploads/4b9744659457679aac83dc12735949a8/55798221_430762367732051_8910319008884981760_n.png)

### **When**: The user presses on the level of difficulty with value '2' or '3'
### **Then**: The application will generate a medium difficulty puzzle for the user to solve 

![54350231_2292202104165148_8077165228283920384_n](/uploads/a227e8f252d57e5a180ffe053bdc9f14/54350231_2292202104165148_8077165228283920384_n.png)

### `Scenario 3`

### **Given**: The user wants to solve a difficult sudoku puzzle and is in the 'New Game' menu of the application

![55819009_2266275993627459_7494780805546246144_n](/uploads/bd0d507e92cfbbe0eee6eaadd9b2ca5b/55819009_2266275993627459_7494780805546246144_n.png)

### **When**: The user presses on the level of difficulty with value '4'
### **Then**: The application will generate a difficult puzzle for the user to solve 

![54350231_2292202104165148_8077165228283920384_n](/uploads/3a0fb97d2916eb13e4adc0d55fa33580/54350231_2292202104165148_8077165228283920384_n.png)

# MARKDOWN SUDOKU VOCABULARY

## **FOR ITERATION 2**

## 10. LANDSCAPE MODE

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to play the game in landscape mode, so that I can play while laying on my side

- As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.

- As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font that standard mode.

### `Test Driven Development`

- When a user rotates the screen orientation to landscape, the app will rotate accordingly with readjustments to board and button locations

### `Scenario`

### **Given**: The user has the application installed on a tablet device

![Screenshot_20190328-163859__1_](/uploads/7bf2af72caa226b99f11671b24305986/Screenshot_20190328-163859__1_.png)

### **When**: The user clicks on the application icon and rotate the screen 90 degrees (making it in landscape view)
### **Then**: The application screen (with the buttons, grid and all the subelements) will rotate as well to fit the landscape view 

![Screenshot_20190328-164311](/uploads/3e16d46621d3fb63d97b51930848de26/Screenshot_20190328-164311.png)

![Screenshot_20190328-164315](/uploads/b5194c9c144d9ec3534ea8e657858ab6/Screenshot_20190328-164315.png)

![Screenshot_20190328-164320](/uploads/92a007687682c536be4211e35d4c0019/Screenshot_20190328-164320.png)

## 11. CSV READER

### Status: **Implemented**

### `User Story`

- As a teacher, I want to specify a list of word pairs via a CSV file for my students to practice this week

- As a teacher, I want to specify a list of word pairs for my students to practice this week.

- As a student working with a textbook, I want to load pairs of words to practice from each chapter of the book.

### `Test Driven Development`

- When a teacher selects the "import word list" button from the main menu, the teacher will be prompted to find the CSV file that contains the pairs of words. The program will then generate a Sudoku board using the user inputted pair of words.
 
### `HOW TO TEST`

1. As a base case, start a new game as usual, and only words pertaining to fruits should be used in the making of the game
2. Download the provided CSV, called test.csv, from gitLab in the master repository
3. Run the app and click the "Import Word List" button
4. Navigate to where test.csv was downloaded to and click on it
5. A toast should appear announcing that the import was a success
6. Start a new game as normal
7. If the word "test" appears as a button or on the board, the import is a success
8. OPTIONAL FURTHER TESTING: repeat steps 2-7 above but with "test2.csv" to add colours to the game


### `Scenario 1`

- Given: The user is in the main menu of the application 
- When: The user presses on the import word list
- Then: The application opens the device's file directory and allows the user to choose the desired csv file


### `Scenario 2`

- Given: The user chose a desired csv file
- When: The user goes back to the main menu and then, and starts a new game
- Then: The application will generate a puzzle with the word list that the user chose 

## 12. VOCABULARY LISTS

### Status: **Implemented**

### `User Story`

- As a student, I want the Sudoku app to keep track of the vocabulary words that I am having difficulty recognizing so that they will be used more often in my practice puzzles.

### `Test Driven Development`

- When a user uses a hint while playing the game, a score incrementor will be applied to the word and the score will be stored on the device. If the user then generates a new game, the new board will choose words with higher scores to play with.


### `Scenario`

- Given: The user has solved multiple puzzles and would like to practie the words they have been having problems with 
- When: The user makes a wrong guess of a particular cell in a sudoku puzzle
- Then: The application will increase the difficulity of the word and with it, the chance of this word appearing in future puzzles increase

## 13. LISTENING COMPREHENSION

### Status: **Implemented**

### `User Story`

- As a student who wants to practice my understanding of spoken words in the language that I am learning, I want a listening comprehension mode. In this mode, numbers will appear in the prefilled cells and the corresponding word in the language that I am learning will be read out to me when I press the number.

### `Test Driven Development`

- When a user selects the "listening mode" from the main menu, the game will instead generate a board with numbers. If the user long presses a pre-filled number on the board, a Text-to-Speech function will be used and the language that the user knows will be spoken while the user tries to fill in the cells with words that they are trying to learn

## 14. ERASE BUTTON

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to erase a cell that I accidentally filled with the wrong word

### `Test Driven Development`

- When a user wants to erase a filled cell, the user will select the desired cell to be erased and click the erase button

### `Scenario 1`

### **Given**: The user is in the process of solving a puzzle and has selected a certain cell that was not pre-filled 

![Screenshot_20190328-164547__1_](/uploads/3b5cf201f52d2b529d70383b96d74921/Screenshot_20190328-164547__1_.png)

### **When**: The user clicks on the erase button in the top left corner
### **Then**: The content of the cell gets erased and cell appears to be empty to the user

![Screenshot_20190328-164554](/uploads/068944aec041a66008db8487a70e11e6/Screenshot_20190328-164554.png)

### `Scenario 2`

### **Given**: The user is in the process of solving a puzzle and has selected a certain cell that was pre-filled 

![Screenshot_20190328-164554](/uploads/068944aec041a66008db8487a70e11e6/Screenshot_20190328-164554.png)

### **When**: The user clicks on the erase button in the top left corner
### **Then**: The application does not allow the deletion of the cell content and a toast message appears with the message 'cannot erase pre-filled cell'

![Screenshot_20190328-164600](/uploads/f28b3264e5c2dd2c1764fdb9fc2bf508/Screenshot_20190328-164600.png)

## 15. BETTER VICTORY DETECTION

### Status: **Implemented**

### `User Story`

- As a user, I don't want to have to click the "check" button after I have completed the game

### `Test Driven Development`

- When a user plays the Sudoku game, the game will automatically check if the board is completed or not rather than having the user to press the button

### `Scenario 1`

- Given: The user has almost finished solving the puzzle (filled in all the cells that were not pre-filled except one)
- When: The user fills in the last cell with the correct value
- Then: The application shows a toast vicotry message accompanied with a victory sound file

### `Scenario 2`

- Given: The user has almost finished solving the puzzle (filled in all the cells that were not pre-filled except one)
- When: The user fills in the last cell with the incorrect value
- Then: The application game screen does not do any actions that indicates the completeness of the game

## 16. DIFFERENT DEVICES

### Status: **Implemented**

### `User Story`

- As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.

### `Test Driven Development`

- When a user uses a tablet, the game is optimized to be played on a bigger screen

### `Scenario`

- Given: The user has installed the game on tablet device 
- When: The user clicks on the application icon to open the game
- Then: The welcoming message and the application screen with all the subelements (grid, buttons, images ... etc.) get optimized to fit the screen of the tablet device 

## 17. SPLASH SCREEN

### Status: **Implemented**

### `User Story`

- As a user, I would like to see a nice welcome animation to the app

### `Test Driven Development`

- When a user opens the game, an animation of the logo will be played and the main menu will soon pursue.

### `Scenario`

- Given: The user has the game installed on their device  
- When: The user clicks on the application icon to open the game
- Then: A welcoming message with an animation of the logo appears before the application proceeds to displaying the main menu 

## **FOR ITERATION 3**

## 18. NIGHT MODE

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to play in the dark, so that I don't have to strain my eyes while playing the game

### `Test Driven Development`

- When a user clicks the night mode button, the colours of the app will change to a darker scheme

## 19. PAUSE

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to pause the timer, so that I can do other important tasks

### `Test Driven Development`

- When a user touches the "pause" button, the timer will pause and a "pause screen" will be displayed (it should be big enough to block the board)

## 20. BACK (TO MENU) BUTTON

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to go back to the menu screen, so that I can go back to the menu to choose other options

### `Test Driven Development`

- When a user clicks a "back" button, the game they are currently on should be saved or prompted that progress will be lost and then returned to the menu screen

## 21. HOW-TO-PLAY BUTTON

### Status: **Not Started**

### `User Story`

- As a beginner to Sudoku, I want to know the rules of the game

### `Test Driven Development`

- When a beignner to Sudoku selects the "help button," a popup will explain the rules to Sudoku and pausing the timer of the game. (Help button in Main Menu and in-game?)

## 22. SAVED BOARDS

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to save my progress for the current puzzle for the future and when I return to the main menu

### `Test Driven Development`

- When a user decided to exit the current board, the user will be prompted with a message if they want to save the board or not. There will be an option in the main menu for users who want to return to a saved board.

## 23. RESET WORDLIST SCORE DATABASE

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to reset the scores of each word to start fresh because I have learned the words I had difficulty with.

### `Test Driven Development`

- When a user decides to click "clear statistics", the scores for each word will be zeroed

## 24. UI WORDLIST DATABASE

### Status: **Not Started**

### `User Story`

- - As a user, I want to be able to view what words are in the database to see what I am learning. 

### `Test Driven Development`

- When a user decides to press the "list of words" button, the user will be taken to another screen showing the list of words and the score of the words

## 25. DIFFERENT LANGUAGES

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to learn other languages other than french.

### `Test Driven Development`

- When a user decides to import a new list of words that contains a language other than french, there will be a column in the csv file dictating what language it will be

- TTS will also use the appropriate locale for the different language inputs

## 26. JSON SUPPORT

### Status: **Not Started**

### `User Story`

- As a user, I want to also be able to use JSON files to import word lists as well.

### `Test Driven Development`

- When a user selects a JSON file instead of a CSV file, the appropriate reader will be used for the file selected.

## **REFERENCES**

- Code from https://github.com/Subh0m0y/Sudoku/blob/master/src/core/Generator.java by Subh0m0y was referenced when implementing feature three (Unique Puzzles)
