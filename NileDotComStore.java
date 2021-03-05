/*Name: Joseph McCoy
 *Course: CNT 4714-Spring 2021
 *Assignment title: Project 1 - Event-driven Enterprise Simulation
 *Date Sunday January 31,2021
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.format.DateTimeFormatter; 


public class NileDotComStore implements ActionListener {

	//creates class for items inventory
	public class Item {
		private int itemID;
		private String listingName;
		private boolean instock;
		private double sellprice;
		
		// Get itemID
			public int getItemID() {
				return itemID;
			}
			public void setItemID(int itemID) {
				this.itemID = itemID;
			}
		
			// Get listing Name
			public String getListingName() {
				return listingName;
			}
			public void setListingName(String listingName) {
				this.listingName = listingName;
			}
			public boolean getInstock() {
				return instock;
			}
			public void setInstock(boolean instock) {
				this.instock = instock;
			}
			
			// Get price of item
			public double getSellPrice() {
				return sellprice;
			}
			public void setSellPrice(double sellprice) {
				this.sellprice = sellprice;
			}
		
		}

	
	String[] transaction;
	String[] order;
	Item[] inv = new Item[40];
	Calendar calendar= Calendar.getInstance();
	Date date = calendar.getTime();

	// pulls inventory 
	 public void getInventoryFromFile() throws FileNotFoundException {
	
		int i=0;
		File file = new File("inventory.txt");
		Scanner theTextFile = new Scanner(file);

		// scan in inventory into array
		while (theTextFile.hasNextLine()) {
			// grab next inventory line and parse into 4 strings
			String item = theTextFile.nextLine();
			String[] theItemInformation = item.split(", ");

			// create an item, then set fields
			Item chosenItem = new Item();
			chosenItem.setItemID(Integer.parseInt(theItemInformation[0]));

			chosenItem.setListingName(theItemInformation[1]);
			
			chosenItem.setInstock(Boolean.parseBoolean(theItemInformation[2]));

			chosenItem.setSellPrice(Double.parseDouble(theItemInformation[3]));

			
			// add an item to the inventory arraylist
			inv[i] = chosenItem;
			
			i++;
			
		}	
		
		//close the stream
	theTextFile.close();
	
	
	}
	
	
	static int itemQuanity =0, itemNum=1 , orderQuantity = 1,idCheck, i;
	static double taxRate = .06, discount5= 10, discount10= 15, discount15= 20, total = 0, subTotal = 0;
	boolean startOrder = true;
		
	
	// set buttons and names 
	JButton process = new JButton("Process item #" +(itemNum));
	
	JButton comfirm = new JButton("Confirm item #" +itemNum);
	JButton view = new JButton("View order");
	JButton finish = new JButton("Finish Order");
	JButton newOrder = new JButton("New Order");
	JButton exit  = new JButton("Exit");
	
	//set textFields
	JTextField entry1 = new JTextField( );
	JTextField entry2 = new JTextField( );
	JTextField entry3 = new JTextField( );
	JTextField entry4 = new JTextField( );
	JTextField entry5 = new JTextField( );
	
	JLabel numOfItems  = new JLabel( "Enter number of items in this order: ", JLabel.RIGHT);
	JLabel id = new JLabel ("Enter item ID for item # " + itemNum +": ", JLabel.RIGHT); 
	JLabel quantity = new JLabel( "enter the quantity for item #  " + itemNum + ": ", JLabel.RIGHT); 
	JLabel info = new JLabel ("item #" + itemNum + "info: " , JLabel.RIGHT); 
	JLabel subtotal = new JLabel ("order subtotal for " + (itemNum-1) +" item(s): ", JLabel.RIGHT);
	
	public NileDotComStore()   throws FileNotFoundException {
		
		getInventoryFromFile();
		JFrame frame = new JFrame(); // creating frame 
		
		//set labels as well as color
		
		numOfItems.setForeground(Color.YELLOW);
		
		id.setForeground(Color.YELLOW);
			
		quantity.setForeground(Color.YELLOW);
				
		info.setForeground(Color.YELLOW);
		
		subtotal.setForeground(Color.YELLOW);
		
		//set actionListeners 
		process.addActionListener(this);
		comfirm.addActionListener(this);
		view.addActionListener(this);
		finish.addActionListener(this);
		newOrder.addActionListener(this);
		exit.addActionListener(this);
		
		//intializes panels 
		JPanel buttons = new JPanel();
		JPanel input = new JPanel();
		
		input.setBackground(Color.BLACK);
		input.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		input.setLayout(new GridLayout(5,2));
		
		input.add(numOfItems);
		input.add(entry1);
		input.add(id);
		input.add(entry2);
		input.add(quantity);
		input.add(entry3);
		input.add(info);
		input.add(entry4);
		input.add(subtotal);
		input.add(entry5);
		
		
		
		buttons.setBackground(Color.BLUE);
		buttons.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		buttons.setLayout(new GridLayout(1,6));
		
		//
		buttons.add(process);
		buttons.add(comfirm);
		comfirm.setEnabled(false);
		buttons.add(view);
		view.setEnabled(false);
		buttons.add(finish);
		finish.setEnabled(false);
		buttons.add(newOrder);
		buttons.add(exit);
		
		frame.add(input,BorderLayout.CENTER);
		frame.add(buttons,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);//set size of the frame
		frame.setTitle("Nile Dot Com - Spring 2021"); // sets the name on top of the 
		frame.pack();
		frame.setVisible(true);
			
		
		
	}
	


	public static void main(String[] args)throws FileNotFoundException
	{
		
		NileDotComStore store = new NileDotComStore();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		
		
		if(e.getSource() == process  )
		{
			
			double discount = 0;
			boolean found = false;
		
			orderQuantity = Integer.parseInt(entry1.getText());
			idCheck = Integer.parseInt(entry2.getText()); 
			itemQuanity = Integer.parseInt(entry3.getText()); 
			
			if(startOrder == true  )
			{
				order = new String[orderQuantity];
				transaction = new String[orderQuantity];
				startOrder = false;
			}
			
		
			
			for( i =0; i<inv.length;i++)
			{
				if(inv[i].itemID == idCheck)
				{
					if(inv[i].instock==false)
					{
						JOptionPane.showMessageDialog(process,"sorry... that item is out of stock, please try another item");
						found= true;
						break;
					}
					
					
					if(itemQuanity >4 && itemQuanity <10)
						discount = discount5;
					
					if(itemQuanity >9 && itemQuanity <15)
						discount = discount5;
					
					if(itemQuanity >14)
						discount = discount15;
					
					total = inv[i].getSellPrice() * itemQuanity  * (1-(discount /100 ));
					total = Math.round(total*100.0)/100.0;
					
					entry4.setText(String.valueOf((inv[i].getItemID())) +" "+ inv[i].getListingName() + " $" + String.valueOf(inv[i].getSellPrice()) + " "  + String.format("%.0f", discount)  + "% $"+total  )   ;
					found =  true;
					comfirm.setEnabled(true);
					process.setEnabled(false);
					
					order[itemNum-1] = itemNum + ". " + inv[i].getItemID() + " " + inv[i].getListingName() + " $" + String.format("%.02f", inv[i].getSellPrice()) +" "+ itemQuanity + " " + String.format("%.0f", discount)  + "% $" +   String.format("%.02f",total); 	
					transaction[itemNum-1] = inv[i].getItemID() + ", " + inv[i].getListingName() + ", " + String.format("%.02f", inv[i].getSellPrice()) +", "+ itemQuanity + ", " + String.format("%.02f", (discount/100))  + ", $" +   String.format("%.02f",total);
					break;

				
				}
				
			}
			if(found == false)
			{
				JOptionPane.showMessageDialog(process, "item ID " + String.valueOf(idCheck) + " was not in the file");
				
			}
		
						
		}
		
		if(e.getSource() == comfirm  )
		{
			entry2.setText("");
			entry3.setText("");
			process.setEnabled(true);
			comfirm.setEnabled(false);
			
			JOptionPane.showMessageDialog(comfirm, "Item #" + itemNum +"Accepted");
		
			itemNum++;
			
			id.setText("Enter item ID for item # " + itemNum +":");
			quantity.setText("enter the quantity for item #  " + itemNum + ": ");
			info.setText("item #" + itemNum + "info: ");
			subtotal.setText("order subtotal for " + (itemNum-1) +" item(s): ");
			process.setText("process item #" + itemNum);
			comfirm.setText("confirm item #"+ itemNum);
		
			
			subTotal = subTotal + total;
			entry5.setText(String.format("%.02f", subTotal));
			
			view.setEnabled(true);
			finish.setEnabled(true);
			
			
			
			
			if(itemNum > orderQuantity)
			{
				process.setEnabled(false);
				comfirm.setEnabled(false);
				info.setText("item #" + (itemNum-1) + "info: ");
				quantity.setText("");
				id.setText("");
			}
			
		}
		if(e.getSource()==  newOrder)
		{
			startOrder = true;
			itemNum =1;
			id.setText("Enter item ID for item # " + itemNum +":");
			quantity.setText("enter the quantity for item #  " + itemNum + ": ");
			info.setText("item #" + itemNum + "info: ");
			subtotal.setText("order subtotal for " + (itemNum-1) +" item(s): ");
			subTotal= 0;
			entry1.setText("");
			entry2.setText("");
			entry3.setText("");
			entry4.setText("");
			entry5.setText("");
			process.setText("process item #" + itemNum);
			comfirm.setText("confirm item #"+ itemNum);
			process.setEnabled(true);
		}
		
		//views the order
		if(e.getSource()== view)
		{
			
			//build the invoice 
			StringBuilder invoice = new StringBuilder("");
			for(int j=0; j < itemNum-1; j++)
			{
				invoice.append(order[j]);
				invoice.append(System.getProperty("line.separator"));
			}
			
			//prints the invoice
			JOptionPane.showMessageDialog(view,invoice);
			process.setEnabled(true);
		}
		
		if(e.getSource() == finish)
		{
			double taxAmount = subTotal * .06;
			double finalTotal = subTotal * .06 + subTotal;
			//formats time for message
			DateFormat setDate = new SimpleDateFormat("MM/dd/YY, hh:mm:ss a z");
			String now = setDate.format(date);
			
			//formats time txt file 
			DateFormat nowFinish = new SimpleDateFormat("ddMMYYYYHHmm");
			String nowTransaction = nowFinish.format(date);
			
			//build message with all the details 
			StringBuilder orderDetails =new StringBuilder("");
			orderDetails.append("Date: " + now);
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Number of line items: " + (itemNum-1));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Item# /ID / Price / Qty / Disc %/ Subtotal");
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			// iterates thru the order array 
			for(int j = 0; j< itemNum-1; j++)
			{
				orderDetails.append(order[j]);
				orderDetails.append(System.getProperty("line.separator"));
			}
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Order subtotal:   $" + String.format("%.02f",subTotal ));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Tax rate:    6%");
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Tax amount :   $" + String.format("%.02f", taxAmount) );
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Order total:      $" + String.format("%.02f", finalTotal));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append(System.getProperty("line.separator"));
			orderDetails.append("Thanks for shopping at Nile Dot Com!");
			
			JOptionPane.showMessageDialog(comfirm, orderDetails);
			
			// writes the file 
			File file = new File("transactions.txt");
			
			FileWriter fw = null;
				try {
					fw = new FileWriter(file,true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			PrintWriter pw;
			pw = new PrintWriter(fw);
			for(int j = 0; j <itemNum-1; j++) 
			{
				pw.println(nowTransaction + ", "+ transaction[j] +", "+ now );
			}
			
			
			pw.close();
		}
		
		if(e.getSource()== exit)
		{
			
			System.exit(0);
		}
	}
}
