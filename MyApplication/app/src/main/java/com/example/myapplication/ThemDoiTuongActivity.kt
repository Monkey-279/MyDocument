package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class ThemDoiTuongActivity : AppCompatActivity() {
    private lateinit var edtTen:EditText
    private lateinit var edtMoTa:EditText
    private lateinit var imghinh:ImageView
    private lateinit var imgCamera:ImageButton
    private lateinit var imgFolder:ImageButton
    private lateinit var btnAdd:Button
    private lateinit var btnHuy:Button
    private val REQUEST_CODE_CAMERA = 123
    private val REQUEST_CODE_FOLDER = 456

    private val imgCameraActivityResultLaunch = registerForActivityResult(StartActivityForResult()) { result ->//: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Handle the Intent
            if(data != null) {

            }
            val data2 = result?.data
            if (data2 != null) {
                val uri = data2.data!!
                val inputStream = contentResolver.openInputStream(uri)
                val bitmapFolder = BitmapFactory.decodeStream(inputStream)
                imghinh.setImageBitmap(bitmapFolder)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_them_doi_tuong)

        anhXa()
        imgCamera.setOnClickListener {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        }
        imgFolder.setOnClickListener {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_FOLDER
            )
        }
        btnAdd.setOnClickListener {
            if(edtTen.text.isNotEmpty() && edtTen.text.isNotEmpty()) {
            //Chuyển kiểu hình về kiểu ByteArray:
                val bitmapDrawable:BitmapDrawable = imghinh.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap //Chuyển từ bitmapDrawable về bitmap
                val bytearray = ByteArrayOutputStream() //Để chuyển về kiểu mảng byte
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytearray)
                /*
                Bitmap.CompressFormat.PNG : định dạng trả về
                100 : chỉ số càng thấp thì chất lượng ảnh trả về càng nét(1-100)
                 */
                val hinh = bytearray.toByteArray() //từ bitmap chuyển về mảng byte
            //-------------------------------------
                MainActivity.database.insertData(
                    edtTen.text.toString().trim(),
                    edtMoTa.text.toString().trim(),
                    hinh
                )
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show()
            }
        }
        btnHuy.setOnClickListener {
            edtTen.setText("")
            edtMoTa.setText("")
            imghinh.setImageDrawable(Drawable.createFromPath("@drawable/software"))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE_CAMERA -> {
                if(grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    imgCameraActivityResultLaunch.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE))
                } else {
                    Toast.makeText(this, "Bạn không cho phép mở camera", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_FOLDER -> {
                if(grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    imgCameraActivityResultLaunch.launch(intent)
                } else {
                    Toast.makeText(this, "Bạn không cho phép mở folder", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun anhXa() {
        edtTen      = findViewById(R.id.edtTen)
        edtMoTa     = findViewById(R.id.edtMoTa)
        imghinh     = findViewById(R.id.imageHinhThem)
        imgCamera   = findViewById(R.id.imageCamera)
        imgFolder   = findViewById(R.id.imageFolder)
        btnAdd      = findViewById(R.id.btnAdd)
        btnHuy      = findViewById(R.id.btnHuy)
    }
}