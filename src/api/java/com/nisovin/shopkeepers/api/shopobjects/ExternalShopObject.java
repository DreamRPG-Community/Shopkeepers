package com.nisovin.shopkeepers.api.shopobjects;

import java.util.Collections;
import java.util.Map;

import org.bukkit.Location;

/**
 * Runtime state owned by an {@link ExternalShopObjectType}.
 *
 * <p>Values returned by {@link #save()} must be safe to read after the method
 * returns. Shopkeepers may copy and serialize them asynchronously.</p>
 */
public interface ExternalShopObject {

	/**
	 * Loads the values stored below the Shopkeepers object section.
	 *
	 * @param values an immutable snapshot of the stored values
	 */
	public void load(Map<String, Object> values);

	/**
	 * Gets the values to store below the Shopkeepers object section.
	 *
	 * @return an immutable or independently owned value map
	 */
	public default Map<String, Object> save() {
		return Collections.emptyMap();
	}

	/**
	 * Called when the containing shopkeeper is removed from the active registry.
	 * This also happens during a normal unload and must not delete persistent data.
	 */
	public default void onRemove() {
	}

	/**
	 * Called when the containing shopkeeper is permanently deleted.
	 */
	public default void onDelete() {
	}

	/**
	 * Called after the containing shopkeeper changes its stored location.
	 *
	 * @param location a defensive copy of the new location
	 */
	public default void onMove(Location location) {
	}

	/**
	 * Checks whether the external object is still valid.
	 *
	 * @return {@code true} when Shopkeepers should treat the object as invalid
	 */
	public default boolean check() {
		return false;
	}
}
