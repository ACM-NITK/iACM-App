import 'package:flutter/material.dart';
import 'package:iACM/models/Project.dart';

Widget projectCard(BuildContext context, Project project) {
  double width = MediaQuery.of(context).size.width;
  double height = MediaQuery.of(context).size.height;
  return Container(
    width: width,
    height: width > height ? height/2 : height/4,
    padding: EdgeInsets.fromLTRB(12, 12, 12, 12),
    child: Container(
      padding: EdgeInsets.only(left: 12.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
        color: Color(0xFF6666ff),
        boxShadow: [
          BoxShadow(
            color: Colors.black45,
            offset: Offset(1, 1),
            blurRadius: 4,
          )
        ],
      ),
      child: Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.only(
                topRight: Radius.circular(10.0),
                bottomRight: Radius.circular(10.0)),
            color: Color(0xFFccccff),
          ),
          child: Stack(
            children: <Widget>[
              Container(
                padding: EdgeInsets.fromLTRB(0, 0, 28, 0),
                alignment: Alignment.centerLeft,
                child: Image.asset('assets/acm.png', height: 100, width: 100,),
              ),
              Container(
                alignment: Alignment.topCenter,
                padding: EdgeInsets.fromLTRB(0, 12, 4, 0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    Container(
                      padding: EdgeInsets.fromLTRB(4, 4, 4, 16),
                      child: Text(
                        project.title,
                        style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.w600,
                            fontFamily: 'Poppins'),
                      ),
                    ),
                    Container(
                      padding: EdgeInsets.fromLTRB(4, 4, 4, 16),
                      child: Text(
                        project.date,
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w500,
                          fontFamily: 'Soleil',
                        ),
                      ),
                    ),
                    Expanded(flex: 4, child: Container()),
                    Container(
                      padding: EdgeInsets.fromLTRB(4, 4, 4, 4),
                      child: Text(
                        '${project.status}% Completed',
                        style: TextStyle(
                          fontStyle: FontStyle.italic,
                          fontWeight: FontWeight.w400,
                        ),
                      ),
                    ),
                    LinearProgressIndicator(
                      value: (project.status/100).toDouble(),
                    ),
                  ],
                ),
              )
            ],
          )),
    ),
  );
}
