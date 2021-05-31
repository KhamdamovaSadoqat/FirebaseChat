package com.example.firebasechat.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.firebasechat.R
import com.example.firebasechat.database.RoomDatabase
import com.example.firebasechat.databinding.CreateGroupPopupViewBinding
import com.example.firebasechat.databinding.FragmentMainBinding
import com.example.firebasechat.helper.InternetHelper
import com.example.firebasechat.model.Group
import com.example.firebasechat.model.Message
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentMainBinding
    private lateinit var bindingPopupWindow: CreateGroupPopupViewBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var internetHelper: InternetHelper
//    private var creatGroupNetwork = CreatGroupNetwork()
//    private var groupListNetwork = GroupListNetwork()
//    private val notJoinedGroupsNetwork = NotJoinedGroupsNetwork()
//    private  var repository =  GroupRepository(creatGroupNetwork, groupListNetwork, notJoinedGroupsNetwork, dataSource)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (getDataFromSharedPref().isNullOrBlank()) {
            val starDestination = findNavController().graph.startDestination
            val navOption = NavOptions.Builder()
                    .setPopUpTo(R.id.registrationFragment, true)
                    .build()
            findNavController().navigate(R.id.registrationFragment, null, navOption)
        }
        val dataSourse = RoomDatabase.getDatabase(requireContext()).groupDao
        val factory = MainViewModelFactory(dataSourse)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
//        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        bindingPopupWindow = DataBindingUtil.inflate(
                inflater,
                R.layout.create_group_popup_view,
                container,
                false
        )
        internetHelper = InternetHelper(requireContext())
        activity?.let {
            internetHelper.makeSnackbar(binding.parent, requireActivity())
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRv()
        val userName = getDataFromSharedPref()
        var flag = true
        internetHelper.checkInternetConnection()
        if (userName != null) {
            viewModel.getGroupList(userName).observe(this) { groups ->
                var listMsg = arrayListOf<com.example.firebasechat.model.Message>()
                Log.d("Group", "onResume: groupSize: ${groups.size}")
                listMsg.clear()
//                if (flag){
//                    for(index in 0 until groups.size){
//                        Log.d("Group", "onResume: index: $index")
//                        Log.d("Group", "onResume: groupName: ${groups[index].groupName}")
//                        viewModel.getLastMessage(groups[index].groupName, index)
//                    }
//                    flag = false
//                }
                internetHelper.dissmissSnackbar()
                for (index in 0 until groups.size) {
                    Log.d("Group", "111onResume: index: $index")
                    Log.d("Group", "111onResume: groupName: ${groups[index].groupName}")
                    viewModel.getLastMessage(groups[index].groupName, index, groups.size).observe(
                            this
                    ) { msg ->
                        adapter.updateData(groups as ArrayList<Group>, msg as ArrayList<Message>)

                    }
                }
            }


//            Singleton.getJoinedList(userName)
//            Singleton.getNotJoinedList()
        }

        binding.btnCreate.setOnClickListener {
            onButtonCreatePopupWindowClick(view)
        }
        binding.btnJoin.setOnClickListener {
            findNavController().navigate(R.id.joinFragment)
            Toast.makeText(context, "Join pressed", Toast.LENGTH_SHORT).show()

        }


    }


    private fun onButtonCreatePopupWindowClick(view: View?) {

        val inflater = LayoutInflater.from(context)
        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow: PopupWindow = PopupWindow(
                bindingPopupWindow.root,
                width,
                height,
                focusable
        )

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        Log.d("MainFragment", "popupview is working")

        bindingPopupWindow.btnSubmit.setOnClickListener {
            val groupName = bindingPopupWindow.etName.text.toString()
            Log.d("MainFragment", "groupName: $groupName")
            val groupId = UUID.randomUUID().toString()
            // adding group name to list
            viewModel.setDataToGroupList(groupName, groupId)
            // creating group root
            viewModel.createNewGroup(getDataFromSharedPref().toString(), groupName, groupId)
            viewModel.insertDataToRoom(groupName, groupId)
            popupWindow.dismiss()
        }
    }

    private fun setRv() {
        adapter = MainAdapter { group ->
            Log.d("Chat", "group: $group")
            findNavController().navigate(R.id.chatFragment, bundleOf("group" to group.groupName))
        }
//        adapter.updateData(repository.getGroupList())
        binding.rvGroupList.adapter = adapter
    }

    private fun getDataFromSharedPref(): String? {
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName: String? = sharedPreferences.getString("userName", "")
        val userId: String? = sharedPreferences.getString("userId", "")

        Log.d("MainFragment", "userName: $userName")
        Log.d("MainFragment", "userId: $userId")
        return userName
    }


}