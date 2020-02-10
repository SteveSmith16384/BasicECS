package com.scs.basicecs.example;

import com.scs.basicecs.BasicECS;

public class MainExample {

	public static void main(String[] args) {
		new MainExample();

	}

	
	public MainExample() {
		BasicECS ecs = new BasicECS();
		
		ecs.addSystem(new ExampleSystem(ecs));
		
		while (true) {
			ecs.addEntity(new ExampleEntity(ecs));
			
			ecs.addAndRemoveEntities();
			
			ecs.getSystem(ExampleSystem.class).process();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
