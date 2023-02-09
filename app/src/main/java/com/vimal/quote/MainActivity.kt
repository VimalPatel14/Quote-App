package com.vimal.quote

/**
 * Created by Vimal on Feb-2023.
 */

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vimal.quote.adapter.QuoteAdapter
import com.vimal.quote.api.MainRepository
import com.vimal.quote.api.RetrofitService
import com.vimal.quote.databinding.ActivityMainBinding
import com.vimal.quote.interfaces.ItemClickListener
import com.vimal.quote.model.Quote
import com.vimal.quote.viewmodel.MainViewModel
import com.vimal.quote.viewmodel.MyViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    var layoutManager: StaggeredGridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            Log.e("vml", "World")
        }
        Log.e("vml", "Hello")         // main thread continues here immediately
        runBlocking {     // but this expression blocks the main thread
            delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
        }

        val adapter = QuoteAdapter(this@MainActivity)

        binding.recyclerview.setHasFixedSize(true)
        layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerview.setLayoutManager(layoutManager)

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)
        viewModel.quoteList.observe(this) {
            binding.progressDialog.visibility = View.GONE
            adapter.setMovies(it)
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })
        viewModel.getAllQuotes()

        val sum1 = { a: Int, b: Int -> a + b }
        val result1 = sum1(7, 3)
        Toast.makeText(
            this@MainActivity, "The sum of two numbers is: $result1",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onItemClick(quote: Quote) {
        quote.let {
            Toast.makeText(
                this@MainActivity, it.text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onLongClick(quote: Quote) {
        //do long click here
    }
}