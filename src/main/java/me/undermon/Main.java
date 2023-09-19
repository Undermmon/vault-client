/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package me.undermon;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.process.ResponseHandler;

public class Main extends JavaPlugin {
	private BukkitCommandHandler commandHandler;

	@Override
	public void onEnable() {
		PluginManager pluginManager = this.getServer().getPluginManager();

		if (pluginManager.getPlugin("Vault") == null) {
			this.getLogger().severe("Dependency Vault not found. Disabling plugin.");
			pluginManager.disablePlugin(this);
		} else {
			var serviceProvider = this.getServer().getServicesManager().getRegistration(Economy.class);

			if (serviceProvider == null) {
				this.getLogger().severe("No service provider found for Vault economy. Disabling plugin.");
				pluginManager.disablePlugin(this);
			} else {
				this.registerCommands(serviceProvider.getProvider());
			}
		}
	}

	private void registerCommands(Economy vaultHook) {
		this.commandHandler = BukkitCommandHandler.create(this);
		commandHandler.registerResponseHandler(String.class, ResponseHandler::reply);
		
		commandHandler.registerResponseHandler(EconomyResponse.class,(response, actor, command) ->
			actor.reply("EconomyResponse[amount=%s, balance=%s, type=%s, errorMessage=%s]".
				formatted(response.amount, response.balance, response.type, response.errorMessage))
		);

		commandHandler.register(new EconomyCommands(vaultHook));
	}
}
