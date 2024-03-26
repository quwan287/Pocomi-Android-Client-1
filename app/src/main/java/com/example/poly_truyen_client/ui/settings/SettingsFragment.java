package com.example.poly_truyen_client.ui.settings;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poly_truyen_client.LoginActivity;
import com.example.poly_truyen_client.MainActivity;
import com.example.poly_truyen_client.R;
import com.example.poly_truyen_client.api.ConnectAPI;
import com.example.poly_truyen_client.api.UserServices;
import com.example.poly_truyen_client.models.User;
import com.example.poly_truyen_client.utils.DataConvertion;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private User loggedUser;
    private TextView tvEmail, tvName, tvUsername;
    private ImageView ivAvatar, previewAvatar;
    private Dialog dialog;
    private File imageFile;
    private UserServices userServices;
    private ImageView imageView;
    private Uri selectedImageUri;
    private ActivityResultLauncher<String> launcher;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    private File getFileFromUri(Uri uri) {
        String filePath = null;
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return new File(filePath);
    }

    void loadUser() {
        loggedUser = new Gson().fromJson(sharedPreferences.getString("user", ""), User.class);

        tvName.setText(loggedUser.getFullName());
        tvEmail.setText(loggedUser.getEmail());
        tvUsername.setText(loggedUser.getUsername());

        if (!loggedUser.getAvatar().equals("") && loggedUser.getAvatar() != null) {
            Picasso.get().load(new ConnectAPI().API_URL + "images/" + loggedUser.getAvatar()).into(ivAvatar);
        } else {
            Picasso.get().load(new ConnectAPI().API_URL + "images/" + "avatar-placeholder.png").into(ivAvatar);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userServices = new ConnectAPI().connect.create(UserServices.class);
        sharedPreferences = view.getContext().getSharedPreferences("poly_comic", Context.MODE_PRIVATE);

        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUsername = view.findViewById(R.id.tvUsername);

//        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(),
//                new ActivityResultCallback<Uri>() {
//                    @Override
//                    public void onActivityResult(Uri result) {
//                        Picasso.get().load(result).into(previewAvatar);
////                        imageFile = new File(result.getPath());
////                        selectedImageUri = result;
//                    }
//                });

        loadUser();

        // logout click
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {

            Dialog confirmDialog = new Dialog(view.getContext());
            confirmDialog.setContentView(R.layout.dialog_action_confirmation);

            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView message = confirmDialog.findViewById(R.id.tvMessage);
            message.setText("You will have to log in again when you want to continue accessing the comic reading service");

            confirmDialog.findViewById(R.id.textBtnCancel).setOnClickListener(vClick -> {
                confirmDialog.dismiss();
            });

            confirmDialog.findViewById(R.id.textBtnConfirm).setOnClickListener(vClick -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", null);
                editor.apply();
                startActivity(new Intent(view.getContext(), LoginActivity.class));
                getActivity().finish();
            });

            confirmDialog.show();
        });

        // change information button click
        view.findViewById(R.id.btnChangeInformation).setOnClickListener(v -> {
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_user_change_information);
            new DataConvertion().decorDialogBackground(dialog);

            ImageView ivAvatar;
            EditText edFullName, edUsername, edEmail, edPassword;

            previewAvatar = dialog.findViewById(R.id.ivAvatar);
            ivAvatar = dialog.findViewById(R.id.ivAvatar);
            edFullName = dialog.findViewById(R.id.edFullName);
            edUsername = dialog.findViewById(R.id.edUserName);
            edEmail = dialog.findViewById(R.id.edEmail);
            edPassword = dialog.findViewById(R.id.edPassword);

            if (!loggedUser.getAvatar().equals("") && loggedUser.getAvatar() != null) {
                Picasso.get().load(new ConnectAPI().API_URL + "images/" + loggedUser.getAvatar()).into(ivAvatar);
            } else {
                Picasso.get().load(new ConnectAPI().API_URL + "images/" + "avatar-placeholder.png").into(ivAvatar);
            }

            edFullName.setText(loggedUser.getFullName());
            edUsername.setText(loggedUser.getUsername());
            edEmail.setText(loggedUser.getEmail());
            edPassword.setText(loggedUser.getPassword());

            ivAvatar.setOnClickListener(vClick -> {
//                launcher.launch("image/*");
            });

            // btn save change
            dialog.findViewById(R.id.textBtnApplyChange).setOnClickListener(vClick -> {
                String fullName = edFullName.getText().toString().trim();
                String username = edUsername.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(v.getContext(), "Please do not leave empty the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User userPayload = new User(username, email, password, fullName);
                Call<User> callUpdate = userServices.updateUserInformation(loggedUser.get_id(), userPayload);
                callUpdate.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            User updatedUser = response.body();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user", new Gson().toJson(updatedUser));
                            editor.apply();
                            loadUser();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(view.getContext(), "Error, email or username is already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable throwable) {
                        Toast.makeText(view.getContext(), "Error from server, unknown error please try again or contact to admin!", Toast.LENGTH_SHORT).show();
                    }
                });
//                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), getFileFromUri(selectedImageUri));
//                MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar", imageFile.getName(), requestFile);

//                Call<User> call = userServices.updateUserAvatar(avatar, loggedUser.get_id());
//                call.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            User userUpdated = response.body();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable throwable) {
//                        throwable.printStackTrace();
//                        Toast.makeText(getContext(), "Failed !", Toast.LENGTH_SHORT).show();
//                    }
//                });

            });

            // cancel change
            dialog.findViewById(R.id.textBtnCancel).setOnClickListener(vClick -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loggedUser = new Gson().fromJson(sharedPreferences.getString("user", ""), User.class);
    }

}