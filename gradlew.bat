import 'package:flutter/material.dart';

void main() => runApp(HomePage());
double sliderValue = 3.9;

double statusBarHeight = 0;
double firstRowHeight = 0;
double sh;

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: homie());
  }
}

class homie extends StatefulWidget {
  @override
  _homieState createState() => _homieState();
}

class _homieState extends State<homie> {
  @override
  Widget build(BuildContext context) {
    screenSize(context);
    screenHeight(context);
    screenWidth(context);
    statusBarHeight = StatusBarHeight(context);
    return Scaffold(
      body: Container(
        child: CustomPaint(
            painter: ShapesPainter(),
            child: Container(
                height: 1000,
                width: 1000,
                child: Column(
                  children: <Widget>[
                    SizedBox(height: statusBarHeight),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: <Widget>[
                        SizedBox(height: firstRowHeight, width: 5),
                        Column(
                          children: <Widget>[
                            Container(
                              width: screenWidth(context) / 9,
                              height: screenWidth(context) / 9,
                              child: IconButton(
                                  onPressed: () {},
                                  icon: Image(
                                    image: AssetImage("images/menu.png"),
                                  )),
                            ),
                            SizedBox(
                              height:
                                  (firstRowHeight - screenWidth(context) / 9) *
                                      0.5,
                            )
                          ],
                        ),
                        SizedBox(width: screenWidth(context) / 6),
                        Container(
          