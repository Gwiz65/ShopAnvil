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

import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import com.wurmonline.server.Features;
import com.wurmonline.server.MiscConstants;

public class ShopAnvil
		implements WurmServerMod, ServerStartedListener, ItemTemplatesCreatedListener, ItemTypes, MiscConstants {

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

		for (CreationEntry entry : CreationMatrix.getInstance().getSimpleEntries()) {
			if(entry instanceof SimpleCreationEntry) {
				SimpleCreationEntry simple = (SimpleCreationEntry) entry;
				if(simple.getObjectSource() == ItemList.anvilSmall) {
					try {
						float percentageLost = ReflectionUtil.getPrivateField(simple, ReflectionUtil.getField(SimpleCreationEntry.class, "percentageLost"));
						SimpleCreationEntry simpleNew = (SimpleCreationEntry) CreationEntryCreator.createSimpleEntry(simple.getPrimarySkill(), shopAnvilId, simple.getObjectTarget(), simple.getObjectCreated(), simple.depleteSource, simple.depleteTarget, percentageLost, simple.depleteEqually, simple.isCreateOnGround(), simple.getCustomCutOffChance(), simple.getMinimumSkillRequirement(), simple.getCategory());
						simpleNew.setDeityRestriction(simple.getDeityRestriction());
						simpleNew.setObjectSourceMaterial(simple.getObjectSourceMaterial());
						simpleNew.setObjectTargetMaterial(simple.getObjectTargetMaterial());
						simpleNew.setUseTemplateWeight(simple.getUseTempalateWeight());
						simpleNew.setColouringCreation(simple.isColouringCreation());
						simpleNew.setDepleteFromSource(simple.getDepleteFromSource());
						simpleNew.setDepleteFromTarget(simple.getDepleteFromTarget());
						simpleNew.setFinalMaterial(simple.getFinalMaterial());
						simpleNew.setIsEpicBuildMissionTarget(simple.isCreateEpicTargetMission);
						simpleNew.isOnlyCreateEpicTargetMission = simple.isOnlyCreateEpicTargetMission;
					} catch (NoSuchFieldException | IllegalAccessException e) {
						throw new HookException(e);
					}
				}
			} else {
				logger.warning("Entry in simple entries was not SimpleCreationEntry. source:"+entry.getObjectSource()+", target:"+entry.getObjectTarget()+", created"+entry.getObjectCreated());
			}
		}

		for (CreationEntry entry : CreationMatrix.getInstance().getAdvancedEntries()) {
			if(entry instanceof AdvancedCreationEntry) {
				AdvancedCreationEntry advanced = (AdvancedCreationEntry) entry;
				if(advanced.getObjectSource() == ItemList.anvilSmall) {
					try {
						float percentageLost = ReflectionUtil.getPrivateField(advanced, ReflectionUtil.getField(AdvancedCreationEntry.class, "percentageLost"));
						AdvancedCreationEntry advancedNew = CreationEntryCreator.createAdvancedEntry(advanced.getPrimarySkill(), shopAnvilId, advanced.getObjectTarget(), advanced.getObjectCreated(), advanced.depleteSource, advanced.depleteTarget, percentageLost, advanced.depleteEqually, advanced.isCreateOnGround(), advanced.getCustomCutOffChance(), advanced.getMinimumSkillRequirement(), advanced.getCategory());
						advancedNew.setColouringCreation(advanced.isColouringCreation());
						advancedNew.setDeityRestriction(advanced.getDeityRestriction());
						advancedNew.setDepleteFromSource(advanced.getDepleteFromSource());
						advancedNew.setDepleteFromTarget(advanced.getDepleteFromTarget());
						advancedNew.setFinalMaterial(advanced.getFinalMaterial());
						advancedNew.setIsEpicBuildMissionTarget(advanced.isCreateEpicTargetMission);
						advancedNew.setObjectSourceMaterial(advanced.getObjectSourceMaterial());
						advancedNew.setObjectTargetMaterial(advanced.getObjectTargetMaterial());
						advancedNew.setUseTemplateWeight(advanced.getUseTempalateWeight());
						advancedNew.isOnlyCreateEpicTargetMission = advanced.isOnlyCreateEpicTargetMission;

						for (CreationRequirement requirement : advanced.getRequirements()) {
							advancedNew.addRequirement(requirement);
						}
					} catch (NoSuchFieldException | IllegalAccessException e) {
						throw new HookException(e);
					}
				}
			} else {
				logger.warning("Entry in advanced entries was not AdvancedCreationEntry. source:"+entry.getObjectSource()+", target:"+entry.getObjectTarget()+", created"+entry.getObjectCreated());
			}
		}
	}
}