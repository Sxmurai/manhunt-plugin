package manhunt.command.commands;

import manhunt.Manhunt;
import manhunt.command.Command;
import manhunt.command.Context;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Command.Cmd(name = "listhunters", aliases = {"allhunters", "lshunters"})
public class ListHunters extends Command {
    @Override
    public boolean execute(Context ctx) {
        final ArrayList<Player> hunters = Manhunt.getHunters();

        if (hunters.isEmpty()) {
            ctx.send("There are no hunters.");
            return false;
        }

        ctx.sendf("Here are the current hunters:\n%s", hunters.stream().map(HumanEntity::getName).collect(Collectors.joining(", ")));
        return true;
    }

    @Override
    public void init(Server server) {
        Objects.requireNonNull(server.getPluginCommand(this.commandName)).setExecutor(this);
    }
}
