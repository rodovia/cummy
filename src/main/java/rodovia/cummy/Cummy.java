package rodovia.cummy;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import rodovia.cummy.api.CommandManager;
import rodovia.cummy.utils.EnviromentAdapter;
import rodovia.cummy.commands.*;
import rodovia.cummy.commands.moderation.*;

public class Cummy {
	private static CommandManager manager = new CommandManager();
	private static EnviromentAdapter enviroment = new EnviromentAdapter();
	
	public static EnviromentAdapter getEnviromentVariables() {
		return enviroment;
	}
	
	public static CommandManager getManager() {
		return manager;
	}
	
	@SuppressWarnings("static-access")
	private Cummy() throws Exception {		
		EnviromentAdapter env = new EnviromentAdapter();
		
		JDABuilder.create(
					GatewayIntent.GUILD_MESSAGES,
					GatewayIntent.GUILD_EMOJIS,
					GatewayIntent.GUILD_MEMBERS
				)
			.setToken(env.getToken())
			.addEventListeners(new Listener())
			.build();
	
		this.manager.addCommands(
			// GENERAL
			new PingCommand(),
			new HelpCommand(),
			
			// MODERATION
			new BanCommand()
		);
		
		this.manager.addListener(new CommandListener());
	}
	
	public static void main(String[] args) throws Exception {
		new Cummy();
	}
}
