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

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import reborncore.api.IToolDrop;
import reborncore.api.tile.IInventoryProvider;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import reborncore.common.util.Inventory;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.containerBuilder.builder.ContainerBuilder;
import techreborn.init.IC2Duplicates;
import techreborn.init.ModBlocks;
import techreborn.init.ModItems;
import techreborn.items.ingredients.ItemParts;
import techreborn.lib.ModInfo;

@RebornRegistry(modID = ModInfo.MOD_ID)
public class TileRecycler extends TilePowerAcceptor 
		implements IToolDrop, IInventoryProvider, IContainerProvider {
	
	@ConfigRegistry(config = "machines", category = "recycler", key = "RecyclerInput", comment = "Recycler Max Input (Value in EU)")
	public static int maxInput = 32;
	@ConfigRegistry(config = "machines", category = "recycler", key = "RecyclerMaxEnergy", comment = "Recycler Max Energy (Value in EU)")
	public static int maxEnergy = 1000;
	@ConfigRegistry(config = "machines", category = "recycler", key = "produceIC2Scrap", comment = "When enabled and when ic2 is installed the recycler will make ic2 scrap")
	public static boolean produceIC2Scrap = false;

	private final Inventory inventory = new Inventory(3, "TileRecycler", 64, this);
	private final int cost = 2;
	private final int time = 15;
	private final int chance = 6;
	private boolean isBurning;
	private int progress;

	public TileRecycler() {
		super();
	}

	public int gaugeProgressScaled(int scale) {
		return progress * scale / time;
	}
	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public void recycleItems() {
		ItemStack itemstack = ItemParts.getPartByName("scrap");
		if(produceIC2Scrap && IC2Duplicates.SCRAP.hasIC2Stack()){
			itemstack = IC2Duplicates.SCRAP.getIc2Stack();
		}
		final int randomchance = this.world.rand.nextInt(chance);

		if (randomchance == 1) {
			if (getStackInSlot(1).isEmpty()) {
				setInventorySlotContents(1, itemstack.copy());
			}
			else {
				this.getStackInSlot(1).grow(itemstack.getCount());
			}
		}
		decrStackSize(0, 1);
	}

	public boolean canRecycle() {
		return !getStackInSlot(0) .isEmpty() && hasSlotGotSpace(1);
	}

	public boolean hasSlotGotSpace(int slot) {
		if (getStackInSlot(slot).isEmpty()) {
			return true;
		} else if (getStackInSlot(slot).getCount() < getStackInSlot(slot).getMaxStackSize()) {
			return true;
		}
		return false;
	}

	public boolean isBurning() {
		return isBurning;
	}

	public void setBurning(boolean burning) {
		this.isBurning = burning;
	}

	public void updateState() {
		final IBlockState BlockStateContainer = world.getBlockState(pos);
		if (BlockStateContainer.getBlock() instanceof BlockMachineBase) {
			final BlockMachineBase blockMachineBase = (BlockMachineBase) BlockStateContainer.getBlock();
			boolean shouldBurn = isBurning || (canRecycle() && canUseEnergy(getEuPerTick(cost)));
			if (BlockStateContainer.getValue(BlockMachineBase.ACTIVE) != shouldBurn) {
				blockMachineBase.setActive(isBurning, world, pos);
			}
		}
	}

	// TilePowerAcceptor
	@Override
	public void update() {
		super.update();
		if (world.isRemote) {
			return;
		}
		charge(2);

		boolean updateInventory = false;
		if (canRecycle() && !isBurning() && getEnergy() != 0) {
			setBurning(true);
		}
		else if (isBurning()) {
			if (useEnergy(getEuPerTick(cost)) != getEuPerTick(cost)) {
				this.setBurning(false);
			}
			progress++;
			if (progress >= Math.max((int) (time* (1.0 - getSpeedMultiplier())), 1)) {
				progress = 0;
				recycleItems();
				updateInventory = true;
				setBurning(false);
			}
		}

		updateState();
		
		if (updateInventory) {
			markDirty();
		}
	}

	@Override
	public double getBaseMaxPower() {
		return maxEnergy;
	}

	@Override
	public boolean canAcceptEnergy(EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canProvideEnergy(EnumFacing direction) {
		return false;
	}

	@Override
	public double getBaseMaxOutput() {
		return 0;
	}

	@Override
	public double getBaseMaxInput() {
		return maxInput;
	}
	
	// TileLegacyMachineBase
	@Override
	public boolean canBeUpgraded() {
		return true;
	}
	
	// IToolDrop
	@Override
	public ItemStack getToolDrop(EntityPlayer entityPlayer) {
		return new ItemStack(ModBlocks.RECYCLER, 1);
	}

	// IInventoryProvider
	@Override
	public Inventory getInventory() {
		return this.inventory;
	}

	// IContainerProvider
	@Override
	public BuiltContainer createContainer(EntityPlayer player) {
		return new ContainerBuilder("recycler").player(player.inventory).inventory().hotbar().addInventory()
			.tile(this).slot(0, 55, 45, itemStack -> itemStack.getItem() != ModItems.UPGRADES).outputSlot(1, 101, 45).energySlot(2, 8, 72).syncEnergyValue()
			.syncIntegerValue(this::getProgress, this::setProgress).addInventory().create(this);
	}
}
