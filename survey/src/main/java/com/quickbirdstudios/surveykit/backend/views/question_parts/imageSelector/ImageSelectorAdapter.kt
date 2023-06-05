package com.quickbirdstudios.surveykit.backend.views.question_parts.imageSelector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.backend.views.question_parts.imageSelector.ImageSelectorPart.ImageWrapper
import com.quickbirdstudios.surveykit.databinding.LayoutImageSelectorImageBinding

internal class ImageSelectorAdapter(
    diffCallback: DiffUtil.ItemCallback<ImageWrapper> = ImageDiff
) : ListAdapter<ImageWrapper, ImageSelectorAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(binding: LayoutImageSelectorImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val border: ViewGroup = binding.border
        val image: ImageView = binding.image
    }

    @ColorRes
    var selectedColor: Int = R.color.cyan_normal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutImageSelectorImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).apply {
            this.root.id = View.generateViewId()
        }

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.bindingAdapterPosition)

        holder.image.background = AppCompatResources.getDrawable(
            holder.itemView.context, item.image.resourceId
        )

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
            else ContextCompat.getColor(this.itemView.context, R.color.transparent)
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
