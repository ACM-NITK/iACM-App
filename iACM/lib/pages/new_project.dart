import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:iACM/constants/constants.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:intl/intl.dart';

class NewProject extends StatefulWidget {
  _NewProjectState createState() => _NewProjectState();
}

class _NewProjectState extends State<NewProject> {
  final _formKey = GlobalKey<FormState>();
  final UserRepository userRepository = UserRepository();
  final RegExp regExp = RegExp(r'[0-9]{2}-[0-9]{2}-[0-9]{4}');
  List<dynamic> options = [];
  String _title;
  String _description;
  String _date = '${DateTime.now().day}-${DateTime.now().month}-${DateTime.now().year}';
  int _sig;
  TextEditingController _projectDate = TextEditingController();

  @override
  void initState() {
    sig.forEach((key, value) {
      options.add({
        "display": value,
        "value": key
      });
    });
    super.initState();
  }

  @override
  void dispose() {
    _projectDate.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          'New Project',
          style: TextStyle(
              fontSize: 18, fontWeight: FontWeight.w600, fontFamily: 'Poppins'),
        ),
      ),
      body: Container(
        padding: EdgeInsets.fromLTRB(8, 8, 8, 8),
        child: SingleChildScrollView(
          physics: AlwaysScrollableScrollPhysics(),
          child: _form(),
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
                  hintText: 'Enter Title',
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
                      child: Icon(Icons.title),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              keyboardType: TextInputType.text,
              validator: (value) {
                if (value.isEmpty) {
                  return 'Please enter a title';
                }
                return null;
              },
              onSaved: (String value) {
                _title = value;
              },
            ),
          ),
          Container(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: TextFormField(
              style: TextStyle(
                fontSize: 20,
              ),
              controller: _projectDate,
              decoration: InputDecoration(
                  hintStyle:
                      TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                  hintText: 'DD-MM-YYYY',
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
                      child: Icon(Icons.calendar_today),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              onTap: () {
                FocusScope.of(context).requestFocus(new FocusNode());
                _selectProjectDate();
              },
              validator: (value) {
                if (value.isEmpty) {
                  return 'Please enter a date';
                } else if (!regExp.hasMatch(value)) {
                  return 'Please enter a valid date';
                }
                return null;
              },
              onSaved: (String value) {
                _date = value;
              },
            ),
          ),
          Container(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: TextFormField(
              obscureText: false,
              style: TextStyle(
                fontSize: 20,
              ),
              maxLines: 3,
              minLines: 1,
              decoration: InputDecoration(
                  hintStyle:
                  TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                  hintText: 'Enter Description',
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
                      child: Icon(Icons.description),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              keyboardType: TextInputType.text,
              validator: (value) {
                if (value.isEmpty) {
                  return 'Please enter a description';
                }
                return null;
              },
              onSaved: (String value) {
                _description = value;
              },
            ),
          ),
          Container(
            padding: EdgeInsets.all(20),
            child: DropdownButtonFormField(
              decoration: InputDecoration(
                  labelText: 'SIG*'
              ),
              items: options.map((e) {
                return new DropdownMenuItem<int>(
                  value: e['value'],
                  child: Text(e['display']),
                );
              }).toList(),
              validator: (value) {
                if (value == null) {
                  return "Please choose an SIG";
                }
                return null;
              },
              onChanged: (value) {
                setState(() {
                  _sig = int.tryParse(value.toString());
                });
              },
              onSaved: (value) {
                if (value == null) return;
                _sig = int.tryParse(value.toString());
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
                        'Submit',
                        style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w500,
                            fontFamily: 'Poppins'),
                      ),
                      onPressed: () async {
                        if (_formKey.currentState.validate()) {
                          _formKey.currentState.save();
                          var result = await postProject(_title, _description, _date, _sig);
                          if (result) {
                            final snackBar = SnackBar(
                                content: Text('Project has been added!'));
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

  Future<Null> _selectProjectDate() async {
    final DateTime picked = await showDatePicker(
        context: context,
        initialDate: DateTime.parse(_date.length == 9 ? _date.substring(5, 9) + '-0${_date.substring(3, 4)}' + '-${_date.substring(0, 2)}' : _date.substring(6, 10) + '-${_date.substring(3, 5)}' + '-${_date.substring(0, 2)}'),
        firstDate: DateTime(2012),
        lastDate: DateTime(2101));
    if (picked != null && DateFormat('dd-MM-yyyy').format(picked).toString() != _date) {
      setState(() {
        _date = DateFormat('dd-MM-yyyy').format(picked).toString();
        _projectDate.value = TextEditingValue(text: _date);
      });      
    }
  }

  Future<bool> postProject(String title, String description, String date, int sig) async {
    Map<String, dynamic> projectDetails = {};
    projectDetails['title'] = title;
    projectDetails['description'] = description;
    projectDetails['status'] = 0;
    projectDetails['date'] = date;
    projectDetails['sig'] = sig;
    var result = await userRepository.insertProjectDB(projectDetails);
    return result;
  }
}
