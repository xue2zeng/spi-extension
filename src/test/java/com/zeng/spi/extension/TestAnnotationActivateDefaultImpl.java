package com.zeng.spi.extension;

import com.zeng.spi.extension.annotation.Activate;


/**
 *
 */
@Activate(order = 1, group =
{ "default_group" })
public class TestAnnotationActivateDefaultImpl implements TestAnnotationActivate
{

	@Override
	public String test()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
