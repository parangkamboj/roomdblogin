package com.example.kitlabs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitlabs.databinding.ItemRvLayoutBinding
import com.example.kitlabs.roomdatabase.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DashboardAdapter(
    private val context: Context?
) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
        private var list: List<Response.Data.AllHarvestData?> = arrayListOf()

    private var itemPos = 0
    var today_day = 0

    inner class ViewHolder(binding: ItemRvLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding = binding
          fun bind(data: Response.Data.AllHarvestData?, position: Int) {

              binding.apply {
                idNumber.text = data?.field_number.toString()
                  beechinor.text = "Beechinor\n${data?.farm_name}"
                  acres.text = "Acres\n${data?.Acres}"
                  pattern.text = "Pattern\n${data?.pattern}"
                  estLoads.text = "Est. Loads\n${data?.estimated_loads}"
                  actualLoads.text = "Actual Loads\n${data?.estimated_loads}"
                  location.text = "Location\n${data?.stewarded}"
                  stewarded.text = "Stewarded\n${data?.stewarded}"
                  splitFields.text = "Split Field\n${data?.split_field}"
                  harvUsed.text = "Harv.Used\n${data?.harvesters_used}"
                  harStarted.text = "Harv.Started\n${data?.harvest_started?.let { formatDateTime(it) }}"
                  harCompleted.text = "Harv.Completed\n${data?.harvest_complete?.let { formatDateTime(it) }}"
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

     fun setList(list: List<Response.Data.AllHarvestData>?) {
         if (list != null) {
             this.list = list
         }
         notifyDataSetChanged()
     }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          holder.bind(list[position], position)
    }

    fun formatDateTime(input: String): String {
        // Input date format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        // Output date format
        val outputFormat = SimpleDateFormat("h:mma d.M.yy", Locale.getDefault())

        // Parse the input date string
        val date = inputFormat.parse(input)

        // Format the date into the desired output format
        return outputFormat.format(date).lowercase(Locale.getDefault())
    }

}