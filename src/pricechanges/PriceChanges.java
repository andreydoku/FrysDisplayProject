package pricechanges;

import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import basic.Product;
import basic.Schematic;
import database.Database;
import gui.TextAreaDialog;
import saveLoad.IllegalSaveStringException;
import saveLoad.SaveLoad;

public class PriceChanges extends JFrame {
	
	
	AdPanel adPanel = new AdPanel();
	
	Database db = null;
	ArrayList<Schematic> schematics = new ArrayList<Schematic>();
	ArrayList<Product> adProductList = null;
	ArrayList<Product> rebateProductList = null;
	
	
	Product beforeProduct = null;
	Product afterProduct = null;
	
	
	public PriceChanges(){
		
		loadDatabase();
		loadSchematics();
		loadAd();
		loadRebates();
		
		
		build();
		
	}
	public void build(){
		
		
		
		
		adPanel.pluField.addActionListener( new MyActionListener() );
		adPanel.onOffBox.addActionListener( new MyActionListener() );
		adPanel.regPriceField.addActionListener( new MyActionListener() );
		adPanel.adPriceField.addActionListener( new MyActionListener() );
		adPanel.goodThruField.addActionListener( new MyActionListener() );
		adPanel.promoCheckbox.addActionListener( new MyActionListener() );
		adPanel.applyBtn.addActionListener( new MyActionListener() );
		
		adPanel.pluField.addFocusListener( new MyActionListener() );
		adPanel.onOffBox.addFocusListener( new MyActionListener() );
		adPanel.regPriceField.addFocusListener( new MyActionListener() );
		adPanel.adPriceField.addFocusListener( new MyActionListener() );
		adPanel.goodThruField.addFocusListener( new MyActionListener() );
		adPanel.promoCheckbox.addFocusListener( new MyActionListener() );
		
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.add( adPanel );
		this.setSize( 850 , 463 );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
		
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
			
			
			dbProduct.adPrice = adProduct.adPrice;
			dbProduct.adDate = adProduct.adDate;
			dbProduct.isPromo = adProduct.isPromo;
			
			
		}
		
		
		
	}
	public void loadRebates(){
		
		
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
			
			
			dbProduct.rebate = rebateProduct.rebate;
			dbProduct.rebateGoodThru = rebateProduct.rebateGoodThru;
			
			
		}
		
		
		
	}
	
	
	
	
	public void pluFieldChanged(){
		
		System.out.println( "plu field changed" );
		System.out.println( this.getSize() );
		
		
		String plu = adPanel.pluField.getText();
		if( plu.isEmpty() )
		{
			String message = "Please enter the PLU";
			JOptionPane.showMessageDialog( this , message );
			
			adPanel.setState( 0 );
			adPanel.beforeProductTile.setProduct( null );
			adPanel.afterProductTile.setProduct( null );
			
			adPanel.setEnabled( false );
			
			return;
			
		}
		
		
		beforeProduct = db.getProduct( plu );//returns null if plu doesnt exist
		if( beforeProduct == null )
		{
			String message = "PLU not found";
			JOptionPane.showMessageDialog( this , message );
			
			adPanel.setState( 0 );
			adPanel.setEnabled( false );
			
			return;
		}
		
		
		System.out.println( beforeProduct );
		System.out.println( beforeProduct.regPrice );
		
		adPanel.regPriceField.setText( beforeProduct.regPrice );
		adPanel.adPriceField.setText( beforeProduct.adPrice );
		adPanel.goodThruField.setText( beforeProduct.adDate );
		adPanel.promoCheckbox.setSelected( beforeProduct.isPromo );
		adPanel.setState( 1 );
		adPanel.setEnabled( false );
		
		adPanel.beforeProductTile.setProduct( beforeProduct );
		adPanel.afterProductTile.setProduct( null );
		adPanel.repaint();
		
		
		
		//adPanel.adPriceField.setText( t );
		
		
		
		
	}
	public void regPriceChanged(){
		
		System.out.println( "reg price changed: " + adPanel.regPriceField.getText() );
		
		String regPrice = adPanel.regPriceField.getText();
		afterProduct.regPrice = regPrice;
		
		adPanel.afterProductTile.setProduct( afterProduct );
		adPanel.repaint();
		
	}
	
	public void onOffBoxChanged(){
		
		System.out.println( "on/off changed" );
		System.out.println( "current state: " + adPanel.getState() );
		
		int selectedIndex = adPanel.onOffBox.getSelectedIndex();
		if( selectedIndex == 0 )//empty
		{
			chooseOneSelected();
		}
		else if( selectedIndex == 1 )//on ad
		{
			onAdSelected();
		}
		else if( selectedIndex == 2 )//off ad
		{
			offAdSelected();
		}
		
	}
	private void chooseOneSelected(){
		
		if( beforeProduct != null )	
		{
			adPanel.regPriceField.setText( beforeProduct.regPrice );
			adPanel.adPriceField.setText( beforeProduct.adPrice );
			adPanel.goodThruField.setText( beforeProduct.adDate );
			adPanel.promoCheckbox.setSelected( false );
		}
		
		
		
		adPanel.setState( 1 );
		
		
		adPanel.afterProductTile.setProduct( null );
		adPanel.repaint();
		
		
		
	}
	private void onAdSelected(){
		
		//adPanel.regPriceField.setText( beforeProduct.regPrice );
		//adPanel.adPriceField.setText( beforeProduct.adPrice );
		//adPanel.goodThruField.setText( beforeProduct.adDate );
		//adPanel.promoCheckbox.setSelected( beforeProduct.isPromo );
		
		adPanel.setState( 2 );
		afterProduct = beforeProduct.getCopy();
		if( afterProduct.adPrice.isEmpty() )
		{
			afterProduct.adPrice = beforeProduct.regPrice;
		}
		
		adPanel.regPriceField.setText( afterProduct.regPrice );
		adPanel.adPriceField.setText( afterProduct.adPrice );
		adPanel.goodThruField.setText( afterProduct.adDate );
		adPanel.promoCheckbox.setSelected( afterProduct.isPromo );
		
		adPanel.adPriceField.selectAll();
		
		adPanel.afterProductTile.setProduct( afterProduct );
		adPanel.repaint();
		
	}
	private void offAdSelected(){
		
		adPanel.regPriceField.setText( beforeProduct.regPrice );
		adPanel.adPriceField.setText( "" );
		adPanel.goodThruField.setText( "" );
		adPanel.promoCheckbox.setSelected( false );
		
		adPanel.setState( 3 );
		afterProduct = beforeProduct.getCopy();
		afterProduct.adPrice = null;
		afterProduct.adDate = null;
		afterProduct.isPromo = false;
		
		adPanel.afterProductTile.setProduct( afterProduct );
		adPanel.repaint();
		
	}
	
	public void addPriceChanged(){
		
		System.out.println( "add price changed: " + adPanel.adPriceField.getText() );
		
		String adPrice = adPanel.adPriceField.getText();
		adPrice = adPrice.replace( "$" , "" ).replace( "," , "" ).trim();
		
		double adPriceDouble;
		try
		{
			adPriceDouble = Double.parseDouble( adPrice );
		}
		catch( NumberFormatException nfe )
		{
			String message = "Ad price has to be a number";
			JOptionPane.showMessageDialog( this , message );
			
			adPanel.adPriceField.requestFocus();
			adPanel.adPriceField.selectAll();
			
			return;
		}
		
		
		String adjustedAdString = new DecimalFormat( "$#,##0.00" ).format( adPriceDouble );
		adPanel.adPriceField.setText( adjustedAdString );
		
		afterProduct.adPrice = adjustedAdString;
		adPanel.afterProductTile.setProduct( afterProduct );
		adPanel.repaint();
		
		
	}
	
	public void goodThruChanged(){
		
		String goodThru = adPanel.goodThruField.getText();
		
		Date date;
		try
		{
			date = new SimpleDateFormat("MM/dd/yy").parse( goodThru );
			System.out.println( "date changed to: " + new SimpleDateFormat("MM/dd/yy").format( date ) );
		}
		catch ( ParseException e )
		{
			String message = "Good thru date needs to be in this format: mm/dd/yy";
			JOptionPane.showMessageDialog( this , message );
			
			adPanel.goodThruField.requestFocus();
			adPanel.goodThruField.selectAll();
			
			return;
		}
		
		
		afterProduct.adDate = goodThru;
		adPanel.afterProductTile.setProduct( afterProduct );
		adPanel.repaint();
		
		
	}
	
	
	public void promoChanged(){
		
		boolean isPromo = adPanel.promoCheckbox.isSelected();
		afterProduct.isPromo = isPromo;

		adPanel.afterProductTile.setProduct( afterProduct );
		adPanel.repaint();
		
	}
	
	
	
	
	
	public void applyPushed(){
		
		System.out.println( "apply pushed" );
		
		
		int selectedIndex = adPanel.onOffBox.getSelectedIndex();
		if( selectedIndex == 0 )//empty
		{
			//shouldnt be possible
		}
		else if( selectedIndex == 1 )//on ad
		{
			applyOnAd();
		}
		else if( selectedIndex == 2 )//off ad
		{
			applyOffAd();
		}
		
		
		
	}
	private void applyOnAd(){
		
		
		System.out.println( "applyOnAd" );
		
		
		String goodThruInput = adPanel.goodThruField.getText();
		if( goodThruInput.isEmpty() )
		{
			String message = "Please enter good thru date (mm/dd/yy)";
			JOptionPane.showMessageDialog( this , message );
			
			adPanel.goodThruField.requestFocus();
			return;
		}
		
		
		String goodThru = adPanel.goodThruField.getText();
		Date date;
		try
		{
			date = new SimpleDateFormat("MM/dd/yy").parse( goodThru );
			
		}
		catch ( ParseException e )
		{
			//invalid date entry - will be caught by focus listener
			//this is here for the instance where the user enters something invalid into the date thing, then leaves date field by clicking the apply button
			return;
		}
		
		String adPriceString = adPanel.adPriceField.getText();
		adPriceString = adPriceString.replace( "$" , "" ).replace( "," , "" ).trim();
		double adPriceDouble;
		try
		{
			adPriceDouble = Double.parseDouble( adPriceString );
		}
		catch( NumberFormatException nfe )
		{
			//should be caught by focus listener
			return;
		}
		
		
		System.out.println( "on-ad apply successful" );
		System.out.println( "\t" + "plu: " + adPanel.pluField.getText() );
		System.out.println( "\t" + "ad price: " + adPanel.adPriceField.getText() );
		System.out.println( "\t" + "good thru: " + goodThru );
		System.out.println( "\t" + "promo: " + adPanel.promoCheckbox.isSelected() );
		
		
		
	}
	private void applyOffAd(){
		
		
		
	}
	
	
	
	private class MyActionListener implements ActionListener, FocusListener {

		@Override
		public void actionPerformed( ActionEvent e ){
			
			if( e.getSource() == adPanel.pluField )
			{
				pluFieldChanged();
			}
			else if( e.getSource() == adPanel.onOffBox )
			{
				onOffBoxChanged();
			}
			else if( e.getSource() == adPanel.regPriceField )
			{
				regPriceChanged();
			}
			else if( e.getSource() == adPanel.adPriceField )
			{
				addPriceChanged();
			}
			else if( e.getSource() == adPanel.goodThruField )
			{
				goodThruChanged();
			}
			else if( e.getSource() == adPanel.promoCheckbox )
			{
				promoChanged();
			}
			else if( e.getSource() == adPanel.applyBtn )
			{
				applyPushed();
			}
			
			
			
		}

		
		public void focusGained( FocusEvent arg0 ){
			
			
			
		}

		@Override
		public void focusLost( FocusEvent e ){
			
			if( e.getSource() == adPanel.pluField )
			{
				String newPlu = adPanel.pluField.getText();
				if( beforeProduct.plu != null && !newPlu.equals( beforeProduct.plu ) )
				{
					pluFieldChanged();
				}
				
			}
			else if( e.getSource() == adPanel.onOffBox )
			{
				onOffBoxChanged();
			}
			else if( e.getSource() == adPanel.regPriceField )
			{
				regPriceChanged();
			}
			else if( e.getSource() == adPanel.adPriceField && !adPanel.adPriceField.getText().isEmpty() )
			{
				addPriceChanged();
			}
			else if( e.getSource() == adPanel.goodThruField && !adPanel.goodThruField.getText().isEmpty() )
			{
				goodThruChanged();
			}
			else if( e.getSource() == adPanel.promoCheckbox )
			{
				promoChanged();
			}
			
		}
		
		
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		
		PriceChanges pc = new PriceChanges();
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
}











