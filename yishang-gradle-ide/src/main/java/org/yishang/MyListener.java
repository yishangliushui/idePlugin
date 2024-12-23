package org.yishang;

import com.intellij.util.messages.Topic;

public interface MyListener {

	Topic<MyListener> MY_TOPIC = Topic.create("listener", MyListener.class);

	void onClick();
}
