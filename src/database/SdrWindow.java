package database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SdrWindow extends JFrame{
	
	
	
	
	JPanel topPanel = new JPanel();
		JLabel instructionsLabel = new JLabel( "Load stock display report for..." );
		JTextField addressField = new JTextField();
		JButton browseBtn = new JButton( "Browse" );
	
	JPanel mainPanel = new JPanel();
		JScrollPane sp = new JScrollPane();
			JTextArea textArea = new JTextArea();

	JPanel btnPanel = new JPanel();
		JLabel applyLabel = new JLabel( "Apply these changes?" );
		JButton applyBtn = new JButton( "Apply" );
		JButton cancelBtn = new JButton( "Cancel" );
	
	
		
	public SdrWindow(){
		
		build();
		
	}
	public void build(){
		
		
		int bt = 20;
		Color borderColor = Color.lightGray.brighter();
		
		
		buildTopPanel();
		topPanel.setBorder( BorderFactory.createMatteBorder( bt , bt , 0 , bt , borderColor ) );
		
		textArea.setEditable( false );
		textArea.setBackground( Color.WHITE );
		
		
		applyBtn.addActionListener( new BtnListener() );
		cancelBtn.addActionListener( new BtnListener() );
		
		applyBtn.setEnabled( false );
		
		btnPanel.add( applyLabel );
		btnPanel.add( applyBtn );
		btnPanel.add( cancelBtn );
		btnPanel.setBorder( BorderFactory.createMatteBorder( 0 , bt , bt , bt , borderColor ) );
		
		
		Border matteBorder = BorderFactory.createMatteBorder( bt , bt , bt , bt , borderColor );
		Border lineBorder = BorderFactory.createLineBorder( new Color(171,173,179) );
		Border compoundBorder = BorderFactory.createCompoundBorder( matteBorder , lineBorder );
		sp.setBorder( compoundBorder );
		
		sp.setViewportView( textArea );
		sp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
		
		
		this.setLayout( new BorderLayout() );
		this.add( topPanel , BorderLayout.NORTH );
		this.add( sp , BorderLayout.CENTER );
		this.add( btnPanel , BorderLayout.SOUTH );
		
		
		this.setSize( 600,500 );
		this.setLocationRelativeTo( null );
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
	}
	public void buildTopPanel(){
		
		topPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0; c.gridy = 0; topPanel.add( instructionsLabel , c );
		
		
		c.gridwidth = 1;
		c.weightx = 1;
		c.gridx = 0; c.gridy = 1; topPanel.add( addressField , c );
		
		c.weightx = 0;
		c.gridx = 1; c.gridy = 1; topPanel.add( browseBtn , c );
		
		
		
		addressField.setEditable( false );
		
		browseBtn.addActionListener( new BtnListener() );
		
		
	}
	
	public void browse(){
		
		JFileChooser fc = new JFileChooser();
		File startingDirectory = new File( "./" );
		fc.setCurrentDirectory( startingDirectory );
		FileNameExtensionFilter filter = new FileNameExtensionFilter( ".txt files" , "txt" , "text" );
		fc.setFileFilter(filter);
		
		
		
		int returnVal = fc.showOpenDialog( this );
		if ( returnVal == JFileChooser.APPROVE_OPTION )
		{
			File file = fc.getSelectedFile();
			addressField.setText( file.getPath() );
			
			
			loadSdrFile( file );
			
		}
		else
		{
			addressField.setText( "" );
		}
		
	}
	
	//override later
	public void loadSdrFile( File sdrFile ){}
	public void apply(){}
	public void cancel(){
		
		this.dispose();
		
	}
	
	
	
	public void addText( String text ){
		
		textArea.append( text );
		textArea.setCaretPosition( textArea.getDocument().getLength() );
		
	}
	public void addTextLine( String text ){
		
		
		textArea.append( text + "\n" );
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
	}
	
	
	
	private class BtnListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ){
			
			if( e.getSource() == browseBtn )
			{
				browse();
			}
			if( e.getSource() == applyBtn )
			{
				apply();
			}
			if( e.getSource() == cancelBtn )
			{
				cancel();
			}
			
		}
		
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		SdrWindow sdrw = new SdrWindow();
		sdrw.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		sdrw.setVisible( true );
		
	}
	
	
	
	
	
}




















