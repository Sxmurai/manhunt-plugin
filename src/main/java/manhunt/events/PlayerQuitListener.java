package manhunt.events;

import manhunt.Manhunt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Manhunt.getHunters().contains(event.getPlayer())) {
            Manhunt.removeHunter(event.getPlayer());
            return;
        }

        if (Manhunt.getTarget() != null && Manhunt.getTarget() == event.getPlayer()) {
            Manhunt.setHunter(null);

            for (Player player : Manhunt.getHunters()) {
                player.sendMessage("The hunter has left, someone will need to set the target again.");
            }
        }
    }
}
