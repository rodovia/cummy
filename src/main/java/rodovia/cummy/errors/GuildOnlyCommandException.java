package rodovia.cummy.errors;

public class GuildOnlyCommandException extends CummyException {
	private static final long serialVersionUID = 1L;
	
	public GuildOnlyCommandException(String message) {
		super(message);
	}
	
	public GuildOnlyCommandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public GuildOnlyCommandException() {
		super();
	}
}
