package com.example.firebasechat.ui.chat

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.firebasechat.R
import com.example.firebasechat.databinding.ChatFragmentBinding
import com.example.firebasechat.model.Message
import io.reactivex.Notification
import java.util.ArrayList

class ChatFragment: Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ChatFragmentBinding
    private lateinit var adapter: ChatAdapter
    private lateinit var sharedPreferences: SharedPreferences
    var msgText = MutableLiveData<String?>()
    var message: String? = null
    var date: String? = null
    private var sender: String? = null
    var groupName: String? = null
    lateinit var builder: NotificationCompat

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment, container, false)
        getGroupName()
        createNotificationChannel()

        binding.imgSkrepka.setOnClickListener{
            val resultLauncher
                    = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: String? = result.data?.data?.path  // only path
                    val name = data.toString()
                    binding.pictureLayout.visibility = View.VISIBLE
                    binding.tvPicInfo.text = name

                    binding.tvClose.setOnClickListener {
                        Toast.makeText(context, "closed", Toast.LENGTH_SHORT).show()
                       binding.pictureLayout.visibility = View.GONE
                    }
                }
            }

            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent = Intent.createChooser(intent, "Choose content")
            resultLauncher.launch(intent)

        }

        var builder = NotificationCompat.Builder(requireContext(), "1")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Notification")
            .setContentText("this is new notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val factory =  ChatViewModelFactory(requireContext())
          viewModel =  ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    private fun getGroupName(){
        val group = arguments?.getString("group")
        viewModel.groupNameis(group?:"")
        Log.d("Chat", "argument: $group")
        setRv()
        viewModel.getMessage(group?:"").observe(viewLifecycleOwner){message ->
            adapter.updateData(message as ArrayList<Message>)
        }
        binding.message = viewModel
    }

    override fun onResume() {
        super.onResume()

        binding.btnSend.setOnClickListener{
            message = binding.etView.text.toString()
            sender = getDataFromSharedPref()
            groupName = binding.groupName.text.toString()
            Log.d("Chat", "sender: $sender")
            date = GetDate.date()
            Log.d("Chat", "date: $date")
            Log.d("Chat", "msgText: ${message.toString()}")
            if(!message.isNullOrEmpty()) {

                val messageM = com.example.firebasechat.model.Message(
                        sender.toString(),
                        date.toString(),
                        message!!
                )
                Log.d("Chat", "group: $groupName")
                Log.d("Chat", "msgText: $messageM")
                groupName?.let { viewModel.sendMessage(messageM, it) }
                binding.etView.text = null
            }
        }

    }

    private fun setRv() {
        adapter = ChatAdapter{ position ->
            Log.d("MainFragment", "position: $position")
            if (position != null) {
                binding.rcView.smoothScrollToPosition(position)
            }
        }
        getDataFromSharedPref()?.let { adapter.giveMeUserName(it) }
        binding.rcView.adapter = adapter

    }

    private fun getDataFromSharedPref(): String? {
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName: String? = sharedPreferences.getString("userName", "")
        val userId: String? = sharedPreferences.getString("userId", "")

//        Log.d("MainFragment", "userName: $userName")
//        Log.d("MainFragment", "userId: $userId")
        return userName
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "1"
            val descriptionText = "hello"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}