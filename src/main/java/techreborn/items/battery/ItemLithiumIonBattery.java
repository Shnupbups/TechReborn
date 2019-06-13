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

package techreborn.items.battery;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import reborncore.common.powerSystem.ItemPowerManager;
import techreborn.init.TRContent;

public class ItemLithiumIonBattery extends ItemBattery {

	// 400k FE with 1k FE\t charge rate
	public ItemLithiumIonBattery() {
		super(400_000, 1_000);
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
		if (!isIn(group)) {
			return;
		}
		ItemStack uncharged = new ItemStack(TRContent.LITHIUM_ION_BATTERY);
		ItemStack charged = new ItemStack(TRContent.LITHIUM_ION_BATTERY);
		ItemPowerManager capEnergy = new ItemPowerManager(charged);
		capEnergy.setEnergyStored(capEnergy.getMaxEnergyStored());
		items.add(uncharged);
		items.add(charged);
	}
}

