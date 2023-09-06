/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to <http://unlicense.org/>
*/

package org.gwiz.wurmunlimited.mods;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import com.wurmonline.server.Features;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;

public class ShopAnvil
		implements WurmServerMod, ServerStartedListener, ItemTemplatesCreatedListener, ItemTypes, MiscConstants, Versioned {

	private static final String version = "1.1";
	private static Logger logger = Logger.getLogger(ShopAnvil.class.getName());
	private int shopAnvilId;

	@Override
	public void onItemTemplatesCreated() {
		logger.log(Level.INFO, "Adding Shop Anvil template.");
		// create our new item template and build it
		try {
			ItemTemplateBuilder itemTemplateBuilder = new ItemTemplateBuilder("shop.anvil");
			itemTemplateBuilder.name("shop anvil", "shop anvils", "A ground usable small anvil.");
			itemTemplateBuilder.descriptions("excellent", "good", "ok", "poor");
			itemTemplateBuilder.itemTypes(new short[] { ITEM_TYPE_NAMED, ITEM_TYPE_DECORATION, ITEM_TYPE_TOOL,
					ITEM_TYPE_METAL, ITEM_TYPE_REPAIRABLE, ITEM_TYPE_DESTROYABLE, ITEM_TYPE_USE_GROUND_ONLY,
					ITEM_TYPE_NOT_MISSION, ITEM_TYPE_TURNABLE, ITEM_TYPE_PLANTABLE });
			itemTemplateBuilder.imageNumber((short) 791);
			itemTemplateBuilder.behaviourType((short) 1);
			itemTemplateBuilder.combatDamage(0);
			itemTemplateBuilder.decayTime(3024000L);
			itemTemplateBuilder.dimensions(10, 10, 10);
			itemTemplateBuilder.primarySkill((int) -10);
			itemTemplateBuilder.bodySpaces(EMPTY_BYTE_PRIMITIVE_ARRAY);
			itemTemplateBuilder.modelName("model.tool.anvil.small.");
			itemTemplateBuilder.difficulty(5.0f);
			itemTemplateBuilder.weightGrams(2000);
			itemTemplateBuilder.material((byte) 11);
			itemTemplateBuilder.value(10000);
			itemTemplateBuilder.isTraded(true);
			itemTemplateBuilder.dyeAmountOverrideGrams((short) -1);
			ItemTemplate gwizAnvilTemplate = itemTemplateBuilder.build();
			this.shopAnvilId = gwizAnvilTemplate.getTemplateId();
			logger.log(Level.INFO, "Shop Anvil using template id: " + shopAnvilId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onServerStarted() {
		// add recipes for creating shop anvil
		logger.log(Level.INFO, "Adding Shop Anvil to recipies.");
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10015, 63, 46, shopAnvilId, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createMetallicEntries(10015, 62, 46, shopAnvilId, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
		} else {
			CreationEntryCreator.createSimpleEntry(10015, 63, 46, shopAnvilId, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10015, 62, 46, shopAnvilId, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
		}

		// add recipes for everything that uses small anvil to use shop anvil too
		logger.log(Level.INFO, "Adding Shop Anvil to all recipies that use the small anvil.");
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 147, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createMetallicEntries(10011, shopAnvilId, 46, 89, false, true, 0.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createMetallicEntries(10011, shopAnvilId, 46, 523, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
		} else {
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 147, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 694, 147, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 837, 147, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 698, 147, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 205, 147, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 46, 89, false, true, 0.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 46, 523, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 694, 89, false, true, 0.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 694, 523, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 837, 89, false, true, 0.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 837, 523, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 205, 89, false, true, 0.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 205, 523, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 698, 89, false, true, 0.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 698, 523, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
		}
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 215, false, true, 400.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 259, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 257, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 258, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
		} else {
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 215, false, true, 400.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 259, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 257, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 258, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 259, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 257, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 258, false, true, 10.0f, false, false,
					CreationCategories.COOKING_UTENSILS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 47, 216, false, true, 400.0f, false, false,
					CreationCategories.TOOLS);
		}
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 47, 772, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 773, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 220, 1298, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 49, 1299, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 205, 597, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 44, 599, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 598, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 127, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 154, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 389, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 494, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 709, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 391, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 393, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 395, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 125, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10010, shopAnvilId, 46, 126, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 734, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 720, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 735, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
		} else {
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 127, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 154, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 389, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 494, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 709, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 205, 709, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 698, 709, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 694, 709, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 837, 709, false, true, 0.0f, false, false,
					CreationCategories.BLADES);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 391, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 393, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 205, 393, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 694, 393, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 837, 393, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 698, 393, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 395, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 734, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 221, 720, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 735, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 125, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 46, 126, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 205, 126, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 698, 126, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 694, 126, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 837, 126, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
		}
		CreationEntryCreator.createSimpleEntry(10010, shopAnvilId, 45, 793, false, true, 0.0f, false, false,
				CreationCategories.TOOL_PARTS);
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createMetallicEntries(10011, shopAnvilId, 46, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createMetallicEntries(10011, shopAnvilId, 46, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createMetallicEntries(10011, shopAnvilId, 46, 123, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 219, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 701, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 124, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 24, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
		} else {
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 205, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 47, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 44, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 223, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 694, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 837, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 698, 131, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 205, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 47, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 44, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 223, 517, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 205, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 47, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 45, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 44, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 223, 444, false, true, 10.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 46, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 698, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 694, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 837, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 205, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 47, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 45, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 44, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 223, 451, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 46, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 698, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 694, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 837, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 205, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 47, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 45, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 44, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 223, 452, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 46, 123, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 205, 123, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 694, 123, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 837, 123, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 698, 123, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 219, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 701, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 124, false, true, 0.0f, false, false,
					CreationCategories.TOOL_PARTS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 24, false, true, 0.0f, false, false,
					CreationCategories.TOOLS);
		}
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 217, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 218, false, true, 0.0f, false, false,
				CreationCategories.CONSTRUCTION_MATERIAL);
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10043, shopAnvilId, 46, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createMetallicEntries(10043, shopAnvilId, 46, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createMetallicEntries(10043, shopAnvilId, 46, 229, false, true, 0.0f, false, false,
					CreationCategories.JEWELRY);
		} else {
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 229, false, true, 0.0f, false, false,
					CreationCategories.JEWELRY);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 229, false, true, 0.0f, false, false,
					CreationCategories.JEWELRY);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 46, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 698, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 694, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 837, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 47, 228, false, true, 0.0f, false, false,
					CreationCategories.LIGHTS_AND_LAMPS);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 46, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 47, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 223, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 221, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 49, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 205, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 694, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 837, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
			CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 698, 232, false, true, 0.0f, false, false,
					CreationCategories.CONSTRUCTION_MATERIAL);
		}
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 227, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 227, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 227, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 505, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 505, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 505, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 506, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 506, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 506, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 507, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 507, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 507, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 508, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 508, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 508, false, true, 0.0f, false, false,
				CreationCategories.STATUETTES);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 230, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 230, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 230, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 231, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 231, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 231, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 694, 231, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 837, 231, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 698, 231, false, true, 0.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 44, 297, false, true, 100.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 45, 297, false, true, 100.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 1411, 297, false, true, 100.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 698, 297, false, true, 100.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 694, 297, false, true, 100.0f, false, false,
				CreationCategories.JEWELRY);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 837, 297, false, true, 100.0f, false, false,
				CreationCategories.JEWELRY);
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10034, shopAnvilId, 46, 167, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createMetallicEntries(10034, shopAnvilId, 46, 194, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createMetallicEntries(10034, shopAnvilId, 46, 193, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createMetallicEntries(10034, shopAnvilId, 46, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 185, false, true, 0.0f, false, true,
					CreationCategories.TOOLS);
		} else {
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 46, 167, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 46, 194, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 46, 193, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 694, 167, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 694, 194, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 694, 193, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 837, 167, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 837, 194, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 837, 193, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 698, 167, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 698, 194, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 698, 193, false, true, 0.0f, false, false,
					CreationCategories.LOCKS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 46, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 205, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 694, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 837, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 698, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10034, shopAnvilId, 47, 463, false, true, 0.0f, false, false, 0,
					25.0, CreationCategories.TOOLS);
			CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 46, 185, false, true, 0.0f, false, true,
					CreationCategories.TOOLS);
		}
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 221, 902, false, true, 0.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 223, 904, false, true, 0.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createSimpleEntry(10015, shopAnvilId, 220, 1166, false, true, 0.0f, false, false,
				CreationCategories.TOOLS);
		CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 46, 1126, false, true, 10.0f, false, false,
				CreationCategories.WEAPON_HEADS);
		final AdvancedCreationEntry washingBowl = CreationEntryCreator.createAdvancedEntry(10015, shopAnvilId, 221, 895,
				false, true, 0.0f, false, true, 0, 30.0, CreationCategories.FURNITURE);
		washingBowl.setDepleteFromTarget(1500);
		washingBowl.addRequirement(new CreationRequirement(1, 897, 3, true));
		washingBowl.addRequirement(new CreationRequirement(2, 77, 1, true));
		if (Features.Feature.METALLIC_ITEMS.isEnabled()) {
			CreationEntryCreator.createMetallicEntries(10011, shopAnvilId, 698, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
		} else {
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 698, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 694, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 837, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 205, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 47, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 45, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 44, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
			CreationEntryCreator.createSimpleEntry(10011, shopAnvilId, 223, 935, false, true, 10.0f, false, false,
					CreationCategories.WEAPON_HEADS);
		}
		CreationEntryCreator.createAdvancedMetalicEntry(10015, shopAnvilId, 220, 1341, false, true, 0.0f, false, false,
				CreationCategories.TOOLS, new CreationRequirement(1, 790, 3, true),
				new CreationRequirement(2, 100, 1, true));
		CreationEntryCreator.createMetallicEntries(10043, shopAnvilId, 46, 1345, false, true, 0.0f, false, false,
				CreationCategories.TOOLS);
		CreationEntryCreator.createMetallicEntries(10015, shopAnvilId, 46, 1357, false, true, 1000.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createMetallicEntries(10043, shopAnvilId, 46, 1368, false, true, 10.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 205, 1369, false, true, 10.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 837, 1369, false, true, 10.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 694, 1369, false, true, 10.0f, false, false,
				CreationCategories.TOOL_PARTS);
		CreationEntryCreator.createSimpleEntry(10043, shopAnvilId, 698, 1369, false, true, 10.0f, false, false,
				CreationCategories.TOOL_PARTS);
	}

	@Override
	public String getVersion() {
		return version;
	}
}