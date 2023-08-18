package com.zeng.spi.extension;

import com.zeng.spi.extension.annotation.Activate;


/**
 *
 */
@Activate(order = 2, group =
{ "order" })
public class TestAnnotationActivateImpl2 implements TestAnnotationActivate
{

	@Override
	public String test()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
