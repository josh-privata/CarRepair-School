/**
 *
 * @author josh
 */
       
import java.text.DecimalFormat;

// Begin Class Job
public abstract class Job {
    
    // Set global variables
    private int jobnumber;
    private String jobdate;
    private String registration;

    // Job constructor
    public Job ( int n, String d, String r ) {
        setJobNumber(n);
        setJobDate(d);
        setRegistration(r);
    }
  
    // Set jobnumber
    public void setJobNumber( int n ) {
        if ( n > 0 )
            jobnumber = n; 
        else 
            throw new IllegalArgumentException ( "Job number must be greater than 0" );
    }

    // Get jobnumber
    public int getJobNumber() {
        return jobnumber;
    }
      
    // Set jobdate
    public void setJobDate( String j ) {
        jobdate = j;
    }

    // Get jobdate
    public String getJobDate() {
        return jobdate;
    }

    // Set registration
    public void setRegistration( String r ) {
        registration = r;
    } 

    // Get registration
    public String getRegistration() {
        return registration;
    }
    
    // Global double to string conversion for currency display
    public String DoubleToString (double p) {
        DecimalFormat d = new DecimalFormat("00.00");
        return d.format(p);
    }
    
    // Return job object string 
    @Override
    public String toString() {
        return String.format( "%s %s %s",getJobNumber(), getJobDate(), getRegistration() );
    }

    // Global method defined elsewhere
    public abstract double totalCharge();
  
} // End Class Job
