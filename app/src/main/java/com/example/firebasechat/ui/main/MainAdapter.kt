package com.example.firebasechat.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechat.databinding.GroupListBinding
import com.example.firebasechat.model.Group
import com.example.firebasechat.model.Message
import java.util.ArrayList

class MainAdapter(private  val itemClickListener: ((Group) -> Unit)):RecyclerView.Adapter<MainAdapter.VH>() {

    private var list = arrayListOf<Group>()
    private var message = arrayListOf<Message>()

    fun updateData(list: ArrayList<Group>, msg: ArrayList<Message>){
        this.list = list
        this.message = msg
//        Log.d("Group", "updateData: message: $message")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GroupListBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener{
            itemClickListener.invoke(list[position])
        }
        holder.onBind(list[position])
        holder.onMessageBind(message[position])
    }

    override fun getItemCount() = list.size

    class VH(private val binding: GroupListBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(group: Group){
            binding.group = group
        }
        fun onMessageBind(msg: Message){
            binding.lastMassage = msg
        }
    }
}