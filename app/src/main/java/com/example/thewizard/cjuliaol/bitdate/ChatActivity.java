package com.example.thewizard.cjuliaol.bitdate;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, MessageDataSource.MessagesCallbacks {

    public static final String USER_EXTRA="USER";


    private static final String TAG = "ChatActivityLog";
    private ArrayList<com.example.thewizard.cjuliaol.bitdate.Message> mMessages;
    private MessageAdapter mMessageAdapter;

    private User mRecipient;
    private ListView mMessageListView;
    private Date mLastMessageDate = new Date();
    private String mConvoId;
    private MessageDataSource.MessagesListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mRecipient = (User) getIntent().getSerializableExtra(USER_EXTRA);

        mMessageListView = (ListView) findViewById(R.id.messages_list);
        mMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(mMessages);

        mMessageListView.setAdapter(mMessageAdapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String title = mRecipient.getFirstName() + " " +mRecipient.getLastName();
        setTitle(title);

        Button sendMessage = (Button) findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        String [] ids = {mRecipient.getId(),UserDataSource.getCurrentUser().getId() };
        Arrays.sort(ids);
        mConvoId = ids[0]+ ids[1];

        mListener = MessageDataSource.addMessagesListener(mConvoId, this);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MessageDataSource.stop(mListener);
    }

    @Override
    public void onMessagesAdded(com.example.thewizard.cjuliaol.bitdate.Message message) {
         mMessages.add(message);
        mMessageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        EditText newMessageView = (EditText) findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        com.example.thewizard.cjuliaol.bitdate.Message msg= new com.example.thewizard.cjuliaol.bitdate.Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(UserDataSource.getCurrentUser().getId());

        MessageDataSource.saveMessage(msg, mConvoId);

        newMessageView.setText("");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if(id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class MessageAdapter extends ArrayAdapter<com.example.thewizard.cjuliaol.bitdate.Message> {

        MessageAdapter(ArrayList<com.example.thewizard.cjuliaol.bitdate.Message> messages) {
            super(ChatActivity.this, R.layout.message, R.id.message, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
             com.example.thewizard.cjuliaol.bitdate.Message message;
            message = getItem(position);


            TextView messageText = (TextView) convertView.findViewById(R.id.message);
            messageText.setText(message.getText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageText.getLayoutParams();
            int sdk = Build.VERSION.SDK_INT;



            if (message.getSender().equals(UserDataSource.getCurrentUser().getId())) {

                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    messageText.setBackground(getDrawable(R.drawable.bubble_right_green));
                }
                else {
                    messageText.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                }

                layoutParams.gravity = Gravity.RIGHT;
            } else {

                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    messageText.setBackground(getDrawable(R.drawable.bubble_left_gray));
                }
                else {
                    messageText.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                }

                layoutParams.gravity = Gravity.LEFT;
            }

            messageText.setLayoutParams(layoutParams);

            return convertView;
        }
    }
}
