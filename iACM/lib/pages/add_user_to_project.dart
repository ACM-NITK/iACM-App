import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:multiselect_formfield/multiselect_formfield.dart';

class AddUserToProject extends StatefulWidget {
  final projectId;
  final tableName;

  AddUserToProject(this.projectId, this.tableName);

  _AddUserToProjectState createState() => _AddUserToProjectState();
}

class _AddUserToProjectState extends State<AddUserToProject> {
  List<dynamic> _userSelected = [];
  final UserRepository userRepository = UserRepository();
  final _formKey = GlobalKey<FormState>();

  Future<List<dynamic>> fetchUsers() async {
    List<dynamic> users = await userRepository.fetchUsers();
    return users;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
            icon: Icon(Icons.close),
            onPressed: () {
              Navigator.pop(context);
            }),
        title: Text(
          'Add User as ${widget.tableName.substring(0, widget.tableName.length - 1)}',
          style: TextStyle(
              fontSize: 16, fontWeight: FontWeight.w600, fontFamily: 'Poppins'),
        ),
      ),
      body: FutureBuilder<List<dynamic>>(
        future: fetchUsers(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            if (snapshot.hasData) {
              if (snapshot.data.length == 0) {
                return Center(
                  child: Text('There are no users!'),
                );
              }
              return _form(snapshot.data);
            }
          }
          return Center(
            child: CircularProgressIndicator(),
          );
        },
      )
    );
  }

  Widget _form(List<dynamic> users) {
    return Form(
      key: _formKey,
      child: Column(
        children: <Widget>[
          Container(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: MultiSelectFormField(
              titleText: 'Select User',
              dataSource: users,
              validator: (value) {
                if (value == null || value.length == 0) {
                  return 'Please choose a user';
                }
                return null;
              },
              onSaved: (newValue) {
                if (newValue == null) return;
                _userSelected = newValue;
              },
              textField: 'display',
              valueField: 'value',
              okButtonLabel: 'OK',
              cancelButtonLabel: 'CANCEL',
              initialValue: _userSelected,
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
                          var result = await postUser(_userSelected, widget.projectId);
                          if (result) {
                            final snackBar = SnackBar(
                                content: Text(
                                    'Users have been added as ${widget.tableName.substring(0, widget.tableName.length - 1)}'));
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

  Future<bool> postUser(List<dynamic> userIds, int projectId) async {
    Map<String, dynamic> data = {};
    data['userIds'] = userIds;
    data['projectId'] = projectId;
    var response = await userRepository.insertIntoDB(data, widget.tableName);
    return response;
  }
}
