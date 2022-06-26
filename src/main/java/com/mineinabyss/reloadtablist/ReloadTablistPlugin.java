package com.mineinabyss.reloadtablist;

import com.google.inject.Inject;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

@Plugin(
        id = "reloadtablist",
        name = "ReloadTablist",
        version = BuildConstants.VERSION
)
public class ReloadTablistPlugin {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public ReloadTablistPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, ServerConnectedEvent.class, e ->
                server.getScheduler().buildTask(this, () -> {
                    logger.info("Correcting tablist...");
                    server.getCommandManager().executeAsync(server.getConsoleCommandSource(), "vtab reload");
                }).delay(2L, TimeUnit.SECONDS).schedule());

        server.getEventManager().register(this, DisconnectEvent.class, e ->
                server.getScheduler().buildTask(this, () -> {
                    logger.info("Correcting tablist...");
                    server.getCommandManager().executeAsync(server.getConsoleCommandSource(), "vtab reload");
                }).delay(2L, TimeUnit.SECONDS).schedule());
    }
}
