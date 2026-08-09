package com.nisovin.shopkeepers.api.shopobjects;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData;
import com.nisovin.shopkeepers.api.shopkeeper.Shopkeeper;
import com.nisovin.shopkeepers.api.types.Type;

/**
 * Describes a shop object whose runtime and persistent state is owned by an
 * external plugin.
 *
 * <p>The returned object is wrapped by Shopkeepers and therefore participates
 * in the normal shopkeeper lifecycle without exposing Shopkeepers internals to
 * the registering plugin.</p>
 */
public interface ExternalShopObjectType extends Type {

	/**
	 * Creates an external object for a shopkeeper.
	 *
	 * @param shopkeeper the Shopkeepers shopkeeper
	 * @param creationData creation data, or {@code null} while loading saved data
	 * @return the external object, never {@code null}
	 */
	public ExternalShopObject createObject(Shopkeeper shopkeeper, ShopCreationData creationData);

	/**
	 * Whether the object should be activated only while its chunk is loaded.
	 *
	 * @return {@code true} when the object requires chunk lifecycle spawning
	 */
	public default boolean needsSpawning() {
		return false;
	}

	/**
	 * Validates a target block face for creation.
	 *
	 * @param targetBlock the targeted block
	 * @param targetBlockFace the targeted face
	 * @return whether the location is valid
	 */
	public default boolean isValidSpawnBlockFace(Block targetBlock, BlockFace targetBlockFace) {
		return targetBlock != null && targetBlockFace != null && targetBlockFace != BlockFace.DOWN;
	}

	/**
	 * Validates a block at which the object would be spawned.
	 *
	 * @param spawnBlock the block
	 * @return whether the location is valid
	 */
	public default boolean isValidSpawnBlock(Block spawnBlock) {
		return spawnBlock != null;
	}
}
