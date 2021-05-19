package manhunt.command;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {
    protected String commandName;
    protected List<String> aliases;

    public Command() {
        if (!this.getClass().isAnnotationPresent(Cmd.class)) {
            System.out.println("Command did not have Command.Cmd annotation.");
            return;
        }

        Cmd command = this.getClass().getDeclaredAnnotation(Cmd.class);

        this.commandName = command.name();
        this.aliases = Arrays.asList(command.aliases());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        Context context = new Context(sender, command, label, args);

        if (!this.commandName.equalsIgnoreCase(command.getName()) || !this.aliases.contains(command.getName().toLowerCase())) {
            context.send("Invalid command! Run /help");
            return false;
        }

        if (!sender.isOp()) {
            context.send("You must have operator status to run this command!");
            return false;
        }

        return this.execute(context);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }

    public abstract boolean execute(Context ctx);
    public abstract void init(Server server);

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Cmd {
        String name();
        String[] aliases() default {};
    }
}
