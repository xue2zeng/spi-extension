package com.zeng.spi.extension;

import static com.zeng.spi.extension.constants.CommonConstants.GROUP_KEY;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 *
 */
public class ExtensionLoaderTest {
	//自定义协议
	static Protocol protocol = new Protocol()
	{

		@Override
		public String getParameter(final String key)
		{
			return key;
		}

		@Override
		public boolean hasParameter(final String key)
		{
			return false;
		}

		@Override
		public String getParameter(final String key, final String defaultValue)
		{
			return key;
		}

		@Override
		public Map<String, String> getParameters()
		{
			final Map<String, String> map = new HashedMap<>();
			map.put(GROUP_KEY, "order");
			return map;
		}
	};
	/**
	 *
	 */
	@Test
	public void getExtensionTest() {
		final TestAnnotationSPI spiImpl1 = ExtensionLoader.getExtensionLoader(TestAnnotationSPI.class).getActivate("testImpl1");
		Assertions.assertEquals(spiImpl1.test(), "SPI Impl 1");

		final TestAnnotationSPI spiImpl2 = ExtensionLoader.getExtensionLoader(TestAnnotationSPI.class).getActivate("testImpl2");
		Assertions.assertEquals(spiImpl2.test(), "SPI Impl 2");

		final TestAnnotationSPI spiImpl = ExtensionLoader.getExtensionLoader(TestAnnotationSPI.class).getActivate("com.zeng.spi.extension.TestAnnotationSPIImpl1");
		Assertions.assertEquals(spiImpl.test(), "SPI Impl 1");
	}

	/**
	 *
	 */
	@Test
	public void testLoadActivateExtension()
	{
		List<TestAnnotationActivate> activateExtension = ExtensionLoader.getExtensionLoader(TestAnnotationActivate.class)
				.getActivateExtension(protocol, new String[]
						{}, "order");
		Assertions.assertEquals(activateExtension.size(), 2);
		assertSame(activateExtension.get(0).getClass(), TestAnnotationActivateImpl2.class);
		assertSame(activateExtension.get(1).getClass(), TestAnnotationActivateImpl1.class);

		activateExtension = ExtensionLoader.getExtensionLoader(TestAnnotationActivate.class).getActivateExtension(protocol,
				new String[] {}, "g");
		Assertions.assertEquals(activateExtension.size(), 0);
	}
}
