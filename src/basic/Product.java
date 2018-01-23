package basic;

import java.util.*;

import table.RowData;

public class Product implements RowData {
	
	
	
	public String title = "";
	public String line1 = "";
	public String line2 = "";
	public String line3 = "";
	
	public String plu = "";
	public String upc = "";
	public String regPrice = "";
	public String status = "";
	
	public String adPrice = "";
	public String adDate = "";
	public boolean isPromo = false;
	
	public double rebate = 0;
	public String rebateGoodThru = "";
	
	
	
	public Product(){
		
		
		
	}
	
	
	public Product getCopy(){
		
		Product copy = new Product();
		
		copy.title = title;
		copy.line1 = line1;
		copy.line2 = line2;
		copy.line3 = line3;
		copy.plu = plu;
		copy.upc = upc;
		copy.regPrice = regPrice;
		copy.status = status;
		copy.adPrice = adPrice;
		copy.adDate = adDate;
		copy.isPromo = isPromo;
		copy.rebate = rebate;
		copy.rebateGoodThru = rebateGoodThru;
		
		
		
		return copy;
	}
	
	public String toString(){
		
		String string = "#" + plu;
		if( !title.isEmpty() )	string += "  " + title;
		if( !line1.isEmpty() )	string += "  " + line1;
		if( !line2.isEmpty() )	string += "  " + line2;
		if( !line3.isEmpty() )	string += "  " + line3;
		
		return string;
		
		
		//return "#" + plu + "  " + title + "  " + line1 + "  " + line2 + "  " + line3;
		
	}
	
	
	
	
	//for table
	@Override
	public Object getValueAt( int actualC ){
		
		
		if     ( actualC == 0 ) 	return this.plu;
		if     ( actualC == 1 ) 	return this.upc;
		if     ( actualC == 2 ) 	return this.title;
		else if( actualC == 3 )		return this.line1;
		else if( actualC == 4 )		return this.line2;
		else if( actualC == 5 )		return this.line3;
		else if( actualC == 6 )		return this.regPrice;
		else if( actualC == 7 )		return this.status;
		else 						throw new IllegalArgumentException( "Ram.getValueAt(): invalid actualC: " + actualC );
		
	}
	@Override
	public void setValueAt( int actualC , Object object ){
		
		String string = (String) object;
		
		if     ( actualC == 0 ) 	this.plu = string;
		else if( actualC == 1 )		this.upc = string;
		else if( actualC == 2 )		this.title = string;
		else if( actualC == 3 )		this.line1 = string;
		else if( actualC == 4 )		this.line2 = string;
		else if( actualC == 5 )		this.line3 = string;
		else if( actualC == 6 )		this.regPrice = string;
		else if( actualC == 7 )		this.status = string;
		else 						throw new IllegalArgumentException( "Ram.setValueAt(): invalid actualC: " + actualC );
		
	}
	@Override
	public int getColumnCount(){
		
		return 8;
	}
	@Override
	public ArrayList<String> getColumnNames(){
		
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add( "PLU" );
		columnNames.add( "UPC" );
		columnNames.add( "Title" );
		columnNames.add( "Line 1" );
		columnNames.add( "Line 2" );
		columnNames.add( "Line 3" );
		columnNames.add( "Reg $" );
		columnNames.add( "Order Stat" );
		
		return columnNames;
		
	}
	@Override
	public Class<?> getColumnClass( int actualC ){
		
		return String.class;
		
	}
	
	@Override
	public boolean isCellEditable( int actualC ){
		
		return true;
		
	}
	
	
	
	
	
	
	
	public static void sort( ArrayList<Product> list ){
		
		Collections.sort( list , new Comparator<Product>(){
			
			
			public int compare( Product p1 , Product p2 ){
				
				return p1.plu.compareTo( p2.plu );
				
			}
			
		});
		
	}
	
	
	
	
	
	
	
	
	
}


















