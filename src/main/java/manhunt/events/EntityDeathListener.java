package manhunt.events;

import manhunt.Manhunt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (Manhunt.getHunters().contains(event.getEntity())) {
            Manhunt.addCompassToPlayer((Player) event.getEntity());
        }
    }
}
