import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:iACM/models/Project.dart';
import 'package:iACM/pages/project_specification.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:iACM/widgets/projectsCard.dart';

class ViewProjects extends StatefulWidget {
  _ViewProjectsState createState() => _ViewProjectsState();
}

class _ViewProjectsState extends State<ViewProjects> {
  List<Project> list = [];
  bool _refresh = false;
  final UserRepository _userRepository = UserRepository();

  Future<List<Project>> getProjects() async {
    if (_refresh) {
      list = [];
    }
    list = await _userRepository.getAllProjects();
    return list;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'View Projects',
          style: TextStyle(
              fontSize: 18, fontWeight: FontWeight.w600, fontFamily: 'Poppins'),
        ),
      ),
      body: buildProjects(),
    );
  }

  Widget buildProjects() {
    return FutureBuilder<List<Project>>(
      future: getProjects(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (snapshot.data.length == 0)
            return Center(
              child: Text('No projects found.'),
            );
          return ListView.builder(
            itemCount: snapshot.data.length,
            itemBuilder: (context, index) {
              return InkWell(
                onTap: () {
                  Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) =>
                                  ProjectSpecification(snapshot.data[index])))
                      .then((value) {
                    setState(() {
                      _refresh = true;
                      getProjects();
                    });
                  });
                },
                child: projectCard(context, snapshot.data[index]),
              );
            },
          );
        } else if (snapshot.hasError) {
          return Container(
            child: Text('${snapshot.error}'),
          );
        }
        return Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }
}
