import 'dart:convert';

class User {
  final int id;
  final String email;
  final String password;
  final String name;
  final bool admin;
  final List<dynamic> sig;

  User({
    this.id,
    this.email,
    this.password,
    this.name,
    this.admin,
    this.sig
  });

  factory User.returnMap(Map<String, dynamic> data) {
    List<dynamic> sig = jsonDecode(data['sig']);
    bool admin = data['admin'] == 1 ? true : false;
    return User(
      id: data['id'],
      email: data['email'],
      password: data['password'],
      name: data['name'],
      admin: admin,
      sig: sig
    );
  }

  Map<String, dynamic> insertIntoDB() => {
    'id': this.id,
    'email': this.email,
    'password': this.password,
    'name': this.name,
    'admin': this.admin ? 1 : 0,
    'sig': jsonEncode(this.sig)
  };
}