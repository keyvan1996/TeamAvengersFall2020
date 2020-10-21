import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Game {

    private static Map<Integer, Room> rooms;
    private static int currentRoomId;
    private static ArrayList<Item> pickupedItems;
    //=============================================Methods==============================================
    public static void main(String[] args) {
        //-------------------------Init fields--------------------------
        rooms = new HashMap<>();
        currentRoomId = 1;
        pickupedItems = new ArrayList<>();

        //-------------------------call read from file methods--------------------------
        readFileAndPutInMap("rooms.txt");
        readFileAndPutInMap("puzzles.txt");
        readFileAndPutInMap("items.txt");
        readFileAndPutInMap("monsters.txt");


        //-------------------------main--------------------------
        Scanner userCommand = new Scanner(System.in);  // Create a Scanner object

        String userInput = "";
        System.out.println("Welcome");
        System.out.println("You can drop, pickup, inspect items with below format:");
        System.out.println("command item_name");
        System.out.println("============================================");
        while (!userInput.equals("exit")){
            printRoomsItem(currentRoomId);
            printRoomInformationAndSetVisited(currentRoomId);
            userInput = userCommand.nextLine();
            if (userInput.contains("pickup") ||
                    userInput.contains("drop") ||
                    userInput.contains("inspect") ||
                    userInput.contains("inventory") ||
                    userInput.contains("unequip") ||
                    userInput.contains("heal") ||
                    userInput.contains("equip")){
                String itemName = "";

                switch (userInput.split(" ")[0]){
                    case "pickup":
                        itemName = userInput.substring(userInput.indexOf("pickup ")+7);
                        pickupItem(itemName);
                        break;
                    case "drop":
                        itemName = userInput.substring(userInput.indexOf("drop ")+5);
                        dropItem(itemName);
                        break;
                    case "inspect":
                        itemName = userInput.substring(userInput.indexOf("inspect ")+8);
                        inspectItem(itemName);
                        break;

                    case "inventory":
                        itemName = userInput.substring(userInput.indexOf("inventory: ")+4);
                        checkInventory(itemName);
                        break;

                    case "equip":
                        itemName = userInput.substring(userInput.indexOf("equip ")+6);
                        equip(itemName);
                        break;

                    case "unequip":
                        itemName = userInput.substring(userInput.indexOf("unequip ")+8);
                        unequip(itemName);
                        break;

                    case "heal":
                        itemName = userInput.substring(userInput.indexOf("heal ")+5);
                        heal(itemName);
                        break;
                }
            }else{
                moveBetweenRooms(userInput);

            }

        }


    }

    //-------------------------Read Directions and Rooms from file--------------------------

    private static void readFileAndPutInMap(String filePath){


        String content = "";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            content = String.join(System.lineSeparator(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] roomsString = content.split("-------------------------------------");


        for(int i = 0 ; i < roomsString.length; i++){

            switch (filePath){

                case "rooms.txt":
                    roomsString[i] = roomsString[i].trim();
                    int indexOfId = 4;
                    int indexOfName = roomsString[i].indexOf("name: ")+6;
                    int indexOfDesc = roomsString[i].indexOf("description: ")+13;
                    int indexOfDirs = roomsString[i].indexOf("directions: ")+12;
                    int roomId = Integer.parseInt(roomsString[i].substring(indexOfId,indexOfName-7));
                    String roomName = roomsString[i].substring(indexOfName,indexOfDesc-13);
                    String roomDesc = roomsString[i].substring(indexOfDesc,indexOfDirs-12);
                    String roomDirs = roomsString[i].substring(indexOfDirs);
                    rooms.put(roomId,new Room(roomId,roomName,roomDesc,roomDirs,false,new ArrayList<Item>()));
                    break;
                case "items.txt":
                    roomsString[i] = roomsString[i].trim();
                    int indexOfItemName = roomsString[i].indexOf("name: ")+6;
                    int indexOfItemDesc = roomsString[i].indexOf("description: ")+13;
                    int indexOfItemPickup = roomsString[i].indexOf("pickup: ")+8;
                    int indexOfItemHeal = roomsString[i].indexOf("heal: ")+6;
                    int indexOfItemDamage = roomsString[i].indexOf("damage: ")+8;
                    int indexOfItemConsume = roomsString[i].indexOf("consume: ")+9;
                    int indexOfItemEquipped = roomsString[i].indexOf("equipped: ")+10;
                    String itemName = roomsString[i].substring(indexOfItemName,indexOfItemDesc-13).trim();
                    String itemDesc = roomsString[i].substring(indexOfItemDesc,indexOfItemPickup-8).trim();
                    String itemPickup = roomsString[i].substring(indexOfItemPickup, indexOfItemHeal-6).trim();
                    String itemHeal = roomsString[i].substring(indexOfItemHeal,indexOfItemDamage-8).trim();
                    String itemDamage = roomsString[i].substring(indexOfItemDamage,indexOfItemConsume-9).trim();
                    String itemConsume = roomsString[i].substring(indexOfItemConsume,indexOfItemEquipped-10).trim();
                    boolean itemConsumable = Boolean.parseBoolean(itemConsume);
                    String itemEquipped = roomsString[i].substring(indexOfItemEquipped).trim();
                    boolean itemEquipped1 = Boolean.parseBoolean(itemEquipped);
                    Item item = new Item(itemName,itemDesc,itemPickup, itemHeal, itemDamage, itemEquipped1, itemConsumable);
                    if (i == 2){
                        rooms.get(1).getItemsInRoom().add(item);
                    }else  if (i == 1){
                        rooms.get(3).getItemsInRoom().add(item);

                    }else {
                        rooms.get(4).getItemsInRoom().add(item);

                    }
                    break;
                case "puzzles.txt":
                    roomsString[i] = roomsString[i].trim();
                    int indexOfQuestion = roomsString[i].indexOf("question: ")+10;
                    int indexOfanswer = roomsString[i].indexOf("answer: ")+8;
                    String puzzleQuesstion = roomsString[i].substring(indexOfQuestion,indexOfanswer-9).trim();
                    String puzzleAnswer = roomsString[i].substring(indexOfanswer).trim();
                    Puzzle puzzle = new Puzzle(puzzleQuesstion,puzzleAnswer,false,3);
                    if (i == 2){
                        rooms.get(2).setPuzzle(puzzle);
                    }else  if (i == 1){
                        rooms.get(4).setPuzzle(puzzle);
                    }else {
                        rooms.get(5).setPuzzle(puzzle);
                    }
                    break;
                case "monsters.txt":
                    roomsString[i] = roomsString[i].trim();
                    int indexOfmonsterId = roomsString[i].indexOf("id: ")+4;
                    int indexOfname = roomsString[i].indexOf("name: ")+6;
                    int indexOfdescription = roomsString[i].indexOf("description: ")+13;
                    int indexOfhealth = roomsString[i].indexOf("health points: ")+15;
                    int indexOfactions = roomsString[i].indexOf("actions: ")+9;
                    //placeholders until i understand what the numbers mean exactly.
                    int monsterId = 0;
                    String monsterName = "";
                    String monsterDesc = "";
                    int monsterHP = 0;
                    String monsterAction = "";
                    break;

            }


        }

    }


    //-------------------------Print Room Info and Set Visit bool--------------------------
    private static void printRoomInformationAndSetVisited(int id){
        System.out.println("You are at room "+ id);
        System.out.println(rooms.get(id).getDescription());
        System.out.println("Choose a direction or write exit to shutdown the game.(N,S,E,W)");
        if (rooms.get(currentRoomId).getVisited())
            System.out.println("you've been here");
        rooms.get(id).setVisited(true);

    }



    //-------------------------Check has a direction or not--------------------------
    //returns room id if exist and if not returns 0
    private static void moveBetweenRooms(String direction){
        String roomDirs = rooms.get(currentRoomId).getDirs();
        if (roomDirs.contains(direction))
            if (rooms.get(Integer.valueOf(roomDirs.substring(roomDirs.indexOf(direction)+2,roomDirs.indexOf(direction)+3))).getPuzzle() == null){
                currentRoomId =  Integer.valueOf(roomDirs.substring(roomDirs.indexOf(direction)+2,roomDirs.indexOf(direction)+3));

            }
            else {
                int id = Integer.valueOf(roomDirs.substring(roomDirs.indexOf(direction)+2,roomDirs.indexOf(direction)+3));
                boolean endPuzzle = true;
                System.out.println("=============Puzzle==============");
                while (endPuzzle){
                    System.out.println(rooms.get(id).getPuzzle().getQuestion()+" \nattend: "+rooms.get(id).getPuzzle().getAttends());
                    Scanner userCommand = new Scanner(System.in);  // Create a Scanner object
                    String userInput = "";
                    userInput = userCommand.nextLine();
                    if(userInput.equals(rooms.get(id).getPuzzle().getAnswer())){
                        currentRoomId =  id;
                        rooms.get(id).setPuzzle(null);
                        System.out.println("You solved the puzzle");

                        endPuzzle = false;
                    }else {
                        rooms.get(id).getPuzzle().setAttends(rooms.get(id).getPuzzle().getAttends()-1);
                        if (rooms.get(id).getPuzzle().getAttends() == 0){
                            System.out.println("You couldn't solve the puzzle");
                            endPuzzle = false;
                            rooms.get(id).getPuzzle().setAttends(3);

                        }
                    }



                }
            }
        else {
            System.out.println("You can’t go this way");
        }


    }

    //-------------------------Print Room Info and Set Visit bool--------------------------
    private static void printRoomsItem(int id){
        if (rooms.get(id).getItemsInRoom().size() == 0)
            System.out.println("There is no item in room");
        else
            System.out.println("Items in room:");

        for (int i = 0; i < rooms.get(id).getItemsInRoom().size();i++){
            System.out.println(rooms.get(id).getItemsInRoom().get(i).getName());

        }

    }


    //-------------------------drop Item--------------------------
    private static void dropItem(String item){
        for (Item itemInRoom:pickupedItems) {
            if (itemInRoom.getName().equals(item)){
                rooms.get(currentRoomId).getItemsInRoom().add(itemInRoom);
                pickupedItems.remove(itemInRoom);
                System.out.println("You Drop '"+item+"' successfully.");
                return;
            }
        }
        System.out.println("There is no item with name '"+item+"'");
    }
    //-------------------------pickup Item--------------------------
    private static void pickupItem(String item){
        for (Item itemInRoom:rooms.get(currentRoomId).getItemsInRoom()) {
            if (itemInRoom.getName().equals(item)){
                pickupedItems.add(itemInRoom);
                rooms.get(currentRoomId).getItemsInRoom().remove(itemInRoom);
                System.out.println("You pickup '"+item+"' successfully.");
                System.out.println(itemInRoom.getPickup());
                return;

            }
        }
        System.out.println("There is no item with name '"+item+"'");

    }
    //-------------------------Inspect Item--------------------------
    private static void inspectItem(String item){
        for (Item itemInRoom:pickupedItems) {
            if (itemInRoom.getName().equals(item)){
                System.out.println(itemInRoom.getDescription());
                return;
            }
        }
        System.out.println("There is no item with name '"+item+"'");
    }
    //------------------------check inventory------------------------
    private static void checkInventory(String item) {
        System.out.println("player inventory contains:");
        for (Item itemInRoom:pickupedItems) {
            System.out.println(itemInRoom.getName());
        }
    }
    //------------------------equip------------------------
    private static void equip(String item) {
        for(Item itemInRoom:pickupedItems) {
            if (item.equalsIgnoreCase(itemInRoom.getName()))
            {
                if (Player.isEquipped())
                {
                    System.out.println("An item is already equipped.");
                }
                else
                {
                    int damage = Integer.parseInt(itemInRoom.getDamage());
                    System.out.println(itemInRoom.getName() + " has successfully been equipped.");
                    Player.setAttackPower(Player.getAttackPower() + damage);
                    itemInRoom.setEquipped(true);
                    Player.setEquipped(true);
                    Player.setEquippedItem(itemInRoom.getName());
                    System.out.println("Your current Attack Power is: " + Player.getAttackPower());
                }
            }
        }
    }
    //------------------------unequip------------------------
    public static void unequip(String item)
    {
        for(Item itemInRoom:pickupedItems)        {
            if (itemInRoom.isEquipped())
            {
                int damage = Integer.parseInt(itemInRoom.getDamage());
                System.out.println(itemInRoom.getName() + " has successfully been unequipped.");
                Player.setAttackPower(Player.getAttackPower() - damage);
                itemInRoom.setEquipped(false);
                Player.setEquipped(false);
                Player.setEquippedItem(null);
                System.out.println("Your current Attack Power is: " + Player.getAttackPower());
            }
        }
    }
    //------------------------heal------------------------
    public static void heal(String item)
    {
        if (Player.isEquipped())
        {
            for(Item itemInRoom:pickupedItems)
            {
                if (itemInRoom.isEquipped() && Player.getEquippedItem().equals(itemInRoom.getName()))
                {
                    if (itemInRoom.isConsume())
                    {
                        int heal = Integer.parseInt(itemInRoom.getHeal());
                        System.out.println(itemInRoom.getName() + " has successfully been consumed & the player has been healed.");
                        Player.setHP(Player.getHP() + heal);
                        //Player.getInventory().remove(i);
                        Player.setEquipped(false);
                        Player.setEquippedItem(null);
                        System.out.println("Your current HP is: " + Player.getHP());
                    }
                    else
                    {
                        System.out.println("This item cannot be consumed.");
                    }
                }
            }
        }
        else
        {
            System.out.println("Before healing you must equip the item first.");
        }
    }
}