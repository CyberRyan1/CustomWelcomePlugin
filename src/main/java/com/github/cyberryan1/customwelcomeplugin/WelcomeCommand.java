package com.github.cyberryan1.customwelcomeplugin;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class WelcomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        // Enabling/disabling welcome messages to the sender
        if ( args.length == 0 || Bukkit.getOfflinePlayer( args[0] ) == null ) {
            // Making sure the command sender is a player
            if ( sender instanceof Player == false ) {
                sender.sendMessage( MiniMessage.miniMessage().deserialize( "<gray>You must specify a target!</gray>" ) );
                return true;
            }

            OfflinePlayer target = ( OfflinePlayer ) sender;
            boolean enabled = toggleWelcome( target );
            if ( enabled ) {
                sender.sendMessage( MiniMessage.miniMessage()
                        .deserialize( "<green>Enabled</green> welcomes for <aqua>" + target.getName() + "</aqua>" ) );
            }
            else {
                sender.sendMessage( MiniMessage.miniMessage()
                        .deserialize( "<red>Disabled</red> welcomes for <aqua>" + target.getName() + "</aqua>" ) );
            }
        }

        // Enabling/disabling welcome messages to the target
        else {
            OfflinePlayer target = Bukkit.getOfflinePlayer( args[0] );
            boolean enabled = toggleWelcome( target );
            if ( enabled ) {
                sender.sendMessage( MiniMessage.miniMessage()
                        .deserialize( "<green>Enabled</green> welcomes for <aqua>" + target.getName() + "</aqua>" ) );
            }
            else {
                sender.sendMessage( MiniMessage.miniMessage()
                        .deserialize( "<red>Disabled</red> welcomes for <aqua>" + target.getName() + "</aqua>" ) );
            }
        }

        return true;
    }

    // Returns true if the welcome message is now enabled for the target, false otherwise
    private boolean toggleWelcome( OfflinePlayer target ) {
        // Enabling welcomes for the target
        if ( CustomWelcomePlugin.getPlugin().getData().get( "welcome." + target.getUniqueId().toString() ) == null
                || CustomWelcomePlugin.getPlugin().getData().getBoolean( "welcome." + target.getUniqueId().toString() ) == false ) {
            CustomWelcomePlugin.getPlugin().getData().set( "welcome." + target.getUniqueId().toString(), true );

            // Save the new data
            try {
                CustomWelcomePlugin.getPlugin().getData().save( CustomWelcomePlugin.getPlugin().getDataFile() );
            } catch ( IOException e ) { e.printStackTrace(); }
            CustomWelcomePlugin.getPlugin().reloadConfig();

            return true;
        }

        // Disabling welcomes for the target
        else {
            CustomWelcomePlugin.getPlugin().getData().set( "welcome." + target.getUniqueId().toString(), false );

            // Save the new data
            try {
                CustomWelcomePlugin.getPlugin().getData().save( CustomWelcomePlugin.getPlugin().getDataFile() );
            } catch ( IOException e ) { e.printStackTrace(); }
            CustomWelcomePlugin.getPlugin().reloadConfig();

            return false;
        }


    }
}