/**
 *
 * @author josh
 */

public class Maint extends Job {
      
    // User definable variables
    final double minor = 180.00; // Minor service price
    final double major = 320.00; // Major service price
    
    // Set global variables
    byte type; // Hold service type identifier. 1 is major 0 is minor
    double finalprice; // Hold final price
    String maintname; // Hold maint string name
    
    // Maint constructor
    public Maint (int n, String d, String r, byte t) {
        super(n, d, r);
        setMaintType(t);
        setMaintPrice();
    }
  
    // Set maint type
    public void setMaintType( byte t ) {
        if ( t == 1 ) {
            type = t;
            maintname = "Major";
        }
        else if ( t == 0) {
            type = t;
            maintname = "Minor"; 
        }
        else
            throw new IllegalArgumentException ( "Please select appropriate"
                + " maintenance type" );
    }
        
    // Get maint type
    public String getMaintType() {
        return maintname;
    }

    // Set maint price
    public void setMaintPrice() {
        if (type == 1)
            finalprice = major;
        else finalprice = minor;
    }
     
    // Get maint price
    public Double getMaintPrice() {
        return finalprice;
    }
           
    // Return maintenance object string 
    @Override
    public String toString() {
        String s; 
        s = String.format("\n%s\n%s\n\n%s%s\n%s%s\n%s%s\n%s\n\n %s%s\n\n%s\n\n%s$%s\n\n\n\n%s",
            "           Welcome to CQ-Car Repairs",
            "  ---------------------------------------------------------------",
            " Job Number : ", getJobNumber(),
            " Job Date : ", getJobDate(),
            " Registration Number : ", getRegistration(),
            "  ---------------------------------------------------------------",
            maintname, " Service",
            "  ---------------------------------------------------------------",
            " Total charge for this job is : ", totalCharge(),
            "      Thank you for using CQ-Car Repairs");
        return s;
    }

    // Implement abstract toCharge method
    @Override
    public double totalCharge() { 
        return finalprice;
    }

} //End Class Maint