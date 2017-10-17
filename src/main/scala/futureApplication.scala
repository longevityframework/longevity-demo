object futureApplication extends App {
  import domainModel._
  import longevity.context.LongevityContext
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future

  val context  = LongevityContext[Future, DomainModel]()
  val repo     = context.repo

  val username = Username("sméagol")
  val oldEmail = Email("gollum@gmail.example.com")
  val newEmail = Email("sméagol@gmail.example.com")
  val fullname = FullName("last", "first", Some("title"))
  val user     = User(username, oldEmail, fullname)

  val f = for {
    // created   <- repo.create(user)
    retrieved <- repo.retrieveOne[User](username)
    // modified   = retrieved.modify(_.copy(email = newEmail))
    // updated   <- repo.update(modified)
    // deleted   <- repo.delete(updated)
  } yield {
    // println(s"created   ${created.get}")
    println(s"retrieved ${retrieved.get}")
    // println(s"updated   ${updated.get}")
    // println(s"deleted   ${deleted.get}")
  }

  import scala.concurrent.Await
  import scala.concurrent.duration.Duration

  Await.result(f, Duration.Inf)

  repo.closeConnection

}
