package com.pos.empressa.empressa_pos.Sunyard;

import android.content.Context;

import androidx.annotation.NonNull;

import com.socsi.exception.SDKException;
import com.socsi.smartposapi.printer.Align;
import com.socsi.smartposapi.printer.FontLattice;
import com.socsi.smartposapi.printer.FontType;
import com.socsi.smartposapi.printer.Printer2;
import com.socsi.smartposapi.printer.TextEntity;

import io.flutter.plugin.common.MethodCall;

public class SunyardPrinter {

    private Printer2 print;
    private FontType mCh = FontType.SIMSUM, mEn = FontType.AVENIR_NEXT_CONDENSED_BLOD;

    public SunyardPrinter(Context context) {
        initPrinter(context);
    }

    private void initPrinter(Context context) {
        print = Printer2.getInstance(context);
        boolean havePrinter = false;
        try {
            havePrinter = print.havePrinter();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    public void startPrint(@NonNull MethodCall call) {
        // String stan, int originalMinorAmount, String terminalId, String merchantId,
        // String transmissionDate, String transactionComment
        // Bitmap bitmap = BitmapFactory.decodeResource();Ha
        // Bitmap bitmapForWatermark = print.createBitmapForWatermark(bitmap);
        // print.appendImage(bitmap);
        // print.appendImage(bitmapForWatermark, Align.CENTER);

        print.appendTextEntity2(
                new TextEntity(call.argument("vendorName"), mCh, mEn, FontLattice.THIRTY, false, Align.CENTER, true));
        print.appendTextEntity2(
                new TextEntity("Transaction Receipt\n", mCh, mEn, FontLattice.THIRTY, false, Align.CENTER, true));
        print.appendTextEntity2(print.getSeparatorLinetEntity());
        print.appendTextEntity2(new TextEntity("NGN" + call.argument("originalMinorAmount").toString() + "\n",
                mCh, mEn, FontLattice.THIRTY, false, Align.CENTER, true));
        print.appendTextEntity2(print.getSeparatorLinetEntity());
        printText(call, "merchantName", "Merchant Name");
        printText(call, "merchantLocation", "Merchant Location");
        printText(call, "sender", "Sender");
        printText(call, "receiver", "Receiver");
        printText(call, "transactionType", "Transaction Type");
        printText(call, "payee", "Payee");
        printText(call, "phoneNumber", "Phone Number");
        printText(call, "service", "Service");
        printText(call, "beneficiaryName", "Beneficiary Name");
        printText(call, "bankName", "Bank Name");
        printText(call, "merchant", "Merchant");
        printText(call, "accountNumber", "Account Number");
        printText(call, "description", "Description");
        printText(call, "bill", "Bill");
        printText(call, "paymentItem", "Payment Item");
        printText(call, "billItem", "Bill Item");
        printText(call, "qty", "Qty");
        printText(call, "packageName", "Package Name");
        printText(call, "customerName", "Customer Name");
        printText(call, "customerId", "Customer Id");
        printText(call, "customerReference", "Customer Reference");
        printText(call, "transactionFee", "Transaction Fee");
        printText(call, "transactionRef", "Reference Number");
        printText(call, "originalTransStan", "Stan");
        printText(call, "cardPan", "Card PAN");
        printText(call, "tokenValue", "Token");
        printText(call, "expiryDate", "Card Expiry");
        printText(call, "terminalId", "TerminalID");
        printText(call, "merchantId", "MerchantID");
        printText(call, "reason", "Reason");
        printText(call, "agent", "Agent");
        printText(call, "time", "Time");
        printText(call, "transmissionDate", "Date");

        print.appendTextEntity2(print.getSeparatorLinetEntity());
        print.appendTextEntity2(new TextEntity(call.argument("transactionComment") + "\n", mCh, mEn,
                FontLattice.THIRTY, false, Align.CENTER, true));
        printFooter(call, "footer");
        print.appendTextEntity2(new TextEntity("\n", mCh, mEn,
                FontLattice.THIRTY, false, Align.CENTER, true));
        print.appendTextEntity2(print.getSeparatorLinetEntity());
        print.limitTimePrint(10, print.getPrintBuffer());
        print.startPrint();
    }

    public void printTransactionSummary(@NonNull MethodCall call) {
        print.initPrinter();
        print.setLetterSpacing(5);
        print.appendPrnStr(call.argument("vendorName"), fontNormal, Align.CENTER);
        print.appendPrnStr("Transaction Receipt", fontBig, Align.CENTER);
        print.appendPrnStr("--------------------------------", fontNormal, Align.CENTER);
        print.appendPrnStr("End of Day Report", fontNormal, Align.CENTER);
        print.appendPrnStr("--------------------------------", fontNormal, Align.CENTER);
        printText(call, "merchantName", "Merchant Name");
        printText(call, "merchantLocation", "Merchant Location");
        printText(call, "time", "Report Date");
        printText(call, "totalTransactionAmount", "Total");
        print.appendPrnStr("Total: NGN " + call.argument("totalTransactionAmount").toString(), fontNormal,
                Align.LEFT);
        printText(call, "totalTransactionCount", "Count");
        print.appendPrnStr("--------------------------------", fontNormal, Align.CENTER);
        print.appendPrnStr("Summary breakdown", fontNormal, Align.CENTER);
        print.appendPrnStr("--------------------------------", fontNormal, Align.CENTER);
        printSummaryList(call, "summaryList");
        print.appendPrnStr("--------------------------------", fontNormal, Align.CENTER);
        printFooter(call, "footer");
        print.appendPrnStr("\n", fontNormal, Align.LEFT);
        print.appendPrnStr("--------------------------------", fontNormal, Align.CENTER);
        print.startPrint();
    }

    private void printSummaryList(@NonNull MethodCall call, String key) {
        // ["type1,count1,200", "type2,count3,500"]

        ArrayList<String> transactions = call.argument(key);

        for (String transaction : transactions) {
            String[] transactionsParts = transaction.split(",");

            String transactionType = transactionsParts[0];
            String transactionCount = transactionsParts[1];
            String transactionValue = transactionsParts[2];

            print.appendPrnStr(transactionType, fontNormal, Align.LEFT);
            print.appendPrnStr("Count: " + transactionCount + "\n", fontNormal,
                    Align.LEFT);
            print.appendPrnStr("Value: " + "NGN " + transactionValue + "\n\n",
                    fontNormal,
                    Align.LEFT);

        }

    }

    private void printText(@NonNull MethodCall call, String key, String title) {
        if (call.argument(key) != null) {
            print.appendTextEntity2(new TextEntity(title + ": " + call.argument(key) + "\n", mCh, mEn,
                    FontLattice.TWENTY_FOUR, false, Align.LEFT, true));
        }
    }

    private void printTitle(@NonNull MethodCall call, String key) {
        print.appendPrnStr(call.argument(key), fontNormal, Align.LEFT);
    }

    private void printFooter(@NonNull MethodCall call, String key) {
        if (call.argument(key) != null) {
            print.appendTextEntity2(new TextEntity(call.argument(key) + "\n\n\n\n", mCh, mEn,
                    FontLattice.TWENTY, false, Align.CENTER, true));
        } else {
            print.appendTextEntity2(new TextEntity("Built on Fizido, Powered by Support MFB" + "\n\n\n\n",
                    mCh, mEn, FontLattice.TWENTY, false, Align.CENTER, true));
        }
    }
}
