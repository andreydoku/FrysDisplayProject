package saveLoad;

import static saveLoad.SaveLoad.DATABASE_DIRECTORY;
import static saveLoad.SaveLoad.loadDatabaseFile;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import basic.Product;
import basic.Schematic;
import database.Database;
import myLibrary.*;

public class SaveLoad{
	
	
	
	public final static String D = "|";
	
	
	public final static String DATABASE_DIRECTORY = "database/";
	public final static int DATABASE_PIECE_COUNT = 8;
	
	
	public final static String SCHEMATICS_DIRECTORY = "schematics/";
	
	final static String SCHEMATIC_FILE_PATH = "schematic/schematic.txt";
	public final static String SCHEMATIC_DELIMETER = "---------------------------------------------------------" + "\n";
	public final static int COLS = 7;
	
	
	public final static String AD_FILE_PATH = "ad/ad.txt";
	public final static String REBATES_FILE_PATH = "rebates/rebates.txt";
	
	
	public final static String SDR_DIRECTORY = "stock display report/";
	public final static String[] SDR_FILE_NAMES = { "ddr.txt" , "ddr2.txt" , "ddr3.txt" , "ddr4.txt" };
	
	
	
	
	
	
	//save database
	public static String getSaveString( Product product ){
		
		String saveString = "";
		
		saveString += product.plu + D;		// 0
		saveString += product.upc + D;		// 1
		saveString += product.title + D;	// 2
		saveString += product.line1 + D;	// 3
		saveString += product.line2 + D;	// 4
		saveString += product.line3 + D;	// 5
		saveString += product.regPrice + D;	// 6
		saveString += product.status + D;	// 7
		
		
		return saveString;
		
	}
	public static ArrayList<String> getSaveStringList( ArrayList<Product> ramList ){
		
		ArrayList<String> saveStringList = new ArrayList<String>();
		for( int i=0; i<ramList.size(); i++ )
		{
			Product product = ramList.get( i );
			String saveString = getSaveString( product );
			saveStringList.add( saveString );
		}
		
		return saveStringList;
		
		
	}
	
	
	
	//load database
	public static Product getProduct( String saveString ) throws IllegalSaveStringException{
		
		ArrayList<String> pieces = Methods.split( saveString , "|" , false , true , true );
		if( pieces.size() != DATABASE_PIECE_COUNT )
		{
			String message = "should have " + DATABASE_PIECE_COUNT + " pieces, found: " + pieces.size() + "\n";
			message += "IllegalSaveString: " + saveString + "\n";
			message += "pieces: " + "\n";
			message += ArrayToString.getString( pieces , "\n" , true );
			throw new IllegalSaveStringException( saveString , message );
		}
		
		
		Product product = new Product();
		product.plu = pieces.get( 0 );
		product.upc = pieces.get( 1 );
		product.title = pieces.get( 2 );
		product.line1 = pieces.get( 3 );
		product.line2 = pieces.get( 4 );
		product.line3 = pieces.get( 5 );
		product.regPrice = pieces.get( 6 );
		product.status = pieces.get( 7 );
		
		
		return product;
		
		
		
//		ArrayList<String> pieces = Methods.split( saveString , "|" , false , true , true );
//		Product product = new Product();
//		
//		product.plu = pieces.get( 0 );
//		product.upc = pieces.get( 1 );
//		
//		//product.line1 = pieces.get( 2 ) + " " + pieces.get( 3 ) + " " + "(" + pieces.get( 4 ) + ")";
//		product.title = pieces.get( 2 );
//		if( !pieces.get( 3 ).isEmpty() )	product.title += " " + pieces.get( 3 );
//		if( !pieces.get( 4 ).isEmpty() )	product.title += " " + "(" + pieces.get( 4 ) + ")";
//		
//		
//		
//		//product.line1 = pieces.get( 6 ) + pieces.get( 5 );
//		product.line1 = pieces.get( 6 );
//		if( !pieces.get( 5 ).isEmpty() )	product.line1 += " " + pieces.get( 5 );
//		
//		product.line2 = pieces.get( 7 );
//		product.line3 = pieces.get( 8 );
//		product.price = pieces.get( 9 );
//		product.status = pieces.get( 10 );
//		
//		return product;
		
		
		
	}
	public static ArrayList<Product> getProductList( ArrayList<String> saveStringList ) throws IllegalSaveStringException{
		
		ArrayList<Product> productList = new ArrayList<Product>();
		for( int i=0; i<saveStringList.size(); i++ )
		{
			String saveString = saveStringList.get( i );
			
			Product product;
			try
			{
				product = getProduct( saveString );
			}
			catch ( IllegalSaveStringException e )
			{
				throw e;
			}
			
			productList.add( product );
		}
		
		return productList;
		
	}
	public static ArrayList<Product> loadDatabaseFile( File file ) throws IOException, IllegalSaveStringException{
		
		
		ArrayList<String> saveStrings;
		try
		{
			//System.out.println( "loading " + file.getName() + "   found saveStrings: " + saveStrings.size() );
			saveStrings = Methods.readTextFileLineByLine( file );
		}
		catch ( IOException e )
		{
			String message = "Failed to load " + file.getName();
			throw new IOException( message );
		}
		
		
		
		ArrayList<Product> productList = SaveLoad.getProductList( saveStrings );
		return productList;
		
		
	}
	public static Database loadDatabase() throws FileNotFoundException, IOException, IllegalSaveStringException{
		
		Database database = new Database();
		
		
		File directory = new File( DATABASE_DIRECTORY );
		if( !directory.exists() )
		{
			//addTab( "New List" , new ArrayList<Product>() );
			//return;
			
			database.addList( "New List" , new ArrayList<Product>() );
			return database;
			
		}
		
		File[] textFiles = directory.listFiles( new FileExtensionFilter( ".txt" ) );
		
		if( textFiles.length == 0 )
		{
			//addTab( "New List" , new ArrayList<Product>() );
			//return;
			
			database.addList( "New List" , new ArrayList<Product>() );
			return database;
		}
		
		for( int i=0; i<textFiles.length; i++ )
		{
			File textFile = textFiles[i];
			ArrayList<Product> productList = loadDatabaseFile( textFile );
			
			String title = textFile.getName().replace( ".txt" , "" );
			database.addList( title , productList );
			
//			String title = textFile.getName().replace( ".txt" , "" );
//			addTab( title , productList );
			
		}
		
		return database;
		
	}
	
	
	
	
	
	//load schematics
	public static ArrayList<Schematic> loadSchematics() throws FileNotFoundException, IOException{
		
		boolean debug = true;
		
		File directory = new File( SCHEMATICS_DIRECTORY );
		if( !directory.exists() )
		{
			String message = "Failed to load schematics: schematics directory not found";
			throw new FileNotFoundException( message );
		}
		
		
		File[] schematicFiles = directory.listFiles( new FileExtensionFilter( ".txt" ) );
		
		if( schematicFiles.length == 0 )
		{
			String message = "Not schematic files found";
			throw new FileNotFoundException( message );
		}
		
		if( debug )	System.out.println( "loading schematics" );
		ArrayList<Schematic> schematics = new ArrayList<Schematic>();
		for( int i=0; i<schematicFiles.length; i++ )
		{
			File schematicFile = schematicFiles[i];
			Schematic schematic = loadSchematic( schematicFile );
			schematics.add( schematic );
			
			if( debug )	System.out.println( "\t" + "schematic loaded: " + schematicFile.getName() );
		}
		
		
		
		
		return schematics;
		
		
		
	}
	public static Schematic loadSchematic( File schematicFile ) throws FileNotFoundException, IOException{
		
		String title = schematicFile.getName();
		title = title.replace( ".txt" , "" );
		title = title.replace( "schematic" , "" );
		title = title.trim();
		
		String saveString = Methods.readTextFile( schematicFile );
		//return new Schematic( title , saveString );
		
		Schematic schematic = getSchematicFromSavestring( title , saveString );
		return schematic;
		
		
	}
	public static Schematic getSchematicFromSavestring( String title , String saveString ){
		
		boolean debug = false;
		
		Schematic schematic = new Schematic( title , saveString );
		
		
		ArrayList<String> panelStrings = Methods.split( saveString , SCHEMATIC_DELIMETER , false , false , false );
		
		if( debug ) System.out.println( "# panelStrings: " + panelStrings.size() );
		if( debug ) ArrayToString.printArray( panelStrings , "\n" , true );
		if( debug ) System.out.println( "---end panelStrings---" );
		
		for( int i=0; i<panelStrings.size(); i++ )
		{
			String panelString = panelStrings.get( i );
			ArrayList<String> lines = Methods.split( panelString , "\n" , false , true , true );
			
			if( debug ) System.out.println( "panelString index: " + i );
			if( debug ) System.out.println( "# lines: " + lines.size() );
			if( debug ) ArrayToString.printArray( lines , "\n" , true );
			if( debug ) System.out.println( "---end lines---" );
			
			
			String panelTitle = lines.get( 0 );
			lines.remove( 0 );
			
			schematic.addSection( panelTitle , lines.size() , COLS );
			
			for( int r=0; r<lines.size(); r++ )
			{
				String line = lines.get( r );
				if( line.startsWith( "|" ) )
				{
					line = line.substring( 1 , line.length() );
				}
				
				ArrayList<String> plus = Methods.split( line , "|" , false , true , true );
				
				if( debug ) System.out.println( "line index: " + r );
				if( debug ) System.out.println( "# plus: " + plus.size() );
				if( debug ) ArrayToString.printArray( plus , "\n" , true );
				if( debug ) System.out.println( "----" );
				for( int c=0; c<plus.size(); c++ )
				{
					String plu = plus.get( c );
					schematic.setPlu( plu , panelTitle , r , c );
					
				}
				
			}
				
			
			
			
		}
		
		
		return schematic;
		
		
		
	}
	
	
	

	public static ArrayList<Product> loadAd() throws FileNotFoundException, IOException{
		
		ArrayList<Product> adProducList = new ArrayList<Product>();
		
		File file = new File( AD_FILE_PATH );
		if( !file.exists() )
		{
			String message = "ad.txt not found";
			JOptionPane.showMessageDialog( null , message );
			return null;
		}
		
		
		ArrayList<String> adStrings = Methods.readTextFileLineByLine( file );
		for( int i=0; i<adStrings.size(); i++ )
		{
			String adLine = adStrings.get( i );
			
			System.out.println( "adLine: " + adLine );
			
			ArrayList<String> pieces = Methods.split( adLine , "\t" , false , true , false );
			
			
			
			Product adProduct = new Product();
			adProduct.plu = pieces.get( 0 );
			adProduct.adPrice = pieces.get( 1 );
			adProduct.adDate = pieces.get( 2 );
			adProduct.isPromo = false;
			if( pieces.size() == 4 )
			{
				adProduct.isPromo = pieces.get( 3 ).toLowerCase().equals( "p" );
			}
			
			adProducList.add( adProduct );
			
			
		}
		
		return adProducList;
		
		
		
		
		
	}
	
	public static ArrayList<Product> loadRebates() throws FileNotFoundException, IOException{
		
		ArrayList<Product> adProducList = new ArrayList<Product>();
		
		File file = new File( REBATES_FILE_PATH );
		if( !file.exists() )
		{
			String message = "rebates.txt not found";
			JOptionPane.showMessageDialog( null , message );
			return null;
						
		}
		
		
		ArrayList<String> rebateStrings = Methods.readTextFileLineByLine( file );
		for( int i=0; i<rebateStrings.size(); i++ )
		{
			String rebateLine = rebateStrings.get( i );
			
			System.out.println( "rebateLine: " + rebateLine );
			
			ArrayList<String> pieces = Methods.split( rebateLine , "\t" , false , true , false );
			
			
			
			Product adProduct = new Product();
			adProduct.plu = pieces.get( 0 );
			adProduct.rebate = Double.parseDouble(    pieces.get( 1 ).replace( "$" , "" ).replace( "," , "" )   );
			adProduct.rebateGoodThru = pieces.get( 2 );
			
			adProducList.add( adProduct );
			
			
		}
		
		return adProducList;
		
		
		
		
		
	}
	
	
	
	
	//SDR
	public static Product getProductFromSDR( String sdrString ) throws IllegalSaveStringException{
		
		
		
		
		ArrayList<String> pieces = Methods.split( sdrString , "\t" , false , true , true );
		if( pieces.size() != 3 )
		{
			String message = "Must have 1 product per line, with exactly 3 pieces of data (plu,price,status) separated by a TAB delimeter." ;
			message += "Number of pieces found: " + pieces.size();
			message += "\n" + "Line: " + sdrString;
			throw new IllegalSaveStringException( sdrString , message );
		}
		
		Product product = new Product();
		product.plu = pieces.get( 0 );
		product.regPrice = pieces.get( 1 );
		product.status = pieces.get( 2 );
		
		return product;
		
	}
	public static ArrayList<Product> getProductListFromSdrList( ArrayList<String> sdrStringList ) throws IllegalSaveStringException{
		
		ArrayList<Product> productList = new ArrayList<Product>();
		for( int i=0; i<sdrStringList.size(); i++ )
		{
			String sdrString = sdrStringList.get( i );
			
			if( sdrString.trim().isEmpty() )
			{
				continue;
			}
			
			
			Product product = getProductFromSDR( sdrString );
			productList.add( product );
			
			
			
		}
		
		return productList;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
