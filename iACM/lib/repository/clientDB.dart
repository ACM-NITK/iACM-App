import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'dart:math';
import 'package:iACM/models/User.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';

final userTable = 'users';
final projectsTable = 'projects';
final mentorsTable = 'mentors';
final membersTable = 'members';

class ClientDBProvider {
  static final ClientDBProvider dbProvider = ClientDBProvider();

  Database _database;

  Future<Database> get database async {
    if (_database != null) return _database;
    _database = await createDatabase();
    return _database;
  }

  createDatabase() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    String path = join(documentsDirectory.path, 'client.db');
    var database = await openDatabase(path, version: 1, onCreate: initDB);
    return database;
  }

  void initDB(Database database, int version) async {
    await database.execute(
        'CREATE TABLE $userTable (id INTEGER PRIMARY KEY, email TEXT, password TEXT, name TEXT, admin INTEGER, sig TEXT)');
    List<User> users = [];
    for (int i = 0; i < 7; i++) {
      int random = 1 + new Random().nextInt(6);
      int random1 = 1 + new Random().nextInt(6);
      List<int> sig = [];
      sig.add(random);
      sig.add(random1);
      users.add(User.returnMap({
        'id': i + 1,
        'email': i == 0 ? 'acm@nitk.edu.in' : 'acm_$i@nitk.edu',
        'password': 'acmnitk@357',
        'name': 'Acm NITK $i',
        'admin': i == 0 ? 1 : i == 5 ? 1 : 0,
        'sig': jsonEncode(sig)
      }));
      Map<String, dynamic> map = users[i].insertIntoDB();
      await database.insert('$userTable', map);
    }
    await database.execute(
        'CREATE TABLE $projectsTable (id INTEGER PRIMARY KEY, title TEXT, description TEXT, status INTEGER, date TEXT, sig INTEGER)');
    await database.insert(
        projectsTable, {'id': '1', 'title': 'Project-1', 'description': 'Project-1', 'status': '50', 'date': '22-07-2020', 'sig': '1'});
    await database.insert(
        projectsTable, {'id': '2', 'title': 'Project-2', 'description': 'Project-2', 'status': '10', 'date': '22-07-2020', 'sig': '2'});
    await database.insert(
        projectsTable, {'id': '3', 'title': 'Project-3', 'description': 'Project-3', 'status': '40', 'date': '22-07-2020', 'sig': '3'});
    await database.insert(
        projectsTable, {'id': '4', 'title': 'Project-4', 'description': 'Project-4', 'status': '10', 'date': '22-07-2020', 'sig': '4'});
    await database.insert(
        projectsTable, {'id': '5', 'title': 'Project-5', 'description': 'Project-5', 'status': '80', 'date': '22-07-2020', 'sig': '5'});
    await database.insert(
        projectsTable, {'id': '6', 'title': 'Project-6', 'description': 'Project-6', 'status': '20', 'date': '22-07-2020', 'sig': '6'});
    await database.insert(
        projectsTable, {'id': '7', 'title': 'Project-7', 'description': 'Project-7', 'status': '75', 'date': '22-07-2020', 'sig': '1'});
    await database.execute(
        'CREATE TABLE $mentorsTable (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, projectId INTEGER)');
    await database.insert(mentorsTable, {'userId': '2', 'projectId': '2'});
    await database.insert(mentorsTable, {'userId': '2', 'projectId': '3'});
    await database.insert(mentorsTable, {'userId': '4', 'projectId': '6'});
    await database.execute(
        'CREATE TABLE $membersTable (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, projectId INTEGER)');
    await database.insert(membersTable, {'userId': '3', 'projectId': '4'});
    await database.insert(membersTable, {'userId': '3', 'projectId': '5'});
    await database.insert(mentorsTable, {'userId': '5', 'projectId': '7'});
  }
}
