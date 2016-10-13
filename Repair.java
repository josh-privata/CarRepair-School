/**
 *
 * @author josh
 */

public class Repair extends Job {
          
    // User definable variables
    final double initial = 120.00; // Inspection charges applied to all jobs
    
    // Set global variables
    double finalprice;  // Holds final bill price
    String partname; 
    String parttype;
    int partnumber;
    int qty; 
    double partprice; // Holds part price
    double finalpartprice; // Holds (qty*partprice) for item
    String parts; // Holds parts used
    String p = ""; // Temporary for display
    File f; // Global file object
    String [] partArray; // Array used for combobox display
    
    // Maint constructor
    public Repair (int n, String d, String r, String x) {
        super(n, d, r);
        f = new File(x);
        f.readFile();    
        parts = "";
        partname = "";
        qty = 0;
        partprice = 0;
        finalpartprice = 0;
        finalprice = initial; 
        createArray();
    }
    
    // Set part name
    public void setPartName (String p) {
        partname = p;
    }
    
    // Get part name
    public String getPartName () {
        return partname;
    }
    
    // Set part type
    public void setPartType (String p) {
        parttype = p;
    }
    
    // Get part type
    public String getPartType () {
        return parttype;
    }

    // Set quantity
    public void setQty (int q) {
        qty = q;
    }
    
    // Get quantity
    public int getQty () {
        return qty;
    }
    
    // Set part price
    public void setPartPrice (double p) {
        partprice = p;
    }
    
    // Get part price
    public double getPartPrice () {
        return partprice;
    }
    
    // Search parts
    public void search(String s) {
        int i = f.searchPart(s);
        if ( i >= 0) {
            f.loadSearchResult(i);        
        partname = f.partName;
        parttype = f.partType;
        partnumber = f.partNumber;
        partprice = f.partPrice; 
        Gui.test = 1;
        } else 
            Gui.test = 0;  
    }
    
    // Set final part price
    public void setFinalPartPrice (double p, int q) {
        finalpartprice = (p*q);
    }
    
    // Get final part price
    public double getFinalPartPrice () {
        return finalpartprice;
    }
    
    // Append part array to parts string (alternate methodology String.format)
    public void setPartsString () {
        p = partname + "\t\t" + this.DoubleToString(qty) + "\t"
          + this.DoubleToString(partprice) + "\t"
          + this.DoubleToString(finalpartprice) + "\n";
        parts = parts + p;
    } 
    
    // Get parts string 
    public String getPartsString () {
        return parts;
    }
    
    // Get p string
    public String getPString () {
        return p;
    }
    
    // Empty parts string
    public void emptyPartsString () {
        parts = "";
    }
    
    // Add finalpartprice to total price
    public void addToFinalPrice (double p) {
       finalprice = finalprice + p; 
    }
    
    // Create part from user entry, auto-increment partnumber
    public void addPart () {
        f.setPart(new Part (partname, (f.partlist.size()+1), parttype, partprice));
        f.writeFile();
    }
    
    // Create array for combobox display
    public void createArray () {
        partArray = new String[(f.partlist.size()+1)];
        int count = 1;
        partArray[0] = "";
        for (Part p: f.partlist) {
            partArray[count] = p.getPartName();
            count++;
        }
    }
   
    // Create bill header
    public String partHeader() {
        String h = String.format ( "  %s\t\t%s\t%s\t%s\n%s\n",
                "Part Name", "Quantity", "Price", "Total",
                "  ------------------------------------------------"
              + "------------------------------------------------");
        return h;
    }
    
    // Return repair object string 
    @Override
    public String toString() {
        String s = String.format("\n\t%s\n%s\n\n%s%s\n%s%s\n%s%s\n\n%s\n"
                + "%s\n%s\n%s\t\t\t%s\n\n%s\n\n%s$%s\n\n\n\n\t%s",
            "     Welcome to CQ-Car Repairs",
            "  ------------------------------------------------"
          + "------------------------------------------------",
            " Job Number : ", getJobNumber(),
            " Job Date : ", getJobDate(),
            " Registration Number : ", getRegistration(),
            "  ------------------------------------------------"
          + "------------------------------------------------",
            partHeader(),
            parts,
            " Service charge", this.DoubleToString(initial),
            "  ------------------------------------------------"
          + "------------------------------------------------",
            " Total charge for this job is : ", totalCharge(),
            "Thank you for using CQ-Car Repairs");
        return s;
    }
    
    // Implement abstract toCharge method
    @Override
    public double totalCharge() { 
        return finalprice;
    }

} // End Class Maint

