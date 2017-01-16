package domainModel {
  import longevity.model.annotations._

  // define our domain classes:

  @persistent(keySet = Set(partitionKey(User.props.username)))
  case class User(
    username: Username,
    email: Email,
    fullName: FullName)

  @keyVal[User]
  case class Username(username: String)

  @component
  case class Email(email: String)

  @component
  case class FullName(
    last: String,
    first: String,
    title: Option[String])

  // gather all the domain classes into a domain model:

  @domainModel
  object DomainModel
}

object applicationServices extends App {
  import domainModel._
  import longevity.context.LongevityContext
  import scala.concurrent.ExecutionContext.Implicits.global

  // build the context for our domain model

  val context  = LongevityContext(DomainModel)

  // get the user repository

  val userRepo = context.repoPool[User]

  // we are now ready to start persisting users

  val username = Username("sméagol")
  val oldEmail = Email("gollum@gmail.example.com")
  val newEmail = Email("sméagol@gmail.example.com")
  val fullName = context.testDataGenerator.generate[FullName]
  val user     = User(username, oldEmail, fullName)

  // create, retrieve, update, delete

  for {
    created   <- userRepo.create(user)
    retrieved <- userRepo.retrieveOne(username)
    modified   = retrieved.map(_.copy(email = newEmail))
    updated   <- userRepo.update(modified)
    deleted   <- userRepo.delete(updated)
  } yield {
    println(s"created   ${created.get}")
    println(s"retrieved ${retrieved.get}")
    println(s"updated   ${updated.get}")
    println(s"deleted   ${deleted.get}")

    // close db connection after CRUD ops complete

    context.repoPool.closeSession()
  }

}
