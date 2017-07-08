object blockingApplication extends App {
  import domainModel._
  import longevity.context.LongevityContext
  import longevity.effect.Blocking

  // build the context for our domain model

  val context  = LongevityContext[Blocking, DomainModel]()
  val repo     = context.repo

  // we are now ready to start persisting users

  val username = Username("sméagol")
  val oldEmail = Email("gollum@gmail.example.com")
  val newEmail = Email("sméagol@gmail.example.com")
  val fullName = context.testDataGenerator.generate[FullName]
  val user     = User(username, oldEmail, fullName)

  // create, retrieve, update, delete

  val created   = repo.create(user)
  val retrieved = repo.retrieveOne[User](username)
  val modified  = retrieved.map(_.copy(email = newEmail))
  val updated   = repo.update(modified)
  val deleted   = repo.delete(updated)

  println(s"created   ${created.get}")
  println(s"retrieved ${retrieved.get}")
  println(s"updated   ${updated.get}")
  println(s"deleted   ${deleted.get}")

  repo.closeConnection

}
