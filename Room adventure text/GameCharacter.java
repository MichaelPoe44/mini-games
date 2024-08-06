
// Michael Poe
// Program 5 Csc 132


class GameCharacter{

    private String name;
    private int lives;
    public String[] inventory;
    private int MAXLIVES = 5;
    

    public GameCharacter(){
        this.name = "Sam Sung";
        this.lives = this.MAXLIVES;
        this.inventory = new String[5]; // Which is right????
    }

    public GameCharacter(String name, int lives){
        this.name = name;
        setLives(lives);
        this.inventory = new String[5]; //??????
        
    }
    // Name
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    // lives
    public int getLives(){
        return lives;
    }

    public void setLives(int lives){
        if (0<=lives && lives<=MAXLIVES){
         this.lives = lives;
        }
        else{
            this.lives = MAXLIVES;
        }
    }
    // inventory
    public String[] getInventory(){
        
        return inventory;
    }

    public void setInventory(String[] inventory){
        if (inventory.length <= 5){
            this.inventory = inventory;
        }
    }
    

    public boolean isAlive(){
        if (lives>0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean hasWeapon(){
        for (int i=0; i<inventory.length;i++){
            if (inventory[i] != null){
                if (inventory[i].equals("knife") || inventory[i].equals("gun")){
                return true;
                }
            }
        }
        return false;
    }

    public int sizeOfInventory(){
        int counter = 0;
        for (int i=0;i<inventory.length;i++){
            if (inventory[i] != null){
                counter += 1;
            }
        }
        return counter;
    }

    public void heal(){
        lives = MAXLIVES;
    }

    public void damage(){
        if (isAlive()){
            lives -= 1;
        }
    }

    public void pickUp(String item){
        for (int i=0; i<inventory.length;i++){
            if (inventory[i] == null){
                inventory[i] = item;
                break;
            }
        }
    }

    public void drop(String item){
        for (int i=0; i<inventory.length;i++){
            if (inventory[i] == item){
                inventory[i] = null;
                break;
            }
        }
    }

    public String toString(){
        String list = "";
        for (int i=0;i<inventory.length;i++){
            if (inventory[i] != null){
                list += inventory[i]+", ";
            }
        }
        return "Name:   "+name+"\nLives:    "
        +lives+"\nInventory:     "+list+"\n";
    }

















}