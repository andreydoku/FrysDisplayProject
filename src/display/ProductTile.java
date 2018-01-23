package display;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import basic.Product;
import gui.ImagePanel;
import myLibrary.Methods;



public class ProductTile extends JPanel {
	
	
	
	
	
	
	//final static Color YELLOW = new Color( 207,207,112 );
	final static Color YELLOW = new Color( 255,255,64 );
	
	//final static Color GREEN = new Color( 123,207,112 );
	final static Color GREEN = new Color( 20,159,0 );
	
	final static Color BLUE = new Color( 0,126,253 );
	
	final static Color CLEAR = new Color( 255,255,255 , 100 );
	
	
	static int IMAGE_SIZE = 156;
	
	//static Dimension PREFERRED_DIMENSIONS = new Dimension( 560,156 );
	static Dimension PREFERRED_DIMENSIONS = new Dimension( 522,156 );
	
	
	static String FONT_NAME = "Calibri";
	
	//static int PRICE_FONT_SIZE = 52;
	static int PRICE_FONT_SIZE = 70;
	static int GOODTHRU_FONT_SIZE = 18;
	
	ImagePanel imagePanel = new ImagePanel();
	
	static int B_OUT = 8;
	static int B_IN = 6;
	
	JPanel centerPanel = new JPanel();
		JLabel line1 = new JLabel();
		JLabel line2 = new JLabel();
		JLabel line3 = new JLabel();
	
		JLabel pluLabel = new JLabel();
	
	
	JPanel rightPanel = new JPanel();
		JLabel fillerLabel1 = new JLabel();
		JLabel fillerLabel2 = new JLabel();
		JLabel priceLabel = new JLabel();
		JLabel goodThruLabel = new JLabel();
		JLabel dateLabel = new JLabel();
	
	Product product;
	
	
	boolean debug = false;
	
	
	public ProductTile(){
		
		build();
		setProduct( null );
		
	}
	public ProductTile( Product product ){
		
		build();
		setProduct( product );
		
	}
	
	public void build(){
		
		
		buildCenterPanel();
		buildRightPanel();
		
		
		
		this.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		
		
		
		c.weighty = 1;
		c.insets = new Insets( 0 , 0 , 0 , 0 );		c.weightx = 0;c.gridx = 0; c.gridy = 0; add( imagePanel , c );
		c.insets = new Insets( 0 , 0 , 0 , 0 );		c.weightx = 1;c.gridx = 1; c.gridy = 0; add( centerPanel , c );
		c.insets = new Insets( 0 , 0 , 0 , 0 );		c.weightx = 1;c.gridx = 2; c.gridy = 0; add( rightPanel , c );
		
		
		imagePanel.setOpaque( false );
		centerPanel.setOpaque( false );
		rightPanel.setOpaque( false );
		
		
		if( debug ) imagePanel.setBorder( BorderFactory.createLineBorder( Color.blue ) );
		if( debug ) centerPanel.setBorder( BorderFactory.createLineBorder( Color.blue ) );
		if( debug ) rightPanel.setBorder( BorderFactory.createLineBorder( Color.blue ) );
		
		setBackground( YELLOW );
		
		
		//imagePanel.setPreferredSize( new Dimension( IMAGE_SIZE , IMAGE_SIZE ) );
		this.setPreferredSize( PREFERRED_DIMENSIONS );
		
	}
	public void buildCenterPanel(){
		
		centerPanel.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		
		
		
		
		c.weightx = 1;
		c.insets = new Insets( B_OUT , B_OUT , 0     , 0 );		c.weighty = 1;c.gridx = 0; c.gridy = 0; centerPanel.add( line1 , c );
		c.insets = new Insets( 0     , B_OUT , 0     , 0 );		c.weighty = 1;c.gridx = 0; c.gridy = 1; centerPanel.add( line2 , c );
		c.insets = new Insets( 0     , B_OUT , 0     , 0 );		c.weighty = 1;c.gridx = 0; c.gridy = 2; centerPanel.add( line3 , c );
		c.insets = new Insets( 0     , B_OUT , B_OUT , 0 );		c.weighty = 1f;c.gridx = 0; c.gridy = 3; centerPanel.add( pluLabel , c );
		
		if( debug ) line1.setBorder( BorderFactory.createLineBorder( Color.red ) );
		if( debug ) line2.setBorder( BorderFactory.createLineBorder( Color.red ) );
		if( debug ) line3.setBorder( BorderFactory.createLineBorder( Color.red ) );
		if( debug ) pluLabel.setBorder( BorderFactory.createLineBorder( Color.red ) );
		
		
		
		String fontName = "Arial";
		fontName = "Calibri";
		
		line1.setFont( 		new Font( fontName , Font.BOLD , 22 ) );
		line2.setFont( 		new Font( fontName , Font.PLAIN , 22 ) );
		line3.setFont( 		new Font( fontName , Font.PLAIN , 16 ) );
		
		pluLabel.setFont( 	new Font( fontName , Font.PLAIN , 18 ) );
		
	}
	public void buildRightPanel(){
		
		rightPanel.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.CENTER;
		
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 1;
		c.insets = new Insets( 0 , 0 , 0 , 0 );c.gridx = 0; c.gridy = 1;rightPanel.add( priceLabel , c );
		
		c.weightx = 0;
		c.weighty = 0.5;
		c.insets = new Insets( 0 , 0 , 0 , 0 );c.gridx = 0; c.gridy = 0;rightPanel.add( fillerLabel1 , c );
		//c.insets = new Insets( 0 , 0 , 0 , 0 );c.gridx = 0; c.gridy = 1;rightPanel.add( fillerLabel2 , c );
		c.insets = new Insets( 0 , 0 , 0 , 0 );c.gridx = 0; c.gridy = 3;rightPanel.add( goodThruLabel , c );
		c.insets = new Insets( 0 , 0 , 0 , 0 );c.gridx = 0; c.gridy = 4;rightPanel.add( dateLabel , c );
		
		priceLabel.setFont( new Font( FONT_NAME , Font.BOLD  , PRICE_FONT_SIZE ) );
		if( debug ) priceLabel.setBorder( BorderFactory.createLineBorder( Color.red ) );
		priceLabel.setVerticalAlignment( SwingConstants.TOP );
		priceLabel.setHorizontalAlignment( SwingConstants.CENTER );
		
		goodThruLabel.setHorizontalAlignment( SwingConstants.CENTER );
		dateLabel.setHorizontalAlignment( SwingConstants.CENTER );
		
		fillerLabel1.setFont ( new Font( FONT_NAME , Font.PLAIN  , GOODTHRU_FONT_SIZE ) );
		//fillerLabel2.setFont ( new Font( FONT_NAME , Font.PLAIN  , GOODTHRU_FONT_SIZE ) );
		goodThruLabel.setFont( new Font( FONT_NAME , Font.PLAIN  , GOODTHRU_FONT_SIZE ) );
		dateLabel.setFont    ( new Font( FONT_NAME , Font.PLAIN  , GOODTHRU_FONT_SIZE ) );
		
		
	}
	
	
	public void setProduct( Product product ){
		
		this.product = product;
		
		if( product == null )
		{
			line1.setText( "" );
			line2.setText( "" );
			line3.setText( "" );
			pluLabel.setText( "" );
			
			
			fillerLabel1.setText( "" );
			fillerLabel2.setText( "" );
			priceLabel.setText( "" );
			goodThruLabel.setText( "" );
			dateLabel.setText( "" );
			
			imagePanel.setBackground( CLEAR );
			this.setBackground( CLEAR );
			
			
		}
		else
		{
			
			String priceString = product.regPrice;
			if( !product.adPrice.isEmpty() )
			{
				priceString = product.adPrice;
			}
			
			line1.setText( product.title.trim() ); 		if( product.title.isEmpty() ) line1.setText( " " );
			line2.setText( product.line1.trim() );		if( product.line1.isEmpty() ) line2.setText( " " );
			line3.setText( product.line2.trim() );		if( product.line2.isEmpty() ) line3.setText( " " );
			
			pluLabel.setText( ("#" + product.plu.trim() ).trim() );
			
			//priceLabel.setText( product.price );
			ArrayList<String> pieces = Methods.split( priceString , "." , false , true , false );
			String dollars = pieces.get( 0 );
			dollars = dollars.replace( "$" , "" );
			dollars = dollars.trim();
			
			String cents = pieces.get( 1 );
			cents = cents.trim();
			//String priceText = "<html>" + dollars + "<sup>" + "<font size=\"8\">" + cents + "</font>" + "</sup>" + "</html>";
			
			String priceText = "";
			priceText += "<html>";
			
				priceText += "<sup>";
					priceText += "<font size=\"8\">";
						priceText += "$";
					priceText += "</font>";
				priceText += "</sup>";
				
				priceText += dollars;
					
				priceText += "<sup>";
					priceText += "<font size=\"8\">";
						priceText += cents;
					priceText += "</font>";
				priceText += "</sup>";
					
			priceText += "</html>";
			
			priceLabel.setText( priceText );
			
			
			fillerLabel1.setText( " " );
			fillerLabel2.setText( " " );
			if( !product.adDate.isEmpty() )
			{
				goodThruLabel.setText( "Price good thru" );
				dateLabel.setText( product.adDate );
			}
			else
			{
				goodThruLabel.setText( " " );
				dateLabel.setText( " " );
			}
			
			
			//<html>ax<sup>2</sup>+bx+c</html>
			
			
			String path = "images/" + product.plu + ".jpg";
			
			try
			{
				File file = new File( path );
				BufferedImage image = Methods.loadImage( file );
				
				image = Methods.scaleImage( image , IMAGE_SIZE , IMAGE_SIZE );
				
				imagePanel.setBackground( Color.white );
				imagePanel.setImage( image );
				
			}
			catch ( IOException e )
			{
				System.out.println( "failed to load image: " + path );
				
				imagePanel.setBackground( Color.white );
				imagePanel.setImage( createImageNotFound( IMAGE_SIZE , IMAGE_SIZE , Color.WHITE , Color.BLACK ) );
				
			}
			
			
			
			
			if( product.status.toLowerCase().equals( "r" ) || product.status.toLowerCase().equals( "n" ) || product.status.toLowerCase().equals( "s" ) )
			{
				this.setBackground( YELLOW );
			}
			else if( product.status.toLowerCase().equals( "w" ) || product.status.toLowerCase().equals( "d" ) )
			{
				this.setBackground( GREEN );
			}
			
			
			if( product.isPromo )
			{
				this.setBackground( BLUE );
			}
			
			
		}
		
	}
	
	public void setFont1( int size ){
		
		Font oldFont = line1.getFont();
		Font newFont = new Font( oldFont.getFontName() , oldFont.getStyle() , size );
		
		line1.setFont( newFont );
		line2.setFont( newFont );
		line3.setFont( newFont );
		
		
	}
	public void setFont2( int size ){
		
		Font oldFont = priceLabel.getFont();
		Font newFont = new Font( oldFont.getFontName() , oldFont.getStyle() , size );
		
		priceLabel.setFont( newFont );
		
		
		
		
	}
	public void setFont3( int size ){
	
	Font oldFont = pluLabel.getFont();
	Font newFont = new Font( oldFont.getFontName() , oldFont.getStyle() , size );
	
	pluLabel.setFont( newFont );
	
	
	
	
}
	
	
	public void paintComponent( Graphics gfx ){
		
		super.paintComponent( gfx );
		Graphics2D g = (Graphics2D) gfx;
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON );
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		g.setRenderingHint( RenderingHints.KEY_COLOR_RENDERING , RenderingHints.VALUE_COLOR_RENDER_QUALITY );
		
		g.setFont( new Font( "Tahoma" , Font.BOLD , 40 ) );
		
		
		if( this.product == null )
		{
			return;
		}
		
		if( this.product.status.toLowerCase().equals( "d" ) )
		{
			String text = "DISCONTINUED";
			int w = Methods.getStringWidth( g , text );
			int h = Methods.getStringHeight( g );
			
			int x = ( PREFERRED_DIMENSIONS.width - IMAGE_SIZE - w ) / 2    +    IMAGE_SIZE ;
			int y = ( PREFERRED_DIMENSIONS.height - h ) / 2  +  h;
			
			g.setColor( new Color( 0,0,0 , 100 ) );
			g.drawString( text , x , y );
		}
		
		if( this.product.status.toLowerCase().equals( "n" ) )
		{
			String text = "COMING SOON";
			int w = Methods.getStringWidth( g , text );
			int h = Methods.getStringHeight( g );
			
			int x = ( PREFERRED_DIMENSIONS.width - IMAGE_SIZE - w ) / 2    +    IMAGE_SIZE ;
			int y = ( PREFERRED_DIMENSIONS.height - h ) / 2  +  h;
			
			g.setColor( new Color( 100,0,0 , 100 ) );
			g.drawString( text , x , y );
		}
		
			
		
	}
	
	
	public static BufferedImage createImageNotFound( int width , int height , Color backColor , Color textColor ){
		
		
		BufferedImage image = new BufferedImage( width , height , BufferedImage.TYPE_INT_RGB );
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		g.setColor( backColor );
		g.fillRect( 0 , 0 , width , height );
		
		g.setFont( new Font( "Tahoma" , Font.PLAIN , 20 ) );
		g.setColor( textColor );
		
		String text;
		int x,y,w,h;
		int b = 20;
		
		text = "Image";
		w = Methods.getStringWidth( g , text );
		h = Methods.getStringHeight( g );
		x = ( width - w ) / 2;
		y = ( height - 4*b - 3*h ) / 2   +   b+h;
		g.drawString( text , x , y );
		
		text = "Coming";
		w = Methods.getStringWidth( g , text );
		h = Methods.getStringHeight( g );
		x = ( width - w ) / 2;
		y = ( height - 4*b - 3*h ) / 2   +   b+h+b+h;
		g.drawString( text , x , y );
		
		text = "Soon";
		w = Methods.getStringWidth( g , text );
		h = Methods.getStringHeight( g );
		x = ( width - w ) / 2;
		y = ( height - 4*b - 3*h ) / 2   +   b+h+b+h+b+h;
		g.drawString( text , x , y );
		
//		g.drawString( "Image" , 10 , 20 );
//		g.drawString( "Coming" , 10 , 40 );
//		g.drawString( "Soon" , 10 , 60 );
		
		return image;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}







