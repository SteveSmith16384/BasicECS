package com.scs.basicecs.example;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

/**
 * A simple system that increases a number in the component, and removes the entity once it reaches 10.
 * @author StephenCS
 *
 */
public class ExampleSystem extends AbstractSystem {

	public ExampleSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return ExampleComponent.class; // Only handle entities that have an ExampleComponent component.
	}
	

	@Override
	public void processEntity(AbstractEntity entity) {
		ExampleComponent component = (ExampleComponent)entity.getComponent(ExampleComponent.class);
		component.myNumber++;
		System.out.println("Entity #" + entity.id + " now has a value of " + component.myNumber);
		
		if (component.myNumber > 10) {
			entity.remove();
		}
	}

}
