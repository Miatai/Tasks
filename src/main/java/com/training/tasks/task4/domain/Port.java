package com.training.tasks.task4.domain;

import java.util.ArrayList;
import java.util.List;

public class Port {
	private List<Ship> store;
	private int maxShipsInPort;
	private int minShipsInPort;
	private int shipsCounter = 0;
	private int containersInPort;
	private int loadCapacity;
	private int containersInStorage;

	public Port(int minShipsInPort, int maxShipsInPort, int containersInStorage, int loadCapacity) {
		store = new ArrayList<>();
		this.minShipsInPort = minShipsInPort;
		this.maxShipsInPort = maxShipsInPort;
		this.loadCapacity = loadCapacity;
		this.containersInStorage = containersInStorage;
		this.containersInPort = containersInStorage;
	}

	public synchronized void shipDeparture(Ship ship) {
		containersInPort = containersInPort - ship.getContainerCount();
	}

	public synchronized boolean addShip(Ship ship) {
		try {
//			System.out.println(String.format("---- Port (%d/%d/%d)",
//					containersInStorage,
//					containersInPort,
//					loadCapacity));
			if (shipsCounter < maxShipsInPort) {
				if ((ship.getContainerCount() + containersInPort) > 0
						&& (ship.getContainerCount() + containersInPort) < (ship.getLoadCapacity() + loadCapacity)
						&& (ship.getContainerCount() + containersInPort) <= loadCapacity
						&& (ship.getContainerCount() + containersInPort) >= ship.getLoadCapacity()) {
//					System.out.println(ship.getContainerCount() + this.containersInStorage);
					notifyAll();
					System.out.println(String.format(
							"---- %s + The ship %d with %d/%d containers arrived in the port (%d/%d/%d).",
							store.size(),
							ship.getShipNumber(),
							ship.getContainerCount(),
							ship.getLoadCapacity(),
							containersInStorage,
							containersInPort,
							loadCapacity));
					containersInPort = containersInPort + ship.getContainerCount();
					store.add(ship);
					shipsCounter++;
				} else {
					System.out.println(String.format("---- Ship can't going in port. Ship %d/%d. Port %d/%d/%d",
							ship.getContainerCount(),
							ship.getLoadCapacity(),
							containersInStorage,
							containersInPort,
							loadCapacity));
					return false;
				}
			} else {
				System.out.println("---- " + store.size() + "> There is no place for a ship in the port.");
				wait();
				return false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return true;
	}

	public synchronized Ship getShip() {
		try {
			if (shipsCounter > minShipsInPort) {
				notifyAll();
				for (Ship ship : store) {
					shipsCounter--;
					System.out.println("---- " +
							store.size() + "- The ship " + ship.getShipNumber() + " is taken from the port: "
							+ Thread.currentThread().getName());
					store.remove(ship);
					return ship;
				}
			}
			System.out.println("---- There are no ships in the port");
			wait();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized void getContainer(int count) {
		try {
			while (containersInStorage == 0) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		containersInStorage = containersInStorage - count;
		System.out.println(String.format("%s take %d containers from the port. %d/%d/%d containers in the storage.",
				Thread.currentThread().getName(), count, containersInStorage, containersInPort, loadCapacity));
		notifyAll();

	}

	public synchronized void addContainer(int count) {
		try {
			while (containersInStorage == loadCapacity) {
				wait();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		containersInStorage = containersInStorage + count;
		System.out.println(String.format("%s add %d containers to the port. %d/%d/%d containers in the storage.",
				Thread.currentThread().getName(), count, containersInStorage, containersInPort, loadCapacity));
		notifyAll();
	}
}
