package com.nisovin.shopkeepers.api.shopobjects;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExternalShopObjectTest {

	@Test
	public void defaultLifecycleIsSafeForStatelessAdapters() {
		ExternalShopObject object = new ExternalShopObject() {
			@Override
			public void load(Map<String, Object> values) {
			}
		};

		assertTrue(object.save().isEmpty());
		assertFalse(object.check());
		object.onRemove();
		object.onDelete();
		object.onMove(null);
	}

	@Test
	public void externalTypeExposesStableIdentifierAndDefaults() {
		ExternalShopObjectType type = new ExternalShopObjectType() {
			@Override
			public ExternalShopObject createObject(
					com.nisovin.shopkeepers.api.shopkeeper.Shopkeeper shopkeeper,
					com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData creationData
			) {
				return null;
			}

			@Override
			public String getIdentifier() {
				return "external-test";
			}

			@Override
			public String getPermission() {
				return null;
			}

			@Override
			public boolean hasPermission(org.bukkit.entity.Player player) {
				return true;
			}

			@Override
			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean matches(String identifier) {
				return "external-test".equals(identifier);
			}
		};

		assertTrue(type.isEnabled());
		assertTrue(type.matches("external-test"));
		assertFalse(type.isValidSpawnBlockFace(null, null));
		assertFalse(type.isValidSpawnBlock(null));
	}
}
