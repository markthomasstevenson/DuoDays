package uk.co.qiiq.duodays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_content.view.*
import uk.co.qiiq.duodays.model.Content


class ContentAdapter(private var deleteAction: (String) -> Unit,
                     private var dataSet : MutableList<Content> =  mutableListOf()) :
    RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {


    class ContentViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {
        private var idStore = ""
        private var textTitle: TextView? = null
        private var textContent: TextView? = null
        private var buttonDelete: ImageButton? = null

        init {
            textTitle = itemView.textview_card_title
            textContent = itemView.textview_card_content
            buttonDelete = itemView.button_delete
        }

        fun setup(id: String, title: String, content: String, action: (String) -> Unit) {
            idStore = id
            textTitle?.text = title
            textContent?.text = content
            buttonDelete?.setOnClickListener {
                action(idStore)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_content, parent, false)
        return ContentViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.setup(dataSet[position].id, dataSet[position].title, dataSet[position].content, deleteAction)
    }

    override fun getItemCount() = dataSet.size

    fun updateContent(data: List<Content>) {
        dataSet = data.toMutableList()
        notifyDataSetChanged()
    }
}