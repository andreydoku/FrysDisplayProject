package basic;



import java.io.*;
import java.util.ArrayList;



import myLibrary.ArrayToString;
import myLibrary.Methods;
import saveLoad.IllegalSaveStringException;
import saveLoad.SaveLoad;

public class SchematicBuilder{
	
	
	
	public static ArrayList<Product> removeD( ArrayList<Product> productList ){
		
		ArrayList<Product> newProductList = new ArrayList<Product>();
		
		while( productList.size() > 0 )
		{
			Product product = productList.get( 0 );
			if( !product.status.toLowerCase().equals( "d" ) )
			{
				newProductList.add( product );
			}
			
			productList.remove( product );
		}
		
		return newProductList;
		
	}
	
	public static ArrayList<Product> arrangeByMhz( ArrayList<Product> productList ){
		
		ArrayList<Product> sortedProductList = new ArrayList<Product>();
		
		while( productList.size() > 0 )
		{
			int lowestIndex = getLowestMhz( productList );
			Product lowestProduct = productList.get( lowestIndex );
			
			sortedProductList.add( lowestProduct );
			productList.remove( lowestProduct );
		}
		
		return sortedProductList;
		
		
		
		
		
	}
	private static int getLowestMhz( ArrayList<Product> productList ){
		
		int lowestIndex = 0;
		String lowestMhz = "zzzz";
		
		for( int i=0; i<productList.size(); i++ )
		{
			Product product = productList.get( i );
			String mhz = product.line2;
			
			if( !mhz.isEmpty()  &&  mhz.compareTo( lowestMhz ) < 0 )
			{
				lowestIndex = i;
				lowestMhz = mhz;
			}
			
		}
		
		return lowestIndex;
		
	}
	
	private static ArrayList<Product> sortByModules( ArrayList<Product> productList ){
		
		ArrayList<Product> sortedList = new ArrayList<Product>();
		//String[] modulesList = { "4x16gb" , "2x16gb" , "1x16gb" , "4x8gb" , "2x8gb" , "1x8gb" , "4x4gb" , "2x4gb" , "1x4gb" , "2x2gb" , "1x2gb" , "2x1gb" , "1x1gb" };
		String[] modulesList = { "1x1gb" , "2x1gb" , "1x2gb" , "2x2gb" , "1x4gb" , "2x4gb" , "4x4gb" , "1x8gb" , "2x8gb" , "4x8gb" , "1x16gb" , "2x16gb" , "4x16gb" };
		for( int i=0; i<modulesList.length; i++ )
		{
			copyIfModulesEquals( modulesList[i] , productList , sortedList );
		}
		
		if( sortedList.size() != productList.size() )
		{
			System.out.println( "not equal size!" );
		}
		
		return sortedList;
		
	}
	private static void copyIfModulesEquals( String modules , ArrayList<Product> list1 , ArrayList<Product> list2 ){
		
		for( int i=0; i<list1.size(); i++ )
		{
			Product product = list1.get( i );
			String productModules = getModules( product ).toLowerCase();
			if( productModules.equals( modules ) )
			{
				list2.add( product );
			}
		}
		
	}
	private static String getModules( Product product ){
		
		return Methods.getSubstring( product.title , "(" , ")" );
		
	}
	
	private static void createSchematicFromClipboard() throws IllegalSaveStringException{
		
		String clipboard = Methods.getClipboardString();
		ArrayList<String> saveStrings = Methods.split( clipboard , "\n" , false , true , true );
		
		
		ArrayList<ArrayList<String>> pieces = Methods.split( saveStrings , "---" );
		
		int cCount = 7;
		int rCount = 0;
		for( int i=0; i<pieces.size(); i++ )
		{
			ArrayList<String> subArray = pieces.get( i );
			if( subArray.size() > rCount )
			{
				rCount = subArray.size();
			}
			
			
			
		}
		
		Product[][] product2dArray = new Product[rCount][cCount];
		
		
		for( int c=0; c<pieces.size(); c++ )
		{
			ArrayList<String> subArray = pieces.get( c );
			for( int r=0; r<subArray.size(); r++ )
			{
				String saveString = subArray.get( r );
				Product product = SaveLoad.getProduct( saveString );
				
				product2dArray[r][c] = product;
				
			}
		}
		
		
		
		
		
		for( int r = 0; r<rCount; r++ )
		{
			System.out.print( "|" );
			for( int c=0; c<cCount; c++ )
			{
				Product product = product2dArray[r][c];
				if( product == null )
				{
					System.out.print( "       " + "|" );
				}
				else
				{
					System.out.print( product.plu + "|" );
				}
			}
			
			System.out.println();
		}
		
		
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, IllegalSaveStringException {
		
		
//		String directory = "database/";
//		String filename = "Desktop - DDR.txt";
//		
//		File file = new File( directory + filename );
//		ArrayList<String> saveStrings = Methods.readTextFileLineByLine( file );
//		ArrayList<Product> productList = SaveLoad.getProductList( saveStrings );
		
		
		
//		//sort by mhz
//		String clipboard = Methods.getClipboardString();
//		ArrayList<String> saveStrings = Methods.split( clipboard , "\n" , false , true , false );
//		ArrayList<Product> productList = SaveLoad.getProductList( saveStrings );
//		
//		productList = removeD( productList );
//		
//		ArrayList<Product> sortedList = arrangeByMhz( productList );
//		ArrayList<String> sortedSaveStrings = SaveLoad.getSaveStringList( sortedList );
//		ArrayToString.printArray( sortedSaveStrings , "\n" );
		
		
		
//		//sort by 2x8GB
//		String clipboard = Methods.getClipboardString();
//		ArrayList<String> saveStrings = Methods.split( clipboard , "\n" , false , true , false );
//		ArrayList<Product> productList = SaveLoad.getProductList( saveStrings );
//		
//		ArrayList<Product> sortedList = sortByModules( productList );
//		ArrayList<String> sortedSaveStrings = SaveLoad.getSaveStringList( sortedList );
//		ArrayToString.printArray( sortedSaveStrings , "\n" );
		
		
		
		
		createSchematicFromClipboard();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}












