package xyz.aesthetical.manhunt.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Hunter : CommandExecutor, TabCompleter {
  val hunters = mutableListOf<UUID>()
  var target: UUID? = null
  
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    return if (command.name != "hunter") {
      false
    } else {
      if (args.isEmpty()) {
        invalid(sender)
        return true
      }
      
      if (
        listOf("add", "remove", "target").contains(args[0].toLowerCase()) &&
        args.toList().minus(args[0]).isEmpty()
      ) {
        invalid(sender)
        
        return true
      }
      
      when (args[0].toLowerCase()) {
        "add" -> add(sender, Bukkit.getPlayer(args[1]))
        "remove" -> remove(sender, Bukkit.getPlayer(args[1]))
        "list" -> list(sender)
        "target" -> target(sender, Bukkit.getPlayer(args[1]))
        else -> invalid(sender)
      }
      
      true
    }
  }
  
  override fun onTabComplete(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: Array<out String>
  ): MutableList<String> {
    if (args.size == 1) {
      return mutableListOf("list", "target", "add", "remove")
    }
    
    if (args.size == 2) {
      return mutableListOf(* Bukkit.getOnlinePlayers().map { it.name }.toTypedArray())
    }
    
    return mutableListOf()
  }
  
  private fun invalid(sender: CommandSender): Boolean {
    sender.sendMessage(
      "${ChatColor.RED}${sender.name}, invalid arguments provided! Run /help hunter"
    )
    
    return true
  }
  
  private fun target(sender: CommandSender, player: Player?): Boolean {
    if (player == null) {
      sender.sendMessage(
        "${ChatColor.RED}${sender.name}, that player does not exist."
      )
    
      return true
    }
    
    return if (hunters.contains(player.uniqueId)) {
      sender.sendMessage(
        "${ChatColor.RED}${sender.name}, ${player.name} is a hunter. Please remove them to make them a target."
      )
      
      true
    } else {
      if (target == player.uniqueId) {
        sender.sendMessage(
          "${ChatColor.RED}${sender.name}, ${player.name} is already the target."
        )
        
        return true
      }
      
      target = player.uniqueId
      
      sender.sendMessage(
        "${ChatColor.GREEN}${sender.name}, ${player.name} is now the target."
      )
      
      true
    }
  }
  
  
  private fun list(sender: CommandSender): Boolean {
    return if (hunters.isEmpty()) {
      sender.sendMessage(
        "${ChatColor.RED}${sender.name}, there are no hunters."
      )
      
      true
    } else {
      sender.sendMessage(
        "${ChatColor.GREEN}There ${
          if (hunters.size > 1) "are" else "is"
        } ${hunters.size} hunter${
          if (hunters.size > 1) "s" else ""
        }.\n\n\n${hunters.joinToString(",\n") {
          Bukkit.getPlayer(it)?.name.toString()
        }}"
      )
      
      true
    }
  }
  
  private fun add(sender: CommandSender, player: Player?): Boolean {
    if (player == null) {
      sender.sendMessage(
        "${ChatColor.RED}${sender.name}, that player does not exist."
      )
      
      return true
    }
    
    return if (hunters.contains(player.uniqueId)) {
      sender.sendMessage(
        "${ChatColor.RED}${sender.name}, ${player.name} is already a hunter."
      )
  
      true
    } else {
      hunters.add(player.uniqueId)
  
      sender.sendMessage(
        "${ChatColor.GREEN}${sender.name}, added ${player.name} as a hunter."
      )
  
      true
    }
  }
  
  private fun remove(sender: CommandSender, player: Player?): Boolean {
    if (player == null) {
      sender.sendMessage(
        "${ChatColor.RED} ${sender.name}, that player does not exist."
      )
    
      return true
    }
    
    return if (hunters.contains(player.uniqueId)) {
      hunters.remove(player.uniqueId)
      
      sender.sendMessage(
        "${ChatColor.GREEN}${sender.name}, removed ${player.name} as a hunter."
      )
      
      player.sendMessage(
        "${ChatColor.YELLOW}Alert! You are no longer a hunter."
      )
      
      true
    } else {
      sender.sendMessage(
        "${ChatColor.RED}${sender.name}, ${player.name} is not a hunter."
      )
      
      true
    }
  }
}