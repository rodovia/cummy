package rodovia.cummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import rodovia.cummy.api.CommandManager;
import rodovia.cummy.commands.*;
import rodovia.cummy.commands.moderation.*;
import rodovia.cummy.utils.EnviromentAdapter;

public class Cummy {
	private static CommandManager manager = new CommandManager();
	private static EnviromentAdapter enviroment = new EnviromentAdapter();
	private static Logger LOGGER = LoggerFactory.getLogger(Cummy.class);
	
	public static EnviromentAdapter getEnviromentVariables() {
		return enviroment;
	}
	
	public static CommandManager getManager() {
		return manager;
	}
	
	@SuppressWarnings("static-access")
	private Cummy() throws Exception {	
		getManager().addCommands(
				// GENERAL
				new PingCommand(),
				new HelpCommand(),
				
				// MODERATION
				new BanCommand()
			);
				
		this.manager.addListener(new CommandListener());
		
		JDABuilder.create(
					GatewayIntent.GUILD_MESSAGES,
					GatewayIntent.GUILD_EMOJIS,
					GatewayIntent.GUILD_MEMBERS
				)
			.setToken(getEnviromentVariables().getToken())
			.addEventListeners(new Listener())
			.build();
	}
	
	public static void main(String[] args) throws Exception {
		new Cummy();
	}
}
