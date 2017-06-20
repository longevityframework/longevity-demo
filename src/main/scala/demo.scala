package domainModel {
  import longevity.model.annotations._

  // first, define our domain classes:

  // gather all the domain classes into a domain model
  @domainModel trait DomainModel

  // the @persistent is the thing we want to persist in its own table
  @persistent[DomainModel]
  case class User(
    username: Username,
    email: Email,
    fullName: FullName)

  object User {
    // the primaryKey describes how we want to retrieve the objects
    implicit val usernameKey = primaryKey(props.username)
  }

  // @keyVal is the type of the values we can use to look up by key
  @keyVal[DomainModel, User]
  case class Username(username: String)

  // a @component is a part of the object we want to persist
  @component[DomainModel]
  case class Email(email: String)

  @component[DomainModel]
  case class FullName(
    last: String,
    first: String,
    title: Option[String])
}

object applicationServices extends App {
  import domainModel._
  import longevity.context.LongevityContext
  import scala.concurrent.ExecutionContext.Implicits.global

  // build the context for our domain model

  val context  = LongevityContext[DomainModel]()
  val repo     = context.repo

  // we are now ready to start persisting users

  val username = Username("sméagol")
  val oldEmail = Email("gollum@gmail.example.com")
  val newEmail = Email("sméagol@gmail.example.com")
  val fullName = context.testDataGenerator.generate[FullName]
  val user     = User(username, oldEmail, fullName)

  // create, retrieve, update, delete

  val f = for {
    ()        <- repo.openConnection()
    ()        <- repo.createSchema()
    created   <- repo.create(user)
    retrieved <- repo.retrieveOne[User](username)
    modified   = retrieved.map(_.copy(email = newEmail))
    updated   <- repo.update(modified)
    deleted   <- repo.delete(updated)
  } yield {
    println(s"created   ${created.get}")
    println(s"retrieved ${retrieved.get}")
    println(s"updated   ${updated.get}")
    println(s"deleted   ${deleted.get}")
  }

  // wait for the CRUD ops to complete

  import scala.concurrent.Await
  import scala.concurrent.duration.Duration

  Await.result(f, Duration.Inf)

  // close db connection after CRUD ops complete

  repo.closeConnection()

}
