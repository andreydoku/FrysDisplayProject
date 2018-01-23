package display;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;

import basic.Product;
import basic.Schematic;
import database.Database;
import gui.TextAreaDialog;
import saveLoad.IllegalSaveStringException;
import saveLoad.SaveLoad;

public class DisplayLauncher {
	
	
	
	Database db = null;
	ArrayList<Schematic> schematics = new ArrayList<Schematic>();
	
	
	
	public DisplayLauncher(){
		
		
		loadDatabase();//will exit if fails to load
		loadSchematics();//will exit if fails to load
		loadAd();//will exit if fails to load
		loadRebates();//will exit if fails to load
		
		
		createDisplayFrames();
		
	}
	
	public void loadDatabase(){
		
		
		
		try
		{
			db = SaveLoad.loadDatabase();
			
		}
		catch ( FileNotFoundException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
			
		}
		catch ( IOException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		catch ( IllegalSaveStringException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		
		
		
		
		
		
		
		
		
	}
	public void loadSchematics(){
		
		try
		{
			schematics = SaveLoad.loadSchematics();
		}
		catch ( FileNotFoundException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		catch ( IOException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		
	}
	public void loadAd(){
		
		ArrayList<Product> adProductList = null;
		try
		{
			adProductList = SaveLoad.loadAd();
		}
		catch ( FileNotFoundException e )
		{
			String message = "Failed to load ad file - file not found";
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		catch ( IOException e )
		{
			String message = "Failed to load ad file - failed to open the file";
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		catch( IndexOutOfBoundsException e )
		{
			String message = "Failed to load ad file - data is entered incorrectly";
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		
		
		
		for( int i=0; i<adProductList.size(); i++ )
		{
			Product adProduct = adProductList.get( i );
			Product dbProduct = db.getProduct( adProduct.plu );
			
			if( dbProduct == null )//that plu doesnt exist in the database - either typo, or it's a new product not yet entered into the database
			{
				String message = "Failed to load ad file - unrecognized PLU";
				message += "\n";
				message += "\n" + "PLU: " + adProduct.plu;
				
				message += "\n";
				message += "\n" + "PLU is not in the database, because either...";
				message += "\n" + "1) It's a typo";
				message += "\n" + "     OR     ";
				message += "\n" + "2) PLU needs to be added to the database";
				message += "\n" + "   Use the database editor to add it in";
				TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
				System.out.println( message );
				
				while( dialog.isShowing() )
				{
					
				}
				
				System.exit( 0 );
			}
			
			dbProduct.adPrice = adProduct.adPrice;
			dbProduct.adDate = adProduct.adDate;
			dbProduct.isPromo = adProduct.isPromo;
			
			
		}
		
		
		
	}
	public void loadRebates(){
		
		ArrayList<Product> rebateProductList = null;
		try
		{
			rebateProductList = SaveLoad.loadRebates();
		}
		catch ( FileNotFoundException e )
		{
			String message = "Failed to load rebates file - file not found";
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		catch ( IOException e )
		{
			String message = "Failed to load rebates file - failed to open the file";
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		catch( IndexOutOfBoundsException e )
		{
			String message = "Failed to load rebates file - data is entered incorrectly";
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
		}
		
		
		
		for( int i=0; i<rebateProductList.size(); i++ )
		{
			Product rebateProduct = rebateProductList.get( i );
			Product dbProduct = db.getProduct( rebateProduct.plu );
			
			if( dbProduct == null )//that plu doesnt exist in the database - either typo, or it's a new product not yet entered into the database
			{
				String message = "Failed to load rebates file - unrecognized PLU";
				message += "\n";
				message += "\n" + "PLU: " + rebateProduct.plu;
				
				message += "\n";
				message += "\n" + "PLU is not in the database, because either...";
				message += "\n" + "1) It's a typo";
				message += "\n" + "     OR     ";
				message += "\n" + "2) PLU needs to be added to the database";
				message += "\n" + "   Use the database editor to add it in";
				TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , null , message );
				System.out.println( message );
				
				while( dialog.isShowing() )
				{
					
				}
				
				System.exit( 0 );
			}
			
			dbProduct.rebate = rebateProduct.rebate;
			dbProduct.rebateGoodThru = rebateProduct.rebateGoodThru;
			
			
		}
		
		
		
	}
	
	
	
	public void createDisplayFrames(){
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gd = ge.getScreenDevices();
	    
	    System.out.println( "\n" + "Screens: " + gd.length );
	    for( int i=0; i<gd.length; i++ )
		{
	    	DisplayMode dm = gd[i].getDisplayMode();
	    	int w = dm.getWidth();
	    	int h = dm.getHeight();
	    	
	    	System.out.println( "screen "+ (i+1) + ": " + w + "x" + h );
	    	
		}
	    System.out.println();
	    
		
		ArrayList<DisplayFrame2> frames = new ArrayList<DisplayFrame2>();
	    
		for( int i=0; i<schematics.size(); i++ )
		{
			Schematic schematic = schematics.get( i );
			DisplayFrame2 frame = new DisplayFrame2( db , schematic );
			
			int screenIndex = i;
			if( screenIndex >= gd.length )
			{
				screenIndex = gd.length - 1;
			}
			
		    Rectangle bounds = gd[screenIndex].getDefaultConfiguration().getBounds();
		    frame.setLocation( bounds.x, bounds.y );
		    frame.setSize( bounds.width , bounds.height );
		    frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
			
		    frames.add( frame );
		    
		}
		
		for( int i=0; i<frames.size(); i++ )
		{
			frames.get( i ).setVisible( true );
		}
		
		
	}
	
	
	public static void main(String[] args) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		DisplayLauncher dl = new DisplayLauncher();
		
	}
	
	
	
	
	
	
	
	
	
}



