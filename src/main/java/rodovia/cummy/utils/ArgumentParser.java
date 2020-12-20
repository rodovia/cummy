package rodovia.cummy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.api.JDA;

public class ArgumentParser {

	private static final Pattern MENTION = Pattern.compile("^<@!?(\\d{17,19})>");
	
	public static String parseMentions(String str) {
		Matcher match = MENTION.matcher(str);
		
		if (match.find()) {
			String id = match.group(1);
			return id;
		}
		
		return null;
		
	}
}
