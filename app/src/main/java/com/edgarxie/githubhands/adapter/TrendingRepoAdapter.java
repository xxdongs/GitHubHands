package com.edgarxie.githubhands.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.edgarxie.githubhands.R;
import com.edgarxie.githubhands.model.DbCollectionMode;
import com.edgarxie.githubhands.model.bean.TrendingRepoBean;
import com.edgarxie.githubhands.util.Constant;
import com.edgarxie.utils.android.ToastUtil;
import com.edgarxie.utils.android.recyclerview.BaseRVAdapter;

import java.util.List;

/**
 * Created by edgar on 17-5-3.
 */

public class TrendingRepoAdapter extends BaseRVAdapter<TrendingRepoBean,BaseRVAdapter.SparseArrayViewHolder> {
    private Context mContext;

    public TrendingRepoAdapter(Context context){
        mContext=context;
    }
    @Override
    protected void bindDataToItemView(SparseArrayViewHolder holder, TrendingRepoBean item) {
        holder.setText(R.id.item_repo,item.getRepo());
        holder.setText(R.id.item_desc,item.getDesc());
        holder.setText(R.id.item_lang,item.getLang());
        holder.setText(R.id.item_stars,item.getStars());
        holder.setText(R.id.item_forks,item.getForks());
        holder.setText(R.id.item_added_stars,item.getAddedStars());
        boolean selected=DbCollectionMode.isRepoCollected(item.getRepo());
        if (!selected){
            holder.setImageBackground(R.id.item_collect
                    ,mContext.getDrawable(R.drawable.collection_heart_unselected));
        }else {
            holder.setImageBackground(R.id.item_collect
                    ,mContext.getDrawable(R.drawable.collection_heart_selected));
        }
        setAvatarsUrl(holder,item.getAvatars());
        holder.setOnClickListener(R.id.item_collect, v -> {
            boolean select=DbCollectionMode.isRepoCollected(item.getRepo());
            if (!select){
                holder.setImageBackground(R.id.item_collect
                        ,mContext.getDrawable(R.drawable.collection_heart_selected));
                DbCollectionMode.repoCollected(item);
                ToastUtil.show(mContext, Constant.COLLECTED);
            }else {
                holder.setImageBackground(R.id.item_collect
                        ,mContext.getDrawable(R.drawable.collection_heart_unselected));
                DbCollectionMode.repoUncollected(item);
                ToastUtil.show(mContext,Constant.UNCOLLECTED);
            }
        });
    }


    @Override
    public SparseArrayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SparseArrayViewHolder holder=new SparseArrayViewHolder(inflateItemView(parent, R.layout.item_trending_repo));
        return holder;
    }

    //set the avatars url
    public void setAvatarsUrl(SparseArrayViewHolder holder, List<String> avatars) {
        LinearLayout linearLayout=holder.getView(R.id.item_avatars);
        int avatarSize= (int) mContext.getResources().getDimension(R.dimen.avatar_small_size);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(avatarSize,avatarSize);
        for (int i = 0; i < avatars.size(); i++) {
            ImageView image=new ImageView(mContext);
            linearLayout.addView(image,params);
        }
        for (int i = 0; i < avatars.size(); i++) {
            Glide.with(mContext).load(avatars.get(i))
                    .into((ImageView) linearLayout.getChildAt(i));
        }
    }

}
