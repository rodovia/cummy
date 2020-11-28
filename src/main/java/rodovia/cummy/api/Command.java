package rodovia.cummy.api;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import rodovia.cummy.errors.GuildOnlyCommandException;
import rodovia.cummy.errors.MissingPermissionsException;
import rodovia.cummy.errors.UserMissingPermissionsException;

public abstract class Command {
	protected String name = null;
	protected String[] aliases = new String[0];
	protected Permission[] requiredPermissions = new Permission[0];
	protected Permission[] requiredUserPermissions = new Permission[0];
	protected boolean guildOnly = false;
	protected String help = null;
	protected String brief = null;
	protected String example = null;
	protected String argumentation = null;
	protected Category category = null;
	
	protected abstract void execute(Context ctx, String[] args);
	
	public String getName() {
		return this.name;
	}
	
	public String[] getAliases() {
		return this.aliases;
	}
	
	public Permission[] getRequiredPermissions() {
		return this.requiredPermissions;
	}
	
	public boolean isGuildOnly() {
		return this.guildOnly;
	}
	
	public String getHelp() {
		return (this.help != null) ? this.help : "Nenhuma ajuda disponível";
	}
	
	public String getBrief() {
		if (this.brief == null)
			return this.getHelp().split("\n")[0];
		return this.brief;
	}
	
	public String getExample() {
		return this.example;
	}
	
	public String getArgumentation() {
		return this.argumentation;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public final void run(Context ctx, String[] args) {
		if (ctx.getGuild() == null) {
			if (this.guildOnly) {
				throw new GuildOnlyCommandException();
			}
		}
		
		// Verificador das permissões que o bot tem.
		List<Permission> forb = new ArrayList<>();
		for (Permission p : this.requiredPermissions) {
			if (!ctx.getSelfUserAsMember().hasPermission(p)) {
				forb.add(p);
			}
		}
		
		if (forb.size() > 0) {
			throw new MissingPermissionsException(forb.toArray(new Permission[0]));
		}
		
		// verificar as permissões do autor
		List<Permission> userForb = new ArrayList<>();
		for (Permission p : this.requiredUserPermissions) {
			if (!ctx.getAuthor().hasPermission(p)) {
				userForb.add(p);
			}
		}
		
		if (forb.size() > 0) {
			throw new UserMissingPermissionsException(forb.toArray(new Permission[0]));
		}
		
		this.execute(ctx, args);	
	}
}
