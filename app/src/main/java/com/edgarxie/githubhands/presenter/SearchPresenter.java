package com.edgarxie.githubhands.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edgarxie.githubhands.adapter.SearchRepoAdapter;
import com.edgarxie.githubhands.model.bean.JsonSearchRepoBean;
import com.edgarxie.githubhands.model.bean.SearchRepoBean;
import com.edgarxie.githubhands.ui.activity.WebRepoDevAty;
import com.edgarxie.githubhands.ui.interf.ISearchView;
import com.edgarxie.githubhands.util.Constant;
import com.edgarxie.githubhands.util.NetConstant;
import com.xxdong.ok.OkManagerBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dofor on 2017/7/5.
 */

public class SearchPresenter extends BasePresenter<ISearchView> {
    private List<SearchRepoBean> repos=new ArrayList<>();
    private SearchRepoAdapter mAdapter;

    public SearchPresenter(Context context){
        mContext=context;
        mAdapter=new SearchRepoAdapter(mContext);
    }

    public void textSubmit(String query) {
        OkManagerBean<JsonSearchRepoBean> okManagerBean=new OkManagerBean<>();
        Map<String,String> p=new HashMap<>();
        p.put("q",query);
        mView.setVisibility(View.VISIBLE);
        try {
            okManagerBean.getAsync(NetConstant.GITHUB_BASE_API
                    + NetConstant.GITHUB_SEARCH_REPO, p, JsonSearchRepoBean.class, data -> {
                    int count=data.getTotalCount();
                if (count!=0){
                    repos=data.getItems();
                    mAdapter.setList(repos);
                    mView.setAdapter(mAdapter);
                }else {
                    mView.showToast("No result for the search");
                }
                mView.setVisibility(View.GONE);
            }, msg -> {
                mView.showToast(NetConstant.CONNECT_ERROR);
                mView.setVisibility(View.GONE);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void textChange(String newText) {

    }

    public void setAdapterListener(){
        mAdapter.setOnItemClickedListener((view, item) -> {
            SearchRepoBean bean= (SearchRepoBean) item;
            Intent intent=new Intent(mContext, WebRepoDevAty.class);
            Bundle bundle=new Bundle();
            bundle.putBoolean(Constant.BUNDLE_IS_REPO,true);
            bundle.putString(Constant.BUNDLE_WEB_URL,bean.getHtmlUrl());
            bundle.putString(Constant.BUNDLE_REPO,bean.getFullName());
            bundle.putString(Constant.BUNDLE_REPO_LANG,bean.getLanguage());
            bundle.putString(Constant.BUNDLE_REPO_DESC,bean.getDescription());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }
}
