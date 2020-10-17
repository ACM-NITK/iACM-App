import 'dart:convert';
import 'dart:math';

import 'package:iACM/models/Project.dart';
import 'package:iACM/repository/clientDB.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:iACM/models/User.dart';

class UserRepository {
  final dbProvider = ClientDBProvider();
  // ignore: missing_return
  Future<Map<String, dynamic>> authenticateUser(
      String email, String password) async {
    final userDB = await dbProvider.database;
    List<Map<String, dynamic>> result;
    result = (await userDB.query('users',
        where: 'email LIKE ?', whereArgs: [email], limit: 1));
    if (result.isNotEmpty && result != null) {
      if (result[0]['password'] == password) {
        var response = (await userDB.query('mentors',
            where: 'userId = ?', whereArgs: [result[0]['id']], limit: 1));
        if (response.isNotEmpty && response != null) {
          return {'auth': true, 'id': result[0]['id'], 'mentor': true};
        } else {
          var r = (await userDB.query('members',
              where: 'userId = ?', whereArgs: [result[0]['id']], limit: 1));
          if (r.isNotEmpty && r != null) {
            return {'auth': true, 'id': result[0]['id'], 'member': true};
          } else if (result[0]['admin'] == 1) {
            return {'auth': true, 'id': result[0]['id'], 'admin': 1};
          } else {
            return {'auth': true, 'id': result[0]['id'], 'admin': 0};
          }
        }
      } else {
        return {'auth': false};
      }
    } else {
      return {'auth': false};
    }
  }

  Future<List<Project>> getAllProjects() async {
    final projectsDB = await dbProvider.database;
    List<Project> projects = [];
    List<Map<String, dynamic>> result;
    result = await projectsDB.query('projects');
    if (result.isNotEmpty && result != null) {
      projects = result.map((e) => Project.returnMap(e)).toList();
      return projects;
    }
    return projects;
  }

  Future<List<Project>> getProjects() async {
    final projectsDB = await dbProvider.database;
    List<Map<String, dynamic>> result_1;
    List<Map<String, dynamic>> result_2;
    List<Project> projects = [];
    SharedPreferences preferences = await SharedPreferences.getInstance();
    if (preferences.getBool('mentor') != null) {
      result_1 = await projectsDB.query('mentors',
          where: 'userId = ?',
          whereArgs: [jsonDecode(preferences.getString('currentUser'))['id']]);
      if (result_1.isNotEmpty && result_1 != null) {
        List<dynamic> list = result_1.map((e) => e['projectId']).toList();
        result_2 = await projectsDB.query('projects',
            where: 'id IN (${list.join(', ')})');
        if (result_2.isNotEmpty && result_2 != null) {
          projects = (result_2.map((e) => Project.returnMap(e)).toList());
          return projects;
        } else {
          return projects;
        }
      } else {
        return projects;
      }
    } else if (preferences.getBool('members') != null) {
      result_1 = await projectsDB.query('projects',
          where: 'userId = ?',
          whereArgs: [jsonDecode(preferences.getString('currentUser'))['id']]);
      if (result_1.isNotEmpty && result_1 != null) {
        List<dynamic> list = result_1.map((e) => e['projectId']).toList();
        result_2 = await projectsDB.query('projects',
            where: 'id IN (${list.join(', ')})');
        if (result_2.isNotEmpty && result_2 != null) {
          projects = result_2.map((e) => Project.returnMap(e)).toList();
          return projects;
        } else {
          return projects;
        }
      } else {
        return projects;
      }
    } else {
      return projects;
    }
  }

  Future<List<Project>> getSIGProjects(int sig) async {
    var projectsDB = await dbProvider.database;
    List<Project> projects = [];
    var result = await projectsDB.query('projects', where: 'sig = ?', whereArgs: [sig]);
    if (result != null && result.isNotEmpty) {
      projects = result.map((e) => Project.returnMap(e)).toList();
      return projects;      
    } else {
      return projects;
    }
  }

  Future<bool> insertUserDB(Map<String, dynamic> user) async {
    final userDB = await dbProvider.database;
    User u;
    if (user['password'] == user['passwordConfirmation']) {
      user['id'] = 50 + new Random().nextInt(100);
      user.remove('passwordConfirmation');
      user['admin'] = 0;
      user['sig'] = jsonEncode(user['sig']);
      u = User.returnMap(user);
    } else {
      return false;
    }
    var result = await userDB.insert('users', u.insertIntoDB());
    if (result != null) {
      return true;
    } else {
      return false;
    }
  }

  Future<bool> insertProjectDB(Map<String, dynamic> project) async {
    final projectDB = await dbProvider.database;
    project['id'] = 50 + new Random().nextInt(100);
    var result = await projectDB.insert('projects', project);
    if (result != null) {
      return true;
    } else {
      return false;
    }
  }

  Future<bool> insertIntoDB(Map<String, dynamic> data, String tableName) async {
    final db = await dbProvider.database;
    List<dynamic> userIds = data['userIds'];
    List<bool> results = [];
    userIds.forEach((element) async {
      var result = await db.insert(tableName, {"projectId": data['projectId'], "userId": element});
      if (result != null) {
        results.add(true);
      } else {
        results.add(false);
      }
    });
    if (results.indexOf(false) == -1) {
      return true;
    } else {
      return false;
    }
  }

  Future<List<dynamic>> getProjectDetails(String tableName, int projectId) async {
    final db = await dbProvider.database;
    List<dynamic> names = [];
    var result = await db.query(tableName, where: 'projectId = ?', whereArgs: [projectId]);
    if (result.isNotEmpty && result != null) {
      List<dynamic> userIds = result.map((e) => e['userId']).toList();
      var result_1 = await db.query('users', where: 'id IN (${userIds.join(', ')})');
      if (result_1.isNotEmpty && result_1 != null) {
        names = result_1.map((e) => e['name']).toList();
        return names;
      } else {
        return names;
      }
    } else {
      return names;
    }
  }

  Future<bool> updateProject(Map<String, dynamic> data) async {
    final projectsDB = await dbProvider.database;
    var result = await projectsDB.update('projects', data, where: 'id = ?', whereArgs: [data['id']]);
    if (result != null) {
      return true;
    } else {
      return false;
    }
  }
  
  Future<List<dynamic>> fetchUsers() async {
    final db = await dbProvider.database;
    List<dynamic> users = new List();
    var result = await db.query('users', where: 'admin = ?', whereArgs: [0]);
    if (result != null) {
      print(result.length);
      users = result.map((e) => {"display": e['name'], "value": e['id']}).toList();
      return users;
    }
    return [];
  }
}
