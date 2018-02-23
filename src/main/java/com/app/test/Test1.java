package com.app.test;

/**
 * 业务实现类
 * @author Administrator
 *
 */
public class Test1 extends Test3 implements Test2{
	public void dayin() {
		//需要用到a方法，不用 b c方法。
		a();
	}
	
    public static void main(String[] args) {
		Test1 test1 = new Test1();
		test1.dayin();
	}

}
