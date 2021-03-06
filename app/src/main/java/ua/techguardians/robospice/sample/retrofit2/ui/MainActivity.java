package ua.techguardians.robospice.sample.retrofit2.ui;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.networkstate.DefaultNetworkStateChecker;
import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import ua.techguardians.robospice.sample.retrofit2.BuildConfig;
import ua.techguardians.robospice.sample.retrofit2.R;
import ua.techguardians.robospice.sample.retrofit2.errors.CustomRequestFailureException;
import ua.techguardians.robospice.sample.retrofit2.requests.MessageRequest;
import ua.techguardians.robospice.sample.retrofit2.services.ApiService;
import ua.techguardians.robospice.sample.retrofit2.responses.SingleMessageJson;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = BuildConfig.APPLICATION_ID;
    private static final long MESSAGE_ID = 1L;
    private SpiceManager mSpiceManager = new SpiceManager(ApiService.class);
    private Button mBtnExecuteRequest;
    private ProgressBar mProgressBar;
    private TextView mTextInfoMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextInfoMessage = (TextView) findViewById(R.id.text_info_message);
        mBtnExecuteRequest = (Button) findViewById(R.id.btn_do_request);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        clearInfoMessage();
        mBtnExecuteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequest();
            }
        });
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSpiceManager.shouldStop();
    }


    private void executeRequest() {
        enableRequestButton(false);
        showProgress(true);
        showInfoMessage("");
        final MessageRequest request = new MessageRequest(MESSAGE_ID);
        final RequestListener<SingleMessageJson> listener = new RequestListener<SingleMessageJson>() {
            @SuppressLint({"DefaultLocale", "LongLogTag"})
            @Override
            public void onRequestFailure(SpiceException spiceException) {

                // If we throw an instance of our custom Exception implementation from MessageRequest.loadDataFromNetwork() method
                // we can get an instance of that class and analyze it's data.

                Throwable rootException = spiceException != null ? spiceException.getCause() : null;
                CustomRequestFailureException requestFailureException = (CustomRequestFailureException) rootException;
                String message = null;

                if (requestFailureException != null) {

                    // Here we can get an HTTP status code and do custom actions depending on it.
                    // For the sake of example lets just print it to a log output.

                    final int httpStatusCode = requestFailureException.getHttpStatusCode();
                    final String serverMessage = requestFailureException.getMessage();
                    final String errorBody = requestFailureException.getErrorBody();
                    message = String.format("Request failed: http_status=%d," +
                            " message='%s'," +
                            " errorBody='%s'",
                            httpStatusCode,
                            serverMessage,
                            errorBody);
                } else {
                    message = String.format("Request failed: '%s'",
                            spiceException != null ? spiceException.getMessage() : "null");
                }

                Log.e(LOG_TAG, message);
                enableRequestButton(true);
                showInfoMessage(message);
                showProgress(false);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onRequestSuccess(SingleMessageJson singleMessageJson) {
                String result = (singleMessageJson != null) ? singleMessageJson.toString() : "null";
                String message = String.format("Request result is '%s'", result);
                Log.d(LOG_TAG, message);
                enableRequestButton(true);
                showInfoMessage(message);
                showProgress(false);
            }
        };
        NetworkStateChecker networkStateChecker = new DefaultNetworkStateChecker();
        if (!networkStateChecker.isNetworkAvailable(this)) {
            mSpiceManager.getFromCache(SingleMessageJson.class, request.getCacheKey(), DurationInMillis.ALWAYS_RETURNED, listener);
        } else {
            mSpiceManager.execute(request, request.getCacheKey(), DurationInMillis.ALWAYS_EXPIRED, listener);
        }
    }

    private void showProgress(boolean show) {
        if (mProgressBar == null) {
            return;
        }
        mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    private void enableRequestButton(boolean enable) {
        if (mBtnExecuteRequest == null) {
            return;
        }
        mBtnExecuteRequest.setEnabled(enable);
    }

    private void showInfoMessage(String messageText) {
        if (mTextInfoMessage == null) {
            return;
        }
        mTextInfoMessage.setText(messageText);
    }

    private void clearInfoMessage() {
        showInfoMessage("");
    }

}
