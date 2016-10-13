/**
 *
 * @author josh
 */

import java.text.DecimalFormat;

public class Part{
    
    // Define Variables
    String partName;
    String partType;
    int partNumber;        
    double partPrice;
    
    // Part Constructors
    public Part(){
    }
    
    public Part(String p1, int p2, String p3, double p4) {
        setPartName(p1);
        setPartNumber(p2);
        setPartType(p3);
        setPartPrice(p4);
    }
    
    // Get part name
    public String getPartName() {
        return partName;
    }

    // Set part name
    public void setPartName(String s) {
        this.partName = s;
    }
    
    // Get part number
    public int getPartNumber() {
        return partNumber;
    }

    // Set part name
    public void setPartNumber(int s) {
        this.partNumber = s;
    }

    // Get part type
    public String getPartType() {
        return partType;
    }

    // Set part name
    public void setPartType(String s) {
        this.partType = s;
    }
    
    // Get part partPrice
    public double getPartPrice() {
        return partPrice;
    }

    // Set part partPrice
    public void setPartPrice(double s) {
        this.partPrice = s;
    }

    // Return part object string 
    @Override
    public String toString() {
        return String.format ("\t%s\t\t%s\t      %s\t%s",partName,partType,partNumber,DoubleToString(partPrice));
    }
    
    // Global double to string conversion for currency display
    public String DoubleToString (double p) {
        DecimalFormat d = new DecimalFormat("00.00");
        return d.format(p);
    }
}