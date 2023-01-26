package com.aplikasi.apptokosi01.response.cartsupplier

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cartsupplier(
    var id:Int,
    var admin_id:Int,
    var stok:Int
): Parcelable
