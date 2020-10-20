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
                    userInput.contains("inventory")){
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
                    String itemName = roomsString[i].substring(indexOfItemName,indexOfItemDesc-13).trim();
                    String itemDesc = roomsString[i].substring(indexOfItemDesc,indexOfItemPickup-8).trim();
                    String itemPickup = roomsString[i].substring(indexOfItemPickup).trim();
                    Item item = new Item(itemName,itemDesc,itemPickup);
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

}