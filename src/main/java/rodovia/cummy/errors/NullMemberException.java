package rodovia.cummy.errors;

/**
 * Jogado quando o membro passado como argumento for nulo.
 * @author rodovia
 */
public class NullMemberException extends NullPointerException {
	private static final long serialVersionUID = 1L;
	
	public NullMemberException(String message) {
		super(message);
	}
	
	public NullMemberException() {
		super();
	}
}
