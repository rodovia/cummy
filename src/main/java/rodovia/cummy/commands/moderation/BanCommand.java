package rodovia.cummy.commands.moderation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import rodovia.cummy.api.Category;
import rodovia.cummy.api.Command;
import rodovia.cummy.api.Context;
import rodovia.cummy.errors.NullMemberException;
import rodovia.cummy.utils.ArgumentParser;
import rodovia.cummy.utils.Constants;

public class BanCommand extends Command {
	
	public BanCommand() {
		this.name = "ban";
		this.aliases = new String[] {"banir"};
		this.category = Category.MODERATION;
		this.requiredPermissions = new Permission[] {Permission.BAN_MEMBERS};
		this.requiredUserPermissions = new Permission[] {Permission.BAN_MEMBERS, Permission.KICK_MEMBERS};
		this.argumentation = "<user> [reason]";
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(Context ctx, String[] args) {
		String id;
		try {
			id = ArgumentParser.parseMentions(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			ctx.explain();
			return;
		}
		
		Member user = ctx.getGuild().getMemberById(Long.parseLong(id));
		if (user == null) {
			throw new NullMemberException("Cannot find member " + id);
		}
		
		String[] reasonArray;
			
		// verifica se o motivo do banimento foi informado
		try {
			List<String> parsed = new ArrayList<String>(Arrays.asList(args));
			List<String> newList = parsed.subList(1, args.length);
				
			reasonArray = newList.toArray(new String[0]);
		} catch(IndexOutOfBoundsException e) {
			// eu não sei se eu deveria suprimir o IllegalArgumentException...
			reasonArray = null;
		}
		
		String reason = "Ação executada por: " + ctx.getAuthorUser().getAsTag();
		if (reasonArray != null || reasonArray != new String[0]) {
			reason += " Razão: " + String.join(" ", reasonArray);
		}
		user.ban(1)
			.reason(reason)
			.queue();
			
		EmbedBuilder embed = new EmbedBuilder()
			.setColor(Constants.cummyDefaultColor)
			.setAuthor("Ação Realizada!")
			.setDescription(user.getUser().getAsTag() + " foi banido.");
			
		MessageBuilder message = new MessageBuilder()
			.setEmbed(embed.build())
			.setContent(ctx.getAuthorUser().getAsMention());
			
		ctx.send(message.build()).queue();
			
		return;
		
		
	}

}
