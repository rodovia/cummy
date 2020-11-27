package rodovia.cummy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
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
			Permission[] permissions = ((MissingPermissionsException) t).getMissingPermissions();
			
			// pega os nomes das permissões faltando.
			Stream<String> stream = new ArrayList<Permission>(Arrays.asList(permissions))
					.stream().map((it) -> it.getName());
			List<String> l = stream.collect(Collectors.toList());
			List<String> missingPermissionsName = new ArrayList<String>(l);
			
			EmbedBuilder builder = new EmbedBuilder()
					.setTitle("O comando não pôde ser realizado devido a insuficiências de permissões minhas.")
					.setDescription("É necessário das permissão(ões) " + missingPermissionsName.toArray(new String[0]))
					.setColor(Color.RED.brighter())
					.setFooter(String.format("Esta ação foi tomada por %s", 
							ctx.getAuthorUser().getAsTag()), 
							ctx.getAuthorUser().getAvatarUrl());

			ctx.send(builder).queue();
			return;
		}
		else if (t instanceof UserMissingPermissionsException) {
			Permission[] permissions = ((UserMissingPermissionsException) t).getMissingPermissions();
			
			Stream<String> stream = new ArrayList<Permission>(Arrays.asList(permissions))
					.stream().map((it) -> it.getName());
			List<String> l = stream.collect(Collectors.toList());
			List<String> missingPermissionsName = new ArrayList<String>(l);
			
			EmbedBuilder builder = new EmbedBuilder()
					.setTitle("O comando não pôde ser realizado devido a insuficiências de permissões minhas.")
					.setDescription("É necessário das permissão(ões) " + missingPermissionsName.toArray(new String[0]))
					.setColor(Color.RED.brighter())
					.setFooter(String.format("Esta ação foi tomada por %s", 
							ctx.getAuthorUser().getAsTag()), 
							ctx.getAuthorUser().getAvatarUrl());
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
