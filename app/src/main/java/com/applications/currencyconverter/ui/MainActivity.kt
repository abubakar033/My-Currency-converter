package com.applications.currencyconverter.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.applications.currencyconverter.R
import com.applications.currencyconverter.data.db.entities.CurrencyEntities
import com.applications.currencyconverter.databinding.ActivityMainBinding
import com.applications.currencyconverter.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    var mList: List<CurrencyEntities> = ArrayList()
    lateinit var adapter : CurrencyRVAdapter
    lateinit var etAmount : String
    var baseCurrency: String? = null
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
adapter = CurrencyRVAdapter(mList,"1")
        progressDialog = Helper.showProgressBar(
            this,
            (getString(R.string.loading_data)),
            (getString(R.string.please_wait))
        )
        progressDialog.setCancelable(false)
        getCountryList()
        getLocal()


        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                etAmount = s.toString()

                adapter?.let {
                    it.setUpdatedValue(etAmount)
                    it.notifyDataSetChanged()
                }


        }
        })
        binding.floatingActionButton.setOnClickListener {
            baseCurrency = getSpinnerValue()
            if (CheckNetworkConnectivity.isInternetAvailable(this)) {
                if (binding.etAmount.text.toString().isNullOrEmpty()) Helper.showSnackBar(
                    this,
                    getString(R.string.enter_amount_)
                )
                else if (baseCurrency.isNullOrEmpty()) Helper.showSnackBar(
                    this,
                    getString(R.string.select_base_currency)
                )
                else {
                    sharedPreferences.setSaveValue(
                        Constants.LAST_AMOUNT,
                        binding.etAmount.text.toString()
                    )
                    sharedPreferences.setSaveValue(Constants.LAST_SOURCE, baseCurrency!!)
                    liveCurrencyCall(baseCurrency!!)
                }

            } else {
                getLocal()
            }
        }
    }


    fun getSpinnerValue(): String {
        val spinnerValue :String = binding.spinner.adapter.getItem(binding.spinner.selectedItemPosition).toString()
        if(!spinnerValue.isNullOrEmpty()) return spinnerValue
        else return ""

    }

    fun getCountryList() {
        viewModel.getCurrencyList()
        viewModel.callListResponse.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    progressDialog.dismiss()
                    if (it.data?.currencies != null) {
                        setSpinnerAdapter(it.data.currencies)
                    }
                }
                is NetworkResult.Error -> {
                    progressDialog.dismiss()
                }
                is NetworkResult.Loading -> {
                    progressDialog.show()
                }
                is NetworkResult.CheckInternet -> {
                    Helper.showSnackBar(this, getString(R.string.internet_required))
                }
                else -> {

                }
            }
        }
    }


    fun liveCurrencyCall(source: String) {
        viewModel.getCurrencyLive(source)
        viewModel.liveCurrencyResponse.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    progressDialog.dismiss()
                    if (it.data?.quotes != null) {
                        val listDb: ArrayList<CurrencyEntities> = ArrayList()
                        it.data.quotes.forEach { it ->
                            listDb.add(CurrencyEntities(0, it.key, it.value))
                        }
                        viewModel.liveGetLocalData.value = listDb
                        saveLocal(listDb)
                    }
                }
                is NetworkResult.Error -> {
                    progressDialog.dismiss()
                }
                is NetworkResult.Loading -> {
                    progressDialog.show()
                }
                is NetworkResult.CheckInternet -> {
                    Helper.showSnackBar(this, getString(R.string.internet_required))
                }
                else -> {

                }
            }
        }
    }

    fun saveLocal(list: ArrayList<CurrencyEntities>) {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.saveLocal(list)
        }
        getLocal()
    }

    fun getLocal() {
        progressDialog.show()
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getLocal()
        }
        viewModel.liveGetLocalData.observe(this) {
            progressDialog.dismiss()
            if (!it.isNullOrEmpty()) {
                setRecyclerviewAdapter(it)
            }
        }
    }

    private fun setRecyclerviewAdapter(quotes: List<CurrencyEntities>) {
        etAmount = getAmount()
        adapter = CurrencyRVAdapter(quotes, etAmount)
        binding.recyclerview.adapter = adapter
    }

    fun getAmount(): String {
        val prefAmount = sharedPreferences.getSaveValue(Constants.LAST_AMOUNT)
        val etvalue = binding.etAmount.text.toString()

        return if (!etvalue.isNullOrEmpty()) {
            binding.etAmount.text.toString()
        } else if (!prefAmount.isNullOrEmpty()) {
            prefAmount

        } else {
            "1"
        }
    }

    private fun setSpinnerAdapter(data: HashMap<Any, Any>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter


    }


}