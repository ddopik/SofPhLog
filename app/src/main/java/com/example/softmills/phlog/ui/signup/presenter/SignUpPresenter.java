package com.example.softmills.phlog.ui.signup.presenter;

import java.util.HashMap;

public interface SignUpPresenter {

    void getAllCounters();

    void signUpUser(HashMap<String,String> signUpData);
}
