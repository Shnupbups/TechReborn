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

package techreborn.api.fluidreplicator;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import reborncore.common.util.FluidUtils;
import techreborn.init.ModItems;
import techreborn.tiles.multiblock.TileFluidReplicator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author drcrazy
 *
 */
public class FluidReplicatorRecipe implements Cloneable {
	
	/**
	 * This is the UU matter amount that is required as input
	 * <p>
	 * This cannot be null
	 */
	@Nonnull
	private final int input;
	
	/**
	 * This is the fluid we will produce
	 * <p>
	 * This cannot be null
	 */
	@Nonnull
	private final Fluid output;

	/**
	 * This is the time in ticks that the recipe takes to complete
	 */
	private final int tickTime;
	
	/**
	 * This is the EU that charges every tick to replicate fluid.
	 */
	private final int euPerTick;

	/**
	 * @param input int Amount of UU-matter per bucket
	 * @param output Fluid Fluid to replicate
	 * @param tickTime int Production time for recipe
	 * @param euPerTick int EU amount per tick 
	 */
	public FluidReplicatorRecipe(int input, Fluid output, int tickTime, int euPerTick) {
		this.input = input;
		this.output = output;
		this.tickTime = tickTime;
		this.euPerTick = euPerTick;
	}
	
	public int getInput() {
		return input;
	}

	public Fluid getFluid() {
		return output;
	}

	public int getTickTime() {
		return tickTime;
	}
	
	public int getEuTick() {
		return euPerTick;
	}
	
	public List<Object> getInputs() {
		ArrayList<Object> inputs = new ArrayList<>();
		inputs.add(new ItemStack(ModItems.UU_MATTER, input));
		return inputs;
	}
	
	public boolean useOreDic() {
		return false;
	}
	
	public boolean canCraft(TileFluidReplicator tile) {
		if (!tile.getMultiBlock()) {
			return false;
		}
		final BlockPos hole = tile.getPos().offset(tile.getFacing().getOpposite(), 2);
		if(hasValidTank(tile.getWorld(), hole)){
			return true;
		}
		final Fluid fluid = FluidRegistry.lookupFluidForBlock(tile.getWorld().getBlockState(hole).getBlock());
		if (fluid == null) {
			return false;
		}
		if (!FluidUtils.fluidEquals(fluid, output)) {
			return false;
		}
		final Fluid tankFluid = tile.tank.getFluidType();
		if (tankFluid != null && !FluidUtils.fluidEquals(tankFluid, fluid)) {
			return false;
		}
		
		return true;
	}

	private boolean hasValidTank(World world, BlockPos pos){
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity == null){
			return false;
		}
		IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if(fluidHandler != null){
			FluidStack containedStack = fluidHandler.drain(1000, false);
			return containedStack != null && FluidUtils.fluidEquals(containedStack.getFluid(), output);
		}
		return false;
	}
	
	public boolean onCraft(TileFluidReplicator tile) {
		return true;
	}
	
	// Cloneable
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
