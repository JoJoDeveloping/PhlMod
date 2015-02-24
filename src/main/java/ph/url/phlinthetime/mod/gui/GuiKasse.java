package ph.url.phlinthetime.mod.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.Values;
import ph.url.phlinthetime.mod.networking.packets.MainPacket;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiKasse extends GuiContainer {

	public GuiTextField[] textfields = new GuiTextField[4];
	
	public GuiKasse(InventoryPlayer invP, TileEntityKasse te_){
		super(new ContainerKasse(invP, te_));
	}
	
	public void initGui() {
		super.initGui();
		this.xSize = 176;
		this.ySize = 200;
        this.guiLeft = (width  - xSize) / 2;
        this.guiTop  = (height - ySize) / 2;
		for(int i = 0; i < 4; i++){
			textfields[i] = new GuiTextField(fontRendererObj, 46, 16+(22*i), 82, 18);
			textfields[i].setFocused(false);
			textfields[i].setCanLoseFocus(true);
		}
	};
	
	ResourceLocation kasse_bg = new ResourceLocation(ModPhantasialand.MODID+":textures/gui/kasse.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.bindTexture(kasse_bg);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
           
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		for(int i = 0; i < 4; i++){
			if(!textfields[i].isFocused())textfields[i].setText((((ContainerKasse)this.inventorySlots).getPrice(i)/*==0?"Nichts verfügbar":((ContainerKasse)this.inventorySlots).prices[i]*/)+"");
			textfields[i].drawTextBox();
		}
	}
	
	
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		/*
		par1 = par1-guiLeft;
		par2 = par2-guiTop;
		for(int i = 0; i < 4; i++){
			boolean flag = textfields[i].isFocused();
			textfields[i].mouseClicked(par1, par2, par3);
			if(flag==true&& textfields[i].isFocused()==false){
				try{
					int price = Integer.valueOf(textfields[i].getText());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(baos);
					dos.writeByte(Values.PACKET_CHANGE_PRICE);
					dos.writeByte(-1);
					dos.writeInt(this.inventorySlots.windowId);
					dos.writeInt(i);
					dos.writeInt(price);
					ModPhantasialand.networkCon.sendToServer(new MainPacket(baos.toByteArray(), ModPhantasialand.MODID));
				}catch(NumberFormatException e){
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		boolean flag = false;
		for(int i = 0; i < 4; i++){
			textfields[i].textboxKeyTyped(par1, par2);
			if(textfields[i].isFocused()){
				flag=true;
			}
		}
		if(!flag)super.keyTyped(par1, par2);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		// TODO Auto-generated method stub
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}
}
