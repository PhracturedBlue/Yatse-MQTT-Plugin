/*
 * Copyright 2015 Tolriq / Genimee.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package tv.yatse.plugin.customcommands.MQTTPlugin;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import tv.yatse.plugin.customcommands.api.CustomCommandsPluginService;
import tv.yatse.plugin.customcommands.api.PluginCustomCommand;
import tv.yatse.plugin.customcommands.api.YatseLogger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Sample CustomCommandsPluginService that implement all functions with dummy code that displays Toast and logs to main Yatse log system.
 * <p/>
 * See {@link CustomCommandsPluginService} for documentation on all functions
 */
public class CCPluginService extends CustomCommandsPluginService {
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final String TAG = "AVPluginService";

    public CCPluginService() {
        super();
    }

    @Override
    protected void executeCustomCommand(PluginCustomCommand pluginCustomCommand, String hostId, String hostName, String hostIP) {
        MqttClient client;
        displayToast(pluginCustomCommand.title());
        YatseLogger.getInstance(this).logVerbose(TAG, "Starting customCommand : %s", pluginCustomCommand.title());
        try {
            client = new MqttClient("tcp://" + pluginCustomCommand.param1(), MqttClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            if (pluginCustomCommand.param2() != "") {
                options.setUserName(pluginCustomCommand.param2());
                options.setPassword(pluginCustomCommand.param3().toCharArray());
            }
            client.connect(options);
            final MqttTopic yatseTopic = client.getTopic(pluginCustomCommand.param4());
            final MqttMessage message = new MqttMessage(pluginCustomCommand.param5().getBytes());
            yatseTopic.publish(message);
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
            return;
        }
    }

    private void displayToast(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
