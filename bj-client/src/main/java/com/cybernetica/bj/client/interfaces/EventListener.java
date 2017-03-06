package com.cybernetica.bj.client.interfaces;

import com.cybernetica.bj.client.events.BaseEvent;
import com.cybernetica.bj.client.exceptions.ClientException;

public interface EventListener<T extends BaseEvent> {

	void onEvent(T event) throws ClientException;
}
