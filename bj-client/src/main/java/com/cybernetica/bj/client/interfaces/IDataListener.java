package com.cybernetica.bj.client.interfaces;

import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;

public interface IDataListener {
	 void onUserData(UserDataEvent event) throws ClientException;
}
