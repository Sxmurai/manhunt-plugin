package manhunt.command.commands;

import manhunt.Manhunt;
import manhunt.command.Command;
import manhunt.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Command.Cmd(name = "settarget", aliases = {"starget"})
public class SetTarget extends Command {
    @Override
    public boolean execute(Context ctx) {
        if (!ctx.hasArgument(0)) {
            ctx.send("Invalid arguments! Please provide a target or 'none'");
            return false;
        }

        if (ctx.args.get(0).equalsIgnoreCase("none")) {
            Manhunt.setHunter(null);
            ctx.send("Reset the hunter successfully!");

            return true;
        }

        Player player = ctx.getPlayer(ctx.args.get(0));

        if (player == null) {
            ctx.send("Couldn't find user based off arguments");
            return false;
        }

        Manhunt.setHunter(player);

        ctx.sendf("Set the hunter to %s%s%s", ChatColor.GREEN, player.getName(), ChatColor.RESET);
        return true;
    }

    @Override
    public void init(Server server) {
        Objects.requireNonNull(server.getPluginCommand(this.commandName)).setExecutor(this);
        Objects.requireNonNull(server.getPluginCommand(this.commandName)).setTabCompleter(this);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> targets = new ArrayList<>(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        targets.add("none");

        return targets;
    }
}
