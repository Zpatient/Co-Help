// Generated by view binder compiler. Do not edit!
package com.cohelp.task_for_stu.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTaskCenterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText idEtSearch;

  @NonNull
  public final ImageView idIvSearch;

  @NonNull
  public final LinearLayout idLlQuestionCenter;

  @NonNull
  public final LinearLayout idLlTaskCenter;

  @NonNull
  public final LinearLayout idLlUserCenter;

  @NonNull
  public final RecyclerView idRecyclerview;

  @NonNull
  public final SwipeRefreshLayout idSwiperefresh;

  @NonNull
  public final TextView idTvActivity;

  @NonNull
  public final TextView idTvHelp;

  @NonNull
  public final TextView idTvTreehole;

  private ActivityTaskCenterBinding(@NonNull LinearLayout rootView, @NonNull EditText idEtSearch,
      @NonNull ImageView idIvSearch, @NonNull LinearLayout idLlQuestionCenter,
      @NonNull LinearLayout idLlTaskCenter, @NonNull LinearLayout idLlUserCenter,
      @NonNull RecyclerView idRecyclerview, @NonNull SwipeRefreshLayout idSwiperefresh,
      @NonNull TextView idTvActivity, @NonNull TextView idTvHelp, @NonNull TextView idTvTreehole) {
    this.rootView = rootView;
    this.idEtSearch = idEtSearch;
    this.idIvSearch = idIvSearch;
    this.idLlQuestionCenter = idLlQuestionCenter;
    this.idLlTaskCenter = idLlTaskCenter;
    this.idLlUserCenter = idLlUserCenter;
    this.idRecyclerview = idRecyclerview;
    this.idSwiperefresh = idSwiperefresh;
    this.idTvActivity = idTvActivity;
    this.idTvHelp = idTvHelp;
    this.idTvTreehole = idTvTreehole;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTaskCenterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTaskCenterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_task_center, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTaskCenterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.id_et_search;
      EditText idEtSearch = ViewBindings.findChildViewById(rootView, id);
      if (idEtSearch == null) {
        break missingId;
      }

      id = R.id.id_iv_search;
      ImageView idIvSearch = ViewBindings.findChildViewById(rootView, id);
      if (idIvSearch == null) {
        break missingId;
      }

      id = R.id.id_ll_questionCenter;
      LinearLayout idLlQuestionCenter = ViewBindings.findChildViewById(rootView, id);
      if (idLlQuestionCenter == null) {
        break missingId;
      }

      id = R.id.id_ll_taskCenter;
      LinearLayout idLlTaskCenter = ViewBindings.findChildViewById(rootView, id);
      if (idLlTaskCenter == null) {
        break missingId;
      }

      id = R.id.id_ll_userCenter;
      LinearLayout idLlUserCenter = ViewBindings.findChildViewById(rootView, id);
      if (idLlUserCenter == null) {
        break missingId;
      }

      id = R.id.id_recyclerview;
      RecyclerView idRecyclerview = ViewBindings.findChildViewById(rootView, id);
      if (idRecyclerview == null) {
        break missingId;
      }

      id = R.id.id_swiperefresh;
      SwipeRefreshLayout idSwiperefresh = ViewBindings.findChildViewById(rootView, id);
      if (idSwiperefresh == null) {
        break missingId;
      }

      id = R.id.id_tv_activity;
      TextView idTvActivity = ViewBindings.findChildViewById(rootView, id);
      if (idTvActivity == null) {
        break missingId;
      }

      id = R.id.id_tv_help;
      TextView idTvHelp = ViewBindings.findChildViewById(rootView, id);
      if (idTvHelp == null) {
        break missingId;
      }

      id = R.id.id_tv_treehole;
      TextView idTvTreehole = ViewBindings.findChildViewById(rootView, id);
      if (idTvTreehole == null) {
        break missingId;
      }

      return new ActivityTaskCenterBinding((LinearLayout) rootView, idEtSearch, idIvSearch,
          idLlQuestionCenter, idLlTaskCenter, idLlUserCenter, idRecyclerview, idSwiperefresh,
          idTvActivity, idTvHelp, idTvTreehole);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}