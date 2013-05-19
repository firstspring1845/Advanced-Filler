package mods.firstspring.advfiller;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class RenderAdvFiller extends Render {

	public void renderAdvFiller(EntityRendererFiller e, double x, double y, double z, float f, float f1){
		TileAdvFiller tile = e.filler;
		if(!e.filler.doRender)
			return;
		System.out.println("render");
		double fromX = -(tile.xCoord - tile.fromX);
		double toX = -(tile.xCoord - tile.toX) + 1;
		double fromY = -(tile.yCoord - tile.fromY);
		double toY = -(tile.yCoord - tile.toY) + 1;
		double fromZ = -(tile.zCoord - tile.fromZ);
		double toZ = -(tile.zCoord - tile.toZ) + 1;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        RenderHelper.disableStandardItemLighting();
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
		//down
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(fromX, fromY, fromZ);
		Tessellator.instance.addVertex(toX, fromY, fromZ);
		Tessellator.instance.addVertex(toX, fromY, toZ);
		Tessellator.instance.addVertex(fromX, fromY, toZ);
		Tessellator.instance.draw();
		//up
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(fromX, toY, fromZ);
		Tessellator.instance.addVertex(toX, toY, fromZ);
		Tessellator.instance.addVertex(toX, toY, toZ);
		Tessellator.instance.addVertex(fromX, toY, toZ);
		Tessellator.instance.draw();
		//west
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(fromX, fromY, fromZ);
		Tessellator.instance.addVertex(fromX, toY, fromZ);
		Tessellator.instance.addVertex(fromX, toY, toZ);
		Tessellator.instance.addVertex(fromX, fromY, toZ);
		Tessellator.instance.draw();
		//east
		Tessellator.instance.startDrawing(GL11.GL_LINE_LOOP);
		Tessellator.instance.setColorRGBA(255, 0, 0, 255);
		Tessellator.instance.addVertex(toX, fromY, fromZ);
		Tessellator.instance.addVertex(toX, toY, fromZ);
		Tessellator.instance.addVertex(toX, toY, toZ);
		Tessellator.instance.addVertex(toX, fromY, toZ);
		Tessellator.instance.draw();
		//他の描画に影響するので戻す
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public void doRender(Entity var1, double var2, double var4,
			double var6, float var8, float var9) {
		this.renderAdvFiller((EntityRendererFiller)var1, var2, var4, var6, var8, var9);
	}
}
