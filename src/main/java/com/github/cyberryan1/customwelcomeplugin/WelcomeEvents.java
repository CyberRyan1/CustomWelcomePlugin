package com.github.cyberryan1.customwelcomeplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WelcomeEvents implements Listener {

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        // Sending the welcome message to the player
        if ( CustomWelcomePlugin.getPlugin().getData().getBoolean( "welcome." + event.getPlayer().getUniqueId().toString() ) ) {
            Component message = LegacyComponentSerializer.legacyAmpersand().deserialize(
                    CustomWelcomePlugin.getPlugin().getConfig().getString( "welcome.message" )
            );
            event.getPlayer().sendMessage( message );
        }

        // Replacing the default join message with the broadcast specified in the config
        event.joinMessage( LegacyComponentSerializer.legacyAmpersand().deserialize(
                CustomWelcomePlugin.getPlugin().getConfig().getString( "welcome.broadcast" )
                        .replace( "[PLAYER]", event.getPlayer().getName() ) ) );
    }
}