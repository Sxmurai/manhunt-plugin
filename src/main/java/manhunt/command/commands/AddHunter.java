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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Command.Cmd(name = "addhunter")
public class AddHunter extends Command {
    @Override
    public boolean execute(Context ctx) {
        if (!ctx.hasArgument(0)) {
            ctx.send("Invalid arguments! Please provide a target or 'none'");
            return false;
        }

        Player player = ctx.getPlayer(ctx.args.get(0));

        if (player == null) {
            ctx.sendf("%sThat is an invalid player provided!", ChatColor.RED);
            return false;
        }

        String result = Manhunt.addHunter(player);
        ctx.send(result);

        if (result.startsWith("Added")) {
            Manhunt.addCompassToPlayer(player);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void init(Server server) {
        Objects.requireNonNull(server.getPluginCommand(this.commandName)).setExecutor(this);
        Objects.requireNonNull(server.getPluginCommand(this.commandName)).setTabCompleter(this);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }
}
