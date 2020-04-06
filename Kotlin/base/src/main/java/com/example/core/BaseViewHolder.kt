package com.example.core

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.util.HashMap

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  @SuppressLint("UseSparseArrays")
  private val viewHashMap = HashMap<Int, View>()

  protected fun <T : View> getView(@IdRes id: Int): T {
    var view = viewHashMap[id]
    if (view == null) {
      view = itemView.findViewById(id)
      viewHashMap[id] = view
    }
    return view as T
  }

  protected fun setText(@IdRes id: Int, text: String?) {
    (getView<View>(id) as TextView).text = text
  }
}
