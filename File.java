/**
 *
 * @author josh
 */

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class File {

    // Define variables
    String file;
    String partName;
    String partType;
    int partNumber;
    double partPrice;
    Part part;
    private Scanner in;
    private PrintWriter out;
    LinkedList<Part> partlist = new LinkedList<>();
    
    // File constructor
    public File (String f) {
        file = f; 
     }
    
    // Close Scanner 
    public void close() {
        in.close();
    }
    
    // Parse String
    public void parseString (String s) {
        StringTokenizer st = new StringTokenizer(s,",");
        while(st.hasMoreTokens()) {
            partName = st.nextToken();
            partNumber = Integer.parseInt(st.nextToken());
            partType = st.nextToken();
            partPrice = Double.parseDouble(st.nextToken());
        }    
    }

    // Save part to partlist
    public void setPart(Part p) {
        partlist.add(p);
    }
    
    // Get part from partlist
    public Part getPart (int i) {
        return partlist.get(i);
    }    

    // Remove part from partlist
    public void removePart (int i) {
        partlist.remove(i);
    }
    
    // Search for part within list
    public int searchPart (String p) {
        sortByName();
        int count = -1;
        for (int i=0; i < partlist.size();i++) {
            Part temp = getPart(i);
            if (temp.getPartName().equalsIgnoreCase(p)) 
                count = i;           
    }
        return count;
    }
        
    // Print search result
    public void loadSearchResult(int r) {
        Part s = new Part();
        s = partlist.get(r);
        partName = s.getPartName();
        partType = s.getPartType();
        partPrice = s.getPartPrice();
        partNumber = s.getPartNumber();
        }
    
    // Load data from file
    private void loadData() {
        try { 
            partlist.clear();
            while (in.hasNextLine()) {
                parseString(in.nextLine());
                newPart(partName, partNumber, partType, partPrice);   
            }
        } catch(NoSuchElementException ex) {
            System.out.println("No such element found in the file");
            JOptionPane.showMessageDialog(null, "No such element found in the file");
        }   
    }
    
    // Create new part object
    public void newPart (String a, int b, String c, Double d) {
        partlist.add(new Part(a,b,c,d)); 
    }
    
    // Write data to file
    private void writeData() {
        for (Part party : partlist) {
            out.format("%s,%s,%s,%s\n",party.getPartName(),
                Integer.toString(party.getPartNumber()),party.getPartType(),
                party.DoubleToString(party.getPartPrice()));    
        }
        out.close();
    }
     
    // Sort by name
    public void sortByName () {
        Collections.sort(partlist, new NameComparator());
    }
    
    // Sort by price
    public void sortByPrice () {
        Collections.sort(partlist, new PriceComparator());    
    }

    // Format string for display
    public String printPartList() {
        String a = "\tPart Name\t\tType\tNumber\tPrice\n"
                 + "              ------------------------------------------"
                 + "--------------------------------------------------------"
                 + "--------------\n";
        for (int i = 0; i < partlist.size();i++) {
            a = a + partlist.get(i) + "\n";
        }
        return a;
    }

    // Parse file for Part objects
    public void readFile () {
        try {
            in = new Scanner(new FileReader(file));          
        } catch(FileNotFoundException ex) {
            System.out.println("Input file not found");
            JOptionPane.showMessageDialog(null, "Input file not found");
        }
        loadData();
    }
    
    // Write file
    public void writeFile() {
        try {
            out = new PrintWriter(file);
        } catch (SecurityException e1) {
            System.err.println("Permission error on file");
        } catch ( FileNotFoundException e2) {
            System.err.println("File not found");
        }
        writeData();
}  
   
} // End Class File

// Sort by name class
class NameComparator implements Comparator<Part> {
    
    @Override
    public int compare(Part a, Part b) {
        return (a.getPartName().compareTo(b.getPartName())) ;
    }
    
} // End NameComparator Class

// Sort by price class
class PriceComparator implements Comparator<Part> {
    
    @Override
    public int compare(Part a, Part b) {
        return Double.compare(a.getPartPrice(), b.getPartPrice());
    }
    
} // End PriceComparator Ckass