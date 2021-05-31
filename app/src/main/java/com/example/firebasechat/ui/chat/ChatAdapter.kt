package com.example.firebasechat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechat.BR
import com.example.firebasechat.R
import com.example.firebasechat.model.Message

class ChatAdapter(function: (Int?) -> Unit) :  RecyclerView.Adapter<ChatAdapter.SetviewHolder>() {

    private var list = arrayListOf<Message>()
    private var userName : String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetviewHolder{

        val inflater = LayoutInflater.from(parent.context)
        val binding
                = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return ChatAdapter.SetviewHolder(binding)
    }

    override fun onBindViewHolder(holder: SetviewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (userName != list[position].sender) {
            R.layout.right_message
        } else {
            R.layout.left_message
        }
    }

    override fun getItemCount() = list.size

    class SetviewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Message) {
            binding.setVariable(BR.message, msg)
        }
    }

//    class VH(private  val binding: LeftMessageBinding): RecyclerView.ViewHolder(binding.root) {
//        fun onBind(msg: Message){
//
//        }
//    }

    fun updateData(list: ArrayList<Message>){
        this.list = list
        notifyDataSetChanged()
    }

    fun giveMeUserName(username: String){
        userName = username
    }

}