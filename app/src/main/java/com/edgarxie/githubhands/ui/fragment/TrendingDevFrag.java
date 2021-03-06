package com.edgarxie.githubhands.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.edgarxie.githubhands.R;
import com.edgarxie.githubhands.adapter.TrendingDevAdapter;
import com.edgarxie.githubhands.presenter.TrendingDevPresenter;
import com.edgarxie.githubhands.ui.activity.MainActivity;
import com.edgarxie.githubhands.ui.interf.ITrendingDeveloperView;
import com.edgarxie.githubhands.util.NetConstant;

/**
 * Created by edgar on 17-4-18.
 */

public class TrendingDevFrag extends BaseFragment<TrendingDevPresenter>
        implements  ITrendingDeveloperView {

    private String mFrequency= NetConstant.DAILY;
    private RecyclerView mRvDeveloper;
    private SwipeRefreshLayout mRefresh;
    protected MainActivity mActivity;
    private boolean isFirstLoad;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFirstLoad=true;

        mRvDeveloper = (RecyclerView) mView.findViewById(R.id.rv_list_repo_dev);
        mRefresh= (SwipeRefreshLayout) mView.findViewById(R.id.layout_swipe_refresh);
        mRvDeveloper.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDeveloper.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        mPresenter=new TrendingDevPresenter(getContext());
        mActivity= (MainActivity) getActivity();

        mRefresh.setOnRefreshListener(() -> mPresenter.requestDeveloper(mFrequency));
        mActivity.setDevMenuClick(id -> {
            switch (id) {
                case R.id.action_trending_daily:
                    mActivity.setDevTitle(getString(R.string.developer_daily));
                    applyFrequencyChange(NetConstant.DAILY);
                    break;
                case R.id.action_trending_weekly:
                    mActivity.setDevTitle(getString(R.string.developer_weekly));
                    applyFrequencyChange(NetConstant.WEEKLY);
                    break;
                case R.id.action_trending_monthly:
                    mActivity.setDevTitle(getString(R.string.developer_monthly));
                    applyFrequencyChange(NetConstant.MONTHLY);
                    break;
            }
        });
    }

    private void applyFrequencyChange(String frequency){
        if (!mFrequency.equals(frequency)){
            mFrequency=frequency;
            mPresenter.requestDeveloper(mFrequency);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            mPresenter.resumeRequest(mFrequency);
        }
    }

    @Override
    public void setFirstLoad(boolean firstLoad) {
        isFirstLoad = firstLoad;
    }

    @Override
    public void setRecyclerAdapter(TrendingDevAdapter adapter) {
        mRvDeveloper.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trending_list;
    }

    @Override
    protected void attachView() {
        mPresenter.attach(this);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mRefresh.setRefreshing(refreshing);
    }

    @Override
    public void refreshingPost(Runnable runnable) {
        mRefresh.post(runnable);
    }

    @Override
    public void runOnUIThread(Runnable action) {
        getActivity().runOnUiThread(action);
    }
}
