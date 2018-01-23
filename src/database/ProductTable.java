package database;


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import basic.Product;
import table.*;




public class ProductTable extends MyTable {
	
	
	public static Color W_Color = new Color( 0,100,0 );
	
	
	
	
	public MyTableModel model = new MyTableModel( new Product() );
	
	
	public ProductTable(){
		
		
		this.setModel( model );
		this.updateColumnHeaders();
		this.setHeaderHeight( 36 );
		this.getTableHeader().setOpaque( false );
		
		
		//65 , 92 , 54 , 53 , 57 , 71 , 75 , 78 , 122 , 72 , 49
		this.getColumnModel().getColumn( 0 ).setPreferredWidth( 65 );
		this.getColumnModel().getColumn( 1 ).setPreferredWidth( 92 );
		this.getColumnModel().getColumn( 2 ).setPreferredWidth( 150 );
		this.getColumnModel().getColumn( 3 ).setPreferredWidth( 150 );
		this.getColumnModel().getColumn( 4 ).setPreferredWidth( 150 );
		this.getColumnModel().getColumn( 5 ).setPreferredWidth( 150 );
		this.getColumnModel().getColumn( 6 ).setPreferredWidth( 72 );
		this.getColumnModel().getColumn( 7 ).setPreferredWidth( 49 );
		
		
		
		this.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setFocusColor( new Color(0,0,0,0) );
		
		this.setRowHeight( 36 );
		
	}
	
	
	public void addProduct( Product product ){
		
		model.addRow( product );
		
	}
	public void setProduct( int actualR , Product product ){
		
		model.setActualRowData( actualR , product );
		
	}
	public Product getProduct( int actualR ){
		
		return (Product)model.getActualRowData( actualR );
		
	}
	
	
	public void addProducts( ArrayList<Product> products ){
		
		
		for( int actualR=0; actualR<products.size(); actualR++ )
		{
			model.addRow( products.get( actualR ) );
		}
		
	}
	public void setProducts( ArrayList<Product> products ){
		
		model.removeAllRows();
		for( int actualR=0; actualR<products.size(); actualR++ )
		{
			model.addRow( products.get( actualR ) );
		}
		
		
		
		
	}
	
	public void removeProduct( Product product ){
		
		this.model.removeRow( product );
		
	}
	public void removeProducts( ArrayList<Product> products ){
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			this.model.removeRow( product );
		}
		
		
		
	}
	
	public ArrayList<Product> getAllProducts(){
		
		ArrayList<Product> productList = new ArrayList<Product>();
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Product product = (Product) this.model.getActualRowData( actualR );
			productList.add( product );
		}
		
		
		return productList;
		
	}
	
	public Product getSelectedProduct(){
		
		
		int displayedR = this.getSelectedRow();
		if( displayedR == -1 )
		{
			return null;
		}
		
		Product product = (Product) model.getDisplayedRowData( displayedR );
		return product;
		
		
		
	}
	
	
	
	public int getActualRowIndex( String plu ){
		
		for( int actualR=0; actualR < model.getActualRowCount(); actualR++ )
		{
			Product product = (Product) model.getActualRowData( actualR );
			if( product.plu.equals( plu ) )
			{
				return actualR;
			}
		}
		
		return -1;
		
		
	}
	
	
	
	
	
	public TableCellRenderer getCellRenderer( int displayedR , int displayedC ) {
		
		
		boolean isSelected = ( this.getSelectedRow() == displayedR );
		return new RowRenderer( isSelected , this.getSelectionBackground() );
		
		
//		int actualC = model.getActualC( displayedC );
//		
//		if( actualC == 0 )			return new RowRenderer();//plu
//		else if( actualC == 1 )		return new RowRenderer();//upc
//		else if( actualC == 2 )		return new RowRenderer();
//		else if( actualC == 3 )		return new RowRenderer();
//		else if( actualC == 4 )		return new RowRenderer();
//		else if( actualC == 5 )		return new RowRenderer();
//		else if( actualC == 6 )		return new RowRenderer();
//		else if( actualC == 7 )		return new RowRenderer();
//		
//		else						return super.getCellRenderer( displayedR , displayedC );
		
		
		
		/*
		if     ( actualC == 0 ) 	return this.plu;
		if     ( actualC == 1 ) 	return this.upc;
		if     ( actualC == 2 ) 	return this.title;
		else if( actualC == 3 )		return this.line1;
		else if( actualC == 4 )		return this.line2;
		else if( actualC == 5 )		return this.line3;
		else if( actualC == 6 )		return this.price;
		else if( actualC == 7 )		return this.status;
		
		*/
		
	}
	private class RowRenderer extends MyTable.StringRenderer {
		
		Color selectionBackground;
		boolean isSelected;
		
		public RowRenderer( boolean isSelected , Color selectionBackground ){
			
			super( SwingConstants.CENTER );
			
			this.isSelected = isSelected;
			this.selectionBackground = selectionBackground;
			
		}
		
		public void setValue( RowData rowData , Object value ){
			
			super.setValue( rowData , value );
			
			
			
			
			
			Product product = (Product) rowData;
			String status = product.status.toLowerCase();
			
			if( isSelected )
			{
				setBackground( selectionBackground );
			}
			else
			{
				if( status.equals( "w" ) )
				{
					setBackground( new Color( 200,250,200 ) );
				}
				else if( status.equals( "d" ) )
				{
					setBackground( new Color( 250,220,220 ) );
				}
			}
				
			
			
			
			
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	
}



















