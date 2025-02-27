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

package techreborn.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.apache.commons.lang3.Validate;
import reborncore.common.util.StringUtils;


public class ItemCells {
	public static ItemStack getCellByName(String name, int count) {
		if (name.equalsIgnoreCase("empty") || name.equalsIgnoreCase("cell")) {
			return ItemDynamicCell.getEmptyCell(count).copy();
		}
		Fluid fluid = FluidRegistry.getFluid("fluid" + name.toLowerCase());
		if (fluid == null) {
			fluid = FluidRegistry.getFluid(name.toLowerCase());
			if (fluid == null) {
				fluid = FluidRegistry.getFluid("fluid" + StringUtils.toFirstCapital(name.toLowerCase()));
			}
		}
		Validate.notNull(fluid, "The fluid " + name + " could not be found");
		return ItemDynamicCell.getCellWithFluid(fluid, count);
	}

	public static ItemStack getCellByName(String name) {
		return getCellByName(name, 1);
	}

	public static boolean isCellEqual(ItemStack stack1, ItemStack stack2){
		if(stack1 == null || stack2 == null){
			return false;
		}
		if(stack1.isEmpty() || stack2.isEmpty()){
			return false;
		}
		if(stack1.getTagCompound() == null || stack2.getTagCompound() == null){
			return false;
		}
		return stack1.getTagCompound().equals(stack2.getTagCompound());
	}

}
