package com.edgarxie.bindingtest.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.edgarxie.bindingtest.BR;

/**
 * Created by edgar on 17-4-6.
 */

public class FormModel extends BaseObservable {
    private String name;
    private String password;

    public FormModel(String name,String password){
        this.name=name;
        this.password=password;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
