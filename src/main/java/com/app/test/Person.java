package com.app.test;

/**
 * 源类，有eat方法，但是又想在某个地方使用时加上drink
 * 方法，但是在其他地方不会使用drink，如果此时在此类中加上drink方法，
 * 代码就显得重复
 * @author Administrator
 *
 */
public class Person {
	public void eat(){
		System.out.println("eat");
	}
}
