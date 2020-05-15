package com.quickbirdstudios.surveykit.backend.views.question_parts.imageSelector

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickbirdstudios.surveykit.ImageChoice
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

internal class ImageSelectorPart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {

    //region Members

    private val recyclerView: RecyclerView
    private val adapter: ImageSelectorAdapter

    private val list: MutableList<ImageWrapper> = mutableListOf()

    //endregion

    //region Public API

    fun update(imageList: List<ImageChoice>) {
        list.addAll(imageList.map { ImageWrapper(it, false) })
        adapter.submitList(list)
    }

    var numberOfColumns: Int = 4
        get() = recyclerViewLayoutManager.spanCount
        set(nr) {
            recyclerView.setLayoutManager(nr)
            field = nr
        }

    fun isOneSelected(): Boolean = list.any { it.selected }

    var selected: List<Int> = emptyList()
        get() = list
            .mapIndexed { index, item -> index to item }
            .filter { it.second.selected }
            .map { it.first }
        set(selectedList) {
            list.mapIndexed { index, imageWrapper ->
                imageWrapper.selected = selectedList.contains(index)
            }
            adapter.notifyDataSetChanged()
            field = selectedList
        }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        adapter.selectedColor = surveyTheme.themeColor
    }

    //endregion

    //region Private API

    private val recyclerViewLayoutManager: GridLayoutManager
        get() = this.recyclerView.layoutManager as GridLayoutManager

    private fun RecyclerView.setLayoutManager(cols: Int) {
        this.layoutManager = GridLayoutManager(context, cols)
        this.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    //endregion

    //region Subtypes

    internal class ImageWrapper(val image: ImageChoice, var selected: Boolean)

    //endregion

    init {
        recyclerView = RecyclerView(context).apply {
            id = R.id.imageSelectorPart
            setLayoutManager(4)
        }

        adapter = ImageSelectorAdapter()
        recyclerView.adapter = adapter

        layoutParams =
            LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply { this.gravity = Gravity.CENTER }
        addView(recyclerView)
    }
}
