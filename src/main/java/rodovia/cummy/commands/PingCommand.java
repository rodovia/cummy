package rodovia.cummy.commands;

import rodovia.cummy.api.Category;
import rodovia.cummy.api.Command;
import rodovia.cummy.api.Context;

public class PingCommand extends Command {
	
	public PingCommand() {
		this.name = "ping";
		this.aliases = new String[] {"pong"};
		this.category = Category.GENERAL;
	}
	
	@Override
	public void execute(Context ctx, String[] args) {
		long ping = ctx.getJDA().getGatewayPing();
		ctx.send(String.format("Ping? *Pong*!\nLatency:**%sms**", Math.round((double) ping))).queue();
	}
	
}
