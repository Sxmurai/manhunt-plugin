package manhunt;

import manhunt.command.commands.AddHunter;
import manhunt.command.commands.ListHunters;
import manhunt.command.commands.RemoveHunter;
import manhunt.command.commands.SetTarget;
import manhunt.events.EntityDeathListener;
import manhunt.events.PlayerInteractionListener;
import manhunt.events.PlayerQuitListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Manhunt extends JavaPlugin implements Listener {
    private static final ArrayList<Player> hunters = new ArrayList<>();
    private static Player target;

    @Override
    public void onEnable() {
        getServer().getLogger().info("Starting the manhunt plugin...");

        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

        getServer().getLogger().info("Registered all events!");

        new AddHunter().init(getServer());
        new ListHunters().init(getServer());
        new RemoveHunter().init(getServer());
        new SetTarget().init(getServer());

        getServer().getLogger().info("Registered all commands!");
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("Disabled the Manhunt plugin!");
    }

    public static void addCompassToPlayer(Player player) {
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
    }

    public static String addHunter(Player player) {
        if (hunters.contains(player)) {
            return "That player is already a hunter! Use /removehunter <player>";
        }

        hunters.add(player);

        return String.format("Added %s%s%s to the hunter list!", ChatColor.GREEN, player.getName(), ChatColor.RESET);
    }

    public static String removeHunter(Player player) {
        if (!hunters.contains(player)) {
            return "That player isn't a hunter1 Use /addhunter <player>";
        }

        hunters.remove(player);

        return String.format("Removed %s%s%s from the hunter list!", ChatColor.GREEN, player.getName(), ChatColor.RESET);
    }

    public static void setHunter(Player player) {
        if (player == null) {
            target = null;
            return;
        }

        target = player;
    }

    public static ArrayList<Player> getHunters() {
        return hunters;
    }

    public static Player getTarget() {
        return target;
    }
}
