/*
package com.pos.empressa.empressa_pos.TopWise

import io.flutter.app.FlutterApplication
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import com.topwise.cloudpos.aidl.printer.Align
import com.topwise.cloudpos.aidl.printer.ImageUnit
import com.topwise.cloudpos.aidl.printer.PrintTemplate
import com.topwise.cloudpos.aidl.printer.TextUnit
import com.topwise.library.activity.TopWiseDevice
import com.topwise.library.util.emv.DeviceState
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class TopWiseApplication : FlutterApplication{


    //For printer, set isPrinter = true
    private val topWiseDevice by lazy {
        TopWiseDevice(this, isPrinter = true) {
            when (it.state) {
                DeviceState.PRINTER_SERVICE_CONNECTION -> {
                    onPrintReceipt()
                }
                DeviceState.PRINTER_SUCCESS -> {
                    runOnUiThread {
                        //To print MERCHANT's COPY
                        //You can call the print option again while you change the holder copy
                    }
                }
                DeviceState.PRINTER_ERROR -> {
                    Log.e("ERROR", "Printing Failed with error ${it.message}")

                }
                else -> {
                }
            }
        }
    }
    //Start EMV (Card Transaction)
    private val topWiseDevice by lazy {
        TopWiseDevice(this) {
            when (it.state) {
                DeviceState.INSERT_CARD -> {

                }
                DeviceState.PROCESSING -> {

                }
                DeviceState.INPUT_PIN -> {

                }
                DeviceState.PIN_DATA -> {

                }
                DeviceState.INFO -> {
                    //Get Transaction INFO
                }
                else -> {
                    Log.e("ERROR", it.message)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler()
            .postDelayed({ initData() }, 3000L)
    }

    fun initData() {
        //The configure terminal is expected to be a once in a lifetime event.
        // This only happens when trying to setup the terminal for usage for the first time.
        configureTerminalButton.setOnClickListener {
            topWiseDevice.configureTerminal()
        }

        //This reads the Data from the card inserted into the terminal and sends back all the
        // expected transaction data.
        startEmvButton.setOnClickListener {
            topWiseDevice.startEmv("Amount")
        }

    }

    private fun onPrintReceipt() {
        try {
//            val encodedString = "${viewModel.profileImage}"
            val encodedString = "base64"
            val pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

            val decodedString: ByteArray = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            //For image file
//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            requireActivity().resources,
//            R.drawable.card
//        )
            val textSize = TextUnit.TextSize.SMALL + 4
            val template: PrintTemplate = PrintTemplate.getInstance()
            template.init(this, null)
            template.clear()
            template.add(ImageUnit(bitmap, bitmap.width, bitmap.height))
            template.add(
                TextUnit(
                    "NAME",
                    textSize + 4, Align.CENTER
                )
            )

            template.add(
                1, TextUnit("TID", TextUnit.TextSize.NORMAL, Align.LEFT),
                1, TextUnit("TID INFO", TextUnit.TextSize.NORMAL, Align.RIGHT)
            )
            template.add(
                1, TextUnit("MID", TextUnit.TextSize.NORMAL, Align.LEFT),
                1, TextUnit("MID INFO", TextUnit.TextSize.NORMAL, Align.RIGHT)
            )
            template.add(
                1, TextUnit("DATE/TIME", TextUnit.TextSize.NORMAL, Align.LEFT),
                1, TextUnit("DATE FORMAT", TextUnit.TextSize.NORMAL, Align.RIGHT)
            )

            template.add(
                TextUnit(
                    "BOLD TEXT",
                    TextUnit.TextSize.NORMAL,
                    Align.CENTER
                ).setBold(true)
            )


            template.add(
                TextUnit("--------------------------------------------------------------------").setWordWrap(
                    false
                )
            )

            topWiseDevice.printDoc(template = template)
        } catch (e: Exception) {

        }

    }
}*/
