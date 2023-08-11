package com.zeng.spi.jdk;

import java.util.Iterator;
import java.util.ServiceLoader;

public class FruitTest {
	public static void main(String[] args) {
		ServiceLoader<Fruit> load = ServiceLoader.load(Fruit.class);
		Iterator<Fruit> iterator = load.iterator();
		while (iterator.hasNext()) {
			Fruit next = iterator.next();
			System.out.println(next.getName());
		}
	}
}
