package database;

import java.util.ArrayList;

import basic.Product;





public class ProductList {
	
	String title = "";
	ArrayList<Product> products = new ArrayList<Product>();
	
	public ProductList( String title , ArrayList<Product> products ){
		
		this.title = title;
		this.products.addAll( products );
		
	}
	
	public Product getProduct( String plu ){
		
		for( int i=0;i<products.size(); i++ )
		{
			Product product = products.get( i );
			if( product.plu.equals( plu ) )
			{
				return product;
			}
		}
		
		return null;
		
	}
	
}