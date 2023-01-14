// Generated by view binder compiler. Do not edit!
package com.cohelp.task_for_stu.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemBaseTaskBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView idTvAuthor;

  @NonNull
  public final TextView idTvContent;

  @NonNull
  public final TextView idTvGrade;

  @NonNull
  public final TextView idTvState;

  @NonNull
  public final TextView idTvTitle;

  private ItemBaseTaskBinding(@NonNull LinearLayout rootView, @NonNull TextView idTvAuthor,
      @NonNull TextView idTvContent, @NonNull TextView idTvGrade, @NonNull TextView idTvState,
      @NonNull TextView idTvTitle) {
    this.rootView = rootView;
    this.idTvAuthor = idTvAuthor;
    this.idTvContent = idTvContent;
    this.idTvGrade = idTvGrade;
    this.idTvState = idTvState;
    this.idTvTitle = idTvTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemBaseTaskBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemBaseTaskBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_base_task, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemBaseTaskBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.id_tv_author;
      TextView idTvAuthor = ViewBindings.findChildViewById(rootView, id);
      if (idTvAuthor == null) {
        break missingId;
      }

      id = R.id.id_tv_content;
      TextView idTvContent = ViewBindings.findChildViewById(rootView, id);
      if (idTvContent == null) {
        break missingId;
      }

      id = R.id.id_tv_grade;
      TextView idTvGrade = ViewBindings.findChildViewById(rootView, id);
      if (idTvGrade == null) {
        break missingId;
      }

      id = R.id.id_tv_state;
      TextView idTvState = ViewBindings.findChildViewById(rootView, id);
      if (idTvState == null) {
        break missingId;
      }

      id = R.id.id_tv_title;
      TextView idTvTitle = ViewBindings.findChildViewById(rootView, id);
      if (idTvTitle == null) {
        break missingId;
      }

      return new ItemBaseTaskBinding((LinearLayout) rootView, idTvAuthor, idTvContent, idTvGrade,
          idTvState, idTvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}