# Project 5 - Graphical Adventure Game
## 1. About/Overview
### This project builds on project4 and adds a view thus continuing our MVC Design. This project also implements thieves, pits and adds visibility to the dungeon.

## 2. List Of Features
### a. Dungeon is represented on the screen as a GUI.
### b. There is a path from every cave in the dungeon to every other cave in the dungeon.
### c. Paths between dungeons are generated randomly.
### d. Player can move using directions in this dungeon.
### e. Dungeon consists of caves which have treasures. Players can pick up treasures as they go through the dungeon like diamonds, sapphires ,rubies and arrows.
### f. Player can pick up treasure and arrows, and use those arrows to shoot monsters called otyughs.
### g. Otyughs are smelly creatures, who can kill the player. Player must use his arrows properly to kill the otyughs and get to the end of the dungeon.
### h. This game has thieves. If you encounter one the thief will steal all your valuable treasure and disappear.
### i. GUI version displays status on the side, like player inventory and items in cave.

## 3. How to Run
### a. You can run the jar file inside the res folder. If you provide command line arguments then, then the CLI version of the game will open. If you dont provide any command line arguments, then the GUI verison will open.
### b. Provide arguments as, DungeonType, rows, columns, interconnectivity, treasure percentage and number of otyughs for CLI version

## 4. How to Use the program
### a. The complete functionality of the program has been outlined through 4 interfaces viz Cave, Dungeon, Player and RandomInterface. Also a controller has been provided to play the game with. Using the controller and the methods detailed in public interfaces, all the functionality of the program can be accessed and the game can be played. Any specific help regarding individual methods can be gotten from the javadocs. Also, this application as a GUI based view through which the user can play the game.
### b. Use commands M - move, P - Pickup, S-Shoot and Q-Quit to control the game through the CLI version.
### c. If you pick M: You can go to directions N:North, S:South, E:East, W:West if permitted by the game.
### d. If you pick S: you can shoot an arrow by further providing the direction and distance to shoot that arrow.
### e. If you pick Q: the game will exit.
### f. If you pick P: you will pickup all items in the dungeon
### 
### For the GUI version:
### g. Use arrow keys to move inside the dungeon
### h. Press Shift + W or A or S or D to shoot at North, South, East and West. You can specify the distance later.
### i. You can click on adjacent cells to move to them if possible.
### j. Click on create to create a new dungeon with new attributes.
### k. Click on reset to create a new dungeon with same parameters as previous.
### l. Click on refresh to play the same exact game again.
### m. Click on quit to quit the game.

## 5. Description of Examples/Screenshots
### Due to size limitations I have combined all images together, and will explain those in parts
### Part1
### Shows the first screen the user will see when he starts a game.

### Part2
### Shows an invalid move done by the player.

### Part3
### Shows the screen when player gets killed by otyugh

### Part4
### Shows the screen when player falls into the pit.

### Part5
### Shows the screen when player is robbed by a thief. It says your pockets feel lighter

### Part6
### Shows the screen when player wins.

## 6. Design/Model Changes
### 1. Added visited on cave.
### 2. Added thieves and pits to the caves. Also marked caves as visited.
### 3. Added cracks to caves near pits.
### 4. Added a GUI view to play the game.

## 7. Assumptions
### a. Game ends when player reaches the end cave.
### b. Tunnels cannot hold treasure.
### c. Only a certain percentage of _available_ caves can hold treasure.
### d. Player might not find all treasures at once inside the dungeon.
### e. It will take 2 shots to kill an otyugh.
### f. Distances of arrows must be exact to kill an otyugh.
### g. Player cannot quit the game while in the middle of shooting , moving or picking up.
### h. Number of otyughs thieves and pits are the same.
### i. Thief disappears after stealing.
### j. Player dies when he reaches to a pit.
### k. Thief will steal all the treasure but not arrows.
### l. Thieves are sneaky and dont have any indicators. Thieves also cant be killed.

## 8. Limitations
### a. Dungeon has to be of size 5x5 or greater. Lower dimensions might work, but there is a possibility of a dungeon being generated, whose requirements havent been met.
### b. Dungeons with higher interconnectivity(greater than or equal to 10) might lead to slower dungeon creations. Reccommend reducing interconnectivity to 10 or lower in such cases.
### c. No entities except the dungeon can be directly created by the user.
### d. New commands can only be added by using the command design pattern.
### e. Pits can only be found at single entranced caves except start and end.

## 9. Citations
### a.https://www.youtube.com/watch?v=fAuF0EuZVCk - Kruskals Algorithm.
### b.https://www.geeksforgeeks.org/print-paths-given-source-destination-using-bfs/ - BFS for finding start and end node
### c.Professor Freifelds course material for CS5010.
