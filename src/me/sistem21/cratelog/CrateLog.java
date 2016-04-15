package me.sistem21.cratelog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CrateLog extends JavaPlugin {

	public void onEnable() { saveDefaultConfig(); }

	public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cratelog")) {
			if (cs.hasPermission("cratelog.log")) {
				if (args.length < 1) {
					cs.sendMessage("§cWhat do you want to log? Use §7/cratelog <args>");
					return false;
				}
				
				Date now = new Date();
				SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
				
				String format = getConfig().getString("log-format");
				String message = "";
				for (int i = 0; i < args.length; i++) {
					message += args[i] + " ";
				}

				format = format.replaceAll("\\{message\\}", message);
				format = format.replaceAll("\\{date\\}", date.format(now));
				format = format.replaceAll("\\{time\\}", time.format(now));
				log(format);
				cs.sendMessage("§aYour message was logged!");
				return true;
			}
			
			cs.sendMessage("§cYou don't have enough permissions to execute that command!");
		}
		return false;
	}

	private void log(String message) {
		File logFile = new File("plugins" + File.separator + "log.txt");
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			FileWriter fw = new FileWriter(logFile, true);
			PrintWriter pw = new PrintWriter(fw);

			pw.println(message + "\n");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			getLogger().warning("§cThere was an error during logging!");
			e.printStackTrace();
		}

	}

}
