package dev.mzc.client.events;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventBus {

	private static EventBus instance = new EventBus();

	public interface EventListener<T extends Event> {
		void onEvent(T event);

		default int getPriority() {
			return 0;
		}
	}

	private final ConcurrentHashMap<Type, CopyOnWriteArrayList<EventListener<?>>> listenerMap = new ConcurrentHashMap<>();
	private final Map<Class<?>, Field[]> declaredFieldsCache = new HashMap<>();

	private final Comparator<EventListener<?>> priorityOrder = Comparator
			.comparingInt((EventListener<?> listener) -> listener.getPriority()).reversed();
	private final BiConsumer<List<EventListener<?>>, Comparator<EventListener<?>>> sortCallback = List::sort;
	private final Consumer<Throwable> errorHandler = Throwable::printStackTrace;

	private Field[] getCachedDeclaredFields(final Class<?> clazz) {
		return declaredFieldsCache.computeIfAbsent(clazz, Class::getDeclaredFields);
	}

	public void register(final Object o) {
		modifyEventListenerState(o, (type, listener) -> {
			listenerMap.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(listener);
			sortCallback.accept(listenerMap.get(type), priorityOrder);
		});
	}

	public <T extends Event> void post(T event) {
		Type eventType = event.getClass();
		CopyOnWriteArrayList<EventListener<?>> listeners = listenerMap.get(eventType);

		if (listeners != null) {
			for (EventListener<?> listener : listeners) {
				try {
					@SuppressWarnings("unchecked")
					EventListener<T> castedListener = (EventListener<T>) listener;
					castedListener.onEvent(event);
				} catch (Throwable t) {
					errorHandler.accept(t);
				}
			}
		}
	}

	public void unregister(final Object o) {
		modifyEventListenerState(o, (type, listener) -> {
			CopyOnWriteArrayList<EventListener<?>> listeners = listenerMap.get(type);
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty()) {
					listenerMap.remove(type);
				}
			}
		});
	}

	private void modifyEventListenerState(final Object o,
			final BiConsumer<Type, EventListener<?>> eventListenerBiConsumer) {
		final Class<?> type = o.getClass();
		for (final Field field : getCachedDeclaredFields(type)) {
			if (field.getType() == EventListener.class) {
				final EventListener<?> eventListener = getEventListener(o, field);
				final Type eventType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				eventListenerBiConsumer.accept(eventType, eventListener);
			}
		}
	}

	private EventListener<?> getEventListener(final Object o, final Field field) {
		final boolean accessible = field.canAccess(o);
		field.setAccessible(true);
		EventListener<?> fieldSubscription = null;
		try {
			fieldSubscription = (EventListener<?>) field.get(o);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			field.setAccessible(accessible);
		}
		return fieldSubscription;
	}

	public static EventBus getInstance() {
		return instance;
	}
}