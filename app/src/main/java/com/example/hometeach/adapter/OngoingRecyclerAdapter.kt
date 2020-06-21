package com.example.hometeach.adapter

import android.graphics.*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hometeach.R
import com.example.hometeach.datamodel.OngoingClassModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class OngoingRecyclerAdapter(private val list: List<OngoingClassModel>)
    : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val classModel: OngoingClassModel = list[position]
        holder.bind(classModel)
    }

    override fun getItemCount(): Int = list.size

}
class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_class, parent, false)) {

    private var subjectView: TextView? = null
    private var dateView: TextView? = null
    private var timeView: TextView? = null
    private var tutorNameView: TextView? = null
    private var tutorImageView: ImageView? = null
    private var tutorSubjectView: TextView? = null
    private var progressView: ProgressBar? = null


    init {
        subjectView = itemView.findViewById(R.id.subject_name)
        dateView = itemView.findViewById(R.id.class_date)
        timeView = itemView.findViewById(R.id.class_time)
        tutorNameView = itemView.findViewById(R.id.tutor_name)
        tutorImageView = itemView.findViewById(R.id.tutor_image)
        tutorSubjectView = itemView.findViewById(R.id.tutor_subject)
        progressView = itemView.findViewById(R.id.class_progress)
    }

    fun bind(classmodel: OngoingClassModel) {
        subjectView?.text = classmodel.subject
        dateView?.text = classmodel.date
        timeView?.text = classmodel.time
        tutorNameView?.text = classmodel.tutorName
        tutorSubjectView?.text = classmodel.tutorSubject
        progressView?.progress = classmodel.progress

        Picasso.get().load(classmodel.tutorImage).transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(tutorImageView)
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