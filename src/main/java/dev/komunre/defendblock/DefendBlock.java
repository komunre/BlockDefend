package dev.komunre.defendblock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DefendBlock extends JavaPlugin {
	public static JavaPlugin plugin = null;
	
	@Override
	public void onEnable() {
		getLogger().info("Enabling block defense");
		
		plugin = this;
		try {
			getDataFolder().mkdir();
			String regsDir = this.getDataFolder().toString();
			Paths.get(regsDir, "regs_place.txt").toFile().createNewFile();
			Paths.get(regsDir, "regs_break.txt").toFile().createNewFile();
			Paths.get(regsDir, "regs_attack.txt").toFile().createNewFile();
			Paths.get(regsDir, "regs_interact.txt").toFile().createNewFile();
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Unable to create data folder with data files");
		}
		
		getServer().getPluginManager().registerEvents(new BlockDefenseListener(), this);
	}
	
	private boolean checkRegionCmd(Player ply, String fileName, String[] args) {
		if (args.length < 6) {
			ply.sendMessage("Please point all arguments");
			return false;
		}
		String data = "\n" + ply.getWorld().getUID().toString() + " " + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5];
		
		String regsDir = this.getDataFolder().toString();
		try {
			Files.write(Paths.get(regsDir, fileName), data.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			getLogger().log(Level.WARNING, "Error writing region to regs.txt");
			getLogger().log(Level.WARNING, e.getMessage());
		}
		
		return true;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player ply = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("addsafeplace") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_place.txt", args);
		}
		
		if (cmd.getName().equalsIgnoreCase("addsafebreak") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_break.txt", args);
		}
		
		if (cmd.getName().equalsIgnoreCase("addsafeattack") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_attack.txt", args);
		}
		
		if (cmd.getName().equalsIgnoreCase("addsafeinteract") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_interact.txt", args);
		}
		
		if (cmd.getName().equalsIgnoreCase("addsafeall") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_place.txt", args)
			&& checkRegionCmd(ply, "regs_break.txt", args)
			&& checkRegionCmd(ply, "regs_attack.txt", args)
			&& checkRegionCmd(ply, "regs_interact.txt", args);
		}
		
		if (cmd.getName().equalsIgnoreCase("addsafethree") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_place.txt", args)
			&& checkRegionCmd(ply, "regs_break.txt", args)
			&& checkRegionCmd(ply, "regs_attack.txt", args);
		}
		
		if (cmd.getName().equalsIgnoreCase("addsafeantiblock") && ply.hasPermission("blocks.regions.create")) {
			return checkRegionCmd(ply, "regs_place.txt", args)
			&& checkRegionCmd(ply, "regs_break.txt", args);
		}
		
		return false;
	}
	
	@Override
	public void onDisable() {
		
	}
}
