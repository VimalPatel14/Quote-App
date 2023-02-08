package com.vimal.quote.adapter

/**
 * Created by Vimal on Feb-2023.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vimal.quote.R
import com.vimal.quote.databinding.AdapterQuoteBinding
import com.vimal.quote.interfaces.ItemClickListener
import com.vimal.quote.model.Quote

class QuoteAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    private lateinit var binding: AdapterQuoteBinding
    var movieList = mutableListOf<Quote>()

    fun setMovies(movies: List<Quote>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_quote, parent, false)
        return ViewHolder(inflater)
//        binding = AdapterQuoteBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent, false
//        )
//        return ViewHolder(binding)
    }

//    inner class ViewHolder(binding: AdapterQuoteBinding) : RecyclerView.ViewHolder(binding.root)

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val author: TextView = itemView.findViewById(R.id.author)
        val mainlay: CardView = itemView.findViewById(R.id.mainlay)
        val quote: TextView = itemView.findViewById(R.id.quote)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        movieList[position].let {
            when(it.author) {
                null -> holder.author.visibility = View.GONE
                else -> holder.author.text = it.author
            }
            holder.quote.text = it.text
            holder.mainlay.setOnClickListener() {
                    var pos = position
                    pos++
                    itemClickListener.onItemClick(movieList[position])
                }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
