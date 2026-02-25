package dev.mzc.client.events;

public abstract class Event {
	private boolean isCancelled;

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}