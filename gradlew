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
                          child: textWithStroke(
                            fontSize: screenWidth(context) / 6,
                            text: " CARDS ",
                            fontFamily: "Canin",
                            textColor: Colors.white,
                            strokeWidth: 2,
                            strokeColor: Colors.white,
                            fontWeight: FontWeight.normal,
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.fromLTRB(0, 6, 0, 0),
                          child: Container(
                            width: 40,
                            child: IconButton(
                                onPressed: () {},
                                icon: Image(
                                  image: AssetImage("images/add.png"),
                                )),
                          ),
                        ),
                      ],
                    ),
                    SizedBox(
                      height: sh * (1 / 7 - 1 / 13),
                      child: Visibility(
                        visible: false,
                        maintainInteractivity: true,
                        maintainSize: true,
                        maintainAnimation: true,
                        maintainState: true,
                        maintainSemantics: true,
                        child: Slider(
                          min: 1,
                          max: 7,
                          value: sliderValue,
                          onChanged: (newValue) {
                            setState(() {
                              if (newValue != 0) sliderValue = newValue;
                            });
                          },
                        ),
                      ),
                    ),
                    SizedBox(height: sh * (1 / 7 - 1 / 13) * 3 / 5),
                    Padding(
                      padding: const EdgeInsets.fromLTRB(20, 0, 20, 0),
                      child: Container(
                        height: screenHeight(context) * 0.69,
                        child: GridView.count(
                          crossAxisCount: (sliderValue).toInt(),
                          children: List.generate(100, (index) {
//                        index % 3 == 0 ? t = 10 : t = 0;

                            return GestureDetector(
                              child: GridTile(
                                child: Padding(
                                  padding: EdgeInsets.all(
                                      8.0 * 3 / (sliderValue).toInt()),
                                  child: CircleAvatar(
                                    radius: 43 * 3 / (sliderValue).toInt(),
                                    backgroundImage:
                                        AssetImage("images/male_profile.jpg"),
                                  ),
                    