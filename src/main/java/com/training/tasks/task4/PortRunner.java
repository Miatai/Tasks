package com.training.tasks.task4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.training.tasks.task4.domain.Port;
import com.training.tasks.task4.service.PierLoader;
import com.training.tasks.task4.service.ShipGenerator;

//Задание 4. Многопоточность. Порт . Корабли заходят в порт для
//разгрузки/загрузки контейнеров. Число контейнеров, находящихся в текущий момент
//в порту и на корабле, должно быть неотрицательным и превышающим заданную
//грузоподъемность судна и вместимость порта. В порту работает несколько причалов.
//У одного причала может стоять один корабль. Корабль может загружаться у причала
//или разгружаться.
public class PortRunner {

	public static void main(String[] args) {
		Port port = new Port(0, 5, 200, 400);

		ShipGenerator shipGenerator = new ShipGenerator(port);

		PierLoader pierLoader1 = new PierLoader(port);
		PierLoader pierLoader2 = new PierLoader(port);

		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		service.execute(shipGenerator);
		service.execute(pierLoader1);
		service.execute(pierLoader2);

		service.shutdown();

	}
}
