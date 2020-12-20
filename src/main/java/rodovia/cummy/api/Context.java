package rodovia.cummy.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import rodovia.cummy.utils.Constants;
import rodovia.cummy.utils.EnviromentAdapter;

public class Context {
	private GuildMessageReceivedEvent event;
	private Command commandIssued;
	private CommandManager cmdMan;
	private String invokedWith;
	
	public Context(GuildMessageReceivedEvent event, String invokedAs, Command commandIssued, CommandManager man) {
		this.event = event;
		this.commandIssued = commandIssued;
		this.cmdMan = man;
		this.invokedWith = invokedAs;
	}
	
	public SelfUser getSelfUser() {
		return this.getJDA().getSelfUser();
	}
	
	public Member getSelfUserAsMember() {
		Member m = this.getGuild().getMember(this.getSelfUser());
		return m;
	}
	
	public User getAuthorUser() {
		return this.event.getAuthor();
	}
	
	public Member getAuthor() {
		return this.event.getMember();
	}
	
	public Guild getGuild() {
		return this.event.getGuild();
	}
	
	public Message getMessage() {
		return this.event.getMessage();
	}
	
	public TextChannel getChannel() {
		return this.getMessage().getTextChannel();
	}
	
	public MessageAction send(String content) {
		return this.getChannel().sendMessage(content);
	}
	
	public void explain() {
		
		EmbedBuilder emb = new EmbedBuilder()
				.setAuthor("Ajuda com o comando " + this.commandIssued.getName());
		
		if (this.commandIssued.getHelp() != null) 
			emb.setDescription(this.commandIssued.getHelp());
		else
			emb.setDescription("Nenhuma ajuda disponível.");
		
		if (this.commandIssued.getAliases() != null) {
			StringBuilder aliases = new StringBuilder();
			
			for (String aliase : this.commandIssued.getAliases()) {
				aliases.append(aliase);
			}
			
			emb.addField("Sinônimos", aliases.toString(), true);			
		}
		
		if (this.commandIssued.getExample() != null) {
			emb.addField("Exemplo", this.commandIssued.getExample(), true);
		}
		
		if (this.commandIssued.getArgumentation() != null) {
			String prefix = new EnviromentAdapter().getPrefix();
			String commandName = this.commandIssued.getName();
			String arguments = this.commandIssued.getArgumentation();
			emb.addField("Argumentos", String.format("%s%s %s", prefix, commandName, arguments), false);
		}
		
		emb.setColor(Constants.cummyDefaultColor);
		this.send(emb).queue();
		
	}
	
	public MessageAction send(EmbedBuilder embed) {
		return this.getChannel().sendMessage(embed.build());
	}
	
	public MessageAction send(Message message) {
		// é o que você recebe de uma MessageBuilder.
		return this.getChannel().sendMessage(message);
	}
	
	public MessageAction reply(String content) {
		return this.getChannel().sendMessage(this.getAuthor().getAsMention() + " " + content);
	}
	
	public CommandManager getCommandManager() {
		return this.cmdMan;
	}
	
	public String invokedAs() {
		return this.invokedWith;
	}
	
	public JDA getJDA() {
		return this.event.getJDA();
	}
	
	public Command getCommand() {
		return this.commandIssued;
	}
}
