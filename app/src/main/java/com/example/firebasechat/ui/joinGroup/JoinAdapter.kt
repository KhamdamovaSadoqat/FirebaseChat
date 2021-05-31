package com.example.firebasechat.ui.joinGroup

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechat.databinding.GroupListBinding
import com.example.firebasechat.databinding.JoinFragmentBinding
import com.example.firebasechat.databinding.JoinGroupListBinding
import com.example.firebasechat.model.Group

class JoinAdapter(private  val itemClickListener: ((Group) -> Unit)):
    RecyclerView.Adapter<JoinAdapter.VH>() {

    private var list = arrayListOf<Group>()

    fun updateData(list: ArrayList<Group>){
        Log.d("list: ", list.toString())
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinAdapter.VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JoinGroupListBinding.inflate(inflater, parent, false)
        return JoinAdapter.VH(binding)
    }

    override fun onBindViewHolder(holder: JoinAdapter.VH, position: Int) {
        holder.itemView.setOnClickListener{
            itemClickListener.invoke(list[position])
        }
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    class VH(private val binding: JoinGroupListBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(group: Group){
            binding.group = group
        }
    }

}