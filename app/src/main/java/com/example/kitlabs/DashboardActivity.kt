package com.example.kitlabs

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitlabs.databinding.ActivityDashboardBinding
import com.example.kitlabs.roomdatabase.Response
import com.example.kitlabs.roomdatabase.Response.Data.AllHarvestData
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dashboardAdapter: DashboardAdapter
    private var list : ArrayList<AllHarvestData> = arrayListOf()
    private lateinit var data: Response
    private lateinit var listData: List<AllHarvestData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        data = CompJson.myGsonData

        listData = data.data?.all_harvest_data!!

        Log.d("TAG", "onCreate: ${data.data?.all_harvest_data}")

        setLayout()

        clickEvent()

    }

    private fun clickEvent() {
        binding.apply {
            btnExport.setOnClickListener {
                val random = Random(System.currentTimeMillis())
                val fiveDigitNumber = random.nextInt(10000, 99999)
                Log.e("TAG", "onCreateView: $fiveDigitNumber",)
                data.data?.all_harvest_data?.let { it1 -> createExcelSheet(it1,fiveDigitNumber) }
            }
        }
    }

    private fun setLayout() {
        val linearLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayout
        dashboardAdapter =
            DashboardAdapter(this)
        binding.recyclerView.adapter = dashboardAdapter

        dashboardAdapter.setList(data.data?.all_harvest_data)
    }

    fun createExcelSheet(data: List<AllHarvestData>, fiveDigitNumber: Int) {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("HarvestSheet")

            // Create a header cell style with background color, font style, and alignment
            val headerStyle = workbook.createCellStyle().apply {
                fillForegroundColor = IndexedColors.LIGHT_BLUE.index // Background color
                fillPattern = FillPatternType.SOLID_FOREGROUND

                // Font settings
                val font = workbook.createFont().apply {
                    bold = true
                    fontHeightInPoints = 12
                    color = IndexedColors.WHITE.index
                }
                setFont(font)

                // Alignment settings
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
            }

            // Create the header row and apply the style
            val headerRow = sheet.createRow(0)
            val headers = listOf(
                "S.No", "FieldId", "Acres", "Pattern", "Est.Loads",
                "Act.Loads", "Location", "Stewarded", "Split Field",
                "Harv.Used", "Harv. Started", "Harv. Completed", "Note"
            )

            for ((index, header) in headers.withIndex()) {
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.cellStyle = headerStyle
            }

            // Populate the data rows
            for ((index, item) in data.withIndex()) {
                val dataRow = sheet.createRow(index + 1)
                dataRow.createCell(0).setCellValue((index + 1).toString())
                dataRow.createCell(1).setCellValue(item.field_number.toString())
                dataRow.createCell(2).setCellValue(item.Acres.toString())
                dataRow.createCell(3).setCellValue(item.pattern.toString())
                dataRow.createCell(4).setCellValue(item.estimated_loads.toString())
                dataRow.createCell(5).setCellValue(item.harvested_loads)
                dataRow.createCell(6).setCellValue(item.location)
                dataRow.createCell(7).setCellValue(item.stewarded)
                dataRow.createCell(8).setCellValue(item.split_field)
                dataRow.createCell(9).setCellValue(item.harvesters_used)
                dataRow.createCell(10).setCellValue(item.harvest_started)
                dataRow.createCell(11).setCellValue(item.harvest_complete)
                dataRow.createCell(12).setCellValue(item.harvest_notes)
            }

            // Save the workbook to a file
            val fileName = "HarvestSheet$fiveDigitNumber.xlsx"
            val myDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            myDir.createNewFile()
            val fileOut = FileOutputStream(myDir)
            workbook.write(fileOut)
            fileOut.close()
            workbook.close()

            Toast.makeText(this, "Exporting.....", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception (e.g., showExportErrorNotification())
        }
    }
}