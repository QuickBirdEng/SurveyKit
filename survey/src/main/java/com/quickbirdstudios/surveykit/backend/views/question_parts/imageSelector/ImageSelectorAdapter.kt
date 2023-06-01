package com.quickbirdstudios.surveykit.backend.views.question_parts.imageSelector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.backend.views.question_parts.imageSelector.ImageSelectorPart.ImageWrapper

internal class ImageSelectorAdapter(
    diffCallback: DiffUtil.ItemCallback<ImageWrapper> = ImageDiff
) : ListAdapter<ImageWrapper, ImageSelectorAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val border: ViewGroup = view.findViewById(R.id.border)
        val image: ImageView = view.findViewById(R.id.image)

        companion object {
            val Layout = R.layout.layout_image_selector_image
        }
    }

    @ColorRes
    var selectedColor: Int = R.color.cyan_normal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(ViewHolder.Layout, parent, false).apply {
            id = View.generateViewId()
        }
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)

        holder.image.background = holder.view.context.getDrawable(item.image.resourceId)

        holder.selected(item.selected)

        holder.image.setOnClickListener {
            holder.selected(!item.selected)
            item.selected = !item.selected
        }
    }

    //region Private API

    private fun ViewHolder.selected(isSelected: Boolean) {
        this.border.setBackgroundColor(
            if (isSelected) selectedColor
            else ContextCompat.getColor(this.view.context, R.color.transparent)
        )
    }

    //endregion
}

//region Private Objects

private object ImageDiff : DiffUtil.ItemCallback<ImageWrapper>() {
    override fun areItemsTheSame(oldItem: ImageWrapper, newItem: ImageWrapper): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ImageWrapper, newItem: ImageWrapper): Boolean =
        oldItem == newItem
}

//endregion
