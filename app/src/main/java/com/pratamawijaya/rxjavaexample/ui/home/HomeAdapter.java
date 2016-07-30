package com.pratamawijaya.rxjavaexample.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pratamawijaya.rxjavaexample.R;
import com.pratamawijaya.rxjavaexample.models.pojo.Post;
import java.util.List;

/**
 * Created by pratama on 7/30/16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

  private Context context;
  private List<Post> posts;

  public HomeAdapter(Context context, List<Post> posts) {
    this.context = context;
    this.posts = posts;
  }

  @Override public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new HomeViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
  }

  @Override public void onBindViewHolder(HomeViewHolder holder, int position) {
    if (posts != null) {
      holder.bindItem(posts.get(position));
    }
  }

  @Override public int getItemCount() {
    return posts.size();
  }

  public class HomeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_title) TextView txtTitle;
    @BindView(R.id.txt_date) TextView txtDate;

    public HomeViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindItem(Post post) {
      txtTitle.setText(post.title);
      txtDate.setText("" + post.date.toString());
    }
  }
}
