package display;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import basic.Product;
import database.Database;
import gui.ImagePanel2;
import myLibrary.ArrayToString;
import myLibrary.Methods;




public class DisplayFrame extends JFrame {
	
	
	final static String version = "v2.0";
	
	
	
	final static String SCHEMATIC_FILE_PATH = "schematic/schematic.txt";
	final static String DELIMETER = "---------------------------------------------------------" + "\n";
	
	final static String AD_FILE_PATH = "ad.txt";
	
	
	static final Color BACK_COLOR = new Color( 100,50,50 );
	//static final Color BACK_COLOR = new Color( 130,65,65 );
	
	
	//static int SECTION_TITLE_FONT_SIZE = 40;
	static int SECTION_TITLE_FONT_SIZE = 60;
	
	
	static final int BT = 10*2;
	
	final static int COLS = 7;
	
	
	
	JScrollPane sp;
	static int SCROLL_INTERVAL = 30;
	Timer scrollTimer;
	ScrollListener scrollListener;
	
	
	ImagePanel2 productwallPanel = new ImagePanel2();
	
	ArrayList<ProductPanel> panels = new ArrayList<ProductPanel>();
	JPanel fillerPanel = new JPanel();
	
	JMenuItem reloadMenuItem;
	
	
	
	MenuListener menuListener = new MenuListener();
	
	
	Database database;
	
	
	public DisplayFrame( Database database , String schematic ){
		
		
		this.database = database;
		
		buildProductwallPanel();
		
		
		buildFrame();
		buildSchematic( schematic );
		
		
		
	}
	
	public void buildFrame(){
		
		
		
		sp = new JScrollPane( productwallPanel );
		sp.getVerticalScrollBar().setUnitIncrement( 40 );
		
		scrollListener = new ScrollListener( sp.getViewport() );
		scrollTimer = new Timer( SCROLL_INTERVAL , scrollListener );
		scrollTimer.start();
		
		
		
		
		
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menu = new JMenu( "Menu" );
		menubar.add( menu );
		
		reloadMenuItem = new JMenuItem( "Reload" );
		reloadMenuItem.addActionListener( menuListener );
		menu.add( reloadMenuItem );
		
		this.setJMenuBar( menubar );
		
		
		
		
		
		this.setLayout( new BorderLayout() );
		this.add( sp , BorderLayout.CENTER );
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setSize( 3840 , 1080 );
		this.setTitle( "Display" + " " + version );
		
		//this.setLocation( 0 , 0 );
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gd = ge.getScreenDevices();
	    Rectangle bounds = gd[0].getDefaultConfiguration().getBounds();
	    this.setLocation( bounds.x, bounds.y );
		
	    this.setVisible( true );
		
	}
	public void buildProductwallPanel(){
		
		productwallPanel.setLayout( new GridBagLayout() );
		
		//this.setColors( BACK_COLOR.darker().darker() , BACK_COLOR );
		//this.setBackground( BACK_COLOR );
		
		File file = new File( "red wallpaper.jpg" );
		BufferedImage image;
		try
		{
			image = Methods.loadImage( file );
			productwallPanel.setImage( image );
		}
		catch ( IOException e )
		{
			productwallPanel.setBackground( BACK_COLOR );
		}
		
		productwallPanel.addMouseListener( new MyMouseListener() );
		productwallPanel.addMouseMotionListener( new MyMouseListener() );
		
		
		
	}
	
	public void buildSchematic( String schematic ){
		
		boolean debug = false;
		
		
		
		ArrayList<String> panelStrings = Methods.split( schematic , DELIMETER , false , true , false );
		
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
			
			addPanel( panelTitle , lines.size() , COLS );
			
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
					Product product = database.getProduct( plu );
					
					this.setProduct( product , panelTitle , r , c );
					
				}
				
			}
				
			
			
			
		}
		
		
		
	}
	
	
	
	public void reload(){
		
		clearProductwallPanel();
		buildProductwallPanel();
		
		
		this.remove( sp );
		sp = new JScrollPane( productwallPanel );
		sp.getVerticalScrollBar().setUnitIncrement( 40 );
		
		scrollListener = new ScrollListener( sp.getViewport() );
		scrollTimer = new Timer( SCROLL_INTERVAL , scrollListener );
		scrollTimer.start();
		
		this.setLayout( new BorderLayout() );
		this.add( sp , BorderLayout.CENTER );
		
		
		
		productwallPanel.repaint();
		productwallPanel.revalidate();
		
		this.repaint();
		this.revalidate();
		
	}
	public void clearProductwallPanel(){
		
//		productwallPanel.remove( fillerPanel );
//		for( int i=0; i<panels.size(); i++ )
//		{
//			productwallPanel.remove( panels.get( i ) );
//		}
		
		productwallPanel = new ImagePanel2();
		
		
		panels.clear();
		
	}
	
	
	
	
	public void addPanel( String title , int R , int C ){
		
		ProductPanel rp = new ProductPanel( title , R , C );
		panels.add( rp );
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weighty = 0;
		c.weightx = 1;
		c.gridx = 0; c.gridy = panels.size(); productwallPanel.add( rp , c );
		
		c.gridy += 1;
		c.weighty = 1;
		productwallPanel.remove( fillerPanel );
		productwallPanel.add( fillerPanel , c );
		
		
		//this.add( rp );
		
	}
	public void setProduct( Product product , String panelTitle , int r , int c ){
		
		ProductPanel panel = getPanel( panelTitle );
		if( panel == null )
		{
			String message = "ProductWall3.addProduct( product , panelTitle ): invalid panelTitle: " + panelTitle;
			throw new IllegalArgumentException( message );
		}
		
		panel.setProduct( product , r , c );
		
	}
	private ProductPanel getPanel( String title ){
		
		for( int i=0; i<panels.size(); i++ )
		{
			ProductPanel panel = panels.get( i );
			if( panel.getTitle().equals( title ) )
			{
				return panel;
			}
		}
		
		return null;
		
	}
	
	
	
	public void movedMouse( Point mousePoint ){
		
		scrollListener.pause( 10000 );
		
	}
	
	
	
	private class ProductPanel extends JPanel{
		
		
		int rows, cols;
		
		
		JLabel titleLabel = new JLabel();
		JPanel centerPanel = new JPanel();
		
		JPanel[][] tilePanels;
		ProductTile[][] tiles;
		
		
		public ProductPanel( String title , int rows , int cols ){
			
			setTitle( title );
			build( rows , cols );
			
			
		}
		
		
		public void build( int rows , int cols ){
			
			this.rows = rows;
			this.cols = cols;
			
			
			//this.setBorder( BorderFactory.createMatteBorder( 10 , 0 , 0 , 0 , BACK_COLOR ) );
			this.setBorder( BorderFactory.createEmptyBorder( 2 , 0 , 0 , 0 ) );
			this.setOpaque( false );
			
			
			
			tilePanels = new JPanel[rows][cols];
			tiles = new ProductTile[rows][cols];
			
			this.setLayout( new BorderLayout() );
			this.add( titleLabel , BorderLayout.NORTH );
			this.add( centerPanel , BorderLayout.CENTER );
			
			
			titleLabel.setFont( new Font( "Arial" , Font.BOLD  , SECTION_TITLE_FONT_SIZE ) );
			titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
			
			titleLabel.setOpaque( false );
			//titleLabel.setBackground( BACK_COLOR );
			titleLabel.setForeground( Color.WHITE );
			
			
			
			//centerPanel.setBackground( BACK_COLOR );
			//centerPanel.setBackground( new Color( 0,0,255 ) );
			centerPanel.setOpaque( false );
			
			
			//centerPanel.setLayout( new FlowLayout( FlowLayout.LEFT , 10,10 ) );
			centerPanel.setLayout( new GridBagLayout() );
			
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			
			c.anchor = GridBagConstraints.LINE_START;
			c.weighty = 0;
			c.weightx = 0;
			
			
			for( int R=0; R<rows; R++ )
			{
				for( int C=0; C<cols; C++ )
				{
					c.gridx = C;
					c.gridy = R;
					
					tiles[R][C] = new ProductTile();
					//tiles[R][C].setBorder( getBorder( R , C ) );
					
					
					//centerPanel.add( tiles[R][C] , c );
					
					
					tilePanels[R][C] = new JPanel();
					tilePanels[R][C].setOpaque( false );
					tilePanels[R][C].setBorder( getBorder( R , C ) );
					tilePanels[R][C].setLayout( new BorderLayout() );
					tilePanels[R][C].add( tiles[R][C] , BorderLayout.CENTER );
					
					tilePanels[R][C].setBackground( BACK_COLOR );
					
					
					centerPanel.add( tilePanels[R][C] , c );
					
				}
			}
			
			
			
			
		}
		private Border getBorder( int r , int c ){
			
			
			
			int top = 0;
			int left = BT;
			int bottom = BT;
			int right = 0;
			
			if( r == 0 )//last row
			{
				top = BT;
			}
			if( c == cols-1 )//last column
			{
				right = BT;
			}
			 
			
			//return BorderFactory.createMatteBorder( top , left , bottom , right , BACK_COLOR );
			return BorderFactory.createEmptyBorder( top , left , bottom , right );
			
			
		}
		
		
		
		
		public void setTitle( String title ){
			
			this.titleLabel.setText( title );
			
		}
		public String getTitle(){
			
			return titleLabel.getText();
			
		}
		
		
		public void setProduct( Product product , int r , int c ){
			
			tiles[r][c].setProduct( product );
			
		}
		
	
	}
	
	
	
	private class ScrollListener implements ActionListener {

		private JViewport viewport;
		
		private boolean down = true;
		
		private boolean paused = false;
		private int pauseDurationInMillis = 0;
		private int currentTimePausedInMillis = 0;
		
		
		
		private int scrollAmount = 1;
		
		
		public ScrollListener( JViewport viewport ){
			
			this.viewport = viewport;
			
		}
		
		public void pause( int pauseDurationInMillis ){
			
			paused = true;
			this.pauseDurationInMillis = pauseDurationInMillis;
			currentTimePausedInMillis = 0;
			
			
		}
		public void unpause(){
			
			paused = false;
			
		}
		
		public void scroll(){
			
			int x = viewport.getViewPosition().x;
			int y = viewport.getViewPosition().y;
			if( y >= productwallPanel.getHeight() - viewport.getHeight() )
			{
				//System.out.println( "reached bottom" );
				down = false;
			}
			if( y == 0 )
			{
				//System.out.println( "reached top" );
				down = true;
			}
			
			if( down )
			{
				y += scrollAmount;
			}
			else
			{
				y -= scrollAmount;
			}
			viewport.setViewPosition( new Point(x,y) );
			
		}
		
		public void actionPerformed( ActionEvent e ){
			
			
			if( paused )
			{
				currentTimePausedInMillis += SCROLL_INTERVAL;
				if( currentTimePausedInMillis >= pauseDurationInMillis )
				{
					unpause();
				}
			}
			else
			{
				scroll();
			}
			
			
		}
		
		
		
	}
	private class MenuListener implements ActionListener{

		
		public void actionPerformed( ActionEvent e ){
			
			if( e.getSource() == reloadMenuItem )
			{
				reload();
			}
			
		}
		
		
		
		
	}
	private class MyMouseListener implements MouseListener, MouseMotionListener {

		
		public void mouseClicked( MouseEvent me ){}
		public void mouseEntered( MouseEvent me ){}
		public void mouseExited( MouseEvent me ){}
		public void mousePressed( MouseEvent me ){}
		public void mouseReleased( MouseEvent me ){}

		
		public void mouseDragged( MouseEvent me ){}
		public void mouseMoved( MouseEvent me ){
			
			Point mouse = me.getPoint();
			movedMouse( mouse );
			
		}
		
		
		
		
	}
	
	
	
	
	
}























