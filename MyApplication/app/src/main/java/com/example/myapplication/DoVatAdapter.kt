package com.example.myapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class DoVatAdapter constructor(var context: Context, var layout:Int, var mangDoVat: ArrayList<DoVat>): BaseAdapter() {
    override fun getCount(): Int {
        return mangDoVat.size
    }

    override fun getItem(position: Int): Any {
        return mangDoVat.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder {
        lateinit var textTen: TextView
        lateinit var textMoTa: TextView
        lateinit var imgHinh: ImageView
    }
    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View? {
        var view = converView
        val holder: ViewHolder

        if(view == null) {
            holder = ViewHolder()
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder.textTen = view.findViewById(R.id.textTen)
            holder.textMoTa = view.findViewById(R.id.textMoTa)
            holder.imgHinh = view.findViewById(R.id.imageViewHinhThem)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val dovat = mangDoVat[position]
        holder.textTen.text = dovat.ten
        holder.textMoTa.text = dovat.mota
        //Chuyển hình từ ByteArray về Bitmap:
        val hinhAnh:ByteArray = dovat.hinh
        val bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.size)
        holder.imgHinh.setImageBitmap(bitmap)

        return view
    }
}