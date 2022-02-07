package lobby.lobby;


import lobby.lobby.listeners.LobbyListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {


    public static Lobby instance;

    public static Lobby getInstance(Class<Lobby> lobbyClass) {
        return instance;
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Plugin LOBBY-1.0 iniciado! Feito por Kinox");
        instance = this;
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
    }


    public void registerEvents(){
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new LobbyListeners(),this);
    }
    public void registerCommands(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }
}
