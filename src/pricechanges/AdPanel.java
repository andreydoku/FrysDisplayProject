package pricechanges;

import java.awt.*;
import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import javax.swing.*;

import display.ProductTile2;
import gui.ImagePanel;
import myLibrary.Methods;




public class AdPanel extends JPanel{
	
	
	final static int FONT_SIZE = 20;
	final static Color DISABLED_COLOR = Color.gray.brighter();
	
	
	JPanel leftPanel = new JPanel();
		JLabel pluLabel = new JLabel( "PLU" );
		JTextField pluField = new JTextField();
		
		JLabel onOffLabel = new JLabel( "On/Off Ad?" );
		JComboBox onOffBox = new JComboBox();
		
		JLabel regPriceLabel = new JLabel( "Reg Price" );
		JTextField regPriceField = new JTextField();
		
		JLabel adPriceLabel = new JLabel( "Ad Price" );
		JTextField adPriceField = new JTextField();
		
		JLabel goodThruLabel = new JLabel( "Good Thru" );
		JTextField goodThruField = new JTextField();
		
		JLabel promoLabel = new JLabel( "Promo" );
		JCheckBox promoCheckbox = new JCheckBox();
	
		JButton applyBtn = new JButton( "Apply" );
		
		
	JPanel rightPanel = new JPanel();
		ProductTile2 beforeProductTile = new ProductTile2();
		ProductTile2 afterProductTile = new ProductTile2();
		ImagePanel arrowPanel = new ImagePanel();
		
		
	
	private int state = 0;
	//0 - start, all empty, all disabled except plu
	//1 - plu entered, waiting for on/off input... on/off enabled, plu kept enabled, everything else disabled
	//2 - "On Ad" entered, waiting for ad price input, ad price enabled, good thru enabled, reg price disabled
	//3 - "Off Ad" entered, waiting for reg price input, ad price cleared and disabled, good thru cleared and disabled, reg price enabled
		
		
	
	public AdPanel(){
		
		build();
		
		setState( 0 );
		
	}
	
	public void build(){
		
		buildLeftPanel();
		buildRightPanel();
		
		this.setLayout( new BorderLayout() );
		this.add( leftPanel , BorderLayout.CENTER );
		this.add( rightPanel , BorderLayout.EAST );
		
	}
	public void buildLeftPanel(){
		
		
				
		String[] options = { "(choose)" , "On Ad" , "Off Ad" };
		onOffBox = new JComboBox<>( options );
		
		
		Font oldFont = pluLabel.getFont();
		Font newFont = new Font( oldFont.getFontName() , oldFont.getStyle() , FONT_SIZE );
		pluLabel.setFont( newFont );
		pluField.setFont( newFont );
		onOffLabel.setFont( newFont );
		onOffBox.setFont( newFont );
		regPriceLabel.setFont( newFont );
		regPriceField.setFont( newFont );
		adPriceLabel.setFont( newFont );
		adPriceField.setFont( newFont );
		goodThruLabel.setFont( newFont );
		goodThruField.setFont( newFont );
		promoLabel.setFont( newFont );
		applyBtn.setFont( newFont );
		
		
		leftPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		
		c.gridx = 0; c.gridy = 0; c.weightx = 0; c.insets = new Insets( 20,20,0, 0 ); leftPanel.add( pluLabel , c );
		c.gridx = 1; c.gridy = 0; c.weightx = 1; c.insets = new Insets( 20,30,0,20 ); leftPanel.add( pluField , c );
		
		c.gridx = 0; c.gridy = 1; c.weightx = 0; c.insets = new Insets( 20,20,0, 0 ); leftPanel.add( onOffLabel , c );
		c.gridx = 1; c.gridy = 1; c.weightx = 1; c.insets = new Insets( 20,30,0,20 ); leftPanel.add( onOffBox , c );
		
		c.gridx = 0; c.gridy = 2; c.weightx = 0; c.insets = new Insets( 20,20,0, 0 ); leftPanel.add( regPriceLabel , c );
		c.gridx = 1; c.gridy = 2; c.weightx = 1; c.insets = new Insets( 20,30,0,20 ); leftPanel.add( regPriceField , c );
		
		c.gridx = 0; c.gridy = 3; c.weightx = 0; c.insets = new Insets( 20,20,0, 0 ); leftPanel.add( adPriceLabel , c );
		c.gridx = 1; c.gridy = 3; c.weightx = 1; c.insets = new Insets( 20,30,0,20 ); leftPanel.add( adPriceField , c );
		
		c.gridx = 0; c.gridy = 4; c.weightx = 0; c.insets = new Insets( 20,20,0, 0 ); leftPanel.add( goodThruLabel , c );
		c.gridx = 1; c.gridy = 4; c.weightx = 1; c.insets = new Insets( 20,30,0,20 ); leftPanel.add( goodThruField , c );
		
		c.gridx = 0; c.gridy = 5; c.weightx = 0; c.insets = new Insets( 20,20,0, 0 ); leftPanel.add( promoLabel , c );
		c.gridx = 1; c.gridy = 5; c.weightx = 1; c.insets = new Insets( 20,30,0,20 ); leftPanel.add( promoCheckbox , c );
		
		
		
		applyBtn.setBorder( BorderFactory.createEmptyBorder( 5 , 30 , 5 , 30 ) );
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 2;
		c.gridx = 0; c.gridy = 6; c.weightx = 1; c.insets = new Insets( 20,0,20,0 ); leftPanel.add( applyBtn , c );
		
		
	}
	public void buildRightPanel(){
		
		
		rightPanel.setBackground( Color.gray );
		
		rightPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		
		
		c.gridx = 0; c.gridy = 0; c.weightx = 1; c.insets = new Insets( 10,10,10,10 ); rightPanel.add( beforeProductTile , c );
		c.gridx = 0; c.gridy = 1; c.weightx = 1; c.insets = new Insets( 10,10,10,10 ); rightPanel.add( arrowPanel , c );
		c.gridx = 0; c.gridy = 2; c.weightx = 1; c.insets = new Insets( 10,10,10,10 ); rightPanel.add( afterProductTile , c );
		
		
		int imageSize = 50;
		BufferedImage arrowImage = new BufferedImage( imageSize , imageSize , BufferedImage.TYPE_INT_ARGB );
		Graphics2D g = (Graphics2D) arrowImage.getGraphics();
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON );
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		g.setRenderingHint( RenderingHints.KEY_COLOR_RENDERING , RenderingHints.VALUE_COLOR_RENDER_QUALITY );
		
		g.setColor( Color.BLACK );
		Methods.drawArrow( g , 0 , 0 , imageSize , Math.PI/2 , Color.black , Color.white );
		
		arrowPanel.setOpaque( false );
		arrowPanel.setImage( arrowImage );
		
		
		//rightPanel.add( beforeProductTile );
		//rightPanel.add( afterProductTile );
		
		
	}
	
	
	public void setState( int state ){
		
		if( state == 0 )//start, all empty, all disabled except plu
		{
			
			//pluField.setText( "" );
			pluField.requestFocus();
			pluField.selectAll();
			
			
			onOffLabel.setForeground( DISABLED_COLOR );
			onOffBox.setSelectedIndex( 0 );
			onOffBox.setEnabled( false );
			
			regPriceLabel.setForeground( DISABLED_COLOR );
			regPriceField.setText( "" );
			regPriceField.setEditable( false );
			
			adPriceLabel.setForeground( DISABLED_COLOR );
			adPriceField.setText( "" );
			adPriceField.setEditable( false );
			
			goodThruLabel.setForeground( DISABLED_COLOR );
			goodThruField.setText( "" );
			goodThruField.setEditable( false );
			
			promoLabel.setForeground( DISABLED_COLOR );
			promoCheckbox.setSelected( false );
			promoCheckbox.setEnabled( false );
			
			applyBtn.setEnabled( false );
			
			pluField.requestFocus();
			
		}
		else if( state == 1 )//plu entered, waiting for on/off input... on/off enabled, plu kept enabled, everything else disabled
		{
						
			onOffLabel.setForeground( Color.black );
			onOffBox.setSelectedIndex( 0 );
			onOffBox.setEnabled( true );
			
			regPriceLabel.setForeground( DISABLED_COLOR );
			regPriceField.setForeground( DISABLED_COLOR );
			regPriceField.setEditable( false );
			
			adPriceLabel.setForeground( DISABLED_COLOR );
			adPriceField.setForeground( DISABLED_COLOR );
			adPriceField.setEditable( false );
			
			goodThruLabel.setForeground( DISABLED_COLOR );
			goodThruField.setForeground( DISABLED_COLOR );
			goodThruField.setEditable( false );
			
			promoLabel.setForeground( DISABLED_COLOR );
			promoCheckbox.setSelected( false );
			promoCheckbox.setEnabled( false );
			
			applyBtn.setEnabled( false );
			
			onOffBox.requestFocus();
			
		}
		else if( state == 2 )//"On Ad" entered, waiting for input of ad price/goodthru/promo - ad price enabled, good thru enabled, promo enabled, reg price disabled
		{
			onOffLabel.setForeground( Color.black );
			onOffBox.setEnabled( true );
			
			regPriceLabel.setForeground( DISABLED_COLOR );
			regPriceField.setForeground( DISABLED_COLOR );
			regPriceField.setEditable( false );
			
			adPriceLabel.setForeground( Color.black );
			adPriceField.setForeground( Color.black );
			adPriceField.setEditable( true );
			
			goodThruLabel.setForeground( Color.black );
			goodThruField.setForeground( Color.black );
			goodThruField.setEditable( true );
			
			promoLabel.setForeground( Color.black );
			//promoCheckbox.setSelected( false );
			promoCheckbox.setEnabled( true );
			
			applyBtn.setEnabled( true );
			
			adPriceField.requestFocus();
			adPriceField.selectAll();
			
		}
		else if( state == 3 )//"Off Ad" entered, waiting for reg price input, ad price cleared and disabled, good thru cleared and disabled, reg price enabled
		{
			onOffLabel.setForeground( Color.black );
			onOffBox.setEnabled( true );
			
			regPriceLabel.setForeground( Color.black );
			regPriceField.setForeground( Color.black );
			regPriceField.setEditable( true );
			
			adPriceLabel.setForeground( DISABLED_COLOR );
			adPriceField.setForeground( DISABLED_COLOR );
			adPriceField.setEditable( false );
			
			goodThruLabel.setForeground( DISABLED_COLOR );
			goodThruField.setForeground( DISABLED_COLOR );
			goodThruField.setEditable( false );
			
			promoLabel.setForeground( DISABLED_COLOR );
			promoCheckbox.setSelected( false );
			promoCheckbox.setEnabled( false );
			
			applyBtn.setEnabled( true );
			
			regPriceField.requestFocus();
			regPriceField.selectAll();
			
		}
		else
		{
			String message = "setState() - Illegal state: " + state;
			throw new IllegalArgumentException( message );
		}
		
		
		this.state = state;
		
		
	}
	
	public int getState(){
		
		return this.state;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}





























