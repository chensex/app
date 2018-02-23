package com.app.test;

/**
 * 对三个方法进行实现成类供实现类继承，
 * 因为继承可以不使用父类的方法，如果有其他类继承这个类，
 * 那么这个类就是适配器
 * @author Administrator
 *
 */
public class Test3 implements Test4{
	public void a() {
		System.out.println("打印a");
	}

	public void b() {
		System.out.println("打印b");
	}

	public void c() {
		System.out.println("打印c");
	}
}
