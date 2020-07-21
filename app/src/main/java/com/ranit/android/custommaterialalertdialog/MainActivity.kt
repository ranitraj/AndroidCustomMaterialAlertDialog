package com.ranit.android.custommaterialalertdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var floatingActionButton : FloatingActionButton
    private lateinit var lottieView : LottieAnimationView
    private lateinit var cardView : CardView
    private lateinit var nameTextView : TextView
    private lateinit var phoneNumberTextView : TextView
    private lateinit var addressTextView : TextView
    private lateinit var customAlertDialogView : View
    private lateinit var nameTextField : TextInputLayout
    private lateinit var phoneNumberTextField : TextInputLayout
    private lateinit var addressTextField : TextInputLayout
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

        floatingActionButton.setOnClickListener(View.OnClickListener {
            // Inflate Custom alert dialog view
            customAlertDialogView = LayoutInflater.from(this)
                .inflate(R.layout.order_details_custom_dialog, null, false)

            launchCustomAlertDialog()
        })
    }

    /**
     * Method to initialize all views
     */
    private fun initializeViews() {
        // Create a MaterialAlertDialogBuilder object instance
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        floatingActionButton = findViewById(R.id.floating_action_button)
        lottieView = findViewById(R.id.no_data_lottie_animation)
        cardView = findViewById(R.id.address_details_card)
        nameTextView = findViewById(R.id.name_text_view)
        phoneNumberTextView = findViewById(R.id.phone_number_text_view)
        addressTextView = findViewById(R.id.address_text_view)
    }

    /**
     * Method to launch custom alert dialog
     * Steps:
     * 1. Map views with the layout
     * 2. Set the custom view to the materialAlertDialogBuilder instance
     * 3. Set the additional parameters to be displayed such as 'setTitle', 'setMessage'
     * 4. Extract the text from the editText fields and performing basic validation
     * 5. Display data as required
     * 6. Call 'show()' on the materialAlertDialogBuilder instance
     *
     * NOTE: It is necessary to create a new instance of the Dialog every single time.
     *       This is the reason why the Layout is being inflated inside click Listener of the
     *       button and findViewById is performed before setting the view to the dialog.
     *       Else, the value in the editText fields will not be overwritten with the
     *       updated values.
     */
    private fun launchCustomAlertDialog() {
        nameTextField = customAlertDialogView.findViewById(R.id.name_text_field)
        phoneNumberTextField = customAlertDialogView.findViewById(R.id.phone_number_text_field)
        addressTextField = customAlertDialogView.findViewById(R.id.address_text_field)

        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle("Details")
            .setMessage("Enter your basic details")
            .setPositiveButton("Add") { dialog, _ ->
                val name = nameTextField.editText?.text.toString()
                val phoneNumber = phoneNumberTextField.editText?.text.toString()
                val address = addressTextField.editText?.text.toString()

                val isInputValid : Boolean = validateInput(name, phoneNumber, address)

                if (isInputValid) {
                    lottieView.visibility = View.GONE
                    cardView.visibility = View.VISIBLE

                    nameTextView.text = name
                    phoneNumberTextView.text = phoneNumber
                    addressTextView.text = address
                    displayMessage("Address added successfully!")
                } else {
                    displayMessage("Fields must not be left blank!")
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                displayMessage("Operation cancelled!")
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Method to validate user input (Basic validation)
     */
    private fun validateInput(name : String, phoneNumber : String, address : String) : Boolean {
        if (name.trim().isNotEmpty() && phoneNumber.trim().isNotEmpty()
            && address.trim().isNotEmpty()) {
            return true
        }
        return false
    }

    /**
     * Method to display any message in the form of Toast (can be replaced with Snack bar)
     */
    private fun displayMessage(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}