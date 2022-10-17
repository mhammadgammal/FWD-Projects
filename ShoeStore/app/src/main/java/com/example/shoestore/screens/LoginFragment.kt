package com.example.shoestore.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.shoestore.R
import com.example.shoestore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.loginBTN.setOnClickListener { view ->
            if (binding.nameTextField.editText?.text?.toString()
                    ?.isEmpty()!! || binding.passwordTextField.editText?.text?.toString()
                    ?.isEmpty()!!
            )
                Toast.makeText(context, "please fill all your data", Toast.LENGTH_SHORT).show()
            else {
                val navToWelcome: NavDirections =
                    LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
                view.findNavController().navigate(navToWelcome)
            }
        }
        binding.signupBTN.setOnClickListener { view ->
            val navToWelcome: NavDirections =
                LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
            view.findNavController().navigate(navToWelcome)
        }
        return binding.root
    }
}