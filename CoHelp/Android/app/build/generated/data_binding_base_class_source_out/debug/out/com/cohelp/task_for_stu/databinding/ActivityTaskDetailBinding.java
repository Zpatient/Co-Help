// Generated by view binder compiler. Do not edit!
package com.cohelp.task_for_stu.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cohelp.task_for_stu.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTaskDetailBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button idBtnFun;

  @NonNull
  public final ImageView idIvIcon;

  @NonNull
  public final LinearLayout idLlLeaderBoard;

  @NonNull
  public final LinearLayout idLlQuestionCenter;

  @NonNull
  public final LinearLayout idLlTaskCenter;

  @NonNull
  public final LinearLayout idLlUserCenter;

  @NonNull
  public final TextView idTvContent;

  @NonNull
  public final TextView idTvLoadMore;

  @NonNull
  public final TextView idTvNickname;

  @NonNull
  public final TextView idTvPostTime;

  @NonNull
  public final TextView idTvState;

  @NonNull
  public final TextView idTvTimeLimit;

  private ActivityTaskDetailBinding(@NonNull LinearLayout rootView, @NonNull Button idBtnFun,
      @NonNull ImageView idIvIcon, @NonNull LinearLayout idLlLeaderBoard,
      @NonNull LinearLayout idLlQuestionCenter, @NonNull LinearLayout idLlTaskCenter,
      @NonNull LinearLayout idLlUserCenter, @NonNull TextView idTvContent,
      @NonNull TextView idTvLoadMore, @NonNull TextView idTvNickname,
      @NonNull TextView idTvPostTime, @NonNull TextView idTvState,
      @NonNull TextView idTvTimeLimit) {
    this.rootView = rootView;
    this.idBtnFun = idBtnFun;
    this.idIvIcon = idIvIcon;
    this.idLlLeaderBoard = idLlLeaderBoard;
    this.idLlQuestionCenter = idLlQuestionCenter;
    this.idLlTaskCenter = idLlTaskCenter;
    this.idLlUserCenter = idLlUserCenter;
    this.idTvContent = idTvContent;
    this.idTvLoadMore = idTvLoadMore;
    this.idTvNickname = idTvNickname;
    this.idTvPostTime = idTvPostTime;
    this.idTvState = idTvState;
    this.idTvTimeLimit = idTvTimeLimit;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTaskDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTaskDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_task_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTaskDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.id_btn_fun;
      Button idBtnFun = ViewBindings.findChildViewById(rootView, id);
      if (idBtnFun == null) {
        break missingId;
      }

      id = R.id.id_iv_icon;
      ImageView idIvIcon = ViewBindings.findChildViewById(rootView, id);
      if (idIvIcon == null) {
        break missingId;
      }

      id = R.id.id_ll_leaderBoard;
      LinearLayout idLlLeaderBoard = ViewBindings.findChildViewById(rootView, id);
      if (idLlLeaderBoard == null) {
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

      id = R.id.id_tv_content;
      TextView idTvContent = ViewBindings.findChildViewById(rootView, id);
      if (idTvContent == null) {
        break missingId;
      }

      id = R.id.id_tv_loadMore;
      TextView idTvLoadMore = ViewBindings.findChildViewById(rootView, id);
      if (idTvLoadMore == null) {
        break missingId;
      }

      id = R.id.id_tv_nickname;
      TextView idTvNickname = ViewBindings.findChildViewById(rootView, id);
      if (idTvNickname == null) {
        break missingId;
      }

      id = R.id.id_tv_postTime;
      TextView idTvPostTime = ViewBindings.findChildViewById(rootView, id);
      if (idTvPostTime == null) {
        break missingId;
      }

      id = R.id.id_tv_state;
      TextView idTvState = ViewBindings.findChildViewById(rootView, id);
      if (idTvState == null) {
        break missingId;
      }

      id = R.id.id_tv_timeLimit;
      TextView idTvTimeLimit = ViewBindings.findChildViewById(rootView, id);
      if (idTvTimeLimit == null) {
        break missingId;
      }

      return new ActivityTaskDetailBinding((LinearLayout) rootView, idBtnFun, idIvIcon,
          idLlLeaderBoard, idLlQuestionCenter, idLlTaskCenter, idLlUserCenter, idTvContent,
          idTvLoadMore, idTvNickname, idTvPostTime, idTvState, idTvTimeLimit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
