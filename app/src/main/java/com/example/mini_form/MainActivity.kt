package com.example.mini_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var editTextMSSV: EditText
    private lateinit var editTextHoTen: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var textViewDateOfBirth: TextView
    private lateinit var spinnerPhuongXa: Spinner
    private lateinit var spinnerQuanHuyen: Spinner
    private lateinit var spinnerTinhThanh: Spinner
    private lateinit var checkBoxTerms: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMSSV = findViewById(R.id.editTextMSSV)
        editTextHoTen = findViewById(R.id.editTextHoTen)
        radioGroup = findViewById(R.id.radioGroup)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        textViewDateOfBirth = findViewById(R.id.textViewDateOfBirth)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)

        spinnerPhuongXa = findViewById(R.id.spinnerPhuongXa)
        spinnerQuanHuyen = findViewById(R.id.spinnerQuanHuyen)
        spinnerTinhThanh = findViewById(R.id.spinnerTinhThanh)

        // Khởi tạo spinner cho Phường/Xã
        val phuongXaAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("Chọn phường/xã", "Phường 1", "Phường 2", "Phường 3")
        )
        phuongXaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPhuongXa.adapter = phuongXaAdapter

        // Khởi tạo spinner cho Quận/Huyện
        val quanHuyenAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("Chọn quận/huyện", "Quận 1", "Quận 2", "Quận 3")
        )
        quanHuyenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerQuanHuyen.adapter = quanHuyenAdapter

        // Khởi tạo spinner cho Tỉnh/Thành
        val tinhThanhAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("Chọn tỉnh/thành", "Hà Nội", "Hồ Chí Minh", "Đà Nẵng")
        )
        tinhThanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTinhThanh.adapter = tinhThanhAdapter

        findViewById<Button>(R.id.buttonDatePicker).setOnClickListener {
            showDatePickerDialog()
        }

        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            validateForm()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            textViewDateOfBirth.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day).show()
    }

    private fun validateForm() {
        val mssv = editTextMSSV.text.toString()
        val hoTen = editTextHoTen.text.toString()
        val email = editTextEmail.text.toString()
        val phone = editTextPhone.text.toString()
        val dateOfBirth = textViewDateOfBirth.text.toString()
        val gender = if (radioGroup.checkedRadioButtonId != -1) findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text else null
        val termsAccepted = checkBoxTerms.isChecked

        // Lấy giá trị từ các Spinner
        val phuongXa = spinnerPhuongXa.selectedItem.toString()
        val quanHuyen = spinnerQuanHuyen.selectedItem.toString()
        val tinhThanh = spinnerTinhThanh.selectedItem.toString()

        val missingFields = mutableListOf<String>()
        if (mssv.isEmpty()) missingFields.add("MSSV")
        if (hoTen.isEmpty()) missingFields.add("Họ tên")
        if (email.isEmpty()) missingFields.add("Email")
        if (phone.isEmpty()) missingFields.add("Số điện thoại")
        if (dateOfBirth.isEmpty()) missingFields.add("Ngày sinh")
        if (gender == null) missingFields.add("Giới tính")
        if (phuongXa == "Chọn phường/xã") missingFields.add("Phường/Xã")
        if (quanHuyen == "Chọn quận/huyện") missingFields.add("Quận/Huyện")
        if (tinhThanh == "Chọn tỉnh/thành") missingFields.add("Tỉnh/Thành")
        if (!termsAccepted) missingFields.add("Đồng ý với các điều khoản")

        if (missingFields.isNotEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ các thông tin: ${missingFields.joinToString(", ")}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
        }
    }
}

