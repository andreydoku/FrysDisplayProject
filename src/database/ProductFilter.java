package database;

import RowFilters.MyRowFilter;
import basic.Product;
import myLibrary.Methods;
import table.RowData;

public class ProductFilter implements MyRowFilter {
	
	
	
	
	private boolean hideDstat = false;
	private String searchText = "";
	
	

	
	public boolean isRowHidden( RowData rowData ){

		String rowText = "";

		for ( int actualC=0;actualC<rowData.getColumnCount();actualC++ )
		{
			Object value = rowData.getValueAt( actualC );
			if( value != null )
			{
				rowText += value.toString();
			}
			

		}
		
		boolean containsSearchText = Methods.stringContainsWords( rowText , searchText );
		boolean hideCuzOfSearch = !containsSearchText;
		
		
		Product product = (Product) rowData;
		boolean hideCuzOfDstat = hideDstat && product.status.toLowerCase().equals( "d" );
		
		
		
		
		//boolean hidden = hideCuzOfDstat;
		boolean hidden = hideCuzOfSearch || hideCuzOfDstat;

		return hidden;

	}

	public void setSearchText( String searchText ){

		this.searchText = searchText;

	}
	public String getSearchText(){

		return searchText;

	}
	
	public void setHideDStat( boolean dstat ){
		
		this.hideDstat = dstat;
		
	}
	
	
	
}
