package com.training.tasks.task4.service;

import com.training.tasks.task4.domain.Port;
import com.training.tasks.task4.domain.Ship;

public class ShipGenerator implements Runnable {

	private Port port;

	public ShipGenerator(Port port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		while (true) {
			Thread.currentThread().setName("Ship Generator");
			port.addShip(new Ship(getRandomCapasity(5, 10)));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getRandomCapasity(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
