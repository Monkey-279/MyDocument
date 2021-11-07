package com.example.myapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class Database(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    fun queryData(sql: String) {
        val database = writableDatabase
        database.execSQL(sql)
    }
    fun readData(sql: String): Cursor? {
        val database = readableDatabase
        return database.rawQuery(sql, null)
    }

    fun insertData(Ten: String, MoTa: String, Hinh: ByteArray) {
        val database = writableDatabase
        val sql = "INSERT INTO DoiTuong VALUES(null, ?, ?, ?)"
        val statement = database.compileStatement(sql)
        statement.clearBindings()

        statement.bindString(1, Ten)
        statement.bindString(2, MoTa)
        statement.bindBlob(3, Hinh)
        statement.executeInsert()
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}