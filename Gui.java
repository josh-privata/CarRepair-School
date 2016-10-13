/**
 *
 * @author josh
 */

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JTextField;

public class Gui extends JFrame {
    
    // User defined variables
    public static final String filename = "parts.txt";
    
    // Gui declarations
    private final JButton newBill;
    private final JButton addPart;
    private final JButton editPart;
    private final JButton showBill;
    private final JButton clearBill;
    private final JButton exitButton;
    //private final JTextField partName;
    private final JTextField partType;
    private final JTextField jobDate;
    private final JTextField partQTY;
    private final JTextField partPrice;   
    private final JTextField totalPrice;  
    private final JTextField jobNumber;
    private final JTextField regoNumber;
    private final JRadioButton jobMaint;
    private final JRadioButton jobRepair;
    private final JRadioButton maintMinor;
    private final JRadioButton maintMajor;
    private final JTextArea textArea;
    private final ButtonGroup jobGroup;
    private final ButtonGroup maintGroup;
    private JComboBox partName; 
    
    // Local variables
    private String rn; // temporary for rego number
    private int jn;  // temporary for job number
    private String date; // temporary date
    private byte mt;  // temporary for maintanence type  1 = major  0 = minor
    private byte jt; // temporary for job type  1 = repair  0 = maint
    Maint maint; // Global maint object
    Repair repair; // Global repair object 
    String message; // Globally used message object
    public static int test = 0; // Used to determine search results
    
    // Interface constructor
    public Gui() {

        // Initialise components
        this.setLayout(new GridBagLayout());
        this.setTitle("Josh Cannons S0248763");
        textArea = new JTextArea(10, 10);
        newBill = new JButton("Create Bill");
        addPart = new JButton("Add");
        editPart = new JButton("Edit Part");
        showBill = new JButton("Show Bill");
        clearBill = new JButton("Clear Bill");
        exitButton = new JButton("Exit");        
        partType = new JTextField ("", 5);
        partType.setToolTipText("Please enter the Part Type");
        partQTY = new JTextField ("", 2);
        partQTY.setToolTipText("Please enter the Part Quantity");
        partPrice = new JTextField ("", 2);
        partPrice.setToolTipText("Please enter the Part Price");
        totalPrice = new JTextField ("", 2);
        jobNumber = new JTextField ("", 3);
        jobNumber.setToolTipText("Please enter the Job Number");
        jobDate = new JTextField (todaysDate(), 6);
        jobDate.setToolTipText("This is today's date");
        regoNumber = new JTextField ("", 10);
        regoNumber.setToolTipText("Please enter the Registration Number");
        jobRepair = new JRadioButton( "Repair", false );
        jobMaint = new JRadioButton( "Maint", false );
        maintMinor = new JRadioButton( "Minor", false );
        maintMajor = new JRadioButton( "Major", false );        
        
            
        // create combobox
        partName = new JComboBox();
        partName.setEditable(true);
        partName.setToolTipText("Please enter the Part Name");
        
        
        // create button groups
        jobGroup = new ButtonGroup();
        jobGroup.add(jobRepair);
        jobGroup.add(jobMaint);
        maintGroup = new ButtonGroup();
        maintGroup.add(maintMinor);
        maintGroup.add(maintMajor);        
        
        // create button boxes
        Box jobBox = Box.createVerticalBox();
        jobBox.add(jobRepair);
        jobBox.add(jobMaint);
        jobBox.setBorder(BorderFactory.createTitledBorder("Job Type"));
        Box maintBox = Box.createVerticalBox();
        maintBox.add(maintMinor);
        maintBox.add(maintMajor);
        maintBox.setBorder(BorderFactory.createTitledBorder("Maint Type"));        
         
        // Set text fields to uneditable initially
        partName.setEnabled(false);
        textArea.setEditable(false);
        partQTY.setEnabled(false);
        partPrice.setEnabled(false);
        totalPrice.setEnabled(false);
        jobNumber.setEnabled(false);
        regoNumber.setEnabled(false);
        jobDate.setEnabled(false);
        addPart.setEnabled(false);
        newBill.setEnabled(false);
        showBill.setEnabled(false);
        maintMinor.setEnabled(false);
        maintMajor.setEnabled(false);
        partType.setEnabled(false);

        // Add components to f
        add(maintBox, addItem(1, 0, 1, 5, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(jobBox, addItem(0, 0, 1, 5, GridBagConstraints.WEST, GridBagConstraints.NONE));  
        add(new JLabel("Job Number:"),addItem(3, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
        add(jobNumber, addItem(4 ,0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(new JLabel("Date:"), addItem(3, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
        add(jobDate, addItem(4, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));            
        add(new JLabel("Rego Number:"), addItem(3, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(regoNumber, addItem(4, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(newBill, addItem(5, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
        add(showBill, addItem(5, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
        add(new JLabel("Part"), addItem(0, 6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(partName, addItem(0, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        add(new JLabel("Type"), addItem(1, 6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(partType, addItem(1, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        add(new JLabel("QTY"), addItem(3, 6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(partQTY, addItem(3, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        add(new JLabel("Price"), addItem(4, 6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
        add(partPrice, addItem(4, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        add(addPart, addItem(5, 7, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
        add(textArea, addItem(0, 9, 6, 3, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL));
        add(editPart, addItem(3, 15, 1, 1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL));
        add(clearBill, addItem(4, 15, 1, 1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL));
        add(exitButton, addItem(5, 15, 1, 1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL));
              
        // Register event handlers
        DoListener handler = new DoListener();
        newBill.addActionListener(handler);
        addPart.addActionListener(handler);
        editPart.addActionListener(handler);
        showBill.addActionListener(handler);
        exitButton.addActionListener(handler);
        clearBill.addActionListener(handler);
        jobMaint.addItemListener(new RadioButtonHandler());
        jobRepair.addItemListener(new RadioButtonHandler());
        maintMinor.addItemListener(new RadioButtonHandler());
        maintMajor.addItemListener(new RadioButtonHandler());
        partName.addActionListener(new PartsBox());
    
    }
    
    // Gui construction helper method
    private GridBagConstraints addItem( int x, int y, int width, int height,
        int align, int fill) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.insets = new Insets(5, 0, 5, 5);
        gc.anchor = align;
        gc.fill = fill;
        return gc;
    }
    
    // Dynamically fill combo box
    public void fillComboBox () {
        repair.createArray();
        for (String s:repair.partArray)
            partName.addItem(s);
    }
    
    // Handle combobox events
    public class PartsBox implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        partName = (JComboBox)e.getSource();
        String s = (String)partName.getSelectedItem();
        repair.partname = "";
        repair.parttype = "";
        repair.partnumber = 0;
        repair.partprice = 0;
        repair.search(s);
        partType.setText(repair.parttype);
        partPrice.setText(repair.DoubleToString(repair.partprice));
    }
}
    
    // Handle radio buttons events
    private class RadioButtonHandler implements ItemListener {
      
        // On button change
        @Override
        public void itemStateChanged(ItemEvent e) {
         
            // Determine jobGroup type and change text fields to editable
            if  (jobMaint.isSelected()) {
                newBill.setEnabled(true);
                jobNumber.setEnabled(true);
                regoNumber.setEnabled(true);
                partName.setEnabled(false);
                partType.setEnabled(false);
                partQTY.setEnabled(false);
                partPrice.setEnabled(false);
                totalPrice.setEnabled(false);
                jobDate.setEnabled(false);
                maintMinor.setEnabled(true);
                maintMajor.setEnabled(true);
                addPart.setEnabled(false);
                textArea.setText("");
                jt=0;
                }
            else { 
                jobNumber.setEnabled(true);
                newBill.setEnabled(true);
                regoNumber.setEnabled(true);
                maintMinor.setEnabled(false);
                maintMajor.setEnabled(false);
                maintMajor.setSelected(false);
                maintMinor.setSelected(false);
                jt=1;
            }
            if (maintMinor.isSelected())
                mt=0;
            else if (maintMajor.isSelected()) 
                mt=1; 
        }
    } 
      
    // Handle button events
    private class DoListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            
            String action = e.getActionCommand();
            
            switch (action) {
                case "Add": // Add part details to bill
                        try{  
                            String a = partPrice.getText();
                            repair.partprice = (Double.parseDouble(a));
                            String b = partQTY.getText();
                            repair.qty = (Integer.parseInt(b));
                            String c = (String)partName.getSelectedItem();
                            String d = partType.getText();
                            repair.partname = c;
                            repair.finalpartprice = 0;
                            repair.parttype = d;
                            repair.finalpartprice = (repair.qty*repair.partprice);
                            repair.addToFinalPrice(repair.finalpartprice);
                            if (test==0) {
                                repair.addPart();
                            }
                            repair.setPartsString();
                            textArea.append(repair.getPString());   
                            partQTY.setText("");
                            partPrice.setText("");
                            partType.setText("");
                            fillComboBox();
                        }
                        catch (Exception e1) {
                            message = "Please make sure all fields have been enered correctly";
                            System.out.println(e1);
                            JOptionPane.showMessageDialog(null,message);  
                        }
                    break;
             
                case "Create Bill": // Create Job object
                    date = jobDate.getText();                        
                    if (regoNumber.getText().equals("")) {
                            message = "Please make sure a valid Registration Number has been entered into the Registration Number field.";
                            JOptionPane.showMessageDialog(null,message);  
                    }
                    else {
                        rn = regoNumber.getText();
                        try{  // Validate an int has been entered in the job number field
                            String j = jobNumber.getText();
                            jn = (Integer.parseInt(j));  
                        }
                        catch (Exception e1) {
                            message = "Please make sure a valid number has been entered into the Job Number field.";
                            System.out.println(e1);
                            JOptionPane.showMessageDialog(null,message);      
                        }
                        if (jt==0) {
                            if (maintMinor.isSelected() || maintMajor.isSelected()) {                               
                                maint = new Maint(jn, date, rn, mt);
                                showBill.setEnabled(true);
                            }
                            else {
                            message = "Please make sure a Maintanence Type button has been selected";
                            System.out.println(message);
                            JOptionPane.showMessageDialog(null,message); 
                            }
                        }
                        else {
                            repair = new Repair(jn, date, rn, filename);
                            partName.setEnabled(true);
                            partType.setEnabled(true);
                            totalPrice.setEnabled(true);
                            partQTY.setEnabled(true);
                            partPrice.setEnabled(true);
                            addPart.setEnabled(true);
                            textArea.setText(repair.partHeader());
                            showBill.setEnabled(true);
                            fillComboBox();
                        }
                    }                                   
                    break;
                    
                case "Show Bill": // Show bill preview
                    billFrame();
                    break;
                    
                case "Edit Part": // Show bill preview
                    partFrame();
                    break;    
                
                case "Clear Bill":
                    partType.setText(null);
                    partQTY.setText("");
                    partPrice.setText("");    
                    jobNumber.setText(null);
                    regoNumber.setText(null);
                    textArea.setText(null);
                    showBill.setEnabled(false);
                    addPart.setEnabled(false);
                    partName.setEnabled(false);
                    partQTY.setEnabled(false);
                    partPrice.setEnabled(false);
                    totalPrice.setEnabled(false);
                    if (jt == 0)
                        maint = null;
                    else if (jt == 1)
                        repair = null;
                    break;     
                
                case "Exit": // Exit program
                    System.exit(1);
                    break;
            }
        }
    } 
    
    // Create and format today's date
    private String todaysDate() {
        Date today = new Date();
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        String dateOut = dateFormatter.format(today);
        return dateOut;
    }
          
    // Bill helper method
    public static int countLines(String str) {
        int lines = 1;
        int pos = 0;
        while ((pos = str.indexOf("\n", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }

    // Show bill frame
    public void billFrame()  {
        EventQueue.invokeLater(new Runnable()  {
            
            JFrame f;
        
            @Override
            public void run()  {
                
                // Initialise f
                f = new JFrame("Final Bill");
                f.setVisible(true);
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try  {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } 
                catch (Exception e)  {
                   e.printStackTrace();
                }
                f.setLayout(new FlowLayout());
                                
                // Create text area
                JTextArea textArea;
                if ( jt == 0)  {
                    message = maint.toString();
                    textArea = new JTextArea((countLines(message)+2), 25);
                    f.setSize(350, (countLines(message)*22));
                }
                else  { 
                    message = repair.toString();
                    textArea = new JTextArea((countLines(message)+2), 38);
                    f.setSize(450, ((countLines(message)*20)));
                }
                textArea.setWrapStyleWord(false);
                textArea.setEditable(false);
                textArea.setText(message);
                                      
                // Create buttons
                JButton closeBill = new JButton("Close Bill");
                closeBill.addActionListener(new DoListener());
                JButton printBill = new JButton("Print Bill");
                printBill.addActionListener(new DoListener());
                JButton saveBill = new JButton("Save Bill");
                saveBill.addActionListener(new DoListener());
                
                // Add components
                f.add(textArea);
                f.add(printBill);
                f.add(saveBill);
                f.add(closeBill);              
            }
            // Implement panel buttons
                class DoListener implements ActionListener {
                    public void actionPerformed(ActionEvent p) {
                        String action = p.getActionCommand();
                        switch (action) {
                            case "Close Bill" :
                                f.setVisible(false);
                                f.dispose();
                                break;
                            case "Print Bill":
                                // Not implemented
                                break;
                            case "Save Bill":
                                // Not implemented
                                break;
                        }
                    }
                }
        });
    }
   
    // Show part frame
    public void partFrame()  {                
        EventQueue.invokeLater(new Runnable()  {
            File f;
            JFrame p;
            String a;
            String b;
            int c = 0;
            double d = 0;
            JButton searchPart;
            JButton exit;
            JButton savePart;
            JButton removePart;
            JButton sortName;
            JButton sortPrice;
            JButton  openFile;
            JTextField partName;
            JTextField partPrice;
            JTextField fileName;
            JTextField typeName;
            JTextField partNumber;
            JTextField searchField;
            JTextArea tA;

            
            @Override
            public void run()  {
                // Initialise p
                p = new JFrame("Edit Parts");
                p.setVisible(true);
                p.setResizable(true);
                p.setSize (600, 475);
                p.setLayout(new GridBagLayout());
                p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try  {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e)  {
                    e.printStackTrace();
                }
                
                // Initialise file object
                f = new File(filename);
                f.readFile();
                
                // Create text area
                tA = new JTextArea(15, 50);
                tA.setWrapStyleWord(false);
                tA.setEditable(false);
                tA.setText(f.printPartList());             
                                      
                // Create buttons
                searchPart = new JButton("Search Part");
                searchPart.addActionListener(new DoListener());
                exit = new JButton("Exit");
                exit.addActionListener(new DoListener());
                savePart = new JButton("Add Part");
                savePart.addActionListener(new DoListener());
                removePart = new JButton("Remove Part");
                removePart.addActionListener(new DoListener());
                sortName = new JButton("Name Sort");
                sortName.addActionListener(new DoListener());
                sortPrice = new JButton("Price Sort");
                sortPrice.addActionListener(new DoListener());
                
                // Create text fields
                JLabel partLabel = new JLabel("Part");
                partName = new JTextField ("", 20);
                partName.setToolTipText("Please enter the Part Name");
                JLabel numberLabel = new JLabel("Number");
                partNumber = new JTextField ("", 5);
                partNumber.setToolTipText("Please enter the Part Number");
                JLabel priceLabel = new JLabel("Price");
                partPrice = new JTextField ("", 5);
                partPrice.setToolTipText("Please enter the Part Price");
                JLabel typeLabel = new JLabel("Type");
                typeName = new JTextField ("", 10);
                typeName.setToolTipText("Please enter the Part Type");
                searchField = new JTextField ("", 20);
                searchField.setToolTipText("Please enter a part to search for");

                // Add components
                // 1
                p.add(tA, addItem(0, 0, 5, 4, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));  
                // 2
                p.add(sortName, addItem(2, 5, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
                p.add(sortPrice, addItem(3, 5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(searchField, addItem(0, 5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(searchPart, addItem(1, 5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                // 3
                p.add(partLabel, addItem(0, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(partName, addItem(0, 8, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(numberLabel, addItem(2, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(partNumber, addItem(2, 8, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(typeLabel, addItem(1, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(typeName, addItem(1, 8, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE)); 
                p.add(priceLabel, addItem(3, 7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(partPrice, addItem(3, 8, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                // 4
                p.add(savePart, addItem(1, 9, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
                p.add(removePart, addItem(2, 9, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));
                p.add(exit, addItem(3, 9, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE));
            }
            // Implement panel buttons
                class DoListener implements ActionListener {
                    public void actionPerformed(ActionEvent e) {
                        String action = e.getActionCommand();
                        switch (action) {
                            case "Search Part" :
                                try {
                                    message = "Part not Found";
                                    String search = searchField.getText();
                                    System.out.println(searchField.getText());
                                    int s = f.searchPart(search);
                                    if (s == -1) {
                                        JOptionPane.showMessageDialog(null,message);
                                    }
                                    else{
                                        System.out.println(s);
                                        f.loadSearchResult(s);
                                        // Print values to boxes
                                        a = f.partName;
                                        b = f.partType;
                                        c = f.partNumber;
                                        d = f.partPrice;
                                        partName.setText(f.partName);
                                        typeName.setText(b);
                                        partNumber.setText(Integer.toString(c));
                                        partPrice.setText(Double.toString(d));
                                        tA.setText(f.printPartList());
                                    }
                                } catch (Exception e3) {
                                    message = "Please make sure a valid Part Name has been entered into the Text field.";
                                    JOptionPane.showMessageDialog(null,message);
                                    System.out.println(e3);
                                }    
                                break;
                            case "Name Sort" :
                                f.sortByName();
                                tA.setText(f.printPartList());
                                break;
                            case "Price Sort" :
                                f.sortByPrice();
                                tA.setText(f.printPartList());
                                break;
                            case "Add Part":
                                try {
                                    if (!partName.getText().isEmpty()){
                                        if (!typeName.getText().isEmpty()) {
                                            if (!partNumber.getText().isEmpty()) {
                                                try {
                                                    c = Integer.parseInt(partNumber.getText());
                                                } catch (NumberFormatException n) {                                                       
                                                }
                                                if (!partPrice.getText().isEmpty()) {
                                                    try {
                                                        d = (Double.parseDouble(partPrice.getText()));
                                                    } catch (NumberFormatException n) {                                                         
                                                    }
                                                        a = partName.getText();
                                                        b = typeName.getText();
                                                        f.setPart(new Part (a,c,b,d));
                                                        f.writeFile();
                                                        tA.setText(f.printPartList());
                                                } else {  
                                                    message = "Please make sure a valid price has been entered into part price";
                                                    JOptionPane.showMessageDialog(null,message); 
                                                }
                                            } else {    
                                                message = "Please make sure a valid number has been entered into part number";
                                                JOptionPane.showMessageDialog(null,message);
                                            }        
                                        } else {
                                            message = "Please make sure a valid type has been entered into part type";
                                            JOptionPane.showMessageDialog(null,message);
                                        }
                                    } else {
                                        message = "Please make sure a valid name has been entered into part name";
                                        JOptionPane.showMessageDialog(null,message);
                                    }
                                } catch  (Exception e2)  {
                                    message = "Please make sure fields have been entered correctly";
                                    JOptionPane.showMessageDialog(null,message);
                                } 
                                break;
                            case "Remove Part": 
                                f.removePart(f.searchPart(partName.getText()));
                                f.writeFile();
                                tA.setText(f.printPartList());
                                break;
                            case "Exit":             
                                f.close();
                                p.setVisible(false);
                                p.dispose();
                                break;
                        }
                    }
                }
        });
    }
   
} // End Class Gui 