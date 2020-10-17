import 'package:flutter/material.dart';
import 'package:iACM/models/Project.dart';
import 'package:iACM/pages/project_specification.dart';
import 'package:iACM/repository/user_repository.dart';
import 'package:iACM/constants/constants.dart';
import 'package:iACM/widgets/projectsCard.dart';

class SigProjectsPage extends StatefulWidget {
  final int sig;

  SigProjectsPage(this.sig);

  _SigProjectsPageState createState() => _SigProjectsPageState();
}

class _SigProjectsPageState extends State<SigProjectsPage> {
  final UserRepository userRepository = UserRepository();

  Future<List<Project>> getProjects() async {
    List<Project> list = await userRepository.getSIGProjects(widget.sig);
    return list;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          '${sig[widget.sig]}',
          style: TextStyle(
              fontSize: 22, fontWeight: FontWeight.w600, fontFamily: 'Poppins'),
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
            return Container(
              child: Center(
                child: Text('No projects found.'),
              ),
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
                              ProjectSpecification(snapshot.data[index])));
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
