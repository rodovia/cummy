package rodovia.cummy;

import java.awt.Color;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.EmbedBuilder;
import rodovia.cummy.api.Command;
import rodovia.cummy.api.CommandListenerAdapter;
import rodovia.cummy.api.Context;
import rodovia.cummy.errors.MissingPermissionsException;
import rodovia.cummy.errors.UserMissingPermissionsException;

public class CommandListener extends CommandListenerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);
	
	public CommandListener() {
		
	}
	
	@Override
	public void onCommandError(Throwable t, Context ctx, Command cmd) {
		if (t instanceof MissingPermissionsException) {
			List<String> permissions = ((MissingPermissionsException) t).getMissingPermissions();

			EmbedBuilder builder = new EmbedBuilder()
					.setTitle("O comando não pôde ser realizado devido a insuficiências de permissões minhas.")
					.setDescription("É necessário das permissão(ões) " + permissions)
					.setColor(Color.RED);

			ctx.send(builder).queue();
			return;
		}
		else if (t instanceof UserMissingPermissionsException) {
			List<String> permissions = ((UserMissingPermissionsException) t).getMissingPermissions();
			
			EmbedBuilder builder = new EmbedBuilder()
					.setTitle("O comando não pôde ser realizado devido "
							+ "a insuficiências de permissões do autor.")
					.setDescription("É necessário das permissão(ões) " + permissions)
					.setColor(Color.RED);
			
			ctx.send(builder).queue();
			return;
		}
		
		
		else {
			ctx.send("Algo aconteceu durante a execução do comando, Desculpe a incoveniência!").queue();
			
			LOGGER.error(String.format("While running the command %s, an error occurred. Printing stack trace.", cmd.getName()));
			t.printStackTrace();
		}
	}
}
