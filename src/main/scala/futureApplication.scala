package f
@longevity.model.annotations.persistent[domainModel.DomainModel] case class Foo()


object futureApplication extends App {
  import domainModel._
  import longevity.context.LongevityContext
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future

  // build the context for our domain model

  val context  = LongevityContext[Future, DomainModel]()
  val repo     = context.repo

  // we are now ready to start persisting users

  val username = Username("sméagol")
  val oldEmail = Email("gollum@gmail.example.com")
  val newEmail = Email("sméagol@gmail.example.com")
  val fullName = context.testDataGenerator.generate[FullName]
  val user     = User(username, oldEmail, fullName)

  // create, retrieve, update, delete

  val f = for {
    created   <- repo.create(user)
    retrieved <- repo.retrieveOne[User](username)
    modified   = retrieved.modify(_.copy(email = newEmail))
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

  repo.closeConnection

}
