package rodovia.cummy.threads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;

/**
 * troca a atividade do cummy
 * a cada 50 segundos.
 * @author rodovia
 */
public class ActivityThread extends Thread {
	private Logger LOGGER = LoggerFactory.getLogger(ActivityThread.class);
	private List<JSONObject> activities;
	private JDA api;
	private volatile boolean flag = true;
	private int currentIndex = 0;
	
	public ActivityThread(JDA jda) {
		this.api = jda;
		this.setName("Activity Thread");
	}
	
	public void kill() {
		this.flag = false;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		super.run();
		
		while(flag) {
			try {
				this.setPresence();
				LOGGER.debug("Presence set to " + this.api.getPresence().getActivity().getName());
			} catch(Throwable e) {
				LOGGER.error("Was not possible to set presence due to an " + e.getClass().getName());
				e.printStackTrace();
				this.kill();
			}
			
			try {
				this.sleep(50000L);
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}
		}
		
	}
	
	private void setPresence() throws IOException {
		this.readActivities();
		JSONObject nex = this.activities.get(currentIndex);
		currentIndex++;
		if (currentIndex >= this.activities.size()) {
			currentIndex = 0;
		}
		
		Activity ack = Activity.of(ActivityType.fromKey(nex.getInt("type")), nex.getString("name"));
		this.api.getPresence().setActivity(ack);
	}
	
	private void readActivities() throws IOException {
		JSONObject obj;
		
		String fileContent = Files.readString(Path.of("src/main/resources/activities.json"));
		obj = new JSONObject(fileContent);
		
		JSONArray activities = obj.getJSONArray("activities");
		
		List<JSONObject> js = new ArrayList<>(); 
		for (int c = 0; c < activities.length(); c++) {
			js.add(activities.optJSONObject(c));
		}

		this.activities = js;
	}
	
}
