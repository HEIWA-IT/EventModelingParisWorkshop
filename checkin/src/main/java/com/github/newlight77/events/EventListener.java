package com.github.newlight77.events;

import org.json.simple.JSONObject;

public interface EventListener {

    void onEvent(JSONObject event);
}
