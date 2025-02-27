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

package techreborn.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import reborncore.client.gui.builder.GuiBase;
import techreborn.tiles.tier1.TilePump;

public class GuiPump extends GuiBase {
	// Fields >>
	public static final ResourceLocation cellOverlay = new ResourceLocation("reborncore", "textures/gui/slot_sprites.cells.png");

	TilePump tile;
	// << Fields

	public GuiPump(final EntityPlayer player, final TilePump tile) {
		super(player, tile, tile.createContainer(player));
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int mouseX, final int mouseY) {
		super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;

		// Battery slot
		drawSlot(8, 72, layer);
		// Liquid input slot
		drawSlot(91, 35, layer);
		// Liquid output slot
		drawSlot(91, 55, layer);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		final GuiBase.Layer layer = GuiBase.Layer.FOREGROUND;

		builder.drawTank(this, 66, 25, mouseX, mouseY, tile.tank.getFluid(), tile.tank.getCapacity(), tile.tank.isEmpty(), layer);
		builder.drawMultiEnergyBar(this, 9, 19, (int) tile.getEnergy(), (int) tile.getMaxPower(), mouseX, mouseY, 0, layer);
	}
}
