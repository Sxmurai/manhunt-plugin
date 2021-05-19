package manhunt.events;

import manhunt.Manhunt;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerInteractionListener implements Listener {
    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        if (event.hasItem()) {
            if (Objects.requireNonNull(event.getItem()).getType() != Material.COMPASS) {
                return;
            }

            if (Manhunt.getTarget() == null) {
                return;
            }

            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                event.getPlayer().setCompassTarget(Manhunt.getTarget().getLocation());
            }
        }
    }
}
