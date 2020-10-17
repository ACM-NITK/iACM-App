import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:iACM/pages/add_user_to_project.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:iACM/constants/constants.dart';
import 'package:intl/intl.dart';

class ProjectSpecification extends StatefulWidget {
  final dynamic projectSpec;

  ProjectSpecification(this.projectSpec);

  _ProjectSpecificationState createState() => _ProjectSpecificationState();
}

class _ProjectSpecificationState extends State<ProjectSpecification> {
  Map<String, dynamic> user;
  int admin;
  List<dynamic> mentorList = [];
  List<dynamic> memberList = [];
  final RegExp regExp = RegExp(r'[0-9]{2}-[0-9]{2}-[0-9]{4}');
  List<dynamic> options = [];
  final UserRepository userRepository = UserRepository();
  final _formKey = GlobalKey<FormState>();
  String _date;
  String _title;
  String _description;
  int _status;
  TextEditingController _projectTitle = TextEditingController();
  TextEditingController _projectDesc = TextEditingController();
  TextEditingController _projectDate = TextEditingController();
  TextEditingController _projectStatus = TextEditingController();
  int _sig;

  @override
  void initState() {
    _date = widget.projectSpec.date;
    _title = widget.projectSpec.title;
    _description = widget.projectSpec.description;
    _status = widget.projectSpec.status;
    _sig = widget.projectSpec.sig;
    _projectDate.value = TextEditingValue(text: _date);
    _projectTitle.value = TextEditingValue(text: _title);
    _projectDesc.value = TextEditingValue(text: _description);
    _projectStatus.value = TextEditingValue(text: _status.toString());
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
    _projectTitle.dispose();
    _projectDesc.dispose();
    super.dispose();
  }

  Future<List<dynamic>> fetchMentors() async {
    mentorList = await userRepository.getProjectDetails(
        'mentors', widget.projectSpec.id);
    return mentorList;
  }

  Future<List<dynamic>> fetchMembers() async {
    memberList = await userRepository.getProjectDetails(
        'members', widget.projectSpec.id);
    return memberList;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(
            'Project Specifications',
            style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w600,
                fontFamily: 'Poppins'),
          ),
        ),
        body: SingleChildScrollView(
          physics: AlwaysScrollableScrollPhysics(),
          child: FutureBuilder<SharedPreferences>(
            future: SharedPreferences.getInstance(),
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                if (snapshot.data.getString('currentUser') != null) {
                  user = jsonDecode(snapshot.data.getString('currentUser'));
                  admin = snapshot.data.getInt('admin');
                  if (user != null && admin == 1) {
                    return Center(
                      child: Container(
                        padding: EdgeInsets.fromLTRB(8, 16, 8, 18),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: <Widget>[
                            Padding(
                              padding: EdgeInsets.fromLTRB(8, 8, 8, 16),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Expanded(
                                    flex: 1,
                                    child: Wrap(
                                      direction: Axis.horizontal,
                                      children: [
                                        getMentors(),
                                        RaisedButton(
                                          shape: RoundedRectangleBorder(
                                            borderRadius: BorderRadius.circular(18.0),
                                          ),
                                          disabledColor: Colors.black45,
                                          hoverColor: Colors.blueGrey,
                                          color: Colors.blueAccent,
                                          textColor: Colors.white,
                                          padding: EdgeInsets.all(12.0),
                                          child: Text(
                                            'Add Mentor',
                                            style: TextStyle(
                                                fontSize: 16,
                                                fontWeight: FontWeight.w500,
                                                fontFamily: 'Poppins'),
                                          ),
                                          onPressed: () {
                                            Navigator.push(
                                                context,
                                                MaterialPageRoute(
                                                    builder: (context) => AddUserToProject(
                                                        widget.projectSpec.id, 'mentors')));
                                          },
                                        ),
                                      ],
                                    ),
                                  ),
                                  Expanded(
                                    flex: 1,
                                    child: Wrap(
                                      direction: Axis.horizontal,
                                      children: [
                                        getMembers(),
                                        RaisedButton(
                                          shape: RoundedRectangleBorder(
                                            borderRadius: BorderRadius.circular(18.0),
                                          ),
                                          disabledColor: Colors.black45,
                                          hoverColor: Colors.blueGrey,
                                          color: Colors.blueAccent,
                                          textColor: Colors.white,
                                          padding: EdgeInsets.all(12.0),
                                          child: Text(
                                            'Add Member',
                                            style: TextStyle(
                                                fontSize: 16,
                                                fontWeight: FontWeight.w500,
                                                fontFamily: 'Poppins'),
                                          ),
                                          onPressed: () {
                                            Navigator.push(
                                                context,
                                                MaterialPageRoute(
                                                    builder: (context) => AddUserToProject(
                                                        widget.projectSpec.id, 'members')));
                                          },
                                        ),
                                      ],
                                    ),
                                  )
                                ],
                              ),
                            ),
                            Container(
                              padding: EdgeInsets.all(32),
                              height: 2,
                              width: MediaQuery.of(context).size.width - 64,
                              color: Colors.grey,
                            ),
                            Container(
                              padding: EdgeInsets.all(20),
                              alignment: Alignment.center,
                              child: Text('Edit Project Specifications', style: TextStyle(
                                fontSize: 20,
                                fontWeight: FontWeight.w600,
                                fontFamily: 'Poppins'
                              ),),
                            ),
                            Container(
                              padding: EdgeInsets.all(32),
                              height: 2,
                              width: MediaQuery.of(context).size.width - 64,
                              color: Colors.grey,
                            ),
                            _form()
                          ],
                        ),
                      ),
                    );
                  } else if (snapshot.data.getBool('mentor')) {
                    return Center(
                      child: Container(
                        padding: EdgeInsets.fromLTRB(8, 16, 8, 16),
                        child: Column(
                          children: <Widget>[
                            getMentors(),
                            getMembers(),
                            RaisedButton(
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(18.0),
                              ),
                              disabledColor: Colors.black45,
                              hoverColor: Colors.blueGrey,
                              color: Colors.blueAccent,
                              textColor: Colors.white,
                              padding: EdgeInsets.all(12.0),
                              child: Text(
                                'Add Member',
                                style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.w500,
                                    fontFamily: 'Poppins'),
                              ),
                              onPressed: () {
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => AddUserToProject(
                                            widget.projectSpec.id, 'members')));
                              },
                            ),
                          ],
                        ),
                      ),
                    );
                  }
                } else {
                  return Center(
                    child: Container(
                      padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
                      child: Text('This is project page'),
                    ),
                  );
                }
              } else if (snapshot.hasError) {
                return Container(
                  padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
                  child: Text('Sorry, we are facing a glitch at the moment!'),
                );
              }
              return Center(
                child: CircularProgressIndicator(),
              );
            },
          ),
        ));
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
              controller: _projectTitle,
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
              obscureText: false,
              style: TextStyle(
                fontSize: 20,
              ),
              maxLines: 3,
              minLines: 1,
              controller: _projectDesc,
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
            child: TextFormField(
              obscureText: false,
              style: TextStyle(
                fontSize: 20,
              ),
              controller: _projectStatus,
              decoration: InputDecoration(
                  hintStyle:
                  TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                  hintText: 'Enter Status (between 0-100)',
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
                      child: Icon(Icons.pie_chart),
                    ),
                    padding: EdgeInsets.only(left: 30, right: 10),
                  )),
              keyboardType: TextInputType.number,
              validator: (value) {
                if (value.isEmpty) {
                  return 'Please enter status';
                } else if (int.tryParse(value) > 100 || int.tryParse(value) < 0) {
                  return '$value is out of range => 0% - 100%';
                }
                return null;
              },
              onSaved: (String value) {
                _status = int.tryParse(value);
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
            padding: EdgeInsets.all(20),
            child: DropdownButtonFormField(
              decoration: InputDecoration(
                  labelText: 'SIG*'
              ),
              value: _sig,
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
                      var result = await updateProject(widget.projectSpec.id, _title, _description, _status, _date, _sig);
                      if (result) {
                        final snackBar = SnackBar(
                            content: Text('Project has been updated!'));
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

  Future<bool> updateProject(int id, String title, String description, int status, String date, int sig) async {
    Map<String, dynamic> data = {};
    data['id'] = id;
    data['title'] = title;
    data['description'] = description;
    data['status'] = status;
    data['date'] = date;
    data['sig'] = sig;
    bool result = await userRepository.updateProject(data);
    return result;
  }

  Widget getMembers() {
    return FutureBuilder<List<dynamic>>(
      future: fetchMembers(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (snapshot.data.length == 0) {
            return Container(
              padding: EdgeInsets.fromLTRB(8, 8, 8, 16),
              child: Text('There are no members in this project.'),
            );
          }
          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: snapshot.data.map((e) {
              return Container(
                padding: EdgeInsets.fromLTRB(8, 8, 8, 16),
                child: Text(e),
              );
            }).toList(),
          );
        } else if (snapshot.hasError) {
          return Center(
            child: Text('Sorry, we are facing a glitch at the moment!'),
          );
        }
        return Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }

  Widget getMentors() {
    return FutureBuilder<List<dynamic>>(
      future: fetchMentors(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (snapshot.data.length == 0) {
            return Container(
              padding: EdgeInsets.fromLTRB(8, 8, 8, 16),
              child: Text('There are no mentors for this project'),
            );
          }
          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: snapshot.data.map((e) {
              return Container(
                padding: EdgeInsets.fromLTRB(8, 8, 8, 16),
                child: Text(e),
              );
            }).toList(),
          );
        } else if (snapshot.hasError) {
          return Center(
            child: Text('Sorry, we are facing a glitch at the moment!'),
          );
        }
        return Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }
}
