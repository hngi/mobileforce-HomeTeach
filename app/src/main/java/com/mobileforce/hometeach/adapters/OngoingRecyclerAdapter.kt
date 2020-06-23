package com.mobileforce.hometeach.adapters

import android.graphics.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemClassBinding
import com.mobileforce.hometeach.models.OngoingClassModel
import com.squareup.picasso.Transformation


class OngoingRecyclerAdapter : ListAdapter<OngoingClassModel, OngoingClassViewHolder>(DiffClass) {

    companion object DiffClass : DiffUtil.ItemCallback<OngoingClassModel>() {
        override fun areItemsTheSame(
            oldItem: OngoingClassModel,
            newItem: OngoingClassModel
        ): Boolean {
            return oldItem.subject == newItem.subject
        }

        override fun areContentsTheSame(
            oldItem: OngoingClassModel,
            newItem: OngoingClassModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngoingClassViewHolder {
        return OngoingClassViewHolder(ListItemClassBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OngoingClassViewHolder, position: Int) {
        val ongoingClassTutor = getItem(position)
        holder.bind(ongoingClassTutor)
    }


}

class OngoingClassViewHolder(private val binding: ListItemClassBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(classesOngoing: OngoingClassModel) {
        binding.item = classesOngoing
        binding.executePendingBindings()
    }

}
class CircleTransform : Transformation {

    private var x: Int = 0
    private var y: Int = 0

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        x = (source.width - size) / 2
        y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap !== source) source.recycle()
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key() = "circle(x=$x,y=$y)"
}