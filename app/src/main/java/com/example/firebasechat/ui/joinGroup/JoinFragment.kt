package com.example.firebasechat.ui.joinGroup

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasechat.R
import com.example.firebasechat.database.RoomDatabase
import com.example.firebasechat.databinding.JoinFragmentBinding
import com.example.firebasechat.model.Group
import com.example.firebasechat.ui.main.MainViewModelFactory
import java.util.ArrayList

class JoinFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: JoinViewModel
    private lateinit var binding: JoinFragmentBinding
    private lateinit var adapter: JoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataSourse = RoomDatabase.getDatabase(requireContext()).groupDao
        val factory = JoinViewModelFactory(dataSourse)
        viewModel = ViewModelProvider(this, factory).get(JoinViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.join_fragment, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        setRv()
        val userName = getDataFromSharedPref()
        if (userName != null) {
            viewModel.getNotJoinedGroupLiveData().observe(this){ groups ->
                adapter.updateData(groups as ArrayList<Group>)
            }
        }
    }

    private fun setRv() {
        adapter = JoinAdapter { group ->
            getDataFromSharedPref()?.let { viewModel.addGroupToJoinedGroup(it, group) }
            findNavController().popBackStack(R.id.mainFragment, true)
        }
        binding.rv.adapter = adapter
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