package com.vimal.quote.interfaces

/**
 * Created by Vimal on Feb-2023.
 */

import com.vimal.quote.model.Quote

interface ItemClickListener {
        fun onItemClick(position: Quote)
        fun onLongClick(position: Quote)
}