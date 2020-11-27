package rodovia.cummy.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnviromentAdapter {
	private Dotenv env;
	
	public EnviromentAdapter() {
		this.env = Dotenv.configure()
				.filename("env")
				// .ignoreIfMissing()
				.load();
	}
	
	public String getToken() {
		return this.env.get("TOKEN");
	}
	
	public String getPrefix() {
		return this.env.get("PREFIX");
	}
}
