/*
 * author:	anky
 * date:	March 15, 2023
 * description:	A program to test out the students' implementations of
 * the GameCharacter class.
 */

class GameCharacterTest 
{
    public static void main(String [] args)
    {
	// test the constructors
	testConstructor();

	// testing input validation with accessors and mutators
	testAccess();
	
	// A sequence of commands/scenarios to test out the other
	// functions in the GameCharacter class.
	GameCharacter p1 = new GameCharacter("Allie Gater", 4), p2 = new GameCharacter("Anita Bath", 2);
	testPickUp(p1, p2);	// testing pickUp function
	testWeapon(p1, p2);	// testing hasWeapon and sizeOfInventory functions
	testDrop1(p1, p2);	// testing the drop functions.
	testDrop2(p1, p2);	// testing the drop functions some more.
	testDamage(p1, p2);	// testing the damage function
	testHeal(p1, p2);	// testing the heal function
    }
    
    // A function to test the constructors of the GameCharacter class.
    public static void testConstructor()
    {
	// testing the default constructor.
	GameCharacter p1 = new GameCharacter();
	System.out.println(p1);
	
	// testing the second constructor.
	GameCharacter p2 = new GameCharacter("Lori Driver", 3);
	System.out.println(p2);
	System.out.println("---------------------------------------------------------------------");
    }

    // A function to test the accessors and mutators of the
    // GameCharacter class.
    public static void testAccess()
    {
	GameCharacter p3 = new GameCharacter("Justin Thyme", 8);
	System.out.println(p3);

	p3.setLives(-4);
	p3.setName("Lipin Jection");
	String [] list = {"this", null, "is", null, "a", null, "test", null, "list", null, "with", null, "more", null, "elements"};
	p3.setInventory(list);
	System.out.println(p3);
	
	p3.setLives(10);
	p3.setName("Hugh Raye");
	String [] list2 = {null, null, "short", "list"};
	p3.setInventory(list2);
	System.out.println(p3);
	System.out.println("---------------------------------------------------------------------");
    }    
    
    // A function to test the pickUp capabilities of the GameCharacter
    // class.
    public static void testPickUp(GameCharacter p1, GameCharacter p2)
    {
	String [] things = {"baton", "knife", "cup", "stick", "pen", "gun", "pencil", "shoe", "bullet", "rock", "stone", "fish"};

	for (int i = 0; i < things.length; i+=2)
	{
	    p1.pickUp(things[i]);
	    p2.pickUp(things[i+1]);
	}

	System.out.println(p1);
	System.out.println(p2);
	System.out.println("---------------------------------------------------------------------");
    }

    // A function to test the hasWeapon and sizeOfInventory functions.
    public static void testWeapon(GameCharacter p1, GameCharacter p2)
    {
	System.out.println(p1.getName() + " has a weapon:\t" + p1.hasWeapon());
	System.out.println(p2.getName() + " has a weapon:\t" + p2.hasWeapon());
	System.out.println(p1.getName() + " has " + p1.sizeOfInventory() + " things in their inventory");
	System.out.println(p2.getName() + " has " + p2.sizeOfInventory() + " things in their inventory");
	System.out.println("---------------------------------------------------------------------");
    }

    // A function to test the drop function.
    public static void testDrop1(GameCharacter p1, GameCharacter p2)
    {
	p1.drop("gun");
	p2.drop("gun");
	System.out.println(p1.getName() + " has a weapon:\t" + p1.hasWeapon());
	System.out.println(p2.getName() + " has a weapon:\t" + p2.hasWeapon());
	System.out.println(p1.getName() + " has " + p1.sizeOfInventory() + " things in their inventory");
	System.out.println(p2.getName() + " has " + p2.sizeOfInventory() + " things in their inventory");
	System.out.println("---------------------------------------------------------------------");
    }

    // some more testing of the drop function of the GameCharacter class
    public static void testDrop2(GameCharacter p1, GameCharacter p2)
    {
	String [] items = {"baton", "cup", "pencil", "bullet", "pen", "shoe", "rock", "flipflop"};
	for (int i = 0; i < items.length; i++)
	{
	    p1.drop(items[i]);
	    p2.drop(items[i]);
	}
	System.out.println(p1.getName() + " has a weapon:\t" + p1.hasWeapon() +"\t and is " + (p1.isAlive() ? "ALIVE" : "DEAD"));
	System.out.println(p2.getName() + " has a weapon:\t" + p2.hasWeapon() +"\t and is " + (p2.isAlive() ? "ALIVE" : "DEAD"));
	System.out.println(p1.getName() + " has " + p1.sizeOfInventory() + " things in their inventory");
	System.out.println(p2.getName() + " has " + p2.sizeOfInventory() + " things in their inventory");
	System.out.println(p1);
	System.out.println(p2);
	System.out.println("---------------------------------------------------------------------");
    }

    // A function to test the damage functionality in the GameCharacter
    // class.
    public static void testDamage(GameCharacter p1, GameCharacter p2)
    {
	for (int i = 0; i < 3; i++)
	{
	    p1.damage();
	    p2.damage();
	}

	System.out.println(p1);
	System.out.println(p2);
	System.out.println(p1.getName() + " is " + (p1.isAlive() ? "ALIVE" : "DEAD"));
	System.out.println(p2.getName() + " is " + (p2.isAlive() ? "ALIVE" : "DEAD"));
	System.out.println("---------------------------------------------------------------------");
    }

    // A function to test the heal functionality in the GameCharacter
    // class.
    public static void testHeal(GameCharacter p1, GameCharacter p2)
    {
	p2.heal();
	System.out.println(p1);
	System.out.println(p2);
	System.out.println(p1.getName() + " is " + (p1.isAlive() ? "ALIVE" : "DEAD"));
	System.out.println(p2.getName() + " is " + (p2.isAlive() ? "ALIVE" : "DEAD"));
	System.out.println("---------------------------------------------------------------------");
    }
}
