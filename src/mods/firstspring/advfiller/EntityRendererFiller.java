package mods.firstspring.advfiller;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityRendererFiller extends Entity
{
	public TileAdvFiller filler;

	public EntityRendererFiller(World par1World, TileAdvFiller filler)
	{
		super(par1World);
		this.setSize(1F, 1F);
		this.filler = filler;
		// レンダリングにはlastTickPosが使われるのでこのメソッドを使用
		setLocationAndAngles(filler.xCoord, filler.yCoord, filler.zCoord, 0, 0);
		this.prevPosX = filler.xCoord;
		this.prevPosY = filler.yCoord;
		this.prevPosZ = filler.zCoord;

	}

	protected void entityInit()
	{
		ignoreFrustumCheck = true;

	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		// if(filler != null)
		// setPosition(filler.xCoord, filler.yCoord, filler.zCoord);
		/*
		 * this.motionX = 0; this.motionY = 0; this.motionZ = 0;
		 */
		// super.onUpdate();
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
	}
}
