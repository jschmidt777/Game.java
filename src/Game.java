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
    public static int score = 5;                // Tracker of the player's score.
    public static Items[] ItemsArr;				//An array of type Items to hold the possible items.
    public static Items[] Inventory;            //An array of type Items to hold the player's items.
    public static Items[] storeMap;             // Stores the player's map.
    public static String[] MagickItems;		// Stores the player's magick items. Will be printed out with the inventory.
    
    public static void main(String[] args) {
    	Inventory = new Items[4];
        storeMap = new Items[1];
        MagickItems = new String[1];
       // Make the list manager.
        ListMan lm1 = new ListMan();
       // lm1.setName("Magic Items");
       // lm1.setDesc("These are some of my favorite things.");

        final String fileName = "magicitems.txt";
        readMagicItemsFromFileToList(fileName, lm1);

	    // Declare an array for the items.
        ListItem[] items = new ListItem[lm1.getLength()];
	    readMagicItemsFromFileToArray(fileName, items);
	    // Display the array of items.
	    
	    selectionSort(items);
	    
        
        if (DEBUGGING) {
            // Display the command line args.
            System.out.println("Starting with args:");
            for (int i = 0; i < args.length; i++) {
                System.out.println(i + ":" + args[i]);
            }
        }
       
       
        // Get the game started.
        init();
        updateDisplay();
        
        

        // Game Loop
        while (stillPlaying) {
        	
        	if(Inventory[0]==ItemsArr[0] && Inventory[1]==ItemsArr[1] && Inventory[2]==ItemsArr[2] && Inventory[3]==ItemsArr[3] && score>=40  ){
        		//Win Condition: If the player has all of the pieces of the device, and they have gone to each planet (not necessarily the Magick Shoppe).
        		//They have to then type in a password to win.
        		puzzlePassWord();
        		
        	}else if(currentLocale != locations[8]){
	        	
	        		getCommand();
		            navigate();
		            updateDisplay();
	        	}else{
	        		
	        		magickShoppe(items, fileName, lm1);
	        		getCommand();

	        		navigate();
		            updateDisplay();
	        		
	        	}
        	
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
    
    //
    // Private
    //
   
    
    private static ListItem binarySearchArray(ListItem[] items,
            String target) {
			ListItem retVal = null;
			System.out.println("Binary Searching for " + target + ".");
			ListItem currentItem = new ListItem();
			boolean isFound = false;
			int counter = 0;
			int low  = 0;
			int high = items.length-1; // because 0-based arrays
			while ( (!isFound) && (low <= high)) {
			int mid = Math.round((high + low) / 2);
			currentItem = items[mid];
				if (currentItem.getName().equalsIgnoreCase(target)) {
						// We found it!
						isFound = true;
						retVal = currentItem;
				} else {
				// Keep looking.
						counter++;
				if (currentItem.getName().compareToIgnoreCase(target) > 0) {
				// target is higher in the list than the currentItem (at mid)
						high = mid - 1;
				} else {
				// target is lower in the list than the currentItem (at mid)
						low = mid + 1;
				}
			}
		}
				if (isFound) {
					System.out.println("Found " + target + " after " + counter + " comparisons.");
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
                }else{
                	
                }
            }
            ListItem temp = items[indexOfTarget];
            items[indexOfTarget] = items[indexOfSmallest];
            items[indexOfSmallest] = temp;
        }
    }
   
   
    private static void puzzlePassWord(){
    	String passWord = new String();
    	passWord= "pace";
    	System.out.println("Type in the password to win the game");
    	getCommand();
    	
    	if(command.equalsIgnoreCase(passWord)){
    		
    		System.out.println("You Win! You've collected all the pieces of the device, fused them together with the password, and saved the Solar System with the completed device!");
    	    quit();
    		
    	}else if(command.equalsIgnoreCase("i") || command.equalsIgnoreCase("inventory") ){
    		printInventory();
    	}else{
    		
    		System.out.println("That is incorrect. Try again. (Hint: The letters are on the pieces. Look at your inventory.) ");
    	}
	   
   }
 
    
	private static void init() {
        // Initialize any uninitialized globals.
        command = new String();
        stillPlaying = true; 
        System.out.println("Solar Sysdoom V3 (Java Edition)");
        System.out.println("Go to each planet and collect all of the pieces of the device to save the Solar System.");
        
        
        Items item0 = new Items(0);
	  		item0.setName(" Piece One.");
	  		item0.setDesc(" This is the first piece of the device. The letter 'A' is printed on it.");
	  		
	  	Items item1 = new Items(1);
  			item1.setName(" Piece Two.");
  			item1.setDesc(" This is the second piece of the device. The letter 'E' is printed on it.");
  		
  		Items item2 = new Items(2);
	  		item2.setName(" Piece Three.");
	  		item2.setDesc(" This is the third piece of the device. The letter 'P' is printed on it.");
	  		
	  	Items item3 = new Items(3);
	  		item3.setName(" Piece Four.");
	  		item3.setDesc(" This is the fourth piece of the device. The letter 'C' is printed on it."); 
  		
	  	Items item4 = new Items(4);
  			item4.setName("");
  			item4.setDesc(" These can help you with your journey."); 
  		
  	        
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
  			
   ItemsArr = new Items[7];
   			ItemsArr[0]= item0;
   			ItemsArr[1]= item1;
   			ItemsArr[2]= item2;
   			ItemsArr[3]= item3;
   			ItemsArr[4]= item4;
   			ItemsArr[5]= item5;
   			
        // Set up the location instances of the Locale class.
   			
   		    //Attributes for loc0. Instantiated as a global variable. 
	        loc0.setName("Earth");
	        loc0.setDesc("You are on the surface of your home planet.");
	        loc0.setItem(item5);
	        loc0.setNearestPlanet("Mercury(north).");
	        loc0.setHasVisited(true);
	        //Add Earth to the stack and the queue 
	        myStack.push(loc0.getName());
	        try {
				myQueue.enqueue(loc0.getName());
			} catch (Exception e) {
			
				e.printStackTrace();
			}
	        
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
	        loc8.setDesc("This is where you can buy magick items." ); 
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
    }
		
	 //Instantiate the list item from the list of Magick Items.
	 static ListItem li = new ListItem();
	
	 private static void magickShoppe(ListItem[] items, String fileName, ListMan lm1){
		    
		    /*
		    System.out.println("Items in the array BEFORE sorting:");
		    for (int i = 0; i < items.length; i++) {
		        if (items[i] != null) {
		            System.out.println(items[i].toString());
		        }
		    }
				*/
		    
		    System.out.println("These are the Magic Items:");
		    for (int i = 0; i < items.length; i++) {
		        if (items[i] != null) {
		            System.out.println(items[i].toString());
		        }else{
		        	System.out.println("I'm just not going to print for some reason");
		        }
		    }
		    
		    // Ask player for an item.
		    Scanner inputReader = new Scanner(System.in);
		    System.out.print("Welcome to the Magick Shoppe. You may only have one item at a time. What item would you like? (score/gold: " + score + " )");        
		    String targetItem = new String();
		    targetItem = inputReader.nextLine();
		    System.out.println();
		
		   
		    li = binarySearchArray( items, targetItem);
		    if (li != null) {
		    	if( li.getCost() <= score){
		        System.out.println(li.toString());
		        System.out.print("That item is listed, buy with your score for " + li.getCost() + "? (or, go south back to Saturn ). Press enter to search again.");
		    	}else{
		    		System.out.print("You cannot buy that item. Please search again (enter) or go south to Saturn.");
		    	}
		    	
		    }else {
		     System.out.print("That item is not listed. Please press enter to search again or go south to Saturn.");
		     
		    }
		   
	 }	
	 

    private static void updateDisplay() {
        System.out.println(currentLocale.getText());
       
    }

    private static void getCommand() {
    	
        System.out.print("[" + moves + " moves, score " + score + ", achievement ratio " + ((float)score/moves) +", near planet(s) " + ((Space)  currentLocale).getNearestPlanet() + " ] ");
        Scanner inputReader = new Scanner(System.in);
        command = inputReader.nextLine();  // command is global.
       
        }
    
    static BackwardLocationsStack myStack = new BackwardLocationsStack();
    //Create the stack for the locations; Backwards 
    static ForwardLocationsQueue myQueue = new ForwardLocationsQueue();
    //Create the queue for the locations; Forwards
    
    private static void navigate() {
     
    	
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
        } else if ( command.equalsIgnoreCase("buy")  || command.equalsIgnoreCase("b") && currentLocale == locations[8]){
        	buyItem();
        }else{
        	if(currentLocale != locations[8]){
        	System.out.println("Please enter a valid command as listed: ");
        	help();
        	}
        };
        	
            if (currentLocale.getHasVisited() != true) {
                
                moves = moves + 1;
                score = score + 5;
                currentLocale.setHasVisited(true);
                myStack.push(currentLocale.getName());
                try {
					myQueue.enqueue(currentLocale.getName());
				} catch (Exception e) {
				
					e.printStackTrace();
				}
                
            }else if (currentLocale.getHasVisited() != false){
            	//Unless you go from one location to another, it does not count as a 'move' 
            	if(command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") || command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") || command.equalsIgnoreCase("east") || command.equalsIgnoreCase("e") ||command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w")){
            		moves = moves + 1;
                    score = score;
                    myStack.push(currentLocale.getName());
                    try {
						myQueue.enqueue(currentLocale.getName());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}else{
            		moves = moves;
            		score = score;
            	}
            }
    }
    
    	   
    	 private static void buyItem( ){
	    	
	    		if(score >= li.getCost()){
	    			MagickItems[0] = li.getName();
	    			System.out.println("You bought a(n): " + li.getName() + ".");
                    goSouth(currentLocale);
                    myStack.push(currentLocale.getName());
                    
                    try {
						myQueue.enqueue(currentLocale.getName());
					} catch (Exception e) {
						
						e.printStackTrace();
					}
                    
	    		}else{
	    			System.out.println("You cannot buy this. ");
	    		}	
	    	
	   	   }
	     
	    
	         private static void goNorth(Locale loc, Locale locMS){
	        	   
	        	   if(loc.getNorth() != null){
	        		   currentLocale = loc.getNorth();
	        		   
	        	   }else {
	        		   System.out.println("You cannot go that way. ");
	        	   }
	        	   
	           }
	           
			 private static void goSouth(Locale loc){
				        	   
				   if(loc.getSouth() != null){
	        		   currentLocale = loc.getSouth();
	        		   
	        	   }else {
				       System.out.println("You cannot go that way.");
				   }
			   }
				 
	 		 private static void goEast(Locale loc){
					   
					   if(loc.getEast() != null){
						   currentLocale = loc.getEast();
						   
					   }else {
						   System.out.println("You cannot go that way.");
					   }
				 	}
	 
	 		  private static void goWest(Locale loc){
		   
	 				if(loc.getWest() != null){
	 					currentLocale = loc.getWest();
	 					
	 				}else {
	 					System.out.println("You cannot go that way.");
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
        System.out.println("At the Magick Shoppe:");
        System.out.println("   b/buy");
        System.out.println("Go to each planet and collect all of the pieces of the device to save the Solar System.");
    }

    private static void quit() {
    	stillPlaying = false;
    	askForwardorBackward();
    }
    
    
    private static void askForwardorBackward(){
    	System.out.println("Do you want to see your moves forwards (f) or backwards (b)?");
    	getCommand();
    	if(command.equalsIgnoreCase("forward")  || command.equalsIgnoreCase("f")){
    		forwardprintQueue();
    		
    	}else if (command.equalsIgnoreCase("backward")  || command.equalsIgnoreCase("b")){
    		backwardprintStack();
    		
    	}else{
    		
    		System.out.println("Welp, you're going to see it forward");
    		forwardprintQueue();
    	}
    	//This function should be in quit 
    	
    }
    
	private static void backwardprintStack() {
		
		for(int i = 0; i <= moves + 1 ; i++){
			try {
	            System.out.println(myStack.pop());
	            
	        } catch (Exception ex) {
	            System.out.println("Caught exception: " + ex.getMessage());
	        }
			//Need exception just in case, but having it print after each stack item is annoying
	        //System.out.println(myStack.isEmpty());
		}
	}
	
	private static void forwardprintQueue() {
		
		for(int i = 0; i <= moves + 1 ; i++){
			try {
	            System.out.println(myQueue.dequeue());
	            
	        } catch (Exception ex) {
	            System.out.println("Caught exception: " + ex.getMessage());
	        }
			//Need exception just in case, but having it print after each stack item is annoying
	        //System.out.println(myStack.isEmpty());
		}
	}
	
	private static void tookItem(Items item){
    	System.out.println("You took: " + item.getName());
    }
     
    private static void printMap(){
    	
    	for (int i = 0; i < storeMap.length; ++i){
			if(storeMap[i] != null){
				
			System.out.println(storeMap[i]);
			
			}else if (storeMap[i] == null) {
			
			System.out.println("You do not have the map.");
				
			}
			
		}
    	 
    }
    
    private static void printInventory(){
		System.out.println("Here is your inventory: ");
		
		for (int i = 0; i < Inventory.length; ++i){
			if(Inventory[i] != null){
				
			System.out.println(Inventory[i]);
			
			}else if (Inventory[i] == null) {
				
			//Don't print anything if the inventory item is null.	
			}
		}
		for (int i = 0; i < MagickItems.length; ++i){
			if(MagickItems[i] != null){
					
			System.out.println(MagickItems[i]);
				
			}else if (Inventory[i] == null) {
					
			//Don't print anything if the MagickItems array is null.		
			}	
		}
	}
  
    
    private static void take(){
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
    
  