package lobby.lobby.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lobby.lobby.Lobby;
import lobby.lobby.apis.ItemAPI;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.DataOutputStream;

public class LobbyListeners implements Listener {

    Location lobbyLocation = new Location(Bukkit.getWorld("lobby"),508.49803,58.500,-617.37778,0,0);

    @EventHandler
    public void aoLogar(PlayerJoinEvent event){
        event.setJoinMessage(null);

        World world = event.getPlayer().getWorld();
        Player player = event.getPlayer();


        customWorldSettings(player,world);
        ItemStack sword = ItemAPI.newItem(Material.DIAMOND_SWORD,ChatColor.BLUE+"Modos de jogo",1,0,ChatColor.DARK_PURPLE+"Veja os modos de jogo");
        player.getInventory().clear();
        player.getInventory().setItem(0,sword);

        player.teleport(lobbyLocation);
        player.playSound(player.getLocation(), Sound.CAT_MEOW,10,5);
        player.sendMessage(ChatColor.UNDERLINE+"");
        player.sendMessage(ChatColor.RED+"      Seja bem vindo ao Lobby do TeaxeyPractice   ");
        player.sendMessage(ChatColor.UNDERLINE+"");
    }

    @EventHandler
    public void aoSair(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }

    @EventHandler
    public void placeEvent(BlockPlaceEvent event){
        if(!(event.getPlayer().isOp())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void breakEvent(BlockBreakEvent event){
        if(!(event.getPlayer().isOp())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noThundering(ThunderChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void clickEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(player.getItemInHand().getType() == Material.DIAMOND_SWORD){
                createInventoryLobby(player);
            }
        }
    }

    @EventHandler
    public void noDropping(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void changeInventory(InventoryClickEvent event){

        if(!(event.getWhoClicked().isOp())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void clickInventoryEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem().getType() == Material.DIAMOND_SWORD){
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("practice-01");
            player.sendPluginMessage(Lobby.instance, "BungeeCord", out.toByteArray());
        }
    }


    // METHODS ------------------|

    public void createInventoryLobby(Player player){
        Inventory inventory = Bukkit.createInventory(null,27,ChatColor.RED+"SELECAO DE SERVIDORES");

        // ITEM NO 20
        ItemStack glass = ItemAPI.newItem(Material.GLASS,null,1);
        ItemStack sword = ItemAPI.newItem(Material.DIAMOND_SWORD,ChatColor.BLUE+"PRACTICE",1,0,ChatColor.DARK_PURPLE+"Clique para entrar");
        ItemStack wool = ItemAPI.newItem(Material.WOOL,ChatColor.RED+"EM BREVE");

        for(int i = 0; i <= 26;i++){
            if(!(i == 11 || i == 13 || i == 15)) {
                inventory.setItem(i,glass);
            }
            inventory.setItem(13,wool);
            inventory.setItem(15,wool);
        }
        inventory.setItem(11,sword);
        player.openInventory(inventory);
    }

    public void customWorldSettings(Player player, World world){
        player.setFoodLevel(20);
        player.setSaturation(14);
        player.setHealth(20);
        player.setFallDistance(-500);
        player.setGameMode(GameMode.ADVENTURE);

        world.setAnimalSpawnLimit(0);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setMonsterSpawnLimit(0);
        world.setPVP(false);
        world.setFullTime(9999999999999L);
    }

    @EventHandler
    public void moveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(player.getLocation().getY() < 0){
            player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,10,5);
            player.teleport(lobbyLocation);
        }
    }
}
