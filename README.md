# MARKDOWN SUDOKU VOCABULARY

## **FOR ITERATION 1**

## 1. SUDOKU DUPLICATE IDENTIFIER

### Status: **Implemented**

### `User Story`

- As a beginner in Sudoku, I want to be able to quickly see if I have inputted duplicate words, so that I can quickly fix the mistake

### `Test Driven Development`

- When a beginner in Sudoku inputs a duplicate entry either in the row or column or the 3x3 box, the duplicates will be highlighted in red.

### `Scenario`

### **Given**: The user is filling the grid in either the listening compreshsion mode or the reading mode

![Screenshot_20190328-163633](/uploads/4c5619902e4f69c1722cc5d40968bfcd/Screenshot_20190328-163633.png)

### **When**: The user fills in a cell with a duplicate value, a value that already exists in the row or the column or the sub-grid
### **Then**: All the cells containing duplicate values and violate the row, column or cell rules get highlighted in with a red hue 

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

- Given: The user has finished solving the puzzle (filled in all the cells that were not pre-filled)
- When: The user clicks on the 'check' button 
- Then: A vicotry toast will appear on the screen accompanied by a victory sound

### `Scenario 2`

- Given: The user has not finished solving the puzzle (has not filled in all the cells that were not pre-filled) 
- When: The user clicks on the 'check' button 
- Then: A toast will appear on the screen telling the user that the puzzle is not completed yet

### `Scenario 3`

- Given: The user has finished solving the puzzle, but solved it incorrectly (filled in some or all the cells that were not prefilled with incorrect values)
- When: The user clicks on the 'check' button 
- Then: A toast will appear on the screen telling the user that the puzzle is not completed yet

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

![56225987_2303163643288099_5416660406930243584_n](/uploads/e2b8a52c348ab0213e9991f8671f3c9c/56225987_2303163643288099_5416660406930243584_n.png)

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

### **Given**: The user wants to play the game in landscape mode

![Screenshot_20190328-163859__1_](/uploads/7bf2af72caa226b99f11671b24305986/Screenshot_20190328-163859__1_.png)

### **When**: The user clicks on the application icon and rotates the screen 90 degrees (changing to landscape mode from portrait mode)
### **Then**: The application screen (with the buttons, grid and all the subelements) will rotate to fit the landscape view 

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

### **Given**: The user is in the main menu of the application

![Screenshot_20190328-163859](/uploads/9ba0457a686723211785005c25e43f99/Screenshot_20190328-163859.png)

### **When**: The user presses on the "Import Word List" button

### **Then**: The application opens the device's file directory and allows the user to choose the desired CSV file

![55759997_2151279198287373_6102627367694368768_n](/uploads/1080e17005d55ed6e52e79352fa8e995/55759997_2151279198287373_6102627367694368768_n.png)

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

- Given: The user has solved multiple puzzles and would like to practice the words they have been having problems with 
- When: The user makes a wrong answer of a particular cell in the sudoku puzzle
- Then: The application will increase the "score" of the word and thus, increasing the chance of the word appearing in future puzzles

## 13. LISTENING COMPREHENSION

### Status: **Implemented**

### `User Story`

- As a student who wants to practice my understanding of spoken words in the language that I am learning, I want a listening comprehension mode. In this mode, numbers will appear in the prefilled cells and the corresponding word in the language that I am learning will be read out to me when I press the number.

### `Test Driven Development`

- When a user selects the "listening mode" from the main menu, the game will instead generate a board with numbers. If the user long presses a pre-filled number on the board, a Text-to-Speech function will be used and the language that the user knows will be spoken while the user tries to fill in the cells with words that they are trying to learn

### `Scenario 1`

- Given: that listening comprehension mode is enabled
- When: the user initiates a new puzzle
- Then: the user sees a standard Sudoku grid with some prefilled cells showing digits in the range 1...9 (1 and 9 inclusive) and all other cells empty

### `Scenario 2`

- Given: that the user is filling in the grid in listening comprehension mode, and that the grid includes a cell with the prefilled digit 4 and that word pair 4 is (green, vert)
- When: the user presses the prefilled cell having the digit 4
- Then: the user hears the word "vert" read out and pronounced in French.

### `Scenario 3`

- Given: that the user is filling in the grid in listening comprehension mode, and that the grid includes a cell with the prefilled digit 4 and that word pair 4 is (green, vert)
- When: the user selects a non-prefilled cell to enter the word "green"
- Then: the word "green" appears in the list of words that may be selected, but not in the fourth position

### `Scenario 4`

- Given: that the user is filling in the grid in listening comprehension mode,
- When: the users presses a cell and hears the word "vert"
- Then: the user does not see the word "vert" anywhere on the game grid

## 14. ERASE BUTTON

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to erase a cell that I accidentally filled with the wrong word

### `Test Driven Development`

- When a user wants to erase a filled cell, the user will select the desired cell to be erased and click the erase button

### `Scenario 1`

### **Given**: The user is in the process of solving a puzzle and has selected a certain cell that was not pre-filled 

![Screenshot_20190328-164547__1_](/uploads/3b5cf201f52d2b529d70383b96d74921/Screenshot_20190328-164547__1_.png)

### **When**: The user clicks on the erase button in the top-left corner
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

### **Given**: The user has almost finished solving the puzzle (filled in all the cells that were not pre-filled except one)

![56173362_2853733077977836_7234541620320796672_n](/uploads/b82e98b0597bc230d471719ce60890dd/56173362_2853733077977836_7234541620320796672_n.png)

### **When**: The user fills in the last cell with the correct value
### **Then**: The application shows a toast vicotry message accompanied with a victory sound file

![55788386_2182403741837489_510562300281749504_n](/uploads/489c1e27fa51611fe63a789fda3d8e24/55788386_2182403741837489_510562300281749504_n.png)

### `Scenario 2`

### **Given**: The user has almost finished solving the puzzle (filled in all the cells that were not pre-filled except one)

![56173362_2853733077977836_7234541620320796672_n](/uploads/b82e98b0597bc230d471719ce60890dd/56173362_2853733077977836_7234541620320796672_n.png)

### **When**: The user fills in the last cell with the incorrect value
### **Then**: The application game screen does not do any actions that indicates the completeness of the game

![55686829_809700919401217_1034749216078954496_n](/uploads/7e6b1038cf209e9cad03dd5408c66ddf/55686829_809700919401217_1034749216078954496_n.png)

## 16. DIFFERENT DEVICES

### Status: **Implemented**

### `User Story`

- As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.

### `Test Driven Development`

- When a user uses a tablet, the game is optimized to be played on a bigger screen

### `Scenario`

### **Given**: The user has installed the game on tablet device 

### **When**: The user clicks on the application icon to open the game

### **Then**: The entirety of the application with all the subelements (grid, buttons, images ... etc.) gets optimized to fit the screen of the tablet device 

## 17. SPLASH SCREEN

### Status: **Implemented**

### `User Story`

- As a user, I would like to see a nice welcome animation to the app

### `Test Driven Development`

- When a user opens the game, an animation of the logo will be played and the main menu will soon pursue.

### `Scenario`

### **Given**: The user has the game installed on their device

![Screenshot_20190328-163852](/uploads/cf3e14f38997248f6e5054f26fd7945f/Screenshot_20190328-163852.png)

### **When**: The user clicks on the application icon to open the game
### **Then**: A welcoming message with an animation of the logo appears before the application proceeds to displaying the main menu

![56158213_2245117275527695_845553621767553024_n](/uploads/816cc6cec63222a52969542a43d4089f/56158213_2245117275527695_845553621767553024_n.png)

## 18. SCALABLE SUDOKU PUZZLES

### Status: **Implemented**

### `User Story`

- As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3).

- As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4).

### `Test Driven Development`

- When a user starts a new game, they will be prompted to select a sudoku board size, ranging from 4x4, 6x6, 9x9, and 12x12, that they would like to play with, then the game will create a board with the chosen size and start a normal sudoku game

### `Scenario`

### **Given**: The user wants to play a game of sudoku of size n x n (n is in {4, 6, 9, 12}), and is on the menu of ‘New Game.' The 12 x 12 is made for the tablet.

### **When**: The user chooses a size n x n via the seek bar and presses ‘START’

### **Then**: A n x n sudoku will appear on the screen with n buttons for the words, and it would be scaled to fit the entire phone/tablet screen

## **FOR ITERATION 4**

## 19. PAUSE

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to pause the timer, so that I can do other important tasks

### `Test Driven Development`

- When a user touches the "pause" button, the timer will pause and a "pause screen" will be displayed (it should be big enough to block the board)

### `Scenario`

### **Given**: The user needs to do a different task other than playing the game and does not want the timer to keep increasing

### **When**: The user clicks the "pause" icon in the game

### **Then**: A pause screen will appear over the app and the timer will be paused so that the user can't also cheat to solve the game.

## 20. SAVED BOARDS

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to go back to the menu screen, so that I can go back to the menu to choose other options

- As a user, I want to be able to turn off the game but still have the current board saved, so that I can resume the same puzzle at a later time

### `Test Driven Development`

- When a user clicks a "back" button or exits the game, the game they are currently on will be saved up to 3 recently boards are saved

### `Scenario`

### **Given**: The user accidentally clicks "back" or closes the game.

### **When**: The user clicks "Continue Game"

### **Then**: The game will prompt the user with the 3 most recent boards that are started by the user and not completed 

## 21. HOW-TO-PLAY BUTTON

### Status: **Not Started**

### `User Story`

- As a beginner to Sudoku, I want to know the rules of the game

### `Test Driven Development`

- When a beignner to Sudoku selects the "help button," a popup will explain the rules to Sudoku and pausing the timer of the game. (Help button in Main Menu and in-game?)

### `Scenario`

### **Given**: The user downloads the app for the first time and has no idea how to play the game

### **When**: The user clicks on the "information" icon

### **Then**: The user will be prompted with a screen that explains the rules of the game and the features of the game to help the user solve the puzzle

## 21. ERASE STORED DATA

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to easily delete my settings preferences, saved puzzles, and imported wordlists, and not have to delete the entire app so that I can have more storage

### `Test Driven Development`

- When a user decides to press the "Clear Data and Cache" button from the settings menu, they will eb prompted with a confirmation screen. Then the settings preferences, saved puzzles, and imported wordlists will be deleted but the app will remain.

- When the user goes to the "import word list" page, they may also manually delete the words that are currently stored on the app without having to delete other settings

### `Scenario`

### **Given**: The user wants to easily delete their settings preferences, stored puzzles, and imported wordlists

### **When**: The user clicks on the "Clear Data and Cache" button from the settings menu

### **Then**: The wordlists in the app will be cleared, the settings preferences will be restored to default settings, local leaderboard, and the stored puzzles will be cleared

## 23. RESET WORDLIST SCORE DATABASE

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to reset the scores of each word to start fresh because I have learned the words I had difficulty with.

### `Test Driven Development`

- When a user decides to click "clear statistics", the scores for each word will be zeroed

### `Scenario`

### **Given**: The user has practiced a lot and mastered the words that they were having difficulty with from earlier gameplays

### **When**: The user decides to click the "reset word scores" button that shows up when they are editing the words within the "Manage WOrd List" screen

### **Then**: The scores of all the words in the database will be reset to 1

## 22. UI WORDLIST DATABASE

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to view what words are in the database to see what I am learning

- As a user, I want to be able to sort the words alphabetically or by its score

- As a user, I want to be able to edit the words or the scores

- As a user, I want to be able to remove the words

### `Test Driven Development`

- When a user decides to press the "Import Word List" button, the user will be prompted with a screen that shows the list of words and the score of the words

### `Scenario`

### **Given**: The user wants to see what words are contained in the database, edit the words or scores of the words, delete the words, and sort the words

### **When**: The user clicks on the "Import Word List" button

### **Then**: The user will be prompted with a user interface of database for the word list and will be able to edit the word pairs and its score, delete the word pair, and sort the word pairs

## 23. LOCAL LEADERBOARD

### Status: **Implemented**

### `User Story`

- As a user, I want to be able to keep track of my finishing times

- As a user, I want to be able to see if my friend is able to beat my finishing times and label that someone else did better than me

### `Test Driven Development`

- When a user completes a game, they may enter in their name to be added to a leaderboard

### `Scenario`

### **Given**: The user wants to keep track of their fast times

### **When**: The user finishes a puzzle, they may type in their name to add to the leaderboard

### **Then**: Their name and time will be added to the leaderboard. THe leaderboard may be viewed upon game completion

## **FUTURE IDEAS**

## 24. NIGHT MODE

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to play in the dark, so that I don't have to strain my eyes while playing the game

### `Test Driven Development`

- When a user clicks the night mode button, the colours of the app will change to a darker scheme
 
### `Scenario`

### **Given**: The user is playing the game in the dark or just wants to use a dark colour scheme

### **When**: The user clicks the "moon" icon on the menu of the app or in the game or in the settings

### **Then**: The app will get a dark colour scheme to the app that is easier on the eyes when in a dark room

## 25. DIFFERENT LANGUAGES

### Status: **Not Started**

### `User Story`

- As a user, I want to be able to learn other languages other than french.

### `Test Driven Development`

- When a user decides to import a new list of words that contains a language other than french, there will be a column in the csv file dictating what language it will be

- TTS will also use the appropriate locale for the different language inputs

### `Scenario`

### **Given**: The user wants to learn different languages other than french and english

### **When**: The user imports a CSV with words from other languages or uses the drop down menu to choose another language

### **Then**: The board will operate as normally but using the user specified language

## 26. JSON SUPPORT

### Status: **Not Started**

### `User Story`

- As a user, I want to also be able to use JSON files to import word lists as well.

### `Test Driven Development`

- When a user selects a JSON file instead of a CSV file, the appropriate reader will be used for the file selected.

### `Scenario`

### **Given**: The user has a JSON file instead of a CSV file

### **When**: The user presses the "Import Word List" button and selects a JSON file instead of a CSV file

### **Then**: The app will read the JSON file accordingly

## **REFERENCES**

- Code from https://github.com/Subh0m0y/Sudoku/blob/master/src/core/Generator.java by Subh0m0y was referenced when implementing feature three (Unique Puzzles)
- [1] taken from SFU CMPT276 course website - Specification By Example, link: https://coursys.sfu.ca/2019sp-cmpt-276-e1/pages/SpecByExample