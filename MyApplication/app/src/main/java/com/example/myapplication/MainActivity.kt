package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var database: Database
    }
    lateinit var lvDoiTuong: ListView
    lateinit var btnHuySQL: Button
    val arrDoiTuong = ArrayList<DoVat>()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnThemDT = findViewById<Button>(R.id.buttonThemDT)
        btnHuySQL = findViewById(R.id.buttonDeleteSQL)
        lvDoiTuong = findViewById(R.id.listViewDoiTuong)
        val apdater = DoVatAdapter(this, R.layout.dong_do_vat, arrDoiTuong)
        lvDoiTuong.adapter = apdater

        database = Database(this, "ThemDoiTuong.sqlite", null, 1)
        database.queryData("CREATE TABLE IF NOT EXISTS DoiTuong(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR[150], MoTa VARCHAR[250], HinhAnh BLOB)")

        val cursor = database.readData("SELECT * FROM DoiTuong")
        while(cursor?.moveToNext() == true) {
            arrDoiTuong.add(DoVat(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getBlob(3)
            ))
        }
        apdater.notifyDataSetChanged()

        btnThemDT.setOnClickListener {
            val intent = Intent(this, ThemDoiTuongActivity::class.java)
            startActivity(intent)
        }
        btnHuySQL.setOnClickListener {
            database.queryData("DELETE FROM DoiTuong")
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}