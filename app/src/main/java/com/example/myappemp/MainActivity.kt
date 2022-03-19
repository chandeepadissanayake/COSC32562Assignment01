package com.example.myappemp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myappemp.config.URL_API
import com.example.myappemp.models.User
import com.example.myappemp.services.UsersService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private fun initComponents() {
        val btnGetData = findViewById<Button>(R.id.btnGetData)
        btnGetData.setOnClickListener {
            this.btnGetDataOnClick()
        }
    }

    private fun btnGetDataOnClick() {
        val txtUserId = findViewById<EditText>(R.id.txtUserId)
        val txtName = findViewById<EditText>(R.id.txtName)
        val txtUsername = findViewById<EditText>(R.id.txtUsername)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPhone = findViewById<EditText>(R.id.txtPhone)
        val txtAdStreet = findViewById<EditText>(R.id.txtAdStreet)
        val txtAdSuite = findViewById<EditText>(R.id.txtAdSuite)
        val txtAdCity = findViewById<EditText>(R.id.txtAdCity)
        val txtAdZip = findViewById<EditText>(R.id.txtAdZip)

        val retro = Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retro.create(UsersService::class.java)
        val countryRequest = service.getUser(txtUserId.text.toString())

        countryRequest.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()

                if (user == null) {
                    Toast.makeText(this@MainActivity, "No Such User Found!", Toast.LENGTH_LONG).show()
                }
                else {
                    txtName.setText(user.name)
                    txtUsername.setText(user.username)
                    txtEmail.setText(user.email)
                    txtPhone.setText(user.phone)
                    txtAdStreet.setText(user.address.street)
                    txtAdSuite.setText(user.address.suite)
                    txtAdCity.setText(user.address.city)
                    txtAdZip.setText(user.address.zipcode)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to Fetch.", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initComponents()
    }
}