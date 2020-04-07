package com.example.lesson

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.BaseView
import com.example.core.utils.CacheUtils
import com.example.lesson.entity.Lesson
import kotlin.reflect.KProperty

class LessonActivity : AppCompatActivity(),
    BaseView<LessonPresenter>,
    Toolbar.OnMenuItemClickListener {

  var token: String by Saver("token")

  class Saver(var token: String) {
    operator fun getValue(
      lessonActivity: LessonActivity,
      property: KProperty<*>
    ): String {
      return CacheUtils.get(token)!!
    }

    operator fun setValue(
      lessonActivity: LessonActivity,
      property: KProperty<*>,
      s: String
    ) {
      CacheUtils.save(token, s)
    }

  }

  override val presenter: LessonPresenter by lazy {
    LessonPresenter(this)
  }

  private val lessonAdapter = LessonAdapter()

  private var refreshLayout: SwipeRefreshLayout? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_lesson)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    toolbar.inflateMenu(R.menu.menu_lesson)
    toolbar.setOnMenuItemClickListener(this)

    findViewById<RecyclerView>(R.id.list).also {
      it.layoutManager = LinearLayoutManager(this)
      it.adapter = lessonAdapter
      it.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }

    refreshLayout = findViewById(R.id.swipe_refresh_layout)
    refreshLayout!!.setOnRefreshListener { presenter.fetchData() }
    refreshLayout!!.isRefreshing = true

    presenter.fetchData()
  }

  fun showResult(lessons: List<Lesson>) {
    lessonAdapter.updateAndNotify(lessons)
    refreshLayout!!.isRefreshing = false
  }

  override fun onMenuItemClick(item: MenuItem): Boolean {
    presenter.showPlayback()
    return false
  }

}


