package dev.komunre.defendblock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.util.permissions.DefaultPermissions;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockDefenseListener implements Listener {
	@EventHandler
	public boolean onBlockBreak(BlockBreakEvent event) {
		World world = event.getPlayer().getWorld();
		
		if (!checkPermission(world, "regs_break.txt", event.getPlayer())) {
			event.setCancelled(true);
			return false;
		}
		return true;
	}
	
	private boolean checkPermission(World world, String fileName, Entity ply) {
		String regsDir = DefendBlock.plugin.getDataFolder().toString();
		List<String> regsData = null;
		try {
			regsData = Files.readAllLines(Paths.get(regsDir, fileName));
		} catch (IOException e) {
			DefendBlock.plugin.getLogger().log(Level.WARNING, "Unable to read lines from regs.txt");
			DefendBlock.plugin.getLogger().log(Level.WARNING, e.getMessage());
		}
		
		if (regsData == null) {
			return false;
		}
		
		if (ply.hasPermission("blocks.manipulate")) {
			return true;
		}
		
		Location loc = ply.getLocation();
		for (String line : regsData) {
			String[] data = line.split(" ");
			if (data[0].equals(world.getUID().toString())) {
				DefendBlock.plugin.getLogger().info("Found region with player's world UUID");
				if ((loc.getBlockX() >= Integer.parseInt(data[1]) && loc.getBlockY() >= Integer.parseInt(data[2]) && loc.getBlockZ() >= Integer.parseInt(data[3])) && (loc.getBlockX() <= Integer.parseInt(data[4]) && loc.getBlockY() <= Integer.parseInt(data[5]) && loc.getBlockZ() <= Integer.parseInt(data[6]))) {
					DefendBlock.plugin.getLogger().info("Preventing player " + ply.getName() + " from manipulating block in world: " + ply.getWorld().getUID().toString());
					return false;
				}
			}
		}
		
		return true;
	}
	
	@EventHandler
	public boolean onBlockPlace(BlockPlaceEvent event) {
		World world = event.getPlayer().getWorld();
		
		if (!checkPermission(world, "regs_place.txt", event.getPlayer())) {
			event.setCancelled(true);
			return false;
		}
		return true;
	}
	
	@EventHandler
	public boolean onEntityDamage(EntityDamageByEntityEvent event) {
		if (!checkPermission(event.getEntity().getWorld(), "regs_attack.txt", event.getEntity())) {
			event.setCancelled(true);
			return false;
		}
		return true;
	}
	
	@EventHandler
	public boolean onEntityInteract(PlayerInteractEvent event) {
		if (!checkPermission(event.getPlayer().getWorld(), "regs_interact.txt", event.getPlayer())) {
			event.setCancelled(true);
			return false;
		}
		return true;
	}
}
