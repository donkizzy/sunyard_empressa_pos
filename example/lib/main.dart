import 'dart:async';

import 'package:empressa_pos/bluetooth_devices.dart';
import 'package:empressa_pos/pos.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
 // EmpressaPos.initializeMPos();
  EmpressaPos.initializeTerminal();
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<BluetoothDevices> bluetoothDevices ;
  bool connectionResult ;

  @override
  void initState() {

    super.initState();

  }


  Future<bool> connectDevices({String bluetoothName,String bluetoothMac}) async {

    try {

     connectionResult =  await EmpressaPos.connectMPosDevice(bluetoothMac: bluetoothMac,bluetoothName: bluetoothName);
     setState(() {

     });
    } on PlatformException  catch (e) {
      print(e);
    }
    return connectionResult ;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [

            RaisedButton(onPressed: (){
              EmpressaPos.startPinInputTest();
            },
              child: Text('Search Card'),

            ),
            SizedBox(height: 20,),
            Expanded(child: ListView.separated(
              shrinkWrap: true,
              itemCount:  bluetoothDevices == null ? 0 :bluetoothDevices.length,
              itemBuilder: (BuildContext context, int index) {
                return InkWell(
                  onTap: (){
                    connectDevices(bluetoothName: bluetoothDevices[index].name,bluetoothMac: bluetoothDevices[index].address);
                  },
                    child: Text('${bluetoothDevices[index].name + bluetoothDevices[index].address}'));
              }, separatorBuilder: (BuildContext context, int index) {
                return SizedBox(height: 20,);
            },)),
            Text(connectionResult == true ? 'Connected' : 'I NO Fit CONNECT' + '$connectionResult')
          ],
        ),
      ),
    );
  }
}