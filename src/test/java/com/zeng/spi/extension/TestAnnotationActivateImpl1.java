package com.zeng.spi.extension;

import com.zeng.spi.extension.annotation.Activate;


/**
 *
 */
@Activate(order = 1, group =
{ "order" })
public class TestAnnotationActivateImpl1 implements TestAnnotationActivate
{

	@Override
	public String test()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
