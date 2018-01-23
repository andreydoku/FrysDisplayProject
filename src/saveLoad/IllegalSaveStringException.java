package saveLoad;


public class IllegalSaveStringException extends Exception {
	
	
	private String saveString;
	
	
	public IllegalSaveStringException( String saveString , String message ){
		
		super( message );
		this.saveString = saveString;
		
	}
	public String getSaveString(){
		
		return this.saveString;
		
	}
}