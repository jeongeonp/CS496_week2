package com.example.q.trialtwo;

import com.facebook.FacebookSdk;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FbLogin extends AppCompatActivity {

    CallbackManager callbackManager;
    TextView txtEmail, txtBirthday, txtFriends;
    ProgressDialog mDialog;
    ImageView imgAvatar;
    Button logout_button, fb, game;
    String email, birthday, friends, name;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fblogin);

        callbackManager = CallbackManager.Factory.create();

        txtBirthday = (TextView) findViewById(R.id.txtBirthday);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtFriends = (TextView) findViewById(R.id.txtFriends);

        imgAvatar = (ImageView) findViewById(R.id.avatar);
        logout_button = (Button) findViewById(R.id.logout_button);
        //login_button = (Button)  findViewById(R.id.login_button);
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        fb = (Button) findViewById(R.id.fb);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        /*public void onClickFacebookButton(View view) {
            if (view == fb) {
                loginButton.performClick();
            }
        }*/

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(FbLogin.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        getData(object);

                        try {
                            name = object.getString("name");
                            email = object.getString("email");
                            birthday = object.getString("birthday");
                            friends = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");

                            Log.i("RESULTS : ", object.getString("email"));
                        }catch (Exception e){

                        }
                        //Intent intent = new Intent(FbLogin.this, game.class);
                        //startActivity(intent);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,birthday,friends,name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        //If already logged in
        if(AccessToken.getCurrentAccessToken() != null) {
            //imgAvatar.setVisibility(View.VISIBLE);
            txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                //imgAvatar.setVisibility(View.INVISIBLE);
                imgAvatar.setImageResource(R.drawable.def);
                txtEmail.setText("Logged out ;o");
                txtBirthday.setText("");
                txtFriends.setText("");
                //AccessToken.getCurrentAccessToken() = null;

            }
        });

        game = (Button) findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FbLogin.this, game.class);
                intent.putExtra("name",name);
                intent.putExtra("email", email);
                intent.putExtra("birthday", birthday);
                intent.putExtra("friends", friends);
                startActivity(intent);
            }
        });

    }



    public void launchGame(View view) {
        Intent intent = new Intent(this, game.class);
        startActivity(intent);
    }

    private void getData(JSONObject object) {
        try{
            //Toast.makeText(this, object.getString("email"), Toast.LENGTH_SHORT).show();
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id")+"/picture?width=250&height=250");

            Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);


            txtEmail.setText(object.getString("email"));
            txtBirthday.setText(object.getString("birthday"));
            txtFriends.setText("Friends: " + object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.q.fblogin",PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}