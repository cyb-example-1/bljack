package com.cybernetica.bj.client.interfaces;

import com.cybernetica.bj.client.events.BaseEvent;

public interface EventListener<T extends BaseEvent> {

	void onEvent(T event);
}
