package com.androidpemula.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), View.OnClickListener {

   // Dekalarasi menyimpan nilai ketika perubahan orientasi terjadi
    // SaveInstanceState
    companion object {
        private  const val STATE_RESULT = "state_result"
    }

    // Deklarasi Komponen View yang akan dimanipulasi
    private lateinit var edtWidth: EditText
    private lateinit var edtHeight: EditText
    private lateinit var  edtLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // Casting View

        edtWidth        = findViewById(R.id.edt_width)
        edtHeight       = findViewById(R.id.edt_height)
        edtLength       = findViewById(R.id.edt_length)
        btnCalculate    = findViewById(R.id.btn_calculate)
        tvResult        = findViewById(R.id.tv_result)

        // Fungsi btnCalculate
        btnCalculate.setOnClickListener(this)

        // Menyimpan nilai ketika perubahan orientasi terjadi
        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT) as String
            tvResult.text = result
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_calculate) {
            val inputLength = edtLength.text.toString().trim()
            val inputWidth  = edtWidth.text.toString().trim()
            val inputHeight = edtHeight.text.toString().trim()

            var isEmptyFields   = false
            var isInvalidDouble = false

            // Cek Inpuntan yang Kosong
            if (inputLength.isEmpty()) {
                isEmptyFields   = true
                edtLength.error = "Field ini tidak boleh kosong"
            }

            if (inputWidth.isEmpty()) {
                isEmptyFields   = true
                edtWidth.error  = "Field ini tidak boleh kosong"
            }

            if (inputHeight.isEmpty()) {
                isEmptyFields   = true
                edtHeight.error = "Field ini tidak boleh kosong"
            }

           // Validasi input yang bukan angka (toDouble)

            val length  = toDouble(inputLength)
            val width   = toDouble(inputWidth)
            val height  = toDouble(inputHeight)

            if (length == null) {
                isInvalidDouble = true
                edtLength.error = "Field ini harus berupa nomer yang valid"
            }

            if (width == null) {
                isInvalidDouble = true
                edtWidth.error  = "Field ini harus berupa nomer yang valid"
            }

            /*Sintaks !isEmptyFields memiliki arti "jika semua inputan tidak kosong",
            dan !isInvalidDouble memiliki arti "jika semua inputan berisi angka".
            Jika kedua kondisi tersebut terpenuhi, maka langkah selanjutnya yaitu melakukan proses perhitungan */
            if (!isEmptyFields && !isInvalidDouble) {
                val volume = length as Double * width as Double * height as Double
                tvResult.text = volume.toString()
            }
        }
    }

    // toDouble() adalah sebuah fungsi yang kita buat sendiri di luar onCreate
    // untuk merubah data inputan yang sebelumnya berupa String menjadi Double
    private fun toDouble(str: String): Double? {
        return try {
            str.toDouble()
        }catch (e: NumberFormatException) {
            null
        }

    }

    // SaveInstaceState

    /*Perhatikan metode onSaveInstanceState.
    Di dalam metode tersebut, hasil perhitungan yang ditampilkan pada tvResult dimasukkan pada bundle kemudian disimpan isinya.
    Untuk menyimpan data disini menggunakan konsep Key-Value, dengan STATE_RESULT sebagai key dan isi dari tvResult sebagai value.
    Fungsi onSaveInstanceState akan dipanggil secara otomatis sebelum sebuah Activity hancur. */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }
}
