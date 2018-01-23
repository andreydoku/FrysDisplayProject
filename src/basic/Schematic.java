package basic;

import java.util.ArrayList;

import database.Database;

public class Schematic{
	
	
	public String title;
	public String saveString;
	
	
	public ArrayList<Section> sections = new ArrayList<Section>();
	
	public Schematic( String title ){
		
		this.title = title;
		
	}
	public Schematic( String title, String string ){
		
		this.title = title;
		this.saveString = string;
		
	}
	
	
	public void addSection( String title , int rows , int cols ){
		
		sections.add( new Section( title , rows , cols ) );
		
	}
	public void setPlu( String plu , String sectionTitle , int r , int c ){
		
		Section section = getSection( sectionTitle );
		if( section != null )
		{
			section.plus[r][c] = plu;
		}
		else
		{
			String message = "setPlu( plu , sectionTitle , r , c ): panelTitle not found";
			message += "\n" + "sectionTitle: " + sectionTitle;
			message += "\n" + "\n" + "Existing section titles: " + sections.size();
			for( int i=0; i<sections.size(); i++ )
			{
				message += "\n" + "\t" + sections.get( i ).title;
				
			}
			throw new IllegalArgumentException( message );
		}
		
	}
	private Section getSection( String sectionTitle ){
		
		for( int i=0; i<sections.size(); i++ )
		{
			Section section = sections.get( i );
			if( section.title.toLowerCase().equals( sectionTitle.toLowerCase() ) )
			{
				return section;
			}
		}
		
		return null;
		
	}
	
	public boolean containsPlu( String plu ){
		
		for( int i=0; i<sections.size(); i++ )
		{
			Section section = sections.get( i );
			if( section.containsPlu( plu ) )
			{
				return true;
			}
		}
		
		return false;
		
	}
	public ArrayList<Product> getAllDstatProducts( Database db ){
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		for( int i=0; i<sections.size(); i++ )
		{
			Section section = sections.get( i );
			for( int r=0; r<section.rows; r++ )
			{
				for( int c=0; c<section.cols; c++ )
				{
					String plu = section.plus[r][c];
					Product product = db.getProduct( plu );
					if( product != null )
					{
						if( product.status.toLowerCase().equals( "d" ) )
						{
							products.add( product );
						}
					}
				}
			}
			
		}
		
		return products;
		
	}
	
	
	
	
	public class Section {
		
		public String title;
		public String[][] plus;
		
		public int rows, cols;
		
		public Section( String title , int rows , int cols ){
			
			this.title = title;
			plus = new String[rows][cols];
			this.rows = rows;
			this.cols = cols;
			
		}
		
		public boolean containsPlu( String plu ){
			
			for( int r=0; r<rows; r++ )
			{
				for( int c=0; c<cols; c++ )
				{
					if( plus[r][c] != null && plus[r][c].equals( plu ) )
					{
						return true;
					}
				}
			}
			
			return false;
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}





