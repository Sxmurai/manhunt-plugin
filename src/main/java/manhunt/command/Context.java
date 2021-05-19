package manhunt.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Context {
    public CommandSender sender;
    public org.bukkit.command.Command command;
    public String label;
    public List<String> args;

    public Context(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        this.sender = commandSender;
        this.command = command;
        this.label = label;
        this.args = Arrays.asList(args);
    }

    public void send(String message) {
        this.sender.sendMessage(message);
    }

    public void sendf(String mesage, Object... args) {
        this.send(String.format(mesage, args));
    }

    public Player getPlayer(String search) {
        return Bukkit.getPlayer(search);
    }

    public boolean hasArgument(int index) {
        try {
            this.args.get(index);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
