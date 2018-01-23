package database;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import basic.Product;
import myLibrary.*;
import saveLoad.IllegalSaveStringException;
import saveLoad.SaveLoad;

public class FrysWebsiteChecker{
	
	
	
	public final static String notFoundTag = "Sorry, but no products were found.";
	
	
	
	public static ArrayList<String> getHtmlArray( String plu ) throws IOException, IllegalArgumentException{
		
		String address = "http://www.frys.com/product/" + plu;
		
		
		String[] cutoffs = { notFoundTag , "Detailed Description" };
		
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( address );
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( address , cutoffs , true );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			System.out.println( "Frys: " + notFoundTag );
			System.out.println( "------------------end--------------------------------------------------------------------" );
			throw new IllegalArgumentException( "PLU not found: " + plu );
		}
		
		
		return htmlArray;
		
	}
	public static String getUpc( ArrayList<String> htmlArray ){
		
		
		String upc = Methods.getSubstring( htmlArray , "<li style=\"width: 160px;\">UPC #" , "</li>" );
		if( upc.isEmpty() )
		{
			upc = null;
		}

		return upc;
		
	}
	public static String getBrand( ArrayList<String> htmlArray ){
		
		
		String manufacturer = Methods.getSubstring( htmlArray , "Manufacturer: " , "</li>" );
		manufacturer = manufacturer.trim();
		
				
		
		return manufacturer;
		
	}
	public static String getModel( ArrayList<String> htmlArray ){
		
		
		String model = Methods.getSubstring( htmlArray , "Model #" , "</li>" );
		model = model.trim();
		
				
		
		return model;
		
	}
	public static String getDescription( ArrayList<String> htmlArray ){
		
		
		String desc = Methods.getSubstring( htmlArray , "description\" content=", "\">" );
		desc = desc.trim();
		
				
		
		return desc;
		
	}
	
	//
	
	
	public static BufferedImage getImage( String plu ) throws IOException{
		
		String address = "http://images.frys.com/art/product/300x300/" + plu + ".01.prod.jpg";
		URL url = new URL( address );
		
		BufferedImage image = ImageIO.read( url );
		image = Methods.makeWhiteTransparent( image , 250 );
		
		return image;
		
	}
	public static ArrayList<BufferedImage> getAllImages( String plu ){
		
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		
		boolean keepGoing = true;
		int i=1;
		
		try
		{
			while( keepGoing )
			{
				String address = "http://images.frys.com/art/product/300x300/" + plu + ".0" + i + ".prod.jpg";
				URL url = new URL( address );
				
				BufferedImage image = ImageIO.read( url );
				//image = Methods.makeWhiteTransparent( image , 250 );
				
				images.add( image );
				
				i++;
			}
				
		}
		catch( IOException e )
		{
			keepGoing = false;
		}
			
		
		return images;
		
	}
	public static void pickImage( String plu , String title ){
		
		ArrayList<BufferedImage> images = getAllImages( plu );
		ArrayList<JFrame> frames = new ArrayList<JFrame>();
		
		if( images.size() == 0 )
		{
			JOptionPane.showMessageDialog( null , "0 images found: " + plu );
			return;
		}
		
		
		for( int i=0; i<images.size(); i++ )
		{
			BufferedImage image = images.get( i );
			String fullTitle = title + " - " + String.valueOf(i+1);
			int x = 200 + i*310;
			int y = 200;
			
			if( i>3 )
			{
				y += 360;
				x = 200 + (i-4)*310;
			}
			
			JFrame frame = Methods.displayImage( image , x,y , fullTitle );
			frames.add( frame );
		}
		
		String input = JOptionPane.showInputDialog( "Enter image number" );
		int number = Integer.parseInt( input );
		int i = number-1;
		
		File file = new File( "images/" + plu + ".jpg" );
		try
		{
			Methods.saveAsJpeg( images.get( i ) , file );
		}
		catch ( IOException e )
		{
			System.out.println( "failed to save as JPEG" );
			e.printStackTrace();
		}
		
		for( int x=0; x<frames.size(); x++ )
		{
			frames.get( x ).dispose();
		}
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, IllegalSaveStringException {
		
//		String plu = "3976678";
//		
//		ArrayList<String> htmlArray = getHtmlArray( plu );
//		String upc = getUpc( htmlArray );
//		String brand = getBrand( htmlArray );
//		
//		System.out.println( "upc: " + upc );
//		System.out.println( "brand: " + brand );
		
		
		//String plu = "9018937";
		//String plu = "7985150";
		//pickImage( plu , plu );
		
		
		
//		String plu = "7985150";
//		ArrayList<String> htmlArray = getHtmlArray( plu );
//		ArrayToString.printArray( htmlArray , "\n" , true );
//		
//		String model = getModel( htmlArray );
//		System.out.println( "model: " + model );
//		
//		String description = getDescription( htmlArray );
//		System.out.println( "description: " + description );
		
		
		
//		String[] plus = { "9015797" , "8535349" , "9112068" , "9112398" , "9112078" };
//		for( int i=0; i<plus.length; i++ )
//		{
//			pickImage( plus[i] , plus[i] );
//		}
		
		
		
		
		
		Database db = SaveLoad.loadDatabase();
		for( int i=0; i<db.getListCount(); i++ )
		{
			ProductList list = db.getList( i );
			for( int j=0; j<list.products.size(); j++ )
			{
				Product p = list.products.get( j );
				String filePath = "images/" + p.plu + ".jpg";
				File file = new File( filePath );
				
				if( !file.exists() )
				{
					pickImage( p.plu , p.plu + " - " + p.title );
				}
				
			}
		} 
		
		JOptionPane.showMessageDialog( null , "DONE" );
		
		
		
		
	}
	
	
	
	
	
	
}


















