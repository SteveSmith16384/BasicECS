package com.scs.basicecs.example;

import com.scs.basicecs.AbstractEntity;

public class ExampleEntity extends AbstractEntity {

	public ExampleEntity() {
		super("ExampleEntity");
		
		this.addComponent(new ExampleComponent());
	}

}
