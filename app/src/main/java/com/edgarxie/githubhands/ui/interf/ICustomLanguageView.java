package com.edgarxie.githubhands.ui.interf;

import android.os.Bundle;

import com.edgarxie.utils.android.recyclerview.BaseRVAdapter;

/**
 * Created by edgar on 17-5-3.
 */

public interface ICustomLanguageView extends ITopView {
    Bundle getBundle();
    void setAdapter(BaseRVAdapter adapter);
}
