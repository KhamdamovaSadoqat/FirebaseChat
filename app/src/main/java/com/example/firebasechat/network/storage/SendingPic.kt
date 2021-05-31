package com.example.firebasechat.network.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SendingPic {

    private var mStorageRef: StorageReference? = null

    fun uploadData(uri: Uri){
        mStorageRef = FirebaseStorage.getInstance().reference;

    }
}