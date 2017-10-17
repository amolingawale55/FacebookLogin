package com.example.admin.fb;


import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.AccessToken;

import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

        //private CallbackManager callbackManager;
//    private AccessTokenTracker accessTokenTracker;
//    private ProfileTracker profileTracker;
    private LoginButton loginButton;
   // private String firstName, lastName, email, birthday, gender;
    private URL profilePicture;
    private String userId;
    private String TAG = "LoginActivity";
    String resopnsecode;
    //    private Facebook facebook;
//    private AsyncFacebookRunner mAsyncRunner;

    private SharedPreferences mPrefs;
    String access_token;
    String FILENAME = "AndroidSSO_data";
    private LoginButton btnLogin;
    private CallbackManager callbackManager;
    private ProfilePictureView profilePictureView;
    private LinearLayout infoLayout;
    private TextView email;
    private TextView gender;
    private TextView facebookName;
    String phone="phone";
    private static final List<String> PERMISSIONS = Arrays.asList("public_profile","email","user_birthday,user_location,user_status");
    Button buttonphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        //btnLogin = (LoginButton)findViewById(R.id.login_button);
        btnLogin = (LoginButton)findViewById(R.id.login_button);
        buttonphone = (Button) findViewById(R.id.btnphone);
       // btnLogin.setReadPermissions(Arrays.asList("user_birthday"));
        email = (TextView)findViewById(R.id.email);
        facebookName = (TextView)findViewById(R.id.name);
        gender = (TextView)findViewById(R.id.gender);
        infoLayout = (LinearLayout)findViewById(R.id.layout_info);
        profilePictureView = (ProfilePictureView)findViewById(R.id.image);
      //  AccessToken Token=AccessToken.getCurrentAccessToken();
       // Log.d("TAG=","token="+Token);

        TelephonyManager tMgr = (TelephonyManager)LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        buttonphone.setText(mPhoneNumber);
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.admin.fb", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign= Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
                //  Toast.makeText(getApplicationContext(),sign,     Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {

        }

        buttonphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ///////

        btnLogin.setReadPermissions(PERMISSIONS);
        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v(TAG, response.toString());


                                JSONObject jsonObject=response.getJSONObject();
                                Log.v(TAG, "========="+jsonObject);

                                try {
                                  //  JSONObject jsonObject=new JSONObject(response.toString());
                                   // JSONObject jsonObject = new JSONObject(String.valueOf(response.getJSONObject()));
                                    String Emailid=jsonObject.getString("email");
                                    Log.d(TAG,"Emailid="+Emailid);
                                   // Log.d(TAG,"UserUniqueKey="+UserUniqueKey);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                setProfileToView(object);
                            }
                        });
                Bundle parameters = new Bundle();
                //user_mobile_phone
                parameters.putString("fields","id,name,email,gender,locale,timezone,first_name,last_name,age_range,birthday,verified");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "error to Login Facebook", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setProfileToView(JSONObject jsonObject) {
        try {
            email.setText(jsonObject.getString("email"));
            gender.setText(jsonObject.getString("gender"));
            facebookName.setText(jsonObject.getString("name"));

            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setProfileId(jsonObject.getString("id"));
            infoLayout.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        callbackManager.onActivityResult(requestCode, responseCode, intent);
    }
}