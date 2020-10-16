import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:multiselect_formfield/multiselect_formfield.dart';
import 'package:iACM/constants/constants.dart';

class NewUser extends StatefulWidget {
  _NewUserState createState() => _NewUserState();
}

class _NewUserState extends State<NewUser> {
  final _formKey = GlobalKey<FormState>();
  List<dynamic> options = [];
  String _email;
  String _password;
  String _passwordConfirmation;
  String _name;
  List<dynamic> _sigSelected;
  final UserRepository userRepository = UserRepository();

  @override
  void initState() {
    sig.forEach((key, value) {
      options.add(
        {
          "display": value,
          "value": key
        }
      );
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.close),
          onPressed: () => {Navigator.pop(context)},
        ),
        title: Text(
          'New User',
          style: TextStyle(
              fontSize: 16.0,
              fontWeight: FontWeight.w600,
              fontFamily: 'Poppins'),
        ),
      ),
      body: Container(
        child: SingleChildScrollView(
          scrollDirection: Axis.vertical,
          child: Container(
            padding: EdgeInsets.fromLTRB(2, 16, 2, 8),
            child: _form(),
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
                _password = value;
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
                  hintText: 'Confirm Password',
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
                } else if (value != _password) {
                  return 'Password mismatch';
                }
                return null;
              },
              onSaved: (String value) {
                _passwordConfirmation = value;
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
                  hintText: 'Enter Name',
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
                      child: Icon(Icons.person_add_alt_1_rounded),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              keyboardType: TextInputType.text,
              validator: (value) {
                _name = value;
                if (value.isEmpty) {
                  return 'Please enter a name';
                }
                return null;
              },
              onSaved: (String value) {
                _name = value;
              },
            ),
          ),
          Container(
            padding: EdgeInsets.all(20),
            child: MultiSelectFormField(
              titleText: 'Select SIG',
              dataSource: options,
              validator: (value) {
                if (value == null || value.length == 0) {
                  return 'Please choose an SIG';
                }
                return null;
              },
              onSaved: (newValue) {
                if (newValue == null) return;
                _sigSelected = newValue;
              },
              textField: 'display',
              valueField: 'value',
              okButtonLabel: 'OK',
              cancelButtonLabel: 'CANCEL',
              initialValue: _sigSelected,
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
                        'Save',
                        style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w500,
                            fontFamily: 'Poppins'),
                      ),
                      onPressed: () async {
                        if (_formKey.currentState.validate()) {
                          _formKey.currentState.save();
                          var result = await postNewUser(
                              _email, _password, _passwordConfirmation, _name, _sigSelected);
                          if (result) {
                            final snackBar = SnackBar(
                                content:
                                Text('User has been successfully added!'));
                            Scaffold.of(context).showSnackBar(snackBar);
                          } else {
                            final snackBar = SnackBar(
                                content: Text(
                                    'Sorry, we are facing a glitch at the moment!'));
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

  Future<bool> postNewUser(
      String email, String password, String passwordConfirmation, String name, List<dynamic> options) async {
    Map<String, dynamic> userDetails = {};
    userDetails['email'] = email;
    userDetails['password'] = password;
    userDetails['passwordConfirmation'] = passwordConfirmation;
    userDetails['name'] = name;
    userDetails['sig'] = options;
    var result = userRepository.insertUserDB(userDetails);
    return result;
  }
}
