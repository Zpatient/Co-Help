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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityItemQuestionDetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button idBtnRepeat;

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
  public final RecyclerView idRecyclerview;

  @NonNull
  public final SwipeRefreshLayout idSwiperefresh;

  @NonNull
  public final TextView idTvContent;

  @NonNull
  public final TextView idTvNickname;

  @NonNull
  public final TextView idTvPostTime;

  private ActivityItemQuestionDetailsBinding(@NonNull LinearLayout rootView,
      @NonNull Button idBtnRepeat, @NonNull ImageView idIvIcon,
      @NonNull LinearLayout idLlLeaderBoard, @NonNull LinearLayout idLlQuestionCenter,
      @NonNull LinearLayout idLlTaskCenter, @NonNull LinearLayout idLlUserCenter,
      @NonNull RecyclerView idRecyclerview, @NonNull SwipeRefreshLayout idSwiperefresh,
      @NonNull TextView idTvContent, @NonNull TextView idTvNickname,
      @NonNull TextView idTvPostTime) {
    this.rootView = rootView;
    this.idBtnRepeat = idBtnRepeat;
    this.idIvIcon = idIvIcon;
    this.idLlLeaderBoard = idLlLeaderBoard;
    this.idLlQuestionCenter = idLlQuestionCenter;
    this.idLlTaskCenter = idLlTaskCenter;
    this.idLlUserCenter = idLlUserCenter;
    this.idRecyclerview = idRecyclerview;
    this.idSwiperefresh = idSwiperefresh;
    this.idTvContent = idTvContent;
    this.idTvNickname = idTvNickname;
    this.idTvPostTime = idTvPostTime;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityItemQuestionDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityItemQuestionDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_item_question_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityItemQuestionDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.id_btn_repeat;
      Button idBtnRepeat = ViewBindings.findChildViewById(rootView, id);
      if (idBtnRepeat == null) {
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

      id = R.id.id_tv_content;
      TextView idTvContent = ViewBindings.findChildViewById(rootView, id);
      if (idTvContent == null) {
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

      return new ActivityItemQuestionDetailsBinding((LinearLayout) rootView, idBtnRepeat, idIvIcon,
          idLlLeaderBoard, idLlQuestionCenter, idLlTaskCenter, idLlUserCenter, idRecyclerview,
          idSwiperefresh, idTvContent, idTvNickname, idTvPostTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
