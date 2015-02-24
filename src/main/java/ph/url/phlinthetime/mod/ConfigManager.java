package ph.url.phlinthetime.mod;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigManager {

	private boolean isBeta = true;
	private int ticketsystemRedstoneOnTimeAfterCorrectTicket = 10;
	
	public boolean isBeta() {
		return isBeta;
	}

	private File f;
	
	private final String blocks = "BLOCKS";
	
	public ConfigManager(File suggestedConfigurationFile) {
		f = suggestedConfigurationFile;
	}

	public void loadConfig(){
		Configuration con = new Configuration(f);
		con.load();
		isBeta = con.get(con.CATEGORY_GENERAL, "BetaStuff", true, "Enables use of not yet finished, buggy beta stuff. Do not activate unless needed").getBoolean(true);
		ticketsystemRedstoneOnTimeAfterCorrectTicket = con.get(blocks, "TicketSystemRedstoneOnTime", ticketsystemRedstoneOnTimeAfterCorrectTicket, "Determines how many ticks (1/20 seconds) the redstone signal should be emitted from the ticketsystem block after clicking it with the right ticket.").getInt();
		con.save();
	}
	
	public int ticketsystemRedstoneDelay() {
		return ticketsystemRedstoneOnTimeAfterCorrectTicket;
	}
	
}
