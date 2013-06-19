package mods.firstspring.advfiller;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

public class RenderAdvFiller extends TileEntitySpecialRenderer
{
	public void renderAdvFiller(TileAdvFiller tile, double x, double y, double z)
	{
		if (!tile.doRender)
			return;
		//System.out.println("render");
		double fromX = -(tile.xCoord - tile.fromX);
		double toX = -(tile.xCoord - tile.toX) + 1;
		double fromY = -(tile.yCoord - tile.fromY);
		double toY = -(tile.yCoord - tile.toY) + 1;
		double fromZ = -(tile.zCoord - tile.fromZ);
		double toZ = -(tile.zCoord - tile.toZ) + 1;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		RenderHelper.disableStandardItemLighting();
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glLineWidth(6);//線を太く
		// down
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(fromX, fromY, fromZ);
		Tessellator.instance.addVertex(toX, fromY, fromZ);
		Tessellator.instance.addVertex(toX, fromY, toZ);
		Tessellator.instance.addVertex(fromX, fromY, toZ);
		Tessellator.instance.draw();
		// up
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(fromX, toY, fromZ);
		Tessellator.instance.addVertex(toX, toY, fromZ);
		Tessellator.instance.addVertex(toX, toY, toZ);
		Tessellator.instance.addVertex(fromX, toY, toZ);
		Tessellator.instance.draw();
		// west
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(fromX, fromY, fromZ);
		Tessellator.instance.addVertex(fromX, toY, fromZ);
		Tessellator.instance.addVertex(fromX, toY, toZ);
		Tessellator.instance.addVertex(fromX, fromY, toZ);
		Tessellator.instance.draw();
		// east
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(toX, fromY, fromZ);
		Tessellator.instance.addVertex(toX, toY, fromZ);
		Tessellator.instance.addVertex(toX, toY, toZ);
		Tessellator.instance.addVertex(toX, fromY, toZ);
		Tessellator.instance.draw();
		// 他の描画に影響するので戻す
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glLineWidth(1);//線の太さを戻す
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		this.renderAdvFiller((TileAdvFiller) tileentity, d0, d1, d2);
	}
}
