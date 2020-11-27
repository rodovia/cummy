package rodovia.cummy.commands;

import java.util.function.Consumer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import rodovia.cummy.Cummy;
import rodovia.cummy.api.Category;
import rodovia.cummy.api.Command;
import rodovia.cummy.api.Context;
import rodovia.cummy.utils.Constants;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		this.name = "help";
		this.category = Category.GENERAL;
	}
	
	private String getSyntax(Command command) {
		String prefix = Cummy.getEnviromentVariables().getPrefix();
		String str = String.format("%s%s", prefix, command.getName());
		
		String aliases = String.join("|", command.getAliases());
		if (aliases != "") 
			str += "|" + aliases;
		
		if (command.getArgumentation() != null)
			str += " " + command.getArgumentation();
		
		return str;
	}
	
	@Override
	protected void execute(Context ctx, String[] args) {
		EmbedBuilder menuBuilder = new EmbedBuilder();
		
		for(Category c : Category.all()) {
			StringBuilder sb = new StringBuilder();
			for (Command command : Cummy.getManager().getCommands()) {
				if (command.getCategory() == c) {
					sb.append(String.format("%s -- %s\n", this.getSyntax(command), command.getBrief()));
				}
			}
			menuBuilder.addField(Category.asString(c), sb.toString(),false);
		}
		
		menuBuilder.setColor(Constants.cummyDefaultColor);
		
		MessageEmbed menu = menuBuilder.build();
		ctx.getAuthorUser().openPrivateChannel().queue(new Consumer<PrivateChannel>() {
				
			@Override
			public void accept(PrivateChannel channel) {
				channel.sendMessage(menu).queue();
			}
				
		});
		
	}
	
}
