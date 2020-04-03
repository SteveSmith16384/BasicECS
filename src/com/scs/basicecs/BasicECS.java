package com.scs.basicecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BasicECS {

	private HashMap<Class<?>, ISystem> systems = new HashMap<Class<?>, ISystem>();
	private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
	private List<AbstractEntity> to_add_entities = new ArrayList<AbstractEntity>();
	public List<AbstractEvent> events = new ArrayList<AbstractEvent>(); // todo - make private, add an add()
	
	public BasicECS() {
	}


	public void addSystem(ISystem system) {
		this.systems.put(system.getClass(), system);
	}


	public void removeSystem(Class<?> clazz) {
		this.systems.remove(clazz);
	}


	public ISystem getSystem(Class<?> clazz) {
		return this.systems.get(clazz);
	}

	
	public void processSystem(Class<?> clazz) {
		ISystem system = this.getSystem(clazz);
		if (system != null) {
			system.process();
		}
	}
	
	
	/**
	 * Do not call this directly.  It will be called automatically by AbstractEntity.
	 */
	protected void addEntityToSystems(AbstractEntity e, Class<?> component_class) {
		// Add to appropriate systems
		for(ISystem isystem : this.systems.values()) {
			if (isystem instanceof AbstractSystem) {
				AbstractSystem system = (AbstractSystem)isystem;
				Class<?> system_clazz = system.getComponentClass();
				if (system_clazz != null) {
					if (component_class.equals(system_clazz)) {
						system.addEntity(e);
					}
				}
			}
		}
	}
	
	
	/**
	 * Do not call this directly.  It will be called automatically by AbstractEntity.
	 */
	protected void removeEntityFromSystems(AbstractEntity e, Class<?> component_class) {
		// Remove from appropriate systems
		for(ISystem isystem : this.systems.values()) {
			if (isystem instanceof AbstractSystem) {
				AbstractSystem system = (AbstractSystem)isystem;
				Class<?> system_clazz = system.getComponentClass();
				if (system_clazz != null) {
					if (component_class.equals(system_clazz)) {
						system.entities.remove(e);
					}
				}
			}
		}
	}
	
	
	/**
	 * Call this in your main loop to avoid concurrency errors.
	 */
	public void addAndRemoveEntities() {
		// Remove any entities
		for (int i = this.entities.size()-1 ; i >= 0; i--) {
			AbstractEntity entity = this.entities.get(i);
			if (entity.isMarkedForRemoval()) {
				this.entities.remove(entity);

				// Remove from systems
				for(ISystem isystem : this.systems.values()) {
					if (isystem instanceof AbstractSystem) {
						AbstractSystem system = (AbstractSystem)isystem;
						Class<?> clazz = system.getComponentClass();
						if (clazz != null) {
							if (entity.getComponents().containsKey(clazz)) {
								system.entities.remove(entity);
							}
						}
					}
				}
			}
		}

		for(AbstractEntity e : this.to_add_entities) {
			for(ISystem isystem : this.systems.values()) {
				if (isystem instanceof AbstractSystem) {
					AbstractSystem system = (AbstractSystem)isystem;
					Class<?> clazz = system.getComponentClass();
					if (clazz != null) {
						if (e.getComponents().containsKey(clazz)) {
							system.addEntity(e);
						}
					}
				}
			}
			this.entities.add(e);
		}

		to_add_entities.clear();
	}


	public void addEntity(AbstractEntity e) {
		e.unmarkForRemoval(); // In case it's been re-added after being removed
		this.to_add_entities.add(e);
	}

	
	public void removeEntity(AbstractEntity e) {
		e.remove();
	}
	

	public AbstractEntity get(int i) {
		return this.entities.get(i);
	}


	public Iterator<AbstractEntity> getEntityIterator() {
		return this.entities.iterator();
	}

	
	public List<AbstractEvent> getEvents(Class<? extends AbstractEvent> clazz) {
		List<AbstractEvent> list = new ArrayList<AbstractEvent>();
		Iterator<AbstractEvent> it = this.events.iterator();
		while (it.hasNext()) {
			AbstractEvent evt = it.next();
			if (evt.getClass().equals(clazz)) {
				list.add(evt);
			}
		}
		return list;
	}

	
	public List<AbstractEvent> getEventsForEntity(Class<? extends AbstractEvent> clazz, AbstractEntity e) {
		List<AbstractEvent> list = new ArrayList<AbstractEvent>();
		Iterator<AbstractEvent> it = this.events.iterator();
		while (it.hasNext()) {
			AbstractEvent evt = it.next();
			if (evt.getClass().equals(clazz)) {
				if (evt.isForEntity(e)) {
					list.add(evt);
				}
			}
		}
		return list;
	}

	
	public void removeAllEntities() {
		for(AbstractEntity e : this.entities) {
			e.remove();
		}
		this.to_add_entities.clear();
	}
	
	
	public boolean containsEntity(AbstractEntity e) {
		return this.entities.contains(e);
	}

}
