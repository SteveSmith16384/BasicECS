package com.scs.basicecs.example;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class ExampleEntity extends AbstractEntity {

	public ExampleEntity(BasicECS ecs) {
		super(ecs, ExampleEntity.class.getSimpleName());
		
		this.addComponent(new ExampleComponent());
	}

}
