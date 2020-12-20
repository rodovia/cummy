package rodovia.cummy.errors;

public class CummyException extends Error {
	// não me pergunte o que é por quê eu não sei.
	private static final long serialVersionUID = 4731088069019476970L;
	
	public CummyException(String message) {
		super(message);
	}
	
	public CummyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CummyException() {
		
	}
}


