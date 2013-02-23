package polarstar.advfiller;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.opengl.GL11;

import buildcraft.api.core.Position;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenAdvFiller extends GuiScreen
{
	int left,right,up,down,forward,type;
	int tileX,tileY,tileZ;
	boolean loopMode,iterate,drop;

	public GuiScreenAdvFiller(Position pos, int left, int right, int up, int down, int forward, int type, boolean loop, boolean iterate, boolean drop){
		
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		this.forward = forward;
		this.type = type;
		this.tileX = (int)pos.x;
		this.tileY = (int)pos.y;
		this.tileZ = (int)pos.z;
		this.loopMode = loop;
		this.iterate = iterate;
		this.drop = drop;
	}

	public void initGui()
	{
		this.controlList.clear();
		byte cnt = 0;
		byte m = -10; // 位置補正＋間隔取り
		 String str[] = {"--", "-", "+", "++"};
		for(int i = 3;i <= 22; i++){
			this.controlList.add(new GuiButton(i, ((this.width - 248) / 2) + i * 10 + m, (this.height - 166) / 2 + 105, 10, 20, str[cnt]));
			cnt++;
			if(cnt > 3){
				cnt = 0;
				m += 3;
			}
		}
		this.controlList.add(new GuiButton(1, ((this.width - 248) / 2) + 20, (this.height - 166) / 2 + 41, 40, 20, "Type"));
		this.controlList.add(new GuiButton(2, ((this.width - 248) / 2) + 106, (this.height - 166) / 2 + 135, 40, 20, "Set"));
		this.controlList.add(new GuiButton(23, ((this.width - 248) / 2) + 192, (this.height - 166) / 2 + 135, 40, 20, "Loop : " + getStateFromBoolean(loopMode)));
		this.controlList.add(new GuiButton(24, ((this.width - 248) / 2) + 20, (this.height - 166) / 2 + 135, 40, 20, (iterate ? "ASCEND" : "DESCEND")));
		this.controlList.add(new GuiButton(25, ((this.width - 248) / 2) + 63, (this.height - 166) / 2 + 135, 40, 20, "Drop : " + getStateFromBoolean(drop)));
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case 1:
			type++;
			if(type > 5)
				type = 0;
			break;
		case 2:
			this.mc.getSendQueue().addToSendQueue(createPacket());
			break;
		//Left
		case 3:
			left = creasesNumber(left,-16);
			break;
		case 4:
			left = creasesNumber(left,-1);
			break;
		case 5:
			left = creasesNumber(left,1);
			break;
		case 6:
			left = creasesNumber(left,16);
			break;
		//Right
		case 7:
			right = creasesNumber(right,-16);
			break;
		case 8:
			right = creasesNumber(right,-1);
			break;
		case 9:
			right = creasesNumber(right,1);
			break;
		case 10:
			right = creasesNumber(right,16);
			break;
		//Up
		case 11:
			up = creasesNumber(up,-16);
			break;
		case 12:
			up = creasesNumber(up,-1);
			break;
		case 13:
			up = creasesNumber(up,1);
			break;
		case 14:
			up = creasesNumber(up,16);
			break;
		//Down
		case 15:
			down = creasesNumber(down,-16);
			break;
		case 16:
			down = creasesNumber(down,-1);
			break;
		case 17:
			down = creasesNumber(down,1);
			break;
		case 18:
			down = creasesNumber(down,16);
			break;
		//Forward
		case 19:
			forward = creasesNumber(forward,-16);
			break;
		case 20:
			forward = creasesNumber(forward,-1);
			break;
		case 21:
			forward = creasesNumber(forward,1);
			break;
		case 22:
			forward = creasesNumber(forward,16);
			break;
		case 23:
			loopMode = !loopMode;
			button.displayString = "Loop : " + getStateFromBoolean(loopMode);
			break;
		case 24:
			iterate = !iterate;
			button.displayString = iterate ? "ASCEND" : "DESCEND";
			break;
		case 25:
			drop = !drop;
			button.displayString = "Drop : " + getStateFromBoolean(drop);
		}
	}
	
	private Packet createPacket() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(b);
		try{
			data.writeInt(tileX);
			data.writeInt(tileY);
			data.writeInt(tileZ);
			data.writeInt(left);
			data.writeInt(right);
			data.writeInt(up);
			data.writeInt(down);
			data.writeInt(forward);
			data.writeInt(type);
			data.writeBoolean(loopMode);
			data.writeBoolean(iterate);
			data.writeBoolean(drop);
		}catch(Exception e){
		}
		
		return new Packet250CustomPayload("advfiller_server", b.toByteArray());
	}

	public int creasesNumber(int i, int j){
		i += j;
		if(i < 0)
			i = 0;
		if(i > AdvFiller.maxDistance)
			i = AdvFiller.maxDistance;
		return i;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		super.updateScreen();
	}
	
	public String getStateFromBoolean(boolean b){
		return b ? "ON" : "OFF";
	}
	
	@Override
    public void keyTyped(char par1, int par2)
    {
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode)
			this.mc.thePlayer.closeScreen();
    }

	/**
	 * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
	 */
	public void drawDefaultBackground()
	{
		super.drawDefaultBackground();
		int var1 = this.mc.renderEngine.getTexture("/polarstar/advfiller/gui/advfiller.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var1);
		int var2 = (this.width - 248) / 2;
		int var3 = (this.height - 166) / 2;
		this.drawTexturedModalRect(var2, var3, 0, 0, 248, 166);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		int var4 = (this.width - 248) / 2;
		int var5 = (this.height - 166) / 2;
		String str;
		str = String.valueOf(left);
		this.fontRenderer.drawString(str, var4 + 40 - this.fontRenderer.getStringWidth(str) / 2, var5 + 90, 65280);
		str = String.valueOf(right);
		this.fontRenderer.drawString(str, var4 + 83 - this.fontRenderer.getStringWidth(str) / 2, var5 + 90, 65280);
		str = String.valueOf(up);
		this.fontRenderer.drawString(str, var4 + 126 - this.fontRenderer.getStringWidth(str) / 2, var5 + 90, 65280);
		str = String.valueOf(down);
		this.fontRenderer.drawString(str, var4 + 169 - this.fontRenderer.getStringWidth(str) / 2, var5 + 90, 65280);
		str = String.valueOf(forward);
		this.fontRenderer.drawString(str, var4 + 212 - this.fontRenderer.getStringWidth(str) / 2, var5 + 90, 65280);
		str = "LEFT:";
		this.fontRenderer.drawString(str, var4 + 20, var5 + 80, 5197647);
		str = "RIGHT:";
		this.fontRenderer.drawString(str, var4 + 63, var5 + 80, 5197647);
		str = "UP:";
		this.fontRenderer.drawString(str, var4 + 106, var5 + 80, 5197647);
		str = "DOWN:";
		this.fontRenderer.drawString(str, var4 + 149, var5 + 80, 5197647);
		str = "FORWARD:";
		this.fontRenderer.drawString(str, var4 + 192, var5 + 80, 5197647);
		str = getType(type);
		this.fontRenderer.drawString(str, var4 + 66, var5 + 46, 65280);
		str = "Remove Mode Iteration";
		this.fontRenderer.drawString(str, var4 + 20, var5 + 125, 5197647);
		super.drawScreen(par1, par2, par3);
	}
	
	public String getType(int type){
		switch(type){
		case 0:
			return "Quarry Mode";
		case 1:
			return "Remove Mode";
		case 2:
			return "Filling Mode";
		case 3:
			return "Flatten Mode";
		case 4:
			return "Exclusive Remove Mode";
		case 5:
			return "TofuBuild Mode";
		}
		return "";
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
}
