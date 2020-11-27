package rodovia.cummy.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
	private Map<String, Command> commands;
	private List<CommandListenerAdapter> listeners;
	
	public CommandManager() {
		 this.commands 	= new HashMap<>();
		 this.listeners = new ArrayList<>();
	}
	
	public synchronized void  addCommand(Command command) {
		this.commands.put(command.getName(), command);
	}
	
	public synchronized void addCommands(Command... commands) {
		for (Command command : commands) {
			this.addCommand(command);
		}
	}
	
	public Collection<Command> getCommands() {
		return this.commands.values();
	}
	
	public void executeCommandEvent(Context ctx, Command command) {
		for (CommandListenerAdapter c : this.listeners) {
			c.onCommand(ctx, command);
		}
	}
	
	public void executeCommandEvent(Context ctx, Command command, Throwable t) {
		for (CommandListenerAdapter c : this.listeners) {
			c.onCommandError(t, ctx, command);
		}
	}
	
	public void addListener(CommandListenerAdapter listener) {
		this.listeners.add(listener);
	}
	
	public Command getCommand(String query) {
		return this.commands.get(query);
	}
}
