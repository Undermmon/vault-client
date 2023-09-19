/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package me.undermon;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.Switch;

@Command({"vault-client-economy", "vce"})
public class EconomyCommands {
	private static final String OWNER = "owner";
	private static final String TARGET = "target balance";
	private static final String WORLD = "world";
	private static final String AMOUNT = "amount";
	private static final String BANK_NAME = "bank name";
	private static final String IS_PLAYER_SWITCH = "offlineplayer";
	private Economy vaultHook;

	public EconomyCommands(Economy vaultHook) {
		this.vaultHook = vaultHook;
	}

	@Subcommand("isEnabled")
	public String isEnabled() {
		return Boolean.toString(this.vaultHook.isEnabled());
	}

	@Subcommand("getName")
	public String getName() {
		return this.vaultHook.getName();
	}

	@Subcommand("hasbankSupport")
	public String hasBankSupport() {
		return Boolean.toString(this.vaultHook.hasBankSupport());
	}

	@Subcommand("fractionalDigits")
	public String fractionalDigits() {
		return Integer.toString(this.vaultHook.fractionalDigits());
	}

	@Subcommand("format")
	public String format(@Named(AMOUNT) double amount) {
		return this.vaultHook.format(amount);
	}

	@Subcommand("currencyNamePlural")
	public String currencyNamePlural() {
		return this.vaultHook.currencyNamePlural();
	}

	@Subcommand("currencyNameSingular")
	public String currencyNameSingular() {
		return this.vaultHook.currencyNameSingular();
	}

	@SuppressWarnings("deprecation")
	@Subcommand("hasAccount")
	public String hasAccount(@Named(TARGET) String target, @Named(WORLD) @Optional String world, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		Boolean result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = (world == null) ? this.vaultHook.hasAccount(player) : this.vaultHook.hasAccount(player, world);
		} else {

			result = (world == null) ? this.vaultHook.hasAccount(target) : this.vaultHook.hasAccount(target, world);
		}

		return result.toString();
	}

	@SuppressWarnings("deprecation")
	@Subcommand("getBalance")
	public String getBalance(@Named(TARGET) String target, @Named(WORLD) @Optional String world, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		Double result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = (world == null) ? this.vaultHook.getBalance(player) : this.vaultHook.getBalance(player, world);
		} else {

			result = (world == null) ? this.vaultHook.getBalance(target) : this.vaultHook.getBalance(target, world);
		}

		return result.toString();
	}

	@SuppressWarnings("deprecation")
	@Subcommand("has")
	public String has(@Named(TARGET) String target, @Named(AMOUNT) double amount, @Named(WORLD) @Optional String world, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		Boolean result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = (world == null) ? this.vaultHook.has(player, amount) : this.vaultHook.has(player, world, amount);
		} else {

			result = (world == null) ? this.vaultHook.has(target, amount) : this.vaultHook.has(target, world, amount);
		}

		return result.toString();
	}

	@SuppressWarnings("deprecation")
	@Subcommand("withdrawPlayer")
	public EconomyResponse withdrawPlayer(@Named(TARGET) String target, @Named(AMOUNT) double amount, @Named(WORLD) @Optional String world, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		EconomyResponse result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = (world == null) ? this.vaultHook.withdrawPlayer(player, amount) : this.vaultHook.withdrawPlayer(player, world, amount);
		} else {

			result = (world == null) ? this.vaultHook.withdrawPlayer(target, amount) : this.vaultHook.withdrawPlayer(target, world, amount);
		}

		return result;
	}
	
	@SuppressWarnings("deprecation")
	@Subcommand("depositPlayer")
	public EconomyResponse depositPlayer(@Named(TARGET) String target, @Named(AMOUNT) double amount, @Named(WORLD) @Optional String world,
		@Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
	
		EconomyResponse result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = (world == null) ? this.vaultHook.depositPlayer(player, amount) : this.vaultHook.depositPlayer(player, world, amount);
		} else {

			result = (world == null) ? this.vaultHook.depositPlayer(target, amount) : this.vaultHook.depositPlayer(target, world, amount);
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	@Subcommand("createBank")
	public EconomyResponse createBank(@Named(BANK_NAME) String bankName, @Named(OWNER) String owner,
		@Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {

		EconomyResponse result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(owner);

			result = this.vaultHook.createBank(bankName, player);
		} else {

			result = this.vaultHook.createBank(bankName, owner);
		}

		return result;
	}

	@Subcommand("deleteBank")
	public EconomyResponse deleteBank(@Named(BANK_NAME) String bankName) {
		return this.vaultHook.deleteBank(bankName);
	}

	@Subcommand("bankBalance")
	public EconomyResponse bankBalance(@Named(BANK_NAME) String bankName) {
		return this.vaultHook.bankBalance(bankName);
	}

	@Subcommand("bankHas")
	public EconomyResponse bankHas(@Named(BANK_NAME) String bankName, @Named(AMOUNT) double amount) {
		return this.vaultHook.bankHas(bankName, amount);
	}

	@Subcommand("bankWithdraw")
	public EconomyResponse bankWithdraw(@Named(BANK_NAME) String bankName, @Named(AMOUNT) double amount) {
		return this.vaultHook.bankWithdraw(bankName, amount);
	}

	@Subcommand("bankDeposit")
	public EconomyResponse bankDeposit(@Named(BANK_NAME) String bankName, @Named(AMOUNT) double amount) {
		return this.vaultHook.bankDeposit(bankName, amount);
	}

	@SuppressWarnings("deprecation")
	@Subcommand("isBankOwner")
	public EconomyResponse isBankOwner(@Named(BANK_NAME) String bankName, @Named(TARGET) String target, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		EconomyResponse result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = this.vaultHook.isBankOwner(bankName, player);
		} else {

			result = this.vaultHook.isBankOwner(bankName, target);
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	@Subcommand("isBankMember")
	public EconomyResponse isBankMember(@Named(BANK_NAME) String bankName, @Named(TARGET) String target, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		EconomyResponse result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(bankName);

			result = this.vaultHook.isBankMember(bankName, player);
		} else {

			result = this.vaultHook.isBankMember(bankName, target);
		}

		return result;
	}

	@Subcommand("getBanks")
	public String getBanks() {
		return this.vaultHook.getBanks().toString();
	}
	
	@SuppressWarnings("deprecation")
	@Subcommand("createPlayerAccount")
	public String createPlayerAccount(@Named(TARGET) String target, @Named(WORLD) @Optional String world, @Switch(IS_PLAYER_SWITCH) boolean isOfflinePlayer) {
		Boolean result;

		if (isOfflinePlayer) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(target);

			result = (world == null) ? this.vaultHook.createPlayerAccount(player) : this.vaultHook.createPlayerAccount(player, world);
		} else {

			result = (world == null) ? this.vaultHook.createPlayerAccount(target) : this.vaultHook.createPlayerAccount(target, world);
		}

		return result.toString();
	}
}
