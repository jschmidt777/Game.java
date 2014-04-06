public class Locale {

	//
    // Public
    //

    // Constructor
    public Locale(int id) {
        this.id = id;
    }
    // Getters and setters for navigation
    public Locale getNorth() {
		return north;
	}

	public void setNorth(Locale north) {
		this.north = north;
	}

	public Locale getSouth() {
		return south;
	}

	public void setSouth(Locale south) {
		this.south = south;
	}

	public Locale getEast() {
		return east;
	}

	public void setEast(Locale east) {
		this.east = east;
	}

	public Locale getWest() {
		return west;
	}

	public void setWest(Locale west) {
		this.west = west;
	}

	// Getters and Setters
    public int getId() {
        return this.id;
    }

    public String getText() {
    	
    	if(this.hasItem != false){
        return this.name + "\n" + this.desc + this.item;
    	}else if (this.hasItem != true){
    		
    	return this.name + "\n" + this.desc;
    	}
		return name ;
		
		
    }

    public String getName() {
        return this.name;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String value) {
        this.desc = value;
    }

    public boolean getHasVisited() {
        return hasVisited;
    }
    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }


    public boolean getHasItem() {
		return hasItem;
	}

	public void setHasItem(boolean hasItem) {
		this.hasItem = hasItem;
	}
	
	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

    
	
    // Other methods
    @Override
    public String toString(){
        return "[Locale id="
                + this.id
                + " name="
                + this.name
                + " desc=" + this.desc
                + " hasVisited=" + this.hasVisited + "]";
    }

   

	//
    //  Private
    //
    private int     id;
    private String  name;
    private String  desc;
    private boolean hasVisited = false;
    private boolean hasItem = true;
    private Items item;
	private Locale north = null;
	private Locale south = null;
	private Locale east = null;
	private Locale west = null;
	
	
}