import 'dart:async';
import 'dart:convert';

import 'package:empressa_pos/bluetooth_devices.dart';
import 'package:empressa_pos/card_details.dart';
import 'package:flutter/services.dart';

class EmpressaPos {
  static const MethodChannel _channel = const MethodChannel('empressa_pos');

  static Future<CardDetails?> search(int transactionAmount) async {
    CardDetails? cardDetails;
    try {
      var result = await _channel
          .invokeMethod('searchCard', {"transactionAmount": transactionAmount});
      print(result);
      var cardResponse = Map<String, String>.from(result);
      cardDetails = CardDetails.fromJson(cardResponse);
      var track2Data = cardDetails.the57!;
      var strTrack2 = track2Data.split("F")[0];
      var pan = strTrack2.split('D')[0];
      var expiry = strTrack2.split('D')[1].substring(0, 4);
      var src = strTrack2.split("D")[1].substring(4, 7);
      cardDetails.strTrack2 = strTrack2;
      cardDetails.pan = pan;
      cardDetails.expiry = expiry;
      cardDetails.src = src;
    } on PlatformException catch (e) {
      // cardDetails = null;
      print(e.stacktrace);
    }
    return cardDetails;
  }

  static Future<void> initializeTerminal() async {
    try {
      await _channel.invokeMethod('initEmv');
    } catch (e) {
      print(e);
    }
  }

  static Future<void> stopSearch() async {
    try {
      await _channel.invokeMethod('stopSearch');
    } catch (e) {
      print(e);
    }
  }

  static Future<void> sunyardPrint(Map<String, dynamic> printerDetails) async {
    try {
      await _channel.invokeMethod('startPrinter', printerDetails);
    } catch (e) {
      print(e);
    }
  }

  static Future<void> sunyardPrintSummary(
      Map<String, dynamic> printerDetails) async {
    try {
      await _channel.invokeMethod('startSummaryPrinter', printerDetails);
    } catch (e) {
      print(e);
    }
  }

  static Future<bool?> checkCard() async {
    var result;
    try {
      result = await _channel.invokeMethod('checkSunyardCard');
    } catch (e) {
      print(e);
    }
    return result;
  }

  static Future<void> initializeMPos() async {
    var result;
    try {
      result = await _channel.invokeMethod('initializeMPos');
    } catch (e) {
      print(e);
    }
  }

  static Future<void> startPinInputTest() async {
    var result;
    try {
      result = await _channel.invokeMethod('testStartPinInput');
    } catch (e) {
      print(e);
    }
  }

  static Future<List<BluetoothDevices>?> startMPosDiscovery() async {
    var result;
    List<BluetoothDevices>? bluetoothDevices;
    try {
      result = await _channel.invokeMethod('startMPosDiscovery');
      bluetoothDevices = List<BluetoothDevices>.from(
          jsonDecode(result).map((x) => BluetoothDevices.fromJson(x)));
      print(bluetoothDevices.toString());
    } catch (e) {
      print(e);
    }
    return bluetoothDevices;
  }

  static Future<bool?> connectMPosDevice(
      {String? bluetoothName, String? bluetoothMac}) async {
    var result;
    try {
      result = await _channel.invokeMethod('connectMPos',
          {'bluetoothName': bluetoothName, 'bluetoothMac': bluetoothMac});
    } catch (e) {
      print(e);
    }
    return result;
  }
}
