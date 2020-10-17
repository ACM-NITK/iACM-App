class Project {
  final int id;
  final String title;
  final String description;
  final int status;
  final String date;
  final int sig;

  Project({
    this.id,
    this.title,
    this.description,
    this.status,
    this.date,
    this.sig
  });

  factory Project.returnMap(Map<String, dynamic> data) {
    return Project(
      id: data['id'],
      title: data['title'],
      description: data['description'],
      status: data['status'],
      date: data['date'],
      sig: data['sig']
    );
  }
}
