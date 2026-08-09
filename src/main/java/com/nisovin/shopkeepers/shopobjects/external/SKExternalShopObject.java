package com.nisovin.shopkeepers.shopobjects.external;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData;
import com.nisovin.shopkeepers.api.shopobjects.ExternalShopObject;
import com.nisovin.shopkeepers.shopkeeper.AbstractShopkeeper;
import com.nisovin.shopkeepers.shopobjects.AbstractShopObject;

import java.util.Objects;

/**
 * Delegates a Shopkeepers object lifecycle to an external plugin.
 */
public final class SKExternalShopObject extends AbstractShopObject {

	private final SKExternalShopObjectType type;
	private final ExternalShopObject externalObject;

	public SKExternalShopObject(SKExternalShopObjectType type, AbstractShopkeeper shopkeeper,
			ShopCreationData creationData) {
		super(shopkeeper, creationData);
		this.type = Objects.requireNonNull(type, "External shop object type is null!");
		this.externalObject = Objects.requireNonNull(
				type.getExternalType().createObject(shopkeeper, creationData),
				"External shop object factory returned null!");
	}

	@Override
	public SKExternalShopObjectType getType() {
		return type;
	}

	@Override
	public void load(ConfigurationSection configSection) {
		super.load(configSection);
		Map<String, Object> values = new HashMap<>(configSection.getValues(false));
		values.remove("type");
		externalObject.load(Collections.unmodifiableMap(values));
	}

	@Override
	public void save(ConfigurationSection configSection) {
		super.save(configSection);
		Map<String, Object> values = externalObject.save();
		if (values == null) {
			throw new IllegalStateException("External shop object returned null save data: " + type.getIdentifier());
		}
		for (Map.Entry<String, Object> entry : new HashMap<>(values).entrySet()) {
			String key = entry.getKey();
			if (key != null && !key.equals("type")) {
				configSection.set(key, entry.getValue());
			}
		}
	}

	@Override
	public void remove() {
		externalObject.onRemove();
	}

	@Override
	public void delete() {
		externalObject.onDelete();
	}

	@Override
	public boolean isActive() {
		return shopkeeper.isValid();
	}

	@Override
	public String getId() {
		return type.getIdentifier() + ":" + shopkeeper.getUniqueId();
	}

	@Override
	public boolean spawn() {
		return true;
	}

	@Override
	public void despawn() {
		// External plugins own their runtime representation.
	}

	@Override
	public boolean check() {
		return externalObject.check();
	}

	@Override
	public Location getLocation() {
		Location location = shopkeeper.getLocation();
		return location == null ? null : location.clone();
	}

	@Override
	public int getNameLengthLimit() {
		return 256;
	}

	@Override
	public void setName(String name) {
		// The external plugin may expose a separate naming model.
	}

	@Override
	public String getName() {
		return shopkeeper.getName();
	}

	/**
	 * Informs the external object about a stored location change.
	 *
	 * @param location the new location
	 */
	public void onShopkeeperMove(Location location) {
		externalObject.onMove(location == null ? null : location.clone());
	}
}
