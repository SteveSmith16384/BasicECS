package com.scs.basicecs.example;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

/**
 * A simple system that increases a number in the component.
 * @author StephenCS
 *
 */
public class ExampleSystem extends AbstractSystem {

	public ExampleSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return ExampleComponent.class; // Only bother with entities that have a ExampleComponent component.
	}
	

	@Override
	public void processEntity(AbstractEntity entity) {
		ExampleComponent component = (ExampleComponent)entity.getComponent(ExampleComponent.class);
		component.myNumber++;
		System.out.println("Entity ID " + component.myId + " is now " + component.myNumber);
	}

}
