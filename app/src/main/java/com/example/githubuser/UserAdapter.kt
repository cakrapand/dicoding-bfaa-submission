package com.example.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding

class UserAdapter(private val listUser: List<User>, private val onClick: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserAdapter.ViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsername.text = listUser[holder.adapterPosition].login
        Glide.with(holder.itemView)
            .load(listUser[holder.adapterPosition].avatar_url)
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener{
            onClick(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size



}