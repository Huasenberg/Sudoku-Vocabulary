# MARKDOWN SUDOKU VOCABULARY

## 1. UNIQUE PUZZLES

### `User Story`

- As a user, I want to play a unique game each time so that the games will be different each time

### `Test Driven Development`

- When a user starts a game, a unique Sudoku board will be generated each time

## 2. CSV READER

### `User Story`

- As a teacher, I want to specify a list of word pairs via a CSV file for my students to practice this week

### `Test Driven Development`

- When a teacher selects the "import word list" button from the main menu, the teacher will be prompted to find the CSV file that contains the 9 (or 10) pairs of words and prompted to enter the name of the 2 languages. The program will then generate a Sudoku board using the user inputted nine pair of words

## 3. SUDOKU DUPLICATE IDENTIFIER

### `User Story`

- As a beginner in Sudoku, I want to be able to quickly see if I have inputted duplicate words, so that I can quickly fix the mistake

### `Test Driven Development`

- When a beginner in Sudoku inputs a duplicate entry either in the row or column or the 3x3 box, the duplicates will be highlighted in red

## 4. PAUSE

### `User Story`

- As a user, I want to be able to pause the timer, so that I can do other important tasks

### `Test Driven Development`

- When a user touches the "pause" button, the timer will pause and a "pause screen" will be displayed (it should be big enough to block the board)

## 5. TIMER

### `User Story`

- As a user, I want to be able to know how long it took me to solve the Sudoku puzzle, so that I can try to improve for next time and see how much time has elapsed

### `Test Driven Development`

- When a user starts a Sudoku puzzle, a timer is initiated and displayed above the Sudoku board

## 6. WORD TRANSLATION HINTS

### `User Story`

- As a language learner, I want to be able to peek at the translation of the pre-filled cell so that I can remind myself what the translation is

### `Test Driven Development`

- When a user selects a Sudoku cell that is part of the pre-filled configuration of the puzzle, the translation of that word is momentarily displayed

## 7. MAIN MENU

### `User Story`

- As a user, I want to see a main menu so that I can choose when to start my game

### `Test Driven Development`

- When a user opens the app, a main menu will be presented to the user

## 8. BOLDED PRE-FILLED WORDS

### `User Story`

- As a user, I want to be able to differentiate between pre-filled cells and the cells that I filled out

### `Test Driven Development`

- When a user tries to change a pre-filled cell, a toast will state that the pre-filled cell cannot be changed and the pre-filled cells will be bolded

## 9. BACK (TO MENU) BUTTON

### `User Story`

- As a user, I want to be able to go back to the menu screen, so that I can go back to the menu to choose other options

### `Test Driven Development`

- When a user clicks a "back" button, the game they are currently on should be saved or prompted that progress will be lost and then returned to the menu screen

## 10. ERASE BUTTON

### `User Story`

- As a user, I want to be able to erase a cell that I accidentally filled with the wrong word

### `Test Driven Development`

- When a user wants to erase a filled cell, the user will select the desired cell to be erased and click the erase button

## 11. HOW-TO-PLAY BUTTON

### `User Story`

- As a beginner to Sudoku, I want to know the rules of the game

### `Test Driven Development`

- When a beignner to Sudoku selects the "help button," a popup will explain the rules to Sudoku and pausing the timer of the game. (Help button in Main Menu and in-game?)

## 12. SAVED BOARDS

### `User Story`

- As a user, I want to be able to save my progress for the current puzzle for the future and when I return to the main menu

### `Test Driven Development`

- When a user decided to exit the current board, the user will be prompted with a message if they want to save the board or not. There will be an option in the main menu for users who want to return to a saved board.

## 13. FINISH SCREEN

### `User Story`

- As a user, I want to be able to know when I complete a puzzle, so that I can stop with the puzzle and move on

### `Test Driven Development`

- When a user completes the board, a victory screen will pop up

## 14. APP ICON

### `User Story`

- As a user, I want to see a unique icon for the app rather, so that it looks nicer than the default icon

### `Test Driven Development`

- When a user decided to open the app, a unique icon will be displayed rather than the default Android icon

## 15. NIGHT MODE

### `User Story`

- As a user, I want to be able to play in the dark, so that I don't have to strain my eyes while playing the game

### `Test Driven Development`

- When a user clicks the night mode button, the colours of the app will change to a darker scheme

## 16. LANDSCAPE MODE

### `User Story`

- As a user, I want to be able to play the game in landscape mode, so that I can play while laying on my side

### `Test Driven Development`

- When a user rotates the screen orientation to landscape, the app will rotate accordingly with readjustments to board and button locations

- Ex. ![GitHub Logo](landscape_sudoku_ex.png)

