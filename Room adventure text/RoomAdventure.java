import java.util.Scanner;

// Name: Michael Poe
// Description: Room adventure Java
// 



class RoomAdventure{
    private static Room currentRoom;
    private static String[] inventory = new String[3];
    private static String status;

    final private static String DEFAULT_STATUS = "Sorry. Try Verbs:(go,look,take,fight) with:(something you see)";



    public static void main(String[] args){
        setupGame();
        
        while (true){
            System.out.println(currentRoom.toString());
            System.out.print("Inventory: ");
            for (int i=0; i < inventory.length; i++){
                if (inventory[i] != null){
                    System.out.print(inventory[i] + " ");
                }

            }
            System.out.println("\nWhat would you like to do? ");

            // take input
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();

            //Proccess input 
            String[] words = input.split(" ");
            
            if (words.length == 2){
                String verb = words[0];
                String noun = words[1];



                 switch (verb){
                    case "go":
                        handleGo(noun);
                        break;

                    case "look":
                        handleLook(noun);
                        break;

                    case "take":
                        handleTake(noun);
                        break;
                    case "fight":
                        handleFight(noun);
                        break;
                    default:
                    status = DEFAULT_STATUS;
                }
            }
            else{
                status = DEFAULT_STATUS;
    
            }

            System.out.println(status);
            System.out.println("-----------------------------------------");
            
        }
    }

    private static void handleGo(String noun){
        Exit[] exits = currentRoom.getExits();
        
        status = "I dont see that room.";
        for (int i=0;i<exits.length;i++){
            if (noun.equals(exits[i].direction)){
                currentRoom = exits[i].destination;
                status = "Changed Room.";
            }
        }
    }

    private static void handleLook(String noun){

        Item[] items = currentRoom.getItems();
        status = "I dont see that";
        for (int i=0; i < items.length;i++){
            if (items[i] != null && noun.equals(items[i].name)){
                status = items[i].description;
            }
        }
    }

    private static void handleTake(String noun){

        String[] grabbables = currentRoom.getGrabbables();
        status = "I cant grab that";
        for (int i=0; i < grabbables.length; i++){
            if (noun.equals(grabbables[i])){
                for (int j=0; j < inventory.length; j++){
                    if (inventory[j] == null){
                        inventory[j] = noun;
                        currentRoom.removeItem(noun);
                        status = "added it to inventory";
                        break;
                    }
                }
            }
        }

        
    }

    private static void handleFight(String noun){
        if (noun.equals("Malenia")){
            int j = 0;
            for (int i=0; i < inventory.length; i++){
                if (inventory[i] != null){
                    j ++;
                }

            }
            if (j==3){
                status = "Malenia is too strong for you no matter what you do. You got killed";
            }
            else{
                status = "Malenia killed you. Maybe you just needed more gear?";
            }
            System.out.println("========================================================================");
            System.out.println(status);
            System.out.println("========================================================================");
            System.exit(0);

        }
        else{
            status = "cant fight that silly goose";
        }
    }






    private static void setupGame(){
        Room r1 = new Room("Room 1");
        Room r2 = new Room("Room 2");
        Room r3 = new Room("Room 3");
        Room r4 = new Room("Room 4");


        //Room 1
        Exit r1east = new Exit("east",r2);
        Exit r1south = new Exit("south",r3);
        Exit[] r1exits = {r1east, r1south};
        

        Item chair = new Item("chair","its a wooden chair",false,false);
        Item table = new Item("table","It has a sword on it",false,false);
        Item sword = new Item("sword","a very cool looking sword",true,false);
        Item[] r1items = {chair,table,sword};

        r1.setExits(r1exits);
        r1.setItems(r1items);
        


        //Room 2
        Exit r2west = new Exit("west",r1);
        Exit r2south = new Exit("south",r4);
        Exit[] r2exits = {r2west, r2south};
        
    
        Item armorstand = new Item("armorstand","it has armor on it",false,false);
        Item armor = new Item("armor","Its big and shiny",true,false);
        Item warning = new Item("note","It has a warning about a strong enemy",false,false);
    
        Item[] r2items = {armorstand,armor,warning};

        r2.setExits(r2exits);
        r2.setItems(r2items);
       


        
        // Room 3
        Exit r3east = new Exit("east",r4);
        Exit r3north = new Exit("north",r1);
        Exit[] r3exits = {r3east, r3north};
        
        Item Malenia = new Item("Malenia","She is the Blade of Miquella and has never known defeat",false,true);
        Item[] r3items = {Malenia};

        r3.setExits(r3exits);;
        r3.setItems(r3items);


        //room 4
        Exit r4west = new Exit("west",r3);
        Exit r4north = new Exit("north",r2);
        Exit[] r4exits = {r4west, r4north};
        

        Item shelf = new Item("shelf","It has potions on it",false,false);
        Item potions = new Item("health_potions","They have red liquid that heals you",true,false);
        Item painting = new Item("painting","depicts a woman with red hair standing over a mountain of bodies",false,false);
        Item[] r4items = {shelf, potions, painting};

        r4.setExits(r4exits);;
        r4.setItems(r4items);
        
        currentRoom = r1;
    }

    




}



class Room{

    private String name;

    private Exit[] exits;
    private Item[] items;
   

    public Room(String name){
        this.name = name;
    }


    // Room directions
    public Exit[] getExits(){
        return this.exits;
    }
    public void setExits(Exit[] exit){
        this.exits = exit;
    }
    
    


    // Items
    public Item[] getItems(){
        return this.items;
    }
    public void setItems(Item[] items){
        this.items = items;
    }

    public String[] getGrabbables(){
        String[] grabbables = {null};
        for (int i=0; i < items.length; i++){
            if (items[i] != null && items[i].is_grabbable){
                grabbables[0] = items[i].name;
            }
        }
        return grabbables;
    }
    public void removeItem(String item){
        for (int i=0; i < items.length; i++){
            if (items[i].name.equals(item)){
                items[i] = null;
            }
        }
    }
   

    public String toString(){
        String result = "\n";

        result += "Location: " + name;
        result += "\nYou see: ";
        
        for (int i=0; i<items.length; i++){
            if (items[i] != null){
                result += items[i].name + " "; 
            }
        }
            
        
        result += "\nExits to the: ";
        // for each loop
        for (Exit exit : exits){
            result += exit.direction + " ";
        }

        return result + "\n";
    }









}






class Exit{

    String direction;
    Room destination;

    public Exit(String direction,Room destination){
        this.direction = direction;
        this.destination = destination;
    }   //decided not to use the setters for instantiation since i am instantiating them with valid data

    public String getDirection(){
        return this.direction;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }

    public Room getDestination(){
        return this.destination;
    }

    public void setDestination(Room destination){
        this.destination = destination;
    }

}





class Item{

    String name;
    String description;
    boolean is_grabbable;
    boolean fightable;

    public Item(String name, String description, boolean is_grabbable, boolean fightable){
        this.name = name;
        this.description = description;
        this.is_grabbable = is_grabbable;
        this.fightable = fightable;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String info){
        this.description = info;
    }

    public boolean getIsGrabbable(){
        return this.is_grabbable;
    }

    public void setIsGrabbable(boolean value){
        this.is_grabbable = value;
    }

    public boolean getIsFightable(){
        return this.fightable;
    }


}
