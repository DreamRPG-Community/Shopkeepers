package com.nisovin.shopkeepers.shopobjects.external;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.Objects;

import com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData;
import com.nisovin.shopkeepers.api.shopkeeper.Shopkeeper;
import com.nisovin.shopkeepers.api.shopobjects.ExternalShopObjectType;
import com.nisovin.shopkeepers.shopkeeper.AbstractShopkeeper;
import com.nisovin.shopkeepers.shopobjects.AbstractShopObjectType;
import com.nisovin.shopkeepers.util.Utils;

/**
 * Internal adapter that exposes an external object type to Shopkeepers'
 * object registry.
 */
public final class SKExternalShopObjectType extends AbstractShopObjectType<SKExternalShopObject> {

	private final ExternalShopObjectType externalType;

	public SKExternalShopObjectType(ExternalShopObjectType externalType) {
		super(Objects.requireNonNull(externalType, "External shop object type is null!").getIdentifier(),
				externalType.getPermission());
		this.externalType = externalType;
	}

	public ExternalShopObjectType getExternalType() {
		return externalType;
	}

	@Override
	public SKExternalShopObject createObject(AbstractShopkeeper shopkeeper, ShopCreationData creationData) {
		return new SKExternalShopObject(this, shopkeeper, creationData);
	}

	@Override
	public boolean needsSpawning() {
		return externalType.needsSpawning();
	}

	@Override
	public boolean isValidSpawnBlockFace(Block targetBlock, BlockFace targetBlockFace) {
		return externalType.isValidSpawnBlockFace(targetBlock, targetBlockFace);
	}

	@Override
	public boolean isValidSpawnBlock(Block spawnBlock) {
		return externalType.isValidSpawnBlock(spawnBlock);
	}

	@Override
	public boolean isEnabled() {
		return externalType.isEnabled();
	}

	@Override
	public boolean hasPermission(Player player) {
		return externalType.hasPermission(player);
	}

	@Override
	public boolean matches(String identifier) {
		return externalType.matches(identifier);
	}

	@Override
	protected void onSelect(Player player) {
		if (externalType.getPermission() != null && !externalType.hasPermission(player)) {
			Utils.sendMessage(player, "You do not have permission to use this shop object type.");
		}
	}
}
