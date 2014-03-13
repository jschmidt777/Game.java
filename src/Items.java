
public class Items {
	//
    // Public
    //

   
	// Constructor
    public Items(int id) {
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.name + this.desc;
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
    
    @Override
    public String toString(){
        return  this.name + this.desc;
    }

    //
    //Private
    //
    
    private int id;
   	private String name;
   	private String desc;
}
