/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.tiles.tier1;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import reborncore.common.util.Inventory;
import reborncore.common.util.ItemUtils;
import techreborn.api.Reference;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.containerBuilder.builder.ContainerBuilder;
import techreborn.init.ModBlocks;
import techreborn.items.ItemDynamicCell;
import techreborn.lib.ModInfo;
import techreborn.tiles.TileGenericMachine;

@RebornRegistry(modID = ModInfo.MOD_ID)
public class TileIndustrialElectrolyzer extends TileGenericMachine implements IContainerProvider {

	@ConfigRegistry(config = "machines", category = "industrial_electrolyzer", key = "IndustrialElectrolyzerMaxInput", comment = "Industrial Electrolyzer Max Input (Value in EU)")
	public static int maxInput = 128;
	@ConfigRegistry(config = "machines", category = "industrial_electrolyzer", key = "IndustrialElectrolyzerMaxEnergy", comment = "Industrial Electrolyzer Max Energy (Value in EU)")
	public static int maxEnergy = 10_000;

	public TileIndustrialElectrolyzer() {
		super("IndustrialElectrolyzer", maxInput, maxEnergy, ModBlocks.INDUSTRIAL_ELECTROLYZER, 6);
		final int[] inputs = new int[] { 0, 1 };
		final int[] outputs = new int[] { 2, 3, 4, 5 };
		this.inventory = new Inventory(7, "TileIndustrialElectrolyzer", 64, this);
		this.crafter = new RecipeCrafter(Reference.INDUSTRIAL_ELECTROLYZER_RECIPE, this, 2, 4, this.inventory, inputs, outputs);
	}
	
	// IContainerProvider
	@Override
	public BuiltContainer createContainer(final EntityPlayer player) {
		return new ContainerBuilder("industrialelectrolyzer").player(player.inventory).inventory().hotbar()
			.addInventory().tile(this)
			.filterSlot(1, 47, 72, stack -> ItemUtils.isItemEqual(stack, ItemDynamicCell.getEmptyCell(1), true, true))
			.filterSlot(0, 81, 72, stack -> !ItemUtils.isItemEqual(stack, ItemDynamicCell.getEmptyCell(1), true, true))
			.outputSlot(2, 51, 24).outputSlot(3, 71, 24).outputSlot(4, 91, 24).outputSlot(5, 111, 24)
			.energySlot(6, 8, 72).syncEnergyValue().syncCrafterValue().addInventory().create(this);
	}
}