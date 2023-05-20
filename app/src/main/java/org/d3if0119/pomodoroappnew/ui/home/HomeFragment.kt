package org.d3if0119.pomodoroappnew.ui.home


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.d3if0119.pomodoroappnew.R
import org.d3if0119.pomodoroappnew.ui.settings.ViewModelUI
import org.d3if0119.pomodoroappnew.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private var timer: CountDownTimer? = null
    private var timerRunning = false
    private var remainingTime: Long = 0
    private val CHANNEL_ID = "my_channel"
    private val viewModelActivity: ViewModelUI by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.button?.setOnClickListener {
            if (!timerRunning) {
                if (remainingTime > 0) {
                    resumeTimer() // Resume the timer from the remaining time
                } else {
                    startTimer() // Start a new timer
                }
            } else {
                pauseTimer()
            }
        }
    }

    private fun startTimer() {
        val lengthTimer: Long = if (remainingTime > 0) remainingTime else 25 * 60 * 1000
        timer = object : CountDownTimer(lengthTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished // Update the remaining time
                val minute = millisUntilFinished / 1000 / 60
                val second = millisUntilFinished / 1000 % 60
                binding?.textTimer?.text = "$minute:$second"
            }

            override fun onFinish() {
                timer?.cancel()
                timerRunning = false

                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Mulai waktu istirahat?")
                    .setPositiveButton("Iya") { _, _ -> breakTimer() }
                    .setNegativeButton("Tidak") { _, _ ->
                        startTimer()
                        timerRunning = true
                        binding?.button?.text = "Pause"
                    }
                    .show()
                notification()
            }
        }
        timer?.start()
        timerRunning = true
        binding?.button?.text = "Pause"
    }

    private fun pauseTimer() {
        timer?.cancel()
        timerRunning = false
        binding?.button?.text = "Start"
    }

    private fun resumeTimer() {
        startTimer() // Start a new timer with the remaining time
    }

    private fun breakTimer() {
        val timerLength: Long = 5 * 60 * 1000
        timer = object : CountDownTimer(timerLength, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished // Update the remaining time
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding?.textTimer?.text = "$minutes:$seconds"

            }

            override fun onFinish() {
                timer?.cancel()
                timerRunning = false
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Mulai Belajar Lagi?")
                    .setPositiveButton("Yes") { _, _ -> startTimer() }
                    .setNegativeButton("No") { _, _ ->
                        Toast.makeText(
                            requireContext(),
                            "Yeyy, proses belajarmu telah selesai",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }
        }
        timer?.start()
        timerRunning = true
        binding?.button?.text = "Pause"
    }


    private fun notification() {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if Android version is greater than or equal to Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Build notification
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Waktu telah selesai!")
            .setContentText("Waktu timer telah habis berjalan.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show notification
        notificationManager.notify(0, builder.build())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navigation_menu, menu)
        lifecycleScope.launch {
            val isChecked = viewModelActivity.getUIMode.first()
            val item = menu.findItem(R.id.night_mode)
            item.isChecked = isChecked
            setUIMode(item, isChecked)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tasks ->{
                findNavController().navigate(
                    R.id.action_homeFragment_to_tasksFragment)
                return true
            }
            R.id.about ->{
                findNavController().navigate(
                    R.id.action_homeFragment_to_aboutFragment)
                return true
            }
            R.id.night_mode -> {
                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModelActivity.saveToDataStore(true)
            item.setIcon(R.drawable.baseline_light_mode_24)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModelActivity.saveToDataStore(false)
            item.setIcon(R.drawable.baseline_dark_mode_24)

        }
    }
}


