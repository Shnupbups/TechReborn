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

package techreborn.items.tool.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import reborncore.common.util.ItemUtils;
import techreborn.TechReborn;

public class ItemTRSword extends SwordItem {

	String repairOreDict = "";

	public ItemTRSword(ToolMaterial material) {
		this(material, "");
	}

	public ItemTRSword(ToolMaterial material, String repairOreDict) {
		super(material, 1, (material.getAttackDamage() + 6.75F) * -0.344444F, new Item.Settings().group(TechReborn.ITEMGROUP));
		this.repairOreDict = repairOreDict;
	}

	@Override
	public boolean canRepair(ItemStack toRepair, ItemStack repair) {
		if (toRepair.getItem() == this && !repairOreDict.isEmpty()) {
			return ItemUtils.isInputEqual(repairOreDict, repair, false,  true);
		}
		return super.canRepair(toRepair, repair);
	}
}
