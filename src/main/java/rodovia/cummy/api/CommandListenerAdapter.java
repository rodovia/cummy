package rodovia.cummy.api;

public abstract class CommandListenerAdapter {
	public void onCommand(Context ctx, Command command) {  }
	public void onCommandError(Throwable t, Context ctx, Command cmd) {  }
}
