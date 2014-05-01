
public class BackwardLocationsStack {
	//
    // Public
    //
	public BackwardLocationsStack() {
        init();
        
    }
	public String[] getArr() {
		return arr;
	}


    public void push(String locname) {
        // Check for stack overflow.
        if (topPtr > 0) {
            topPtr = topPtr - 1;
            getArr()[topPtr] = locname;
        } else {
            // TODO: Throw an overflow exception.
        }
    }

    public String pop() {
        String retVal = "(Your Moves Backwards)";
        // Check for stack underflow.
        if (topPtr < CAPACITY) {
            retVal = getArr()[topPtr];
            topPtr = topPtr + 1;
        } else {
            // In case of underflow, return -1.
            // TODO: Throw an underflow exception.
            
        }
        return retVal;
    }

    public boolean isEmpty() {
        boolean retVal = false;
        if (topPtr == CAPACITY) {
            retVal = true;
        }
        return retVal;
    }
    
    public int getCapacity() {
		return CAPACITY;
    }
    
    //
    // Private
    //
   
    private final int CAPACITY = 50;
	private String[] arr = new String[CAPACITY];
    private int topPtr = 0;

    private void init() {
       for (int i = 0; i < CAPACITY; i++) {
           getArr()[i] = "End";
       }
       topPtr = CAPACITY;
    }
    
}

