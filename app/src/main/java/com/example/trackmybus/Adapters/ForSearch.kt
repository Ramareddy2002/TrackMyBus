package com.example.trackmybus.Adapters

import android.content.Context
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmybus.Interactions.Searchable
import com.example.trackmybus.Responses.Model.Search
import com.example.trackmybus.databinding.CardbindBinding
import com.example.trackmybus.databinding.CardsearchBinding

class ForSearch(
    val context: Context,
    val data: ArrayList<Search>,
    val searchable: Searchable
) : RecyclerView.Adapter<ForSearch.Viewing>() {
    class Viewing(val item: CardsearchBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Viewing(
        CardsearchBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Viewing, position: Int) {
        with(holder.item) {
            val d = data[position]
            fromplace.text = spannable(string = "${d.fromplace}")
            toplace.text = spannable(string = "${d.toplace}")
            further.text = spannable(
                string = "<b>Start time : </b>${d.starttime}<br>" +
                        "<b>End time : </b>${d.endtime}<br>" +
                        "<b>Per km :</b> ₹ ${d.perkm} /-<br>" +
                        "<b>No of days :</b>${d.days}"
            )
           d.state?.let {selectseats.isVisible =it}
        selectseats.setOnClickListener {
            searchable.Forintent(d)
        }
        }

    }

    fun spannable(string: String): Spanned {
        return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
    }
}