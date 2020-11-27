package rodovia.cummy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import rodovia.cummy.api.Command;
import rodovia.cummy.api.Context;
import rodovia.cummy.threads.ActivityThread;

public class Listener extends ListenerAdapter {
	private final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
	private final String PREFIX = Cummy.getEnviromentVariables().getPrefix();

	public Listener() {  }
	
	@Override
	public void onReady(ReadyEvent event) {
		LOGGER.info(String.format("%s is ready.", event.getJDA().getSelfUser().getAsTag()));
		
		ActivityThread thread = new ActivityThread(event.getJDA());
		thread.start();
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getMessage().getContentDisplay().startsWith(this.PREFIX)) {
			String raw = event.getMessage().getContentRaw();
			String[] splitted = raw.split(" ");
			
			String query = raw.substring(PREFIX.length(), splitted[0].length());
			Command possibleCommand = Cummy.getManager().getCommand(query);
			if (possibleCommand != null) {
				String[] args = this.subListOfStringArray(splitted, 1, splitted.length);
				Context ctx = new Context(event, query, possibleCommand, Cummy.getManager());
				try {
					possibleCommand.run(ctx, args);
				} catch(Throwable e) {
					Cummy.getManager().executeCommandEvent(ctx, possibleCommand, e);
				}
			}
		}
	}
	
	/** o que isso faz:
	 * Converte uma String[] para uma {@link List<String> java.util.List<java.lang.String>}, para fazer o corte,
	 * e depois volta a ser uma String[] com o corte
	 **/
	private String[] subListOfStringArray(String[] cuttable, int start, int end) {
		List<String> parsed = new ArrayList<String>(Arrays.asList(cuttable));
		List<String> newList = parsed.subList(start, end);
		
		return newList.toArray(new String[0]);
	}
}
