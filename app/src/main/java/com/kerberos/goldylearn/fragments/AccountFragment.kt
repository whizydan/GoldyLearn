package com.kerberos.goldylearn.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.activities.BackupActivity
import com.kerberos.goldylearn.activities.EditAccountActivity
import com.kerberos.goldylearn.activities.HelpActivity
import com.kerberos.goldylearn.activities.LoginActivity
import com.kerberos.goldylearn.activities.PrivacyPolicyActivity
import com.kerberos.goldylearn.utils.TinyDB

class AccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editAccount = view.findViewById<TextView>(R.id.textView16)
        val privacyPolicy = view.findViewById<TextView>(R.id.textView17)
        val help = view.findViewById<TextView>(R.id.textView22)
        val backup = view.findViewById<TextView>(R.id.textView15)

        help.setOnClickListener {
            //sendEmail("support@elder.com", "Help", "Write your message here...")
            //log out instead
            FirebaseAuth.getInstance().signOut()
            val db = TinyDB(requireContext())
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        privacyPolicy.setOnClickListener {
            //start activity with privacy policy
            startActivity(Intent(requireContext(), PrivacyPolicyActivity::class.java))
        }
        editAccount.setOnClickListener {
            //start activity to update the password
            startActivity(Intent(requireContext(), EditAccountActivity::class.java))
        }
        backup.setOnClickListener {
            startActivity(Intent(requireContext(),BackupActivity::class.java))
        }
    }
    private fun sendEmail(recipient: String, subject: String, body: String) {
        // Create an intent with the ACTION_SENDTO action
        val intent = Intent(Intent.ACTION_SENDTO)

        // Set the email address using the "mailto:" scheme
        intent.data = Uri.parse("mailto:$recipient")

        // Set the subject and body of the email
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)

        startActivity(intent)
    }
}