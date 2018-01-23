package database;

import static saveLoad.SaveLoad.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import basic.*;
import gui.ButtonDialog;
import gui.TextAreaDialog;
import myLibrary.*;
import saveLoad.IllegalSaveStringException;
import saveLoad.SaveLoad;
import table.MyTable;
import table.MyTableSelectionListener;

public class DatabaseEditor extends JFrame {
	
	
	static String VERSION = "v2.4";
	
	
	JPanel btnPanel = new JPanel();
		JButton saveBtn = new JButton( "Save" );
		JButton pasteBtn = new JButton( "Paste" );
		JButton addBtn = new JButton( "Add" );
		JButton deleteBtn = new JButton( "Delete" );
		JButton updateBtn = new JButton( "Update SDR" );
		JButton missingImagesBtn = new JButton( "Missing Images" );
		JButton schematicBtn = new JButton( "Analyze Schematics" );
		JCheckBox hideDstatBox = new JCheckBox( "Hide D stat" );
		JLabel searchLabel = new JLabel( "Search" );
		JTextField searchField = new JTextField( 10 );
		
	JTabbedPane tabbedPane = new JTabbedPane();
	
		ArrayList<JScrollPane> tablePanelList = new ArrayList<JScrollPane>();
		ArrayList<ProductTable> productTableList = new ArrayList<ProductTable>();
		
	
	Color themeColor = new Color( 130,150,230 );
	
	
	SdrWindow sdrWindow;
	SdrChanges sdrChanges;
	
	
	ProductFilter filter = new ProductFilter();
	
	
	
	
	public DatabaseEditor(){
		
		
		build();
		
		sdrWindow = new SdrWindow(){
			
			public void loadSdrFile( File file ){
				
				DatabaseEditor.this.loadSdrFile( file );
				
			}
			public void apply(){
				
				DatabaseEditor.this.applySdr();
				
			}
			
			
			
		};
		
		
	}
	
	
	
	
	public void build(){
		
		
		buildBtnPanel();
		
		loadDatabase();
		
		
		
		
		
		
		
		tabbedPane.setFont( new Font( "Tahoma" , Font.PLAIN , 16 ) );
		
		
		setLayout( new BorderLayout() );
		add( btnPanel , BorderLayout.NORTH );
		add( tabbedPane , BorderLayout.CENTER );
		
		this.addWindowListener( new MyWindowListener() );
		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		this.setTitle( "Dababase Editor" + " " + VERSION + "   " + "by Andrey Dokuchayev" );
		this.setSize( 1600,1000 );
		this.setLocationRelativeTo( null );
		this.setVisible( true );
		
		
	}
	public void buildBtnPanel(){
		
		btnPanel.setOpaque( false );
		
		saveBtn.setOpaque( false );
		pasteBtn.setOpaque( false );
		addBtn.setOpaque( false );
		deleteBtn.setOpaque( false );
		updateBtn.setOpaque( false );
		schematicBtn.setOpaque( false );
		missingImagesBtn.setOpaque( false );
		
		
		saveBtn.addActionListener( new BtnListener() );
		pasteBtn.addActionListener( new BtnListener() );
		addBtn.addActionListener( new BtnListener() );
		deleteBtn.addActionListener( new BtnListener() );
		updateBtn.addActionListener( new BtnListener() );
		schematicBtn.addActionListener( new BtnListener() );
		missingImagesBtn.addActionListener( new BtnListener() );
		
		hideDstatBox.addActionListener( new BtnListener() );
		
		searchField.addKeyListener( new MyKeyListener() );
		
		
		
		deleteBtn.setEnabled( false );
		
		
		
		
		btnPanel.add( saveBtn );
		
		JPanel p = new JPanel();	p.setBorder( BorderFactory.createEmptyBorder( 1 , 10 , 1 , 0 ) );	btnPanel.add( p );
		
		btnPanel.add( addBtn );
		btnPanel.add( deleteBtn );
		
		JPanel p2 = new JPanel();	p2.setBorder( BorderFactory.createEmptyBorder( 1 , 10 , 1 , 0 ) );	btnPanel.add( p2 );
		
		btnPanel.add( updateBtn );
		btnPanel.add( schematicBtn );
		btnPanel.add( missingImagesBtn );
		
		JPanel p3 = new JPanel();	p3.setBorder( BorderFactory.createEmptyBorder( 1 , 50 , 1 , 0 ) );	btnPanel.add( p3 );
		
		btnPanel.add( searchLabel );
		btnPanel.add( searchField );
		btnPanel.add( hideDstatBox );
		
		
		
		
		
		
		
		
	
	}
	
	
	public void loadDatabase(){
		
		
		Database db = null;
		try
		{
			db = SaveLoad.loadDatabase();
			
		}
		catch ( FileNotFoundException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , this , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );
			
		}
		catch ( IOException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , this , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );;
		}
		catch ( IllegalSaveStringException e )
		{
			TextAreaDialog dialog = TextAreaDialog.showDialog( "Error" , this , e.getMessage() );
			e.printStackTrace();
			
			while( dialog.isShowing() )
			{
				
			}
			
			System.exit( 0 );;
		}
		
		
		
		
		for( int i=0; i<db.getListCount(); i++ )
		{
			ProductList list = db.getList( i );
			addTab( list.title , list.products );
			
		}
		
		
		
		
	}
	private void addTab( String title , ArrayList<Product> products ){
		
		JScrollPane tablePanel = new JScrollPane();
		ProductTable table = new ProductTable();
		
		
		
		
		
		tablePanel.setCorner( JScrollPane.UPPER_RIGHT_CORNER , MyTable.createCornerComponent( table ) );
		tablePanel.setViewportView( table );
		tablePanel.getViewport().setOpaque( false );
		tablePanel.setOpaque( false );
		tablePanel.setBorder( BorderFactory.createEmptyBorder() );
		tablePanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		
		
		table.setAutoResizeMode( JTable.AUTO_RESIZE_LAST_COLUMN );
		table.setFillsViewportHeight( true );
		table.setFillsViewportWidth( true );
		table.setOpaque( false );
		table.addMyTableSelectionListener( new TableSelectionListener() );
		table.setProducts( products );
		
		table.model.setRowFilter( filter );
		
		
		String t = " ";
		tabbedPane.addTab( t + title + t , tablePanel );
		//tabbedPane.setBackgroundAt( 0 , Color.blue );
		
		tablePanelList.add( tablePanel );
		productTableList.add( table );
		
		
	}
	
	
	
	public void save(){
		
		
		File databaseDirectory = new File( DATABASE_DIRECTORY );
		if( !databaseDirectory.exists() )
		{
			databaseDirectory.mkdirs();
		}
		
		
		
		
		
		for( int i=0; i<tabbedPane.getTabCount(); i++ )
		{
			String title = tabbedPane.getTitleAt( i ).trim();
			
			File file = new File( DATABASE_DIRECTORY + title + ".txt" );
			ArrayList<Product> productList = productTableList.get( i ).getAllProducts();
			Product.sort( productList );
			
			try
			{
				Methods.writeTextFile( file , SaveLoad.getSaveStringList( productList ) );
				System.out.println( "Saved " + title + "    " + "products: " + productList.size() );
			}
			catch ( IOException e )
			{
				String message = "Failed to save " + title;
				JOptionPane.showMessageDialog( null , message );
				e.printStackTrace();
			}
			
			
		}
		
		JOptionPane.showMessageDialog( this , "Finished saving" );
		
		
	}
	public void add(){
		
		int i = tabbedPane.getSelectedIndex();
		ProductTable table = productTableList.get( i );
		
		
		Product newProduct = new Product();
		table.model.addRow( newProduct );
		table.setSelected( newProduct , true );
		
		int displayedR = table.model.getDisplayedIndexOf( newProduct );
		int displayedC = 0;
		if( displayedR != -1 )
		{
			table.changeSelection( displayedR , displayedC , false , false );
			table.editCellAt( displayedR , displayedC );
			table.transferFocus();
		}
		
//		downloadMissingImages();
//		downloadMissingUpc();
		
		
	}
	public void delete(){
		
		int i = tabbedPane.getSelectedIndex();
		ProductTable table = productTableList.get( i );
		
		Product selectedProduct = table.getSelectedProduct();
		if( selectedProduct != null )
		{
			table.removeProduct( selectedProduct );
		}
		
	}
	
	public void hideDstatBoxPushed(){
		
		
		filter.setHideDStat( hideDstatBox.isSelected() );
		
		for( int i=0; i<tabbedPane.getTabCount(); i++ )
		{
			ProductTable table = productTableList.get( i );
			table.model.applyFilter();
			
			String message = "# of visible users: " + table.model.getDisplayedRowCount() + "/" + table.model.getActualRowCount();
		}
		
	}
	public void searchTyped(){
		
		String searchText = searchField.getText();
		filter.setSearchText( searchText );
		
		for( int i=0; i<tabbedPane.getTabCount(); i++ )
		{
			ProductTable table = productTableList.get( i );
			table.model.applyFilter();
			
			String message = "# of visible users: " + table.model.getDisplayedRowCount() + "/" + table.model.getActualRowCount();
		}
		
	}
	
	
	//update SDR stuff
	public void updateSDR(){
		
		sdrWindow.applyBtn.setEnabled( false );
		sdrWindow.addressField.setText( "(.txt file here)" );
		sdrWindow.textArea.setText( "" );
		
		int i = tabbedPane.getSelectedIndex();
		sdrWindow.instructionsLabel.setText( "Load stock display report for " + "\"" + tabbedPane.getTitleAt( i ) + "\"" );
		
		sdrWindow.setVisible( true );
		
		
	}
	public void loadSdrFile( File file ){
		
		sdrWindow.textArea.setText( "" );
		
		ArrayList<String> sdrStrings;
		try
		{
			sdrStrings = Methods.readTextFileLineByLine( file );
		}
		catch ( FileNotFoundException e )
		{
			String message = "File not found: " + file.getPath() + "\n\n" + e.getMessage();
			sdrWindow.addTextLine( message );
			return;
		}
		catch ( IOException e )
		{
			String message = "Failed to open file: " + file.getPath() + "\n\n" + e.getMessage();
			sdrWindow.addTextLine( message );
			return;
		}
		
		ArrayList<Product> sdrProductList;
		try
		{
			sdrProductList = SaveLoad.getProductListFromSdrList( sdrStrings );
		}
		catch ( IllegalSaveStringException e )
		{
			String message = "Invalid data in SDR file: " + file.getPath() + "\n\n" + e.getMessage();
			sdrWindow.addTextLine( message );
			return;
		}
		
		
		
		this.sdrChanges = getSdrChanges( sdrProductList );
		if( sdrChanges.isEmpty() )
		{
			sdrWindow.addTextLine( "No changes found" );
		}
		else
		{
			sdrWindow.applyBtn.setEnabled( true );
		}
		
		
		
		
		
	}
	public void applySdr(){
		
		int tabIndex = tabbedPane.getSelectedIndex();
		ProductTable table = productTableList.get( tabIndex );
		
		//new
		for( int i=0; i<sdrChanges.newProducts.size(); i++ )
		{
			Product newProduct = sdrChanges.newProducts.get( i );
			
			table.addProduct( newProduct );
			
		}
		
		//price change
		for( int i=0; i<sdrChanges.priceChangeProducts.size(); i++ )
		{
			Product priceChangeProduct = sdrChanges.priceChangeProducts.get( i );
			
			int actualR = table.getActualRowIndex( priceChangeProduct.plu );
			Product tableProduct = table.getProduct( actualR );
			tableProduct.regPrice = priceChangeProduct.regPrice;
			
			table.setProduct( actualR , tableProduct );
			
		}
		
		//status change
		for( int i=0; i<sdrChanges.statusChangeProducts.size(); i++ )
		{
			Product statusChangeProduct = sdrChanges.statusChangeProducts.get( i );
			
			int actualR = table.getActualRowIndex( statusChangeProduct.plu );
			Product tableProduct = table.getProduct( actualR );
			tableProduct.status = statusChangeProduct.status;
			
			table.setProduct( actualR , tableProduct );
			
		}
		
		//D stat
		for( int i=0; i<sdrChanges.dStatChangeProducts.size(); i++ )
		{
			Product dStatChangeProduct = sdrChanges.dStatChangeProducts.get( i );
			
			int actualR = table.getActualRowIndex( dStatChangeProduct.plu );
			Product tableProduct = table.getProduct( actualR );
			tableProduct.status = "D";
			
			table.setProduct( actualR , tableProduct );
			
		}
		
		sdrWindow.dispose();
		
		
	}
	
	//used by updateSDR()
	private SdrChanges getSdrChanges( ArrayList<Product> sdrProductList ){
		
		
		SdrChanges sdrChanges = new SdrChanges();
		
		int tabIndex = tabbedPane.getSelectedIndex();
		ProductTable table = productTableList.get( tabIndex );
		
		
		for( int i=0; i<sdrProductList.size(); i++ )
		{
			Product sdrProduct = sdrProductList.get( i );
			
			int actualR = table.getActualRowIndex( sdrProduct.plu );
			sdrWindow.addText( sdrProduct.plu + ": " );
			
			if( actualR == -1 )//product not found, need to add
			{
				sdrWindow.addTextLine( "new PLU. Make sure it's not a typo" );
				//table.addProduct( sdrProduct );
				sdrChanges.newProducts.add( sdrProduct );
			}
			else
			{
				
				Product tableProduct = table.getProduct( actualR );
				
				boolean priceChange = !tableProduct.regPrice.equals( sdrProduct.regPrice );
				boolean statusChange = !tableProduct.status.equals( sdrProduct.status );
				
				
				if( priceChange )
				{
					sdrWindow.addText( "updating price " + tableProduct.regPrice + " => " + sdrProduct.regPrice + "   " );
					sdrChanges.priceChangeProducts.add( sdrProduct );
					
				}
				if( statusChange )
				{
					sdrWindow.addText( "updating order status " + tableProduct.status + " => " + sdrProduct.status );
					sdrChanges.statusChangeProducts.add( sdrProduct );
					
				}
				if( !priceChange && !statusChange )
				{
					sdrWindow.addText( "no change" );
				}
				sdrWindow.addTextLine( "" );
				
				
				
			}
			
			
		}
		
		
		
		//check for discontinued stuff no longer on Stock Display Report
		
		for( int actualR=0; actualR < table.getActualRowCount(); actualR++ )
		{
			Product product = (Product) table.model.getActualRowData( actualR );
			boolean foundOnSdr = false;
			
			for( int i=0; i<sdrProductList.size(); i++ )
			{
				Product sdrProduct = sdrProductList.get( i );
				if( product.plu.equals( sdrProduct.plu ) )
				{
					foundOnSdr = true;
				}
			}
			
			if( !foundOnSdr )
			{
				sdrWindow.addTextLine( product.plu + ": not found on the SDR. Changing to \"D\" status" );
				
				//product.status = "D";
				//table.model.updateDisplayedRows( true );
				
				sdrChanges.dStatChangeProducts.add( product );
				
			}
			
		}
		
		
		return sdrChanges;
		
		
		
	}
	
	
	//missing images
	public void missingImages(){
		
		ArrayList<Product> missingImageList = getMissingImageList();
		
		
		String message = "Products with missing images:" + "\n\n";
		message += ArrayToString.getString( missingImageList , "\n" );
		
		
		TextAreaDialog.showDialog( "PLUs missing from schematics" , this , message );
		
		
	}
	public ArrayList<Product> getMissingImageList(){
		
		ArrayList<Product> missingImageList = new ArrayList<Product>();
		
		
		for( int i=0; i<tabbedPane.getTabCount(); i++ )
		{
			ArrayList<Product> productList = productTableList.get( i ).getAllProducts();
			
			for( int j=0; j<productList.size(); j++ )
			{
				Product product = productList.get( j );
				
				if( !hasImage( product.plu ) && !product.status.toLowerCase().equals( "D" ) )
				{
					missingImageList.add( product );
				}
				
				
				
			}
			
			
		}
		
		return missingImageList;
		
		
		
	}
	public boolean hasImage( String plu ){
		
		String path = "images/" + plu + ".jpg";
		File file = new File( path );
		
		return file.exists();
		
	}
	
	
	
	
	
	
	public void analyzeSchematics(){
		
		
		ArrayList<Schematic> schematics = null;
		try
		{
			schematics = SaveLoad.loadSchematics();
		}
		catch ( FileNotFoundException e )
		{
			String message = "Schematics files not found";
			message += "\n" + "schematics directory" + SaveLoad.SCHEMATICS_DIRECTORY;
			message += "\n" + e.getMessage();
			JOptionPane.showMessageDialog( null , message );
			
			return;
		}
		catch ( IOException e )
		{
			String message = "Failed to open schematics file";
			message += "\n" + e.getMessage();
			JOptionPane.showMessageDialog( null , message );
			return;
		}
		
		
		
		
		String message = "";
		
		for( int i=0; i<productTableList.size(); i++ )
		{
			String title = tabbedPane.getTitleAt( i ).trim();
			ArrayList<Product> productsMissingFromSchematics = getProductsMissingFromSchematics( i , schematics );
			
			if( !productsMissingFromSchematics.isEmpty() )
			{
				message += "Products missing from schematic: " + title + "\n";
				message += ArrayToString.getString( productsMissingFromSchematics , "\n" );
				message += "\n\n";
			}
			
				
			
		}
		
		TextAreaDialog dialog = TextAreaDialog.showDialog( "PLUs missing from schematics" , null , message );
		
		
	}
	public ArrayList<Product> getProductsMissingFromSchematics( int tabIndex ,  ArrayList<Schematic> schematics  ){
		
		ArrayList<Product> missingProducts = new ArrayList<Product>();
		
		ProductTable table = productTableList.get( tabIndex );
		ArrayList<Product> tableProducts = table.getAllProducts();
		for( int j=0; j<tableProducts.size(); j++ )
		{
			Product tableProduct = tableProducts.get( j );
			boolean found = false;
			boolean isDstat = tableProduct.status.toLowerCase().equals( "d" );
			
			for( int k=0; k<schematics.size(); k++ )
			{
				Schematic schematic = schematics.get( k );
				if( schematic.containsPlu( tableProduct.plu ) )
				{
					found = true;
				}
			}
			
			if( !found && !isDstat )
			{
				missingProducts.add( tableProduct );
			}
			
		}
		
		return missingProducts;
		
		
	}
	
	
	//unfinished
	public ArrayList<Product> getDstatProductsFromSchematics( ArrayList<Schematic> schematics ){
		
		ArrayList<Product> dstatProducts = new ArrayList<Product>();
		
		for( int k=0; k<schematics.size(); k++ )
		{
			Schematic schematic = schematics.get( k );
			Database db = null;//fuuuckk
			ArrayList<Product> products = schematic.getAllDstatProducts( db );
			dstatProducts.addAll( products );
		}
		
		return dstatProducts;
		
	}
	
	
	
	public void downloadMissingImages(){
		
		
		for( int i=0; i<tabbedPane.getTabCount(); i++ )
		{
			ArrayList<Product> productList = productTableList.get( i ).getAllProducts();
			for( int j=0; j<productList.size(); j++ )
			{
				Product product = productList.get( j );
				downloadMissingImage( product );//checks if missing, and if so, asks user to pick one, then downloads it
			}
			
			
		}
		
		
		
	}
	public void downloadMissingImage( Product product ){
		
		File imageFile = new File( "images/" + product.plu + ".jpg" );
		if( !imageFile.exists() )
		{
			String title = product.plu + " " + product.title;
			
			if( product.title.contains( "(" ) )
			{
				title = product.plu + " " + Methods.getSubstring( product.title , "(" , ")" );
			}
			
			
			
			FrysWebsiteChecker.pickImage( product.plu , title );
		}
		
	}
	
	public void downloadMissingUpc(){
		
		
		for( int i=0; i<tabbedPane.getTabCount(); i++ )
		{
			ProductTable table = productTableList.get( i );
			ArrayList<Product> productList = table.getAllProducts();
			for( int j=0; j<productList.size(); j++ )
			{
				Product product = productList.get( j );
				//downloadMissingUpc( product , table );
				
				Thread thread = new Thread(){
					
					public void run()
					{
						downloadMissingUpc( product , table );
					}
					
				};
				thread.start();
				
				
			}
			
			
		}
		
		System.out.println( "Done downloading UPCs" );
		
	}
	public void downloadMissingUpc( Product product , ProductTable table ){
		
		
		
		
		
		if( !product.upc.isEmpty() )
		{
			return;
		}
		
		ArrayList<String> htmlArray = null;
		try
		{
			htmlArray = FrysWebsiteChecker.getHtmlArray( product.plu );
		}
		catch ( IllegalArgumentException e )
		{
			System.out.println( "PLU not found on website" );
			return;
		}
		catch ( IOException e )
		{
			System.out.println( "Failed to access website" );
			e.printStackTrace();
			return;
		}
		
		String upc = FrysWebsiteChecker.getUpc( htmlArray );
		product.upc = upc;
		table.model.fireRowDataUpdated( product );
		
		
		System.out.println( "PLU: " + product.plu + "   " + "UPC: " + product.upc );
		
		
	}
	
	
	
	public void exitPushed(){
		
		int i = tabbedPane.getSelectedIndex();
		ProductTable table = productTableList.get( i );
		table.printColumnWidths();
		
		System.out.println( "frame width: " + this.getWidth() );
		
		
		int response = ButtonDialog.showDialog( this , "Save?" , "Yes" , "No" , "Cancel" );
		if( response == 0 )//OK
		{
			save();
			System.exit( 0 );
		}
		else if( response == 1 )//No
		{
			System.exit( 0 );
		}
		else if( response == 2 )//Cancel
		{
			
		}
		else if( response == -1 )//x button
		{
			
		}
		
	}
	
	
	
	
	private class SdrChanges {
		
		
		ArrayList<Product> newProducts = new ArrayList<Product>();
		ArrayList<Product> priceChangeProducts = new ArrayList<Product>();
		ArrayList<Product> statusChangeProducts = new ArrayList<Product>();
		ArrayList<Product> dStatChangeProducts = new ArrayList<Product>();
		
		
		public boolean isEmpty(){
			
			return newProducts.isEmpty() && priceChangeProducts.isEmpty() && statusChangeProducts.isEmpty() && dStatChangeProducts.isEmpty();
			
		}
		
		
	}
	
	
	
	private class BtnListener implements ActionListener {

		
		public void actionPerformed( ActionEvent e ){
			
			if     ( e.getSource() == saveBtn )				save();
			else if( e.getSource() == addBtn )				add();
			else if( e.getSource() == deleteBtn )			delete();
			else if( e.getSource() == updateBtn )			updateSDR();
			else if( e.getSource() == schematicBtn )		analyzeSchematics();
			else if( e.getSource() == missingImagesBtn )	missingImages();
			else if( e.getSource() == hideDstatBox )		hideDstatBoxPushed();
			
		}
		
		
		
	}
	private class MyWindowListener implements WindowListener{

		
		public void windowActivated( WindowEvent arg0 ){}
		public void windowClosed( WindowEvent arg0 ){}
		public void windowClosing( WindowEvent arg0 ){
			
			exitPushed();
			
		}
		public void windowDeactivated( WindowEvent arg0 ){}
		public void windowDeiconified( WindowEvent arg0 ){}
		public void windowIconified( WindowEvent arg0 ){}
		public void windowOpened( WindowEvent arg0 ){}
		
		
		
	}
	private class TableSelectionListener extends MyTableSelectionListener {
		
		
		public void selectionChanged(){
			
			int selectedDisplayedR = this.getTable().getSelectedRow();
			
			deleteBtn.setEnabled( selectedDisplayedR != -1 );
			
		}
	}
	private class MyKeyListener implements KeyListener {

		
		public void keyPressed( KeyEvent e ){}
		public void keyReleased( KeyEvent e ){
			
			if( e.getSource() == searchField )
			{
				searchTyped();
			}
			
			
		}
		public void keyTyped( KeyEvent e ){}
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		DatabaseEditor editor = new DatabaseEditor();
		
	}
	
	
	
	
	
}


















