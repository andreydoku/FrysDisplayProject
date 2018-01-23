package display;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import basic.Product;
import gui.ImagePanel;
import myLibrary.Methods;
import saveLoad.IllegalSaveStringException;
import saveLoad.SaveLoad;

public class ProductTile2 extends JPanel {
	
	
	static boolean debug = false;
	static Color lineColor = new Color( 0,0,0 , 50 );
	
	
	static Color YELLOW = new Color( 255,255,64 );
	static Color GREEN = new Color( 20,159,0 );
	static Color BLUE = new Color( 0,126,253 );
	static Color CLEAR = new Color( 255,255,255 , 100 );
	
	
	
	static Dimension PREFERRED_DIMENSION_4K = new Dimension( 522 , 156 );
	static Dimension PREFERRED_DIMENSION_1080p = new Dimension( 10 , 156/2 );
	
	
	
//	static int IMAGE_SIZE = 156;
	
	
	
	
	static String FONT_NAME = "Calibri";
	
	
	Product product;
	BufferedImage image;
	
	String title = "";
	String line1 = "";
	String line2 = "";
	String line3 = "";
	String plu = "";
	
	String price = "";
	String priceGoodThru = "";
	String adDate = "";
	
	double rebate = 0;
	String rebateDate = "";
	
	
	public ProductTile2(){
		
		
		image = createImageNotFound( 300 , 300 , 40 , Color.WHITE , Color.BLACK );
		this.setProduct( null );
		//this.setPreferredSize( PREFERRED_DIMENSIONS );
		
		Border border = BorderFactory.createRaisedBevelBorder();
		this.setBorder( border );
		
	}
	public ProductTile2( Product product ){
		
		this();
		setProduct( product );
		
		Border border = BorderFactory.createRaisedBevelBorder();
		this.setBorder( border );
		
	}
	
	
	public Dimension getPreferredSize(){
		
		//return PREFERRED_DIMENSION_4K;
		return PREFERRED_DIMENSION_1080p;
		
	}
	
	
	
	public void setProduct( Product product ){
		
		this.product = product;
		
		if( product == null )
		{
			title = "";
			line1 = "";
			line2 = "";
			line3 = "";
			plu = "";
			
			price = "";
			priceGoodThru = "";
			adDate = "";
			
			rebate = 0;
			rebateDate = "";
			
			this.setBackground( CLEAR );
			image = createImageNotFound( 300 , 300 , 40 , Color.WHITE , Color.BLACK );
			
		}
		else
		{
			
			title = product.title.trim();
			line1 = product.line1.trim();
			line2 = product.line2.trim();
			line3 = product.line3.trim();
			
			plu = "#" + product.plu.trim();
			price = product.regPrice.trim();
			rebate = product.rebate;
			rebateDate = "Rebate good thru" + " " + product.rebateGoodThru;
			
			if( product.adPrice != null && !product.adPrice.isEmpty() )
			{
				price = product.adPrice.trim();
				priceGoodThru = "Price good thru";
				adDate = product.adDate.trim();
			}
			else
			{
				priceGoodThru = "";
				adDate = "";
			}
			
			
			
			String path = "images/" + product.plu + ".jpg";
			
			try
			{
				File file = new File( path );
				BufferedImage image = Methods.loadImage( file );
				
				//image = Methods.scaleImage( image , IMAGE_SIZE , IMAGE_SIZE );
				
				this.image = image;
				
				
			}
			catch ( IOException e )
			{
				System.out.println( "failed to load image: " + path );
				
				image = createImageNotFound( 300 , 300 , 40 , Color.WHITE , Color.BLACK );
				
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
		
		
		//this.repaint();
		
		
	}
	
	protected void paintComponent( Graphics gfx ) {
		
		super.paintComponent( gfx );
		Graphics2D g = (Graphics2D) gfx;
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON );
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		g.setRenderingHint( RenderingHints.KEY_COLOR_RENDERING , RenderingHints.VALUE_COLOR_RENDER_QUALITY );
		
		//boolean drawRectangles = false;
		
		
		if( product == null )
		{
			//g.setColor(  );
			//g.drawRect( 0 , 0 , this.getWidth() , this.getHeight() );
			return;
		}
		
		int imageSize = this.getHeight();
		BufferedImage scaledImage = Methods.scaleImage( image , imageSize , imageSize );
		
		//draw image
		g.setColor( Color.WHITE );
		g.fillRect( 0 , 0 , imageSize , imageSize );
		
		if( debug ) g.setColor( lineColor );
		if( debug ) g.drawRect( 0 , 0 , imageSize , imageSize );
		g.drawImage( scaledImage , 0 , 0 , null );
		
		
		//draw title
		int titleW = this.getWidth() - imageSize;
		int titleH = (int) (this.getHeight() * (4.0/16.0));
		if( debug ) g.setColor( lineColor );
		if( debug ) g.drawRect( imageSize  ,  0  , titleW , titleH );
		
		int titleFontSize = titleH * 2/3;
		Font titleFont = new Font( FONT_NAME , Font.BOLD , titleFontSize );
		
		g.setColor( Color.black );
		Methods.drawStringCenteredOn( g , title , titleFont , new Rectangle(imageSize,0,titleW,titleH) );
		
		
		
		
		//draw lines
		int lineW = (int) (titleW / 2.0);
		int lineH = (int) (this.getHeight() * (3.0/16.0));
		
		int lineFontSize = lineH * 2/3;
		Font lineFont = new Font( FONT_NAME , Font.PLAIN , lineFontSize );
		
		if( debug ) g.setColor( lineColor );
		if( debug ) g.drawRect( imageSize , titleH         , lineW , lineH );
		if( debug ) g.drawRect( imageSize , titleH+lineH*1 , lineW , lineH );
		if( debug ) g.drawRect( imageSize , titleH+lineH*2 , lineW , lineH );
		if( debug ) g.drawRect( imageSize , titleH+lineH*3 , lineW , lineH );
		
		g.setColor( Color.BLACK );
		Methods.drawStringLeftJustifiedOn( g , line1 , lineFont , 10 , new Rectangle(imageSize,titleH,lineW,lineH) );
		Methods.drawStringLeftJustifiedOn( g , line2 , lineFont , 10 , new Rectangle(imageSize,titleH+lineH*1,lineW,lineH) );
		Methods.drawStringLeftJustifiedOn( g , line3 , lineFont , 10 , new Rectangle(imageSize,titleH+lineH*2,lineW,lineH) );
		Methods.drawStringLeftJustifiedOn( g , plu   , lineFont , 10 , new Rectangle(imageSize,titleH+lineH*3,lineW,lineH) );
		
		
		int rightW = lineW;
		int rightH = this.getHeight() - titleH;
		
		int priceW = lineW;
		int priceH = (int) (rightH * 0.4);
		
		if( rebate == 0 )
		{
			
			rebateDate = "";
			
			
			
//			//draw price
//			
//			
//			
//			if( debug ) g.setColor( lineColor );
//			if( debug ) g.drawRect( imageSize+lineW , titleH , rightW-1 , rightH-1 );
//			
//			
//			int priceFontSize = (int) (priceH * 0.85);
//			Font priceFont = new Font( FONT_NAME , Font.BOLD , priceFontSize );
//			
//			
//			//if( debug ) g.setColor( Color.red );
//			//if( debug ) g.drawRect( imageSize+lineW , titleH + (rightH-priceH)/2 , priceW , priceH );
//			
//			
//			g.setColor( Color.BLACK );
//			drawStringCenteredOn( g , price , priceFont , new Rectangle(imageSize+lineW , titleH + (rightH-priceH)/2 , priceW , priceH) );
			
			
			
			Rectangle priceAreaRect = new Rectangle(imageSize+lineW , titleH + (rightH-priceH)/2 , priceW , priceH );
			double priceDouble = Double.parseDouble( price.replace( "$" , "" ).replace( "," , "" ) );
			drawPrice2( g , priceDouble , priceAreaRect );
			
			
			
			
		}
		else
		{
			rebateDate = "Rebate good thru" + " " + product.rebateGoodThru;
			
			
			
			String rebateString = new DecimalFormat( "$#,##0.00" ).format( rebate*-1 );
			String priceString = price.replace( "$" , "" ).replace( "," , "" );
			double priceBeforeRebate = Double.parseDouble( priceString );
			double priceAfterRebate = priceBeforeRebate - rebate;
			String priceAfterRebateString = new DecimalFormat( "$#,##0.00" ).format( priceAfterRebate );
			
			
			int rebateH = (int) (rightH * 0.7)/3;
			int priceFontSize = (int) (rebateH * 0.85);
			Font priceFont = new Font( FONT_NAME , Font.BOLD , priceFontSize );
			
			g.setColor( Color.BLACK );
			Methods.drawStringCenteredOn( g , price                  , priceFont , new Rectangle(imageSize+lineW , titleH + rebateH*0 , priceW , rebateH) );
			Methods.drawStringCenteredOn( g , rebateString           , priceFont , new Rectangle(imageSize+lineW , titleH + rebateH*1 , priceW , rebateH) );
			Methods.drawStringCenteredOn( g , priceAfterRebateString , priceFont , new Rectangle(imageSize+lineW , titleH + rebateH*2 , priceW , rebateH) );
			
			//draw addition line
			int priceWidth = Methods.getStringWidth( priceFont , price );
			int priceAfterWidth = Methods.getStringWidth( priceFont , priceAfterRebateString );
			int textW = Math.max( priceWidth , priceAfterWidth );
			int gap = (priceW - textW)/2;
			g.setColor( Color.black );
			g.drawLine( imageSize+lineW+gap , titleH + rebateH*2 , this.getWidth()-gap , titleH + rebateH*2 );
			
			
		}
			
		
		
//		//draw priceGoodThru
//		int goodthruW = priceW;
//		int goodthruH = (rightH - priceH)/4;
//		
//		int goodthruFontSize = goodthruH * 7/7;
//		Font goodthruFont = new Font( FONT_NAME , Font.PLAIN , goodthruFontSize );
//		
////		if( debug ) g.setColor( Color.blue );
////		if( debug ) g.drawRect( imageSize+lineW , this.getHeight()-1 - goodthruH*2 , goodthruW , goodthruH );
////		if( debug ) g.drawRect( imageSize+lineW , this.getHeight()-1 - goodthruH*1 , goodthruW , goodthruH );
//		
//		g.setColor( Color.BLACK );
//		drawStringCenteredOn( g , priceGoodThru , goodthruFont , new Rectangle(imageSize+lineW , this.getHeight()-1 - goodthruH*2 , goodthruW , goodthruH) );
//		drawStringCenteredOn( g , date          , goodthruFont , new Rectangle(imageSize+lineW , this.getHeight()-1 - goodthruH*1 , goodthruW , goodthruH) );
		
		
		//draw priceGoodThru
		int goodthruW = priceW;
		int goodthruH = (rightH - priceH)/4;
		
		int goodthruFontSize = goodthruH * 5/7;
		Font goodthruFont = new Font( FONT_NAME , Font.PLAIN , goodthruFontSize );
		
//		if( debug ) g.setColor( Color.blue );
//		if( debug ) g.drawRect( imageSize+lineW , this.getHeight()-1 - goodthruH*2 , goodthruW , goodthruH );
//		if( debug ) g.drawRect( imageSize+lineW , this.getHeight()-1 - goodthruH*1 , goodthruW , goodthruH );
		
		g.setColor( Color.BLACK );
		Methods.drawStringCenteredOn( g , priceGoodThru + " " + adDate , goodthruFont , new Rectangle(imageSize+lineW , this.getHeight()-1 - goodthruH*2 , goodthruW , goodthruH) );
		Methods.drawStringCenteredOn( g , rebateDate                   , goodthruFont , new Rectangle(imageSize+lineW , this.getHeight()-1 - goodthruH*1 , goodthruW , goodthruH) );
		
		
		
		
		
		
		
		
		if( this.product.status.toLowerCase().equals( "d" ) )
		{
			g.setColor( new Color(150,125,125 , 175 ) );
			g.fillRect( 0 , 0 , this.getWidth() , this.getHeight() );
			
			int discontinuedFontSize = this.getHeight() * 1/3;
			Font discontinuedFont = new Font( FONT_NAME , Font.BOLD , discontinuedFontSize );
			g.setColor( Color.BLACK );
			Methods.drawStringCenteredOn( g , "DISCONTINUED" , discontinuedFont , new Rectangle( 0 , 0 , this.getWidth() , this.getHeight() ) );
		}
		
		if( this.product.status.toLowerCase().equals( "n" ) )
		{
			g.setColor( new Color(150,125,125 , 175 ) );
			g.fillRect( 0 , 0 , this.getWidth() , this.getHeight() );
			
			
			int comingSoonFontSize = this.getHeight() * 1/3;
			Font comingSoonFont = new Font( FONT_NAME , Font.BOLD , comingSoonFontSize );
			g.setColor( Color.BLACK );
			Methods.drawStringCenteredOn( g , "COMING SOON" , comingSoonFont , new Rectangle( 0 , 0 , this.getWidth() , this.getHeight() ) );
		}
		
		
		
		
	}
	
	
	public void drawPrice( Graphics2D g , double price , Rectangle priceAreaRect ){
		
		
		double dollars = Math.floor( price );
		double cents = price - dollars;
		
		String dollarsText = new DecimalFormat( "$#,###.##" ).format( dollars );
		String centsText = new DecimalFormat( "#.00" ).format( cents ).replace( "." , "" );
		
		
		int dollarFontSize = (int) (priceAreaRect.height * 1.15);
		Font dollarFont = new Font( FONT_NAME , Font.BOLD , dollarFontSize );
		
		int centFontSize = dollarFontSize / 2;
		Font centFont = new Font( FONT_NAME , Font.BOLD , centFontSize ); 
		
		int dollarsW = Methods.getStringWidth( dollarFont , dollarsText );
		int dollarsH = Methods.getStringHeight( dollarFont );
		
		int centsW = Methods.getStringWidth( centFont , centsText );
		int centsH = Methods.getStringHeight( centFont );
		
		int wGap = ( priceAreaRect.width - dollarsW - centsW ) / 2;
		int hGap = ( priceAreaRect.height - dollarsH - centsH ) / 2;
		
		int dollarsX = priceAreaRect.x + wGap;
		int dollarsY = priceAreaRect.y + hGap + dollarsH;
		
		int centsX = priceAreaRect.x + wGap + dollarsW;
		int centsY = priceAreaRect.y + hGap + centsH;
		
		g.setFont( dollarFont );
		g.drawString( dollarsText , dollarsX , dollarsY );
		
		g.setFont( centFont );
		g.drawString( centsText   , centsX   , centsY );
		
		//g.setColor( Color.red );
		//g.drawRect( priceAreaRect.x , priceAreaRect.y , priceAreaRect.width , priceAreaRect.height );
		
		
	}
	public void drawPrice2( Graphics2D g , double price , Rectangle priceAreaRect ){
		
		
		double dollars = Math.floor( price );
		double cents = price - dollars;
		
		String dollarsText = new DecimalFormat( "#,###.##" ).format( dollars );
		String centsText = new DecimalFormat( "#.00" ).format( cents ).replace( "." , "" );
		
		
		int dollarFontSize = (int) (priceAreaRect.height * 1.20);
		Font dollarFont = new Font( FONT_NAME , Font.BOLD , dollarFontSize );
		
		int centFontSize = dollarFontSize / 2;
		Font centFont = new Font( FONT_NAME , Font.BOLD , centFontSize ); 
		
		
		
		int dollarSignW = Methods.getStringWidth( centFont , "$" );
		int dollarSignH = Methods.getStringHeight( centFont );
		
		int dollarsW = Methods.getStringWidth( dollarFont , dollarsText );
		int dollarsH = Methods.getStringHeight( dollarFont );
		
		int centsW = Methods.getStringWidth( centFont , centsText );
		int centsH = Methods.getStringHeight( centFont );
		
		
		
		int wGap = ( priceAreaRect.width - dollarSignW - dollarsW - centsW ) / 2;
		int hGap = ( priceAreaRect.height - dollarSignH - dollarsH - centsH ) / 2;		hGap = 0;
		
		int dollarSignX = priceAreaRect.x + wGap;
		int dollarSignY = priceAreaRect.y + hGap + dollarSignH;
		
		int dollarsX = priceAreaRect.x + wGap + dollarSignW;
		int dollarsY = priceAreaRect.y + hGap + dollarsH;
		
		int centsX = priceAreaRect.x + wGap + dollarSignW + dollarsW;
		int centsY = priceAreaRect.y + hGap + centsH;
		
		g.setFont( centFont );
		g.drawString( "$" , dollarSignX , dollarSignY );
		
		g.setFont( dollarFont );
		g.drawString( dollarsText , dollarsX , dollarsY );
		
		g.setFont( centFont );
		g.drawString( centsText   , centsX   , centsY );
		
		//g.setColor( Color.red );
		//g.drawRect( priceAreaRect.x , priceAreaRect.y , priceAreaRect.width , priceAreaRect.height );
		
		
	}
	
	
	
	
	public static BufferedImage createImageNotFound( int width , int height , int fontSize , Color backColor , Color textColor ){
		
		
		BufferedImage image = new BufferedImage( width , height , BufferedImage.TYPE_INT_RGB );
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		g.setColor( backColor );
		g.fillRect( 0 , 0 , width , height );
		
		g.setFont( new Font( "Tahoma" , Font.PLAIN , fontSize ) );
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
	
	
	
	
	
	
	public static void main(String[] args) throws IllegalSaveStringException {
		
		
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize( 522+16 , 156+39 );
		
		String saveString = "8329567|815530018568|DDR4 16GB (4 x 4GB)|Patriot 2666MHz|LINE 2|PX416G266C5QQK|$9999.99|R|";
		//String saveString = "8329567|815530018568|DDR4 16GB (4x4GB)|Patriot 2666MHz|LINE 2|PX416G266C5QQK|$329.99|R|";
		
		Product product = SaveLoad.getProduct( saveString );
		//product.adDate = "04/13/17";
		//product.rebate = 20;
		//product.rebateGoodThru = "04/15/17";
		
		
		ProductTile2 productTile2 = new ProductTile2( product );
		frame.add( productTile2 );
		
		
		
		frame.setVisible( true );
		
		//static Dimension PREFERRED_DIMENSIONS = new Dimension( 522,156 );
		
		System.out.println( "frame: " + frame.getSize() );
		System.out.println( "productTile: " + productTile2.getSize() );
		
		
	}
	
	
	
	
	
	
	
	
}














