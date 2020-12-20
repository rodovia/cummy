package rodovia.cummy.api;

public enum Category {
	GENERAL,
	MISC,
	MODERATION,
	FUN;
	
	public static String asString(Category item) {
		switch(item) {
		case GENERAL:
			return "Geral";
		case MISC:
			return "Miscelânea";
		case MODERATION:
			return "Moderação";
		case FUN:
			return "Diversão";			
		default:
			throw new IllegalArgumentException("Unsupported category.");
		}
	}
	
	public static Category[] all() {
		return new Category[] {GENERAL, MISC, MODERATION, FUN};
	}
}