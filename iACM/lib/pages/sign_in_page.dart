import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:iACM/main.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SignInPage extends StatefulWidget {
  _SignInPageState createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {
  final _formKey = GlobalKey<FormState>();
  bool _success;
  String _email;
  String _password;
  SharedPreferences prefs;

  void initPrefs() async {
    prefs = await SharedPreferences.getInstance();
  }

  @override
  void initState() {
    initPrefs();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Sign In',
          style: TextStyle(
              fontSize: 18, fontWeight: FontWeight.w600, fontFamily: 'Poppins'),
        ),
      ),
      body: SingleChildScrollView(
        physics: AlwaysScrollableScrollPhysics(),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Container(
                padding: EdgeInsets.fromLTRB(4, 16, 4, 4),
                child: Text(
                  'iACM',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.w600,
                      fontFamily: 'Poppins'),
                ),
              ),
              Container(
                padding: EdgeInsets.fromLTRB(4, 8, 4, 8),
                child: Text(
                  'Official app of ACM NITK',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      fontFamily: 'Poppins'),
                ),
              ),
              Container(
                  height: 120,
                  width: 120,
                  child: Image.asset('assets/acm.png')),
              Container(
                padding: EdgeInsets.fromLTRB(4, 16, 4, 8),
                child: Text(
                  'Please sign in to continue',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.w600,
                      fontFamily: 'Poppins'),
                ),
              ),
              _form(),
            ],
          ),
        ),
      ),
    );
  }

  Widget _form() {
    return Form(
      key: _formKey,
      child: Column(
        children: <Widget>[
          Container(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: TextFormField(
              obscureText: false,
              style: TextStyle(
                fontSize: 20,
              ),
              decoration: InputDecoration(
                  hintStyle:
                      TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                  hintText: 'Enter Email',
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30),
                    borderSide: BorderSide(
                      color: Theme.of(context).primaryColor,
                      width: 2,
                    ),
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30),
                    borderSide: BorderSide(
                      color: Theme.of(context).primaryColor,
                      width: 3,
                    ),
                  ),
                  prefixIcon: Padding(
                    child: IconTheme(
                      data:
                          IconThemeData(color: Theme.of(context).primaryColor),
                      child: Icon(Icons.mail),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              keyboardType: TextInputType.text,
              validator: (value) {
                if (value.isEmpty) {
                  return 'Please enter your email';
                }
                return null;
              },
              onSaved: (String value) {
                _email = value;
              },
            ),
          ),
          Container(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: TextFormField(
              obscureText: true,
              style: TextStyle(
                fontSize: 20,
              ),
              decoration: InputDecoration(
                  hintStyle:
                      TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                  hintText: 'Enter Password',
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30),
                    borderSide: BorderSide(
                      color: Theme.of(context).primaryColor,
                      width: 2,
                    ),
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30),
                    borderSide: BorderSide(
                      color: Theme.of(context).primaryColor,
                      width: 3,
                    ),
                  ),
                  prefixIcon: Padding(
                    child: IconTheme(
                      data:
                          IconThemeData(color: Theme.of(context).primaryColor),
                      child: Icon(Icons.person),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              keyboardType: TextInputType.text,
              validator: (value) {
                if (value.isEmpty) {
                  return 'Please enter your password';
                }
                return null;
              },
              onSaved: (String value) {
                _password = value;
              },
            ),
          ),
          Builder(
              builder: (context) => Container(
                    padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
                    child: RaisedButton(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(18.0),
                      ),
                      disabledColor: Colors.black45,
                      hoverColor: Colors.blueGrey,
                      color: Colors.blueAccent,
                      textColor: Colors.white,
                      padding: EdgeInsets.all(12.0),
                      child: Text(
                        'Login',
                        style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w500,
                            fontFamily: 'Poppins'),
                      ),
                      onPressed: () async {
                        if (_formKey.currentState.validate()) {
                          _formKey.currentState.save();
                          var response =
                              await validateUserCredentials(_email, _password);
                          _success = response['auth'];
                          if (_success) {
                            prefs.setString(
                                'currentUser',
                                jsonEncode({
                                  'id': response['id'],
                                  'email': _email,
                                  'password': _password
                                }));
                            if (response.containsKey('mentor')) {
                              if (response['mentor']) {
                                prefs.setBool('mentor', true);
                              }
                            } else if (response.containsKey('member')) {
                              if (response['member']) {
                                prefs.setBool('member', true);
                              }
                            } else if (response.containsKey('admin')) {
                              if (response['admin'] != null) {
                                print(response['admin']);
                                prefs.setInt('admin', response['admin']);
                              }
                            }
                            final snackBar =
                                SnackBar(content: Text('Login successful!'));
                            Scaffold.of(context).showSnackBar(snackBar);
                            Navigator.of(context).pushReplacement(
                                MaterialPageRoute(
                                    builder: (context) => MyHomePage()));
                          } else {
                            final snackBar = SnackBar(
                                content: Text('Invalid login credentials.'));
                            Scaffold.of(context).showSnackBar(snackBar);
                          }
                        }
                      },
                    ),
                  ))
        ],
      ),
    );
  }

  Future<Map<String, dynamic>> validateUserCredentials(
      String email, String password) async {
    UserRepository userRepository = UserRepository();
    var result = await userRepository.authenticateUser(email, password);
    return result;
  }
}
