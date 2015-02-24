package ph.url.phlinthetime.mod.money;

public class PlayerInfo {

	private int money;
	private String uuid;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public long getLastUnlock() {
		return lastUnlock;
	}

	public void setLastUnlock(long lastUnlock) {
		this.lastUnlock = lastUnlock;
	}

	private boolean locked;
	private long lastUnlock;
	
	public PlayerInfo(String uuid, int money, boolean locked, long lastUnlock){
		super();
		this.money = money;
		this.lastUnlock = lastUnlock;
		this.locked = locked;
		this.uuid = uuid;
	}
	
}
