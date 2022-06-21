package com.training.tasks.task4.service;

import com.training.tasks.task4.domain.Port;
import com.training.tasks.task4.domain.Ship;

public class PierLoader implements Runnable {
	private Port port;

	public PierLoader(Port port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.currentThread().setName("Pier " + Thread.currentThread().getId());
				Thread.sleep(1000);
				Ship ship = port.getShip();
				if (ship != null) {
					while (!ship.isCapasityEmpty()) {
						Thread.sleep(100);
						ship.getContainer(1);
						port.addContainer(1);
						System.out.println(ship.getContainerCount() + "/" + ship.getLoadCapacity() + " Uploaded ship "
								+ ship.getShipNumber() + ". "
								+ Thread.currentThread().getName());
					}
					System.out.println(String.format("Ship %d is uploaded %d/%d. Loading of new containers begins. Port %s.",
							ship.getShipNumber(),
							ship.getContainerCount(),
							ship.getLoadCapacity(),
							Thread.currentThread().getId()));
					while (!ship.isCapasityFull()) {
						Thread.sleep(100);
						port.getContainer(1);
						ship.addContainer(1);
						System.out.println(ship.getContainerCount() + "/" + ship.getLoadCapacity() + " Loaded ship "
								+ ship.getShipNumber() + ". "
								+ Thread.currentThread().getName());
					}
					port.shipDeparture(ship);
					System.out.println(String.format("Ship %d is loaded and leaves port %s", ship.getShipNumber(),
							Thread.currentThread().getId()));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
