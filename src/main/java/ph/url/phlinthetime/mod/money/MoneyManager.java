package ph.url.phlinthetime.mod.money;

import io.netty.buffer.ByteBuf;

import java.io.DataInput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants.NBT;

public class MoneyManager {

	public static MoneyManager instance;
	
	public HashMap<String, PlayerInfo> hashmap = new HashMap<String, PlayerInfo>();
	
	public void addNewPlayer(String uuid){
		if(!hashmap.containsKey(uuid))hashmap.put(uuid, new PlayerInfo(uuid.replace("-", ""), 0, false, 0L));
	}
	
	public MoneyManager(){
		this.instance = this;
		File savePath = DimensionManager.getCurrentSaveRootDirectory();
		File moneyFile;
		NBTTagCompound nbt = null;
		try {
			moneyFile = new File(savePath.getCanonicalPath()+"\\money.nbt");
			if(!moneyFile.exists()){
					moneyFile.createNewFile();
				}else{
				FileInputStream fileinputstream = new FileInputStream(moneyFile);
	            nbt = CompressedStreamTools.readCompressed(fileinputstream);
	            fileinputstream.close();
				}
			
			
			if(nbt!=null&&nbt.hasKey("players")){
				NBTTagList list = nbt.getTagList("players", 10);
				for(int i = 0; i < list.tagCount(); i++){
					NBTTagCompound tag = list.getCompoundTagAt(i);
					String uuid = tag.getString("UUID").replace("-", "");
					long lastUnlock = tag.getLong("lastUnlock");
					int money = tag.getInteger("money");
					boolean locked = tag.getBoolean("lock");
					hashmap.put(uuid, new PlayerInfo(uuid, money, locked, lastUnlock));
				}
			}
			
		} catch (IOException e) {
			System.err.println("Failed to setup money handler due to IOException:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void lockCard(String uuid){
		if(hashmap.containsKey(uuid)){
			PlayerInfo pi = (PlayerInfo) hashmap.get(uuid);
			pi.setLocked(true);
			Iterator i = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
			while(i.hasNext()){
				Object next = i.next();
				if(next instanceof EntityPlayer){
					EntityPlayer p = (EntityPlayer) next;
					if(p.getUniqueID().toString().replace("-", "").equals(uuid)){
						p.addChatMessage(new ChatComponentText("Ihre Kreditkarte wurde gesperrt! Zum Entsperren bitte \"/phl cc unlock\" ohne \" eingeben!"));
					}
				}
			}
		}
	}

	public void save() {
		File savePath = DimensionManager.getCurrentSaveRootDirectory();
		File moneyFile;
		try {
			moneyFile = new File(savePath.getCanonicalPath()+"\\money.nbt");
			if(!moneyFile.exists())moneyFile.createNewFile();
			NBTTagCompound nbt = new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			Iterator iter = hashmap.values().iterator();
			
			while(iter.hasNext()){
				Object next = iter.next();
				if(next instanceof PlayerInfo){
					PlayerInfo pi = (PlayerInfo) next;
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("UUID", pi.getUuid());
					tag.setLong("lastUnlock", pi.getLastUnlock());
					tag.setInteger("money", pi.getMoney());
					tag.setBoolean("lock", pi.isLocked());
					list.appendTag(tag);
				}
			}
			
			nbt.setTag("players", list);
			
			
			CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(moneyFile));
		} catch (IOException e) {
			System.err.println("Failed to save money handler due to IOException:"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
