package database;

import java.util.ArrayList;

import basic.Product;

public class Database {
	
	
	private ArrayList<ProductList> productListArray = new ArrayList<ProductList>();
	
	
	
	public Database(){
		
		
		
	}
	public void addList( String title , ArrayList<Product> products ){
		
		ProductList productList = new ProductList( title , products );
		productListArray.add( productList );
		
	}
	
	
	public int getListCount(){
		
		return productListArray.size();
		
	}
	public ProductList getList( int i ){
		
		return productListArray.get( i );
		
	}
	
	public Product getProduct( String plu ){
		
		for( int i=0; i<productListArray.size(); i++ )
		{
			ProductList list = productListArray.get( i );
			Product product = list.getProduct( plu );
			if( product != null )//found one
			{
				return product;
			}
		}
		
		return null;
		
	}
	
	
	
	
	
	
}

















