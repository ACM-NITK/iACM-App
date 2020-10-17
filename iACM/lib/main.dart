import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:iACM/pages/dashboard.dart';
import 'package:iACM/pages/new_project.dart';
import 'package:iACM/pages/new_user.dart';
import 'package:iACM/pages/sig_projects_page.dart';
import 'package:iACM/pages/sign_in_page.dart';
import 'package:iACM/pages/view_projects.dart';
import 'package:shared_preferences/shared_preferences.dart';

Widget homeScreen() {
  return FutureBuilder<SharedPreferences>(
    future: SharedPreferences.getInstance(),
    builder: (context, snapshot) {
      if (snapshot.connectionState == ConnectionState.done) {
        if (snapshot.data.getString('currentUser') != null) {
          return MyHomePage();
        } else {
          return SignInPage();
        }
      } else if (snapshot.hasError) {
        return Container(
          child: Text('Sorry, we are facing a glitch at the moment!'),
        );
      }
      return Center(
        child: CircularProgressIndicator(),
      );
    },
  );
}

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'iACM',
      debugShowCheckedModeBanner: false,
      home: homeScreen(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Map<String, dynamic> currentUser;
  int admin;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Container(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Text(
                'iACM',
                style: TextStyle(
                    fontSize: 16.0,
                    color: Colors.white,
                    fontWeight: FontWeight.w600,
                    fontFamily: 'Poppins'),
              ),
            ],
          ),
        ),
        backgroundColor: Colors.black,
        elevation: 6,
      ),
      drawer: buildTile(),
      body: Dashboard(),
    );
  }

  Widget buildTile() {
    return FutureBuilder<SharedPreferences>(
      future: SharedPreferences.getInstance(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          currentUser = jsonDecode(snapshot.data.getString('currentUser'));
          admin = snapshot.data.getInt('admin');
          return Drawer(
            child: ListView(
              padding: EdgeInsets.zero,
              children: <Widget>[
                DrawerHeader(
                  child: Center(
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Container(
                          padding: EdgeInsets.fromLTRB(4, 4, 8, 4),
                          child: Image(
                            image: AssetImage('assets/acm.png'),
                            height: 40.0,
                          ),
                        ),
                        Text(
                          'iACM',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 14,
                            fontWeight: FontWeight.w600,
                            fontFamily: 'Poppins',
                          ),
                        ),
                      ],
                    ),
                  ),
                  decoration: BoxDecoration(
                    color: Colors.black,
                  ),
                ),
                ExpansionTile(
                  leading: Icon(Icons.grade),
                  title: Text('SIG'),
                  children: <Widget>[
                    ListTile(
                      title: Text('Sanganitra'),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => SigProjectsPage(1)));
                      },
                    ),
                    ListTile(
                      title: Text('Yantrika'),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => SigProjectsPage(2)));
                      },
                    ),
                    ListTile(
                      title: Text('Vidyut'),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => SigProjectsPage(3)));
                      },
                    ),
                    ListTile(
                      title: Text('Kaaryvarta'),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => SigProjectsPage(4)));
                      },
                    ),
                    ListTile(
                      title: Text('Saahitya'),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => SigProjectsPage(5)));
                      },
                    ),
                    ListTile(
                      title: Text('Media and Publicity'),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => SigProjectsPage(6)));
                      },
                    )
                  ],
                ),
                admin == 1 ?
                ListTile(
                  leading: Icon(Icons.account_circle),
                  title: Text('New User'),
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => NewUser()));
                  },
                ) : Container(),
                admin == 1 ?
                ListTile(
                  leading: Icon(Icons.work),
                  title: Text('New Project'),
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => NewProject()));
                  },
                ) : Container(),
                ListTile(
                  leading: Icon(Icons.album),
                  title: Text('View Projects'),
                  onTap: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => ViewProjects()));
                  },
                ),
                ListTile(
                  leading: Icon(Icons.delete_outline),
                  title: Text('Logout'),
                  onTap: () async {
                    SharedPreferences preferences =
                    await SharedPreferences.getInstance();
                    preferences.clear();
                    Navigator.pushReplacement(
                        context, MaterialPageRoute(builder: (context) => MyApp()));
                  },
                )
              ],
            ),
          );
        } else if (snapshot.hasError) {
          return Container(
            child: Text('We ran into an error!'),
          );
        }
        return Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }
}
