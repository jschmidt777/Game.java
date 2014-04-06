import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Game {

    //
    // Public
    //

    // Globals
    public static final boolean DEBUGGING  = false; // Debugging flag. Don't think this will work with the linked list.
    public static final int MAX_LOCALES = 9;    // Total number of rooms/locations we have in the game.   
    public static String command;               // What the player types as he or she plays the game.
    public static boolean stillPlaying = true; // Controls the game loop.
    public static Locale[] locations;           // An uninitialized array of type Locale. See init() for initialization.
    static Ground loc0 = new Ground(0);  				//Locale (0) and head of the list
    public static Locale currentLocale = loc0;        // Player starts at Earth. Essentially, this is the head of the linked list.
    public static int[][]  nav;                 // An uninitialized array of type int.
    public static int moves = 0;                // Counter of the player's moves.
    public static int score = 0;                // Tracker of the player's score.
    public static Items[] ItemsArr;				//An array of type Items to hold the possible items.
    public static Items[] Inventory;            //An array of type Items to hold the player's items.
    public static Items[] storeMap;             // Stores the player's map.
   
    
    public static void main(String[] args) {
    	Inventory = new Items[4];
        storeMap = new Items[1];
  		  
        if (DEBUGGING) {
            // Display the command line args.
            System.out.println("Starting with args:");
            for (int i = 0; i < args.length; i++) {
                System.out.println(i + ":" + args[i]);
            }
        }
        
    //All of the following code is from the in-class code from ListTester.    
     
        
        // Make the list manager.
       
        // Declare an array for the items.
       

        
        
    


        // Set starting locale, if it was provided as a command line parameter.
        /*
        if (args.length > 0) {
            try {
                int startLocation = Integer.parseInt(args[0]);
                // Check that the passed-in value for startLocation is within the range of actual locations.
                if ( startLocation >= 0 && startLocation <= MAX_LOCALES) {
                    currentLocale = startLocation;
                } else {
                    System.out.println("WARNING: passed-in starting location (" + args[0] + ") is out of range.");
                }
            } catch(NumberFormatException ex) {
                System.out.println("WARNING: Invalid command line arg: " + args[0]);
                if (DEBUGGING) {
                    System.out.println(ex.toString());
                }
            }
        }
        */

        // Get the game started.
        init();
        updateDisplay();

        // Game Loop
        while (stillPlaying) {
            getCommand();
            navigate();
            updateDisplay();
           
        }

        // We're done. Thank the player and exit.
        System.out.println("Thank you for playing.");
    }

    //
    // Private
    //
    /*
 private static String showMap(){
    final String fileName = "space.txt";
    File myFile = new File(fileName);
   
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();	
    	sb.append(line);
 	}
 	
    
   try {
    
    	Scanner input = new Scanner(myFile);
    	//TODO: how to put the results of this into the desc of the map 
    	//( so that you only see the map when you have it an when you first encounter it on earth)?
		while(input.hasNext()){
			String line = input.nextLine();
			System.out.println(line);
			
		}
		input.close();
	} catch (FileNotFoundException ex) {
		
		System.out.println("File not found" + ex.toString());
	}
 
   
    // return sb.toString();
     
  
 }  
 */

 
 // Make the list manager.
    static ListMan lm1 = new ListMan();
   // lm1.setName("Magic Items");
   // lm1.setDesc("These are some of my favorite things.");

    final static String fileName = "magicitems.txt";
    
    //
    // Private
    //
    private static ListItem sequentialSearch(ListMan lm,
                                             String target) {
        ListItem retVal = null;
        System.out.println("Searching for " + target + ".");
        int counter = 0;
        ListItem currentItem = new ListItem();
        currentItem = lm.getHead();
        boolean isFound = false;
        while ( (!isFound) && (currentItem != null) ) {
            counter = counter +1;
            if (currentItem.getName().equalsIgnoreCase(target)) {
                // We found it!
                isFound = true;
                retVal = currentItem;
            } else {
                // Keep looking.
                currentItem = currentItem.getNext();
            }
        }
        if (isFound) {
            System.out.println("Found " + target + " after " + counter + " comparisons.");
            return  currentItem;
        } else {
            System.out.println("Could not find " + target + " in " + counter + " comparisons.");
        }

        return retVal;
    }


    private static void readMagicItemsFromFileToList(String fileName,
                                                     ListMan lm) {
        File myFile = new File(fileName);
        try {
            Scanner input = new Scanner(myFile);
            while (input.hasNext()) {
                // Read a line from the file.
                String itemName = input.nextLine();

                // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setCost(Math.random() * 50);
                fileItem.setNext(null); // Still redundant. Still safe.

                // Add the newly constructed item to the list.
                lm.add(fileItem);
            }
            // Close the file.
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. " + ex.toString());
        }

    }

    private static void readMagicItemsFromFileToArray(String fileName,
                                                      ListItem[] items) {
        File myFile = new File(fileName);
        try {
            int itemCount = 0;
            Scanner input = new Scanner(myFile);

            while (input.hasNext() && itemCount < items.length) {
                // Read a line from the file.
                String itemName = input.nextLine();

                // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setCost(Math.random() * 50);
                fileItem.setNext(null); // Still redundant. Still safe.

                // Add the newly constructed item to the array.
                items[itemCount] = fileItem;
                itemCount = itemCount + 1;
            }
            // Close the file.
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. " + ex.toString());
        }
    }

    private static void selectionSort(ListItem[] items) {
        for (int pass = 0; pass < items.length-1; pass++) {
            // System.out.println(pass + "-" + items[pass]);
            int indexOfTarget = pass;
            int indexOfSmallest = indexOfTarget;
            for (int j = indexOfTarget+1; j < items.length; j++) {
                if (items[j].getName().compareToIgnoreCase(items[indexOfSmallest].getName()) < 0) {
                    indexOfSmallest = j;
                }
            }
            ListItem temp = items[indexOfTarget];
            items[indexOfTarget] = items[indexOfSmallest];
            items[indexOfSmallest] = temp;
        }
    }
   
   
   
 
    
	private static void init() {
        // Initialize any uninitialized globals.
        command = new String();
        stillPlaying = true; 
        System.out.println("Solar Sysdoom V3 (Java Edition)");  
        
        Items item0 = new Items(0);
	  		item0.setName(" Piece One.");
	  		item0.setDesc(" This is the first piece of the device.");
	  		
	  	Items item1 = new Items(1);
  			item1.setName(" Piece Two.");
  			item1.setDesc(" This is the second piece of the device.");
  		
  		Items item2 = new Items(2);
	  		item2.setName(" Piece Three.");
	  		item2.setDesc(" This is the third piece of the device.");
	  		
	  	Items item3 = new Items(3);
	  		item3.setName(" Piece Four.");
	  		item3.setDesc(" This is the fourth piece of the device."); 
  		
	  	Items item4 = new Items(4);
  			item4.setName("\n"+ "Turbo boosters" + "\n" + "Lasers" + "\n" + "SpaceShip Upgrade" + "\n" );
  			item4.setDesc("These will help you with your journey."); 
  		
  	        
  		Items item5 = new Items(5);
  			item5.setName(" Map (this is an item). ");
  			item5.setDesc("This is the map: " + "\n" + 
  				    "                  Neptune " + "\n"+
                    "                     ^    " + "\n"+
                    "                     |                                                         Magick Shoppe" + "\n"+
                    "                     |                                                              ^ " + "\n"+
                    "                     |                                                              | " + "\n"+
                    "                     |                                                              | " + "\n"+
                    "                     |                                                              | " + "\n"+
                    "                   Venus <--------------------------------- Mars ---------------> Saturn " + "\n"+
                    "                     |                                        ^                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     |                 SUN                 Mercury                    " + "\n"+
                    "                     |                                        ^                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     |                                        |                       " + "\n"+
                    "                     V                                        |                       " + "\n"+
                    "                   Jupiter                                  Earth                     " + "\n"+
                    "                     |                                                                " + "\n"+
                    "                     |                                                                " + "\n"+
                    "                     |                                                                " + "\n"+
                    "                     |                                                                " + "\n"+
                    "                     V                                                                " + "\n"+
                    "                    Uranus "    );
  		
   ItemsArr = new Items[6];
   			ItemsArr[0]= item0;
   			ItemsArr[1]= item1;
   			ItemsArr[2]= item2;
   			ItemsArr[3]= item3;
   			ItemsArr[4]= item4;
   			ItemsArr[5]= item5;

        // Set up the location instances of the Locale class.
   			
   		    //Attributes for loc0 
	        loc0.setName("Earth");
	        loc0.setDesc("You are on the surface of your home planet.");
	        loc0.setItem(item5);
	        loc0.setNearestPlanet("Mercury(north).");
	        
         Space loc1 = new Space(1);   //Locale (1)
	        loc1.setName("Mercury");
	        loc1.setDesc("This planet is closest to the sun, so it goes the fastest around it. Hang on! You're orbiting the planet.");
	        loc1.setItem(null);
	        loc1.setNearestPlanet("Mars(north) and Earth(south).");
	        loc1.setHasItem(false);
	       
	    Ground loc2 = new Ground(2);   //Locale (2)
		    loc2.setName("Mars");
		    loc2.setDesc("We may go to this planet someday. That would be sweet. You landed on it's surface.");
		    loc2.setItem( item0);
		    loc2.setNearestPlanet("Mercury(south), Venus(west) and Saturn(east).");
	        
   		Space loc3 = new Space(3);   //Locale (3)
   	        loc3.setName("Saturn");
   	        loc3.setDesc("A gas giant, rings, what's not to love about this planet? You're orbiting the planet.");
   	        loc3.setItem(null);
   	        loc3.setNearestPlanet("Mars(west) and the Magick Shoppe is north.");
   	        loc3.setHasItem(false);
   	        
   	    Ground loc4 = new Ground(4);   //Locale (4)
   	        loc4.setName("Venus");
   	        loc4.setDesc("Named after the Roman god of beauty and love, this planet is hot." +
   	                    " The surface of the planet, I mean." );
   	        loc4.setItem(item1);
   	        loc4.setNearestPlanet("Mars(east), Neptune(north) and Jupiter(south).");
   	       
   	        
   	 
	    Space loc5 = new Space(5);   //Locale (5)
	        loc5.setName("Neptune");
	        loc5.setDesc("This planet has so many moons, its nickname is moony (not really). You're orbiting the planet.");
	        loc5.setItem(null);
	        loc5.setNearestPlanet("Venus(south).");
	        loc5.setHasItem(false);
	        
	        
	    Space loc6 = new Space(6);   //Locale (6)
	        loc6.setName("Jupiter");
	        loc6.setDesc("Probably the most intimidating planet in our solar system." + 
	                                            " I mean, its got an eye that's also a giant tornado. You're orbiting the planet.");
	        loc6.setItem(item2);
	        loc6.setNearestPlanet("Venus(north) and Uranus(south).");
	        
	        
        Space loc7 = new Space(7);   //Locale (7)
	        loc7.setName("Uranus");
	        loc7.setDesc("If you think that name of this planet is funny," + 
	                                                "you're pronouncing it incorrectly, and you're immature. You're orbiting the planet.");
	        loc7.setItem(item3);
	        loc7.setNearestPlanet("Jupiter(north).");
	       
	    Space loc8 = new Space(8);   //Locale (8)
	        loc8.setName("Magick Shoppe");
	        loc8.setDesc("These are the magic items." ); 
	        loc8.setItem(item4);
	        loc8.setNearestPlanet("Saturn(south).");
	       
	        
	    //Set up map navigation
	        
	        //Earth
	        loc0.setNorth(loc1);
	        
	        //Mercury
	        loc1.setNorth(loc2);
	        loc1.setSouth(loc0);
	        
	        //Mars
	        loc2.setSouth(loc1);
	        loc2.setEast(loc3);
            loc2.setWest(loc4);
            
            //Saturn
            loc3.setNorth(loc8);
   	        loc3.setWest(loc2);
   	        
	        //Venus
   	        loc4.setNorth(loc5);
	        loc4.setSouth(loc6);
	        loc4.setEast(loc2);
	        
	        //Neptune
	        loc5.setSouth(loc4);
	        
	        //Jupiter
	        loc6.setNorth(loc4);
	        loc6.setSouth(loc7);
	        
	        //Uranus
	        loc7.setNorth(loc6);
		    
	        //Magick Shoppe
	        loc8.setSouth(loc3);
	        
        // Set up the location array.
        locations = new Locale[9];
        locations[0] = loc0; // "Earth"; 
        locations[1] = loc1; // "Mercury"; 
        locations[2] = loc2; // "Mars"; 
        locations[3] = loc3; // "Saturn"; 
        locations[4] = loc4; // "Venus"; 
        locations[5] = loc5; // "Neptune"; 
        locations[6] = loc6; // "Jupiter"; 
        locations[7] = loc7; // "Uranus"; 
        locations[8] = loc8; // Magick Shoppe;


        if (DEBUGGING) {
            System.out.println("All game locations:");
            for (int i = 0; i < locations.length; ++i) {
                System.out.println(i + ":" + locations[i].toString());
            }
        }
       
        // Set up the navigation matrix.
        /*
        nav = new int[][] {
                                 /* N   S   E   W */
                                 /* 0   1   2   3 */
        /*
        		                   { 1, -1, -1, -1 }, // Earth    
        		                   { 2, 0,-1, -1 },    //Mercury
        		                   { -1, 1, 3, 4},    //Mars                     
        		                   { 8, -1, -1, 2}, // Saturn   
        		                   { 5, 6, 2, -1},    //Venus
        		                   { -1, 4, -1, -1},  //Neptune
        		                   { 4, 7, -1, -1},   //Jupiter
        		                   { 6, -1, -1, -1},  //Uranus  
        		                   {-1, 3, -1, -1 }  //Magick Shoppe
        };

       */
       
        
    }
    
	 public static void magickShoppe(){
		    
		    readMagicItemsFromFileToList(fileName, lm1);
		    // Display the list of items.
		    // System.out.println(lm1.toString());

		    // Declare an array for the items.
		    ListItem[] items = new ListItem[lm1.getLength()];
		    readMagicItemsFromFileToArray(fileName, items);
		    // Display the array of items.
		    /*
		    System.out.println("Items in the array BEFORE sorting:");
		    for (int i = 0; i < items.length; i++) {
		        if (items[i] != null) {
		            System.out.println(items[i].toString());
		        }
		    }
				*/
		    selectionSort(items);
		   
		    System.out.println("These are the Magic Items:");
		    for (int i = 0; i < items.length; i++) {
		        if (items[i] != null) {
		            System.out.println(items[i].toString());
		        }
		    }
			
		    // Ask player for an item.
		    Scanner inputReader = new Scanner(System.in);
		    System.out.print("Welcome to the Magick Shoppe. What item would you like? ");
		    String targetItem = new String();
		    targetItem = inputReader.nextLine();
		    System.out.println();
		    
		    //Add in more to meet the requiements of the project
		    //Add a check to see if the player can buy the item (score is the cost)

		    ListItem li = new ListItem();
		    li = sequentialSearch(lm1, targetItem);
		    if (li != null) {
		        System.out.println(li.toString());
		    }else {
		     System.out.print("That item is not listed, search again? (or go south back to Saturn ");
		    }
		 

	 }	

    private static void updateDisplay() {
        System.out.println(currentLocale.getText());
        //this will just have to be changed to currentLocale
    }

    private static void getCommand() {
    	
        System.out.print("[" + moves + " moves, score " + score + ", achievement ratio " + ((float)score/moves) +", near planet(s) " + ((Space)  currentLocale).getNearestPlanet() + " ] ");
        Scanner inputReader = new Scanner(System.in);
        command = inputReader.nextLine();  // command is global.
       
       //Start with one move since you started the game. Also, I don't know how to make it bypass the divide by zero error.
       
        }
    
    
    

    private static void navigate() {
      //  final int INVALID = -1;
        //int dir = INVALID;  // This will get set to a value > 0 if a direction command was entered.
    	
    	if (        command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") ) {
            //dir = 0;
            goNorth(currentLocale, locations[8]);
        } else if ( command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") ) {
            //dir = 1;
            goSouth(currentLocale);
        } else if ( command.equalsIgnoreCase("east")  || command.equalsIgnoreCase("e") ) {
            //dir = 2;
            goEast(currentLocale);
        } else if ( command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w") ) {
            //dir = 3;
            goWest(currentLocale);
        } else if ( command.equalsIgnoreCase("quit")  || command.equalsIgnoreCase("q")) {
            quit();
        } else if ( command.equalsIgnoreCase("help")  || command.equalsIgnoreCase("h")) {
            help();
        } else if ( command.equalsIgnoreCase("dance")  || command.equalsIgnoreCase("d")){
        	System.out.print("You danced... Very poorly." + "\n");
        } else if ( command.equalsIgnoreCase("take")  || command.equalsIgnoreCase("t")){
        	take();
        } else if ( command.equalsIgnoreCase("inventory")  || command.equalsIgnoreCase("i")){
        	printInventory();
        } else if ( command.equalsIgnoreCase("map")  || command.equalsIgnoreCase("m")){
        	printMap();
        }else{
        	System.out.println("Please enter a valid command as listed: ");
        	help();
        };
            if (currentLocale.getHasVisited() != true) {
                
                moves = moves + 1;
                score = score + 5;
                currentLocale.setHasVisited(true); 
                
            }else if (currentLocale.getHasVisited() != false ){
            	
                moves = moves + 1;
                score = score;
            
            }
    }
    
          
           private static void goNorth(Locale loc, Locale locMS){
        	   
        	   if(loc.getNorth() != null){
        		   currentLocale = loc.getNorth();
        		   
        	   }else {
        		   System.out.println("You cannot got that way.");
        	   }
        	   if(currentLocale == locMS){
        		  magickShoppe(); 
        	   }else{
        		   //Don't do anything if the location is not the Magick Shoppe.
        	   }
           }
           
		   private static void goSouth(Locale loc){
			        	   
			   if(loc.getSouth() != null){
        		   currentLocale = loc.getSouth();
        	   }else {
			       System.out.println("You cannot got that way.");
			   }
		   }
			 
 		   private static void goEast(Locale loc){
				   
				   if(loc.getEast() != null){
					   currentLocale = loc.getEast();
				   }else {
					   System.out.println("You cannot got that way.");
				   }
			 	}
 
 		   private static void goWest(Locale loc){
	   
 				if(loc.getWest() != null){
 					currentLocale = loc.getWest();
 				}else {
 					System.out.println("You cannot got that way.");
 				}
 			}

    private static void help() {
        System.out.println("The commands are as follows:");
        System.out.println("   n/north");
        System.out.println("   s/south");
        System.out.println("   e/east");
        System.out.println("   w/west");
        System.out.println("   d/dance");
        System.out.println("   q/quit");
        System.out.println("   t/take");
        System.out.println("   i/inventory");
        System.out.println("   m/map");
        System.out.println("Collect the pieces of the device at the different planets.");
    }

    private static void quit() {
        stillPlaying = false;
    }
    
    
    
    public static void tookItem(Items item){
    	System.out.println("You took: " + item.getName());
    }
     
    public static void printMap(){
    	
    	for (int i = 0; i < storeMap.length; ++i){
			if(storeMap[i] != null){
				
			System.out.println(storeMap[i]);
			
			}else if (storeMap[i] == null) {
			
			System.out.println("You do not have the map.");
				
			}
			
		}
    	 
    }
    
    public static void printInventory(){
		System.out.println("Here is your inventory: ");
		
		for (int i = 0; i < Inventory.length; ++i){
			if(Inventory[i] != null){
				
			System.out.println(Inventory[i]);
			
			}else if (Inventory[i] == null) {
				
				
			}
			
		}
	}
  
    
    public static void take(){
    	Locale checkTake = currentLocale;
    	
    	if(checkTake == locations[0] && locations[0].getHasItem() != false){
    		storeMap[0] = ItemsArr[5];
    		locations[0].setHasItem(false);
    		tookItem(ItemsArr[5]);
    	
    	}else if(checkTake == locations[2] && locations[2].getHasItem() != false){
    		Inventory[0] = ItemsArr[0];
    		locations[2].setHasItem(false);
    		tookItem(ItemsArr[0]);
    		
    	}else if(checkTake == locations[4] && locations[4].getHasItem() != false){
    		Inventory[1] = ItemsArr[1];
    		locations[4].setHasItem(false);
    		tookItem(ItemsArr[1]);
    	
    	}else if(checkTake == locations[6] && locations[6].getHasItem() != false){
    		Inventory[2] = ItemsArr[2];
    		locations[6].setHasItem(false);
    		tookItem(ItemsArr[2]);
    	
    	}else if(checkTake == locations[7] && locations[7].getHasItem() != false){
    		Inventory[3] = ItemsArr[3];
    		locations[7].setHasItem(false);
    		tookItem(ItemsArr[3]);
    	
    	}else{
    		
    		System.out.println("You took nothing.");
    	}
    
    }
 }   
    
  