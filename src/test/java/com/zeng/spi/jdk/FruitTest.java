package com.zeng.spi.jdk;

import java.util.Iterator;
import java.util.ServiceLoader;


/**
 *
 */
public class FruitTest {
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final ServiceLoader<Fruit> load = ServiceLoader.load(Fruit.class);
		final Iterator<Fruit> iterator = load.iterator();
		while (iterator.hasNext()) {
			final Fruit next = iterator.next();
			System.out.println(next.getName());
		}
	}
}
