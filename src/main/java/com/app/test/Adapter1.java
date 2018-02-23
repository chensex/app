package com.app.test;

/**
 * Person类的适配器继承Person的所有方法，并且实现
 * Person1的方法，这个时候使用适配器类就能满足Person类
 * eat，drink行为
 * @author Administrator
 *
 */
public class Adapter1 implements inerTest{
	@SuppressWarnings("unused")
	private Person person;
	
	public Adapter1(Person person){
		this.person = person;
	}

	public void drink() {
		System.out.println("..,.,,");
	}
	public static void main(String[] args) {
		Person person = new Person();
		//person.eat();
		Adapter1 adapter = new Adapter1(person);
		adapter.drink();
		//adapter.drink();
	}
}
