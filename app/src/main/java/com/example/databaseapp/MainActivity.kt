package com.example.databaseapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.databaseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

     val  database=this.openOrCreateDatabase("People", Context.MODE_PRIVATE,null)
        binding.savebtn.setOnClickListener {
            try {
database.execSQL("CREATE TABLE IF NOT EXISTS insanlar(name varchar,surname varchar)")
val  sorgu ="INSERT INTO insanlar(name,surname)VALUES(?,?)"
                val statment= database.compileStatement(sorgu)
                statment.bindString(1,binding.name.text.toString())
                statment.bindString(2,binding.surname.text.toString())
                statment.execute()
                Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()

            }catch (ex:Exception) {
                ex.printStackTrace()
            }
        }
        binding.showbtn.setOnClickListener {
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS insanlar(name varchar,surname varchar)")
           val cursor=database.rawQuery("SELECT * FROM insanlar WHERE name =?",
               arrayOf(binding.alertTitle.text.toString()))
                val nameIndex=cursor.getColumnIndex("name")
                val surnameIndex=cursor.getColumnIndex("surname")
                while (cursor.moveToNext()){
                    val name=cursor.getString(nameIndex).toString()
                    val surname=cursor.getString(surnameIndex).toString()
                    Toast.makeText(this,"$name $surname",Toast.LENGTH_LONG).show()
                }
                cursor.close()





            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}