package gaderesa.com.demoappgeogaderesav2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gaderesa.com.demoappgeogaderesav2.conecctions.LoginConnections;
import gaderesa.com.demoappgeogaderesav2.domains.Oprj;
import gaderesa.com.demoappgeogaderesav2.domains.Oslp;
import gaderesa.com.demoappgeogaderesav2.util.RequestQueueSingleton;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private ProgressDialog progressDialog;;
    private LoginConnections loginConnections = new LoginConnections();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Login.this);

         SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(Login.this);
        String userName = sharedPreferencesBasicAuth.getUser("userName");
        String password = sharedPreferencesBasicAuth.getPass("password");

        if (userName.length()>0 && password.length()>0) {

            //loginConnections.login(progressDialog, Login.this,userName,password);
            Intent intent = Index.newinIntent(Login.this);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.activity_login);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // Set up the login form.
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {

                        return true;
                    }
                    return false;
                }
            });

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mEmailView.getText().toString().equals("") || mPasswordView.getText().toString().equals(""))
                        Toast.makeText(Login.this, R.string.toast_invalido, Toast.LENGTH_LONG).show();
                    else
                    {
                        progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMax(20);
                    progressDialog.setMessage("Identificando....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        loginConnections.login(progressDialog, Login.this,mEmailView.getText().toString(),mPasswordView.getText().toString());
                                        // onLoginFailed();

                                    }
                                }, 3000);
                        }

                }


            });
        }

    }








}

