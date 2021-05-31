package com.example.firebasechat.ui.registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebasechat.R
import com.example.firebasechat.database.RoomDatabase
import com.example.firebasechat.databinding.RegistrationFragmentBinding
import com.example.firebasechat.network.realtimeDatabase.RegistrationNetwork
import com.example.firebasechat.repository.RegistrartionRepository
import com.example.firebasechat.ui.main.MainViewModel
import com.example.firebasechat.ui.main.MainViewModelFactory
import java.util.*

class RegistrationFragment : Fragment() {

    private lateinit var binding: RegistrationFragmentBinding
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.registration_fragment,
            container,
            false
        )
        val dataSourse = RoomDatabase.getDatabase(requireContext()).registrationDao
        val registrationNetwork = RegistrationNetwork()
        val factory = RegistrationViewModelFactory(registrationNetwork, dataSourse)
        viewModel = ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
//

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnSubmit.setOnClickListener {
            val userName = binding.etName.text.toString()
            val userId = UUID.randomUUID().toString()
            Log.d("RegistrationFragment", "username = $userName")
            viewModel.setDataToFirebase(userName, userId)
            viewModel.setDatatoRoom(userName, userId)
            setDataToSharedPref(userId, userName)
            findNavController().navigate(R.id.mainFragment)
        }
    }

    fun setDataToSharedPref(userId: String, userName: String){
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.putString("userName", userName)
        Log.d("RegistrationFragment", "setting data to shared preference")
        editor.apply()
    }


}