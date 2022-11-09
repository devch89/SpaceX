package com.example.spacexapitest.view.adapter

import android.icu.util.VersionInfo
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexapitest.R
import com.example.spacexapitest.databinding.SpacexLaunchesItemLayoutBinding
import com.example.spacexapitest.model.remote.LaunchItem
import com.example.spacexapitest.model.remote.LaunchRocket
import com.squareup.picasso3.Picasso
import com.squareup.picasso3.RequestHandler
import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

private const val TAG = "RocketAdapter"
class RocketAdapter(private val dataSet: List<LaunchRocket>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class RocketViewHolder(private val binding: SpacexLaunchesItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root){
        fun onBind(dataItem: LaunchRocket){
            binding.apply {

                tvSpxDateTime.text = launchDate(dataItem)
                tvSpxRocket.text = "${dataItem.rocketResponse.name} / ${dataItem.rocketResponse.type}"
                tvSpxDays.text = parsingDates(dataItem).toString()
                tvSpxMission.text = dataItem.launchItem.name
                Picasso.Builder(binding.root.context)
                    .build()
                    .load(dataItem.launchItem.links.patch.small.replace("http:", "https:"))
                    .into(binding.ivSpxMissionPatch)
//                if (dataItem.launchItem.success){
//                binding.ivSpxCheckClearIcon =  binding.root.findViewById<ImageView>(R.id.iv_spx_check_clear_icon)
//                    Log.d(TAG, "onBind: true")
//                    Picasso.Builder(binding.root.context)
//                        .build()
//                        .load(R.drawable.ic_check)
//                        .into(binding.ivSpxCheckClearIcon)
//                } else {
//                    Picasso.Builder(binding.root.context)
//                        .build()
//                        .load(R.drawable.ic_clear)
//                        .into(binding.ivSpxCheckClearIcon)
//                }

            }
        }
        private fun launchDate(dataItem: LaunchRocket): String{
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    .parse(dataItem.launchItem.date_utc)
            val newDateFormat = SimpleDateFormat("MM/dd/yyyy 'at' HH:mm")
                .format(simpleDateFormat)
            return newDateFormat
        }

        private fun parsingDates(dataItem: LaunchRocket): Long{
            val outputFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US)
            val inputFormat: DateFormat
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
            else
                inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)

            val UTCLaunchDate = dataItem.launchItem.date_utc
            val date: Date = inputFormat.parse(UTCLaunchDate)
            val launchDate: String = outputFormat.format(date)


            val todayUTC = Date()
            val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm")
            val today = formatter.format(todayUTC)
            val dates = SimpleDateFormat("MM/dd/yyyy HH:mm")
            val now = dates.parse(today)
            val launch = dates.parse(launchDate)
            val difference: Long = now.time - launch.time
            val differenceDates = difference / (24*60*60*1000)
            return differenceDates
        }

            }
    class NoInternetViewHolder(private val view:View):
            RecyclerView.ViewHolder(view){
        fun onBind() {
            throw Exception("")
        }
    }

    /**
     * @return 0 will be for NoInternetViewHolder and 1 for RocketViewHolder
     */
    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (dataSet.isEmpty()) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when(viewType){
            1 -> {RocketViewHolder(SpacexLaunchesItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))}
            0 -> {NoInternetViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    android.R.layout.simple_list_item_1, parent, false)
                )
            }
            else -> throw Exception("Incorrect ViewHolder")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RocketViewHolder -> { dataSet?.let {
                Log.d(TAG, "onBindViewHolder: viewholder")

                holder.onBind(dataSet[position])
            }
            }
            is NoInternetViewHolder -> {
                holder.onBind()
            }
        }
    }

    override fun getItemCount() = dataSet.size

}
