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

package techreborn.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.common.util.ItemUtils;

import java.util.List;

public class ItemBlockIndustrialBlastFurnace extends ItemBlock {
	public ItemBlockIndustrialBlastFurnace(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
		if (!ItemUtils.isEmpty(stack)) {
			NBTTagCompound nbt = ItemUtils.getStackNbtData(stack);
			if (nbt.hasKey("coils")) {
				byte coils = nbt.getByte("coils");
				switch (coils) {
					case 1:
						list.add("Is equipped with Kanthal Heating Coils");
						break;
					case 2:
						list.add("Is equipped with Kanthal Heating Coils");
						list.add("Is equipped with Nichrome Heating Coils");
						break;
				}
			}
		}
	}

//	@Override
//	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
//		if (!world.setBlockState(pos, newState)) return false;
//
//		if (world.getBlockState(pos).getBlock() == block) world.getBlockState(pos).getBlock().onBlockPlacedBy(world, pos, newState, player, stack);
//
//		if (!ItemUtils.isEmpty(stack) && stack.hasTagCompound()) {
//			TileEntity tileEntity = world.getTileEntity(pos);
//			if (tileEntity instanceof TileIndustrialBlastFurnace) {
//				NBTTagCompound nbt = ItemUtils.getStackNbtData(stack);
//				((TileIndustrialBlastFurnace) tileEntity).coils = nbt.getByte("coils");
//			}
//		}
//
//		return true;
//	}
}
