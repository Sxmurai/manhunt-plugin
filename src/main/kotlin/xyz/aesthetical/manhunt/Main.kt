package xyz.aesthetical.manhunt

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import xyz.aesthetical.manhunt.commands.Hunter

class Main : JavaPlugin(), Listener {
  private val hunter = Hunter()
  
  override fun onEnable() {
    server.pluginManager.registerEvents(this, this)
    server.getPluginCommand("hunter")?.setExecutor(hunter)
    server.getPluginCommand("hunter")?.tabCompleter = hunter
  }
  
  override fun onDisable() {
    println("AestheticalXD has been disabled.")
  }
  
  @EventHandler
  fun onQuit(event: PlayerQuitEvent) {
    val player = event.player
    
    if (hunter.hunters.contains(player.uniqueId)) {
      hunter.hunters.remove(player.uniqueId)
  
      server.logger.info(
        "${player.name} left the server, so I've removed them from the hunters list."
      )
    }
    
    if (hunter.target == player.uniqueId) {
      hunter.target = null
  
      server.logger.info(
        "${player.name} left the server, so they're no longer a target"
      )
    }
  }
  
  @EventHandler
  fun onPlayerInteraction(event: PlayerInteractEvent) {
    val item = event.item
    
    if (item?.type != Material.COMPASS) {
      return
    } else {
      if (hunter.target == null) {
        event.player.sendMessage(
          "${ChatColor.RED}There is nobody to point to!"
        )
        
        return
      }
      
      if (event.action == Action.RIGHT_CLICK_AIR) {
        val target = Bukkit.getPlayer(hunter.target!!)
        event.player.compassTarget = target!!.location
  
        event.player.sendMessage(
          "${ChatColor.GREEN}Pointing to ${target.name}"
        )
      }
    }
  }
}