package com.training.tasks.task4.domain;

public class Ship {

	private int containerCount;
	private int loadCapacity;
	private int shipNumber;

	public Ship(int loadCapacity) {
		super();
		this.loadCapacity = loadCapacity;
		this.containerCount = (int) ((Math.random() * (this.loadCapacity + 1 - 0)) + 0);
		this.shipNumber = (int) ((Math.random() * (150 - 1)) + 1);
	}

	public int getShipNumber() {
		return shipNumber;
	}

	public void setShipNumber(int shipNumber) {
		this.shipNumber = shipNumber;
	}

	public int getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(int containerCount) {
		this.containerCount = containerCount;
	}

	public int getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(int loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public void addContainer(int count) {
		this.containerCount += count;
	}

	public void getContainer(int count) {
		this.containerCount -= count;
	}

	public boolean isCapasityFull() {
		if (containerCount >= loadCapacity) {
			return true;
		}
		return false;
	}
	public boolean isCapasityEmpty() {
		if (containerCount <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Ship [containerCount=" + containerCount + ", loadCapacity=" + loadCapacity + ", shipNumber="
				+ shipNumber + "]";
	}
	

}
