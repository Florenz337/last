package com.example.last

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.Socket
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Registration.newInstance] factory method to
 * create an instance of this fragment.
 */

private var data: String = ""
data class reg_data(
    val command: Int = 1,
    val login: String,
    val password: String
)

class Registration : Fragment() {
    /*--------------------------------------------------------------*/
    private suspend fun client(address: String, port: Int) {
        val gson = Gson()
        val connection = Socket(address, port)
        val writer: OutputStream = connection.getOutputStream()
        val password = editTextTextPassword.text.toString()
        val password2 = editTextTextPassword2.text.toString()
        val email = editTextTextEmailAddress.text.toString()
        val charset = Charsets.UTF_8
        val registrationString = gson.toJson(reg_data(1, email, password))
        val regArr = registrationString.toByteArray(charset)
        writer.write(regArr)
        val reader = Scanner(connection.getInputStream())
        data = reader.nextLine()
        //val message = gson.fromJson(data, MyObject::class.java)
        textView.text = data
        reader.close()
        writer.close()
        connection.close()
    }
    /*--------------------------------------------------------------*/
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*--------------------------------------------------------------*/
        val address = "134.249.171.112"
        val port = 8888
        ЗАРЕГИСТРИРОВАТЬСЯ.setOnClickListener {
            if (ЗАРЕГИСТРИРОВАТЬСЯ.text == "ЗАРЕГИСТРИРОВАТЬСЯ") {
                CoroutineScope(Dispatchers.IO).launch {
                    client(address, port)
                }
            }
        }
        /*--------------------------------------------------------------*/
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Registration.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Registration().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}