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

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.yatse.plugin.customcommands.api.CustomCommandsActivity;

public class CCPluginActivity extends CustomCommandsActivity {

    @BindView(R.id.custom_command_name)
    TextView mViewName;
    @BindView(R.id.custom_command_serverport)
    TextView mViewServerPort;
    @BindView(R.id.custom_command_username)
    TextView mViewUser;
    @BindView(R.id.custom_command_password)
    TextView mViewPassword;
    @BindView(R.id.custom_command_topic)
    TextView mViewTopic;
    @BindView(R.id.custom_command_message)
    TextView mViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_commands);

        ButterKnife.bind(this);
        if (isEditing()) {
            mViewName.setText(pluginCustomCommand.title());
            mViewServerPort.setText(pluginCustomCommand.param1());
            mViewUser.setText(pluginCustomCommand.param2());
            mViewPassword.setText(pluginCustomCommand.param3());
            mViewTopic.setText(pluginCustomCommand.param4());
            mViewMessage.setText(pluginCustomCommand.param5());
        }
    }

    @OnClick({R.id.btn_save, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                cancelAndFinish();
                break;
            case R.id.btn_save:
                // Custom command source field must always equals to plugin uniqueId !!
                pluginCustomCommand.source(getString(R.string.plugin_unique_id));
                pluginCustomCommand.title(String.valueOf(mViewName.getText()));
                pluginCustomCommand.param1(String.valueOf(mViewServerPort.getText()));
                pluginCustomCommand.param2(String.valueOf(mViewUser.getText()));
                pluginCustomCommand.param3(String.valueOf(mViewPassword.getText()));
                pluginCustomCommand.param4(String.valueOf(mViewTopic.getText()));
                pluginCustomCommand.param5(String.valueOf(mViewMessage.getText()));
                saveAndFinish();
                break;
        }
    }

}
