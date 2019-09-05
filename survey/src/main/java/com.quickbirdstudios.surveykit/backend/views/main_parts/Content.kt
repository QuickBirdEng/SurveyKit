package com.quickbirdstudios.surveykit.backend.views.main_parts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.SurveyTheme

class Content @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleRes: Int = 0
) : ScrollView(context, attrs, defStyleRes), StyleablePart {


    //region Member

    private val root: View = View.inflate(context, R.layout.layout_content, this)
    private val container: ViewGroup = root.findViewById(R.id.content_container)
    private val footerContainer: ViewGroup = root.findViewById(R.id.footer_container)
    private val footer: Footer = Footer(context).apply { id = R.id.questionFooter }

    //endregion


    //region Public API

    fun <T : View> add(view: T): T {
        container.addView(view)
        return view
    }

    fun clear() {
        container.removeAllViews()
    }

    //endregion


    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        (0 until container.childCount).forEach {
            (container.getChildAt(it) as StyleablePart).style(surveyTheme)
        }
        footer.style(surveyTheme)
    }

    //endregion

    init {
        footerContainer.addView(footer)
    }
}
