import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map;

import static java.lang.Boolean.getBoolean;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.compareUnsigned;
import static java.lang.Integer.parseInt;

//------------------------------------------------------------------------------------------------------------------
public class Game {
    private static Map<Integer, Room> map;
    private static Player p1;

    //------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        p1 = new Player();
        map = new HashMap<>();
        Player p1 = new Player("Rob", 2, 100, 0);

        readMapFile();
        readItemsFile(); //read files into the game
        readPuzzlesFile();
        readMonsterFile();


        while (!userInput.equalsIgnoreCase("Exit") && p1.getHealth()!=0) { //while loop for game
            userInput = input.nextLine();
            if (userInput.toLowerCase().contains("pickup") || //different inputs for item interactions
                    userInput.toLowerCase().contains("drop") ||
                    userInput.toLowerCase().contains("examine") ||
                    userInput.toLowerCase().contains("equip") ||
                    userInput.toLowerCase().contains("unequip") ||
                    userInput.toLowerCase().contains("heal")){

                String itemName = ""; //string for the name
                switch (userInput.split(" ")[0]) { //splits based on the space
                    case "pickup":
                        itemName = userInput.substring(userInput.indexOf("pickup ") + 7);
                        pickupItem(itemName);
                        break;
                    case "drop":
                        itemName = userInput.substring(userInput.indexOf("drop ") + 5);
                        dropItem(itemName);
                        break;
                    case "examine":
                        itemName = userInput.substring(userInput.indexOf("examine ") + 8);
                        System.out.println(examineItem(itemName));
                        break;
                    case "equip":
                        itemName = userInput.substring(userInput.indexOf("equip ") + 6);
                        equipItem(itemName);
                        break;
                    case "unequip":
                        itemName = userInput.substring(userInput.indexOf("unequip ") + 8);
                        unequipItem(itemName);
                        System.out.println("You have unequipped the item.");
                        break;
                    case "heal":
                        itemName = userInput.substring(userInput.indexOf("heal ") + 5);
                        heal(itemName);
                        break;
                }
            } else if (userInput.toLowerCase().contains("move")) {
                String direction = userInput.split(" ")[1];
                switch (direction) {
                    case "north":
                        moveNorth();
                        break;
                    case "south":
                        moveSouth();
                        break;
                    case "east":
                        moveEast();
                        break;
                    case "west":
                        moveWest();
                        break;
                }
            } else if (userInput.equalsIgnoreCase("inspect room")) {
                printExamineRoom();
            } else if (userInput.equalsIgnoreCase("inspect item")) {
                System.out.println(accessRoomInventory());
            } else if (userInput.equalsIgnoreCase("access inventory")) {
                System.out.println(accessPlayerInventory());
            } else if (userInput.equalsIgnoreCase("inspect puzzle")) {
                if (!map.get(returnCurrentRoomID()).getPuzzles().isEmpty()) {
                    System.out.println(map.get(returnCurrentRoomID()).getPuzzles().get(0).getQuestion());
                    System.out.println("Solve?");
                    String str = input.nextLine();
                    if (str.equalsIgnoreCase("solve puzzle")) {
                        System.out.println("Guess the answer:");
                        String str2 = "";
                        for (int attempts = 3; attempts > 0; attempts--) {
                            if (!map.get(returnCurrentRoomID()).getPuzzles().isEmpty()) {
                                if (!str2.equalsIgnoreCase(map.get(returnCurrentRoomID()).getPuzzles().get(0).getAnswer())) {
                                    str2 = input.nextLine();
                                    solvePuzzle(str2);
                                } else {
                                    str2 = input.nextLine();
                                    solvePuzzle(str2);
                                    if (attempts==0){
                                        System.out.println("You have run out of attempts. Come back later to try again.");
                                    }
                                    return;
                                }
                            }
                        }
                    } else System.out.println("Inspect the puzzle again.");;
                }
            } else if (userInput.equalsIgnoreCase("ignore puzzle")) {
                ignorePuzzle();
            }
            else if (userInput.equalsIgnoreCase("inspect monster")) { //opens fight 'menu'
                examineMonster();
                System.out.println("What will you do next?");
                String str = input.nextLine();
                if (str.equalsIgnoreCase("attack")){
                    System.out.println("You have entered combat mode");
                    while(!(p1.getHealth()<=0) && !(map.get(returnCurrentRoomID()).getMonsters().get(0).getHealth()<=0)) { //starts the fight
                        String monsterName = map.get(returnCurrentRoomID()).getMonsters().get(0).getName();
                        String str2 = input.nextLine();
                            if  (str2.toLowerCase().contains("pickup") || //differnt inputs for item interactions
                                str2.toLowerCase().contains("drop") ||
                                str2.toLowerCase().contains("examine") ||
                                str2.toLowerCase().contains("equip") ||
                                str2.toLowerCase().contains("unequip") ||
                                str2.toLowerCase().contains("attack") ||
                                str2.toLowerCase().contains("heal")) {

                            switch (str2.split(" ")[0]) {
                                case "examine":
                                    str2 = str2.substring(str2.indexOf("examine ") + 8);
                                    System.out.println(examineItem(str2));
                                    attackPlayer(monsterName);
                                    System.out.println(monsterName + "Attacked you.");
                                    displayPlayerHP();
                                    break;
                                case "equip":
                                    str2 = str2.substring(str2.indexOf("equip ") + 6); //equip command
                                    equipItem(str2);
                                    attackPlayer(monsterName);
                                    System.out.println(monsterName + "Attacked you.");
                                    displayPlayerHP();
                                    break;
                                case "unequip":
                                    str2 = str2.substring(str2.indexOf("unequip ") + 8); //unequip command
                                    unequipItem(str2);
                                    System.out.println("You have unequipped the item.");
                                    attackPlayer(monsterName);
                                    System.out.println(monsterName + "Attacked you.");
                                    displayPlayerHP();
                                    break;
                                case "heal":
                                    str2 = str2.substring(str2.indexOf("heal ") + 5); //heal command
                                    heal(str2);
                                    attackPlayer(monsterName);
                                    System.out.println(monsterName + " attacked you.");
                                    displayPlayerHP();
                                    break;
                                case "attack":
                                    str2 = str2.substring(str2.indexOf("attack ") + 7); //attack command
                                    System.out.println("You attacked " + monsterName);
                                    attackMonster(monsterName);
                                    displayMonsterHP(monsterName);
                                    attackPlayer(monsterName);
                                    System.out.println(monsterName + "Attacked you.");
                                    displayPlayerHP();
                            }
                        }
                    }if (p1.getHealth()<=0){
                        System.out.println("Game over... Type 'new game' or 'Exit'");
                        String str3 = input.next();
                        if (str3.equalsIgnoreCase("New Game")){ //re run new game
                            main(null);
                        }
                        if (str3.equalsIgnoreCase("Exit")) return; //exit
                    } else if((map.get(returnCurrentRoomID()).getMonsters().get(0).getHealth() <=0)){
                        map.get(returnCurrentRoomID()).getMonsters().remove(0);
                        System.out.println("You have defeated the monster.");
                    }
                } else if (str.equalsIgnoreCase("ignore")){
                    ignoreMonster();
                } else {
                    System.out.println("You must type 'ignore' or 'attack;");
                }
            }
        }
    }
//-----------------------------------------------------------READ ROOMS FILE-------------------------------------------------------

    public static void readMapFile() throws FileNotFoundException { //method to read map.txt and put objects into arraylist for map
        String rx = "(.*?)";        //regex expression to take items in quotes
        File mapFile = new File("src/rooms.txt");     //create new pathname for the file
        Scanner s = new Scanner(mapFile);           //file scanner
        s.useDelimiter(",");        //uses delimiter of "," to parse through text file for attributes instead of spaces.
        for (int i = 0; i <= 5; i++) { //loops 6 times
            Room room = new Room(); //creates a new room each loop
            if (s.next().equalsIgnoreCase("id:")) room.setRoomID(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("name:")) room.setRoomName((s.next(rx)));
            if (s.next().equalsIgnoreCase("description:")) room.setRoomDescription((s.next(rx)));
            if (s.next().equalsIgnoreCase("north:")) room.setNorth((s.next(rx)));
            if (s.next().equalsIgnoreCase("south:")) room.setSouth((s.next(rx)));
            if (s.next().equalsIgnoreCase("east:")) room.setEast((s.next(rx)));
            if (s.next().equalsIgnoreCase("west:")) room.setWest((s.next(rx)));
            if (s.next().equalsIgnoreCase("hasBeenVisited:")) room.setHasBeenVisited(parseBoolean(s.next(rx)));
            if (s.next().equalsIgnoreCase("containsPlayer:")) room.setContainsPlayer(parseBoolean(s.next(rx)));
            map.put(i, room); //adds room to the arraylist
            s.nextLine(); //goes to the next line in text file
        }
    }
//------------------------------------------------------------READ ITEMS FILE------------------------------------------------------

    public static void readItemsFile() throws FileNotFoundException { //method to read item.txt and put objects into arraylist for rooms
        String rx = "(.*?)";        //regex expression to take items in quotes
        File itemFile = new File("src/items.txt");     //create new pathname for the file
        Scanner s = new Scanner(itemFile);           //file scanner
        s.useDelimiter(",");        //uses delimiter of "," to parse through text file for attributes instead of spaces.
        for (int i = 0; i <= 2; i++) { //loops 6 times
            Item item = new Item(); //creates a new room each loop
            if (s.next().equalsIgnoreCase("itemid:")) item.setItemId(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("roomID:")) item.setRoomId(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("name:")) item.setItemName((s.next(rx)));
            if (s.next().equalsIgnoreCase("description:")) item.setDescription((s.next(rx)));
            if (s.next().equalsIgnoreCase("attackpoints:")) item.setAttackPoints(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("HealthRestored:")) item.setHpRestored((parseInt(s.next(rx))));
            map.get(item.getRoomId()).getRoomInventory().add(item); //adds item to room inventory arraylist
            s.nextLine(); //goes to the next line in text file
        }
    }
//--------------------------------------------------------READ PUZZLE FILE----------------------------------------------------------

    public static void readPuzzlesFile() throws FileNotFoundException { //method to read item.txt and put objects into arraylist for rooms
        String rx = "(.*?)";        //regex expression to take items in quotes
        File puzzleFile = new File("src/puzzles.txt");     //create new pathname for the file
        Scanner s = new Scanner(puzzleFile);           //file scanner
        s.useDelimiter(",");        //uses delimiter of "," to parse through text file for attributes instead of spaces.
        for (int i = 0; i <= 0; i++) { //loops 6 times
            Puzzle puzzle = new Puzzle(); //creates a new room each loop
            if (s.next().equalsIgnoreCase("puzzleID:")) puzzle.setPuzzleID(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("roomID:")) puzzle.setRoomID(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("question:")) puzzle.setQuestion(s.next(rx));
            if (s.next().equalsIgnoreCase("answer:")) puzzle.setAnswer((s.next(rx)));
            if (s.next().equalsIgnoreCase("hint:")) puzzle.setHint((s.next(rx)));
            map.get(puzzle.getRoomID()).puzzles.add(puzzle); //adds puzzle to the puzzle arraylist
            s.nextLine(); //goes to the next line in text file
        }
    }
//----------------------------------------------------------READ MONSTER FILE--------------------------------------------------------

    public static void readMonsterFile() throws FileNotFoundException { //method to read item.txt and put objects into arraylist for rooms
        String rx = "(.*?)";        //regex expression to take items in quotes
        File monsterFile = new File("src/monsters.txt");     //create new pathname for the file
        Scanner s = new Scanner(monsterFile);           //file scanner
        s.useDelimiter(",");        //uses delimiter of "," to parse through text file for attributes instead of spaces.
        for (int i = 0; i <= 0; i++) { //loops 6 times
            Monster monster = new Monster(); //creates a new room each loop
            if (s.next().equalsIgnoreCase("roomID:")) monster.setRoomId(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("monsterID:")) monster.setMonsterID(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("name:")) monster.setName(s.next(rx));
            if (s.next().equalsIgnoreCase("health:")) monster.setHealth(parseInt(s.next(rx)));
            if (s.next().equalsIgnoreCase("attackpoints:")) monster.setAttackPoints(parseInt(s.next(rx)));
            map.get(monster.getRoomId()).monsters.add(monster); //adds puzzle to the puzzle arraylist
            s.nextLine(); //goes to the next line in text file
        }
    }
//------------------------------------------------------MOVE EAST------------------------------------------------------------

    private static void moveEast() { //method to move east
        int current = returnCurrentRoomID();
        if (parseInt(map.get(current).getEast()) != -1) {
            map.get(current).setContainsPlayer(false);
            map.get(current).setHasBeenVisited(true);
            map.get(parseInt(map.get(current).getEast())).setContainsPlayer(true);
            System.out.println("You are now in the " + map.get(returnCurrentRoomID()).getRoomName());
            printExamineRoom();
        } else {
            System.out.println("You cannot move in that direction.");
        }
    }
//------------------------------------------------------MOVE WEST------------------------------------------------------------

    private static void moveWest() { //method to move west
        int current = returnCurrentRoomID();
        if (parseInt(map.get(current).getWest()) != -1) {
            map.get(current).setContainsPlayer(false);
            map.get(current).setHasBeenVisited(true);
            map.get(parseInt(map.get(current).getWest())).setContainsPlayer(true);
            System.out.println("You are now in the " + map.get(returnCurrentRoomID()).getRoomName());
            printExamineRoom();

        } else {
            System.out.println("You cannot move in that direction.");
        }
    }
//-------------------------------------------------------MOVE NORTH-----------------------------------------------------------

    private static void moveNorth() { //method to move north
        int current = returnCurrentRoomID();
        if (parseInt(map.get(current).getNorth()) != -1) {
            map.get(current).setContainsPlayer(false);
            map.get(current).setHasBeenVisited(true);
            map.get(parseInt(map.get(current).getNorth())).setContainsPlayer(true);
            System.out.println("You are now in the " + map.get(returnCurrentRoomID()).getRoomName());
            printExamineRoom();

        } else {
            System.out.println("You cannot move in that direction.");
        }
    }
//----------------------------------------------------MOVE SOUTH--------------------------------------------------------------

    private static void moveSouth() { //method to move south
        int current = returnCurrentRoomID();
        if (parseInt(map.get(current).getSouth()) != -1) {
            map.get(current).setContainsPlayer(false);
            map.get(current).setHasBeenVisited(true);
            map.get(parseInt(map.get(current).getSouth())).setContainsPlayer(true);
            System.out.println("You are now in the " + map.get(returnCurrentRoomID()).getRoomName());
            printExamineRoom();

        } else {
            System.out.println("You cannot move in that direction.");
        }
    }
//-------------------------------------------------EXAMINE AN ITEM-----------------------------------------------------------------

    private static String examineItem(String itemName) { //returns item description from inputted item from player inventory
        String itemDescription = "";
        for (int i = 0; i <= p1.getInventory().size(); i++) {
            if (!p1.getInventory().isEmpty()) {
                if (itemName.equalsIgnoreCase(p1.getInventory().get(i).getItemName())) {
                    itemDescription = p1.getInventory().get(i).getDescription();
                    break;
                } else {
                    return ("You do not have this item in your inventory");
                }
            }
        }
        return itemDescription;
    }
//-----------------------------------------------------PICK UP AN ITEM-------------------------------------------------------------

    private static void pickupItem(String itemName) { //may or may not work
        for (int i = 0; i < map.get(returnCurrentRoomID()).getRoomInventory().size(); i++) {
            if (itemName.equalsIgnoreCase(map.get(returnCurrentRoomID()).getRoomInventory().get(i).getItemName())) { //checks item name
                p1.getInventory().add(map.get(returnCurrentRoomID()).getRoomInventory().get(i)); //adds item to player inventory
                System.out.println("You have picked up " + (map.get(returnCurrentRoomID()).getRoomInventory().get(i).getItemName()));
                map.get(returnCurrentRoomID()).getRoomInventory().remove(i); //removes item from room inventory
                break;
            } else System.out.println("This item does not exist in this room.");
        }
    }
//--------------------------------------------------------DROP ITEM----------------------------------------------------------

    private static void dropItem(String itemName) { //may or may not work
        for (int i = 0; i <= p1.getInventory().size(); i++) {
            if (!p1.getInventory().isEmpty()) {
                if (itemName.equalsIgnoreCase(p1.getInventory().get(i).getItemName())) { //checks item name
                    map.get(returnCurrentRoomID()).getRoomInventory().add(p1.getInventory().get(i)); //adds item to room inventory
                 p1.getInventory().remove(i); //removes item from inventory
                    System.out.println("You have dropped the item.");
                } else System.out.println("You do not have this item in your inventory.");
            } else System.out.println("There are no items in your inventory.");
        }
    }
//----------------------------------------------------------EXAMINE PUZZLE--------------------------------------------------------

    private static void examinePuzzle() { //displays question
        map.get(returnCurrentRoomID()).getPuzzles().get(0).getQuestion();
    }
//----------------------------------------------------SOLVE A PUZZLE--------------------------------------------------------------

    private static void solvePuzzle(String puzzleAnswer) {
        Item potion = new Item(-1, "A health potion.", 10, 0, 0, "Potion");
        if (!map.get(returnCurrentRoomID()).getPuzzles().isEmpty()){
            if (map.get(returnCurrentRoomID()).getPuzzles().get(0).getAnswer().equalsIgnoreCase(puzzleAnswer)) {
                p1.getInventory().add(potion);
                System.out.println("Congratulations, you solved the puzzle! You get a potion.");
                map.get(returnCurrentRoomID()).getPuzzles().remove(map.get(returnCurrentRoomID()).getPuzzles().get(0));
            } else System.out.println("Sorry, wrong answer.");
        }

    }
//--------------------------------------------------IGNORE A PUZZLE----------------------------------------------------------------

    private static void ignorePuzzle () { //removes puzzle from current room
        if (!map.get(returnCurrentRoomID()).getPuzzles().isEmpty()) {
            map.get(returnCurrentRoomID()).getPuzzles().remove(0);
            System.out.println("Puzzle has been removed.");
        } else System.out.println("There is no puzzle to ignore here.");
    }
//-------------------------------------------------------ACCESS PLAYER INVENTORY-----------------------------------------------------------

    private static String accessPlayerInventory () { //prints player inventory maybe??
        String inventoryList = "";
        if (!p1.getInventory().isEmpty()) {
            for (int i = 0; i < p1.getInventory().size(); i++) {
                inventoryList += ((p1.getInventory().get(i).getItemName()) + " ");
            }
        } else System.out.println("You do not have any items in your inventory.");
        return inventoryList;
    }
//------------------------------------------------------ACCESS ROOM INVENTORY------------------------------------------------------------

    private static String accessRoomInventory () { // returns the current rooms inventory in a string, item names
        String inventoryList = "";
            if (!map.get(returnCurrentRoomID()).getRoomInventory().isEmpty()) {
                for (int i = 0; i < map.get(returnCurrentRoomID()).getRoomInventory().size(); i++) {
                    inventoryList += map.get(returnCurrentRoomID()).getRoomInventory().get(i).getItemName();
                }
            }
            return inventoryList;
    }
//-----------------------------------------------------EQUIP ITEM------------------------------------------------------------------

    private static void equipItem (String itemName) {
        if (!p1.getInventory().isEmpty()){
            for (int i =0; i<=p1.getInventory().size();i++){
                if (p1.getInventory().get(i).getItemName().equalsIgnoreCase(itemName)){
                    p1.setAttackPoints(p1.getAttackPoints() + p1.getInventory().get(i).getAttackPoints());
                    p1.equippedItems.add(p1.getInventory().get(i));
                    p1.getInventory().remove(p1.getInventory().get(i));
                }
            }
        }
        System.out.println("You have equipped "+ itemName);
    }
//---------------------------------------------------UNEQUIP ITEM--------------------------------------------------------------------

    private static void unequipItem (String itemName) {
        if (!p1.getEquippedItems().isEmpty()){
            for (int i =0; i<=p1.getEquippedItems().size();i++){
                if (p1.getEquippedItems().get(i).getItemName().equalsIgnoreCase(itemName)){
                    p1.setAttackPoints(p1.getAttackPoints() - p1.getEquippedItems().get(i).getAttackPoints());
                    p1.getInventory().add(p1.getEquippedItems().get(i));
                    p1.getEquippedItems().remove(p1.getEquippedItems().get(i));
                }
            }
        }
        System.out.println("You have equipped "+ itemName);
    }
//----------------------------------------------------HEAL OR USE POTION COMMAND--------------------------------------------------------------

    private static void heal (String itemName) { //use potion method
        int indexOfPotion=-1;
        Item potion = new Item();
        if (itemName.equalsIgnoreCase("potion")){ //checks to make sure input is potion
            if(!p1.getInventory().isEmpty()){ //checks to see if inventory is not empty
                for (int i = 0; i<=p1.getInventory().size();i++) { //loop through array
                    if (p1.getInventory().get(i).getItemName().equalsIgnoreCase("potion")) {
                        if (p1.getInventory().get(i).getItemName().equalsIgnoreCase("potion")) { //checks for where potion is in the array
                            potion = (p1.getInventory().get(i)); //gets information of item from inventory
                            indexOfPotion = i; //gets index for potion
                            break;
                        }
                    }
                }
                p1.setHealth(p1.getHealth() + potion.getHpRestored()); //increases player inventory
                p1.getInventory().remove(indexOfPotion);//removes potion from inventory after use
                System.out.println("You have used the item to heal for " + potion.getHpRestored() + " life points.");
            }
        } else System.out.println("There is no potion in your inventory.");
    }
//--------------------------------------------------EXAMINE MONSTER----------------------------------------------------------------

    private static void examineMonster () {
        System.out.println("This monster is " + map.get(returnCurrentRoomID()).getMonsters().get(0).getName());
        System.out.println(map.get(returnCurrentRoomID()).getMonsters().get(0).getName() + "'s attack points: " + map.get(returnCurrentRoomID()).getMonsters().get(0).getAttackPoints());
    }
//--------------------------------------------------ATTACK MONSTER----------------------------------------------------------------

    private static void attackMonster (String monsterName) { //subtracts player attack points from monster
        if (map.get(returnCurrentRoomID()).getMonsters().get(0).getName().equalsIgnoreCase(monsterName)) {
                map.get(returnCurrentRoomID()).getMonsters().get(0).setHealth((map.get(returnCurrentRoomID()).getMonsters().get(0).getHealth()) - (p1.getAttackPoints()));
            }
    }
//------------------------------------------------IGNORE MONSTER------------------------------------------------------------------

    private static void ignoreMonster () {
            map.get(returnCurrentRoomID()).getMonsters().remove(0);
            System.out.println("Monster has been ignored.");
        }
//---------------------------------------------------------DISPLAY MONSTER HP---------------------------------------------------------

    private static void displayMonsterHP (String monsterName) {
        int health=0;
        if (map.get(returnCurrentRoomID()).getMonsters().get(0).getName().equalsIgnoreCase(monsterName)) {
            health = map.get(returnCurrentRoomID()).getMonsters().get(0).getHealth();
        }
        System.out.println(monsterName + "'s Health: " + health);
    }
//--------------------------------------------------DISPLAY PLAYER HP----------------------------------------------------------------

    private static void displayPlayerHP () {
        System.out.println("Your health: " + p1.getHealth());
    }
//-----------------------------------------------ATTACKS THE PLAYER-------------------------------------------------------------------

    private static void attackPlayer (String monsterName) {
        if (map.get(returnCurrentRoomID()).getMonsters().get(0).getName().equalsIgnoreCase(monsterName)) {
            Game.p1.setHealth((p1.getHealth()) - (map.get(returnCurrentRoomID()).getMonsters().get(0).getAttackPoints()));
        }
    }
//----------------------------------------------RETURNS THE CURRENT ROOM ID--------------------------------------------------------------------

    private static int returnCurrentRoomID() { //returns the current room id where containsplayer = true
        int roomID=0;
        for (int i = 0; i <= map.size(); i++) {
            if (map.get(i).isContainsPlayer()) {
                roomID=map.get(i).getRoomID();
                break;
            }
        }
        return roomID;
    }
//------------------------------------------PRINT ANY ROOM INFO------------------------------------------------------------------------
    private static void printExamineRoom() {
        if (!map.get(returnCurrentRoomID()).getRoomInventory().isEmpty()) {
            System.out.println("This room contains an item.");
        }
        if (!map.get(returnCurrentRoomID()).getMonsters().isEmpty()) {
            System.out.println("This room contains a monster.");
        }
        if (!map.get(returnCurrentRoomID()).getPuzzles().isEmpty()) {
            System.out.println("This room contains a puzzle.");
        }
        if (map.get(returnCurrentRoomID()).isHasBeenVisited()) {
            System.out.println("This room seems familiar.");
        }
    }
//------------------------------------------------------------------------------------------------------------------
}
